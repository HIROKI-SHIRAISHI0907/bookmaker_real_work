package dev.application.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * team_statics_data outputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class TeamStaticDataOutputDTO {

	/**
	 * 更新用通番
	 */
	private String seq;

	/**
	 * 更新フラグ
	 */
	private boolean updFlg;

	/**
	 * 読み込みスコアリスト
	 */
	private Integer[] scoreList;

}
