package dev.application.entity;

import lombok.Data;

/**
 * upd_csv_infoテーブルentity
 * @author shiraishitoshio
 *
 */
@Data
public class UpdCsvInfoEntity {

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** テーブルID */
	private String tableId;

	/** 備考 */
	private String remarks;

}
