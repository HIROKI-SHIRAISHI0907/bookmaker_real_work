package dev.application.common.logic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.application.common.dto.RDataOutputDTO;
import dev.application.common.file.MakeAllCsv;
import dev.application.common.util.DateUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.BookDataSelectEntity;

public class RDataOutputAllSubLogic {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(RDataOutputAllSubLogic.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = RDataOutputAllSubLogic.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = RDataOutputAllSubLogic.class.getSimpleName();

	/**
	 * @param seqList
	 * @param RDataOutputDTO
	 */
	public RDataOutputDTO execute(List<String> seqList, RDataOutputDTO rDataOutputDTO) {
		final String METHOD = "execute";

		logger.info(" RDataOutputSubLogic businessLogic start : {} ", CLASS_NAME);

		SqlMainLogic select = new SqlMainLogic();
		// 通番を設定
		StringBuilder whereBuilder = new StringBuilder();
		for (String seqs : seqList) {
			if (whereBuilder.toString().length() > 0) {
				whereBuilder.append(" OR ");
			}
			whereBuilder.append("seq = " + seqs + " ");
		}

		//logger.info("sum chk seq counter -> No: {}, already seq chk size -> {} ", seq, chkSeqList.size());

		List<String> selectList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M001);
		String[] selList = new String[selectList.size()];
		for (int i = 0; i < selectList.size(); i++) {
			selList[i] = selectList.get(i);
		}

		List<List<String>> selectResultList = null;
		List<BookDataSelectEntity> conditionList = new ArrayList<BookDataSelectEntity>();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M001, selList, whereBuilder.toString(), null,
					"1");
			if (!selectResultList.isEmpty()) {
				// Entityにマッピングする
				for (List<String> list : selectResultList) {
					BookDataSelectEntity mapSelectDestination = mappingSelectEntity(list);
					conditionList.add(mapSelectDestination);
				}
			}
		} catch (Exception e) {
			logger.error("select error -> ", e);
		}

		// 条件句に取得してきたホームチーム,アウェーチームの名前を設定
		String homeTeam = conditionList.get(0).getHomeTeamName();
		String awayTeam = conditionList.get(0).getAwayTeamName();
		String where = "home_team_name = '" + homeTeam + "' AND away_team_name = '" + awayTeam + "'";
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M001, selList, where, null, null);
		} catch (Exception e) {
			logger.error("select error -> ", e);
		}

		if (!selectResultList.isEmpty()) {
			MakeAllCsv makeAllCsv = new MakeAllCsv();
			rDataOutputDTO = makeAllCsv.execute(selectResultList, rDataOutputDTO);
		}

		logger.info(" RDataOutputSubLogic businessLogic end : {} ", CLASS_NAME);

		return rDataOutputDTO;
	}

	/**
	 * ListからDTOにマッピングをかける
	 * @param mapSource list構造
	 * @return BookDataSelectEntity DTO
	 * @throws Exception
	 */
	private BookDataSelectEntity mappingSelectEntity(List<String> parts) throws Exception {
		BookDataSelectEntity mappingDto = new BookDataSelectEntity();
		mappingDto.setSeq(parts.get(0));
		mappingDto.setConditionResultDataSeqId(parts.get(1));
		mappingDto.setDataCategory(parts.get(2));
		mappingDto.setTimes(parts.get(3));
		mappingDto.setHomeRank(parts.get(4));
		mappingDto.setHomeTeamName(parts.get(5));
		mappingDto.setHomeScore(parts.get(6));
		mappingDto.setAwayRank(parts.get(7));
		mappingDto.setAwayTeamName(parts.get(8));
		mappingDto.setAwayScore(parts.get(9));
		mappingDto.setHomeExp(parts.get(10));
		mappingDto.setAwayExp(parts.get(11));
		mappingDto.setHomeDonation(parts.get(12));
		mappingDto.setAwayDonation(parts.get(13));
		mappingDto.setHomeShootAll(parts.get(14));
		mappingDto.setAwayShootAll(parts.get(15));
		mappingDto.setHomeShootIn(parts.get(16));
		mappingDto.setAwayShootIn(parts.get(17));
		mappingDto.setHomeShootOut(parts.get(18));
		mappingDto.setAwayShootOut(parts.get(19));
		mappingDto.setHomeBlockShoot(parts.get(20));
		mappingDto.setAwayBlockShoot(parts.get(21));
		mappingDto.setHomeBigChance(parts.get(22));
		mappingDto.setAwayBigChance(parts.get(23));
		mappingDto.setHomeCorner(parts.get(24));
		mappingDto.setAwayCorner(parts.get(25));
		mappingDto.setHomeBoxShootIn(parts.get(26));
		mappingDto.setAwayBoxShootIn(parts.get(27));
		mappingDto.setHomeBoxShootOut(parts.get(28));
		mappingDto.setAwayBoxShootOut(parts.get(29));
		mappingDto.setHomeGoalPost(parts.get(30));
		mappingDto.setAwayGoalPost(parts.get(31));
		mappingDto.setHomeGoalHead(parts.get(32));
		mappingDto.setAwayGoalHead(parts.get(33));
		mappingDto.setHomeKeeperSave(parts.get(34));
		mappingDto.setAwayKeeperSave(parts.get(35));
		mappingDto.setHomeFreeKick(parts.get(36));
		mappingDto.setAwayFreeKick(parts.get(37));
		mappingDto.setHomeOffside(parts.get(38));
		mappingDto.setAwayOffside(parts.get(39));
		mappingDto.setHomeFoul(parts.get(40));
		mappingDto.setAwayFoul(parts.get(41));
		mappingDto.setHomeYellowCard(parts.get(42));
		mappingDto.setAwayYellowCard(parts.get(43));
		mappingDto.setHomeRedCard(parts.get(44));
		mappingDto.setAwayRedCard(parts.get(45));
		mappingDto.setHomeSlowIn(parts.get(46));
		mappingDto.setAwaySlowIn(parts.get(47));
		mappingDto.setHomeBoxTouch(parts.get(48));
		mappingDto.setAwayBoxTouch(parts.get(49));
		mappingDto.setHomePassCount(parts.get(50));
		mappingDto.setAwayPassCount(parts.get(51));
		mappingDto.setHomeFinalThirdPassCount(parts.get(52));
		mappingDto.setAwayFinalThirdPassCount(parts.get(53));
		mappingDto.setHomeCrossCount(parts.get(54));
		mappingDto.setAwayCrossCount(parts.get(55));
		mappingDto.setHomeTackleCount(parts.get(56));
		mappingDto.setAwayTackleCount(parts.get(57));
		mappingDto.setHomeClearCount(parts.get(58));
		mappingDto.setAwayClearCount(parts.get(59));
		mappingDto.setHomeInterceptCount(parts.get(60));
		mappingDto.setAwayInterceptCount(parts.get(61));
		mappingDto.setRecordTime(DateUtil.convertTimestamp(parts.get(62)));
		mappingDto.setNoticeFlg(parts.get(63));
		mappingDto.setGoalTime(parts.get(64));
		mappingDto.setGoalTeamMember(parts.get(65));
		mappingDto.setJudge(parts.get(66));
		return mappingDto;
	}

}
