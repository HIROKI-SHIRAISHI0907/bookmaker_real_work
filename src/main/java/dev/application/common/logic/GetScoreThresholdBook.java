package dev.application.common.logic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dev.application.common.book.read.ReadExcel;
import dev.application.common.exception.BusinessException;
import dev.application.common.file.MakeExcel;

/**
 * get_score_thresholdブックを作成する
 * @author shiraishitoshio
 *
 */
public class GetScoreThresholdBook {

	/**
	 * 国ヘッダ
	 */
	private String countryHeader = "国";

	/**
	 * リーグヘッダ
	 */
	private String leagueHeader = "リーグ";

	/**
	 * 試合時間ヘッダ
	 */
	private String timeHeader = "試合時間";
	/**
	 * 国
	 */
	private String country = null;

	/**
	 * リーグ
	 */
	private String league = null;

	/**
	 * 試合時間
	 */
	private String time = null;

	/**
	 * ブック作成源流パス
	 */
	private static final String PATH = "/Users/shiraishitoshio/bookmaker/python_analytics/";

	/**
	 * ブックを作成する
	 * @param sheet_name ブックsuffix名
	 * @param game_time 試合時間
	 * @param data_category_list 国,カテゴリ(一意な組み合わせとして必要)
	 * @param dataConvList 登録データ
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void makeBook(String book_name_suffix, String game_time, List<String> data_category_list,
			String[][] dataConvList) throws InvalidFormatException, IOException {
		// ブックをsuffixをつけて作成
		String make_book = PATH + "get_score_threshold_" + book_name_suffix + ".xlsx";

		// 国,カテゴリ,試合時間をメンバ変数に登録する
		this.country = data_category_list.get(0);
		this.league = data_category_list.get(1);
		this.time = game_time;

		// ブックが作成されていない場合
		if (!isExcelFileExist(make_book)) {
			try {
				// ブックとヘッダーを作成する
				MakeExcel.createExcelFile(make_book);
				addHeaders(make_book, dataConvList);
			} catch (Exception e) {
				throw new BusinessException("", "", "",
						"エクセルファイルの作成に失敗しました。:" + make_book + ":" + e);
			}
		}

		// エクセルを読み込み,国とリーグの行を探索する
		ReadExcel readExcel = new ReadExcel();
		boolean existsFlg = true;
		// 少なくとも1つ存在しない場合は新規レコード追加対象
		int row = readExcel.searchInExcel(make_book, country, league);
		if (row == -1) {
			existsFlg = false;
		}

		// 存在するかしないかで新規レコードを作成,既存レコードの更新で分ける
		if (!existsFlg) {
			addRecords(make_book, dataConvList);
		} else {
			updateRecords(make_book, dataConvList, row);
		}

	}

	/**
	 * エクセルファイルが指定されたパスに存在するかどうかをチェック
	 *
	 * @param filePath チェックするファイルのパス
	 * @return ファイルが存在する場合は true, 存在しない場合は false
	 */
	private static boolean isExcelFileExist(String filePath) {
		File file = new File(filePath);

		// ファイルが存在するか、かつ拡張子が .xlsx または .xls の場合
		if (file.exists() && (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls"))) {
			return true;
		}
		return false;
	}

	/**
	 * ヘッダーを構成するデータを作成する
	 * @param filePath ヘッダーを追加するファイルのパス
	 * @param dataConvList ヘッダー登録データ
	 */
	private void addHeaders(String filePath, String[][] dataConvList) {
		// dataConvListからヘッダーに該当する部分を取得する
		int feature_size = dataConvList.length;
		String[][] headerList = new String[feature_size][2];
		for (int feature_int = 0; feature_int < feature_size; feature_int++) {
			headerList[feature_int][0] = dataConvList[feature_int][0];
			headerList[feature_int][1] = dataConvList[feature_int][1];
		}

		try {
			MakeExcel.addHeader(filePath, countryHeader, leagueHeader, timeHeader, headerList);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"ヘッダーの作成に失敗しました。:" + filePath + ":" + e);
		}
	}

	/**
	 * データ列を構成するデータを作成する
	 * @param filePath データを新規追加するファイルのパス
	 * @param dataConvList 登録データ
	 */
	private void addRecords(String filePath, String[][] dataConvList) {
		// dataConvListからデータに該当する部分を取得する
		int feature_size = dataConvList.length;
		String[][] dataList = new String[feature_size][2];
		for (int feature_int = 0; feature_int < feature_size; feature_int++) {
			dataList[feature_int][0] = dataConvList[feature_int][2];
			dataList[feature_int][1] = dataConvList[feature_int][3];
		}

		try {
			MakeExcel.addRecord(filePath, country, league, time, dataList);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"レコードの追加に失敗しました。:" + filePath + ":" + e);
		}
	}

	/**
	 * データ列を構成するデータを作成する(更新)
	 * @param filePath 既存データを更新するファイルのパス
	 * @param dataConvList 更新データ
	 * @param row 更新対象行
	 */
	private void updateRecords(String filePath, String[][] dataConvList, Integer row) {
		// dataConvListからデータに該当する部分を取得する
		int feature_size = dataConvList.length;
		String[][] dataList = new String[feature_size][2];
		for (int feature_int = 0; feature_int < feature_size; feature_int++) {
			dataList[feature_int][0] = dataConvList[feature_int][2];
			dataList[feature_int][1] = dataConvList[feature_int][3];
		}

		try {
			MakeExcel.updateRecord(filePath, country, league, time, dataList, row);
		} catch (Exception e) {
			throw new BusinessException("", "", "",
					"レコードの更新に失敗しました。:" + filePath + ":" + e);
		}
	}
}
