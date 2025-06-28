package dev.application.entity;

import lombok.Data;

/**
 * collect_range_scoreテーブルentity
 * @author shiraishitoshio
 *
 */
@Data
public class CollectRangeScoreEntity {

	/** Id */
	private String id;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** キーフラグ */
	private String keyFlg;

	/** チーム */
	private String team;

	/** 相手チーム */
	private String oppositeTeam;

	/** 0〜10 */
	private String time0_10;

	/** 10〜20 */
	private String time10_20;

	/** 20〜30 */
	private String time20_30;

	/** 30〜40 */
	private String time30_40;

	/** 40〜45 */
	private String time40_45;

	/** 45〜50 */
	private String time45_50;

	/** 50〜60 */
	private String time50_60;

	/** 60〜70 */
	private String time60_70;

	/** 70〜80 */
	private String time70_80;

	/** 80〜90 */
	private String time80_90;

	/** 90〜100 */
	private String time90_100;

}
