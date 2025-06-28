package dev.application.common.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dev.application.common.book.edit.MaxFilter;
import dev.application.common.book.edit.MaxFilterWithLeague;
import dev.application.common.book.edit.SortText;
import dev.application.common.book.edit.SortTextWithLeague;
import dev.application.common.book.read.FolderOperation;
import dev.application.common.constant.MakeTextConst;
import dev.application.common.exception.BusinessException;
import dev.application.common.file.MakeText;

/**
 * get_score_thresholdブックを作成する
 * @author shiraishitoshio
 *
 */
public class WithInTimeBook {

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
	public WithInTimeBook(String file_checker) {
		// ヘッダー文字列を連結する
		StringBuilder stringBuilder = new StringBuilder();
		if (MakeTextConst.WITHIN_TIME_EACH.equals(file_checker)) {
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
		} else if (MakeTextConst.WITHIN_TIME_ALL.equals(file_checker)) {
			// 試合時間範囲,特徴量,閾値,該当数,対象数,割合
			stringBuilder
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
	 * @throws InterruptedException
	 */
	public void makeBook(String country, String league, String game_time, Integer count, String book_name_suffix,
			Map<String, Map<String, Integer>> count_map, String file_checker)
			throws InvalidFormatException, IOException, InterruptedException {
		// ファイル名
		String file_name = book_name_suffix + "_each_feature.txt";
//		// ブックをsuffixをつけて作成
//		String make_sub_text = PATH + "result/" + country + "-" + league + "/" + file_checker + "/";
//		// フォルダ作成
//		FolderOperation.makeFolder(make_sub_text);
//		// バージョン情報が最大のものを取得
//		String ver_info = FolderOperation.getMaxVersionPath(make_sub_text);
//		// オリジンパスを生成
//		String make_origin_text = make_sub_text + ver_info + "/";
//		// フォルダ作成
//		FolderOperation.makeFolder(make_origin_text);
//		// パスを生成
//		String make_text = make_origin_text + file_name;
		String make_text = PATH + file_name;
		System.out.println("make_text: " + make_text);

		// ファイルの存在を確認し,存在したら次のバージョン情報番号に変換したパスを取得
		boolean exFileFlg = FolderOperation.isTxtFileExist(make_text, ".txt");
//		String new_make_text = null;
//		if (exFileFlg) {
//			String new_origin_make_text = FolderOperation.getNextVersionPath(make_text);
//			// フォルダ作成
//			FolderOperation.makeFolder(new_origin_make_text);
//			// パスを生成
//			new_make_text = new_origin_make_text + "/" + file_name;
//		}

		System.out.println("make_text: " + make_text);
		//System.out.println("new_make_text: " + new_make_text);
		System.out.println("country: " + country);
		System.out.println("league: " + league);
		System.out.println("time: " + game_time);

		//String copypath = PATH + file_name;

		// ファイルが存在していたら,次のバージョン情報番号に変換したパスに古いバージョン情報のファイルと絡めて追加
		if (!exFileFlg) {
			// 追加するだけ
			addHeaders(make_text);
			addRecords(country, league, game_time,
					make_text, file_checker, count, count_map);
			// コピー
			//Files.copy(Paths.get(make_text), Paths.get(copypath), StandardCopyOption.REPLACE_EXISTING);

			// 1つ前のバージョン情報を持つファイルが存在する
		} else {
			// 1つ前のバージョン情報のファイルを読み込み,新規の格納先に格納する
			//addHeaders(make_text);
			updateRecords(country, league, game_time,
					make_text, file_checker, count, count_map); // ここで処理を実行
			if (MakeTextConst.WITHIN_TIME_EACH.equals(file_checker)) {
				MaxFilterWithLeague.execute(make_text);
				SortTextWithLeague.execute(make_text, file_checker);
			} else if (MakeTextConst.WITHIN_TIME_ALL.equals(file_checker)) {
				MaxFilter.execute(make_text);
				SortText.execute(make_text, file_checker);
			}
			//Thread.sleep(1000);
			// コピー
			//Files.copy(Paths.get(new_make_text), Paths.get(copypath), StandardCopyOption.REPLACE_EXISTING);
		}
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
				if (MakeTextConst.WITHIN_TIME_EACH.equals(file_checker)) {
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
				} else if (MakeTextConst.WITHIN_TIME_ALL.equals(file_checker)) {
					stringBuilder
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
	 * @param filePath データを読み込むファイルのパス
	 * @param new_filePath データを新規追加するファイルのパス
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
				if (MakeTextConst.WITHIN_TIME_EACH.equals(file_checker)) {
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
				} else if (MakeTextConst.WITHIN_TIME_ALL.equals(file_checker)) {
					stringBuilder
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
			//MakeText.updateRecord(filePath, new_filePath, recordList, file_checker);
			MakeText.updateRecord(filePath, recordList, file_checker);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"レコードの更新に失敗しました。:" + filePath + ":" + e);
		}
	}
}
