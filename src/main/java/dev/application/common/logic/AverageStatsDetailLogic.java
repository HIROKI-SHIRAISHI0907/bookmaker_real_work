package dev.application.common.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.AverageStatisticsOutputDTO;
import dev.application.common.exception.SystemException;
import dev.application.common.mapping.FieldMapping;
import dev.application.common.mapping.StatMapping;
import dev.application.common.mapping.StatSummary;
import dev.application.common.util.ContainsCountryLeagueUtil;
import dev.application.common.util.DateUtil;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.CsvRegisterImpl;
import dev.application.db.ExistsUpdCsvInfo;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.db.UpdateWrapper;
import dev.application.entity.AverageStatisticsTeamDetailEntity;
import dev.application.entity.ThresHoldEntity;

/**
 * 平均統計データロジック
 * @author shiraishitoshio
 *
 */
public class AverageStatsDetailLogic {

	/**
	 * 件数
	 */
	private static final int COUNTER = 52;

	/**
	 * フラグ(全データ単位)
	 */
	private static final String ALL_SCORE = "1";

	/**
	 * フラグ(前半,後半単位)
	 */
	private static final String HALF_SCORE = "2";

	/**
	 * フラグ(前半)
	 */
	private static final String FIRST_HALF_SCORE = "3";

	/**
	 * フラグ(後半)
	 */
	private static final String SECOND_HALF_SCORE = "4";

	/**
	 * フラグ(分単位)
	 */
	private static final String MINUTE_SCORE = "5";

	/**
	 * フラグ(チーム全単位)
	 */
	private static final String GAME_SCORE = "6";

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
	 * 実行
	 * <p>
	 * 1.flg:ALL,team:ホームとアウェーでそれぞれ渡す(oppositeTeamはnull),ha:HorA
	 * 2.flg:GAME,team:oppositeTeamとともに渡す,ha:null
	 * 3.flg:HALF1st,team:oppositeTeamとともに渡す,ha:null
	 * 4.flg:HALF2nd,team:oppositeTeamとともに渡す,ha:null
	 * 5.flg:時間データ,team:oppositeTeamとともに渡す,ha:null
	 * </p>
	 * @param entityList
	 * @param file
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void execute(List<ThresHoldEntity> entityList, String file)
			throws IllegalArgumentException, IllegalAccessException {

		// 取得したデータリストについて最終スコアを確認する
		ThresHoldEntity returnMaxEntity = ExecuteMainUtil.getMaxSeqEntities(entityList);
		// 国,リーグ
		String[] data_category = ExecuteMainUtil.splitLeagueInfo(returnMaxEntity.getDataCategory());
		String country = data_category[0];
		String league = data_category[1];

		// 無関係データはskip
		if (!ContainsCountryLeagueUtil.containsCountryLeague(country, league)) {
			return;
		}

		// 欠け値が存在するのを防ぐため最後のデータを取得する
		ThresHoldEntity allEntityList = entityList.get(entityList.size() - 1);

		System.out.println("score: ALLSCORE");

		String home = allEntityList.getHomeTeamName();
		String away = allEntityList.getAwayTeamName();

		// そのチームの全試合の平均データ
		System.out.println("flg: ALLSCORE");

		commonCountryLeagueTeamLogic(country, league, home, null, "H",
				entityList, allEntityList, file, ALL_SCORE, null);

		commonCountryLeagueTeamLogic(country, league, away, null, "A",
				entityList, allEntityList, file, ALL_SCORE, null);

		// そのチームの各試合の平均
		System.out.println("flg: GAMESCORE");

		commonCountryLeagueTeamLogic(country, league, home, away, null,
				entityList, allEntityList, file, GAME_SCORE, null);

		// そのチームの各試合の前半後半ごとの平均
		for (int i = 1; i <= 2; i++) {
			String str = (i == 1) ? "1st" : "2nd";
			System.out.println("flg: HALF" + str + "SCORE");

			String halfFlg = (i == 1) ? FIRST_HALF_SCORE : SECOND_HALF_SCORE;

			commonCountryLeagueTeamLogic(country, league, home, away, null,
					entityList, allEntityList, file, HALF_SCORE, halfFlg);
		}

		// そのチームの各試合の時間ごとの平均
		System.out.println("flg: MINUTESCORE");

		commonCountryLeagueTeamLogic(country, league, home, away, null,
				entityList, allEntityList, file, MINUTE_SCORE, null);

		try {
			ExistsUpdCsvInfo.insert(country, league,
					UniairConst.BM_M028, "");
		} catch (Exception e) {
			System.err.println("ExistsUpdCsvInfo err: tableId = BM_M006, err: " + e);
		}
	}

