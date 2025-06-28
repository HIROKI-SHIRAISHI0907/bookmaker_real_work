package dev.application.entity;

import lombok.Data;

/**
 * file_chk_tmpテーブルentity
 * @author shiraishitoshio
 *
 */
@Data
public class FileChkTmpEntity {

	/** 国 */
	private String country;

	/** リーグ名 */
	private String league;

	/** ファイル名 */
	private String fileName;

	/** 更新前通番リスト */
	private String befSeqList;

	/** 更新後通番リスト */
	private String afSeqList;

	/** 更新前ハッシュ */
	private String befFileHash;

	/** 更新後ハッシュ */
	private String afFileHash;

}
