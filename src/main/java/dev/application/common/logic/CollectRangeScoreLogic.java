package dev.application.common.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.exception.SystemException;
import dev.application.common.util.ExecuteMainUtil;
import dev.application.db.CsvRegisterImpl;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.CollectRangeScoreEntity;
import dev.application.entity.ThresHoldEntity;

/**
 * 時間範囲における得点分布集計ロジック
 * @author shiraishitoshio
 *
 */
public class CollectRangeScoreLogic {

	/**
	 * 実行メソッド(ホーム,アウェーが正しい並びの方をTというキーフラグで管理)
	 * @param entityList
	 * @param file
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void execute(List<ThresHoldEntity> entityList, String file)
			throws IllegalArgumentException, IllegalAccessException {

		ThresHoldEntity returnMaxEntity = ExecuteMainUtil.getMaxSeqEntities(entityList);
		String[] data_category = ExecuteMainUtil.splitLeagueInfo(returnMaxEntity.getDataCategory());
		String country = data_category[0];
		String league = data_category[1];

		//Map<String, List<ThresHoldEntity>> chkMap = divideTimesIntoRanges(entityList);

		int loop = 1;
		do {
			List<String> scores = new ArrayList<>(Collections.nCopies(11, "0"));
			String keyFlg = "";
			String team = "";
			String opposite_team = "";

			if (loop == 1) {
				keyFlg = "T";
				team = returnMaxEntity.getHomeTeamName();
				opposite_team = returnMaxEntity.getAwayTeamName();
			} else {
				keyFlg = "";
				team = returnMaxEntity.getAwayTeamName();
				opposite_team = returnMaxEntity.getHomeTeamName();
			}

			if (getExistData(country, league, keyFlg, team, opposite_team)) {
				loop++;
				continue;
			}

			int currentScore = 0;

			// ソートして時間順に並べる（ゴール増加順を正確に処理）
//			List<ThresHoldEntity> sortedList = new ArrayList<>();
//			chkMap.values().forEach(sortedList::addAll);
//			sortedList.sort(Comparator.comparingInt(e -> Integer.parseInt(e.getSeq())));

			for (ThresHoldEntity entity : entityList) {
				// ゴール取り消しはスキップ
			    if (BookMakersCommonConst.GOAL_DELETE.equals(entity.getJudge())) {
			        continue;
			    }

				String scoreStr = (loop == 1) ? entity.getHomeScore() : entity.getAwayScore();
				try {
					int score = Integer.parseInt(scoreStr);
					if (score > currentScore) {
						int addedGoals = score - currentScore;
						double minute = ExecuteMainUtil.convertToMinutes(entity.getTimes());

						int index = determineTimeIndex(minute);
						if (index != -1 && index < scores.size()) {
							int prev = Integer.parseInt(scores.get(index));
							scores.set(index, String.valueOf(prev + addedGoals));
						}

						currentScore = score;
					}
				} catch (NumberFormatException e) {
					System.out.println("スコアが数値ではありません: " + e.getMessage());
					continue;
				}
			}

			registerData(country, league, keyFlg, team, opposite_team, scores);
			loop++;
		} while (loop <= 2);
	}

	/**
	 * 登録
	 * @param country 国
	 * @param league リーグ
	 * @param keyFlg キーフラグ
	 * @param team チーム
	 * @param oppoTeam 相手チーム
	 * @param regList 登録リスト
	 */
	private void registerData(String country, String league, String keyFlg,
			String team, String oppoTeam, List<String> regList) {
		List<CollectRangeScoreEntity> insertEntities = new ArrayList<CollectRangeScoreEntity>();
		CollectRangeScoreEntity collectRangeScoreEntity = new CollectRangeScoreEntity();
		collectRangeScoreEntity.setCountry(country);
		collectRangeScoreEntity.setLeague(league);
		collectRangeScoreEntity.setKeyFlg(keyFlg);
		collectRangeScoreEntity.setTeam(team);
		collectRangeScoreEntity.setOppositeTeam(oppoTeam);
		collectRangeScoreEntity.setTime0_10(regList.get(0));
		collectRangeScoreEntity.setTime10_20(regList.get(1));
		collectRangeScoreEntity.setTime20_30(regList.get(2));
		collectRangeScoreEntity.setTime30_40(regList.get(3));
		collectRangeScoreEntity.setTime40_45(regList.get(4));
		collectRangeScoreEntity.setTime45_50(regList.get(5));
		collectRangeScoreEntity.setTime50_60(regList.get(6));
		collectRangeScoreEntity.setTime60_70(regList.get(7));
		collectRangeScoreEntity.setTime70_80(regList.get(8));
		collectRangeScoreEntity.setTime80_90(regList.get(9));
		collectRangeScoreEntity.setTime90_100(regList.get(10));
		insertEntities.add(collectRangeScoreEntity);

		CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
		try {
			csvRegisterImpl.executeInsert(UniairConst.BM_M029,
					insertEntities, 1, insertEntities.size());
			System.out.println("BM_M029に登録しました。country: " + country + ", league: " + league +
					", team: " + team + ", oppoTeam: " + oppoTeam);
		} catch (Exception e) {
			System.err.println("collect_range_score insert err execute: " + e);
		}
	}

	/**
	 * 取得
	 * @param country 国
	 * @param league リーグ
	 * @param keyFlg キーフラグ
	 * @param team チーム
	 * @param oppoTeam 相手チーム
	 * @return boolean
	 */
	private boolean getExistData(String country, String league, String keyFlg,
			String team, String oppoTeam) {
		String[] selDataList = new String[1];
		selDataList[0] = "id";

		String where = "country = '" + country + "' and league = '" + league + "'"
				+ " and key_flg = '" + keyFlg + "'"
				+ " and team = '" + team + "' and opposite_team = '" + oppoTeam + "'";

		List<List<String>> selectResultList = null;
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M029, selDataList,
					where, null, "1");
		} catch (Exception e) {
			throw new SystemException("", "", "", "err");
		}

		if (!selectResultList.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 時間帯インデックス判定
	 * @param minute
	 * @return
	 */
	private int determineTimeIndex(double minute) {
	    if (minute < 0 || minute > 100) return -1;
	    if (minute > 40 && minute <= 45) return 4;
	    if (minute > 45 && minute <= 50) return 5;

	    int rounded = (int) (minute / 10);
	    return switch (rounded) {
	        case 0 -> 0;
	        case 1 -> 1;
	        case 2 -> 2;
	        case 3 -> 3;
	        case 4 -> 4;
	        case 5 -> 6;
	        case 6 -> 7;
	        case 7 -> 8;
	        case 8 -> 9;
	        case 9, 10 -> 10;
	        default -> -1;
	    };
	}
}
