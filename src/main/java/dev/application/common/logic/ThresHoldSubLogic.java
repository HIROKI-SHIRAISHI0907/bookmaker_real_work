package dev.application.common.logic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.exception.BusinessException;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.UniairConst;
import dev.application.entity.ThresHoldEntity;

/**
 * 集計用サブロジック
 * @author shiraishitoshio
 *
 */
public class ThresHoldSubLogic {

	/**
	 * 特徴量ホームアウェー組み合わせサイズ
	 */
	private static final Integer FEATURE_HOME_AWAY_PAIR_INTEGER = 44;

	/**
	 * リストサイズ
	 */
	private static final Integer LIST_SIZE_INTEGER = 3;

	/**
	 * 特徴量に関するリストサイズ
	 */
	private static final Integer FEATURE_SIZE_INTEGER = 4;

	/**
	 * @param entityList CSV読み込みEntityリスト
	 * @param halfList ハーフリスト
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void execute(List<ThresHoldEntity> entityList)
			throws IllegalArgumentException, IllegalAccessException, InvalidFormatException, IOException {

		// 特徴量に当たる論理名を取得
		List<String> ronriList = UniairColumnMapUtil.getWhichKeyValueMap(UniairConst.BM_M001, "連番");
		// 特徴量に当たる物理名を取得
		List<String> butsuriList = UniairColumnMapUtil.getWhichKeyValueMap(UniairConst.BM_M001, "seq");

		// データ格納初期リスト
		String[][][][] dataInitList = new String[FEATURE_HOME_AWAY_PAIR_INTEGER][LIST_SIZE_INTEGER][LIST_SIZE_INTEGER][FEATURE_SIZE_INTEGER];

		// 格納済みフラグ
		boolean chkOverHome = false;
		boolean chkOverAway = false;
		boolean chkOverBoth = false;

		List<String> chk_data_list = new ArrayList<String>();

		// entityをループし,特定のスコアの閾値試合時間を取得する
		Map<String, String> featureMap = new LinkedHashMap<String, String>();
		for (ThresHoldEntity entity : entityList) {
			List<String> data_category_list = ExecuteMainUtil.getCountryLeagueByRegex(entity.getDataCategory());

			int home_score = Integer.parseInt(entity.getHomeScore());
			int away_score = Integer.parseInt(entity.getAwayScore());

			String game_time = entity.getTimes();

			// ホーム = 0, アウェー = 0 (得点が今後入る際のハーフタイム時点のみ取得)
			if (home_score == 0 && away_score == 0) {

				if (BookMakersCommonConst.HALF_TIME.equals(game_time) ||
						BookMakersCommonConst.FIRST_HALF_TIME.equals(game_time)) {

					boolean home_scored_first_flg = false;
					boolean away_scored_first_flg = false;

					String sub_time_file_name = "";
					for (ThresHoldEntity subEntity : entityList) {
						String sub_game_time = subEntity.getTimes();

						sub_time_file_name = ExecuteMainUtil.classifyMatchTime(sub_game_time);

						int home_sub_score = Integer.parseInt(subEntity.getHomeScore());
						int away_sub_score = Integer.parseInt(subEntity.getAwayScore());

						if (home_sub_score > 0 && away_sub_score == 0) {
							home_scored_first_flg = true;
							break;
						} else if (home_sub_score == 0 && away_sub_score > 0) {
							away_scored_first_flg = true;
							break;
						}
					}

					String book_name_suffix = "";
					if (home_scored_first_flg) {
						book_name_suffix = "HalfTimeData-HomeScoreFirst-" + sub_time_file_name;

						if (chk_data_list.contains(book_name_suffix)) {
							continue;
						}

						executeThreadHoldMain(entity, game_time, featureMap,
								ronriList, butsuriList, book_name_suffix,
								data_category_list, dataInitList, "home", "away");

						chk_data_list.add(book_name_suffix);
					} else if (away_scored_first_flg) {
						book_name_suffix = "HalfTimeData-AwayScoreFirst-" + sub_time_file_name;

						if (chk_data_list.contains(book_name_suffix)) {
							continue;
						}

						executeThreadHoldMain(entity, game_time, featureMap,
								ronriList, butsuriList, book_name_suffix,
								data_category_list, dataInitList, "away", "home");

						chk_data_list.add(book_name_suffix);
					}
				}
			}

			String time_file_name = ExecuteMainUtil.classifyMatchTime(game_time);

			// ホーム > 0, アウェー = 0
			if (home_score > 0 && away_score == 0 && !chkOverHome) {
				String book_name_suffix = "HomeScore-" + home_score +
						"-AwayScore-" + away_score + "-" + time_file_name;

				executeThreadHoldMain(entity, game_time, featureMap,
						ronriList, butsuriList, book_name_suffix,
						data_category_list, dataInitList, "home", "away");

				chkOverHome = true;
			}

			// ホーム = 0, アウェー > 0
			else if (home_score == 0 && away_score > 0 && !chkOverAway) {
				String book_name_suffix = "HomeScore-" + home_score +
						"-AwayScore-" + away_score + "-" + time_file_name;

				executeThreadHoldMain(entity, game_time, featureMap,
						ronriList, butsuriList, book_name_suffix,
						data_category_list, dataInitList, "away", "home");

				chkOverAway = true;
			}

			// ホーム > 0, アウェー > 0
			else if (home_score > 0 && away_score > 0 && !chkOverBoth) {

				if (home_score > away_score) {
					String book_name_suffix = "HomeScore-" + home_score +
							"-AwayScore-" + away_score + "-" + time_file_name;

					executeThreadHoldMain(entity, game_time, featureMap,
							ronriList, butsuriList, book_name_suffix,
							data_category_list, dataInitList, "home", "away");

				} else if (away_score > home_score) {
					String book_name_suffix = "HomeScore-" + home_score +
							"-AwayScore-" + away_score + "-" + time_file_name;

					executeThreadHoldMain(entity, game_time, featureMap,
							ronriList, butsuriList, book_name_suffix,
							data_category_list, dataInitList, "away", "home");
				}

				chkOverBoth = true;
			}

		}
	}

	/**
	 *
	 * @param entity エンティティ
	 * @param game_time
	 * @param featureMap
	 * @param ronriList
	 * @param butsuriList
	 * @param book_name_suffix
	 * @param data_category_list
	 * @param dataInitList
	 * @param scoredWhich1
	 * @param scoredWhich2
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void executeThreadHoldMain(ThresHoldEntity entity, String game_time, Map<String, String> featureMap,
			List<String> ronriList, List<String> butsuriList,
			String book_name_suffix, List<String> data_category_list, String[][][][] dataInitList,
			String scoredWhich1, String scoredWhich2)
			throws InvalidFormatException, IOException, IllegalArgumentException, IllegalAccessException {
		// この試合時間における各ホームの特徴量とアウェーの特徴量を取得
		Field[] fields = entity.getClass().getDeclaredFields();

		// フィールドごとに処理
		for (Field field : fields) {
			field.setAccessible(true);
			// フィールド名とフィールド内の値取得
			String feature_name = field.getName();
			// フィールド名をスネーク方式に変更
			String snake = ExecuteMainUtil.convertToSnakeCase(feature_name);

			// 除去リストに入っていたらスキップ
			if (ExecuteMainUtil.chkExclusive(snake)) {
				continue;
			}

			// 特徴量に当たる論理名を取得
			String feature_value = (String) field.get(entity); // 特定のフィールドの値を取得
			featureMap.put(snake, feature_value);
		}

		// dataInitListに情報を格納
		int feature_int = 0;
		do {
			Map<String, String> sameFieldMap = new LinkedHashMap<String, String>();
			for (Map.Entry<String, String> maps : featureMap.entrySet()) {
				String snake = maps.getKey();
				String feature_value = maps.getValue();
				sameFieldMap.put(snake, feature_value);
				if ("home".equals(scoredWhich1)) {
					if (snake.startsWith("home")) {
						snake = snake.replaceFirst("home", "");
						snake = "away" + snake;
					} else if (snake.startsWith("away")) {
						snake = snake.replaceFirst("away", "");
						snake = "home" + snake;
					}
				} else if ("away".equals(scoredWhich1)) {
					if (snake.startsWith("away")) {
						snake = snake.replaceFirst("away", "");
						snake = "home" + snake;
					} else if (snake.startsWith("home")) {
						snake = snake.replaceFirst("home", "");
						snake = "away" + snake;
					}
				}
				// ホームを一時的に削除し, そのフィールド名を含むものがある場合（アウェーに該当するもの）対象
				String subSnake = "";
				for (Map.Entry<String, String> submaps : featureMap.entrySet()) {
					subSnake = submaps.getKey();
					String sub_feature_value = submaps.getValue();
					if (subSnake.equals(snake)) {
						sameFieldMap.put(snake, sub_feature_value);
						break;
					}
				}

				if (sameFieldMap.size() != 2) {
					throw new BusinessException("", "", "",
							snake + ":" + subSnake + "ホームとアウェーの組み合わせのフィールドがマッピングされていません。");
				}

				// Iteratorを使ってMapの1番目と2番目の要素を取得
				Iterator<Map.Entry<String, String>> iterator = sameFieldMap.entrySet().iterator();
				// 1番目の要素
				String homeRonri = "";
				String home_feature_value = "";
				boolean homeSplitFlg = false;
				if (iterator.hasNext()) {
					Map.Entry<String, String> entry1 = iterator.next();
					homeRonri = ExecuteMainUtil.getFeatureRonriField(entry1.getKey(), ronriList, butsuriList);
					home_feature_value = entry1.getValue();

					if (ExecuteMainUtil.chkSplit(homeRonri)) {
						homeSplitFlg = true;
					}
				}
				// 2番目の要素
				String awayRonri = "";
				String away_feature_value = "";
				boolean awaySplitFlg = false;
				if (iterator.hasNext()) {
					Map.Entry<String, String> entry2 = iterator.next();
					awayRonri = ExecuteMainUtil.getFeatureRonriField(entry2.getKey(), ronriList, butsuriList);
					away_feature_value = entry2.getValue();

					if (ExecuteMainUtil.chkSplit(awayRonri)) {
						awaySplitFlg = true;
					}
				}

				// 特定のカラムは割合,試行,成功に三分割する
				if (homeSplitFlg && awaySplitFlg) {
					List<String> home_split_feature_value = ExecuteMainUtil.splitGroup(home_feature_value);
					List<String> away_split_feature_value = ExecuteMainUtil.splitGroup(away_feature_value);
					for (int i = 0; i < home_split_feature_value.size(); i++) {
						if (i == 0) {
							homeRonri += "_割合";
							awayRonri += "_割合";
						} else if (i == 1) {
							homeRonri += "_成功";
							awayRonri += "_成功";
						} else if (i == 2) {
							homeRonri += "_試行";
							awayRonri += "_試行";
						}
						if ("home".equals(scoredWhich1)) {
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][0] = homeRonri + "_得点取った側";
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][1] = awayRonri + "_得点取られた側";
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][2] = home_split_feature_value.get(i); // フィールドの値を格納
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][3] = away_split_feature_value.get(i); // フィールドの値を格納
						} else if ("away".equals(scoredWhich1)) {
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][0] = awayRonri + "_得点取った側";
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][1] = homeRonri + "_得点取られた側";
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][2] = away_split_feature_value.get(i); // フィールドの値を格納
							dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
									.parseInt(entity.getAwayScore())][3] = home_split_feature_value.get(i); // フィールドの値を格納
						}
						if (i == 0) {
							homeRonri = homeRonri.replace("_割合", "");
							awayRonri = awayRonri.replace("_割合", "");
						} else if (i == 1) {
							homeRonri = homeRonri.replace("_成功", "");
							awayRonri = awayRonri.replace("_成功", "");
						} else if (i == 2) {
							homeRonri = homeRonri.replace("_試行", "");
							awayRonri = awayRonri.replace("_試行", "");
						}
						feature_int++;
					}
				} else {
					home_feature_value = (home_feature_value.endsWith("."))
							? home_feature_value.substring(0, home_feature_value.length() - 1)
							: home_feature_value;
					away_feature_value = (away_feature_value.endsWith("."))
							? away_feature_value.substring(0, away_feature_value.length() - 1)
							: away_feature_value;
					if ("home".equals(scoredWhich1)) {
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][0] = homeRonri + "_得点取った側";
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][1] = awayRonri + "_得点取られた側";
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][2] = home_feature_value; // フィールドの値を格納
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][3] = away_feature_value; // フィールドの値を格納
					} else if ("away".equals(scoredWhich1)) {
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][0] = awayRonri + "_得点取った側";
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][1] = homeRonri + "_得点取られた側";
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][2] = away_feature_value; // フィールドの値を格納
						dataInitList[feature_int][Integer.parseInt(entity.getHomeScore())][Integer
								.parseInt(entity.getAwayScore())][3] = home_feature_value; // フィールドの値を格納
					}
				}
				feature_int++;
				break;
			}

			for (Map.Entry<String, String> delMap : sameFieldMap.entrySet()) {
				featureMap.remove(delMap.getKey());
			}
		} while (featureMap.size() > 1);

		String[][] dataConvList = ExecuteMainUtil.convertDataList(
				Integer.parseInt(entity.getHomeScore()),
				Integer.parseInt(entity.getAwayScore()),
				dataInitList);

		// ブック更新(ブックsuffix名, dataInitList, 一意キー(国とカテゴリ))を引数にする
		GetScoreThresholdBook getScoreThresholdBook = new GetScoreThresholdBook();
		getScoreThresholdBook.makeBook(book_name_suffix, game_time, data_category_list, dataConvList);
	}

}
