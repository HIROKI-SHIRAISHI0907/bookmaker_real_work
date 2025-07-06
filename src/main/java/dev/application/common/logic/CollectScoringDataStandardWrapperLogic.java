package dev.application.common.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.application.common.commonservice.CopyFileAndGetStatCsv;

/**
 * 以下のクラスラッパークラス
 * <p>CollectScoringDataStandardValueOnSingleLogic</p>
 * <p>CollectScoringDataStandardValueOnThreeLogic</p>
 * @author shiraishitoshio
 *
 */
public class CollectScoringDataStandardWrapperLogic extends CSVReaderBase {

	/** オリジンパス */
	private static String ORIGINPATH = "/Users/shiraishitoshio/bookmaker/average_stats/";

	/**
	 * stat_singleリスト
	 */
	private static final List<String> STAT_SINGLE_LIST;
	static {
		List<String> list = new ArrayList<>();
		list.add("exp_stat");
		list.add("donation_stat");
		list.add("shoot_all_stat");
		list.add("shoot_in_stat");
		list.add("shoot_out_stat");
		list.add("block_shoot_stat");
		list.add("big_chance_stat");
		list.add("corner_stat");
		list.add("box_shoot_in_stat");
		list.add("box_shoot_out_stat");
		list.add("goal_post_stat");
		list.add("goal_head_stat");
		list.add("keeper_save_stat");
		list.add("free_kick_stat");
		list.add("offside_stat");
		list.add("foul_stat");
		list.add("yellow_card_stat");
		list.add("red_card_stat");
		list.add("slow_in_stat");
		list.add("box_touch_stat");
		list.add("clear_count_stat");
		list.add("intercept_count_stat");
		STAT_SINGLE_LIST = Collections.unmodifiableList(list);
	}

	/**
	 * stat_threeリスト
	 */
	private static final List<String> STAT_THREE_LIST;
	static {
		List<String> list = new ArrayList<>();
		list.add("pass_count_stat");
		list.add("final_third_pass_count_stat");
		list.add("cross_count_stat");
		list.add("tackle_count_stat");
		STAT_THREE_LIST = Collections.unmodifiableList(list);
	}

	/**
	 * 実行メソッド
	 */
	public void execute() {

		// CSV読み込み(statとTIMEごとにリストを分ける)
		CopyFileAndGetStatCsv copyFileAndGetStatCsv = new CopyFileAndGetStatCsv();
		List<String> bookList = copyFileAndGetStatCsv.execute(ORIGINPATH, null);

		// ファイル内のデータ取得
		List<String> regList = new ArrayList<String>();
		for (int i = 0; i < bookList.size(); i++) {
		    for (int j = i + 1; j < bookList.size(); j++) {
				String filePath1 = bookList.get(i);
				String filePath2 = bookList.get(j);
				if (filePath1.equals(filePath2)) {
					continue;
				}
				CSVResult result1 = readCSV(filePath1);
				String[] testPath_sp1 = filePath1.split("/");
				filePath1 = testPath_sp1[testPath_sp1.length - 1].replace(".csv", "");
				String[] element1 = filePath1.split("-");
				if (element1.length < 3) {
					continue;
				}
				String team1 = element1[element1.length - 3];
				String stat1 = element1[element1.length - 2];
				String situ1 = element1[element1.length - 1];
				List<String> headerList1 = result1.getHeaderList();
				List<List<String>> bodyList1 = result1.getBodyList();
				CSVResult result2 = readCSV(filePath2);
				String[] testPath_sp2 = filePath2.split("/");
				filePath2 = testPath_sp2[testPath_sp2.length - 1].replace(".csv", "");
				String[] element2 = filePath2.split("-");
				if (element2.length < 3) {
					continue;
				}
				String team2 = element2[element2.length - 3];
				String stat2 = element2[element2.length - 2];
				String situ2 = element2[element2.length - 1];
				List<String> headerList2 = result2.getHeaderList();
				List<List<String>> bodyList2 = result2.getBodyList();

				System.out.println("team1: " + team1 + ", stat1: " + stat1 + ", situ1: " + situ1
						+ ", team2: " + team2 + ", stat2: " + stat2 + ", situ2: " + situ2);

				if (!regList.contains(team1 + "-" + stat1)) {
					if (containsSingleStat(stat1) && "TIME".equals(situ1)) {
						System.out.println("team1: " + team1 + ", stat1: " + stat1 + ", situ1: " + situ1);
						CollectScoringDataStandardValueOnSingleLogic collectScoringDataStandardValueLogic1 = new CollectScoringDataStandardValueOnSingleLogic();
						collectScoringDataStandardValueLogic1.execute(filePath1, team1, stat1, headerList1, bodyList1,
								null, null, null, new ArrayList<>(), new ArrayList<List<String>>());
					} else if (containsThreeStat(stat1) && "TIME".equals(situ1)) {
						System.out.println("team1: " + team1 + ", stat1: " + stat1 + ", situ1: " + situ1);
						CollectScoringDataStandardValueOnThreeLogic collectScoringDataStandardValueLogic2 = new CollectScoringDataStandardValueOnThreeLogic();
						collectScoringDataStandardValueLogic2.execute(filePath1, team1, stat1, headerList1, bodyList1,
								null, null, null, new ArrayList<>(), new ArrayList<List<String>>());
					}
					// チーム名とstatを保存
					regList.add(team1 + "-" + stat1);
				}

				if (!team1.equals(team2)) {
					continue;
				}

				if (containsSingleStat(stat1) && containsSingleStat(stat2) &&
						stat1.equals(stat2) && "TIME".equals(situ1) && "TIME".equals(situ2)) {
					System.out.println("team1: " + team1 + ", stat1: " + stat1 + ", situ1: " + situ1
							+ ", team2: " + team2 + ", stat2: " + stat2 + ", situ2: " + situ2);
					CollectScoringDataStandardValueOnSingleLogic collectScoringDataStandardValueLogic = new CollectScoringDataStandardValueOnSingleLogic();
					collectScoringDataStandardValueLogic.execute(filePath1, team1, stat1, headerList1, bodyList1,
							filePath2, team2, stat2, headerList2, bodyList2);
				} else if (containsThreeStat(stat1) && containsThreeStat(stat2) &&
						stat1.equals(stat2) && "TIME".equals(situ1) && "TIME".equals(situ2)) {
					System.out.println("team1: " + team1 + ", stat1: " + stat1 + ", situ1: " + situ1
							+ ", team2: " + team2 + ", stat2: " + stat2 + ", situ2: " + situ2);
					CollectScoringDataStandardValueOnThreeLogic collectScoringDataStandardValueOnThreeLogic = new CollectScoringDataStandardValueOnThreeLogic();
					collectScoringDataStandardValueOnThreeLogic.execute(filePath1, team1, stat1, headerList1, bodyList1,
							filePath2, team2, stat2, headerList2, bodyList2);
				}
			}
		}

	}

	/**
	 * statが含まれているか
	 * @param country
	 * @param league
	 * @return
	 */
	private boolean containsSingleStat(String stat) {
		for (String list : STAT_SINGLE_LIST) {
			if (list.contains(stat)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * statが含まれているか
	 * @param country
	 * @param league
	 * @return
	 */
	private boolean containsThreeStat(String stat) {
		for (String list : STAT_THREE_LIST) {
			if (list.contains(stat)) {
				return true;
			}
		}
		return false;
	}

}
