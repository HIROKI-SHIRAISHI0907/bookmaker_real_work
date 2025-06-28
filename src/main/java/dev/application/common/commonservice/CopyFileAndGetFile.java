package dev.application.common.commonservice;

import java.util.List;

import dev.application.common.book.read.FindBook;
import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.FindBookInputDTO;
import dev.application.common.dto.FindBookOutputDTO;
import dev.application.common.exception.BusinessException;
/**
 * ファイルコピー&ファイルパス取得
 * @author shiraishitoshio
 *
 */
public class CopyFileAndGetFile {

	/**
	 * ファイルコピー&ファイルパス取得
	 * @param path オリジナルパス
	 * @param copyPath コピー先パス
	 * @return
	 */
	public List<String> execute(String path, String copyPath) {
		// コピー先パスを保存
		String originalCopyPath = copyPath;
		// フォルダに残っている場合削除する
//		try {
//			File dir = new File(originalCopyPath);
//			FileUtils.deleteDirectory(dir);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

		// 1. ブック探索クラスから特定のパスに存在する全ブックをリストで取得
		FindBookInputDTO findBookInputDTO = new FindBookInputDTO();
		findBookInputDTO.setDataPath(path);
		findBookInputDTO.setCopyPath(originalCopyPath);
		findBookInputDTO.setCopyFlg(true);
		FindBook findBook = new FindBook();
		FindBookOutputDTO findBookOutputDTO = findBook.execute(findBookInputDTO);
		//FindBookOutputDTO findBookOutputDTO = this.findBook.execute(findBookInputDTO);
		// エラーの場合,戻り値の例外を業務例外に集約してスロー
		if (!BookMakersCommonConst.NORMAL_CD.equals(findBookOutputDTO.getResultCd())) {
			throw new BusinessException(
					findBookOutputDTO.getExceptionProject(),
					findBookOutputDTO.getExceptionClass(),
					findBookOutputDTO.getExceptionMethod(),
					findBookOutputDTO.getErrMessage(),
					findBookOutputDTO.getThrowAble());
		}
		return findBookOutputDTO.getBookList();
	}

}
