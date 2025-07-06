package dev.application.common.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVreader基底クラス
 * @author shiraishitoshio
 *
 */
public abstract class CSVReaderBase {

	/**
	 * CSVファイルを読み込む
	 * @param filePath ファイルパス
	 */
	public CSVResult readCSV(String filePath) {
		/**
		 * ヘッダー
		 */
		List<String> headerList = new ArrayList<>();

		/**
		 * ボディ
		 */
		List<List<String>> bodyList = new ArrayList<>();


		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isFirstLine = true;

			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1); // 空要素も含める
				List<String> row = new ArrayList<>();

				for (String value : values) {
					row.add(value.trim());
				}

				if (isFirstLine) {
					headerList = row; // ヘッダー格納
					isFirstLine = false;
				} else {
					bodyList.add(row); // データ格納
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new CSVResult(headerList, bodyList);
	}
}
