package dev.application.common.logic;

import java.io.File;
import java.util.List;

import dev.application.common.book.read.FindThresHoldCsv;
import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.FindBookInputDTO;
import dev.application.common.dto.FindBookOutputDTO;
import dev.application.common.dto.ReadFileOutputDTO;
import dev.application.common.exception.BusinessException;
import dev.application.common.file.ReadThresHoldFile;
import dev.application.entity.ThresHoldEntity;

/**
 * CSVを読み込み得点の閾値がどこなのかを集計するロジック
 * @author shiraishitoshio
 *
 */
public class AnalyzeThresholdGettingScoreLogic {

	/**
	 * CSV原本パス
	 */
	private static final String PATH = "/Users/shiraishitoshio/bookmaker/csv/";

	/**
	 * CSVコピー先パス
	 */
	private static final String COPY_PATH = "/Users/shiraishitoshio/bookmaker/csv/threshold/";

	/**
	 * 実行
	 */
	public void execute() {

		File directory = new File(COPY_PATH); // 削除したいフォルダのパス

		// フォルダが存在し、ディレクトリであることを確認
		if (directory.exists() && directory.isDirectory()) {
			// ディレクトリ内のファイル一覧を取得
			File[] files = directory.listFiles();

			if (files != null) {
				for (File file : files) {
					// ファイルまたはサブディレクトリがあれば削除
					if (file.isFile()) {
						forceDelete(file); // ファイルの強制削除
					} else if (file.isDirectory()) {
						forceDeleteDirectory(file); // サブディレクトリの強制削除
					}
				}
			}

			// 最後にディレクトリ自体を削除
			boolean isDeleted = directory.delete();
			if (isDeleted) {
				System.out.println("Force deleted directory: " + directory.getName());
			} else {
				System.out.println("Failed to force delete directory: " + directory.getName());
			}
		} else {
			System.out.println("The provided path is not a directory or does not exist.");
		}

		// 1. ブック探索クラスから特定のパスに存在する全ブックをリストで取得
		FindBookInputDTO findBookInputDTO = new FindBookInputDTO();
		findBookInputDTO.setDataPath(PATH);
		findBookInputDTO.setCopyPath(COPY_PATH);
		findBookInputDTO.setCopyFlg(true);
		FindThresHoldCsv findThresHoldCsv = new FindThresHoldCsv();
		FindBookOutputDTO findBookOutputDTO = findThresHoldCsv.execute(findBookInputDTO);
		// エラーの場合,戻り値の例外を業務例外に集約してスロー
		if (!BookMakersCommonConst.NORMAL_CD.equals(findBookOutputDTO.getResultCd())) {
			throw new BusinessException(
					findBookOutputDTO.getExceptionProject(),
					findBookOutputDTO.getExceptionClass(),
					findBookOutputDTO.getExceptionMethod(),
					findBookOutputDTO.getErrMessage(),
					findBookOutputDTO.getThrowAble());
		}

		ThresHoldSubLogic thresHoldSubLogic = new ThresHoldSubLogic();
		// 2. コピー先にコピーしたCSVリストを集計し,1ファイルずつ読み込む
		ReadThresHoldFile readThresHoldFile = new ReadThresHoldFile();
		int count = 0;
		for (String file : findBookOutputDTO.getBookList()) {
			ReadFileOutputDTO readFileOutputDTO = readThresHoldFile.getFileBody(file);
			if (!BookMakersCommonConst.NORMAL_CD.equals(readFileOutputDTO.getResultCd())) {
				throw new BusinessException(
						readFileOutputDTO.getExceptionProject(),
						readFileOutputDTO.getExceptionClass(),
						readFileOutputDTO.getExceptionMethod(),
						readFileOutputDTO.getErrMessage(),
						readFileOutputDTO.getThrowAble());
			}

			List<ThresHoldEntity> entityList = readFileOutputDTO.getReadHoldDataList();
			try {
				thresHoldSubLogic.execute(entityList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("chk book: " + count + "/" + findBookOutputDTO.getBookList().size());
			count++;
		}

	}

	// ファイルを強制的に削除するメソッド
	private static void forceDelete(File file) {
		if (file.exists()) {
			if (file.setWritable(true)) {
				if (file.delete()) {
					System.out.println("Force deleted: " + file.getAbsolutePath());
				} else {
					System.out.println("Failed to delete: " + file.getAbsolutePath());
				}
			} else {
				System.out.println("Unable to set writable permission on: " + file.getAbsolutePath());
			}
		}
	}

	// サブディレクトリを強制的に削除するメソッド
	private static void forceDeleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					forceDelete(file); // ファイルの強制削除
				} else if (file.isDirectory()) {
					forceDeleteDirectory(file); // サブディレクトリを再帰的に削除
				}
			}
		}
		// 最後にサブディレクトリを削除
		if (directory.setWritable(true)) {
			boolean isDeleted = directory.delete();
			if (isDeleted) {
				System.out.println("Force deleted directory: " + directory.getAbsolutePath());
			} else {
				System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
			}
		} else {
			System.out.println("Unable to set writable permission on directory: " + directory.getAbsolutePath());
		}
	}

}
