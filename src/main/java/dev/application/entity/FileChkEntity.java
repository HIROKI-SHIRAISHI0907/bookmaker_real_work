package dev.application.entity;

import lombok.Data;

/**
 * file_chkテーブルentity
 * @author shiraishitoshio
 *
 */
@Data
public class FileChkEntity {

	/** ファイル名 */
	private String fileName;

	/** ハッシュ */
	private String fileHash;

}
