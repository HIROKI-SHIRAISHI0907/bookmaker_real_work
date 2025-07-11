package dev.application.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 読み込み済みファイルoutputDTO
 * @author shiraishitoshio
 *
 */
@Setter
@Getter
public class ReadFileOperationChkOutputDTO {

	/**
	 * ファイル読み込みフラグ
	 */
	private boolean fileChkFlg;

	/**
	 * 更新ファイル読み込みフラグ
	 */
	private boolean fileTmpChkFlg;

}
