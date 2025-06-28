package dev.application.common.dto;

import java.util.List;

import dev.application.entity.BookDataInsertEntity;
import dev.application.entity.ThresHoldEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ブック読み取りoutputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class ReadFileOutputDTO extends AbstractResultErrDetailOutputDTO {

	/**
	 * 結果コード(終了コード)
	 */
	private String resultCd;

	/**
	 * 読み取り結果リスト
	 */
	private List<ThresHoldEntity> readHoldDataList;

	/**
	 * 読み取り結果リスト
	 */
	private List<BookDataInsertEntity> readDataList;

}
