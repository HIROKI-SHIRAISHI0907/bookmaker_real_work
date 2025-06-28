package dev.application.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ブック読み取りoutputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class ContainsListOutputDTO {

	/**
	 * 同じ大きさか
	 */
	private boolean sameListFlg;

	/**
	 * 含まれているか
	 */
	private boolean containsListFlg;

}
