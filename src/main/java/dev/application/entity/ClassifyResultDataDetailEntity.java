package dev.application.entity;

import lombok.Data;

/**
 * classify_result_data_detailデータEntity
 * @author shiraishitoshio
 *
 */
@Data
public class ClassifyResultDataDetailEntity {

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** 分類モード */
	private String classifyMode;

	/** 該当数 */
	private String count;

	/** 備考 */
	private String remarks;
}
