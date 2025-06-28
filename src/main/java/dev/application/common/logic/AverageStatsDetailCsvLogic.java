package dev.application.common.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.application.common.file.MakeCsv;
import dev.application.common.util.ContainsCountryLeagueUtil;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.BookDataSelectWrapper;
import dev.application.db.ExistsUpdCsvInfo;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.AverageStatisticsTeamDetailEntity;

/**
 * 各特徴量平均データ順位版整理CSV作成ロジック
 * @author shiraishitoshio
 *
 */
public class AverageStatsDetailCsvLogic {

	/**
	 * ALL
	 */
	private static final String SITUATION_ALL = "ALL";

	/**
	 * GAME
	 */
	private static final String SITUATION_GAME = "GAME";

	/**
	 * HALF1st
	 */
	private static final String SITUATION_HALF1st = "HALF1st";

	/**
	 * HALF2nd
	 */
	private static final String SITUATION_HALF2nd = "HALF2nd";

	/**
	 * CSV作成パス
	 */
	private static final String MAKE_FILE_PATH = "/Users/shiraishitoshio/bookmaker/average_stats/";

	/**
	 * 実行
	 */
	public void execute() {

		// upd_csv_infoにデータがあるか
		boolean updInfoFlg = ExistsUpdCsvInfo.exist();

		// situationリストを作成
		String[] situationList = new String[4];
		situationList[0] = SITUATION_ALL;
		situationList[1] = SITUATION_GAME;
		situationList[2] = "'" + SITUATION_HALF1st + "', '" + SITUATION_HALF2nd + "'";
		situationList[3] = "'0〜10', '10〜20', '20〜30', '30〜40', '40〜50', '50〜60', '60〜70', '70〜80', '80〜90'";

		// レコード件数を取得する
		BookDataSelectWrapper selectWrapper = new BookDataSelectWrapper();
		int cnt = -1;
		try {
			cnt = selectWrapper.executeCountSelect(UniairConst.BM_M006, null);
		} catch (Exception e) {
			return;
		}

		List<String> select6List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M006);
		String[] sel6List = new String[select6List.size()];
		for (int i = 0; i < select6List.size(); i++) {
			sel6List[i] = select6List.get(i);
		}

