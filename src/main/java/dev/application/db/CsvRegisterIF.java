package dev.application.db;

import java.util.List;

/**
 * csv取得,チェックからDBに登録するまでの汎用インターフェース
 * @author shiraishitoshio
 *
 */
public interface CsvRegisterIF {

	/**
	 * DB作成処理
	 * @param db DB名
	 * @param tableId テーブルID
	 */
	void executeCreateDB(String db) throws Exception;

	/**
	 * テーブル削除処理
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 */
	void executeDrop(String auth, String tableId) throws Exception;

	/**
	 * テーブル作成処理
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 */
	void executeCreate(String auth, String tableId) throws Exception;

	/**
	 * Truncate処理
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 */
	void executeTruncate(String auth, String tableId) throws Exception;

	/**
	 * csv取得,チェック,DB登録ロジック
	 * @param tableId テーブルID
	 * @param dataList 登録リスト
	 * @param eachCount 単位処理件数
	 * @param allCount 全体処理件数
	 * @throws Exception
	 */
	void executeInsert(String tableId, List<?> dataList, int eachCount, int allCount) throws Exception;

}
