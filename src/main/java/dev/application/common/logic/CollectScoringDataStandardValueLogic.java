package dev.application.common.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TIMEデータに対して得点が生まれている時間帯ごとの特徴量における最低閾値集計ロジック
 * およびその分析ロジック（平均、標準偏差、クラスタリング、得点率）
 */
public class CollectScoringDataStandardValueLogic {

	/**
	 * 実行メソッド
	 * @param mainTeamKey
	 * @param feature
	 * @param headerList
	 * @param bodyList
	 */
	public void execute(String mainTeamKey, String feature,
			List<String> headerList, List<List<String>> bodyList) {

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
			if (values == null || values.isEmpty()) continue;

			List<Double> filtered = values.stream().filter(v -> v != 0.0).collect(Collectors.toList());
			if (filtered.isEmpty()) continue;

			System.out.println("▼ 時間帯: " + time);
			for (double threshold : thresholds) {
				int total = 0, scored = 0;
				for (double v : filtered) {
					total++;
					if (v >= threshold) scored++;
				}
				double rate = (total == 0) ? 0.0 : (scored * 100.0 / total);
				System.out.printf("　閾値 %.2f 以上: %d / %d 得点率 %.2f%%%n", threshold, scored, total, rate);
			}
		}

		// 時間帯別：得点率分析（以下）
		System.out.println("\n===== 閾値ごとの得点率分析（以下・時間帯別） =====");
		for (String time : sortedTimes) {
			List<Double> values = flatFeatureValuesByTime.get(time);
			if (values == null || values.isEmpty()) continue;

			List<Double> filtered = values.stream().filter(v -> v != 0.0).collect(Collectors.toList());
			if (filtered.isEmpty()) continue;

			System.out.println("▼ 時間帯: " + time);
			for (double threshold : thresholds) {
				int total = 0, scored = 0;
				for (double v : filtered) {
					total++;
					if (v <= threshold) scored++;
				}
				double rate = (total == 0) ? 0.0 : (scored * 100.0 / total);
				System.out.printf("　閾値 %.2f 以下: %d / %d 得点率 %.2f%%%n", threshold, scored, total, rate);
			}
		}
	}
}
