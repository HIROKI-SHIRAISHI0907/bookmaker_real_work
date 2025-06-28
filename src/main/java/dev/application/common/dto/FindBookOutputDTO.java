package dev.application.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * ブック探索outputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class FindBookOutputDTO extends AbstractResultErrDetailOutputDTO {

	/**
	 * 結果コード(終了コード)
	 */
	private String resultCd;

	/**
	 * ブックパスリスト
	 */
	private List<String> bookList;

}
