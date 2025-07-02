package dev.application.entity;

import lombok.Data;

/**
 * 各チームにおける平均必要特徴量(標準偏差含む)を導出するEntity
 * @author shiraishitoshio
 *
 */
@Data
public class CollectScoringStandardDataEntity {

	/** ID */
	private String id;

	/** situationキー */
	private String dataSituationKey;

	/** 時間キー */
	private String timeKey;

	/** チーム */
	private String team;

	/** ホームアウェー */
	private String ha;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** スコア分布 */
	private String scoreDistribution;

	/** ホーム期待値の統計情報 */
	private String expStat;

	/** ホームポゼッションの統計情報 */
	private String donationStat;

	/** ホームシュート数の統計情報 */
	private String shootAllStat;

	/** ホーム枠内シュートの統計情報 */
	private String shootInStat;

	/** ホーム枠外シュートの統計情報 */
	private String shootOutStat;

	/** ホームブロックシュートの統計情報 */
	private String blockShootStat;

	/** ホームビッグチャンスの統計情報 */
	private String bigChanceStat;

	/** ホームコーナーキックの統計情報 */
	private String cornerStat;

	/** ホームボックス内シュートの統計情報 */
	private String boxShootInStat;

	/** ホームボックス外シュートの統計情報 */
	private String boxShootOutStat;

	/** ホームゴールポストの統計情報 */
	private String goalPostStat;

	/** ホームヘディングゴールの統計情報 */
	private String goalHeadStat;

	/** ホームキーパーセーブの統計情報 */
	private String keeperSaveStat;

	/** ホームフリーキックの統計情報 */
	private String freeKickStat;

	/** ホームオフサイドの統計情報 */
	private String offsideStat;

	/** ホームファウルの統計情報 */
	private String foulStat;

	/** ホームイエローカードの統計情報 */
	private String yellowCardStat;

	/** ホームレッドカードの統計情報 */
	private String redCardStat;

	/** ホームスローインの統計情報 */
	private String slowInStat;

	/** ホームボックスタッチの統計情報 */
	private String boxTouchStat;

	/** ホームパス数の統計情報 */
	private String passCountStat;

	/** ホームファイナルサードパス数の統計情報 */
	private String finalThirdPassCountStat;

	/** ホームクロス数の統計情報 */
	private String crossCountStat;

	/** ホームタックル数の統計情報 */
	private String tackleCountStat;

	/** ホームクリア数の統計情報 */
	private String clearCountStat;

	/** ホームインターセプト数の統計情報 */
	private String interceptCountStat;

}
