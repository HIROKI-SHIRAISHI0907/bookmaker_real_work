package dev.application.common.dto;

import java.util.List;

import lombok.Data;

/**
 * 更新CSVoutputDTO
 * @author shiraishitoshio
 *
 */
@Data
public class ReadSoccerCsvDataOutputDTO {

	/**
	 * 通番リスト
	 */
	private List<List<String>> allSeqList;

	/**
	 * CSV番号
	 */
	private int csvNumber;

}
