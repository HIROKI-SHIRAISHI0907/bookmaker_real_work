package dev.application.entity;

import dev.application.common.mapping.StatSummary;
import lombok.Data;

/**
 * 各チームにおける平均必要特徴量(標準偏差含む)を導出するEntity
 * @author shiraishitoshio
 *
 */
@Data
public class AverageStatisticsTeamDetailEntity {

	/** ID */
	private String id;

	/** 単位 */
	private String situation;

	/** チーム */
	private String team;

	/** 相手チーム */
	private String oppositeTeam;

	/** ホームアウェー */
	private String ha;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** ホーム期待値の統計情報 */
	private StatSummary expStat;

	/** ホームポゼッションの統計情報 */
	private StatSummary donationStat;

	/** ホームシュート数の統計情報 */
	private StatSummary shootAllStat;

	/** ホーム枠内シュートの統計情報 */
	private StatSummary shootInStat;

	/** ホーム枠外シュートの統計情報 */
	private StatSummary shootOutStat;

	/** ホームブロックシュートの統計情報 */
	private StatSummary blockShootStat;

	/** ホームビッグチャンスの統計情報 */
	private StatSummary bigChanceStat;

	/** ホームコーナーキックの統計情報 */
	private StatSummary cornerStat;

	/** ホームボックス内シュートの統計情報 */
	private StatSummary boxShootInStat;

	/** ホームボックス外シュートの統計情報 */
	private StatSummary boxShootOutStat;

	/** ホームゴールポストの統計情報 */
	private StatSummary goalPostStat;

	/** ホームヘディングゴールの統計情報 */
	private StatSummary goalHeadStat;

	/** ホームキーパーセーブの統計情報 */
	private StatSummary keeperSaveStat;

	/** ホームフリーキックの統計情報 */
	private StatSummary freeKickStat;

	/** ホームオフサイドの統計情報 */
	private StatSummary offsideStat;

	/** ホームファウルの統計情報 */
	private StatSummary foulStat;

	/** ホームイエローカードの統計情報 */
	private StatSummary yellowCardStat;

	/** ホームレッドカードの統計情報 */
	private StatSummary redCardStat;

	/** ホームスローインの統計情報 */
	private StatSummary slowInStat;

	/** ホームボックスタッチの統計情報 */
	private StatSummary boxTouchStat;

	/** ホームパス数の統計情報 */
	private StatSummary passCountStat;

	/** ホームファイナルサードパス数の統計情報 */
	private StatSummary finalThirdPassCountStat;

	/** ホームクロス数の統計情報 */
	private StatSummary crossCountStat;

	/** ホームタックル数の統計情報 */
	private StatSummary tackleCountStat;

	/** ホームクリア数の統計情報 */
	private StatSummary clearCountStat;

	/** ホームインターセプト数の統計情報 */
	private StatSummary interceptCountStat;

}
