package dev.application.common.logic;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dev.application.common.book.edit.MaxFilterWithLeague;
import dev.application.common.book.edit.SortTextWithLeague;
import dev.application.common.constant.MakeTextConst;
import dev.application.common.exception.BusinessException;
import dev.application.common.file.MakeText;

/**
 * get_score_thresholdブックを作成する
 * @author shiraishitoshio
 *
 */
public class EachFeatureBook {

	/**
	 * 国ヘッダ
	 */
	private String countryHeader = "国";

	/**
	 * リーグヘッダ
	 */
	private String leagueHeader = "リーグ";

	/**
	 * 試合時間範囲ヘッダ
	 */
	private String timeHeader = "試合時間範囲";

	/**
	 * 特徴量ヘッダ
	 */
	private String featureHeader = "特徴量";

	/**
	 * 閾値ヘッダ
	 */
	private String thresHoldHeader = "閾値";

	/**
	 * 該当数ヘッダ
	 */
	private String targetHeader = "該当数";

	/**
	 * 探索数ヘッダ
	 */
	private String countHeader = "探索数";

	/**
	 * 割合ヘッダ
	 */
	private String ratioHeader = "割合";

	/**
	 * ヘッダ
	 */
	private String header = null;

	/**
	 * ブック作成源流パス
	 */
	private static final String PATH = "/Users/shiraishitoshio/bookmaker/python_analytics/";

	/**
	 * コンストラクタ
	 * @param file_checker 作成ファイルチェッカー
	 */
	public EachFeatureBook(String file_checker) {
		// ヘッダー文字列を連結する
		StringBuilder stringBuilder = new StringBuilder();
		if (MakeTextConst.EACH_FEATURE.equals(file_checker)) {
			// 国,リーグ,試合時間範囲,特徴量,閾値,該当数,対象数,割合
			stringBuilder.append(countryHeader)
					.append(",")
					.append(leagueHeader)
					.append(",")
					.append(timeHeader)
					.append(",")
					.append(featureHeader)
					.append(",")
					.append(thresHoldHeader)
					.append(",")
					.append(targetHeader)
					.append(",")
					.append(countHeader)
					.append(",")
					.append(ratioHeader);
		}
		this.header = stringBuilder.toString();
	}

	/**
	 * ブックを作成する
	 * @param country 国
	 * @param league リーグ
	 * @param game_time 試合時間
	 * @param count 探索数
	 * @param book_name_suffix ブック名suffix
	 * @param count_map 特徴量ごとの各閾値をキーを持つその閾値の個数(それぞれの個数は最大1)
	 * @param file_checker 作成ファイルチェッカー
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void makeBook(String country, String league, String game_time, Integer count, String book_name_suffix,
			Map<String, Map<String, Integer>> count_map, String file_checker)
			throws InvalidFormatException, IOException {
		// ブックをsuffixをつけて作成
		String make_text = PATH + book_name_suffix + "_each_feature.txt";

		System.out.println("make_text: " + make_text);
		System.out.println("country: " + country);
		System.out.println("league: " + league);
		System.out.println("time: " + game_time);

		// テキストが作成されていない場合
		boolean existsFlg = isTxtFileExist(make_text);
		System.out.println("isTxtFileExist: " + existsFlg);
		if (!existsFlg) {
			try {
				// ブックとヘッダーを作成する
				MakeText.createTextFile(make_text);
				addHeaders(make_text);

				// ファイルが書き込まれるまで最大5秒待機
				int retries = 5;
				while (Files.exists(Paths.get(make_text)) && Files.size(Paths.get(make_text)) == 0 && retries > 0) {
					Thread.sleep(500); // 0.5秒待機
					retries--;
				}
			} catch (Exception e) {
				throw new BusinessException("", "", "",
						"テキストファイルの作成に失敗しました。:" + make_text + ":" + e);
			}
		}

		// テキストファイルが存在するかしないかで新規レコードを作成,既存レコードの更新で分ける
		if (!existsFlg) {
			addRecords(country, league, game_time,
					make_text, file_checker, count, count_map);
		} else {
			// ファイルのロックを使用（必要に応じて）
			try (FileChannel channel = FileChannel.open(
					Paths.get(make_text), StandardOpenOption.READ)) {
				FileLock lock = channel.lock(0, Long.MAX_VALUE, true); // 共有ロック
				try {
					updateRecords(country, league, game_time,
							make_text, file_checker, count, count_map); // ここで処理を実行
					MaxFilterWithLeague.execute(make_text);
					// ソートする
					SortTextWithLeague.execute(make_text, file_checker);
				} finally {
					lock.release();
				}
			}
		}
	}

	/**
	 * テキストファイルが指定されたパスに存在するかどうかをチェック
	 *
	 * @param filePath チェックするファイルのパス
	 * @return ファイルが存在する場合は true, 存在しない場合は false
	 */
	private static boolean isTxtFileExist(String filePath) {
		File file = new File(filePath);

		// ファイルが存在するか、かつ拡張子が .txt または .xls の場合
		if (file.exists() && (file.getName().endsWith(".txt"))) {
			return true;
		}
		return false;
	}

