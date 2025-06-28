package dev.application.common.logic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.BookDataSelectEntity;

/**
 * zero_score_dataテーブルに登録するレコードに紐づく通番を分類するロジック
 * @author shiraishitoshio
 *
 */
@Component
public class TeamStatisticsDataPartsMultiSeparateSequenceLogic {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(TeamStatisticsDataPartsMultiSeparateSequenceLogic.class);

	/** クラス名 */
	private static final String CLASS_NAME = TeamStatisticsDataPartsMultiSeparateSequenceLogic.class.getSimpleName();

	/**
	 * 探索パス(外部設定値)
	 */
	@Value("${bmbusiness.aftercopypath:/Users/shiraishitoshio/bookmaker/conditiondata/}")
	private String findPath = "/Users/shiraishitoshio/bookmaker/conditiondata/";

	/**
	 * コピー先パス(外部設定値)
	 */
	@Value("${bmbusiness.aftercopypath:/Users/shiraishitoshio/bookmaker/conditiondata/}")
	private String copyPath = "/Users/shiraishitoshio/bookmaker/conditiondata/copyfolder";

	/**
	 * 処理実行
	 * @param seq 検索通番
	 * @return 同一ホームチームに紐づく通番群を持つリスト
	 */
	public List<String> separateLogic(int seq) {
		logger.info("sum chk seq counter -> No: {} ", seq);

		logger.info(" TeamStatisticsDataPartsMultiSeparateSequenceLogic start : {} ", CLASS_NAME);

		List<String> seqGroupList = new ArrayList<>();
		List<String> selectList = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M001);
		String[] selList = new String[selectList.size()];
		for (int i = 0; i < selectList.size(); i++) {
			selList[i] = selectList.get(i);
		}

		List<List<String>> selectResultList = null;
		List<BookDataSelectEntity> conditionList = new ArrayList<BookDataSelectEntity>();
		// 通番を設定
		String where = "seq = " + seq;
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M001, selList, where, null, "1");
			if (!selectResultList.isEmpty()) {
				// Entityにマッピングする
				for (List<String> list : selectResultList) {
					BookDataSelectEntity mapSelectDestination = mappingEntity(list);
					conditionList.add(mapSelectDestination);
				}
			} else {
				logger.info("selectResultList no record seq : {} ", seq);
			}
		} catch (Exception e) {
			logger.error("select error -> ", e);
			return null;
		}

		// 条件句に取得してきたホームチームの名前を設定
		String homeTeam = conditionList.get(0).getHomeTeamName();
		String awayTeam = conditionList.get(0).getAwayTeamName();
		where = "home_team_name = '" + homeTeam + "' AND away_team_name = '" + awayTeam + "'";
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M001, selList, where, null, null);
			if (!selectResultList.isEmpty()) {
				for (List<String> selectResult : selectResultList) {
					// 取得したレコードの通番を保存
					seqGroupList.add(selectResult.get(0));
				}
			}
		} catch (Exception e) {
			logger.error("select error -> ", e);
			return null;
		}

		logger.info(" TeamStatisticsDataPartsMultiSeparateSequenceLogic end : {} ", CLASS_NAME);

		return seqGroupList;
	}

	/**
	 * ListからDTOにマッピングをかける
	 * @param mapSource list構造
	 * @return BookDataSelectEntity DTO
	 * @throws Exception
	 */
	private BookDataSelectEntity mappingEntity(List<String> parts) throws Exception {
		BookDataSelectEntity mappingDto = new BookDataSelectEntity();
		mappingDto.setSeq(parts.get(0));
		mappingDto.setHomeTeamName(parts.get(5));
		mappingDto.setAwayTeamName(parts.get(8));
		return mappingDto;
	}

}
