package dev.application.db;

/**
 * テーブル作成アプリケーション
 * @author shiraishitoshio
 *
 */
public class CreateTable {

	public static void main(String[] args) throws Exception {
    	SqlMainLogic mainLogic = new SqlMainLogic();
        // 実行
        String tableId = UniairConst.BM_M002;
        mainLogic.executeCreate(null, tableId);
        tableId = UniairConst.BM_M003;
        mainLogic.executeCreate(null, tableId);
        tableId = UniairConst.BM_M004;
        mainLogic.executeCreate(null, tableId);
	}
}