	/**
	 * ヘッダーを構成するデータを作成する
	 * @param filePath ヘッダーを追加するファイルのパス
	 * @param file_checker 作成ファイルチェッカー
	 */
	public void addHeaders(String filePath) {
		try {
			MakeText.addHeader(filePath, header);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"ヘッダーの作成に失敗しました。:" + filePath + ":" + e);
		}
	}

	/**
	 * データ列を構成するデータを作成する
	 * @param country 国
	 * @param league リーグ
	 * @param game_time 試合時間
	 * @param filePath データを新規追加するファイルのパス
	 * @param file_checker 作成ファイルチェッカー
	 * @param count 探索数
	 * @param count_map 特徴量ごとの各閾値をキーを持つその閾値の個数(それぞれの個数は最大1)
	 */
	private void addRecords(String country, String league, String game_time,
			String filePath, String file_checker, Integer count,
			Map<String, Map<String, Integer>> count_map) {
		List<String> recordList = new ArrayList<String>();

		for (Map.Entry<String, Map<String, Integer>> mainEntry : count_map.entrySet()) {
			for (Map.Entry<String, Integer> subEntry : mainEntry.getValue().entrySet()) {
				// 割合を求める
				float ratio = ((float) subEntry.getValue() / count) * 100;
				StringBuilder stringBuilder = new StringBuilder();
				if (MakeTextConst.EACH_FEATURE.equals(file_checker)) {
					stringBuilder.append(country)
							.append(",")
							.append(league)
							.append(",")
							.append(game_time)
							.append(",")
							.append(mainEntry.getKey())
							.append(",")
							.append(subEntry.getKey())
							.append(",")
							.append(subEntry.getValue())
							.append(",")
							.append(count)
							.append(",")
							.append(String.format("%.1f", ratio) + "%");
				}
				recordList.add(stringBuilder.toString());
			}
		}

//		System.out.println("addRecord: " + filePath);
//		System.out.println("recordList: " + recordList.get(0));
//		System.out.println("file_checker: " + file_checker);
		try {
			MakeText.addRecord(filePath, recordList);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"レコードの追加に失敗しました。:" + filePath + ":" + e);
		}
	}

	/**
	 * データ列を構成するデータを作成する(更新)
	 * @param country 国
	 * @param league リーグ
	 * @param game_time 試合時間
	 * @param filePath データを新規追加するファイルのパス
	 * @param file_checker 作成ファイルチェッカー
	 * @param count 探索数
	 * @param count_map 特徴量ごとの各閾値をキーを持つその閾値の個数(それぞれの個数は最大1)
	 */
	private void updateRecords(String country, String league, String game_time,
			String filePath, String file_checker, Integer count,
			Map<String, Map<String, Integer>> count_map) {
		List<String> recordList = new ArrayList<String>();

		for (Map.Entry<String, Map<String, Integer>> mainEntry : count_map.entrySet()) {
			for (Map.Entry<String, Integer> subEntry : mainEntry.getValue().entrySet()) {
				// 割合を求める
				float ratio = ((float) subEntry.getValue() / count) * 100;
				StringBuilder stringBuilder = new StringBuilder();
				if (MakeTextConst.EACH_FEATURE.equals(file_checker)) {
					stringBuilder.append(country)
							.append(",")
							.append(league)
							.append(",")
							.append(game_time)
							.append(",")
							.append(mainEntry.getKey())
							.append(",")
							.append(subEntry.getKey())
							.append(",")
							.append(subEntry.getValue())
							.append(",")
							.append(count)
							.append(",")
							.append(String.format("%.1f", ratio) + "%");
				}
				recordList.add(stringBuilder.toString());
			}
		}

//		System.out.println("updateRecord: " + filePath);
//		System.out.println("recordList: " + recordList.get(0));
//		System.out.println("file_checker: " + file_checker);
		try {
			MakeText.updateRecord(filePath, recordList, file_checker);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"レコードの更新に失敗しました。:" + filePath + ":" + e);
		}
	}
}
