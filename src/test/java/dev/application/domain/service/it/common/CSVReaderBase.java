package dev.application.domain.service.it.common;

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
	 * ヘッダー
	 */
	protected List<String> headerList = new ArrayList<>();

	/**
	 * ボディ
	 */
	protected List<List<String>> bodyList = new ArrayList<>();


	/**
	 * CSVファイルを読み込む
	 * @param filePath ファイルパス
	 */
	public void readCSV(String filePath) {
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

	}

	/**
	 * @return getHeaderList
	 */
	public List<String> getHeaderList() {
		return this.headerList;
	}

	/**
	 * @return getBodyList
	 */
	public List<List<String>> getBodyList() {
		return this.bodyList;
	}
}
