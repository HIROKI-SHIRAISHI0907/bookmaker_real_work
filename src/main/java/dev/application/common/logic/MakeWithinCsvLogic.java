package dev.application.common.logic;

import java.util.List;

import dev.application.common.file.MakeAnythingFile;
import dev.application.common.util.ContainsCountryLeagueUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.BookDataSelectWrapper;
import dev.application.db.ExistsUpdCsvInfo;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;

/**
 * within_data_Xminutesロジック(within_data_20minutes_away_all_league.txtなど試合時間範囲,特徴量,閾値をキーに該当数を導出する)
 * @author shiraishitoshio
 *
 */
public class MakeWithinCsvLogic {

	/**
	 * 実行ロジック
	 * @param updCsvFlg CSV更新フラグ
	 */
	public void makeLogic(boolean updCsvFlg) {

		String[] selectList = new String[2];
		selectList[0] = "country";
		selectList[1] = "league";

		MakeAnythingFile makeAnythingFile = new MakeAnythingFile();

		// レコード件数を取得する
		BookDataSelectWrapper selectWrapper = new BookDataSelectWrapper();
		int cnt = -1;
		try {
			cnt = selectWrapper.executeCountSelect(UniairConst.BM_M006, null);
		} catch (Exception e) {
			return;
		}

		SqlMainLogic select = new SqlMainLogic();
		for (int chk = 0; chk <= 8; chk++) {
			List<String> values = UniairColumnMapUtil.getWithInTableIdMap(chk);
			String tableId = values.get(0);
			String fileoption = values.get(1);

			for (int id = 1; id <= cnt; id++) {
				List<List<String>> selectResultList = null;
				String where = "id = '" + id + "'";
				try {
					selectResultList = select.executeSelect(null, UniairConst.BM_M006, selectList, where, null, "1");
				} catch (Exception e) {
					return;
				}

				String country = selectResultList.get(0).get(0);
				String category = selectResultList.get(0).get(1);

				if (!ContainsCountryLeagueUtil.containsCountryLeague(country, category)) {
					continue;
				}

				// 更新CSVテーブルに存在したものは更新対象
				if (updCsvFlg) {
					List<List<String>> selectsList = ExistsUpdCsvInfo.chk(country, category,
							tableId, null);
					if (selectsList.isEmpty()) {
						continue;
					}
				}

				System.out.println("MakeWithinCsvLogic country: " + country + ", category: " + category +
						", chk: " + chk + ", id: " + id);

				// searchを一括で更新
				List<String> selectSubList = UniairColumnMapUtil.getKeyMap(tableId);
				String[] selList = new String[selectSubList.size() - 1];
				for (int i = 1; i < selectSubList.size(); i++) {
					selList[i - 1] = selectSubList.get(i);
				}

				List<List<String>> selectResultSubList = null;
				String subWhere = null;
				String sort = "";
				try {
					if (chk <= 4) {
						subWhere = "country = '" + country + "' and "
								+ "category = '" + category + "'";
						sort += "country, category, ";
					}
					sort += "time_range, feature, CAST(REPLACE(threshold, '%', '') AS DECIMAL(10,2)) ASC";
					selectResultSubList = select.executeSelect(null, tableId, selList, subWhere, sort, null);
				} catch (Exception e) {
					return;
				}

				if (!selectResultSubList.isEmpty()) {
					String filename = (chk <= 4) ? country + "-" + category + "_" + fileoption : fileoption;
					makeAnythingFile.execute(tableId, ".txt", filename, selectResultSubList);
				}

			}
		}
	}

}
