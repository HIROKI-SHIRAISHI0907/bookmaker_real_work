package dev.application.entity;

import lombok.Data;

/**
 * 読み込んだデータから結果マスタにマッピングさせるためのDTOクラス
 * @author shiraishitoshio
 *
 */
@Data
public class ConditionResultDataEntity {

	/** まとめ通番 */
	private String dataSeq;

	/** メール通知対象数 */
	private String mailTargetCount;

	/** メール非通知対象数 */
	private String mailAnonymousTargetCount;

	/** メール通知成功数 */
	private String mailTargetSuccessCount;

	/** メール通知失敗数 */
	private String mailTargetFailCount;

	/** 前メール通知失敗結果不明数 */
	private String mailTargetFailToNoResultCount;

	/** 前終了済データ無し結果不明数 */
	private String mailFinDataToNoResultCount;

	/** ゴール取り消し */
	private String goalDelate;

	/** ゴール取り消しによる通知非通知変更 */
	private String alterTargetMailAnonymous;

	/** ゴール取り消しによる成功失敗変更 */
	private String alterTargetMailFail;

	/** 結果不明 */
	private String noResultCount;

	/** 予期せぬエラーデータ数 */
	private String errData;

	/** 条件分岐データ */
	private String conditionData;

	/** ハッシュ値データ */
	private String hashData;

}
