package dev.application.common.logic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.application.common.exception.SystemException;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.BookDataSelectWrapper;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;

/**
 * R言語用に用意するCSVファイルを作成するメインロジック
 * @author shiraishitoshio
 *
 */
public class RDataOutputSimpleLogic {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(RDataOutputSimpleLogic.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = RDataOutputSimpleLogic.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = RDataOutputSimpleLogic.class.getSimpleName();

	/**
	 * 処理実行
	 */
	public void execute() {

		// データ取得
		final String METHOD = "execute";

		BookDataSelectWrapper selectWrapper = new BookDataSelectWrapper();
		// レコード件数を取得する
		int allCnt = -1;
		try {
			allCnt = selectWrapper.executeCountSelect(UniairConst.BM_M001, null);
		} catch (Exception e) {
			throw new SystemException(
					PROJECT_NAME,
					CLASS_NAME,
					METHOD,
					"",
					e.getCause());
		}

		// 出力ファイル
		final String CSVNAME = "/Users/shiraishitoshio/bookmaker/csv/allData.csv";

		// CSV作成確認
		Path path = Paths.get(CSVNAME);
		try {
			if (!Files.exists(path)) {
				Files.createFile(path);
			}
		} catch (IOException e) {
			System.out.println("CSVが作成できない");
		}

		// select
		List<String> seleList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M001);
		String[] sels = new String[seleList.size()];
		int i = 0;
		for (String sel : seleList) {
			sels[i] = sel;
			i++;
		}

		// CSVオープン
		try (FileOutputStream fileOutputStream = new FileOutputStream(CSVNAME);
				BufferedWriter bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8))) {

			// ヘッダー
			String headList = UniairColumnMapUtil.mkCsvChkHeader(UniairConst.BM_M001);
			bufferedWriter.write(headList);
			bufferedWriter.newLine();

			List<List<String>> selectResultList = null;
			for (int seq = 1; seq <= allCnt; seq++) {
				logger.info("seq : {} ", seq);
				// 通番を設定
				String where = "seq = " + seq;
				SqlMainLogic select = new SqlMainLogic();
				try {
					selectResultList = select.executeSelect(null, UniairConst.BM_M001, sels, where, null, "1");
				} catch (Exception e) {
					logger.error("select error -> ", e);
				}

				for (List<String> rollList : selectResultList) {
					StringBuilder sBuilder = new StringBuilder();
					for (String roll : rollList) {
						if (sBuilder.toString().length() > 0) {
							sBuilder.append(",");
						}
						sBuilder.append(roll);
					}
					bufferedWriter.write(sBuilder.toString());
					bufferedWriter.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("書き込み失敗");
		}
	}
}
