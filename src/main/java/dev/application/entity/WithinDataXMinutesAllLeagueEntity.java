package dev.application.entity;

import lombok.Data;

/**
 * within_data_xminutes_all_leagueデータEntity
 * @author shiraishitoshio
 *
 */
@Data
public class WithinDataXMinutesAllLeagueEntity {

	/** 試合時間範囲 */
	private String timeRange;

	/** 特徴量 */
	private String feature;

	/** 閾値 */
	private String thresHold;

	/** 該当数 */
	private String target;

	/** 探索数 */
	private String search;

	/** 割合 */
	private String ratio;

}
