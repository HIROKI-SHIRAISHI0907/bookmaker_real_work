package dev.application.entity;

import lombok.Data;

/**
 * output_通番.xlsxブックから読み込んだデータをマッピングさせるためのDTOクラス
 * @author shiraishitoshio
 *
 */
@Data
public class TypeOfCountryLeagueDataEntity {

	/** ID */
	private String id;

	/** 国 */
	private String country;

	/** リーグ */
	private String league;

	/** データ件数 */
	private String dataCount;

	/** CSV件数 */
	private String csvCount;

}
