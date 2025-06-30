package dev.application.domain.service.it;

import java.util.List;

import org.junit.jupiter.api.Test;

import dev.application.common.logic.CollectScoringDataStandardValueLogic;
import dev.application.domain.service.it.common.CSVReaderBase;

/**
 * TIMEデータに対して得点が生まれている時間帯ごとの特徴量における最低閾値集計ロジック
 * @author shiraishitoshio
 *
 */
public class CollectScoringDataStandardValueLogicTest extends CSVReaderBase {

	@Test
	public void test() {
		final String team = "サンフレッチェ広島";
		final String stats = "exp_stat";
		String testPath = "/Users/shiraishitoshio/bookmaker/average_stats/日本-J1 リーグ/"
				+ "日本-J1 リーグ-" + team + "-" + stats + "-TIME.csv";
		readCSV(testPath);
		List<String> headerList = getHeaderList();
		List<List<String>> bodyList = getBodyList();

		CollectScoringDataStandardValueLogic collectScoringDataStandardValueLogic
			= new CollectScoringDataStandardValueLogic();
		collectScoringDataStandardValueLogic.execute(team, stats, headerList, bodyList);
	}

}
