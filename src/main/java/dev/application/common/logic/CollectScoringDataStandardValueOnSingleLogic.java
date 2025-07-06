package dev.application.common.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dev.application.common.dto.CollectScoringOutputDTO;
import dev.application.common.exception.BusinessException;
import dev.application.common.exception.SystemException;
import dev.application.common.util.CollectScoringDataStandardValueUtil;
import dev.application.common.util.DateUtil;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.db.CsvRegisterImpl;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.db.UpdateWrapper;
import dev.application.entity.CollectScoringStandardDataEntity;

/**
 * TIMEデータに対して得点が生まれている時間帯ごとの特徴量における最低閾値集計ロジック
 * およびその分析ロジック（平均、標準偏差、クラスタリング、得点率）
 */
public class CollectScoringDataStandardValueOnSingleLogic {

	/**
	 * スレッドセーフ前提・suffix
	 */
	private String suffix_single = "";

	/**
	 * スレッドセーフ前提・suffix
	 */
	private String suffix1 = "";

	/**
	 * スレッドセーフ前提・suffix
	 */
	private String suffix2 = "";

	/**
	 * スレッドセーフ前提・suffix
	 */
	private String suffix3 = "";

	/**
	 * 実行メソッド
	 * @param csv_name
	 * @param mainTeamKey
	 * @param feature
	 * @param headerList
	 * @param bodyList
	 * @param csv_name2 (差分でない場合はnull)
	 * @param mainTeamKey2 (差分でない場合はnull)
	 * @param feature2 (差分でない場合はnull)
	 * @param headerList2 (差分でない場合は空リスト)
	 * @param bodyList2 (差分でない場合は空リスト)
	 */
	public void execute(String csv_name, String mainTeamKey, String feature,
			List<String> headerList, List<List<String>> bodyList,
			String csv_name2, String mainTeamKey2, String feature2,
			List<String> headerList2, List<List<String>> bodyList2) {
		//csv名分割
		String[] csv_data = csv_name.split("-");
		String country = csv_data[0];
		String league = csv_data[1];
		String team = csv_data[2];
		String stat = csv_data[3];
		String situ_data = csv_data[4];
		//csv名分割
		String country2 = "";
		String league2 = "";
		String team2 = "";
		String stat2 = "";
		if (csv_name2 != null) {
			String[] csv_data2 = csv_name2.split("-");
			country2 = csv_data2[0];
			league2 = csv_data2[1];
			team2 = csv_data2[2];
			stat2 = csv_data2[3];

			// 国,リーグ,特徴量が異なる場合はreturn(差分は絶対取れないため)
			if (!(country.equals(country2)) || !(league.equals(league2)) || !(stat.equals(stat2))) {
				System.out.println("国,リーグ,特徴量が一致しません。: (" + country + "," + league + "," + stat + "),"
						+ "(" + country2 + "," + league2 + "," + stat2 + ")");
				return;
			}
		}
		// 単純累積,時間累積差分
		String[] data_situation_key_list = new String[1];
		String[] data_situation_key_list2 = new String[3];
		String[] score_distribution_list = new String[2];
		if ("TIME".equals(situ_data)) {
			data_situation_key_list[0] = CollectScoringDataStandardValueUtil.ACCUMULATION;
			data_situation_key_list2[0] = CollectScoringDataStandardValueUtil.TIME_ACCUMULATION;
			data_situation_key_list2[1] = CollectScoringDataStandardValueUtil.TIME_DIFF;
			data_situation_key_list2[2] = CollectScoringDataStandardValueUtil.DIFF;
			score_distribution_list[0] = CollectScoringDataStandardValueUtil.GET_SCORE;
			score_distribution_list[1] = CollectScoringDataStandardValueUtil.NO_GET_SCORE;
		} else {
			System.err.println("file err: " + csv_name);
			return;
		}

		LinkedHashMap<String, LinkedHashMap<String, String>> keyMap = new LinkedHashMap<>();
		for (List<String> list : bodyList) {
			String teamName = list.get(0);
			String horA = list.get(1);
			String teamKey = teamName + "_" + horA;

			LinkedHashMap<String, String> valueMap = keyMap.computeIfAbsent(teamKey, k -> new LinkedHashMap<>());

			for (int i = 2; i < list.size(); i++) {
				String key = headerList.get(i);
				String value = list.get(i);

				if (!value.isBlank() && !value.endsWith(" (0)")) {
					String cleanedValue = value.substring(0, value.length() - 4);
					valueMap.put(key, cleanedValue);
				}
			}
		}

		Map<String, Map<String, List<Double>>> featureValuesByTimeAndHA = new HashMap<>();
		Map<String, List<Double>> flatFeatureValuesByTime = new HashMap<>();

		for (Map.Entry<String, LinkedHashMap<String, String>> entry : keyMap.entrySet()) {
			String teamKey = entry.getKey();
			String[] parts = teamKey.split("_");
			if (parts.length != 2)
				continue;
			String horA = parts[1];

			Map<String, String> valueMap = entry.getValue();
			for (String time : valueMap.keySet()) {
				String oppoFeature = valueMap.get(time);
				try {
					double numericFeature = Double.parseDouble(oppoFeature.split("-")[0]);

					featureValuesByTimeAndHA
							.computeIfAbsent(time, k -> new HashMap<>())
							.computeIfAbsent(horA, h -> new ArrayList<>())
							.add(numericFeature);

					flatFeatureValuesByTime
							.computeIfAbsent(time, k -> new ArrayList<>())
							.add(numericFeature);

				} catch (NumberFormatException e) {
					System.err.println("format err: " + e);
				}
			}
		}

		// 時間帯を開始時間で昇順にソート
		List<String> sortedTimes = new ArrayList<>(flatFeatureValuesByTime.keySet());
		Collections.sort(sortedTimes, new Comparator<String>() {
			@Override
			public int compare(String t1, String t2) {
				try {
					int start1 = Integer.parseInt(t1.split("〜")[0]);
					int start2 = Integer.parseInt(t2.split("〜")[0]);
					return Integer.compare(start1, start2);
				} catch (Exception e) {
					return t1.compareTo(t2);
				}
			}
		});

		System.out.println("===== 平均・標準偏差 (Home/Away別) =====");
		for (String time : sortedTimes) {
			Map<String, List<Double>> mapByHA = featureValuesByTimeAndHA.get(time);
			for (String horA : mapByHA.keySet()) {
				List<Double> values = mapByHA.get(horA);
				List<Double> filtered = new ArrayList<>();
				for (double v : values) {
					if (v != 0.0)
						filtered.add(v);
				}
				if (filtered.isEmpty())
					continue;

				double sum = 0.0;
				for (double v : filtered)
					sum += v;
				double avg = sum / filtered.size();

				double variance = 0.0;
				for (double v : filtered)
					variance += Math.pow(v - avg, 2);
				double stdDev = Math.sqrt(variance / filtered.size());

				System.out.printf("時間帯: %s (%s), 平均%s: %.3f, 標準偏差: %.3f, 件数: %d%n",
						time, horA, feature, avg, stdDev, filtered.size());
			}
		}

		// クラスタリング（平均で昇順）
		System.out.println("\n===== 時間帯クラスタリング（昇順） =====");
		class TimeStats {
			double avg;
			double stdDev;
			int count;
			String time;

			TimeStats(double avg, double stdDev, int count, String time) {
				this.avg = avg;
				this.stdDev = stdDev;
				this.count = count;
				this.time = time;
			}
		}

		List<TimeStats> timeStatsList = new ArrayList<>();
		for (String time : sortedTimes) {
			List<Double> vals = flatFeatureValuesByTime.get(time);
			List<Double> filtered = vals.stream().filter(v -> v != 0.0).collect(Collectors.toList());
			if (filtered.isEmpty())
				continue;

			double avg = filtered.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

			double variance = 0.0;
			for (double v : filtered) {
				variance += Math.pow(v - avg, 2);
			}
			double stdDev = Math.sqrt(variance / filtered.size());

			timeStatsList.add(new TimeStats(avg, stdDev, filtered.size(), time));
		}

		// 時間帯の開始時刻（例: "15〜30" → 15）で昇順ソート
		Collections.sort(timeStatsList, Comparator.comparingInt(ts -> {
			String[] parts = ts.time.split("〜");
			try {
				return Integer.parseInt(parts[0]);
			} catch (NumberFormatException e) {
				return Integer.MAX_VALUE; // ソート失敗時は後ろに
			}
		}));

		// 出力
		for (TimeStats stats : timeStatsList) {
			System.out.printf("時間帯: %s, 平均: %.3f, 標準偏差: %.3f, 件数: %d%n",
					stats.time, stats.avg, stats.stdDev, stats.count);
		}

		// 閾値範囲・刻み幅設定
		double thresholdStart = 0.2;
		double thresholdEnd = 1.5;
		double step = 0.1;

		List<Double> thresholds = new ArrayList<>();
		for (double t = thresholdStart; t <= thresholdEnd + 1e-6; t += step) {
			thresholds.add(Math.round(t * 1000.0) / 1000.0);
		}

		// 時間帯別：得点率分析（以上）
		System.out.println("\n===== 閾値ごとの得点率分析（以上・時間帯別） =====");
		for (String time : sortedTimes) {
			List<Double> values = flatFeatureValuesByTime.get(time);
			if (values == null || values.isEmpty())
				continue;

			List<Double> filtered = values.stream().filter(v -> v != 0.0).collect(Collectors.toList());
			if (filtered.isEmpty())
				continue;

			System.out.println("▼ 時間帯: " + time);
			for (double threshold : thresholds) {
				int total = 0, scored = 0;
				for (double v : filtered) {
					total++;
					if (v >= threshold)
						scored++;
				}
				double rate = (total == 0) ? 0.0 : (scored * 100.0 / total);
				System.out.printf("　閾値 %.2f 以上: %d / %d 得点率 %.2f%%%n", threshold, scored, total, rate);
			}
		}

		// 時間帯別：得点率分析（以下）
		System.out.println("\n===== 閾値ごとの得点率分析（以下・時間帯別） =====");
		for (String time : sortedTimes) {
			List<Double> values = flatFeatureValuesByTime.get(time);
			if (values == null || values.isEmpty())
				continue;

			List<Double> filtered = values.stream().filter(v -> v != 0.0).collect(Collectors.toList());
			if (filtered.isEmpty())
				continue;

			System.out.println("▼ 時間帯: " + time);
			for (double threshold : thresholds) {
				int total = 0, scored = 0;
				for (double v : filtered) {
					total++;
					if (v <= threshold)
						scored++;
				}
				double rate = (total == 0) ? 0.0 : (scored * 100.0 / total);
				System.out.printf("　閾値 %.2f 以下: %d / %d 得点率 %.2f%%%n", threshold, scored, total, rate);
			}
		}

		// こちらの調べ方をする。
		// 特定のチームの同一特徴量（HとAは別物として）の差分によって得点が入る率を調べる。ついでに累積値も関係あるのかを調べる
		// どの程度のデータ差分があれば得点になるのかを得たい。（時間帯に関係あるのかないのか、）
		// 時間帯に関係あるのであれは、もしかしたらそのチームはこの時間帯に点を取りにいくと決めているのかも。(メキシコとかそのイメージ)
		// 関係ないのであれば最も得点が入る差分値、累積値を調査する
		// 「TIME_DIFF」（時間帯差分）、「DIFF」（単純差分）、「ACCUMULATION」（累積データ）でデータを分ける
		// 「TIME_DIFF」（時間帯差分）...同一時間帯における主チームの得点無得点時における特徴量値差の
		// 「DIFF」（単純差分(データとしては参考にならないが,無得点時の参考値として使用できる)）...時間帯関係なく主チームの得点無得点時における特徴量値差の(得点なしの時,このチームはここまで差が開いていないと点が入らないのでは)
		// 「TIME_ACCUMULATION」（時間帯累積データ）...同一時間帯における主チームの得点無得点時における特徴量累積の(得点なしの時,このチームはここまで値が到達していないと点が入らないのでは)
		// 「ACCUMULATION」（単純累積データ(データとしては参考にならないが,無得点時の参考値として使用できる)）...時間帯関係なく主チームの得点無得点時における特徴量累積の(得点なしの時,このチームはここまで値が到達していないと点が入らないのでは)
		// 差分最小値,差分最大値,差分平均値,差分標準偏差(最小値,最大値は当然マイナスもありうる),差分マイナスの数,差分プラスの数,対象データ数
		// 差分最小値,差分最大値を示した時の対戦チーム名

		// data_situation_keyとtime_keyを設けており,以下の組み合わせがある
		// TIME_DIFF,0〜10から80〜90
		// DIFF,得点なし
		// TIME_ACCUMULATION,0〜10から80〜90
		// ACCUMULATION,得点なし

		//1. 日本-J1 リーグ-FC東京-shoot_in_stat-TIME等を読み込む。
		//2. 国,リーグ,situationキー,主チーム,スコア分布(得点あった場合の統計,得点なかった場合の統計),stat情報があるか
		//3. 2がなければ4.へ,2があるかつstat情報が新規なら更新して4.へ,2があるかつstat情報もあるならまず5.へ
		//4. 国,リーグ,situationキー,主チーム,スコア分布(得点あった場合の統計,得点なかった場合の統計),stat情報(CSV名から特定のカラムに登録)

		// 同一indexは同一時間帯
		//		double[] standardDiviationNoScoreFeature = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		//		double standardDiviationNoScoreMainFeature = 0.0;
		// 得点があるデータか?(もちろん同データの別CSVでは得点がある可能性もある)
		// home awayで分類
		Map<String, List<Integer>> haToIndices = new HashMap<>();
		Map<String, Boolean[]> haToScoreFlags = new HashMap<>();
		Map<String, List<Integer>> haToIndices2 = new HashMap<>();
		Map<String, Boolean[]> haToScoreFlags2 = new HashMap<>();
		// 事前に Home / Away ごとにインデックスを分類
		for (int i = 0; i < bodyList.size(); i++) {
			String ha = bodyList.get(i).get(1); // H or A
			haToIndices.computeIfAbsent(ha, k -> new ArrayList<>()).add(i);
		}
		for (int i = 0; i < bodyList2.size(); i++) {
			String ha = bodyList2.get(i).get(1); // H or A
			haToIndices2.computeIfAbsent(ha, k -> new ArrayList<>()).add(i);
		}

		// 各 H / A に対して個別にスコアフラグを判定
		for (Map.Entry<String, List<Integer>> entry : haToIndices.entrySet()) {
			String ha = entry.getKey();
			List<Integer> indices = entry.getValue();

			Boolean[] scoreFlags = new Boolean[indices.size()];
			for (int i = 0; i < indices.size(); i++) {
				int originalIndex = indices.get(i);
				List<String> body = bodyList.get(originalIndex);
				scoreFlags[i] = false; // 初期化

				for (int j = 2; j < body.size(); j++) {
					String feas = body.get(j);
					if (feas == null || feas.isBlank())
						continue;

					String[] fea_data = feas.split("-");
					if (fea_data.length > 1 && !fea_data[1].endsWith(" (0)")) {
						scoreFlags[i] = true;
						break; // 得点が1つでもあれば判定終了
					}
				}
			}
			haToScoreFlags.put(ha, scoreFlags);
		}
		// TIME_DIFF, TIME_ACCUMULATIONのみ
		for (Map.Entry<String, List<Integer>> entry : haToIndices2.entrySet()) {
			String ha = entry.getKey();
			List<Integer> indices2 = entry.getValue();

			Boolean[] scoreFlags = new Boolean[indices2.size()];
			for (int i = 0; i < indices2.size(); i++) {
				int originalIndex = indices2.get(i);
				List<String> body = bodyList2.get(originalIndex);
				scoreFlags[i] = false; // 初期化

				for (int j = 2; j < body.size(); j++) {
					String feas = body.get(j);
					if (feas == null || feas.isBlank())
						continue;

					String[] fea_data = feas.split("-");
					if (fea_data.length > 1 && !fea_data[1].endsWith(" (0)")) {
						scoreFlags[i] = true;
						break; // 得点が1つでもあれば判定終了
					}
				}
			}
			haToScoreFlags2.put(ha, scoreFlags);
		}

		// ACCUMULATIONのみ
		if (haToIndices2.isEmpty()) {
			for (Map.Entry<String, List<Integer>> entry : haToIndices.entrySet()) {
				String ha = entry.getKey(); // H or A
				List<Integer> indices = entry.getValue();
				Boolean[] scoreFlags = haToScoreFlags.get(ha);

				System.out.println("=== 処理対象: " + ha + " の試合 ===");

				// data_situation_key: ACCUMULATION
				for (String data_situation_key : data_situation_key_list) {

					double minNoScoreMainFeature = 1000.0;
					double maxNoScoreMainFeature = 0.0;
					double averageNoScoreMainFeature = 0.0;
					int noScoreMainInt = 0;
					String minNoScoreMainFeatureOppoTeamSingle = team2;
					String maxNoScoreMainFeatureOppoTeamSingle = team2;

					String time_key = "";

					// 事前に得点なしのみ抽出
					List<Integer> noScoreIndices = extractNoScoreIndices(indices, scoreFlags);

					// DBに保管されているデータを取得し,既存データと比較する
					CollectScoringOutputDTO collectScoringOutputDTO = getData(country, league, team, ha,
							data_situation_key,
							time_key, CollectScoringDataStandardValueUtil.NO_GET_SCORE, stat);
					List<List<String>> resultList = collectScoringOutputDTO.getSelectResultList();
					String id = collectScoringOutputDTO.getId();
					boolean updFlg = collectScoringOutputDTO.isExistFlg();
					for (List<String> list : resultList) {
						String get_stat = list.get(3);
						if (get_stat == null) continue;
						// statを分割
						String[] stat_split = get_stat.split(",");
						String get_stat_min = stat_split[0];
						List<String> data1 = ExecuteMainUtil.getExceptForPercent(get_stat_min);
						get_stat_min = data1.get(0);
						this.suffix1 = data1.get(1);
						String get_stat_max = stat_split[1];
						List<String> data2 = ExecuteMainUtil.getExceptForPercent(get_stat_max);
						get_stat_max = data2.get(0);
						this.suffix2 = data2.get(1);
						String get_stat_ave = stat_split[2];
						List<String> data3 = ExecuteMainUtil.getExceptForPercent(get_stat_ave);
						get_stat_ave = data3.get(0);
						this.suffix3 = data3.get(1);
						int get_stat_count = Integer.parseInt(stat_split[3]);
						minNoScoreMainFeature = Double.parseDouble(get_stat_min);
						maxNoScoreMainFeature = Double.parseDouble(get_stat_max);
						averageNoScoreMainFeature = Double.parseDouble(get_stat_ave);
						noScoreMainInt = get_stat_count;
						minNoScoreMainFeatureOppoTeamSingle = stat_split[4];
						maxNoScoreMainFeatureOppoTeamSingle = stat_split[5];
						// 平均*件数を導出
						averageNoScoreMainFeature *= noScoreMainInt;
					}

					// home, awayのindex
					for (int indMain : noScoreIndices) {
						List<String> body = bodyList.get(indMain);

						// 時間帯データをloop
						for (int ind = 2; ind < body.size(); ind++) {
							// X.XX-X位（1）
							String feas = body.get(ind);
							if (feas == null || feas.isBlank()) {
								continue;
							}
							String[] fea_data = feas.split("-");
							String features = fea_data[0];
							List<String> data1 = ExecuteMainUtil.getExceptForPercent(features);
							features = data1.get(0);
							this.suffix_single = data1.get(1);
							double data = Double.parseDouble(features);
							// 単純累積についてはNO_GET_SCOREのみ確認する(最大値を確認するとその最大値では点が入らないことがわかる)
							List<String> result1 = compareMin(
									minNoScoreMainFeature, data,
									minNoScoreMainFeatureOppoTeamSingle, team2);
							minNoScoreMainFeature = Double.parseDouble(result1.get(0));
							minNoScoreMainFeatureOppoTeamSingle = result1.get(1);
							List<String> result2 = compareMax(
									maxNoScoreMainFeature, data,
									maxNoScoreMainFeatureOppoTeamSingle, team2);
							maxNoScoreMainFeature = Double.parseDouble(result2.get(0));
							maxNoScoreMainFeatureOppoTeamSingle = result2.get(1);
							// 平均導出用
							averageNoScoreMainFeature = sumAverage(
									averageNoScoreMainFeature, data);
							noScoreMainInt = sumCount(noScoreMainInt, 1);
						}
					}

					// 平均を導出
					if (noScoreMainInt > 0) {
						averageNoScoreMainFeature /= noScoreMainInt;
					} else {
						averageNoScoreMainFeature = 0.0;
					}

					// 最小値(minNoScoreMainFeature),最大値(maxNoScoreMainFeature),
					// 平均値(averageNoScoreMainFeature),
					// 最小値,最大値を示した時の対戦チーム名(oppoteams)
					String sBuilder = String.join(",",
							String.format("%.2f", minNoScoreMainFeature) + this.suffix1,
							String.format("%.2f", maxNoScoreMainFeature) + this.suffix1,
							String.format("%.2f", averageNoScoreMainFeature) + this.suffix1,
							String.valueOf(noScoreMainInt));
					//String.valueOf(team2));

					registerData(country, league, team, ha,
							data_situation_key, null, CollectScoringDataStandardValueUtil.NO_GET_SCORE, stat,
							sBuilder.toString(), updFlg, id);
				}
			}
			// TIME_DIFF, DIFF, TIME_ACCUMULATION
		} else {
			for (Map.Entry<String, List<Integer>> entry : haToIndices.entrySet()) {
				String ha = entry.getKey();
				List<Integer> indices = entry.getValue();
				String oppoha = ("H".equals(ha)) ? "A" : "H";
				if (!haToIndices2.containsKey(oppoha)) {
					continue;
				}
				List<Integer> indices2 = haToIndices2.get(oppoha);

				// 同一indexは同一時間帯
				double[] minScoreFeature = { 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0,
						1000.0, 1000.0 };
				double[] maxScoreFeature = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				double[] averageScoreFeature = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				double[] minNoScoreFeature = { 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0,
						1000.0, 1000.0 };
				double[] maxNoScoreFeature = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				double[] averageNoScoreFeature = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				int[] scoreInt = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				int[] noScoreInt = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				String[] minScoreFeatureOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] maxScoreFeatureOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] minNoScoreFeatureOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] maxNoScoreFeatureOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };

				double[] minScoreFeatureDiff = { 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0,
						1000.0, 1000.0 };
				double[] maxScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				int[] scoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				int[] minusScoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				int[] plusScoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				double[] minNoScoreFeatureDiff = { 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0,
						1000.0, 1000.0, 1000.0 };
				double[] maxNoScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				int[] noScoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				int[] minusNoScoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				int[] plusNoScoreIntDiff = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				double minNoScoreMainFeatureDiff = 1000.0;
				double maxNoScoreMainFeatureDiff = 0.0;
				int noScoreMainIntDiff = 0;
				int minusNoScoreMainIntDiff = 0;
				int plusNoScoreMainIntDiff = 0;
				double[] averageScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				double[] averageNoScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				double averageNoScoreMainFeatureDiff = 0.0;

				String[] minScoreFeatureDiffOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] maxScoreFeatureDiffOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] minNoScoreFeatureDiffOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String[] maxNoScoreFeatureDiffOppoTeam = { team2, team2, team2, team2, team2,
						team2, team2, team2, team2 };
				String minNoScoreMainFeatureDiffOppoTeamSingle = team2;
				String maxNoScoreMainFeatureDiffOppoTeamSingle = team2;

				// 差分比較ができるか
				if (indices != null && indices2 != null) {
					String time_key = "";
					// homeなら片方はawayのindex,その逆も然り
					for (int indMain : indices) {
						for (int indMain2 : indices2) {
							// data_situation_key: TIME_ACCUMULATION, TIME_DIFF, DIFF
							for (String data_situation_key : data_situation_key_list2) {

								// csv1のデータ
								List<String> body1 = bodyList.get(indMain);
								// csv2のデータ
								List<String> body2 = bodyList2.get(indMain2);
								// csv1名のチーム名とcsv2データの相手チーム名,csv2名のチーム名とcsv1データの相手チーム名の組み合わせが等しいこと
								String connectTeam1 = ("H".equals(ha)) ? team + "-" + body1.get(0)
										: team2 + "-" + body2.get(0);
								String connectTeam2 = ("H".equals(ha)) ? body2.get(0) + "-" + team2
										: body1.get(0) + "-" + team;
								if (!connectTeam1.equals(connectTeam2)) {
									continue;
								}

								//								double[] standardDiviationNoScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
								//										0.0 };
								//								double[] standardDiviationScoreFeatureDiff = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
								//										0.0 };
								//								double standardDiviationNoScoreMainFeatureDiff = 0.0;

								// ホームチームアウェーチーム決定
								String teams = ("H".equals(ha)) ? team : body1.get(0);
								String oppoteams = ("H".equals(ha)) ? team2 : body2.get(0);
								String opposite_teams = "vs" + oppoteams;

								// DBに保管されているデータを取得し,既存データと比較する
								// TIME_DIFF: time_key全て取得
								if (CollectScoringDataStandardValueUtil.TIME_DIFF.equals(data_situation_key)) {
									// 比較方針: teamを主軸にしたときの同一時間帯における相手チーム対戦時のteamの得点の差分を取得
									CollectScoringOutputDTO collectScoringOutputDTO = getData(country, league, team, ha,
											data_situation_key,
											time_key, null, stat);
									List<List<String>> resultList = collectScoringOutputDTO.getSelectResultList();
									boolean updFlg = collectScoringOutputDTO.isExistFlg();
									// 得点あり,なし両方保持
									List<String> idList = new ArrayList<>();
									for (int j = 0; j < score_distribution_list.length; j++) {
										for (int i = 0; i < CollectScoringDataStandardValueUtil.TIME_KEY.length; i++) {
											idList.add("");
										}
									}
									for (List<String> list : resultList) {
										String get_time_key = list.get(1);
										String[] range = get_time_key.split("〜");
										String score_distribution = list.get(2);
										// GET_SCOREなら0, NOT_GET_SCOREなら1を暫定的に指定
										int tmp_flg = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) ?
												0 : 1;
										int stat_ind = Integer.parseInt(range[0]) / 10;
										String id = list.get(0);
										// GET_SCOREなら0〜9, NOT_GET_SCOREなら10〜19のindexに割り振る
										idList.set(tmp_flg * CollectScoringDataStandardValueUtil.TIME_KEY.length + stat_ind, id);
										String get_stat = list.get(3);
										// statがnull(同一データだが特徴量がまだ未登録)はcontinue
										if (get_stat == null)
											continue;
										// statを分割
										String[] stat_split = get_stat.split(",");
										String get_stat_min = stat_split[0];
										List<String> data1 = ExecuteMainUtil.getExceptForPercent(get_stat_min);
										get_stat_min = data1.get(0);
										this.suffix1 = data1.get(1);
										String get_stat_max = stat_split[1];
										List<String> data2 = ExecuteMainUtil.getExceptForPercent(get_stat_max);
										get_stat_max = data2.get(0);
										this.suffix2 = data2.get(1);
										String get_stat_ave = stat_split[2];
										List<String> data3 = ExecuteMainUtil.getExceptForPercent(get_stat_ave);
										get_stat_ave = data3.get(0);
										this.suffix3 = data3.get(1);
										int minus_get_stat_count = Integer.parseInt(stat_split[3]);
										int plus_get_stat_count = Integer.parseInt(stat_split[4]);
										int get_stat_count = Integer.parseInt(stat_split[5]);
										// 比較エラー
										if (!this.suffix1.equals(this.suffix2) || !this.suffix2.equals(this.suffix3)
												|| !this.suffix3.equals(this.suffix1)) {
											throw new BusinessException("", "", "",
													"suffix err: country: " + country +
															", league: " + league + ", team: " + teams
															+ ", oppoteam: " + oppoteams + ", "
															+ "stat: " + stat + ", data_situation_key: "
															+ data_situation_key + "");
										}
										if (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) {
											minScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_min);
											maxScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_max);
											averageScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_ave);
											scoreIntDiff[stat_ind] = get_stat_count;
											minusScoreIntDiff[stat_ind] = minus_get_stat_count;
											plusScoreIntDiff[stat_ind] = plus_get_stat_count;
											minScoreFeatureDiffOppoTeam[stat_ind] = stat_split[6];
											maxScoreFeatureDiffOppoTeam[stat_ind] = stat_split[7];
											// 平均*件数を導出
											averageScoreFeatureDiff[stat_ind] *= scoreIntDiff[stat_ind];
										} else if (CollectScoringDataStandardValueUtil.NO_GET_SCORE.equals(score_distribution)) {
											minNoScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_min);
											maxNoScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_max);
											averageNoScoreFeatureDiff[stat_ind] = Double.parseDouble(get_stat_ave);
											noScoreIntDiff[stat_ind] = get_stat_count;
											minusNoScoreIntDiff[stat_ind] = minus_get_stat_count;
											plusNoScoreIntDiff[stat_ind] = plus_get_stat_count;
											minNoScoreFeatureDiffOppoTeam[stat_ind] = stat_split[6];
											maxNoScoreFeatureDiffOppoTeam[stat_ind] = stat_split[7];
											// 平均*件数を導出
											averageNoScoreFeatureDiff[stat_ind] *= noScoreIntDiff[stat_ind];
										}
									}

									// 時間帯データをloop
									for (int ind = 2; ind < body1.size(); ind++) {
										// X.XX-X位（1）
										String feas1 = body1.get(ind);
										String feas2 = body2.get(ind);
										if (feas1 == null || feas1.isBlank()) {
											continue;
										}
										if (feas2 == null || feas2.isBlank()) {
											continue;
										}
										String[] fea_data1 = feas1.split("-");
										String features1 = fea_data1[0];
										String rank1 = fea_data1[1];
										String[] fea_data2 = feas2.split("-");
										String features2 = fea_data2[0];
										String rank2 = fea_data2[1];
										List<String> dataSub1 = ExecuteMainUtil.getExceptForPercent(features1);
										features1 = dataSub1.get(0);
										this.suffix1 = dataSub1.get(1);
										List<String> dataSub2 = ExecuteMainUtil.getExceptForPercent(features2);
										features2 = dataSub2.get(0);
										this.suffix2 = dataSub2.get(1);
										// 比較エラー
										if (!this.suffix1.equals(this.suffix2)) {
											throw new BusinessException("", "", "", "suffix err: country: " + country +
													", league: " + league + ", team: " + teams + ", oppoteam: "
													+ oppoteams + ", "
													+ "stat: " + stat + ", data_situation_key: " + data_situation_key
													+ "");
										}
										double data1 = Double.parseDouble(features1);
										double data2 = Double.parseDouble(features2);
										if (rank1.endsWith(" (0)") && !rank2.endsWith(" (0)")) {
											if (data1 != 10000.0 && data2 != 10000.0) {
												// data1-data2
												double sa1 = data1 - data2;
												List<String> result1 = compareSaMin(
														minScoreFeatureDiff[ind - 2], sa1,
														minScoreFeatureDiffOppoTeam[ind - 2], team2);
												minScoreFeatureDiff[ind - 2] = Double.parseDouble(result1.get(0));
												minScoreFeatureDiffOppoTeam[ind - 2] = result1.get(1);
												List<String> result2 = compareSaMax(
														maxScoreFeatureDiff[ind - 2], sa1,
														maxScoreFeatureDiffOppoTeam[ind - 2], team2);
												maxScoreFeatureDiff[ind - 2] = Double.parseDouble(result2.get(0));
												maxScoreFeatureDiffOppoTeam[ind - 2] = result2.get(1);
												averageScoreFeatureDiff[ind - 2] = sumSaAverage(
														averageScoreFeatureDiff[ind - 2], sa1);
												if (sa1 < 0.0) {
													minusScoreIntDiff[ind - 2] = sumSaCount(minusScoreIntDiff[ind - 2],
															1);
												} else if (sa1 > 0.0) {
													plusScoreIntDiff[ind - 2] = sumSaCount(plusScoreIntDiff[ind - 2],
															1);
												}
												scoreIntDiff[ind - 2] = sumSaCount(scoreIntDiff[ind - 2], 1);
											}
										} else if (rank2.endsWith(" (0)") && !rank1.endsWith(" (0)")) {
											if (data1 != 10000.0 && data2 != 10000.0) {
												// data1-data2
												double sa2 = data1 - data2;
												List<String> result1 = compareSaMin(
														minScoreFeatureDiff[ind - 2], sa2,
														minScoreFeatureDiffOppoTeam[ind - 2], team2);
												minScoreFeatureDiff[ind - 2] = Double.parseDouble(result1.get(0));
												minScoreFeatureDiffOppoTeam[ind - 2] = result1.get(1);
												List<String> result2 = compareSaMax(
														maxScoreFeatureDiff[ind - 2], sa2,
														maxScoreFeatureDiffOppoTeam[ind - 2], team2);
												maxScoreFeatureDiff[ind - 2] = Double.parseDouble(result2.get(0));
												maxScoreFeatureDiffOppoTeam[ind - 2] = result2.get(1);
												averageScoreFeatureDiff[ind - 2] = sumSaAverage(
														averageScoreFeatureDiff[ind - 2], sa2);
												if (sa2 < 0.0) {
													minusScoreIntDiff[ind - 2] = sumSaCount(minusScoreIntDiff[ind - 2],
															1);
												} else if (sa2 > 0.0) {
													plusScoreIntDiff[ind - 2] = sumSaCount(plusScoreIntDiff[ind - 2],
															1);
												}
												scoreIntDiff[ind - 2] = sumSaCount(scoreIntDiff[ind - 2], 1);
											}
											// 無得点
										} else if (rank1.endsWith(" (0)") && rank2.endsWith(" (0)")) {
											if (data1 != 10000.0 && data2 != 10000.0) {
												// data1-data2
												double sano = data1 - data2;
												List<String> result1 = compareSaMin(
														minNoScoreFeatureDiff[ind - 2], sano,
														minNoScoreFeatureDiffOppoTeam[ind - 2], team2);
												minNoScoreFeatureDiff[ind - 2] = Double.parseDouble(result1.get(0));
												minNoScoreFeatureDiffOppoTeam[ind - 2] = result1.get(1);
												List<String> result2 = compareSaMax(
														maxNoScoreFeatureDiff[ind - 2], sano,
														maxNoScoreFeatureDiffOppoTeam[ind - 2], team2);
												maxNoScoreFeatureDiff[ind - 2] = Double.parseDouble(result2.get(0));
												maxNoScoreFeatureDiffOppoTeam[ind - 2] = result2.get(1);
												averageNoScoreFeatureDiff[ind - 2] = sumSaAverage(
														averageNoScoreFeatureDiff[ind - 2], sano);
												if (sano < 0.0) {
													minusNoScoreIntDiff[ind - 2] = sumSaCount(
															minusNoScoreIntDiff[ind - 2], 1);
												} else if (sano > 0.0) {
													plusNoScoreIntDiff[ind - 2] = sumSaCount(
															plusNoScoreIntDiff[ind - 2], 1);
												}
												noScoreIntDiff[ind - 2] = sumSaCount(noScoreIntDiff[ind - 2], 1);
											}
											// 両方得点
										} else if (!rank1.endsWith(" (0)") && !rank2.endsWith(" (0)")) {
											if (data1 != 10000.0 && data2 != 10000.0) {
												// data1-data2
												double sabo = data1 - data2;
												List<String> result1 = compareSaMin(
														minScoreFeatureDiff[ind - 2], sabo,
														minScoreFeatureDiffOppoTeam[ind - 2], team2);
												minScoreFeatureDiff[ind - 2] = Double.parseDouble(result1.get(0));
												minScoreFeatureDiffOppoTeam[ind - 2] = result1.get(1);
												List<String> result2 = compareSaMax(
														maxScoreFeatureDiff[ind - 2], sabo,
														maxScoreFeatureDiffOppoTeam[ind - 2], team2);
												maxScoreFeatureDiff[ind - 2] = Double.parseDouble(result2.get(0));
												maxScoreFeatureDiffOppoTeam[ind - 2] = result2.get(1);
												averageScoreFeatureDiff[ind - 2] = sumSaAverage(
														averageScoreFeatureDiff[ind - 2], sabo);
												if (sabo < 0.0) {
													minusScoreIntDiff[ind - 2] = sumSaCount(minusScoreIntDiff[ind - 2],
															1);
												} else if (sabo > 0.0) {
													plusScoreIntDiff[ind - 2] = sumSaCount(plusScoreIntDiff[ind - 2],
															1);
												}
												scoreIntDiff[ind - 2] = sumSaCount(scoreIntDiff[ind - 2], 1);
											}
										}
									}

									// 平均を導出
									for (int ave = 0; ave < averageScoreFeatureDiff.length; ave++) {
										if (scoreIntDiff[ave] > 0) {
											averageScoreFeatureDiff[ave] /= scoreIntDiff[ave];
										} else {
											averageScoreFeatureDiff[ave] = 0.0;
										}
									}
									// 平均を導出
									for (int ave = 0; ave < averageNoScoreFeatureDiff.length; ave++) {
										if (noScoreIntDiff[ave] > 0) {
											averageNoScoreFeatureDiff[ave] /= noScoreIntDiff[ave];
										} else {
											averageNoScoreFeatureDiff[ave] = 0.0;
										}
									}

									// 差分最小値(minScoreFeatureDiff,minNoScoreFeatureDiff),
									// 差分最大値(maxScoreFeatureDiff,maxNoScoreFeatureDiff),
									// 差分平均値(averageScoreFeatureDiff,averageNoScoreFeatureDiff),
									// 差分マイナスの数(minusScoreIntDiff),差分プラスの数(plusScoreIntDiff),
									// 対象データ数(scoreIntDiff)
									// 差分最小値,差分最大値を示した時の対戦チーム名(oppoteams)
									for (String score_distribution : score_distribution_list) {
										int sub_ind = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) ?
												0 : CollectScoringDataStandardValueUtil.TIME_KEY.length;
										int id_ind = sub_ind;
										int stat_id = 0;
										for (String time_key_reg : CollectScoringDataStandardValueUtil.TIME_KEY) {
											// データ連結
											double minDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? minScoreFeatureDiff[stat_id]
													: minNoScoreFeatureDiff[stat_id];
											double maxDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? maxScoreFeatureDiff[stat_id]
													: maxNoScoreFeatureDiff[stat_id];
											double aveDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? averageScoreFeatureDiff[stat_id]
													: averageNoScoreFeatureDiff[stat_id];
											int minusDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? minusScoreIntDiff[stat_id]
													: minusNoScoreIntDiff[stat_id];
											int plusDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? plusScoreIntDiff[stat_id]
													: plusNoScoreIntDiff[stat_id];
											int scoreDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? scoreIntDiff[stat_id]
													: noScoreIntDiff[stat_id];
											String minOppoDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? minScoreFeatureDiffOppoTeam[stat_id]
													: minNoScoreFeatureDiffOppoTeam[stat_id];
											String maxOppoDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
													? maxScoreFeatureDiffOppoTeam[stat_id]
													: maxNoScoreFeatureDiffOppoTeam[stat_id];
											String sBuilder = String.join(",",
													String.format("%.2f", minDiff) + this.suffix1,
													String.format("%.2f", maxDiff) + this.suffix1,
													String.format("%.2f", aveDiff) + this.suffix1,
													String.valueOf(minusDiff),
													String.valueOf(plusDiff),
													String.valueOf(scoreDiff),
													String.valueOf(minOppoDiff),
													String.valueOf(maxOppoDiff));

											registerData(country, league, team, ha,
													data_situation_key, time_key_reg, score_distribution, stat,
													sBuilder.toString(), updFlg, idList.get(id_ind));
											stat_id++;
											id_ind++;
										}
									}

								} else if (CollectScoringDataStandardValueUtil.DIFF.equals(data_situation_key)) {
									// 比較方針: teamを主軸にしたときの時間帯関係なく相手チーム対戦時のteamの得点の差分を取得
									CollectScoringOutputDTO collectScoringOutputDTO = getData(country, league, team, ha,
											data_situation_key,
											time_key, CollectScoringDataStandardValueUtil.NO_GET_SCORE, stat);
									List<List<String>> resultList = collectScoringOutputDTO.getSelectResultList();
									String id = collectScoringOutputDTO.getId();
									boolean updFlg = collectScoringOutputDTO.isExistFlg();
									for (List<String> list : resultList) {
										String get_stat = list.get(3);
										if (get_stat == null) continue;
										// statを分割
										String[] stat_split = get_stat.split(",");
										String get_stat_min = stat_split[0];
										List<String> data1 = ExecuteMainUtil.getExceptForPercent(get_stat_min);
										get_stat_min = data1.get(0);
										this.suffix1 = data1.get(1);
										String get_stat_max = stat_split[1];
										List<String> data2 = ExecuteMainUtil.getExceptForPercent(get_stat_max);
										get_stat_max = data2.get(0);
										this.suffix2 = data2.get(1);
										String get_stat_ave = stat_split[2];
										List<String> data3 = ExecuteMainUtil.getExceptForPercent(get_stat_ave);
										get_stat_ave = data3.get(0);
										this.suffix3 = data3.get(1);
										// 比較エラー
										if (!this.suffix1.equals(this.suffix2) || !this.suffix2.equals(this.suffix3)
												|| !this.suffix3.equals(this.suffix1)) {
											throw new BusinessException("", "", "",
													"suffix err: country: " + country +
															", league: " + league + ", team: " + teams
															+ ", oppoteam: " + oppoteams + ", "
															+ "stat: " + stat + ", data_situation_key: "
															+ data_situation_key + "");
										}
										int minus_get_stat_count = Integer.parseInt(stat_split[3]);
										int plus_get_stat_count = Integer.parseInt(stat_split[4]);
										int get_stat_count = Integer.parseInt(stat_split[5]);
										minNoScoreMainFeatureDiff = Double.parseDouble(get_stat_min);
										maxNoScoreMainFeatureDiff = Double.parseDouble(get_stat_max);
										averageNoScoreMainFeatureDiff = Double.parseDouble(get_stat_ave);
										minusNoScoreMainIntDiff = minus_get_stat_count;
										plusNoScoreMainIntDiff = plus_get_stat_count;
										noScoreMainIntDiff = get_stat_count;
										minNoScoreMainFeatureDiffOppoTeamSingle = stat_split[6];
										maxNoScoreMainFeatureDiffOppoTeamSingle = stat_split[7];
										// 平均*件数を導出
										averageNoScoreMainFeatureDiff *= noScoreMainIntDiff;
									}

									// 時間帯データをloop
									for (int ind = 2; ind < body1.size(); ind++) {
										// X.XX-X位（1）
										String feas1 = body1.get(ind);
										String feas2 = body2.get(ind);
										if (feas1 == null || feas1.isBlank()) {
											continue;
										}
										if (feas2 == null || feas2.isBlank()) {
											continue;
										}
										String[] fea_data1 = feas1.split("-");
										String features1 = fea_data1[0];
										String rank1 = fea_data1[1];
										String[] fea_data2 = feas2.split("-");
										String features2 = fea_data2[0];
										String rank2 = fea_data2[1];
										List<String> dataSub1 = ExecuteMainUtil.getExceptForPercent(features1);
										features1 = dataSub1.get(0);
										this.suffix1 = dataSub1.get(1);
										List<String> dataSub2 = ExecuteMainUtil.getExceptForPercent(features2);
										features2 = dataSub2.get(0);
										this.suffix2 = dataSub2.get(1);
										// 比較エラー
										if (!this.suffix1.equals(this.suffix2)) {
											throw new BusinessException("", "", "", "suffix err: country: " + country +
													", league: " + league + ", team: " + teams + ", oppoteam: "
													+ oppoteams + ", "
													+ "stat: " + stat + ", data_situation_key: " + data_situation_key
													+ "");
										}
										double data1 = Double.parseDouble(features1);
										double data2 = Double.parseDouble(features2);
										// 無得点
										if (rank1.endsWith(" (0)") && rank2.endsWith(" (0)")) {
											if (data1 != 10000.0 && data2 != 10000.0) {
												// data1-data2
												double sano = data1 - data2;
												List<String> result1 = compareSaMin(
														minNoScoreMainFeatureDiff, sano,
														minNoScoreMainFeatureDiffOppoTeamSingle, team2);
												minNoScoreMainFeatureDiff = Double.parseDouble(result1.get(0));
												minNoScoreMainFeatureDiffOppoTeamSingle = result1.get(1);
												List<String> result2 = compareSaMax(
														maxNoScoreMainFeatureDiff, sano,
														maxNoScoreMainFeatureDiffOppoTeamSingle, team2);
												maxNoScoreMainFeatureDiff = Double.parseDouble(result2.get(0));
												maxNoScoreMainFeatureDiffOppoTeamSingle = result2.get(1);
												averageNoScoreMainFeatureDiff = sumSaAverage(
														averageNoScoreMainFeatureDiff, sano);
												if (sano < 0.0) {
													minusNoScoreMainIntDiff = sumSaCount(
															minusNoScoreMainIntDiff, 1);
												} else if (sano > 0.0) {
													plusNoScoreMainIntDiff = sumSaCount(
															plusNoScoreMainIntDiff, 1);
												}
												noScoreMainIntDiff = sumSaCount(noScoreMainIntDiff, 1);
											}
										}
									}

									// 平均を導出
									if (noScoreMainIntDiff > 0) {
										averageNoScoreMainFeatureDiff /= noScoreMainIntDiff;
									} else {
										averageNoScoreMainFeatureDiff = 0.0;
									}

									// 差分最小値(minNoScoreMainFeatureDiff),
									// 差分最大値(maxNoScoreMainFeatureDiff),
									// 差分平均値(averageNoScoreMainFeatureDiff),
									// 差分マイナスの数(minusNoScoreMainIntDiff),差分プラスの数(plusNoScoreMainIntDiff),
									// 対象データ数(noScoreMainIntDiff)
									// 差分最小値,差分最大値を示した時の対戦チーム名(oppoteams)
									String sBuilder = String.join(",",
											String.format("%.2f", minNoScoreMainFeatureDiff) + this.suffix1,
											String.format("%.2f", maxNoScoreMainFeatureDiff) + this.suffix1,
											String.format("%.2f", averageNoScoreMainFeatureDiff) + this.suffix1,
											String.valueOf(minusNoScoreMainIntDiff),
											String.valueOf(plusNoScoreMainIntDiff),
											String.valueOf(noScoreMainIntDiff),
											String.valueOf(minNoScoreMainFeatureDiffOppoTeamSingle),
											String.valueOf(maxNoScoreMainFeatureDiffOppoTeamSingle));

									registerData(country, league, team, ha,
											data_situation_key, null, CollectScoringDataStandardValueUtil.NO_GET_SCORE, stat,
											sBuilder.toString(), updFlg, id);

								} else if (CollectScoringDataStandardValueUtil.TIME_ACCUMULATION.equals(data_situation_key)) {
									// 得点した試合のデータかそうでないか(ここまででcountry, leagueは同一の想定)
									// スコア分布(TIME_ACCUMULATION用)
									String score_distribution = (chkScore(
											teams, opposite_teams, ha))
													? CollectScoringDataStandardValueUtil.GET_SCORE
													: CollectScoringDataStandardValueUtil.NO_GET_SCORE;

									// 比較方針: teamを主軸にしたときの同一時間帯における相手チーム対戦時のteamの得点の特徴量累積を取得
									CollectScoringOutputDTO collectScoringOutputDTO = getData(country, league, team, ha,
											data_situation_key,
											time_key, score_distribution, stat);
									List<List<String>> resultList = collectScoringOutputDTO.getSelectResultList();
									boolean updFlg = collectScoringOutputDTO.isExistFlg();
									List<String> idList = new ArrayList<>();
									for (int i = 0; i < CollectScoringDataStandardValueUtil.TIME_KEY.length; i++) {
										idList.add("");
									}
									for (List<String> list : resultList) {
										String get_time_key = list.get(1);
										int stat_ind = Integer.parseInt(get_time_key.substring(0, 1));
										String id = list.get(0);
										idList.set(stat_ind, id);
										String score_distribution_sub = list.get(2);
										String get_stat = list.get(3);
										// statがnull(同一データだが特徴量がまだ未登録)はcontinue
										if (get_stat == null)
											continue;
										// statを分割
										String[] stat_split = get_stat.split(",");
										String get_stat_min = stat_split[0];
										List<String> data1 = ExecuteMainUtil.getExceptForPercent(get_stat_min);
										get_stat_min = data1.get(0);
										this.suffix1 = data1.get(1);
										String get_stat_max = stat_split[1];
										List<String> data2 = ExecuteMainUtil.getExceptForPercent(get_stat_max);
										get_stat_max = data2.get(0);
										this.suffix2 = data2.get(1);
										String get_stat_ave = stat_split[2];
										List<String> data3 = ExecuteMainUtil.getExceptForPercent(get_stat_ave);
										get_stat_ave = data3.get(0);
										this.suffix3 = data3.get(1);
										// 比較エラー
										if (!this.suffix1.equals(this.suffix2) || !this.suffix2.equals(this.suffix3)
												|| !this.suffix3.equals(this.suffix1)) {
											throw new BusinessException("", "", "", "suffix err: country: " + country +
													", league: " + league + ", team: " + teams + ", oppoteam: "
													+ oppoteams + ", "
													+ "stat: " + stat + ", data_situation_key: " + data_situation_key
													+ "");
										}
										int get_stat_count = Integer.parseInt(stat_split[3]);

										if (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution_sub)) {
											minScoreFeature[stat_ind] = Double.parseDouble(get_stat_min);
											maxScoreFeature[stat_ind] = Double.parseDouble(get_stat_max);
											averageScoreFeature[stat_ind] = Double.parseDouble(get_stat_ave);
											scoreInt[stat_ind] = get_stat_count;
											minScoreFeatureOppoTeam[stat_ind] = stat_split[4];
											maxScoreFeatureOppoTeam[stat_ind] = stat_split[5];
											// 平均*件数を導出
											averageScoreFeature[stat_ind] *= scoreInt[stat_ind];
										} else if (CollectScoringDataStandardValueUtil.NO_GET_SCORE.equals(score_distribution_sub)) {
											minNoScoreFeature[stat_ind] = Double.parseDouble(get_stat_min);
											maxNoScoreFeature[stat_ind] = Double.parseDouble(get_stat_max);
											averageNoScoreFeature[stat_ind] = Double.parseDouble(get_stat_ave);
											noScoreInt[stat_ind] = get_stat_count;
											minNoScoreFeatureOppoTeam[stat_ind] = stat_split[4];
											maxNoScoreFeatureOppoTeam[stat_ind] = stat_split[5];
											// 平均*件数を導出
											averageNoScoreFeature[stat_ind] *= noScoreInt[stat_ind];
										}
									}

									// 時間帯データをloop
									for (int ind = 2; ind < body1.size(); ind++) {
										// X.XX-X位（1）
										String feas1 = body1.get(ind);
										if (feas1 == null || feas1.isBlank()) {
											continue;
										}
										String[] fea_data1 = feas1.split("-");
										String features1 = fea_data1[0];
										String rank1 = fea_data1[1];
										List<String> dataSub1 = ExecuteMainUtil.getExceptForPercent(features1);
										features1 = dataSub1.get(0);
										this.suffix_single = dataSub1.get(1);
										double data1 = Double.parseDouble(features1);
										// 得点なし
										if (rank1.endsWith(" (0)") && data1 != 10000.0) {
											List<String> result1 = compareMin(
													minNoScoreFeature[ind - 2], data1,
													minNoScoreFeatureOppoTeam[ind - 2], team2);
											minNoScoreFeature[ind - 2] = Double.parseDouble(result1.get(0));
											minNoScoreFeatureOppoTeam[ind - 2] = result1.get(1);
											List<String> result2 = compareMax(
													maxNoScoreFeature[ind - 2], data1,
													maxNoScoreFeatureOppoTeam[ind - 2], team2);
											maxNoScoreFeature[ind - 2] = Double.parseDouble(result2.get(0));
											maxNoScoreFeatureOppoTeam[ind - 2] = result2.get(1);
											// 平均導出用
											averageNoScoreFeature[ind - 2] = sumAverage(
													averageNoScoreFeature[ind - 2], data1);
											noScoreInt[ind - 2] = sumCount(noScoreInt[ind - 2], 1);
											// 得点あり
										} else if (!rank1.endsWith(" (0)") && data1 != 10000.0) {
											List<String> result1 = compareMin(
													minScoreFeature[ind - 2], data1,
													minScoreFeatureOppoTeam[ind - 2], team2);
											minScoreFeature[ind - 2] = Double.parseDouble(result1.get(0));
											minScoreFeatureOppoTeam[ind - 2] = result1.get(1);
											List<String> result2 = compareMax(
													maxScoreFeature[ind - 2], data1,
													maxScoreFeatureOppoTeam[ind - 2], team2);
											maxScoreFeature[ind - 2] = Double.parseDouble(result2.get(0));
											maxScoreFeatureOppoTeam[ind - 2] = result2.get(1);
											// 平均導出用
											averageScoreFeature[ind - 2] = sumAverage(
													averageScoreFeature[ind - 2], data1);
											scoreInt[ind - 2] = sumCount(scoreInt[ind - 2], 1);
										}
									}

									// 平均を導出
									for (int ave = 0; ave < averageScoreFeature.length; ave++) {
										if (scoreInt[ave] > 0) {
											averageScoreFeature[ave] /= scoreInt[ave];
										} else {
											averageScoreFeature[ave] = 0.0;
										}
									}
									// 平均を導出
									for (int ave = 0; ave < averageNoScoreFeature.length; ave++) {
										if (noScoreInt[ave] > 0) {
											averageNoScoreFeature[ave] /= noScoreInt[ave];
										} else {
											averageNoScoreFeature[ave] = 0.0;
										}
									}

									// 最小値(minNoScoreMainFeature,minScoreMainFeature),
									// 最大値(maxNoScoreMainFeature,maxScoreMainFeature),
									// 平均値(averageNoScoreMainFeature,averageNoScoreMainFeature),
									// 最小値,最大値を示した時の対戦チーム名(oppoteams)
									int stat_id = 0;
									for (String time_key_reg : CollectScoringDataStandardValueUtil.TIME_KEY) {
										double min = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) ? minScoreFeature[stat_id]
												: minNoScoreFeature[stat_id];
										double max = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) ? maxScoreFeature[stat_id]
												: maxNoScoreFeature[stat_id];
										double ave = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
												? averageScoreFeature[stat_id]
												: averageNoScoreFeature[stat_id];
										int score = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution)) ? scoreInt[stat_id]
												: noScoreInt[stat_id];
										String minOppoDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
												? minScoreFeatureOppoTeam[stat_id]
												: minNoScoreFeatureOppoTeam[stat_id];
										String maxOppoDiff = (CollectScoringDataStandardValueUtil.GET_SCORE.equals(score_distribution))
												? maxScoreFeatureOppoTeam[stat_id]
												: maxNoScoreFeatureOppoTeam[stat_id];
										String sBuilder = String.join(",",
												String.format("%.2f", min) + this.suffix_single,
												String.format("%.2f", max) + this.suffix_single,
												String.format("%.2f", ave) + this.suffix_single,
												String.valueOf(score),
												String.valueOf(minOppoDiff),
												String.valueOf(maxOppoDiff));

										registerData(country, league, team, ha,
												data_situation_key, time_key_reg, score_distribution, stat,
												sBuilder.toString(), updFlg, idList.get(stat_id));
										stat_id++;
									}
								}
							}
						}
					}
				}

				// 初期化
				this.suffix_single = "";
				this.suffix1 = "";
				this.suffix2 = "";
				this.suffix3 = "";
			}
		}
	}

	/**
	 * 取得メソッド
	 * @param country 国
	 * @param league リーグ
	 * @param team チーム
	 * @param ha home away
	 * @param data_situation_key situationkey
	 * @param time_key 時間関連キー
	 * @param score_distribution スコア分布(得点あり,得点なし)
	 * @param stat_name 特徴量名
	 */
	private CollectScoringOutputDTO getData(String country, String league, String team, String ha,
			String data_situation_key, String time_key, String score_distribution, String stat_name) {
		CollectScoringOutputDTO collectScoringOutputDTO = new CollectScoringOutputDTO();

		int list_size = (stat_name != null) ? 4 : 3;
		String[] selDataList = new String[list_size];
		selDataList[0] = "id";
		selDataList[1] = "time_key";
		selDataList[2] = "score_distribution";
		// 更新用統計データを取得する
		if (stat_name != null) {
			selDataList[3] = stat_name;
		}

		String where = "country = '" + country + "' and league = '" + league + "' "
				+ "and data_situation_key = '" + data_situation_key + "'"
				+ " and ha = '" + ha + "' and team = '" + team + "'";

		// nullの場合(score_distributionはTIME_DIFF, TIME_ACCUMULATIONの想定)
		if (score_distribution == null) {
			where += (" and score_distribution IN ('" + CollectScoringDataStandardValueUtil.GET_SCORE + "', "
					+ "'" + CollectScoringDataStandardValueUtil.NO_GET_SCORE + "')");
			// 設定済みの場合(DIFFはNO_GET_SCOREのみ,TIME_ACCUMULATIONはどちらかの想定)
		} else {
			where += (" and score_distribution = '" + score_distribution + "'");
		}

		// タイムキー設定
		if (CollectScoringDataStandardValueUtil.TIME_DIFF.equals(data_situation_key) || CollectScoringDataStandardValueUtil.TIME_ACCUMULATION.equals(data_situation_key)) {
			where += (" and time_key IN ('0〜10', '10〜20', '20〜30', '30〜40', '40〜50', '50〜60', '60〜70',"
					+ " '70〜80', '80〜90') ");
		}

		List<List<String>> selectResultList = new ArrayList<List<String>>();
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M030, selDataList,
					where, null, null);
		} catch (Exception e) {
			throw new SystemException("", "", "", "err: " + e);
		}

		collectScoringOutputDTO.setSelectResultList(selectResultList);
		if (!selectResultList.isEmpty()) {
			collectScoringOutputDTO.setId(selectResultList.get(0).get(0));
			collectScoringOutputDTO.setExistFlg(true);
			return collectScoringOutputDTO;
		}
		collectScoringOutputDTO.setExistFlg(false);
		return collectScoringOutputDTO;
	}

	/**
	 * 取得メソッド
	 * @param team チーム
	 * @param oppoteam 相手チーム
	 * @param ha home away
	 */
	private boolean chkScore(String team, String oppoteam, String ha) {
		String[] selDataList = new String[1];
		selDataList[0] = "score";

		String where = "ha = '" + ha + "' and team_name = '" + team + "' "
				+ "and versus_team_name = '" + oppoteam + "'";

		List<List<String>> selectResultList = null;
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M021, selDataList,
					where, null, "1");
		} catch (Exception e) {
			throw new SystemException("", "", "", "err: " + e);
		}

		// 基本はデータが存在する前提
		String scores = selectResultList.get(0).get(0);
		if (scores == null) {
			// nullはありえない
			throw new SystemException("", "", "", "null err: " + scores);
		} else {
			if (scores.contains("0-0")) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 取得メソッド
	 * @param country 国
	 * @param league リーグ
	 * @param team チーム
	 * @param ha home away
	 * @param data_situation_key situationkey
	 * @param time_key 時間関連キー
	 * @param score_distribution スコア分布(得点あり,得点なし)
	 * @param stat_name 特徴量名
	 * @param feature 特徴量
	 * @param updFlg 更新フラグ
	 * @param id ID
	 */
	private void registerData(String country, String league, String team, String ha,
			String data_situation_key, String time_key, String score_distribution, String stat_name, String feature,
			boolean updFlg, String id) {
		if (updFlg) {
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append(" " + stat_name + " = '" + feature + "'");
			sBuilder.append(", update_time = '" + DateUtil.getSysDate() + "'");
			UpdateWrapper updateWrapper = new UpdateWrapper();

			String where = "id = '" + id + "'";
			updateWrapper.updateExecute(UniairConst.BM_M030, where,
					sBuilder.toString());
			System.out.println("BM_M030を更新しました。country: " + country + ", league: " + league +
					", team: " + team + ", ha: " + ha + ", 登録データ: " + stat_name);
		} else {
			List<CollectScoringStandardDataEntity> insertEntities = new ArrayList<CollectScoringStandardDataEntity>();
			CollectScoringStandardDataEntity entity = new CollectScoringStandardDataEntity();
			entity.setDataSituationKey(data_situation_key);
			entity.setTimeKey(time_key);
			entity.setTeam(team);
			entity.setHa(ha);
			entity.setCountry(country);
			entity.setLeague(league);
			entity.setScoreDistribution(score_distribution);
			// 指定フィールドに feature をセット
			try {
				String camel = ExecuteMainUtil.convertToCamelCase(stat_name);
				Field targetField = entity.getClass().getDeclaredField(camel);
				targetField.setAccessible(true);
				targetField.set(entity, feature);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new SystemException("", "", "", "reflection err: " + e);
			}
			insertEntities.add(entity);

			CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
			try {
				csvRegisterImpl.executeInsert(UniairConst.BM_M030,
						insertEntities, 1, insertEntities.size());
			} catch (Exception e) {
				System.err.println("collect_scoring_standard_data insert err execute: " + e);
			}
			System.out.println("BM_M030に登録しました。country: " + country + ", league: " + league +
					", team: " + team + ", ha: " + ha + ", 登録データ: " + stat_name);
		}
	}

	/**
	 * 差分を比較し小さい方を返す。その際対象の相手チーム名も状況に応じて設定する。
	 * @param origin 比較元
	 * @param sa 比較先
	 * @param originTeam 起源チーム
	 * @param compTeam 比較チーム
	 * @return
	 */
	private List<String> compareSaMin(double origin, double sa, String originTeam, String compTeam) {
		List<String> result = new ArrayList<>();
		if (origin > sa) {
			originTeam = compTeam;
			origin = sa;
		}
		result.add(String.valueOf(origin));
		result.add(originTeam);
		return result;
	}

	/**
	 * 差分を比較し大きい方を返す。その際対象の相手チーム名も状況に応じて設定する。
	 * @param origin 比較元
	 * @param sa 比較先
	 * @param originTeam 起源チーム
	 * @param compTeam 比較チーム
	 * @return
	 */
	private List<String> compareSaMax(double origin, double sa, String originTeam, String compTeam) {
		List<String> result = new ArrayList<>();
		if (origin < sa) {
			originTeam = compTeam;
			origin = sa;
		}
		result.add(String.valueOf(origin));
		result.add(originTeam);
		return result;
	}

	/**
	 * 差分を合計する(平均用)
	 * @param origin 比較元
	 * @param sa 比較先
	 * @return
	 */
	private double sumSaAverage(double origin, double sa) {
		origin += sa;
		return origin;
	}

	/**
	 * 差分に関する件数を合計する(平均用)
	 * @param origin 比較元
	 * @param sa 比較先
	 * @return
	 */
	private int sumSaCount(Integer origin, Integer count) {
		origin += count;
		return origin;
	}

	/**
	 * オリジンを比較し小さい方を返す。その際対象の相手チーム名も状況に応じて設定する。
	 * @param origin 比較元
	 * @param da 比較先
	 * @param originTeam 起源チーム
	 * @param compTeam 比較チーム
	 * @return
	 */
	private List<String> compareMin(double origin, double da, String originTeam, String compTeam) {
		List<String> result = new ArrayList<>();
		if (origin > da) {
			originTeam = compTeam;
			origin = da;
		}
		result.add(String.valueOf(origin));
		result.add(originTeam);
		return result;
	}

	/**
	 * オリジンを比較し大きい方を返す。その際対象の相手チーム名も状況に応じて設定する。
	 * @param origin 比較元
	 * @param da 比較先
	 * @param originTeam 起源チーム
	 * @param compTeam 比較チーム
	 * @return
	 */
	private List<String> compareMax(double origin, double da, String originTeam, String compTeam) {
		List<String> result = new ArrayList<>();
		if (origin < da) {
			originTeam = compTeam;
			origin = da;
		}
		result.add(String.valueOf(origin));
		result.add(originTeam);
		return result;
	}

	/**
	 * オリジンを合計する(平均用)
	 * @param origin 比較元
	 * @param da 比較先
	 * @return
	 */
	private double sumAverage(double origin, double da) {
		origin += da;
		return origin;
	}

	/**
	 * オリジン件数を合計する(平均用)
	 * @param origin 比較元
	 * @param sa 比較先
	 * @return
	 */
	private int sumCount(Integer origin, Integer count) {
		origin += count;
		return origin;
	}

	/**
	 * 得点なしデータを抽出する
	 * @param indices
	 * @param scoreFlags
	 * @return
	 */
	private List<Integer> extractNoScoreIndices(List<Integer> indices, Boolean[] scoreFlags) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < indices.size(); i++) {
			if (!scoreFlags[i])
				result.add(indices.get(i));
		}
		return result;
	}

}
