package dev.application.domain.service.it.common;

import java.util.List;

/**
 * CSV結果返却リスト
 * @author shiraishitoshio
 *
 */
public class CSVResult {

	/** ヘッダーリスト */
	private List<String> headerList;

	/** ボディリスト */
	private List<List<String>> bodyList;

	/**
	 * コンストラクタ
	 * @param headerList
	 * @param bodyList
	 */
	public CSVResult(List<String> headerList, List<List<String>> bodyList) {
		this.headerList = headerList;
		this.bodyList = bodyList;
	}

	public List<String> getHeaderList() {
		return headerList;
	}

	public List<List<String>> getBodyList() {
		return bodyList;
	}
}
