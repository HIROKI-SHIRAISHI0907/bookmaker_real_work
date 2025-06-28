package dev.application.entity;

import dev.application.common.mapping.CorrelationSummary;
import lombok.Data;

/**
 * 相関係数保存特徴量Mapping
 * @author shiraishitoshio
 *
 */
@Data
public class CorrelationEntity {

	/** ID */
	private String id;

	/** ファイル */
	private String file;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** ホーム */
	private String home;

	/** アウェー */
	private String away;

	/** スコア */
	private String score;

	/** 導出内容 */
	private String chkBody;

	/** ホーム期待値のプレースタイル相関係数情報 */
	private CorrelationSummary homeExpInfo;

	/** アウェー期待値のプレースタイル相関係数情報 */
	private CorrelationSummary awayExpInfo;

	/** ホームポゼッションのプレースタイル相関係数情報 */
	private CorrelationSummary homeDonationInfo;

	/** アウェーポゼッションのプレースタイル相関係数情報 */
	private CorrelationSummary awayDonationInfo;

	/** ホームシュート数のプレースタイル相関係数情報 */
	private CorrelationSummary homeShootAllInfo;

	/** アウェーシュート数のプレースタイル相関係数情報 */
	private CorrelationSummary awayShootAllInfo;

	/** ホーム枠内シュートのプレースタイル相関係数情報 */
	private CorrelationSummary homeShootInInfo;

	/** アウェー枠内シュートのプレースタイル相関係数情報 */
	private CorrelationSummary awayShootInInfo;

	/** ホーム枠外シュートのプレースタイル相関係数情報 */
	private CorrelationSummary homeShootOutInfo;

	/** アウェー枠外シュートのプレースタイル相関係数情報 */
	private CorrelationSummary awayShootOutInfo;

	/** ホームブロックシュートのプレースタイル相関係数情報 */
	private CorrelationSummary homeBlockShootInfo;

	/** アウェーブロックシュートのプレースタイル相関係数情報 */
	private CorrelationSummary awayBlockShootInfo;

	/** ホームビッグチャンスのプレースタイル相関係数情報 */
	private CorrelationSummary homeBigChanceInfo;

	/** アウェービッグチャンスのプレースタイル相関係数情報 */
	private CorrelationSummary awayBigChanceInfo;

	/** ホームコーナーキックのプレースタイル相関係数情報 */
	private CorrelationSummary homeCornerInfo;

	/** アウェーコーナーキックのプレースタイル相関係数情報 */
	private CorrelationSummary awayCornerInfo;

	/** ホームボックス内シュートのプレースタイル相関係数情報 */
	private CorrelationSummary homeBoxShootInInfo;

	/** アウェーボックス内シュートのプレースタイル相関係数情報 */
	private CorrelationSummary awayBoxShootInInfo;

	/** ホームボックス外シュートのプレースタイル相関係数情報 */
	private CorrelationSummary homeBoxShootOutInfo;

	/** アウェーボックス外シュートのプレースタイル相関係数情報 */
	private CorrelationSummary awayBoxShootOutInfo;

	/** ホームゴールポストのプレースタイル相関係数情報 */
	private CorrelationSummary homeGoalPostInfo;

	/** アウェーゴールポストのプレースタイル相関係数情報 */
	private CorrelationSummary awayGoalPostInfo;

	/** ホームヘディングゴールのプレースタイル相関係数情報 */
	private CorrelationSummary homeGoalHeadInfo;

	/** アウェーヘディングゴールのプレースタイル相関係数情報 */
	private CorrelationSummary awayGoalHeadInfo;

	/** ホームキーパーセーブのプレースタイル相関係数情報 */
	private CorrelationSummary homeKeeperSaveInfo;

	/** アウェーキーパーセーブのプレースタイル相関係数情報 */
	private CorrelationSummary awayKeeperSaveInfo;

	/** ホームフリーキックのプレースタイル相関係数情報 */
	private CorrelationSummary homeFreeKickInfo;

	/** アウェーフリーキックのプレースタイル相関係数情報 */
	private CorrelationSummary awayFreeKickInfo;

	/** ホームオフサイドのプレースタイル相関係数情報 */
	private CorrelationSummary homeOffsideInfo;

