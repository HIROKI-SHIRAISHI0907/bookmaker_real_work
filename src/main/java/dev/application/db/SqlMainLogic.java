package dev.application.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.application.common.exception.BusinessException;
import dev.application.common.util.UniairColumnMapUtil;

/**
 * SQL作成用メインクラス
 * @author shiraishitoshio
 *
 */
public class SqlMainLogic {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SqlMainLogic.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = SqlMainLogic.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = SqlMainLogic.class.getSimpleName();

	/**
	 * コネクション情報
	 */
	public Connection con;
	/**
	 * ?のサイズ
	 */
	public int valSize = 0;
	/**
	 * テーブル名
	 */
	public String tableName;

	/**
	 * DBを作成する
	 * @param db DB名
	 * @throws Exception
	 */
	public void executeCreateDataBase(String db) throws Exception {
		// コネクション取得
		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(db);
		this.con = cons.getCon();

		// DB作成
		StringBuilder sb = new StringBuilder("CREATE DATABASE IF NOT EXISTS ");
		sb.append(db);
		try (Statement statement = this.con.createStatement();) {
			// 実行
			statement.execute(sb.toString());
			System.out.println("DB:" + db + "を作成しました。");
		} catch (SQLException e) {
			System.out.println("DB:" + db + "を作成できませんでした。");
			throw e;
		}

		// DB選択
		sb = new StringBuilder("USE ");
		sb.append(db);
		try (Statement statement = this.con.createStatement();) {
			// 実行
			statement.execute(sb.toString());
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * テーブルを削除する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @throws Exception
	 */
	public void executeDrop(String auth, String tableId) throws Exception {
		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		if ("ALL".equals(tableId)) {
			List<String> tableIdList = UniairColumnMapUtil.getAllTableIdList();
			for (String tableIdEach : tableIdList) {
				StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ");
				this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableIdEach);
				if (auth != null) {
					sb.append(auth);
					sb.append(".");
				}
				sb.append(this.tableName);
				try (Statement statement = con.createStatement();) {
					// 実行
					statement.execute(sb.toString());
					System.out.println(tableIdEach + ":" + this.tableName + "テーブルをdropしました。");
				} catch (SQLException e) {
					System.out.println(tableIdEach + ":" + this.tableName + "テーブルをdropできませんでした。");
					throw e;
				}
			}
		} else {
			StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ");
			this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
			if (auth != null) {
				sb.append(auth);
				sb.append(".");
			}
			sb.append(this.tableName);
			try (Statement statement = con.createStatement();) {
				// 実行
				statement.execute(sb.toString());
				System.out.println(tableId + ":" + this.tableName + "テーブルをdropしました。");
			} catch (SQLException e) {
				System.out.println(tableId + ":" + this.tableName + "テーブルをdropできませんでした。");
				throw e;
			}
		}
	}

	/**
	 * テーブルを作成する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @throws Exception
	 */
	public void executeCreate(String auth, String tableId) throws Exception {
		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		if ("ALL".equals(tableId)) {
			List<String> tableIdList = UniairColumnMapUtil.getAllTableIdList();
			for (String tableIdEach : tableIdList) {
				StringBuilder sb = new StringBuilder("CREATE TABLE ");
				this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableIdEach);
				if (auth != null) {
					sb.append(auth);
					sb.append(".");
				}
				sb.append(this.tableName);
				sb.append(" (");
				// カラムセット
				List<String> columnlist = UniairColumnMapUtil.getIncludingDefaultKeyMap(tableIdEach);
				// データ型
				List<String> dateTypeList = UniairColumnMapUtil.getCreateTableColumnType(tableIdEach);
				List<String> defaultColumnValueList = UniairColumnMapUtil.getDefaultCreateTableValueMap();
				dateTypeList.addAll(defaultColumnValueList);
				// 制約
				List<String> restList = UniairColumnMapUtil.getCreateTableColumnRest(tableIdEach);
				List<String> defaultColumnRestList = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					defaultColumnRestList.add("NOT NULL");
				}
				restList.addAll(defaultColumnRestList);
				for (int col = 0; col < columnlist.size(); col++) {
					sb.append(columnlist.get(col));
					sb.append(" ");
					sb.append(dateTypeList.get(col));
					sb.append(" ");
					sb.append(restList.get(col));
					sb.append(",");
					sb.append(" ");
				}
				// PRIMARY KEY
				List<String> primaryKeyList = UniairColumnMapUtil.getPrimaryKeyMap(tableIdEach);
				sb.append("PRIMARY KEY (");
				for (int col = 0; col < primaryKeyList.size(); col++) {
					sb.append(primaryKeyList.get(col));
					if (col < primaryKeyList.size() - 1) {
						sb.append(", ");
					}
				}
				sb.append(")");
				sb.append(");");
				try (Statement statement = con.createStatement();) {
					// 実行
					statement.execute(sb.toString());
					System.out.println(tableIdEach + ":" + this.tableName + "をcreateしました。");
				} catch (SQLException e) {
					System.out.println(
							tableIdEach + ":" + this.tableName + "をcreateできませんでした。(原因:" + e.getMessage() + ")");
				}
			}

		} else {
			StringBuilder sb = new StringBuilder("CREATE TABLE ");
			this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
			if (auth != null) {
				sb.append(auth);
				sb.append(".");
			}
			sb.append(this.tableName);
			sb.append(" (");
			// カラムセット
			List<String> columnlist = UniairColumnMapUtil.getIncludingDefaultKeyMap(tableId);
			// データ型
			List<String> dateTypeList = UniairColumnMapUtil.getCreateTableColumnType(tableId);
			List<String> defaultColumnValueList = UniairColumnMapUtil.getDefaultCreateTableValueMap();
			dateTypeList.addAll(defaultColumnValueList);
			// 制約
			List<String> restList = UniairColumnMapUtil.getCreateTableColumnRest(tableId);
			List<String> defaultColumnRestList = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				defaultColumnRestList.add("NOT NULL");
			}
			restList.addAll(defaultColumnRestList);
			for (int col = 0; col < columnlist.size(); col++) {
				sb.append(columnlist.get(col));
				sb.append(" ");
				sb.append(dateTypeList.get(col));
				sb.append(" ");
				sb.append(restList.get(col));
				sb.append(",");
				sb.append(" ");
			}
			// PRIMARY KEY
			List<String> primaryKeyList = UniairColumnMapUtil.getPrimaryKeyMap(tableId);
			sb.append(" PRIMARY KEY (");
			for (int col = 0; col < primaryKeyList.size(); col++) {
				sb.append(primaryKeyList.get(col));
				if (col < primaryKeyList.size() - 1) {
					sb.append(", ");
				}
			}
			sb.append(")");
			sb.append(")");
			try (Statement statement = con.createStatement();) {
				// 実行
				statement.execute(sb.toString());
				System.out.println(tableId + ":" + this.tableName + "をcreateしました。");
			} catch (SQLException e) {
				System.out.println(tableId + ":" + this.tableName + "をcreateできませんでした。(原因:" + e.getMessage() + ")");
			}
		}
	}

	/**
	 * トランケートをかける
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @throws SQLException
	 */
	public void executeTruncate(String auth, String tableId) throws Exception {
		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("TRUNCATE TABLE ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		if (auth != null) {
			sb.append(auth);
			sb.append(".");
		}
		sb.append(this.tableName);
		try {
			PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());
			// 実行
			preparedStatement.executeUpdate();
			//logger.info(" truncate success ");
		} catch (SQLException e) {
			//logger.error(" truncate err ->  ", e);
			throw e;
		}
	}

	/**
	 * 件数を取得する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @return 検索結果
	 * @throws Exception
	 */
	public int executeGetMin(String auth, String tableId, String col,
			String where) throws Exception {
		final String METHOD_NAME = "executeGetMin";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("SELECT MIN(" + col + ") AS mins FROM ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		if (where != null) {
			sb.append(" ");
			sb.append("WHERE ");
			sb.append(where);
		}
		sb.append(";");

		String cnt = "-1";
		try {
			Statement statement = this.con.createStatement();
			// 実行
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				cnt = rs.getString("mins");
			}
			//logger.info(" sql select record count -> {} 件のレコードが見つかりました。" ,cnt);
		} catch (Exception e) {
			//logger.error(" sql select count record error -> project_name : {}, class_name : {},"
			//		+ " method_name : {}, cause : {} " ,
			//		PROJECT_NAME, CLASS_NAME, METHOD_NAME,
			//		e);
			throw e;
		} finally {
			this.con.close();
		}
		if (cnt == null) {
			return -1;
		}
		return Integer.parseInt(cnt);
	}

	/**
	 * 件数を取得する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @return 検索結果
	 * @throws Exception
	 */
	public int executeCountSelect(String auth, String tableId,
			String where) throws Exception {
		final String METHOD_NAME = "executeCountSelect";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("SELECT COUNT(*) AS cnt FROM ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		if (where != null) {
			sb.append(" ");
			sb.append("WHERE ");
			sb.append(where);
		}
		sb.append(";");

		String cnt = "-1";
		try {
			Statement statement = this.con.createStatement();
			// 実行
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				cnt = rs.getString("cnt");
			}
			//logger.info(" sql select record count -> {} 件のレコードが見つかりました。" ,cnt);
		} catch (Exception e) {
			//logger.error(" sql select count record error -> project_name : {}, class_name : {},"
			//		+ " method_name : {}, cause : {} " ,
			//		PROJECT_NAME, CLASS_NAME, METHOD_NAME,
			//		e);
			throw e;
		} finally {
			this.con.close();
		}
		return Integer.parseInt(cnt);
	}

	/**
	 * 選択する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param selectList 選択カラム
	 * @param where 条件句
	 * @param sort ソート
	 * @param limit 何件分取得するか
	 * @return 検索結果
	 * @throws Exception
	 */
	public List<List<String>> executeSelect(String auth, String tableId, String[] selectList,
			String where, String sort, String limit) throws Exception {
		final String METHOD_NAME = "executeSelect";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		List<List<String>> resultList = new ArrayList<List<String>>();
		StringBuilder sb = new StringBuilder("SELECT ");
		int i = 0;
		for (String select : selectList) {
			sb.append(select);
			if (i < selectList.length - 1) {
				sb.append(",");
			}
			sb.append(" ");
			i++;
		}
		sb.append("FROM ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		if (where != null) {
			sb.append(" ");
			sb.append("WHERE ");
			sb.append(where);
		}
		if (sort != null) {
			sb.append(" ");
			sb.append("ORDER BY ");
			sb.append(sort);
		}
		if (limit != null) {
			sb.append(" ");
			sb.append("LIMIT ");
			sb.append(limit);
		}
		sb.append(";");
		// 実行SQL
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		try {
			Statement statement = this.con.createStatement();
			// 実行
			ResultSet rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				List<String> resultTmpList = new ArrayList<String>();
				for (String select : selectList) {
					resultTmpList.add(rs.getString(select));
				}
				resultList.add(resultTmpList);
			}
			//logger.info(" sql select record count -> {} 件のレコードが見つかりました。" ,resultList.size());
		} catch (Exception e) {
//			logger.error(" sql select record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}
		return resultList;
	}

	/**
	 * 選択する(SQLにASとFROMが使用されていること)
	 * @param sql SQL
	 * @return 検索結果
	 * @throws Exception
	 */
	public String executeSomethingSelect(String sql) throws Exception {
		if (!sql.contains("AS") || !sql.contains("FROM")) {
			throw new BusinessException("", "", "", "SQLにASとFROMが使用されていません。");
		}
		String[] split1 = sql.split("AS");
		String sp1 = split1[1].trim();
		String[] split2 = sp1.split("FROM");
		String sp2 = split2[0].trim();

		final String METHOD_NAME = "executeSomethingSelect";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		// 実行SQL
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		try {
			Statement statement = this.con.createStatement();
			// 実行
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				return rs.getString(sp2);
			}
			//logger.info(" sql select record count -> {} 件のレコードが見つかりました。" ,resultList.size());
		} catch (Exception e) {
//			logger.error(" sql select record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}
		return null;
	}

	/**
	 * 選択する(SQLにDISTINCTとFROMが使用されていること)
	 * @param sql SQL
	 * @return 検索結果
	 * @throws Exception
	 */
	public List<String> executeSomethingDistinctSelect(String sql) throws Exception {
		if (!sql.contains("DISTINCT") || !sql.contains("FROM")) {
			throw new BusinessException("", "", "", "SQLにDISTINCTとFROMが使用されていません。");
		}
		String[] split1 = sql.split("DISTINCT");
		String sp1 = split1[1].trim();
		String[] split2 = sp1.split("FROM");
		String sp2 = split2[0].trim();

		final String METHOD_NAME = "executeSomethingDistinctSelect";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		// 実行SQL
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		List<String> getList = new ArrayList<String>();
		try {
			Statement statement = this.con.createStatement();
			// 実行
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				getList.add(rs.getString(sp2));
			}
			return getList;
			//logger.info(" sql select record count -> {} 件のレコードが見つかりました。" ,resultList.size());
		} catch (Exception e) {
//			logger.error(" sql select record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}
	}

	/**
	 * 更新する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @param sets 更新句
	 * @return 検索結果
	 * @throws Exception
	 */
	public int executeUpdate(String auth, String tableId, String where, String sets) throws Exception {
		final String METHOD_NAME = "executeUpdate";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("UPDATE ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		sb.append(" ");
		sb.append("SET ");
		sb.append(sets);
		if (where != null) {
			sb.append(" ");
			sb.append("WHERE ");
			sb.append(where);
		}
		sb.append(";");
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		int result = -1;
		try (PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());) {
			// 実行
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
//			logger.error(" sql update record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}

		//logger.info(" update count info : {} 件更新しました。" , result);
		return result;
	}

	/**
	 * insertを実行する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param sqlTableQuery sql文
	 * @param valuesList VALUES以降に設定するデータ群
	 * @return 成功:登録件数,重複エラー:-4,何か例外発生;-9
	 * @throws Exception
	 */
	public int executeInsert(String auth, String tableId, String sqlTableQuery, String[] valuesList) throws Exception {
		final String METHOD_NAME = "executeInsert";

		// SELECT文で登録されている連番を取得し,+1する
		int seq = 1;
		StringBuilder sbSelect = new StringBuilder("SELECT COUNT(*) AS count FROM ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sbSelect.append(this.tableName);
		sbSelect.append(";");
		//logger.info(" 実行SQL -> {} " ,sbSelect.toString());
		try (Statement statement = this.con.createStatement();) {
			ResultSet rSet = statement.executeQuery(sbSelect.toString());
			while (rSet.next()) {
				seq = rSet.getInt("count");
				break;
			}
		} catch (Exception e) {
//			logger.error(" sql select seq error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		}

		int result = -1;
		// ArrayList型に変更
		ArrayList<String> valuesArrayList = new ArrayList<String>();
		// デフォルト日時追加
		List<String> list = UniairColumnMapUtil.getDefaultValueMap();
		Collections.addAll(valuesArrayList, valuesList);
		valuesArrayList.addAll(list);
		StringBuilder sb = new StringBuilder("INSERT INTO ");

		// テーブル名(列名,列名,...)を連結
		sb.append(sqlTableQuery);
		// VALUES
		sb.append("VALUES (");
		for (int i = 0; i <= valuesArrayList.size(); i++) {
			sb.append(" ?");
			if (i == valuesArrayList.size()) {
				break;
			}
			if (sb.toString().length() > 0) {
				sb.append(",");
			}
		}
		sb.append(");");
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		try (PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());) {
			preparedStatement.setInt(1, seq + 1);
			for (int j = 2; j <= valuesArrayList.size() + 1; j++) {
				preparedStatement.setString(j, valuesArrayList.get(j - 2));
			}
			// 実行
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
//			logger.error(" sql insert record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		}
		return result;
	}

	/**
	 * 削除する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @return 検索結果
	 * @throws Exception
	 */
	public int executeDelete(String auth, String tableId, String where) throws Exception {
		final String METHOD_NAME = "executeDelete";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("DELETE FROM ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		sb.append(" ");
		if (where != null) {
			sb.append("WHERE ");
			sb.append(where);
		}
		sb.append(";");
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		int result = -1;
		try (PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());) {
			// 実行
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
//			logger.error(" sql update record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}

		//logger.info(" update count info : {} 件更新しました。" , result);
		return result;
	}

	/**
	 * 削除する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param ai AI
	 * @return 検索結果
	 * @throws Exception
	 */
	public void executeResetAutoIncrement(String auth, String tableId, String ai) throws Exception {
		final String METHOD_NAME = "executeResetAutoIncrement";

		JdbcConnection cons = new JdbcConnection();
		cons.setAutoCommitCon(UniairConst.DB);
		this.con = cons.getCon();

		StringBuilder sb = new StringBuilder("SET @new_id = 0;");
		try (PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());) {
			// 実行
			preparedStatement.executeUpdate();
		} catch (Exception e) {
//			logger.error(" sql update record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		}

		sb = new StringBuilder("UPDATE ");
		this.tableName = UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId);
		sb.append(this.tableName);
		sb.append(" ");
		sb.append("SET ");
		sb.append(ai);
		sb.append(" = (@new_id := @new_id + 1);");
		//logger.info(" 実行SQL -> {} " ,sb.toString());
		try (PreparedStatement preparedStatement = this.con.prepareStatement(sb.toString());) {
			// 実行
			preparedStatement.executeUpdate();
		} catch (Exception e) {
//			logger.error(" sql update record error -> project_name : {}, class_name : {},"
//					+ " method_name : {}, cause : {} " ,
//					PROJECT_NAME, CLASS_NAME, METHOD_NAME,
//					e);
			throw e;
		} finally {
			this.con.close();
		}
		System.out.println("Auto Increment 再設定しました: " + sb.toString());
	}
}
