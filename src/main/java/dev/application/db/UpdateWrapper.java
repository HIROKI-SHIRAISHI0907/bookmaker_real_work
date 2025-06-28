package dev.application.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DB更新Wrapperクラス
 * @author shiraishitoshio
 *
 */
public class UpdateWrapper {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(UpdateWrapper.class);

	/**
	 * 条件に合うレコードを更新する
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @param setInfo SETする値
	 * @return 登録成功:0,登録失敗:9
	 */
	public int updateExecute(String tableId, String where, String setInfo) {
		SqlMainLogic update = new SqlMainLogic();
		int updateResult = -1;
		try {
			updateResult = update.executeUpdate(null, tableId, where, setInfo);
			return updateResult;
		} catch (Exception e) {
			logger.error("update error -> " , e);
			return -1;
		}
	}
}
