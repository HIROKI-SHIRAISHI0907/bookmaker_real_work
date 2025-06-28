package dev.application.common.dto;

import java.util.List;

import dev.application.entity.BookDataSelectEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * timesが45:XX,46:XXなどハーフタイム付近の並び替えに関するサブ結果DTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class ModifySequenceSubResultDTO {

	/**
	 * 強制交換必須フラグ(true:必須,false:不要)
	 */
	private boolean forceExchangeFlg = false;

	/**
	 * 交換必須フラグ(true:必須,false:不要)
	 */
	private boolean exchangeFlg;

	/**
	 * 並び替え結果リスト
	 */
	private List<BookDataSelectEntity> exchangeList;

}
