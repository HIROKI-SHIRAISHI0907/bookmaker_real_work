package dev.application.domain.service.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dev.application.common.logic.CollectScoringDataStandardValueLogic;
import dev.application.domain.service.it.common.CSVReaderBase;
import dev.application.domain.service.it.common.CSVResult;

/**
 * TIMEデータに対して得点が生まれている時間帯ごとの特徴量における最低閾値集計ロジック
 * @author shiraishitoshio
 *
 */
public class CollectScoringDataStandardValueLogicTest extends CSVReaderBase {

	@Test
	public void test1() {
		final String team1 = "サンフレッチェ広島";
		final String stats1 = "exp_stat";
		String testPath1 = "/Users/shiraishitoshio/bookmaker/average_stats/日本-J1 リーグ/"
				+ "日本-J1 リーグ-" + team1 + "-" + stats1 + "-TIME.csv";
		CSVResult result1 = readCSV(testPath1);
		String[] testPath_sp1 = testPath1.split("/");
		testPath1 = testPath_sp1[testPath_sp1.length - 1].replace(".csv", "");
		List<String> headerList1 = result1.getHeaderList();
		List<List<String>> bodyList1 = result1.getBodyList();
		final String team2 = "セレッソ大阪";
		final String stats2 = "exp_stat";
		String testPath2 = "/Users/shiraishitoshio/bookmaker/average_stats/日本-J1 リーグ/"
				+ "日本-J1 リーグ-" + team2 + "-" + stats2 + "-TIME.csv";
		CSVResult result2 = readCSV(testPath2);
		String[] testPath_sp2 = testPath2.split("/");
		testPath2 = testPath_sp2[testPath_sp2.length - 1].replace(".csv", "");
		List<String> headerList2 = result2.getHeaderList();
		List<List<String>> bodyList2 = result2.getBodyList();

		CollectScoringDataStandardValueLogic collectScoringDataStandardValueLogic
			= new CollectScoringDataStandardValueLogic();
		collectScoringDataStandardValueLogic.execute(testPath1, team1, stats1, headerList1, bodyList1,
				testPath2, team2, stats2, headerList2, bodyList2);
	}

	@Test
	public void test2() {
		final String team1 = "サンフレッチェ広島";
		final String stats1 = "exp_stat";
		String testPath1 = "/Users/shiraishitoshio/bookmaker/average_stats/日本-J1 リーグ/"
				+ "日本-J1 リーグ-" + team1 + "-" + stats1 + "-TIME.csv";
		CSVResult result1 = readCSV(testPath1);
		String[] testPath_sp1 = testPath1.split("/");
		testPath1 = testPath_sp1[testPath_sp1.length - 1].replace(".csv", "");
		List<String> headerList1 = result1.getHeaderList();
		List<List<String>> bodyList1 = result1.getBodyList();

		CollectScoringDataStandardValueLogic collectScoringDataStandardValueLogic
			= new CollectScoringDataStandardValueLogic();
		collectScoringDataStandardValueLogic.execute(testPath1, team1, stats1, headerList1, bodyList1,
				null, null, null, new ArrayList<>(), new ArrayList<List<String>>());
	}

}
