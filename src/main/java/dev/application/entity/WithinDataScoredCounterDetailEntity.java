package dev.application.entity;

import lombok.Data;

/**
 * within_data_xminutesデータEntity
 * @author shiraishitoshio
 *
 */
@Data
public class WithinDataScoredCounterDetailEntity {

	/** ID */
	private String id;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** ホームスコア */
	private String homeScoreValue;

	/** アウェースコア */
	private String awayScoreValue;

	/** ホーム試合時間範囲 */
	private String homeTimeRangeArea;

	/** アウェー試合時間範囲 */
	private String awayTimeRangeArea;

	/** 該当数 */
	private String target;

	/** 探索数 */
	private String search;

	/** 割合 */
	private String ratio;

}
