package dev.application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * jdbc経由でDB情報を取得するクラス
 * @author shiraishitoshio
 *
 */
public class JdbcConnection {

	/**
	 * ?のサイズ
	 */
	public int valSize = 0;
	/**
	 * コネクション情報
	 */
	public Connection con;
	/**
	 * テーブル名
	 */
	public String tableName;

	/**
	 * コネクション設定(自動commit)
	 * @param db DB名
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setNoneAutoCommitCon(String db) throws SQLException {
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost/" + db, "root", "sonic3717");
			this.con.setAutoCommit(false);
		} catch (SQLException e) {
			if (this.con != null) {
				this.con.close();
			}
			throw e;
		}
	}

	/**
	 * コネクション設定(commitは手動で行う)
	 * @param db DB名
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setAutoCommitCon(String db) throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost/" + db, "root", "sonic3717");
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			if (this.con != null) {
				this.con.close();
			}
			throw e;
		}
	}


	/**
	 * コネクションを返却する
	 */
	public Connection getCon() {
		return this.con;
	}

}
