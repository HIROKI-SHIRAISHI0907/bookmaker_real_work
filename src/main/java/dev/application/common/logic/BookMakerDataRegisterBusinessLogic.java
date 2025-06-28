package dev.application.common.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.application.common.book.delete.DeleteBook;
import dev.application.common.commonservice.ConvertCsvAndReadFile;
import dev.application.common.commonservice.CopyFileAndGetFile;
import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.ConvertCsvAndReadFileOutputDTO;
import dev.application.common.dto.DeleteBookInputDTO;
import dev.application.common.dto.DeleteBookOutputDTO;
import dev.application.db.RegisterWrapper;
import dev.application.db.UniairConst;
import dev.application.entity.BookDataInsertEntity;

/**
 * BMデータ登録業務ロジック
 * @author shiraishitoshio
 *
 */
@Transactional
@Component
public class BookMakerDataRegisterBusinessLogic {

	/** Logger */
	//private static final Logger logger = LoggerFactory.getLogger(BookMakerDataRegisterBusinessLogic.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = BookMakerDataRegisterBusinessLogic.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = BookMakerDataRegisterBusinessLogic.class.getSimpleName();

	/**
	 * ブック探索クラス
	 */
	//@Autowired
	//private FindBook findBook;

	/**
	 * ファイル読み取りクラス
	 */
	//@Autowired
	//private ReadFile readFile;

	/**
	 * ブック削除クラス
	 */
	//@Autowired
	//private DeleteBook deleteBook;

	/**
	 * DB登録Repositoryクラス
	 */
	//@Autowired
	//private BmMstRepository bmMstRepository;

	/** 処理単位登録件数 */
	@Value("${bmbusiness.eachcounter:1}")
	private int registerEachCounter = 5;

	/**
	 * コピー先パス(外部設定値)
	 */
	@Value("${bmbusiness.aftercopypath:/Users/shiraishitoshio/bookmaker/copyfolder/}")
	private String copyPath = "/Users/shiraishitoshio/bookmaker/copyfolder/";

	/** .xlsx */
	private static final String XLSX = ".xlsx";

	/** .CSV */
	private static final String CSV = ".csv";

	/**
	 * 処理実行
	 * <p>
	 * 1. ファイル内のデータ取得</br>
	 * 2. DB登録処理</br>
	 * 3. ファイル削除処理</br>
	 * @param path 探索パス
	 * @return 0:正常終了, 4:警告終了, 9:異常終了
	 */
	public int execute(String path) {
		//logger.info(" db register businessLogic start : {} ", CLASS_NAME);

		CopyFileAndGetFile copyFileAndGetFile = new CopyFileAndGetFile();
		List<String> bookList = copyFileAndGetFile.execute(path, this.copyPath);

		// ファイル内のデータ取得
		ConvertCsvAndReadFile convertCsvAndReadFile = new ConvertCsvAndReadFile();
		for (String filePath : bookList) {
			//logger.info(" file name execute start : {} ", filePath);

			// CSV変換&読み取り
			ConvertCsvAndReadFileOutputDTO convertCsvAndReadFileOutputDTO = convertCsvAndReadFile.execute(filePath);
			String afterFilePath = convertCsvAndReadFileOutputDTO.getAfterCsvPath();

			// 読み取りができなかった場合次のループ
			List<BookDataInsertEntity> dbList = convertCsvAndReadFileOutputDTO.getReadDataList();
			if (dbList == null || dbList.isEmpty()) {
				continue;
			}

			// 変換,読み取り結果フラグ
			boolean convertCsvFlg = convertCsvAndReadFileOutputDTO.isConvertCsvFlg();
			boolean readCsvFlg = convertCsvAndReadFileOutputDTO.isReadCsvFlg();

			// 登録単位件数
			int registerEach = this.registerEachCounter;
			// DB登録処理
			RegisterWrapper register = new RegisterWrapper();
			int result = register.sceneCardMemberInsert(UniairConst.BM_M001, dbList, registerEach, dbList.size());
			// 登録フラグ
			boolean registerFlg = true;
			if (result != 0) {
				// 登録エラーの場合
				registerFlg = false;
			}

			//		int registerAllCount = 0;
			//		while (true) {
			//			int registerWhichEach = Math.min(registerEach, dbList.size() - registerAllCount);
			//			List<BookDataEntity> workEntityList = dbList.subList(registerAllCount,
			//					registerAllCount + registerWhichEach);
			//			int registerResult = this.bmMstRepository.soccerBmInsert(workEntityList);
			//			// 登録件数が一致しない
			//			if (registerResult != registerEach) {
			//				throw new SystemException(
			//						readFileOutputDTO.getExceptionProject(),
			//						readFileOutputDTO.getExceptionClass(),
			//						readFileOutputDTO.getExceptionMethod(),
			//						readFileOutputDTO.getErrMessage(),
			//						readFileOutputDTO.getThrowAble());
			//			}
			//			registerAllCount += registerResult;
			//			workEntityList.clear();
			//			if (registerAllCount == dbList.size()) {
			//				break;
			//			}
			//		}

			// ファイル削除処理
			// この時点でDB登録は完了しているため,削除できなかった場合,できるまで処理を実施
			int chkLoop = 0;
			while (true) {
				DeleteBook deleteBook = new DeleteBook();
				DeleteBookInputDTO deleteBookInputDTO = new DeleteBookInputDTO();
				deleteBookInputDTO.setDataPath(filePath);
				deleteBookInputDTO.setCopyPath(afterFilePath);
				// CSV変換,CSV読み取り,DB登録が全て完了した場合,オリジナルパスも削除
				if (convertCsvFlg && readCsvFlg && registerFlg) {
					// コピー元のオリジナルxlsxパスに置き換え
					String originalPath = filePath.replace("copyfolder/", "");
					deleteBookInputDTO.setOriginalPath(originalPath);
				}
				DeleteBookOutputDTO deleteBookOutputDTO = deleteBook.execute(deleteBookInputDTO);
				//DeleteBookOutputDTO deleteBookOutputDTO = this.deleteBook.execute(deleteBookInputDTO);
				if (!BookMakersCommonConst.NORMAL_CD.equals(deleteBookOutputDTO.getResultCd())) {
					//					logger.error(" delete file error -> file_name : {}, project_name : {}, class_name : {},"
					//							+ " method_name : {}, err_cd : {}, cause : {} ",
					//							afterFilePath,
					//							deleteBookOutputDTO.getExceptionProject(),
					//							deleteBookOutputDTO.getExceptionClass(),
					//							deleteBookOutputDTO.getExceptionMethod(),
					//							deleteBookOutputDTO.getErrMessage(),
					//							deleteBookOutputDTO.getThrowAble());
					chkLoop++;
					if (chkLoop == 3) {
						//logger.error(" delete file error -> {} ","削除が失敗しました。");
						break;
					}
				} else {
					break;
				}
			}

			//logger.info(" file name execute end : {} ", filePath);
		}

		//logger.info(" db register businessLogic end : {} ", CLASS_NAME);

		return 0;
	}
}
