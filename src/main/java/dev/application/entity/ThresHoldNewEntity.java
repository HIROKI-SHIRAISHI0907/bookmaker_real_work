package dev.application.entity;

import lombok.Data;

/**
 * output_通番.xlsxブックから読み込んだデータをマッピングさせるためのDTOクラス
 * @author shiraishitoshio
 *
 */
@Data
public class ThresHoldNewEntity {

	/** ホーム期待値 */
	private String homeExp;

	/** アウェー期待値 */
	private String awayExp;

	/** ホームポゼッション */
	private String homeDonation;

	/** アウェーポゼッション */
	private String awayDonation;

	/** ホームシュート数 */
	private String homeShootAll;

	/** アウェーシュート数 */
	private String awayShootAll;

	/** ホーム枠内シュート */
	private String homeShootIn;

	/** アウェー枠内シュート */
	private String awayShootIn;

	/** ホーム枠外シュート */
	private String homeShootOut;

	/** アウェー枠外シュート */
	private String awayShootOut;

	/** ホームブロックシュート */
	private String homeBlockShoot;

	/** アウェーブロックシュート */
	private String awayBlockShoot;

	/** ホームビックチャンス */
	private String homeBigChance;

	/** アウェービックチャンス */
	private String awayBigChance;

	/** ホームコーナーキック */
	private String homeCorner;

	/** アウェーコーナーキック */
	private String awayCorner;

	/** ホームボックス内シュート */
	private String homeBoxShootIn;

	/** アウェーボックス内シュート */
	private String awayBoxShootIn;

	/** ホームボックス外シュート */
	private String homeBoxShootOut;

	/** アウェーボックス外シュート */
	private String awayBoxShootOut;

	/** ホームゴールポスト */
	private String homeGoalPost;

	/** アウェーゴールポスト */
	private String awayGoalPost;

	/** ホームヘディングゴール */
	private String homeGoalHead;

	/** アウェーヘディングゴール */
	private String awayGoalHead;

	/** ホームキーパーセーブ */
	private String homeKeeperSave;

	/** アウェーキーパーセーブ */
	private String awayKeeperSave;

	/** ホームフリーキック */
	private String homeFreeKick;

	/** アウェーフリーキック */
	private String awayFreeKick;

	/** ホームオフサイド */
	private String homeOffside;

	/** アウェーオフサイド */
	private String awayOffside;

	/** ホームファウル */
	private String homeFoul;

	/** アウェーファウル */
	private String awayFoul;

	/** ホームイエローカード */
	private String homeYellowCard;

	/** アウェーイエローカード */
	private String awayYellowCard;

	/** ホームレッドカード */
	private String homeRedCard;

	/** アウェーレッドカード */
	private String awayRedCard;

	/** ホームスローイン */
	private String homeSlowIn;

	/** アウェースローイン */
	private String awaySlowIn;

	/** ホームボックスタッチ */
	private String homeBoxTouch;

	/** アウェーボックスタッチ */
	private String awayBoxTouch;

	/** ホームパス数 */
	private String homePassCount;

	/** アウェーパス数 */
	private String awayPassCount;

	/** ホームファイナルサードパス数 */
	private String homeFinalThirdPassCount;

	/** アウェーファイナルサードパス数 */
	private String awayFinalThirdPassCount;

	/** ホームクロス数 */
	private String homeCrossCount;

	/** アウェークロス数 */
	private String awayCrossCount;

	/** ホームタックル数 */
	private String homeTackleCount;

	/** アウェータックル数 */
	private String awayTackleCount;

	/** ホームクリア数 */
	private String homeClearCount;

	/** アウェークリア数 */
	private String awayClearCount;

	/** ホームインターセプト数 */
	private String homeInterceptCount;

	/** アウェーインターセプト数 */
	private String awayInterceptCount;

}
