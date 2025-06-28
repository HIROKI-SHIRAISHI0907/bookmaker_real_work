package dev.application.common.logic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dev.application.common.constant.MakeTextConst;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.UniairConst;
import dev.application.entity.ThresHoldEntity;

/**
 * 閾値調査用サブロジック
 * @author shiraishitoshio
 *
 */
public class EachFeatureSubLogic {

	/**
	 * @param entityList CSV読み込みEntityリスト
	 * @param halfList ハーフリスト
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public List<String> execute(List<ThresHoldEntity> entityList)
			throws IllegalArgumentException, IllegalAccessException, InvalidFormatException, IOException {

		// 特徴量に当たる論理名を取得
		List<String> ronriList = UniairColumnMapUtil.getWhichKeyValueMap(UniairConst.BM_M001, "連番");
		// 特徴量に当たる物理名を取得
		List<String> butsuriList = UniairColumnMapUtil.getWhichKeyValueMap(UniairConst.BM_M001, "seq");

		List<String> fileList = new ArrayList<String>();

		List<String> containsList = new ArrayList<String>();
		// entityをループし,特定のスコアの閾値試合時間を取得する
		for (ThresHoldEntity entity : entityList) {


			List<String> data_category_list = ExecuteMainUtil.getCountryLeagueByRegex(entity.getDataCategory());

			// ホーム、アウェースコア
			int home_score = Integer.parseInt(entity.getHomeScore());
			int away_score = Integer.parseInt(entity.getAwayScore());

			String scoreConnection = "" + home_score + away_score;

			// 国とリーグ
			String country = data_category_list.get(0);
			String league = data_category_list.get(1);

			// 分単位の範囲に変換した試合時間
			String game_time = entity.getTimes();
			String convert_time_range = ExecuteMainUtil.classifyMatchTime(game_time);

			// ホーム > 0, アウェー = 0
			if (home_score > 0 && away_score == 0 && !containsList.contains(scoreConnection)) {
				String book_name_suffix = country + "-" + league + "-HomeScoreFeature-HomeScore_" + home_score + "-AwayScore_" + away_score;
				fileList.add(book_name_suffix);

				executeEachFeatureMain(entity, country, league, convert_time_range,
						ronriList, butsuriList, book_name_suffix, "home");

				containsList.add(scoreConnection);
			}

			// ホーム = 0, アウェー > 0
			else if (home_score == 0 && away_score > 0 && !containsList.contains(scoreConnection)) {
				String book_name_suffix = country + "-" + league + "-AwayScoreFeature-HomeScore_" + home_score + "-AwayScore_" + away_score;
				fileList.add(book_name_suffix);

				executeEachFeatureMain(entity, country, league, convert_time_range,
						ronriList, butsuriList, book_name_suffix, "away");

				containsList.add(scoreConnection);
			}
		}

		return fileList;
	}

	/**
	 *
	 * @param entity エンティティ
	 * @param game_time
	 * @param featureMap
	 * @param convert_time
	 * @param ronriList
	 * @param butsuriList
	 * @param book_name_suffix
	 * @param data_category_list
	 * @param dataInitList
	 * @param scoredWhich1
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void executeEachFeatureMain(ThresHoldEntity entity, String country, String league,
			String convert_time, List<String> ronriList, List<String> butsuriList,
			String book_name_suffix,
			String scoredWhich1)
			throws InvalidFormatException, IOException, IllegalArgumentException, IllegalAccessException {

		// この試合時間における各ホームの特徴量とアウェーの特徴量を取得
		Field[] fields = entity.getClass().getDeclaredFields();

		// フィールドごとに処理
		Map<String, String> featureMap = new LinkedHashMap<String, String>();
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

			// 特定のフィールドの値を取得し、論理名とともにマップに格納
			String feature_value = (String) field.get(entity);
			String ronri = ExecuteMainUtil.getFeatureRonriField(snake, ronriList, butsuriList);
			featureMap.put(ronri, feature_value);
		}

		// マップに格納されているfeature_valueを整理しやすいように入れ替える
		Map<String, String> newFeatureMap = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> feaEntry : featureMap.entrySet()) {
			if (feaEntry.getValue() == null || "".equals(feaEntry.getValue()) ||
					"0".equals(feaEntry.getValue()) || "0.0".equals(feaEntry.getValue())) {
				continue;
			}
			// 3データが同一値になっている場合(成功と試行のみ取得)(34%(3/7)などの形式)
			if (ExecuteMainUtil.chkSplit(feaEntry.getKey())) {
				List<String> split_feature = ExecuteMainUtil.splitGroup(feaEntry.getValue());
				newFeatureMap.put(feaEntry.getKey() + "_成功", split_feature.get(1));
				newFeatureMap.put(feaEntry.getKey() + "_試行", split_feature.get(2));
			} else {
				newFeatureMap.put(feaEntry.getKey(), feaEntry.getValue());
			}
		}

		// newFeatureMapのvalue側を調べて閾値を調べる(具体的には切り捨てを行う)
		Map<String, Map<String, Integer>> countMap = new HashMap<>();
		for (Map.Entry<String, String> newFeaEntry : newFeatureMap.entrySet()) {
			// すでにcountMapにそのkeyがあるか確認して取得
			Map<String, Integer> countSubMap = countMap.getOrDefault(newFeaEntry.getKey(), new HashMap<>());
			// まずはその値が整数か小数かパーセンテージ表記かを調べ、閾値を調査、マップへと格納する。
			String threshold_value = ExecuteMainUtil.checkNumberTypeAndFloor(newFeaEntry.getValue());
			countSubMap.put(threshold_value, countSubMap.getOrDefault(threshold_value, 0) + 1);
			// ↓ 閾値順にソート（数値部分だけ取り出して）
			Map<String, Integer> sortedSubMap = countSubMap.entrySet()
					.stream()
					.sorted(Comparator.comparingDouble(e -> ExecuteMainUtil.extractNumericValue(e.getKey())))
					.collect(
							LinkedHashMap::new,
							(m, e) -> m.put(e.getKey(), e.getValue()),
							LinkedHashMap::putAll);
			countMap.put(newFeaEntry.getKey(), sortedSubMap);
		}

		// ソートをまとめて最後に適用（任意）
		Map<String, Map<String, Integer>> sortedCountMap = new LinkedHashMap<>();
		for (Map.Entry<String, Map<String, Integer>> entry : countMap.entrySet()) {
			Map<String, Integer> sortedSubMap = entry.getValue().entrySet()
					.stream()
					.sorted(Comparator.comparingDouble(e -> ExecuteMainUtil.extractNumericValue(e.getKey())))
					.collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()),
							LinkedHashMap::putAll);
			// 特徴量をキーに持つ、閾値とその数の組み合わせを持ったマップ完成（閾値は特徴量単位でソート）
			sortedCountMap.put(entry.getKey(), sortedSubMap);
		}

		// ブック更新(ブックsuffix名, dataInitList, 一意キー(国とカテゴリ))を引数にする
		EachFeatureBook eachFeatureBook = new EachFeatureBook(MakeTextConst.EACH_FEATURE);
		eachFeatureBook.makeBook(country, league, convert_time, 1, book_name_suffix, sortedCountMap,
				MakeTextConst.EACH_FEATURE);
	}

}
