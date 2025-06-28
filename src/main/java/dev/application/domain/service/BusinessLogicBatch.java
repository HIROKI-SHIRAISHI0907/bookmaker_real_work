package dev.application.domain.service;

/**
 * データ登録インターフェース
 * @author shiraishitoshio
 *
 */
public interface BusinessLogicBatch {

	/**
	 * output_XX.xlsxに書かれたデータをdbに登録するロジック
	 * @return 0:正常終了, 4:警告終了, 9:異常終了
	 */
	public int execute(String path);

}