	/**
	 * 共通ロジック
	 * @param country 国
	 * @param league リーグ
	 * @param team チーム
	 * @param oppositeTeam 相手チーム
	 * @param ha ホームアウェー(flgがALL_SCOREのみ使用)
	 * @param entityList entityList
	 * @param allEntityList allEntityList
	 * @param file ファイル名
	 * @param flg 導出フラグ
	 * @param halfFlg ハーフタイムフラグ
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void commonCountryLeagueTeamLogic(String country, String league, String team,
			String oppositeTeam, String ha,
			List<ThresHoldEntity> entityList, ThresHoldEntity allEntityList, String file,
			String flg, String halfFlg)
			throws IllegalArgumentException, IllegalAccessException {

		// flgに応じてsituation設定
		String situation = "";
		switch (flg) {
		// そのチームの全試合の平均(ホームアウェー無関係)
		case ALL_SCORE: {
			situation = SITUATION_ALL;
			break;
		}
		// そのチームの各試合の平均(ホームアウェー分割)
		case GAME_SCORE: {
			situation = SITUATION_GAME;
			break;
		}
		// そのチームの各試合の前半後半ごとの平均
		case HALF_SCORE: {
			situation = (FIRST_HALF_SCORE.equals(halfFlg)) ? SITUATION_HALF1st : SITUATION_HALF2nd;
			break;
		}
		// そのチームの各試合の時間ごとの平均
		case MINUTE_SCORE: {
			// ここでは設定しない
			break;
		}
		default:
			throw new IllegalArgumentException("caseエラーです: " + flg);
		}

		Map<String, List<ThresHoldEntity>> filteredAllMap = new HashMap<String, List<ThresHoldEntity>>();
		Map<String, List<Boolean>> updAllMap = new HashMap<String, List<Boolean>>();
		Map<String, List<String>> updIdMap = new HashMap<String, List<String>>();
		Map<String, List<List<String>>> updDataMap = new HashMap<String, List<List<String>>>();
		// 全試合の平均以外はホームアウェーそれぞれで取得
		if (ALL_SCORE.equals(flg)) {
			// DBに登録済みか
			AverageStatisticsOutputDTO averageStatisticsOutputHDTO = getTeamStaticsData(country, league,
					team, null, null, situation);
			// 更新フラグ取得,更新通番取得
			boolean updSubAllFlg = averageStatisticsOutputHDTO.isUpdFlg();
			String updSubId = averageStatisticsOutputHDTO.getUpdId();
			List<List<String>> allSubData = averageStatisticsOutputHDTO.getSelectList();

			// 以降のデータサイズを合わせるため,teamがホーム側ならアウェーデータを空リスト,アウェー側ならホームデータを空リスト
			// を追加する。
			List<List<String>> updAllDataList = new ArrayList<List<String>>();
			if ("H".equals(ha)) {
				updAllDataList = mixData(allSubData, new ArrayList<List<String>>(),
						allEntityList, updAllDataList);
			} else if ("A".equals(ha)) {
				updAllDataList = mixData(new ArrayList<List<String>>(), allSubData,
						allEntityList, updAllDataList);
			}

			// Map格納用リストに入れて保管
			List<Boolean> updList = new ArrayList<Boolean>();
			// updListのサイズを合わせるためfalseを入れる(使用しない)
			updList.add(false);
			updList.add(false);
			updList.add(updSubAllFlg);
			updAllMap.put(situation, updList);
			List<String> updIdList = new ArrayList<String>();
			updIdList.add(updSubId);
			updIdMap.put(situation, updIdList);
			updDataMap.put(situation, updAllDataList);

			// 新規データ用Mapに保管
			filteredAllMap.put(situation, entityList);
		} else if (MINUTE_SCORE.equals(flg)) {
			// 10分ごとのリストに分割
			Map<String, List<ThresHoldEntity>> dividedTimes = divideTimesIntoRanges(entityList);

			for (Map.Entry<String, List<ThresHoldEntity>> map : dividedTimes.entrySet()) {
				// situation設定
				situation = map.getKey();

				// DBに登録済みか(10分単位のデータをloopで確認)
				AverageStatisticsOutputDTO averageStatisticsOutputHDTO = getTeamStaticsData(country, league,
						team, oppositeTeam, "H", situation);

				// 更新フラグ取得,更新通番取得
				boolean updHomeAllFlg = averageStatisticsOutputHDTO.isUpdFlg();
				String updHomeId = averageStatisticsOutputHDTO.getUpdId();
				List<List<String>> allHomeData = averageStatisticsOutputHDTO.getSelectList();

				AverageStatisticsOutputDTO averageStatisticsOutputADTO = getTeamStaticsData(country, league,
						team, oppositeTeam, "A", situation);
				// 更新フラグ取得,更新通番取得
				boolean updAwayAllFlg = averageStatisticsOutputADTO.isUpdFlg();
				String updAwayId = averageStatisticsOutputADTO.getUpdId();
				List<List<String>> allAwayData = averageStatisticsOutputADTO.getSelectList();

				// allHomeData, allAwayDataをまとめる(DBにデータがない場合は初期化データを返す)
				List<List<String>> updDataList = new ArrayList<List<String>>();
				updDataList = mixData(allHomeData, allAwayData, allEntityList, updDataList);

				// 全体更新フラグ設定
				boolean updSubAllFlg = (updHomeAllFlg || updAwayAllFlg) ? true : false;

				// Map格納用リストに入れて保管
				List<Boolean> updList = new ArrayList<Boolean>();
				updList.add(updHomeAllFlg);
				updList.add(updAwayAllFlg);
				updList.add(updSubAllFlg);
				updAllMap.put(situation, updList);
				List<String> updIdList = new ArrayList<String>();
				updIdList.add(updHomeId);
				updIdList.add(updAwayId);
				updIdMap.put(situation, updIdList);
				updDataMap.put(situation, updDataList);
			}

			// 新規データ用Mapに保管
			filteredAllMap = dividedTimes;
		} else {
			// DBに登録済みか
			AverageStatisticsOutputDTO averageStatisticsOutputHDTO = getTeamStaticsData(country, league,
					team, oppositeTeam, "H", situation);
			// 更新フラグ取得,更新通番取得
			boolean updHomeAllFlg = averageStatisticsOutputHDTO.isUpdFlg();
			String updHomeId = averageStatisticsOutputHDTO.getUpdId();
			List<List<String>> allHomeData = averageStatisticsOutputHDTO.getSelectList();

			// DBに登録済みか
			AverageStatisticsOutputDTO averageStatisticsOutputADTO = getTeamStaticsData(country, league,
					team, oppositeTeam, "A", situation);
			// 更新フラグ取得,更新通番取得
			boolean updAwayAllFlg = averageStatisticsOutputADTO.isUpdFlg();
			String updAwayId = averageStatisticsOutputADTO.getUpdId();
			List<List<String>> allAwayData = averageStatisticsOutputADTO.getSelectList();

			// allHomeData, allAwayDataをまとめる(DBにデータがない場合は初期化データを返す)
			List<List<String>> updDataList = new ArrayList<List<String>>();
			updDataList = mixData(allHomeData, allAwayData, allEntityList, updDataList);

			// 全体更新フラグ設定
			boolean updSubAllFlg = (updHomeAllFlg || updAwayAllFlg) ? true : false;

			// Map格納用リストに入れて保管
			List<Boolean> updList = new ArrayList<Boolean>();
			updList.add(updHomeAllFlg);
			updList.add(updAwayAllFlg);
			updList.add(updSubAllFlg);
			updAllMap.put(situation, updList);
			List<String> updIdList = new ArrayList<String>();
			updIdList.add(updHomeId);
			updIdList.add(updAwayId);
			updIdMap.put(situation, updIdList);
			updDataMap.put(situation, updDataList);

			// ハーフタイムで折半用データ
			List<ThresHoldEntity> filteredList = null;
			if (HALF_SCORE.equals(flg)) {
				// ハーフタイムの通番を特定
				String halfTimeSeq = findHalfTimeSeq(entityList);
				// ハーフタイム前の試合時間のデータをフィルタリング（通番が半分より小さいもの）
				if (FIRST_HALF_SCORE.equals(halfFlg)) {
					filteredList = entityList.stream()
							.filter(entity -> entity.getSeq().compareTo(halfTimeSeq) < 0) // 通番がハーフタイムより前
							.collect(Collectors.toList());
				} else if (SECOND_HALF_SCORE.equals(halfFlg)) {
					filteredList = entityList.stream()
							.filter(entity -> entity.getSeq().compareTo(halfTimeSeq) > 0) // 通番がハーフタイムより後
							.collect(Collectors.toList());
				}
				// チームの各試合の平均
			} else if (GAME_SCORE.equals(flg)) {
				filteredList = entityList;
			}

			// 新規データ用Mapに保管
			filteredAllMap.put(situation, filteredList);
		}

		// 全体のMapが空の場合終了
		if (filteredAllMap == null || filteredAllMap.isEmpty()) {
			return;
		}

		// Mapごとにloop
		for (Map.Entry<String, List<ThresHoldEntity>> map : filteredAllMap.entrySet()) {
			// フィルターデータ
			List<ThresHoldEntity> filteredList = map.getValue();

			// 空の場合そのloopはskip
			if (filteredList == null || filteredList.isEmpty()) {
				continue;
			}

			// Mapに保管した更新フラグ,更新ID,更新前リストに応じて以降で条件分岐
			situation = map.getKey();
			List<Boolean> updFlgList = updAllMap.get(situation);
			List<String> updSeqIdList = updIdMap.get(situation);

			boolean updHomeAllFlg = updFlgList.get(0);
			boolean updAwayAllFlg = updFlgList.get(1);
			boolean updAllFlg = updFlgList.get(2);

			// 更新前データ
			List<List<String>> allData = updDataMap.get(situation);

			// 比較メソッドを呼び出し最大値,最小値を導出する。
			List<String> minData = getSplitStringData(allData, 0);
			List<String> maxData = getSplitStringData(allData, 1);
			List<FieldMapping> mappings = StatMapping.createFieldMappings();
			for (ThresHoldEntity entity : filteredList) {
				for (FieldMapping mapping : mappings) {
					int index = mapping.getIndex();
					String currentMin = minData.get(index);
					String currentMax = maxData.get(index);
					String value = mapping.getGetter().apply(entity);
					minData.set(index, compareMin(currentMin, value));
					maxData.set(index, compareMax(currentMax, value));
					//				System.out.println("entityNum: " + entityNum + ", index: " + index);
				}
			}

			List<String> aveData = new ArrayList<>();
			List<String> sigmaData = new ArrayList<>();
			List<Integer> counter = new ArrayList<>();
			aveData = getSplitStringData(allData, 2);
			sigmaData = getSplitStringData(allData, 3);
			counter = getSplitIntegerData(allData);
			// 平均*件数を計算しておく
			aveData = calcAveSum(aveData, counter);

			// 特徴量のスコア最小値データ,特徴量のスコア最大値データ
			String featureScoreMinData = null;
			String featureScoreMaxData = null;
			featureScoreMinData = getSplitFeatureStringData(allData, 5);
			featureScoreMaxData = getSplitFeatureStringData(allData, 6);

			// 特徴量のスコア平均値データ,特徴量のスコア標準偏差データ
			String featureScoreMeanData = null;
			String featureScoreSigmaData = null;
			int featureScoreCounter = 0;
			featureScoreMeanData = getSplitFeatureStringData(allData, 7);
			featureScoreSigmaData = getSplitFeatureStringData(allData, 8);
			featureScoreCounter = getSplitFeatureIntegerData(allData);
			// 平均*件数を計算しておく
			featureScoreMeanData = calcFeatureAveSum(featureScoreMeanData, featureScoreCounter);

			// 最大値,最小値を設定する(パス,ファイナルサード,クロス,タックルの表記を合わせる)
			List<StatSummary> statList = initInstance();
			for (int i = 0; i < statList.size(); i++) {
				String min = (i >= 40 && i <= 47 && !minData.get(i).contains("(")) ? minData.get(i) + " (0.0/0.0)"
						: minData.get(i);
				String max = (i >= 40 && i <= 47 && !maxData.get(i).contains("(")) ? maxData.get(i) + " (0.0/0.0)"
						: maxData.get(i);
				statList.get(i).setMin(min);
				statList.get(i).setMax(max);
			}

			for (ThresHoldEntity entity : filteredList) {
				// indexと一致するレコードの特徴量を計算対象とし平均を出すための和を導出する
				for (FieldMapping mapping : mappings) {
					int index = mapping.getIndex();
					String currentSum = aveData.get(index);
					String value = mapping.getGetter().apply(entity);
					AverageStatisticsOutputDTO outputDTO = sumOfAverage(currentSum, value,
							counter.get(index), file);
					aveData.set(index, outputDTO.getSum());
					counter.set(index, outputDTO.getCounter());
				}
			}

			// 平均を導出する(3分割データの場合それぞれで平均を導出する)
			for (int ind = 0; ind < aveData.size(); ind++) {
				if (aveData.get(ind).contains("/")) {
					List<String> connOrigin = new ArrayList<>();
					List<String> splitAveData = ExecuteMainUtil.splitGroup(aveData.get(ind));
					for (int threeind = 0; threeind < splitAveData.size(); threeind++) {
						String origin = splitAveData.get(threeind);
						if (origin.contains("%")) {
							origin = origin.replace("%", "");
						}
						if (counter.get(ind) != 0) {
							connOrigin.add(String.valueOf(Double.parseDouble(origin) / counter.get(ind)));
						} else {
							connOrigin.add("0.0");
							connOrigin.add("0.0");
							connOrigin.add("0.0");
						}
					}
					aveData.set(ind,
							String.format("%.2f", Double.parseDouble(connOrigin.get(0))) + "% ("
									+ String.format("%.2f", Double.parseDouble(connOrigin.get(1))) + "/" +
									String.format("%.2f", Double.parseDouble(connOrigin.get(2))) + ")");
				} else {
					if (counter.get(ind) != 0) {
						String remarks = "";
						String ave = aveData.get(ind);
						if (ave.contains("%")) {
							remarks = "%";
							ave = ave.replace("%", "");
						}
						aveData.set(ind,
								String.format("%.2f", (Double.parseDouble(ave) / counter.get(ind))) + remarks);
					}
				}
				//			System.out.println("ind: " + ind);
			}

			// 標準偏差を導出する
			for (ThresHoldEntity entity : filteredList) {
				for (FieldMapping mapping : mappings) {
					int index = mapping.getIndex();
					String currentSigma = sigmaData.get(index);
					String value = mapping.getGetter().apply(entity);
					AverageStatisticsOutputDTO outputDTO = sumOfSigma(currentSigma, value,
							aveData.get(index), file);
					sigmaData.set(index, outputDTO.getSigmaSum());
				}
			}
			// 3分割データの場合それぞれで標準偏差を導出する
			for (int ind = 0; ind < sigmaData.size(); ind++) {
				if (sigmaData.get(ind).contains("/")) {
					List<String> connOrigin = new ArrayList<>();
					List<String> splitSigmaData = ExecuteMainUtil.splitGroup(sigmaData.get(ind));
					for (int threeind = 0; threeind < splitSigmaData.size(); threeind++) {
						String origin = splitSigmaData.get(threeind);
						if (origin.contains("%")) {
							origin = origin.replace("%", "");
						}
						if (counter.get(ind) != 0) {
							connOrigin.add(String.valueOf(
									Math.sqrt(Double.parseDouble(origin) / counter.get(ind))));
						} else {
							connOrigin.add("0.0");
							connOrigin.add("0.0");
							connOrigin.add("0.0");
						}
					}
					sigmaData.set(ind,
							String.format("%.2f", Double.parseDouble(connOrigin.get(0))) + "% ("
									+ String.format("%.2f", Double.parseDouble(connOrigin.get(1))) + "/" +
									String.format("%.2f", Double.parseDouble(connOrigin.get(2))) + ")");
				} else {
					if (counter.get(ind) != 0) {
						String remarks = "";
						String sigma = sigmaData.get(ind);
						if (sigma.contains("%")) {
							remarks = "%";
							sigma = sigma.replace("%", "");
						}
						sigmaData.set(ind, String.format("%.2f",
								Math.sqrt(Double.parseDouble(sigma) / counter.get(ind))) + remarks);
					}
				}
			}

			// 統計データがなく,初期値から変化がなかった場合,できるだけ大きい値にする
			for (int i = 0; i < aveData.size(); i++) {
				String afMin = minData.get(i);
				String afMax = maxData.get(i);
				String afAve = aveData.get(i);
				String afSigma = sigmaData.get(i);
				if (afMin.equals("5000.00") && afMax.equals("-5000.00") &&
						(!afAve.contains("10000") || !afSigma.contains("10000"))) {
					if (afAve.contains("%")) {
						afAve = afAve.replace("0.00%", "10000.00%");
						afAve = afAve.replace("0.0% (0.0/0.0)", "10000.0% (10000.0/10000.0)");
					} else {
						afAve = afAve.replaceAll("\\b0\\.0\\b", "10000.0");
					}
					if (afSigma.contains("%")) {
						afSigma = afSigma.replace("0.00%", "10000.00%");
						afSigma = afSigma.replace("0.0% (0.0/0.0)", "10000.0% (10000.0/10000.0)");
					} else {
						afSigma = afSigma.replaceAll("\\b0\\.0\\b", "10000.0");
					}
				}
				aveData.set(i, afAve);
				sigmaData.set(i, afSigma);
			}

			//統計リストに格納(パス,ファイナルサード,クロス,タックルの表記を合わせる)
			for (int i = 0; i < statList.size(); i++) {
				String ave = (i >= 40 && i <= 47 && !aveData.get(i).contains("(")) ? aveData.get(i) + " (0.0/0.0)"
						: aveData.get(i);
				String sigma = (i >= 40 && i <= 47 && !sigmaData.get(i).contains("(")) ? sigmaData.get(i) + " (0.0/0.0)"
						: sigmaData.get(i);
				statList.get(i).setMean(ave);
				statList.get(i).setSigma(sigma);
				statList.get(i).setCount(counter.get(i));
			}

			// 特徴量の最小値,最大値を取得
			for (ThresHoldEntity entity : filteredList) {
				featureScoreMinData = timeCompareMin(featureScoreMinData, entity.getTimes());
				featureScoreMaxData = timeCompareMax(featureScoreMaxData, entity.getTimes());
			}
			featureScoreMinData = String.format("%.1f",
					Double.parseDouble(featureScoreMinData.replace("'", ""))) + "'";
			featureScoreMaxData = String.format("%.1f",
					Double.parseDouble(featureScoreMaxData.replace("'", ""))) + "'";

			// 特徴量の平均値,標準偏差を取得
			for (ThresHoldEntity entity : filteredList) {
				AverageStatisticsOutputDTO outDto = timeSumOfAverage(featureScoreMeanData,
						entity.getTimes(), featureScoreCounter);
				featureScoreMeanData = outDto.getSum();
				featureScoreCounter = outDto.getCounter();
			}
			if (featureScoreCounter != 0) {
				featureScoreMeanData = String.format("%.1f",
						Double.parseDouble(featureScoreMeanData.replace("'", "")) /
								featureScoreCounter)
						+ "'";
			}

			for (ThresHoldEntity entity : filteredList) {
				AverageStatisticsOutputDTO outDto = timeSumOfSigma(featureScoreSigmaData, entity.getTimes(),
						featureScoreMeanData);
				featureScoreSigmaData = outDto.getSigmaSum();
			}
			if (featureScoreCounter != 0) {
				featureScoreSigmaData = String.format("%.1f",
						Math.sqrt(Double.parseDouble(featureScoreSigmaData.replace("'", "")) /
								featureScoreCounter))
						+ "'";
			}

			//統計リストに格納
			for (int i = 0; i < statList.size(); i++) {
				statList.get(i).setFeatureTimeMin(featureScoreMinData);
				statList.get(i).setFeatureTimeMax(featureScoreMaxData);
				statList.get(i).setFeatureTimeMean(featureScoreMeanData);
				statList.get(i).setFeatureTimeSigma(featureScoreSigmaData);
				statList.get(i).setFeatureCount(featureScoreCounter);
			}

			// ホームデータとアウェーデータで分ける
			// 1個おきに格納するリストを2つ用意
			List<StatSummary> list1 = new ArrayList<>();
			List<StatSummary> list2 = new ArrayList<>();
			for (int i = 0; i < statList.size(); i++) {
				if (i % 2 == 0) {
					list1.add(statList.get(i)); // 偶数インデックスをlist1に格納
				} else if (i % 2 == 1) {
					list2.add(statList.get(i)); // 奇数インデックスをlist2に格納
				}
			}

			// insert(すでに登録済みの場合はupdate)
			if (ALL_SCORE.equals(flg)) {
				// ホームデータアウェーデータで格納するデータを選択
				List<StatSummary> insertList = ("H".equals(ha)) ? list1 : list2;
				String updId = updSeqIdList.get(0);
				registerTeamStaticsDetailData(country, league, team, null, situation,
						null, insertList, updAllFlg, updId);
			} else {
				String updHomeId = updSeqIdList.get(0);
				String updAwayId = updSeqIdList.get(1);
				if (!list1.isEmpty()) {
					registerTeamStaticsDetailData(country, league, team, oppositeTeam, situation,
							"H", list1, updHomeAllFlg, updHomeId);
				}
				if (!list2.isEmpty()) {
					registerTeamStaticsDetailData(country, league, team, oppositeTeam, situation,
							"A", list2, updAwayAllFlg, updAwayId);
				}
			}
		}
	}

	/**
	 * ランキング更新
	 * @param country
	 * @param league
	 */
	public void updateRankingData(String country, String league) {
		// ranking登録,更新(当初理が呼ばれた国,リーグ単位でデータ取得し,各特徴量に順位づけ)
		// ただし順位づけは同一situationで行う
		List<String> selDataAllList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M028);
		String[] selDataList = new String[selDataAllList.size()];
		for (int i = 0; i < selDataAllList.size(); i++) {
			selDataList[i] = selDataAllList.get(i);
		}

