package dev.application.common.logic;

import java.util.List;

import dev.application.common.util.DateUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.ExistsUpdCsvInfo;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.db.UpdateWrapper;

/**
 * 追加で反映されたCSVデータ内について特定のマスタのデータを更新するロジック
 * @author shiraishitoshio
 *
 */
public class UpdateMasterDataLogic {

	/**
	 * 特定のマスタからデータを更新する。更新対象は以下
	 * 分類モードマスタ(BM_M020)
	 * <p>
	 * BM_M020...
	 * </p>
	 * @throws Exception
	 */
	public void execute(String file_number) throws Exception {
		List<String> select20List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M020);
		String[] sel20List = new String[select20List.size()];
		for (int i = 0; i < select20List.size(); i++) {
			sel20List[i] = select20List.get(i);
		}

		SqlMainLogic select = new SqlMainLogic();
		List<List<String>> selectResultList = null;
		try {
			String searchWhere = "remarks LIKE '%," + file_number + ",%' OR "
					+ "remarks LIKE '%," + file_number + "' OR "
					+ "remarks LIKE '%" + file_number + ",%'";
			selectResultList = select.executeSelect(null, UniairConst.BM_M020, sel20List,
					searchWhere, null, "1");
		} catch (Exception e) {
			System.err.println("classify_result_detail_deta select err searchData: " + e);
		}

		// id,国,リーグ,分類モード,件数,備考を取得
		String id = selectResultList.get(0).get(0);
		String country = selectResultList.get(0).get(1);
		String league = selectResultList.get(0).get(2);
		String classify_mode = selectResultList.get(0).get(3);
		String count = selectResultList.get(0).get(4);
		String remarks = selectResultList.get(0).get(5);
		// idをキーに件数を-1, remarksの該当CSV番号を削除して更新する
		String remarks_tmp = remarks.replace(file_number, "");
		if (remarks_tmp.contains(",,")) {
			remarks = remarks_tmp.replace(",,", ",");
		} else if (remarks_tmp.startsWith(",")) {
			remarks = remarks_tmp.substring(1);
		} else if (remarks_tmp.endsWith(",")) {
			remarks = remarks_tmp.substring(0, remarks_tmp.length() - 1);
		}
		String af_count = String.valueOf(Integer.parseInt(count) - 1);

		UpdateWrapper updateWrapper = new UpdateWrapper();
		StringBuilder sqlBuilder = new StringBuilder();
		String upWhere = "id = '" + id + "'";
		sqlBuilder.append(" count = '" + af_count + "' ,");
		sqlBuilder.append(" remarks = '" + remarks + "' ,");
		sqlBuilder.append(" update_time = '" + DateUtil.getSysDate() + "'");
		// 決定した判定結果に更新
		updateWrapper.updateExecute(UniairConst.BM_M020, upWhere,
				sqlBuilder.toString());

		// upd_csv_infoに登録
		try {
			ExistsUpdCsvInfo.insert(country, league, UniairConst.BM_M020, classify_mode);
		} catch (Exception e) {
			System.err.println("ExistsUpdCsvInfo err: " + e);
		}
	}

}
