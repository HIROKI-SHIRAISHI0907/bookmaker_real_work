package dev.application.common.commonservice;

import java.io.IOException;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.ConvertCsvAndReadFileOutputDTO;
import dev.application.common.dto.ReadFileOutputDTO;
import dev.application.common.file.ConvertCSV;
import dev.application.common.file.ReadFile;

/**
 * CSV変換&読み取り
 * @author shiraishitoshio
 *
 */
public class ConvertCsvAndReadFile {

	/** .xlsx */
	private static final String XLSX = ".xlsx";

	/** .CSV */
	private static final String CSV = ".csv";

	/**
	 * CSV変換&読み取り
	 * @param filePath
	 * @return
	 */
	public ConvertCsvAndReadFileOutputDTO execute(String filePath) {
		ConvertCsvAndReadFileOutputDTO convertCsvAndReadFileOutputDTO = new ConvertCsvAndReadFileOutputDTO();

		// CSV変換成功フラグ, CSV読み取り成功フラグ初期化
		convertCsvAndReadFileOutputDTO.setConvertCsvFlg(true);
		convertCsvAndReadFileOutputDTO.setReadCsvFlg(true);

		// CSV変換ロジック
		String afterFilePath = null;
		try {
			String csvFilePath = filePath.replace(XLSX, CSV);
			// 変換後のcsvパスをfilePathとする
			afterFilePath = csvFilePath;
			ConvertCSV.convertExecute(filePath, csvFilePath);
			convertCsvAndReadFileOutputDTO.setAfterCsvPath(afterFilePath);
		} catch (IOException e) {
			convertCsvAndReadFileOutputDTO.setConvertCsvFlg(false);
		}

		ReadFile readFile = new ReadFile();
		ReadFileOutputDTO readFileOutputDTO = readFile.getFileBody(afterFilePath);
		//ReadFileOutputDTO readFileOutputDTO = this.readFile.getFileBody(path);
		if (!BookMakersCommonConst.NORMAL_CD.equals(readFileOutputDTO.getResultCd())) {
			convertCsvAndReadFileOutputDTO.setReadCsvFlg(false);
		}
		convertCsvAndReadFileOutputDTO.setReadDataList(readFileOutputDTO.getReadDataList());
		return convertCsvAndReadFileOutputDTO;
	}

}
