package dev.application.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DB登録Wrapperクラス
 * @author shiraishitoshio
 *
 */
public class RegisterWrapper {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(RegisterWrapper.class);

	/**
	 * csv登録する
	 * @param tableId テーブルID
	 * @param fileName ファイル名
	 * @param insertEntity 登録レコードEntity
	 * @param eachCount 単位処理件数
	 * @param allCount 全体処理件数
	 * @return 登録成功:0,登録失敗:9
	 */
	public int sceneCardMemberInsert(String tableId, List<?> insertEntity, int eachCount, int allCount) {
		CsvRegisterImpl register = new CsvRegisterImpl();
		try {
			register.executeInsert(tableId, insertEntity, eachCount, allCount);
		} catch (Exception e) {
			logger.error("register error -> " , e);
			return 9;
		}
		return 0;
	}
}