		for (int id = 1; id <= cnt; id++) {
			String searchWhere = "id = '" + id + "'";

			List<List<String>> selectResultList = null;
			SqlMainLogic select = new SqlMainLogic();
			try {
				selectResultList = select.executeSelect(null, UniairConst.BM_M006, sel6List,
						searchWhere, null, "1");
			} catch (Exception e) {
				System.err.println("err searchData: " + e);
			}

			String country = selectResultList.get(0).get(1);
			String category = selectResultList.get(0).get(2);

			// 不要国,カテゴリか
			if (!ContainsCountryLeagueUtil.containsCountryLeague(country, category)) {
				continue;
			}

			// CSV更新必要なもののみ実行
			if (updInfoFlg) {
				List<List<String>> selectsList = ExistsUpdCsvInfo.chk(country, category,
						UniairConst.BM_M028, null);
				if (selectsList.isEmpty()) {
					continue;
				}
			}

			String allWhere = "country = '" + country + "' and league = '" + category + "'";

			// situation, haを調べる
			for (String situation : situationList) {
				if (SITUATION_ALL.equals(situation) || SITUATION_GAME.equals(situation)) {
					allWhere += (" and situation = '" + situation + "'");
				} else {
					allWhere += (" and situation IN (" + situation + ")");
				}

				// situation:ALL, 同一team, 特徴量全て
				if (SITUATION_ALL.equals(situation)) {
					commonMkCsv(country, category, situation, allWhere, null);
					// situation:GAME, 同一team, ha, 特徴量全て
				} else if (SITUATION_GAME.equals(situation)) {
					commonMkCsv(country, category, situation, allWhere, null);
					// situation:HALF1st,HALF2nd全て, 同一team, 特徴量単位
					// situation:0〜10-80〜90全て, 同一team, 特徴量単位
				} else {
					String csv_sub_name = "";
					if (situation.contains(SITUATION_HALF1st)) {
						csv_sub_name = "HALF";
					} else {
						csv_sub_name = "TIME";
					}
					commonMkCsv(country, category, situation, allWhere, csv_sub_name);
				}

				if (SITUATION_ALL.equals(situation) || SITUATION_GAME.equals(situation)) {
					allWhere = allWhere.replace(" and situation = '" + situation + "'", "");
				} else {
					allWhere = allWhere.replace(" and situation IN (" + situation + ")", "");
				}

			}

		}

	}

	/**
	 * CSV作成ロジック
	 * @param country 国
	 * @param league リーグ
	 * @param situation 状況
	 * @param allWhere 条件句
	 * @param csv_sub_name サブネーム HALF,TIME区別用
	 */
	private void commonMkCsv(String country, String league, String situation,
			String allWhere, String csv_sub_name) {
		List<String> select28List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M028);
		String[] sel28List = new String[select28List.size()];
		for (int i = 0; i < select28List.size(); i++) {
			sel28List[i] = select28List.get(i);
		}

		SqlMainLogic select = new SqlMainLogic();

		// loop回数
		int loopCount = 1;

		List<String> teamList = new ArrayList<String>();
		if (!SITUATION_ALL.equals(situation)) {
			String sql = "SELECT DISTINCT team FROM "
					+ UniairColumnMapUtil.getTableIdToTableName(UniairConst.BM_M028)
					+ " WHERE country = '" + country + "' and league = '" + league + "'";
			try {
				teamList = select.executeSomethingDistinctSelect(sql);
			} catch (Exception e) {
				System.err.println("DISTINCT err: " + e);
			}
			// チーム数分
			loopCount = teamList.size();
		}

		// 作成CSV名称
		String csv_name = MAKE_FILE_PATH + country + "-" + league;
		csv_name += ("/" + country + "-" + league);
		//FolderOperation.makeFolder(csv_name);

		if (SITUATION_ALL.equals(situation)) {
			csv_name += "-ALL";
		}

		// team名,opposite_team名をキーにする

		int loop = 1;
		do {
			// チーム,相手チームをキーとした条件句を追加する
			String teamWhere = "";
			if (!SITUATION_ALL.equals(situation)) {
				teamWhere = teamList.get(loop - 1);
				allWhere += (" and (team = '" + teamWhere + "' and ha = 'H' or opposite_team = '"
						+ teamWhere + "' and ha = 'A')");
			}

			List<List<String>> selectResultList = null;
			try {
				selectResultList = select.executeSelect(null, UniairConst.BM_M028, sel28List,
						allWhere, null, null);
			} catch (Exception e) {
				System.err.println("err country: " + country + ", league: " + league +
						", genre: " + situation);
			}

			// チーム,相手チームをキーとした条件句を削除する
			if (!SITUATION_ALL.equals(situation)) {
				teamWhere = teamList.get(loop - 1);
				allWhere = allWhere.replace(" and (team = '" + teamWhere + "' and ha = 'H' "
						+ "or opposite_team = '" + teamWhere + "' and ha = 'A')", "");
			}

			Field[] fields = new AverageStatisticsTeamDetailEntity().getClass().getDeclaredFields();

			// ALLの場合はteam単独のキー, ALL以外はteamとoppositeTeamを連結したキーにする
			Map<String, List<String>> keyMap = new HashMap<String, List<String>>();
			for (List<String> resultList : selectResultList) {
				String situationKey = resultList.get(1);
				String team = resultList.get(2);
				String oppoTeam = resultList.get(3);
				String ha = resultList.get(4);

				List<String> resultAlterList = resultList.subList(7, resultList.size());

				// SITUATION_ALLまたはSITUATION_GAMEの場合はそのまま格納
				if (SITUATION_ALL.equals(situation) || SITUATION_GAME.equals(situation)) {
					String baseKey = team;
					if (oppoTeam != null && !oppoTeam.isEmpty()) {
						baseKey += ("-" + oppoTeam);
					}
					// ホーム,アウェーキーを連結する
					if (ha != null && !ha.isEmpty()) {
						baseKey += ("-" + ha);
					}
					keyMap.computeIfAbsent(baseKey, k -> new ArrayList<>()).addAll(resultAlterList);
				} else {
					// 特徴量ごとに分解して格納
					for (int i = 0; i < resultAlterList.size(); i++) {
						String baseKey = team;
						if (oppoTeam != null && !oppoTeam.isEmpty()) {
							baseKey += ("-" + oppoTeam);
						}
						// ホーム,アウェーキーを連結する
						if (ha != null && !ha.isEmpty()) {
							baseKey += ("-" + ha);
						}
						baseKey += ("-" + situationKey);
						if (i < fields.length) {
							String featureName = ExecuteMainUtil.convertToSnakeCase(fields[i + 7].getName());
							baseKey += ("-" + featureName);
						}

						String featureValue = resultAlterList.get(i);
						keyMap.computeIfAbsent(baseKey, k -> new ArrayList<>()).add(featureValue);
					}
				}
			}

			// 国,リーグ,特徴量ごとにCSV分割したもの, 国,リーグごとにCSV分割したものを作成
			List<String> headerALLList = new ArrayList<String>();
			headerALLList = setALLHeader(headerALLList);
			List<String> headerGAMEList = new ArrayList<String>();
			headerGAMEList = setGAMEHeader(headerGAMEList);
			List<String> headerOtherList = new ArrayList<String>();
			String key = (situation.contains("〜")) ? "TIME" : "HALF";
			headerOtherList = setOtherHeader(headerOtherList, key);

			List<List<String>> bodyALLGAMEList = new ArrayList<List<String>>();
			// ハイフンがある場合teamが同じ名称同士で再度データリストを作成する
			Map<String, HashMap<String, List<String>>> keySubMap = new HashMap<String, HashMap<String, List<String>>>();
			// team, 特徴量リスト(keyMap)
			for (Map.Entry<String, List<String>> map : keyMap.entrySet()) {
				String teams = map.getKey();

				List<String> dataList = map.getValue();
				// キーチーム-相手チーム-H, 相手チーム-キーチーム-A,
				// キーチーム-相手チーム-H-situ-feature, 相手チーム-キーチーム-A-situ-featureの形式
				if (teams.contains("-")) {
					String[] teamSp = teams.split("-");
					String opTeam = "";
					String ha = "";
					String situ = "";
					String feature = "";
					if (teamSp.length == 3) {
						ha = teamSp[2];
					}
					if (teamSp.length == 4) {
						ha = teamSp[2];
						feature = teamSp[3];
					}
					if (teamSp.length == 5) {
						ha = teamSp[2];
						situ = teamSp[3];
						feature = teamSp[4];
					}
					// haがH,Aいずれかによってキーチーム名を決める
					if ("H".equals(ha)) {
						teams = teamSp[0];
						opTeam = teamSp[1];
					} else if ("A".equals(ha)) {
						teams = teamSp[1];
						opTeam = teamSp[0];
					}

					// 相手チームに自チームがホームかアウェーの情報を付与する
					// 相手チーム-H, 相手チーム-A,
					if (!ha.isEmpty()) {
						opTeam += ("-" + ha);
					}
					// 相手チーム-状況
					if (!situ.isEmpty()) {
						opTeam += ("-" + situ);
					}
					// 相手チーム-特徴量
					if (!feature.isEmpty()) {
						opTeam += ("-" + feature);
					}

					// 既に存在する keySubSubMap を取得 or 新規作成
					HashMap<String, List<String>> keySubSubMap = keySubMap.computeIfAbsent(teams, k -> new HashMap<>());
					// 存在すれば追加,しなければ新規追加
					keySubSubMap.computeIfAbsent(opTeam, k -> new ArrayList<>()).addAll(dataList);
					keySubMap.put(teams, keySubSubMap);
				}

				// team全体リスト(ALL)(タイトル: 国-リーグ-ALL, 縦データ:チーム名, 横データ:全特徴量, 中身:平均値と順位)
				if (SITUATION_ALL.equals(situation)) {
					bodyALLGAMEList = setALLbody(bodyALLGAMEList, teams, dataList);
				}
			}

			// team, 各特徴量リスト(GAME)(タイトル: 国-リーグ-team-GAME-ha, 縦データ:相手チーム名, 横データ:全特徴量, 中身:平均値と順位)
			// team, 各特徴量リスト(ALL, GAME以外)(タイトル: 国-リーグ-ha-team-特徴量, 縦データ:相手チーム名, 横データ:HALF1stor2nd or 0〜10-80〜90, 中身:平均値と順位)
			// team, oppositeteam, 各特徴量リスト
			// csv名称とデータリストを登録する
			Map<String, List<List<String>>> csvMap = new HashMap<String, List<List<String>>>();
			if (!keySubMap.isEmpty()) {
				for (Map.Entry<String, HashMap<String, List<String>>> maps : keySubMap.entrySet()) {
					// キーチームのみの想定
					String keys = maps.getKey();
					csv_name += ("-" + keys);

					Map<String, List<String>> nextMap = maps.getValue();
					for (Map.Entry<String, List<String>> subMaps : nextMap.entrySet()) {
						// 集計リストを保存する
						List<String> scoreSubList = null;

						String oppoInfo = subMaps.getKey();
						// 特徴量キーありの場合,すでに特徴量単位でデータリスト分割済みのためCSV名称に特徴量を入れる
						String ha = "";
						String situ = "";
						String feature = "";
						if (oppoInfo.contains("-")) {
							String[] teamSp = oppoInfo.split("-");
							oppoInfo = teamSp[0];
							if (teamSp.length == 2) {
								ha = teamSp[1];
							}
							if (teamSp.length == 3) {
								ha = teamSp[1];
								situ = teamSp[2];
							}
							if (teamSp.length == 4) {
								ha = teamSp[1];
								situ = teamSp[2];
								feature = teamSp[3];
							}
						}
						List<String> dataList = subMaps.getValue();
						if (SITUATION_GAME.equals(situation)) {
							bodyALLGAMEList = setGAMEbody(bodyALLGAMEList, oppoInfo, ha, dataList);
						} else if (!SITUATION_ALL.equals(situation) && !SITUATION_GAME.equals(situation)) {
							// 存在すれば追加,しなければ新規追加
							List<List<String>> bodyOtherList = new ArrayList<List<String>>();
							if (csvMap.containsKey(feature)) {
								bodyOtherList = csvMap.get(feature);
							}

							// 集計リスト
							if (!SITUATION_HALF1st.equals(situation) && !SITUATION_HALF2nd.equals(situation)) {
								scoreSubList = getCollectRangeScore(country, league,
										situation, ha, keys, oppoInfo);
							}

							bodyOtherList = setHALFTIMEbody(bodyOtherList, scoreSubList,
									oppoInfo, situ, ha, dataList);
							csvMap.put(feature, bodyOtherList);
						}

						// 名称リセット
						if (!SITUATION_ALL.equals(situation) && !SITUATION_GAME.equals(situation)) {
							csv_name = csv_name.replace("-" + situ, "");
						}
					}
				}
			}

			MakeCsv csv = new MakeCsv();
			// ALLデータのCSV作成
			if (SITUATION_ALL.equals(situation)) {
				String csvs = csv_name + ".csv";
				csv.execute(csvs, null, headerALLList, bodyALLGAMEList);
				System.out.println("作成しました。: (" + country + ", " + league + ", "
						+ situation + ", " + teamWhere + ")");
				// GAMEデータのCSV作成
			} else if (SITUATION_GAME.equals(situation)) {
				String csvs = csv_name + "-GAME.csv";
				csv.execute(csvs, null, headerGAMEList, bodyALLGAMEList);
				System.out.println("作成しました。: (" + country + ", " + league + ", "
						+ situation + ", " + teamWhere + ")");
				// それ以外のCSV作成
			} else if (!csvMap.isEmpty()) {
				String baseCsvName = csv_name;
				for (Map.Entry<String, List<List<String>>> subMaps : csvMap.entrySet()) {
					String feature = subMaps.getKey();
					List<List<String>> bodyOtherList = subMaps.getValue();
					String csv_names = baseCsvName + "-" + feature;
					if (!SITUATION_ALL.equals(situation) && !SITUATION_GAME.equals(situation)) {
						csv_names += ("-" + csv_sub_name);
					}
					csv_names += ".csv";
					csv.execute(csv_names, null, headerOtherList, bodyOtherList);
					System.out.println("作成しました。: (" + country + ", " + league + ", "
							+ situation + ", " + teamWhere + ", " + feature + ")");
				}
			}

			// リセット
			csv_name = MAKE_FILE_PATH + country + "-" + league;
			csv_name += ("/" + country + "-" + league);
			if (SITUATION_ALL.equals(situation)) {
				csv_name += "-ALL";
			}

			loop++;
		} while (loop <= loopCount);
	}

	/**
	 * ヘッダー設定(ALL用)
	 * @param headerList
	 * @return
	 */
	private List<String> setALLHeader(List<String> headerList) {
		// チーム名/特徴量を設定
		headerList.add("チーム名/特徴量");
		Field[] fields = new AverageStatisticsTeamDetailEntity().getClass().getDeclaredFields();
		for (int feature_ind = 7; feature_ind < fields.length; feature_ind++) {
			Field field = fields[feature_ind];
			field.setAccessible(true);
			String feature_name = ExecuteMainUtil.convertToSnakeCase(field.getName());
			headerList.add(feature_name);
		}
		return headerList;
	}

	/**
	 * ヘッダー設定(GAME用)
	 * @param headerList
	 * @return
	 */
	private List<String> setGAMEHeader(List<String> headerList) {
		// チーム名/特徴量を設定
		headerList.add("チーム名/特徴量");
		headerList.add("ホームアウェー");
		Field[] fields = new AverageStatisticsTeamDetailEntity().getClass().getDeclaredFields();
		for (int feature_ind = 7; feature_ind < fields.length; feature_ind++) {
			Field field = fields[feature_ind];
			field.setAccessible(true);
			String feature_name = ExecuteMainUtil.convertToSnakeCase(field.getName());
			headerList.add(feature_name);
		}
		return headerList;
	}

	/**
	 * ヘッダー設定(ALL,GAME以外用)
	 * @param headerList
	 * @param situation 状況
	 * @return
	 */
	private List<String> setOtherHeader(List<String> headerList, String situation) {
		// チーム名/特徴量を設定
		headerList.add("チーム名/特徴量");
		headerList.add("ホームアウェー");
		if ("HALF".equals(situation)) {
			headerList.add(SITUATION_HALF1st);
			headerList.add(SITUATION_HALF2nd);
		} else if ("TIME".equals(situation)) {
			headerList.add("0〜10");
			headerList.add("10〜20");
			headerList.add("20〜30");
			headerList.add("30〜40");
			headerList.add("40〜50");
			headerList.add("50〜60");
			headerList.add("60〜70");
			headerList.add("70〜80");
			headerList.add("80〜90");
		}
		return headerList;
	}

	/**
	 * データ設定(ALL)
	 * @param bodyList
	 * @param team 自チーム
	 * @param dataList
	 * @return
	 */
	private List<List<String>> setALLbody(List<List<String>> bodyList, String team,
			List<String> dataList) {
		// チーム名を設定
		List<String> teamDataList = new ArrayList<String>();
		teamDataList.add(team);
		for (String data : dataList) {
			// 平均値と順位を取得する
			String[] dataSp = data.split(",");
			String connectData = dataSp[2] + "-" + dataSp[10];
			teamDataList.add(connectData);
		}
		bodyList.add(teamDataList);
		return bodyList;
	}

	/**
	 * データ設定(GAME用)
	 * @param bodyList
	 * @param team 自チーム
	 * @param ha ホームアウェー
	 * @param dataList
	 * @return
	 */
	private List<List<String>> setGAMEbody(List<List<String>> bodyList, String team, String ha,
			List<String> dataList) {
		// チーム名を設定
		List<String> teamDataList = new ArrayList<String>();
		teamDataList.add(team);
		teamDataList.add(ha);
		for (String data : dataList) {
			// 平均値と順位を取得する
			String[] dataSp = data.split(",");
			String connectData = dataSp[2] + "-" + dataSp[10];
			teamDataList.add(connectData);
		}
		bodyList.add(teamDataList);
		return bodyList;
	}

	/**
	 * データ設定(HALF,TIME以外用)
	 * @param bodyList
	 * @param scoreSubList 集計リスト
	 * @param keyteam 相手チーム
	 * @param oppoteam 相手チーム
	 * @param situation 状況
	 * @param ha ホームアウェー
	 * @param dataList
	 * @return
	 */
	private List<List<String>> setHALFTIMEbody(List<List<String>> bodyList, List<String> scoreSubList,
			String oppoteam, String situation,
			String ha, List<String> dataList) {

		// リストは1件の想定
		String data = dataList.get(0);
		String[] dataSp = data.split(",");
		String connectData = dataSp[2] + "-" + dataSp[10];
		// 得点分布付与
		if (!SITUATION_HALF1st.equals(situation) && !SITUATION_HALF2nd.equals(situation)
				&& scoreSubList != null) {
			if ("0〜10".equals(situation)) {
				connectData += " (" + scoreSubList.get(0) + ") ";
			} else if ("10〜20".equals(situation)) {
				connectData += " (" + scoreSubList.get(1) + ") ";
			} else if ("20〜30".equals(situation)) {
				connectData += " (" + scoreSubList.get(2) + ") ";
			} else if ("30〜40".equals(situation)) {
				connectData += " (" + scoreSubList.get(3) + ") ";
			} else if ("40〜50".equals(situation)) {
				connectData += " (" + String.valueOf(Integer.parseInt(scoreSubList.get(4))
						+ Integer.parseInt(scoreSubList.get(5))) + ") ";
			} else if ("50〜60".equals(situation)) {
				connectData += " (" + scoreSubList.get(6) + ") ";
			} else if ("60〜70".equals(situation)) {
				connectData += " (" + scoreSubList.get(7) + ") ";
			} else if ("70〜80".equals(situation)) {
				connectData += " (" + scoreSubList.get(8) + ") ";
			} else if ("80〜90".equals(situation)) {
				connectData += " (" + scoreSubList.get(9) + ") ";
			}
		}

		// データが存在するか
		boolean dataKey = false;
		int index = -1;
		int chkInd = 0;
		for (List<String> body : bodyList) {
			if (oppoteam.equals(body.get(0)) && ha.equals(body.get(1))) {
				dataKey = true;
				index = chkInd;
				break;
			}
			chkInd++;
		}

		// チーム名を設定
		List<String> teamDataList = new ArrayList<String>();
		if (dataKey) {
			teamDataList = bodyList.get(index);
			// HALFデータ
			if (SITUATION_HALF1st.equals(situation) || SITUATION_HALF2nd.equals(situation)) {
				if (SITUATION_HALF1st.equals(situation)) {
					teamDataList.set(2, connectData);
				} else if (SITUATION_HALF2nd.equals(situation)) {
					teamDataList.set(3, connectData);
				}
				// TIME
			} else {
				if ("0〜10".equals(situation)) {
					teamDataList.set(2, connectData);
				} else if ("10〜20".equals(situation)) {
					teamDataList.set(3, connectData);
				} else if ("20〜30".equals(situation)) {
					teamDataList.set(4, connectData);
				} else if ("30〜40".equals(situation)) {
					teamDataList.set(5, connectData);
				} else if ("40〜50".equals(situation)) {
					teamDataList.set(6, connectData);
				} else if ("50〜60".equals(situation)) {
					teamDataList.set(7, connectData);
				} else if ("60〜70".equals(situation)) {
					teamDataList.set(8, connectData);
				} else if ("70〜80".equals(situation)) {
					teamDataList.set(9, connectData);
				} else if ("80〜90".equals(situation)) {
					teamDataList.set(10, connectData);
				}
			}
			bodyList.set(index, teamDataList);
		} else {
			teamDataList.add(oppoteam);
			teamDataList.add(ha);
			// HALFデータ
			if (SITUATION_HALF1st.equals(situation) || SITUATION_HALF2nd.equals(situation)) {
				teamDataList.add("");
				teamDataList.add("");
				if (SITUATION_HALF1st.equals(situation)) {
					teamDataList.set(2, connectData);
				} else if (SITUATION_HALF2nd.equals(situation)) {
					teamDataList.set(3, connectData);
				}
				// TIME
			} else {
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				teamDataList.add("");
				if ("0〜10".equals(situation)) {
					teamDataList.set(2, connectData);
				} else if ("10〜20".equals(situation)) {
					teamDataList.set(3, connectData);
				} else if ("20〜30".equals(situation)) {
					teamDataList.set(4, connectData);
				} else if ("30〜40".equals(situation)) {
					teamDataList.set(5, connectData);
				} else if ("40〜50".equals(situation)) {
					teamDataList.set(6, connectData);
				} else if ("50〜60".equals(situation)) {
					teamDataList.set(7, connectData);
				} else if ("60〜70".equals(situation)) {
					teamDataList.set(8, connectData);
				} else if ("70〜80".equals(situation)) {
					teamDataList.set(9, connectData);
				} else if ("80〜90".equals(situation)) {
					teamDataList.set(10, connectData);
				}
			}
			bodyList.add(teamDataList);
		}

		return bodyList;
	}

	/**
	 * スコア集計取得
	 * @param country 国
	 * @param league リーグ
	 * @param situation 状況
	 * @param ha homeAway
	 * @param keyteam キーチーム
	 * @param oppoteam 相手チーム
	 * @return
	 */
	private List<String> getCollectRangeScore(String country, String league,
			String situation, String ha, String keyteam, String oppoteam) {
		// その時間帯における得点があったかを付与する
		List<String> select29List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M029);
		String[] sel29List = new String[select29List.size()];
		for (int i = 0; i < select29List.size(); i++) {
			sel29List[i] = select29List.get(i);
		}

		String key_flg = ("H".equals(ha)) ? "T" : " ";
		String allWhere = "country = '" + country + "' and league = '" + league + "' and "
				+ "key_flg = '" + key_flg + "' and "
				+ "team = '" + keyteam + "' "
				+ "and opposite_team = '" + oppoteam + "'";

		SqlMainLogic select = new SqlMainLogic();
		List<List<String>> selectResultList = null;
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M029, sel29List,
					allWhere, null, null);
		} catch (Exception e) {
			System.err.println("err country: " + country + ", league: " + league +
					", genre: " + situation);
		}

		List<String> scoreSubList = null;
		if (selectResultList != null) {
			scoreSubList = selectResultList.get(0).subList(6, selectResultList.get(0).size());
		}
		return scoreSubList;
	}

}
