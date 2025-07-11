package dev.application.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * average_feature_data outputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class AverageFeatureOutputDTO {

	/**
	 * 件数
	 */
	private int counter;

	/**
	 * 合計
	 */
	private int sum;

}