		// situationリストを作成
		String[] situationList = new String[13];
		situationList[0] = SITUATION_ALL;
		situationList[1] = SITUATION_GAME;
		situationList[2] = SITUATION_HALF1st;
		situationList[3] = SITUATION_HALF2nd;
		situationList[4] = "0〜10";
		situationList[5] = "10〜20";
		situationList[6] = "20〜30";
		situationList[7] = "30〜40";
		situationList[8] = "40〜50";
		situationList[9] = "50〜60";
		situationList[10] = "60〜70";
		situationList[11] = "70〜80";
		situationList[12] = "80〜90";

		List<String> situationSubList = new ArrayList<>();
		situationSubList.add("H");
		situationSubList.add("A");

		for (String situ : situationList) {
			String where = "country = '" + country + "' and league = '" + league + "' and "
					+ "situation = '" + situ + "'";

			for (int i = 0; i <= 1; i++) {
				// 国,リーグ, チーム名(「,」連続で結合する)-特徴量index, データ
				Map<String, Map<String, List<String>>> dataMap = new HashMap<String, Map<String, List<String>>>();

				String situSub = "";
				if (!SITUATION_ALL.equals(situ)) {
					situSub = (i == 0) ? situationSubList.get(0) : situationSubList.get(1);
					where += (" and ha = '" + situSub + "'");
				}

				int loopCount = 0;
				List<List<String>> selectResultList = null;
				SqlMainLogic select = new SqlMainLogic();
				try {
					selectResultList = select.executeSelect(null, UniairConst.BM_M028, selDataList,
							where, null, null);
				} catch (Exception e) {
					throw new SystemException("", "", "", "err");
				}

				List<String> selectTeamList = new ArrayList<>();
				if (SITUATION_ALL.equals(situ)) {
					loopCount = 1;
					// 取得したデータの中で国,リーグに所属するチーム数分を別リストに格納
				} else {
					// ホームチーム名のみ重複なく格納
					for (List<String> resultList : selectResultList) {
						String teamKey = resultList.get(2);
						if (!selectTeamList.contains(teamKey)) {
							selectTeamList.add(teamKey);
						}
					}
					loopCount = selectTeamList.size();
				}

				// situationに対応するデータが存在しない場合はskip
				if (!SITUATION_ALL.equals(situ) && selectTeamList.isEmpty()) {
					continue;
				}

				// situationごとにloop回数を決める
				int loop = 1;
				while (true) {
					// ALL以外の場合はteam単位でランキング集計
					if (!SITUATION_ALL.equals(situ)) {
						where += (" and team = '" + selectTeamList.get(loop - 1) + "'");
						try {
							selectResultList = select.executeSelect(null, UniairConst.BM_M028, selDataList,
									where, null, null);
						} catch (Exception e) {
							throw new SystemException("", "", "", "err");
						}
						where = where.replace(" and team = '" + selectTeamList.get(loop - 1) + "'", "");
					}

					for (List<String> resultList : selectResultList) {
						List<String> resultList2 = resultList.subList(7, resultList.size());
						// 国,リーグMapを作成
						String countryLeagueKey = resultList.get(5) + "-" + resultList.get(6);
						// チームをキーとして作成する(ALL: 基準チーム, それ以外: 基準チーム_相手チーム)
						String teamKey = "";
						if (SITUATION_ALL.equals(situ)) {
							teamKey = resultList.get(2);
						} else {
							teamKey = resultList.get(2) + "_" + resultList.get(3);
						}
						// teamKey が存在しなければ、新しいマップを作成
						Map<String, List<String>> innerMap = dataMap.getOrDefault(countryLeagueKey, new HashMap<>());
						dataMap.putIfAbsent(countryLeagueKey, innerMap);

						for (int j = 0; j < resultList2.size(); j++) {
							// "-j"が含まれているキーを取得
							String keyWithJ = null;
							// Mapのキーに"-j"が含まれているか調べる
							for (String key : innerMap.keySet()) {
								if (key.endsWith("-" + j)) {
									keyWithJ = key; // 含まれているキーを保存
									break; // 最初に見つけたキーを取得してループを終了
								}
							}
							// なかった場合
							List<String> befList = new ArrayList<>();
							if (keyWithJ == null) {
								keyWithJ = teamKey + "-" + j;
								befList.add(resultList2.get(j));
							} else {
								// リストを一旦取得し,チーム名を上書き
								befList = innerMap.get(keyWithJ);
								befList.add(resultList2.get(j));
								// キーを削除
								innerMap.remove(keyWithJ);
								// ハイフンで分割し,新規チーム名を入れる
								StringBuilder sb = new StringBuilder();
								String[] keySp = keyWithJ.split("-");
								int key = 0;
								for (String keyS : keySp) {
									if (sb.toString().length() > 0) {
										sb.append("-");
									}
									if (key == keySp.length - 1) {
										break;
									}
									sb.append(keyS);
									key++;
								}
								sb.append(teamKey);
								sb.append("-" + j);
								keyWithJ = sb.toString();
							}

							// 新しいMapとしてリストを作成
							innerMap.computeIfAbsent(keyWithJ, k -> new ArrayList<>()).addAll(befList);
						}
					}

					// 国,リーグ, チーム名(「,」連続で結合する)-特徴量index, データ
					// チームごとに特徴量のインデックスを基にソート
					for (Map.Entry<String, Map<String, List<String>>> entry : dataMap.entrySet()) {
						String countryLeagueName = entry.getKey(); // 国,リーグ
						Map<String, List<String>> indexMap = entry.getValue(); // 特徴量のインデックスと平均値のマップ

						// 一時的に変更を保存するマップ
						Map<String, List<String>> tempMap = new HashMap<>();

						// 各インデックス番号ごとにリストをソート
						for (Map.Entry<String, List<String>> indexEntry : indexMap.entrySet()) {
							String teams = indexEntry.getKey();
							List<String> dataList = indexEntry.getValue(); // 特徴量に対応するデータリスト

							// チーム名（例：鹿島アントラーズ-ガンバ大阪-2など）
							String[] teamSp = teams.split("-");
							String[] newTeamSp = new String[teamSp.length - 1]; //必要
							// 元の配列から最後の要素を除いてコピー
							System.arraycopy(teamSp, 0, newTeamSp, 0, teamSp.length - 1);
							// 特徴量index
							String feature_ind = teamSp[teamSp.length - 1];

							// サイズが1の時はソートの必要がない
							if (dataList.size() <= 1) {
								tempMap.put(teams, dataList); // 変更がなければそのままtempMapに格納
								continue;
							}

							boolean containsPercent = (dataList.get(0).contains("%")) ? true : false;
							String suffix = (containsPercent) ? "%" : "";

							// チーム名とそのデータをペアにしてリストに格納（チーム名とデータを一緒に並び替えるため）
							List<TeamData> teamDataList = new ArrayList<>();
							int ind = 0;
							for (String data : dataList) {
								String[] parts = data.split(",");
								String avgValue = parts[2];
								// pass, finalthirdpass, cross, tackleについては割合で比較
								if (Integer.parseInt(feature_ind) >= 20 &&
										Integer.parseInt(feature_ind) <= 23) {
									List<String> spList = ExecuteMainUtil.splitFlgGroup(avgValue);
									avgValue = spList.get(0);
								}
								avgValue = avgValue.replace("%", ""); // 3番目のデータ（平均値）
								double avg = Double.parseDouble(avgValue); // 数値に変換
								teamDataList.add(new TeamData(newTeamSp[ind], avg, data, suffix)); // チーム名とデータを保存
								ind++;
							}

							// 平均値（avg）で降順に並び替え
							teamDataList.sort((team1, team2) -> Double.compare(team2.avg, team1.avg));

							// ソート後にデータリストを再構築
							dataList.clear(); // 古いリストをクリア
							for (TeamData teamData : teamDataList) {
								String updatedData = teamData.data;
								dataList.add(updatedData); // ソートされたデータを元に戻す
							}

							// チーム名の順序もソートされた順番に変更（ハイフン区切り）
							List<String> sortedTeams = new ArrayList<>();
							for (TeamData teamData : teamDataList) {
								sortedTeams.add(teamData.teamName); // ソートされたチーム名を追加
							}

							// ソートされたチーム名をハイフンで連結して再設定(最後にfeature_indを追加)
							String sortedTeamsJoined = String.join("-", sortedTeams);
							sortedTeamsJoined += ("-" + feature_ind);
							tempMap.put(sortedTeamsJoined, dataList); // 一時的にソートされたチーム名と対応するデータリストをtempMapに再設定
						}

						// ソートされたデータをマップに再設定
						dataMap.put(countryLeagueName, tempMap);
					}

					// 文字列リスト
					Map<String, StringBuilder> sbMap = new HashMap<String, StringBuilder>();
					for (Map.Entry<String, Map<String, List<String>>> entry : dataMap.entrySet()) {
						Map<String, List<String>> indexMap = entry.getValue(); // 特徴量のインデックスと平均値のマップ

						// 鹿島-ガンバ-FC東京, [A,B,C,D.., A,B,C,D.., A,B,C,D..]
						for (Map.Entry<String, List<String>> entrys : indexMap.entrySet()) {
							String team_index = entrys.getKey(); // チーム名(「,」連続で結合する)-特徴量index
							List<String> featureList = entrys.getValue();
							// チームリスト
							String[] teamList = team_index.split("-");
							// index
							int feature_index = Integer.parseInt(teamList[teamList.length - 1]); //必要
							String[] newTeamList = new String[teamList.length - 1]; //必要
							// 元の配列から最後の要素を除いてコピー
							System.arraycopy(teamList, 0, newTeamList, 0, teamList.length - 1);

							// ここまででteamList(String[])がチーム一覧, featureList(List<String>)が特徴量データ一覧を持つ

							// 更新のための特徴量物理名を取得
							Field[] fields = new AverageStatisticsTeamDetailEntity()
									.getClass().getDeclaredFields();
							String name_field = fields[feature_index + 7].getName();
							String snake_name = ExecuteMainUtil.convertToSnakeCase(name_field);

							// 特徴量データ内の平均値が降順に並んている前提
							// 順位付け
							for (int dKey = 0; dKey < featureList.size(); dKey++) {
								// このfeatureから順にランキングが上位になる
								int rank = dKey + 1;
								String teamKey = teamList[dKey];
								String data = featureList.get(dKey);

								String afData = "";
								if (data.contains("位")) {
									String[] datas = data.split(",");
									datas[datas.length - 1] = rank + "位";
									for (String d : datas) {
										if (afData.length() > 0) {
											afData += ",";
										}
										afData += d;
									}
								} else {
									data += ("," + rank + "位");
									afData = data;
								}

								// チームキーがある場合,その要素からbuilderを取得してappend
								if (sbMap.containsKey(teamKey)) {
									StringBuilder sB = sbMap.get(teamKey);
									if (sB.toString().length() > 0) {
										sB.append(", ");
									}
									// シングルクォーテーションを文字列に変換
									afData = afData.replace("'", "''");
									sB.append(snake_name + " = '" + afData + "'");
									sbMap.put(teamKey, sB);
								} else {
									StringBuilder sB = new StringBuilder();
									// シングルクォーテーションを文字列に変換
									afData = afData.replace("'", "''");
									sB.append(snake_name + " = '" + afData + "'");
									sbMap.put(teamKey, sB);
								}
							}

						}
					}

					// 更新
					for (Map.Entry<String, StringBuilder> entry : sbMap.entrySet()) {
						// チーム
						String teamUpd = entry.getKey();
						// 特徴量データ
						StringBuilder sBuilder = entry.getValue();
						String datas = sBuilder.append(", update_time = '" + DateUtil.getSysDate() + "'").toString();

						// selList
						String[] selDatasList = new String[1];
						selDatasList[0] = "id";

						// 条件追加
						if (!SITUATION_ALL.equals(situ)) {
							String[] teamSp = teamUpd.split("_");
							where += (" and team = '" + teamSp[0] + "'");
							where += (" and opposite_team = '" + teamSp[1] + "'");
						} else {
							where += (" and team = '" + teamUpd + "'");
						}

						List<List<String>> selectResultSubList = null;
						try {
							selectResultSubList = select.executeSelect(null, UniairConst.BM_M028, selDatasList,
									where, null, "1");
						} catch (Exception e) {
							throw new SystemException("", "", "", "err");
						}

						// 条件減
						if (!SITUATION_ALL.equals(situ)) {
							String[] teamSp = teamUpd.split("_");
							where = where.replace(" and team = '" + teamSp[0] + "'", "");
							where = where.replace(" and opposite_team = '" + teamSp[1] + "'", "");
						} else {
							where = where.replace(" and team = '" + teamUpd + "'", "");
						}

						String id = selectResultSubList.get(0).get(0);

						UpdateWrapper updateWrapper = new UpdateWrapper();
						String wheres = "id = '" + id + "'";
						updateWrapper.updateExecute(UniairConst.BM_M028, wheres,
								datas);
						System.out.println("BM_M028のランキングを更新しました。country: " +
								country + ", league: " + league +
								", team: " + teamUpd + ", ha: " + situSub + ", genre: " + situ);
					}

					// ALLに関してはloop1回で終わり
					if (SITUATION_ALL.equals(situ)) {
						break;
					}

					if (loop == loopCount) {
						break;
					}

					loop++;
				}

				// 条件削除
				if (!SITUATION_ALL.equals(situ)) {
					where = where.replace(" and ha = '" + situSub + "'", "");
				}

				// ALLに関してはloop1回で終わり
				if (SITUATION_ALL.equals(situ)) {
					break;
				}
			}
		}
	}

	/**
	 * ホームデータとアウェーデータをまとめる
	 * @param allHomeData
	 * @param allAwayData
	 * @param allEntityList
	 * @param allData
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private List<List<String>> mixData(List<List<String>> allHomeData, List<List<String>> allAwayData,
			ThresHoldEntity allEntityList, List<List<String>> allData)
			throws IllegalArgumentException, IllegalAccessException {
		if (allHomeData == null) {
			allHomeData = new ArrayList<>();
		}
		if (allAwayData == null) {
			allAwayData = new ArrayList<>();
		}

		// 事前に初期データを投入しておく
		for (int i = 0; i < COUNTER; i++) {
			// 最小値,最大値,平均,標準偏差,件数の初期化
			List<String> subData = new ArrayList<>();
			if (i >= 40 && i <= 47) {
				subData.add("5000.0% (0.0/0.0)");
				subData.add("-5000.0% (0.0/0.0)");
			} else {
				subData.add("5000.0");
				subData.add("-5000.0");
			}
			int k = 0;
			Field[] fields = allEntityList.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// i に応じて k の範囲を動的に設定
				if (k != (10 + i)) { // iが0の時はk=10, iが1の時はk=11,..., iがCOUNTER-1の時はk=61
					k++;
					continue;
				}
				String feature_value = (String) field.get(allEntityList);
				// pass, finalthirdpass, cross, tackleは強制的に0.0% (0.0/0.0)の形式
				if (feature_value.contains("/") || (i >= 40 && i <= 47)) {
					subData.add("0.0% (0.0/0.0)");
					subData.add("0.0% (0.0/0.0)");
				} else if (feature_value.contains("%")) {
					subData.add("0.00%");
					subData.add("0.00%");
				} else {
					subData.add("0.0");
					subData.add("0.0");
				}
				k++;
			}
			subData.add("0");
			// 時間データ初期化
			subData.add("150'");
			subData.add("0'");
			subData.add("0'");
			subData.add("0'");
			subData.add("0");
			allData.add(subData);
		}

		for (List<String> data : allHomeData) {
			int alt = 0;
			for (String subdatas : data) {
				if (alt == 0) {
					alt++;
					continue;
				}
				List<String> subDataList = new ArrayList<>();
				// 分割してリストに変換(ただしpass, finalthirdpass, cross, tackleはXX%のみの場合,XX% (0/0)の形式)
				String[] split = subdatas.split(",");
				for (String key : split) {
					subDataList.add(key);
				}
				allData.set(2 * (alt - 1), subDataList);
				alt++;
			}

		}

		for (List<String> data : allAwayData) {
			int alt = 0;
			for (String subdatas : data) {
				if (alt == 0) {
					alt++;
					continue;
				}
				List<String> subDataList = new ArrayList<>();
				// 分割してリストに変換(ただしpass, finalthirdpass, cross, tackleはXX%のみの場合,XX% (0/0)の形式)
				String[] split = subdatas.split(",");
				for (String key : split) {
					subDataList.add(key);
				}
				allData.set(2 * (alt - 1) + 1, subDataList);
				alt++;
			}
		}

		return allData;
	}

	/**
	 * 比較して小さい方をreturn
	 * @param origin
	 * @param comp
	 * @return
	 */
	private String compareMin(String origin, String comp) {
		String remarks = "";
		// 3分割データの場合成功数が最も小さいものを比較する
		List<String> splitOrigin = null;
		List<String> splitComp = null;
		boolean threeOriFlg = false;
		boolean threeComFlg = false;
		if (origin.contains("/")) {
			splitOrigin = ExecuteMainUtil.splitGroup(origin);
			if (splitOrigin != null && splitOrigin.size() == 3) {
				origin = splitOrigin.get(1);
			}
			threeOriFlg = true;
		}
		if (comp.contains("/")) {
			splitComp = ExecuteMainUtil.splitGroup(comp);
			if (splitComp != null && splitComp.size() == 3) {
				comp = splitComp.get(1);
			}
			threeComFlg = true;
		}

		// 空文字の場合はoriginを返却
		if ("".contains(comp)) {
			if (threeOriFlg) {
				return splitOrigin.get(0) + " (" + splitOrigin.get(1)
						+ "/" + splitOrigin.get(2) + ")";
			}
			if (origin.contains("%")) {
				remarks = "%";
				origin = origin.replace("%", "");
			}
			return String.format("%.2f", Double.parseDouble(origin)) + remarks;
		}

		if (origin.contains("%")) {
			remarks = "%";
			origin = origin.replace("%", "");
		}
		if (comp.contains("%")) {
			remarks = "%";
			comp = comp.replace("%", "");
		}
		double originDouble = 0.0;
		double compDouble = 0.0;
		try {
			originDouble = Double.parseDouble(origin);
			compDouble = Double.parseDouble(comp);
		} catch (NumberFormatException e) {
			System.err.println("compareMin NumberFormatException: " + origin + ", " + comp);
			e.printStackTrace(); // ここでエラースタックトレースを表示
		} catch (Exception e) {
			System.err.println("compareMin originDouble: " + origin + ", compDouble: " + comp + ", err: " + e);
		}
		if (originDouble > compDouble) {
			origin = comp;
			if (threeComFlg) {
				return splitComp.get(0) + " (" + splitComp.get(1)
						+ "/" + splitComp.get(2) + ")";
			}
		}
		if (threeOriFlg && splitOrigin != null) {
			return splitOrigin.get(0) + " (" + splitOrigin.get(1)
					+ "/" + splitOrigin.get(2) + ")";
		}
		return String.format("%.2f", Double.parseDouble(origin)) + remarks;
	}

	/**
	 * 比較して大きい方をreturn
	 * @param origin
	 * @param comp
	 * @return
	 */
	private String compareMax(String origin, String comp) {
		String remarks = "";
		// 3分割データの場合は成功数が最も多いものを比較する
		List<String> splitOrigin = null;
		List<String> splitComp = null;
		boolean threeOriFlg = false;
		boolean threeComFlg = false;
		if (origin.contains("/")) {
			splitOrigin = ExecuteMainUtil.splitGroup(origin);
			if (splitOrigin != null && splitOrigin.size() == 3) {
				origin = splitOrigin.get(1);
			}
			threeOriFlg = true;
		}
		if (comp.contains("/")) {
			splitComp = ExecuteMainUtil.splitGroup(comp);
			if (splitComp != null && splitComp.size() == 3) {
				comp = splitComp.get(1);
			}
			threeComFlg = true;
		}

		// 空文字の場合はoriginを返却
		if ("".contains(comp)) {
			if (threeOriFlg) {
				return splitOrigin.get(0) + " (" + splitOrigin.get(1)
						+ "/" + splitOrigin.get(2) + ")";
			}
			if (origin.contains("%")) {
				remarks = "%";
				origin = origin.replace("%", "");
			}
			return String.format("%.2f", Double.parseDouble(origin)) + remarks;
		}

		if (origin.contains("%")) {
			remarks = "%";
			origin = origin.replace("%", "");
		}
		if (comp.contains("%")) {
			remarks = "%";
			comp = comp.replace("%", "");
		}
		double originDouble = 0.0;
		double compDouble = 0.0;
		try {
			originDouble = Double.parseDouble(origin);
			compDouble = Double.parseDouble(comp);
		} catch (NumberFormatException e) {
			System.err.println("compareMax NumberFormatException: " + origin + ", " + comp);
			e.printStackTrace(); // ここでエラースタックトレースを表示
		} catch (Exception e) {
			System.err.println("compareMax originDouble: " + origin + ", compDouble: " + comp + ", err: " + e);
		}
		if (originDouble < compDouble) {
			origin = comp;
			if (threeComFlg) {
				return splitComp.get(0) + " (" + splitComp.get(1)
						+ "/" + splitComp.get(2) + ")";
			}
		}
		if (threeOriFlg && splitOrigin != null) {
			return splitOrigin.get(0) + " (" + splitOrigin.get(1)
					+ "/" + splitOrigin.get(2) + ")";
		}
		return String.format("%.2f", Double.parseDouble(origin)) + remarks;
	}

	/**
	 * 比較して小さい方をreturn
	 * @param origin
	 * @param comp
	 * @param format
	 * @return
	 */
	private String timeCompareMin(String origin, String comp) {
		double origins = ExecuteMainUtil.convertToMinutes(origin);
		double comps = ExecuteMainUtil.convertToMinutes(comp);
		// シングルクウォーテーションを付与して返却
		if (origins > comps) {
			origins = comps;
		}
		return String.valueOf(origins) + "'";
	}

	/**
	 * 比較して大きい方をreturn
	 * @param origin
	 * @param comp
	 * @param format
	 * @return
	 */
	private String timeCompareMax(String origin, String comp) {
		double origins = ExecuteMainUtil.convertToMinutes(origin);
		double comps = ExecuteMainUtil.convertToMinutes(comp);
		// シングルクウォーテーションを付与して返却
		if (origins < comps) {
			origins = comps;
		}
		return String.valueOf(origins) + "'";
	}

	/**
	 * 数字を合計する(値がない場合は何もしない)
	 * @param origin
	 * @param comp
	 * @return
	 */
	private AverageStatisticsOutputDTO sumOfAverage(String origin, String comp,
			int count, String file) {
		AverageStatisticsOutputDTO dto = new AverageStatisticsOutputDTO();
		String remarks = "";
		List<String> splitOrigin = null;
		List<String> splitComp = null;
		if ((origin == null || "".equals(origin)) ||
				(comp == null || "".equals(comp))) {
			dto.setSum(origin);
			dto.setCounter(count);
		} else {
			boolean oriThreeFlg = false;
			boolean comThreeFlg = false;
			if (origin.contains("/")) {
				//System.out.println("/ origin in: " + origin);
				splitOrigin = ExecuteMainUtil.splitGroup(origin);
				oriThreeFlg = true;
			}
			if (comp.contains("/")) {
				//System.out.println("/ comp in: " + comp);
				splitComp = ExecuteMainUtil.splitGroup(comp);
				comThreeFlg = true;
			}
			if (oriThreeFlg && comThreeFlg) {
				List<String> connOrigin = new ArrayList<>();
				for (int threeind = 0; threeind < splitOrigin.size(); threeind++) {
					String sigmaOrigin = splitOrigin.get(threeind);
					if (sigmaOrigin.contains("%")) {
						sigmaOrigin = sigmaOrigin.replace("%", "");
					}
					String sigmaComp = splitComp.get(threeind);
					if (sigmaComp.contains("%")) {
						sigmaComp = sigmaComp.replace("%", "");
					}
					double originDouble = Double.parseDouble(sigmaOrigin);
					double compDouble = Double.parseDouble(sigmaComp);
					connOrigin.add(String.valueOf(originDouble + compDouble));
				}
				//System.out.println("three sumOfAverage chk: " + connOrigin);
				origin = String.valueOf(connOrigin.get(0))
						+ "% (" + String.valueOf(connOrigin.get(1)) + "/" +
						String.valueOf(connOrigin.get(2)) + ")";
				dto.setSum(origin);
				dto.setCounter(count + 1);
			} else if (oriThreeFlg && !comThreeFlg) {
				dto.setSum(splitOrigin.get(0) + " (" + splitOrigin.get(1) + "/" + splitOrigin.get(2) + ")");
				dto.setCounter(count);
			} else if (!oriThreeFlg && comThreeFlg) {
				dto.setSum(splitComp.get(0) + " (" + splitComp.get(1) + "/" + splitComp.get(2) + ")");
				dto.setCounter(count + 1);
			} else {
				if (origin.contains("%")) {
					remarks = "%";
					origin = origin.replace("%", "");
				}
				if (comp.contains("%")) {
					remarks = "%";
					comp = comp.replace("%", "");
				}
				double originDouble = 0.0;
				double compDouble = 0.0;
				try {
					originDouble = Double.parseDouble(origin);
					compDouble = Double.parseDouble(comp);
					dto.setSum(String.valueOf(originDouble + compDouble) + remarks);
					if ("0.0".equals(comp) || "0".equals(comp)) {
						dto.setCounter(count);
					} else {
						dto.setCounter(count + 1);
					}
				} catch (NumberFormatException e) {
					System.err.println("sumOfAverage NumberFormatException: " + origin + ", " + comp);
					e.printStackTrace(); // ここでエラースタックトレースを表示
					dto.setSum(origin + remarks);
					dto.setCounter(count);
				} catch (Exception e) {
					System.err
							.println("sumOfAverage originDouble: " + origin + ", "
									+ "compDouble: " + comp + ", err: " + e);
					dto.setSum(origin + remarks);
					dto.setCounter(count);
				}
			}
		}
		return dto;
	}

	/**
	 * 数字を合計する(値がない場合は何もしない)
	 * @param origin
	 * @param comp
	 * @return
	 */
	private AverageStatisticsOutputDTO timeSumOfAverage(String origin, String comp,
			int count) {
		AverageStatisticsOutputDTO dto = new AverageStatisticsOutputDTO();
		double origins = ExecuteMainUtil.convertToMinutes(origin);
		double comps = ExecuteMainUtil.convertToMinutes(comp);
		if (comps == 0.0) {
			dto.setSum(String.valueOf(origins) + "'");
			dto.setCounter(count);
		} else {
			dto.setSum(String.valueOf(origins + comps) + "'");
			dto.setCounter(count + 1);
		}
		return dto;
	}

	/**
	 * 数字から平均を引いた2乗を合計する(値がない場合は何もしない)
	 * @param origin
	 * @param comp
	 * @param ave
	 * @return
	 */
	private AverageStatisticsOutputDTO sumOfSigma(String origin, String comp, String ave, String file) {
		AverageStatisticsOutputDTO dto = new AverageStatisticsOutputDTO();
		List<String> splitOrigin = null;
		List<String> splitComp = null;
		List<String> splitAve = null;
		boolean oriThreeFlg = false;
		boolean comThreeFlg = false;
		boolean aveThreeFlg = false;
		String remarks = "";
		if ((origin == null || "".equals(origin)) ||
				(comp == null || "".equals(comp))) {
			dto.setSigmaSum(origin);
		} else {
			if (origin.contains("/")) {
				//System.out.println("/ origin in: " + origin);
				splitOrigin = ExecuteMainUtil.splitGroup(origin);
				oriThreeFlg = true;
			}
			if (comp.contains("/")) {
				//System.out.println("/ comp in: " + comp);
				splitComp = ExecuteMainUtil.splitGroup(comp);
				comThreeFlg = true;
			}
			if (ave.contains("/")) {
				//System.out.println("/ ave in: " + ave);
				splitAve = ExecuteMainUtil.splitGroup(ave);
				aveThreeFlg = true;
			}
			if (oriThreeFlg && comThreeFlg && aveThreeFlg) {
				List<String> connOrigin = new ArrayList<>();
				for (int threeind = 0; threeind < splitOrigin.size(); threeind++) {
					String sigmaOrigin = splitOrigin.get(threeind);
					if (sigmaOrigin.contains("%")) {
						sigmaOrigin = sigmaOrigin.replace("%", "");
					}
					String sigmaComp = splitComp.get(threeind);
					if (sigmaComp.contains("%")) {
						sigmaComp = sigmaComp.replace("%", "");
					}
					String sigmaAve = splitAve.get(threeind);
					if (sigmaAve.contains("%")) {
						sigmaAve = sigmaAve.replace("%", "");
					}
					double originDouble = 0.0;
					double compDouble = 0.0;
					double aveDouble = 0.0;
					try {
						originDouble = Double.parseDouble(sigmaOrigin);
						compDouble = Double.parseDouble(sigmaComp);
						aveDouble = Double.parseDouble(sigmaAve);
						double result = originDouble += Math.pow((compDouble - aveDouble), 2);
						connOrigin.add(String.valueOf(result));
					} catch (NumberFormatException e) {
						System.err.println("sumOfSigma NumberFormatException: " + sigmaOrigin + ", " + sigmaComp + ", "
								+ sigmaAve);
						e.printStackTrace(); // ここでエラースタックトレースを表示
					} catch (Exception e) {
						System.err.println("sumOfSigma originDouble: " + sigmaOrigin + ", "
								+ "compDouble: " + sigmaComp + ", "
								+ "aveDouble: " + aveDouble + ", err: " + e);
					}
				}
				//System.out.println("three sumOfSigma chk: " + connOrigin);
				if (!connOrigin.isEmpty()) {
					origin = String.valueOf(connOrigin.get(0))
							+ "% (" + String.valueOf(connOrigin.get(1)) + "/" +
							String.valueOf(connOrigin.get(2)) + ")";
				}
				dto.setSigmaSum(origin);
			} else if (comThreeFlg && aveThreeFlg) {
				List<String> connOrigin = new ArrayList<>();
				for (int threeind = 0; threeind < splitComp.size(); threeind++) {
					String sigmaComp = splitComp.get(threeind);
					if (sigmaComp.contains("%")) {
						sigmaComp = sigmaComp.replace("%", "");
					}
					String sigmaAve = splitAve.get(threeind);
					if (sigmaAve.contains("%")) {
						sigmaAve = sigmaAve.replace("%", "");
					}
					double compDouble = 0.0;
					double aveDouble = 0.0;
					try {
						compDouble = Double.parseDouble(sigmaComp);
						aveDouble = Double.parseDouble(sigmaAve);
						connOrigin.add(String.valueOf(Math.pow((compDouble - aveDouble), 2)));
					} catch (NumberFormatException e) {
						System.err.println("sumOfSigma NumberFormatException: " + sigmaComp + ", " + aveDouble);
						e.printStackTrace(); // ここでエラースタックトレースを表示
					} catch (Exception e) {
						System.err.println("sumOfSigma compDouble: " + sigmaComp + ", "
								+ "aveDouble: " + aveDouble + ", err: " + e);
					}
				}
				//System.out.println("two sumOfSigma chk: " + connOrigin);
				if (!connOrigin.isEmpty()) {
					origin = String.valueOf(connOrigin.get(0))
							+ "% (" + String.valueOf(connOrigin.get(1)) + "/" +
							String.valueOf(connOrigin.get(2)) + ")";
				}
				dto.setSigmaSum(origin);
				// 3分割データと単一データが混じっている場合,基本的に単一データ側は成功数と試行数が不明であるパターンが多いため集計しない
			} else if (oriThreeFlg && !comThreeFlg) {
				//System.out.println("sumOfSigma skipします: " + origin + ", " + comp);
				dto.setSigmaSum(origin);
			} else {
				if (origin.contains("%")) {
					remarks = "%";
					origin = origin.replace("%", "");
				}
				if (comp.contains("%")) {
					remarks = "%";
					comp = comp.replace("%", "");
				}
				if (ave.contains("%")) {
					remarks = "%";
					ave = ave.replace("%", "");
				}
				double originDouble = 0.0;
				double compDouble = 0.0;
				double aveDouble = 0.0;
				try {
					originDouble = Double.parseDouble(origin);
					compDouble = Double.parseDouble(comp);
					aveDouble = Double.parseDouble(ave);
					originDouble += Math.pow((compDouble - aveDouble), 2);
					dto.setSigmaSum(String.valueOf(originDouble) + remarks);
				} catch (NumberFormatException e) {
					System.err.println("sumOfSigma NumberFormatException: " + origin + ", " + comp + ", " + ave);
					e.printStackTrace(); // ここでエラースタックトレースを表示
					dto.setSigmaSum(origin + remarks);
				} catch (Exception e) {
					System.err.println("sumOfSigma originDouble: " + origin + ", "
							+ "compDouble: " + comp + ", "
							+ "aveDouble: " + ave + ", err: " + e);
					dto.setSigmaSum(origin + remarks);
				}
			}
		}
		return dto;
	}

	/**
	 * 数字から平均を引いた2乗を合計する(値がない場合は何もしない)
	 * @param origin
	 * @param comp
	 * @param ave
	 * @return
	 */
	private AverageStatisticsOutputDTO timeSumOfSigma(String origin, String comp,
			String ave) {
		AverageStatisticsOutputDTO dto = new AverageStatisticsOutputDTO();
		double origins = ExecuteMainUtil.convertToMinutes(origin);
		double comps = ExecuteMainUtil.convertToMinutes(comp);
		double aves = ExecuteMainUtil.convertToMinutes(ave);
		if (comps == 0.0) {
			dto.setSigmaSum(String.valueOf(origins) + "'");
		} else {
			dto.setSigmaSum(String.valueOf(origins + Math.pow((comps - aves), 2)) + "'");
		}
		return dto;
	}

	/**
	 * インスタンス化する
	 * @param stat
	 * @return
	 */
	private List<StatSummary> initInstance() {
		List<StatSummary> statList = new ArrayList<>();
		for (int i = 0; i < COUNTER; i++) {
			statList.add(new StatSummary(null, null, null, null, 0, null, null, null, null, 0));
		}
		return statList;
	}

	/**
	 * 登録メソッド
	 * @param country 国
	 * @param league リーグ
	 * @param team チーム
	 * @param oppositeTeam 相手チーム
	 * @param situation 単位
	 * @param ha ホームアウェー
	 * @param statList データリスト
	 * @param updFlg 更新フラグ
	 * @param seq 通番
	 */
	private void registerTeamStaticsDetailData(String country, String league, String team,
			String oppositeTeam, String situation,
			String ha, List<StatSummary> statList, boolean updFlg, String id) {
		if (updFlg) {
			List<String> selectList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M028);
			String[] selDataList = new String[selectList.size()];
			for (int i = 0; i < selectList.size(); i++) {
				selDataList[i] = selectList.get(i);
			}
			// 1つずつstatListの値を取得する
			List<String> stat = collectStatSummaryValues(statList);
			StringBuilder sBuilder = new StringBuilder();
			for (int ind = 5; ind < COUNTER / 2 + 5; ind++) {
				if (sBuilder.toString().length() > 0) {
					sBuilder.append(", ");
				}
				String sta = stat.get(ind - 5);
				sta = sta.replace("'", "''");
				sBuilder.append(" " + selDataList[ind + 2] + " = '" + sta + "'");
			}
			sBuilder.append(", update_time = '" + DateUtil.getSysDate() + "'");
			UpdateWrapper updateWrapper = new UpdateWrapper();

			String where = "id = '" + id + "'";
			updateWrapper.updateExecute(UniairConst.BM_M028, where,
					sBuilder.toString());
			System.out.println("BM_M028を更新しました。country: " + country + ", league: " + league +
					", team: " + team + ", ha: " + ha);
		} else {
			List<AverageStatisticsTeamDetailEntity> insertEntities = new ArrayList<AverageStatisticsTeamDetailEntity>();
			AverageStatisticsTeamDetailEntity statSummaries = new AverageStatisticsTeamDetailEntity();
			statSummaries.setSituation(situation);
			statSummaries.setTeam(team);
			statSummaries.setOppositeTeam(oppositeTeam);
			statSummaries.setHa(ha);
			statSummaries.setCountry(country);
			statSummaries.setLeague(league);
			statSummaries.setTeam(team);
			statSummaries.setExpStat(statList.get(0)); // インデックス0: ExpStat
			statSummaries.setDonationStat(statList.get(1)); // インデックス2: DonationStat
			statSummaries.setShootAllStat(statList.get(2)); // インデックス4: ShootAllStat
			statSummaries.setShootInStat(statList.get(3)); // インデックス6: ShootInStat
			statSummaries.setShootOutStat(statList.get(4)); // インデックス8: ShootOutStat
			statSummaries.setBlockShootStat(statList.get(5)); // インデックス10: BlockShootStat
			statSummaries.setBigChanceStat(statList.get(6)); // インデックス12: BigChanceStat
			statSummaries.setCornerStat(statList.get(7)); // インデックス14: CornerStat
			statSummaries.setBoxShootInStat(statList.get(8)); // インデックス16: BoxShootInStat
			statSummaries.setBoxShootOutStat(statList.get(9)); // インデックス18: BoxShootOutStat
			statSummaries.setGoalPostStat(statList.get(10)); // インデックス20: GoalPostStat
			statSummaries.setGoalHeadStat(statList.get(11)); // インデックス22: GoalHeadStat
			statSummaries.setKeeperSaveStat(statList.get(12)); // インデックス24: KeeperSaveStat
			statSummaries.setFreeKickStat(statList.get(13)); // インデックス26: FreeKickStat
			statSummaries.setOffsideStat(statList.get(14)); // インデックス28: OffsideStat
			statSummaries.setFoulStat(statList.get(15)); // インデックス30: FoulStat
			statSummaries.setYellowCardStat(statList.get(16)); // インデックス32: YellowCardStat
			statSummaries.setRedCardStat(statList.get(17)); // インデックス34: RedCardStat
			statSummaries.setSlowInStat(statList.get(18)); // インデックス36: SlowInStat
			statSummaries.setBoxTouchStat(statList.get(19)); // インデックス38: BoxTouchStat
			statSummaries.setPassCountStat(statList.get(20)); // インデックス40: PassCountStat
			statSummaries.setFinalThirdPassCountStat(statList.get(21)); // インデックス42: FinalThirdPassCountStat
			statSummaries.setCrossCountStat(statList.get(22)); // インデックス44: CrossCountStat
			statSummaries.setTackleCountStat(statList.get(23)); // インデックス46: TackleCountStat
			statSummaries.setClearCountStat(statList.get(24)); // インデックス48: ClearCountStat
			statSummaries.setInterceptCountStat(statList.get(25)); // インデックス50: InterceptCountStat
			insertEntities.add(statSummaries);

			CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
			try {
				csvRegisterImpl.executeInsert(UniairConst.BM_M028,
						insertEntities, 1, insertEntities.size());
			} catch (Exception e) {
				System.err.println("average_statistics_data_team_detail insert err execute: " + e);
			}
			System.out.println("BM_M028に登録しました。country: " + country + ", league: " + league +
					", team: " + team + ", ha: " + ha);
		}
	}

	/**
	 * 取得メソッド
	 * @param country 国
	 * @param league リーグ
	 * @param team チーム
	 * @param oppoTeam 相手チーム
	 * @param ha ホームアウェー
	 * @param situation 単位
	 */
	private AverageStatisticsOutputDTO getTeamStaticsData(String country, String league, String team,
			String oppoTeam, String ha, String situation) {
		List<String> selDataAllList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M028);
		String[] selDataList = new String[selDataAllList.size() - 6];
		selDataList[0] = "id";
		for (int i = 7; i < selDataAllList.size(); i++) {
			selDataList[i - 6] = selDataAllList.get(i);
		}

		String where = "country = '" + country + "' and league = '" + league + "'";
		if (team != null) {
			where += (" and team = '" + team + "'");
		}
		if (oppoTeam != null) {
			where += (" and opposite_team = '" + oppoTeam + "'");
		}
		if (ha != null) {
			where += (" and ha = '" + ha + "'");
		}
		if (situation != null) {
			where += (" and situation = '" + situation + "'");
		}

		List<List<String>> selectResultList = null;
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M028, selDataList,
					where, null, "1");
		} catch (Exception e) {
			throw new SystemException("", "", "", "err");
		}

		AverageStatisticsOutputDTO averageStatisticsOutputDTO = new AverageStatisticsOutputDTO();
		averageStatisticsOutputDTO.setUpdFlg(false);

		if (!selectResultList.isEmpty()) {
			averageStatisticsOutputDTO.setUpdFlg(true);
			averageStatisticsOutputDTO.setUpdId(selectResultList.get(0).get(0));
			averageStatisticsOutputDTO.setSelectList(selectResultList);
		}
		return averageStatisticsOutputDTO;
	}

	/**
	 * 統計データをリストにして返却する
	 * @param statSummaries
	 * @return
	 */
	private List<String> collectStatSummaryValues(List<StatSummary> statSummaries) {
		// 新しいリストを作成
		List<String> statValues = new ArrayList<>();
		for (StatSummary stat : statSummaries) {
			statValues.add(connectData(stat));
		}
		return statValues;
	}

	/**
	 * 統計データを連結する
	 * @param summary
	 * @return
	 */
	private String connectData(StatSummary summary) {
		return summary.getMin() + "," + summary.getMax() + "," +
				summary.getMean() + "," + summary.getSigma() + "," +
				String.valueOf(summary.getCount()) + "," +
				summary.getFeatureTimeMin() + "," + summary.getFeatureTimeMax() +
				"," + summary.getFeatureTimeMean() + "," + summary.getFeatureTimeSigma() +
				"," + String.valueOf(summary.getFeatureCount());
	}

	/**
	 * 統計データをリストにして返却する
	 * @param statSummaries
	 * @return
	 */
	private List<String> getSplitStringData(List<List<String>> list, int kankaku) {
		List<String> statValues = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			List<String> currentList = list.get(i);
			String value = currentList.get(kankaku); // kankaku番目の要素を取り出す
			statValues.add(value); // 抜き出した値を新しいリストに追加
		}
		return statValues;
	}

	/**
	 * 統計データを変数にして返却する
	 * @param statSummaries
	 * @return
	 */
	private String getSplitFeatureStringData(List<List<String>> list, int kankaku) {
		String statValues = null;
		for (List<String> da : list) {
			statValues = da.get(kankaku);
			break;
		}
		return statValues;
	}

	/**
	 * 統計データをリストにして返却する
	 * @param statSummaries
	 * @return
	 */
	private List<Integer> getSplitIntegerData(List<List<String>> list) {
		List<Integer> statValues = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			List<String> currentList = list.get(i);
			Integer value = Integer.parseInt(currentList.get(4)); // kankaku番目の要素を取り出す
			statValues.add(value); // 抜き出した値を新しいリストに追加
		}
		return statValues;
	}

	/**
	 * 統計データを変数にして返却する
	 * @param statSummaries
	 * @return
	 */
	private Integer getSplitFeatureIntegerData(List<List<String>> list) {
		Integer statValues = -1;
		for (List<String> da : list) {
			statValues = Integer.parseInt(da.get(9));
			break;
		}
		return statValues;
	}

	/**
	 * 平均*件数を計算し,リストにして返却する
	 * @param statSummaries
	 * @return
	 */
	private List<String> calcAveSum(List<String> exStrList, List<Integer> exIntList) {
		int ind = 0;
		for (String str : exStrList) {
			String result = "";
			String remarks = "";
			if (str.contains("/")) {
				List<String> splitData = ExecuteMainUtil.splitGroup(str);
				List<String> statValues = new ArrayList<>();
				for (String split : splitData) {
					String subRemarks = "";
					if (split.contains("%")) {
						subRemarks = "%";
						split = split.replace("%", "");
					}
					result = String.valueOf(Double.parseDouble(split) *
							exIntList.get(ind)) + subRemarks;
					statValues.add(result);
				}
				result = String.valueOf(statValues.get(0) + " (" + statValues.get(1) + "/"
						+ statValues.get(2) + ")");
			} else if (str.contains("%")) {
				remarks = "%";
				str = str.replace("%", "");
				result = String.valueOf(Double.parseDouble(str) * exIntList.get(ind)) + remarks;
			} else {
				result = String.valueOf(Double.parseDouble(str) * exIntList.get(ind));
			}
			exStrList.set(ind, result);
			ind++;
		}
		return exStrList;
	}

	/**
	 * 平均*件数を計算し,変数にして返却する
	 * @param statSummaries
	 * @return
	 */
	private String calcFeatureAveSum(String exStr, Integer exInt) {
		String statValues = String.format("%.1f",
				(Double.parseDouble(exStr.replace("'", "")) * exInt)) + "'";
		return statValues;
	}

	/**
	 * ハーフタイムの通番を特定するメソッド
	 * @param entityList レコードのリスト
	 * @return ハーフタイムの通番
	 */
	private String findHalfTimeSeq(List<ThresHoldEntity> entityList) {
		// 通番が最も大きいレコードの時がハーフタイムだと仮定
		// もしくは、最初に見つかるハーフタイム通番があればそれを返す
		for (ThresHoldEntity entity : entityList) {
			if (BookMakersCommonConst.FIRST_HALF_TIME.equals(entity.getTimes()) ||
					BookMakersCommonConst.HALF_TIME.equals(entity.getTimes())) {
				return entity.getSeq();
			}
		}
		// もしハーフタイムが見つからなければ、エラーやデフォルト値を返す（ケースに応じて）
		return "-1"; // エラー値（ハーフタイムが見つからない場合）
	}

	/**
	 * 10分ごとに分割するリストを作成する
	 * @param entityList
	 * @return
	 */
	private static Map<String, List<ThresHoldEntity>> divideTimesIntoRanges(List<ThresHoldEntity> entityList) {
		// 結果を格納するマップ
		Map<String, List<ThresHoldEntity>> timeRanges = new TreeMap<>();

		// entityListをループ
		for (ThresHoldEntity entity : entityList) {
			// 試合時間を取得
			double timeInMinutes = ExecuteMainUtil.convertToMinutes(entity.getTimes());

			// 90分を超える場合は処理を終了
			if (timeInMinutes > 90.0) {
				break;
			}

			// 10分単位で範囲を決定 (丸める)
			int startRange = (int) (timeInMinutes / 10) * 10; // 時間を10で割った後、10の倍数に丸める
			int endRange = startRange + 10; // 10分後

			// 範囲の文字列キーを作成
			String rangeKey = startRange + "〜" + endRange;

			// 時間範囲がマップにない場合、リストを作成
			timeRanges.putIfAbsent(rangeKey, new ArrayList<>());
			// 該当する時間範囲に試合を追加
			timeRanges.get(rangeKey).add(entity);
		}

		return timeRanges;
	}
}