	/** アウェーオフサイドのプレースタイル相関係数情報 */
	private CorrelationSummary awayOffsideInfo;

	/** ホームファウルのプレースタイル相関係数情報 */
	private CorrelationSummary homeFoulInfo;

	/** アウェーファウルのプレースタイル相関係数情報 */
	private CorrelationSummary awayFoulInfo;

	/** ホームイエローカードのプレースタイル相関係数情報 */
	private CorrelationSummary homeYellowCardInfo;

	/** アウェーイエローカードのプレースタイル相関係数情報 */
	private CorrelationSummary awayYellowCardInfo;

	/** ホームレッドカードのプレースタイル相関係数情報 */
	private CorrelationSummary homeRedCardInfo;

	/** アウェーレッドカードのプレースタイル相関係数情報 */
	private CorrelationSummary awayRedCardInfo;

	/** ホームスローインのプレースタイル相関係数情報 */
	private CorrelationSummary homeSlowInInfo;

	/** アウェースローインのプレースタイル相関係数情報 */
	private CorrelationSummary awaySlowInInfo;

	/** ホームボックスタッチのプレースタイル相関係数情報 */
	private CorrelationSummary homeBoxTouchInfo;

	/** アウェーボックスタッチのプレースタイル相関係数情報 */
	private CorrelationSummary awayBoxTouchInfo;

	/** ホームパス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary homePassCountInfoOnSuccessRatio;

	/** ホームパス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary homePassCountInfoOnSuccessCount;

	/** ホームパス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary homePassCountInfoOnTryCount;

	/** アウェーパス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary awayPassCountInfoOnSuccessRatio;

	/** アウェーパス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary awayPassCountInfoOnSuccessCount;

	/** アウェーパス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary awayPassCountInfoOnTryCount;

	/** ホームファイナルサードパス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary homeFinalThirdPassCountInfoOnSuccessRatio;

	/** ホームファイナルサードパス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary homeFinalThirdPassCountInfoOnSuccessCount;

	/** ホームファイナルサードパス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary homeFinalThirdPassCountInfoOnTryCount;

	/** アウェーファイナルサードパス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary awayFinalThirdPassCountInfoOnSuccessRatio;

	/** アウェーファイナルサードパス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary awayFinalThirdPassCountInfoOnSuccessCount;

	/** アウェーファイナルサードパス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary awayFinalThirdPassCountInfoOnTryCount;

	/** ホームクロス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary homeCrossCountInfoOnSuccessRatio;

	/** ホームクロス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary homeCrossCountInfoOnSuccessCount;

	/** ホームクロス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary homeCrossCountInfoOnTryCount;

	/** アウェークロス数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary awayCrossCountInfoOnSuccessRatio;

	/** アウェークロス数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary awayCrossCountInfoOnSuccessCount;

	/** アウェークロス数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary awayCrossCountInfoOnTryCount;

	/** ホームタックル数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary homeTackleCountInfoOnSuccessRatio;

	/** ホームタックル数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary homeTackleCountInfoOnSuccessCount;

	/** ホームタックル数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary homeTackleCountInfoOnTryCount;

	/** アウェータックル数のプレースタイル成功率相関係数情報 */
	private CorrelationSummary awayTackleCountInfoOnSuccessRatio;

	/** アウェータックル数のプレースタイル成功数相関係数情報 */
	private CorrelationSummary awayTackleCountInfoOnSuccessCount;

	/** アウェータックル数のプレースタイル試行数相関係数情報 */
	private CorrelationSummary awayTackleCountInfoOnTryCount;

	/** ホームクリア数のプレースタイル相関係数情報 */
	private CorrelationSummary homeClearCountInfo;

	/** アウェークリア数のプレースタイル相関係数情報 */
	private CorrelationSummary awayClearCountInfo;

	/** ホームインターセプト数のプレースタイル相関係数情報 */
	private CorrelationSummary homeInterceptCountInfo;

	/** アウェーインターセプト数のプレースタイル相関係数情報 */
	private CorrelationSummary awayInterceptCountInfo;

}
