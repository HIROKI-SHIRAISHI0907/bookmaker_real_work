package dev.application.common.dto;

import lombok.Data;

/**
 * ブック探索inputDTO
 * @author shiraishitoshio
 *
 */
@Data
public class FindBookInputDTO {

	/**
	 * ブック存在パス(フルパスからファイル名を除いた部分)
	 */
	private String dataPath;

	/**
	 * コピー先パス(フルパスからファイル名を除いた部分)
	 */
	private String copyPath;

	/**
	 * コピーフラグ
	 */
	private boolean copyFlg;

	/**
	 * 取得対象物
	 */
	private String targetFile;

	/**
	 * 含まれる内容
	 */
	private String[] contains;

}
