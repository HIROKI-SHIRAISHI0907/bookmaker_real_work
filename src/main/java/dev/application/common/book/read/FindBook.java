package dev.application.common.book.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.FindBookInputDTO;
import dev.application.common.dto.FindBookOutputDTO;

/**
 * ブック読み取りクラス
 * @author shiraishitoshio
 *
 */
@Component
public class FindBook {

	/** Logger */
	//private static final Logger logger = LoggerFactory.getLogger(FindBook.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = FindBook.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = FindBook.class.getSimpleName();

	/** 探索件数 */
	@Value("${bmbusiness.findbookcounter:1}")
	private int findBookCounter = 300;

	/** output_ */
	private static final String OUTPUT_ = "output_";

	/** .xlsx */
	private static final String XLSX = ".xlsx";

	/**
	 * 探索するパスに存在するブックの情報を読み取る
	 * @param FindBookInputDTO
	 * @return FindBookOutputDTO
	 */
	public FindBookOutputDTO execute(FindBookInputDTO inputDTO) {
		//logger.info(" find book start : {} " , CLASS_NAME);

		final String METHOD_NAME = "execute";

		FindBookOutputDTO readBookOutputDTO = new FindBookOutputDTO();
		// コピーフラグを確認し,Trueならコピーしておく
		String findPath = inputDTO.getDataPath();
		String copyPath = null;
		if (inputDTO.isCopyFlg()) {
			copyPath = inputDTO.getCopyPath();
			try {
				// コピー先が存在しなければ作成
			    Path p2 = Paths.get(copyPath);
			    if (!Files.exists(p2)) {
			    	Files.createDirectory(p2);
			    }
				// 2秒ほど待機
				Thread.sleep(1200);
			} catch (IOException | InterruptedException e){
				readBookOutputDTO.setExceptionProject(PROJECT_NAME);
				readBookOutputDTO.setExceptionClass(CLASS_NAME);
				readBookOutputDTO.setExceptionMethod(METHOD_NAME);
				readBookOutputDTO.setResultCd(BookMakersCommonConst.ERR_CD_ERR_FOLDER_MAKES);
				readBookOutputDTO.setErrMessage(BookMakersCommonConst.ERR_MESSAGE_ERR_FOLDER_MAKES);
				readBookOutputDTO.setThrowAble(e);
				return readBookOutputDTO;
			}
		}

		List<String> bookList = null;
		// ファイルの存在確認
		try {
			bookList = getBookFiles(findPath, this.findBookCounter);
		} catch (IOException e) {
			readBookOutputDTO.setExceptionProject(PROJECT_NAME);
			readBookOutputDTO.setExceptionClass(CLASS_NAME);
			readBookOutputDTO.setExceptionMethod("getBookFiles");
			readBookOutputDTO.setResultCd(BookMakersCommonConst.ERR_CD_NO_FILE_EXISTS);
			readBookOutputDTO.setErrMessage(BookMakersCommonConst.ERR_MESSAGE_NO_FILE_EXISTS);
			readBookOutputDTO.setThrowAble(e);
			return readBookOutputDTO;
		}

		if (copyPath != null) {
			try {
				// コピー先のパスに変換したリストに置換
				int index = 0;
				for (String bookFilePath : bookList) {
					Path befPath = Paths.get(bookFilePath);
					bookFilePath = bookFilePath.replace(findPath, copyPath);
					Path afPath = Paths.get(bookFilePath);
					Files.copy(befPath, afPath, StandardCopyOption.REPLACE_EXISTING);

					bookList.set(index, bookFilePath);
					index++;
				}
			} catch (IOException e) {
				readBookOutputDTO.setExceptionProject(PROJECT_NAME);
				readBookOutputDTO.setExceptionClass(CLASS_NAME);
				readBookOutputDTO.setExceptionMethod(METHOD_NAME);
				readBookOutputDTO.setResultCd(BookMakersCommonConst.ERR_CD_ERR_FILE_COPY);
				readBookOutputDTO.setErrMessage(BookMakersCommonConst.ERR_MESSAGE_ERR_FILE_COPY);
				readBookOutputDTO.setThrowAble(e);
				return readBookOutputDTO;
			}
		}
		readBookOutputDTO.setResultCd(BookMakersCommonConst.NORMAL_CD);
		readBookOutputDTO.setBookList(bookList);

		//logger.info(" find book end : {} " , CLASS_NAME);

		return readBookOutputDTO;
	}

	/**
	 * パス内に存在するブックを検索する
	 * @param path チェックするパス
	 * @param findBookCounter 検索数
	 * @return bookPathList ブックリスト
	 * @throws IOException IOException
	 */
	private static List<String> getBookFiles(String path, int findBookCounter) throws IOException {
		List<String> bookPathList = new ArrayList<>();
		List<Path> bookPathPathList = new ArrayList<>();
		List<String> bookPathSortList = new ArrayList<>();
		Files.walk(Paths.get(path))
			.filter(Files::isRegularFile) // ファイルのみ
			.filter(pathStr -> pathStr.toString().endsWith(XLSX) ||
					pathStr.toString().startsWith(OUTPUT_))
			.forEach(bookPathPathList::add);
		for (Path pathStr : bookPathPathList) {
			if (pathStr.toString().contains("breakfile")) {
				continue;
			}
			String convString = pathStr.toString().replace(path, "");
			convString = convString.replace(OUTPUT_, "");
			convString = convString.replace(XLSX, "");
			bookPathSortList.add(convString);
		}
		// ソート(数字として)
		Collections.sort(bookPathSortList, Comparator.comparingInt(Integer::parseInt));
		for (String pathStr : bookPathSortList) {
			// output_をつける
			String convString = path + OUTPUT_ + pathStr + XLSX;
			if (bookPathList.size() > findBookCounter - 1) break;
			bookPathList.add(convString);
		}
		return bookPathList;
	}
}
