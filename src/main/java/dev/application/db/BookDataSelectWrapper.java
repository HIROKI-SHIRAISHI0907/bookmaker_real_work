package dev.application.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.ModifySequenceResultDTO;
import dev.application.common.dto.ModifySequenceSubResultDTO;
import dev.application.common.util.DateUtil;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.entity.BookDataSelectEntity;

/**
 * DB取得Wrapperクラス
 * @author shiraishitoshio
 *
 */
public class BookDataSelectWrapper {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(BookDataSelectWrapper.class);

	/** 正規表現(1~44を含む文字列) */
	private static final Pattern REGEX_BETWEEN_1_TO_44_ONLY = Pattern
			.compile("^(?:[1-9]|[1-3][0-9]|4[0-4])$");

	private static final Pattern REGEX_BETWEEN_1_TO_9_SINGLE_ONLY = Pattern
			.compile("^[0-9]'");

	private static final Pattern REGEX_BETWEEN_1_TO_9_COLON_ONLY = Pattern
			.compile("^[0-9]:");

	private static final Pattern REGEX_BETWEEN_1_TO_44_OTHER_COLON = Pattern
			.compile("(10:|11:|12:|13:|14:|15:|16:|17:|18:|19:|20:"
					+ "|21:|22:|23:|24:|25:|26:|27:|28:|29:|30:|31:|32:|33:|34:|35:|36:|37:"
					+ "|38:|39:|40:|41:|42:|43:|44:)");

	private static final Pattern REGEX_BETWEEN_1_TO_44_OTHER_SINGLE_QUORT = Pattern
			.compile("^(10'|11'|12'|13'|14'|15'|16'"
					+ "|17'|18'|19'|20'|21'|22'|23'|24'|25'|26'|27'|28'|29'|30'|31'|32'|33'|34'|35'|36'|37'"
					+ "|38'|39'|40'|41'|42'|43'|44')$");

	/** 正規表現(45~120,ハーフタイム,終了済を含む文字列) */
	private static final Pattern REGEX_BETWEEN_45_TO_120_AND_HALFTIME_END_COLON = Pattern
			.compile("(45+|105+|120+|45:|46:|47:|48:|49:|50:|51:|52:|53:|54:|55:|56:|57:|58:|59:|60:|61:|62:|63:"
					+ "|64:|65:|66:|67:|68:|69:|70:|71:|72:|73:|74:|75:|76:|77:|78:|79:|80:"
					+ "|81:|82:|83:|84:|85:|86:|87:|88:|89:|90:)");

	private static final Pattern REGEX_BETWEEN_45_TO_120_AND_HALFTIME_END_SINGLE_QUORT = Pattern
			.compile("^(第一ハーフ|ハーフタイム"
					+ "|45'|46'|47'|48'|49'|50'|51'|52'|53'|54'|55'|56'|57'|58'|59'|60'|61'|62'|63'"
					+ "|64'|65'|66'|67'|68'|69'|70'|71'|72'|73'|74'|75'|76'|77'|78'|79'|80'"
					+ "|81'|82'|83'|84'|85'|86'|87'|88'|89'|90'|91'|92'|93'|94'|95'|96'|97':"
					+ "|98'|99'|100'|101'|102'|103'|104'|105'|106'|107'|108'|109'|110'|111'|112'"
					+ "|113'|114'|115'|116'|117'|118'|119'|120'|終了済)$");

	private static final Pattern REGEX_BETWEEN_45_TO_120_ONLY = Pattern
			.compile("^(?:4[5-9]|[5-9][0-9]|1[0-1][0-9]|120)");

	/** その他 */
	private static final Pattern REGEX_OTHER_GAME_SITUATION = Pattern
			.compile("^(中断中|アワーデッド|放棄|更新まち|更新待ち|延期)$");

	/**
	 * 条件に合うレコードの件数を選択する
	 * @param tableId テーブルID
	 * @param where 条件句
	 * @return List<BookDataSelectEntity>
	 * @throws Exception
	 */
	public int executeCountSelect(String tableId, String where) throws Exception {
		SqlMainLogic select = new SqlMainLogic();
		try {
			return select.executeCountSelect(null, tableId, where);
		} catch (Exception e) {
			logger.error("select count error -> ", e);
			throw e;
		}
	}

	/**
	 * レコードが更新されていない通番の最小値を取得する
	 * @return int
	 * @throws Exception
	 */
	public int executeMinSeqChkNoUpdateRecord() throws Exception {
		SqlMainLogic select = new SqlMainLogic();
		String tableId = UniairConst.BM_M001;
		String where = "condition_result_data_seq_id IS NULL or judge IS NULL ";
		try {
			return select.executeGetMin(null, tableId, "seq", where);
		} catch (Exception e) {
			logger.error("select count error -> ", e);
			throw e;
		}
	}

	/**
	 * 条件に合うレコードを選択する
	 * @param seq 通番
	 * @return List<BookDataSelectEntity>
	 * @throws Exception
	 */
	public List<BookDataSelectEntity> executeSelect(int seq, List<BookDataSelectEntity> lists,
			boolean seqNoFlg) throws Exception {

		SqlMainLogic select = new SqlMainLogic();
		String dataCategory = null;
		String homeTeam = null;
		String awayTeam = null;
		String tableId = UniairConst.BM_M001;
		// 取得したいカラムを設定
		List<String> selectList = UniairColumnMapUtil.getKeyMap(tableId);
		String[] selList = new String[selectList.size()];
		for (int i = 0; i < selectList.size(); i++) {
			selList[i] = selectList.get(i);
		}
		if (!seqNoFlg) {
			// 任意の1件取得
			List<List<String>> selectResultList = null;
			List<BookDataSelectEntity> conditionList = new ArrayList<BookDataSelectEntity>();
			String where = "seq = " + seq;
			try {
				selectResultList = select.executeSelect(null, tableId, selList, where, null, "1");
				// Entityにマッピングする
				for (List<String> list : selectResultList) {
					BookDataSelectEntity mapSelectDestination = mappingEntity(0, list);
					conditionList.add(mapSelectDestination);
				}
			} catch (Exception e) {
				logger.error("select error -> ", e);
				throw e;
			}
			// マッピングしたEntityから該当条件に合うものを条件句にして再度Selectをかける
			// 条件句はデータカテゴリ,ホームチーム,アウェーチームとする
			dataCategory = conditionList.get(0).getDataCategory();
			homeTeam = conditionList.get(0).getHomeTeamName();
			awayTeam = conditionList.get(0).getAwayTeamName();
		} else {
			// マッピングしたEntityから該当条件に合うものを条件句にして再度Selectをかける
			// 条件句はデータカテゴリ,ホームチーム,アウェーチームとする
			dataCategory = lists.get(0).getDataCategory();
			homeTeam = lists.get(0).getHomeTeamName();
			awayTeam = lists.get(0).getAwayTeamName();
		}

		// チーム名にシングルクウォーテーションが含まれている場合があるためその場合はエスケープする。
		if (homeTeam.contains("'"))
			homeTeam = homeTeam.replace("'", "\\'");
		if (awayTeam.contains("'"))
			awayTeam = awayTeam.replace("'", "\\'");
		String where = "data_category = '" + dataCategory + "' "
				+ "AND home_team_name = '" + homeTeam + "' "
				+ "AND away_team_name = '" + awayTeam + "' ";
		String sort = "CASE "
				+ " WHEN times LIKE '45:%' OR times LIKE '45+' THEN 1 "
				+ " WHEN times = 'ハーフタイム' OR times = '第一ハーフ' THEN 2 "
				+ " WHEN times LIKE '46:%' THEN 3 "
				+ " WHEN times REGEXP '^[0-9]+:[0-9]{2}$' THEN 4 "
				+ " ELSE 5 "
				+ " END, times, "
				+ " home_score, "
				+ " away_score,"
				+ " home_shoot_all, "
				+ " away_shoot_all, "
				+ " home_shoot_in, "
				+ " away_shoot_in, "
				+ " home_shoot_out, "
				+ " away_shoot_out, "
				+ " home_block_shoot, "
				+ " away_block_shoot, "
				+ " home_big_chance, "
				+ " away_big_chance, "
				+ " home_corner, "
				+ " away_corner, "
				+ " home_box_shoot_in, "
				+ " away_box_shoot_in, "
				+ " home_box_shoot_out, "
				+ " away_box_shoot_out, "
				+ " home_goal_post, "
				+ " away_goal_post, "
				+ " home_keeper_save, "
				+ " away_keeper_save, "
				+ " home_free_kick, "
				+ " away_free_kick, "
				+ " home_offside, "
				+ " away_offside, "
				+ " home_foul, "
				+ " away_foul, "
				+ " home_yellow_card, "
				+ " away_yellow_card, "
				+ " home_red_card, "
				+ " away_red_card, "
				+ " home_slow_in, "
				+ " away_slow_in, "
				+ " seq ";
		List<List<String>> selectResultMultipleList = null;
		List<BookDataSelectEntity> conditionMultipleList = new ArrayList<BookDataSelectEntity>();
		// timesが「---」のもの
		List<BookDataSelectEntity> conditionTimesHyphenList = new ArrayList<BookDataSelectEntity>();
		try {
			selectResultMultipleList = select.executeSelect(null, tableId, selList, where, sort, null);
			// 空の場合return
			if (selectResultMultipleList.isEmpty())
				return new ArrayList<BookDataSelectEntity>();

			// Entityにマッピングする(内部通番をこの時付与する)
			int innerSeq = 1;
			for (List<String> list : selectResultMultipleList) {
				BookDataSelectEntity mapSelectMultipleDestination = mappingEntity(innerSeq, list);
				conditionMultipleList.add(mapSelectMultipleDestination);
				if ("---".equals(mapSelectMultipleDestination.getTimes())) {
					conditionTimesHyphenList.add(mapSelectMultipleDestination);
				}
				innerSeq++;
			}
		} catch (Exception e) {
			logger.error("multiple select error -> ", e);
			throw e;
		}

		// judge,condition_seq_idが全て埋まっていれば空リスト返却
		boolean allNotEmptyFlg = true;
		if (!seqNoFlg) {
			for (BookDataSelectEntity entity : conditionMultipleList) {
				if (entity.getConditionResultDataSeqId() == null
						&& entity.getJudge() == null) {
					allNotEmptyFlg = false;
					break;
				}
			}
		}

		if (!seqNoFlg && allNotEmptyFlg) {
			return new ArrayList<BookDataSelectEntity>();
		}

		// レコードが1件のみの場合そのまま返却
		if (conditionMultipleList.size() < 2) {
			return conditionMultipleList;
		}

		// conditionMultipleListに格納した値は以下のソートが考慮されていないため,編集する。
		// 1. timesが25',45:XX,46:XXなどの場合,ハーフタイムよりも前の時間,後の時間の場合があるため時系列が適当になっている場合がある
		List<BookDataSelectEntity> beforeHalfTimeList = new ArrayList<BookDataSelectEntity>();
		// timesが25',38',などハーフタイム以前までの時間を集める
		for (BookDataSelectEntity entity : conditionMultipleList) {
			Matcher matcher = REGEX_BETWEEN_1_TO_9_SINGLE_ONLY.matcher(entity.getTimes());
			if (matcher.find()) {
				beforeHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_1_TO_9_COLON_ONLY.matcher(entity.getTimes());
			if (matcher.find()) {
				beforeHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_1_TO_44_OTHER_SINGLE_QUORT.matcher(entity.getTimes());
			if (matcher.find()) {
				beforeHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_1_TO_44_OTHER_COLON.matcher(entity.getTimes());
			if (matcher.find()) {
				beforeHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_1_TO_44_ONLY.matcher(entity.getTimes());
			if (matcher.find()) {
				beforeHalfTimeList.add(entity);
			}
		}

		ModifySequenceResultDTO resultDTO = new ModifySequenceResultDTO();
		// 交換必須フラグをtrueに初期化
		resultDTO.setExchangeFlg(true);

		// ハーフタイム以前がないもしくは1レコードのみの場合入れ替えの必要がないため,次の処理へ
		List<BookDataSelectEntity> beforeExchangeList = new ArrayList<BookDataSelectEntity>();
		if (!beforeHalfTimeList.isEmpty() && beforeHalfTimeList.size() != 1) {
			// 2レコード以上存在の場合,交換必須フラグがfalseになるまでListの入れ替えを繰り返す
			if (beforeHalfTimeList.size() >= 2) {
				while (resultDTO.isExchangeFlg()) {
					resultDTO = modifySequenceRecursive(resultDTO, beforeHalfTimeList);
				}
			}
			beforeExchangeList = resultDTO.getExchangeList();
		} else {
			beforeExchangeList = beforeHalfTimeList;
		}

		// 交換必須フラグをtrueに初期化
		resultDTO.setExchangeFlg(true);

		List<BookDataSelectEntity> nearHalfTimeList = new ArrayList<BookDataSelectEntity>();
		// timesが45:XX,46:XXなどハーフタイム以降(ハーフタイム,終了済も含む)120分までの時間を集める
		for (BookDataSelectEntity entity : conditionMultipleList) {
			Matcher matcher = REGEX_BETWEEN_45_TO_120_AND_HALFTIME_END_SINGLE_QUORT.matcher(entity.getTimes());
			if (matcher.find()) {
				nearHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_45_TO_120_AND_HALFTIME_END_COLON.matcher(entity.getTimes());
			if (matcher.find()) {
				nearHalfTimeList.add(entity);
			}
			matcher = REGEX_BETWEEN_45_TO_120_ONLY.matcher(entity.getTimes());
			if (matcher.find()) {
				nearHalfTimeList.add(entity);
			}
		}

		resultDTO = new ModifySequenceResultDTO();
		// 交換必須フラグをtrueに初期化
		resultDTO.setExchangeFlg(true);

		// ハーフタイム以降がないもしくは1レコードのみの場合入れ替えの必要がないため,そのまま返却
		List<BookDataSelectEntity> afterExchangeList = new ArrayList<BookDataSelectEntity>();
		if (!nearHalfTimeList.isEmpty() && nearHalfTimeList.size() != 1) {
			// 2レコード以上存在の場合,交換必須フラグがfalseになるまでListの入れ替えを繰り返す
			if (nearHalfTimeList.size() >= 2) {
				while (resultDTO.isExchangeFlg()) {
					resultDTO = modifySequenceRecursive(resultDTO, nearHalfTimeList);
				}
			}
			afterExchangeList = resultDTO.getExchangeList();
		} else {
			afterExchangeList = nearHalfTimeList;
		}

		//中断中,アワーデッド,放棄は特に入れ替えしない,後ほど通番が若い順になるよう入れ込む
		List<BookDataSelectEntity> otherList = new ArrayList<BookDataSelectEntity>();
		for (BookDataSelectEntity entity : conditionMultipleList) {
			Matcher matcher = REGEX_OTHER_GAME_SITUATION.matcher(entity.getTimes());
			if (matcher.find()) {
				otherList.add(entity);
			}
		}

		// 重複を除いてマージ
		List<BookDataSelectEntity> allExchangeList = new ArrayList<BookDataSelectEntity>();
		for (BookDataSelectEntity entity : beforeExchangeList) {
			if (!allExchangeList.contains(entity)) {
				allExchangeList.add(entity);
			}
		}
		for (BookDataSelectEntity entity : afterExchangeList) {
			if (!allExchangeList.contains(entity)) {
				allExchangeList.add(entity);
			}
		}
		for (BookDataSelectEntity entity : conditionTimesHyphenList) {
			if (!allExchangeList.contains(entity)) {
				allExchangeList.add(entity);
			}
		}

		// その他情報をソートはせず通番が若くなるように入れ込む
		for (BookDataSelectEntity entity : otherList) {
			for (int i = 0; i < allExchangeList.size(); i++) {
				if (Integer.parseInt(allExchangeList.get(i).getSeq()) > Integer.parseInt(entity.getSeq())) {
					allExchangeList.add(i, entity);
					break;
				}
			}
			allExchangeList.add(entity); // すべての要素より大きい場合は最後に追加
		}

		return allExchangeList;
	}

	/**
	 * ListからDTOにマッピングをかける
	 * @param innerSeq 内部通番
	 * @param mapSource list構造
	 * @return BookDataSelectEntity DTO
	 * @throws Exception
	 */
	private BookDataSelectEntity mappingEntity(int innerSeq, List<String> parts) throws Exception {
		BookDataSelectEntity mappingDto = new BookDataSelectEntity();
		mappingDto.setInnerSeq(String.valueOf(innerSeq));
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
		mappingDto.setWeather(parts.get(63));
		mappingDto.setTemparature(parts.get(64));
		mappingDto.setHumid(parts.get(65));
		mappingDto.setJudgeMember(parts.get(66));
		mappingDto.setHomeManager(parts.get(67));
		mappingDto.setAwayManager(parts.get(68));
		mappingDto.setHomeFormation(parts.get(69));
		mappingDto.setAwayFormation(parts.get(70));
		mappingDto.setStudium(parts.get(71));
		mappingDto.setCapacity(parts.get(72));
		mappingDto.setAudience(parts.get(73));
		mappingDto.setHomeMaxGettingScorer(parts.get(74));
		mappingDto.setAwayMaxGettingScorer(parts.get(75));
		mappingDto.setHomeMaxGettingScorerGameSituation(parts.get(76));
		mappingDto.setAwayMaxGettingScorerGameSituation(parts.get(77));
		mappingDto.setHomeTeamHomeScore(parts.get(78));
		mappingDto.setHomeTeamHomeLost(parts.get(79));
		mappingDto.setAwayTeamHomeScore(parts.get(80));
		mappingDto.setAwayTeamHomeLost(parts.get(81));
		mappingDto.setHomeTeamAwayScore(parts.get(82));
		mappingDto.setHomeTeamAwayLost(parts.get(83));
		mappingDto.setAwayTeamAwayScore(parts.get(84));
		mappingDto.setAwayTeamAwayLost(parts.get(85));
		mappingDto.setNoticeFlg(parts.get(86));
		mappingDto.setGoalTime(parts.get(87));
		mappingDto.setGoalTeamMember(parts.get(88));
		mappingDto.setJudge(parts.get(89));
		mappingDto.setHomeTeamStyle(parts.get(90));
		mappingDto.setAwayTeamStyle(parts.get(91));
		mappingDto.setProbablity(parts.get(92));
		mappingDto.setPredictionScoreTime(parts.get(93));
		return mappingDto;
	}

	/**
	 * timesが45:XX,46:XXなどハーフタイム付近(ハーフタイムも含む)の時間を含むデータ群を時系列順に修正するメソッド(再帰的に呼び出す)
	 * @param targetDTO 並び替え元DTO
	 * @param entityList BookDataSelectEntity型を持つリスト構造
	 * @return ModifySequenceResultDTO 並び替え結果DTO
	 */
	private ModifySequenceResultDTO modifySequenceRecursive(ModifySequenceResultDTO targetDTO,
			List<BookDataSelectEntity> entityList) {

		// 通番リストを保持
		if (targetDTO.getSeqList() == null || targetDTO.getSeqList().isEmpty()) {
			List<Integer> exSeqList = new ArrayList<>();
			for (BookDataSelectEntity seqEntity : entityList) {
				exSeqList.add(Integer.parseInt(seqEntity.getSeq()));
			}
			targetDTO.setSeqList(exSeqList);
		}

		// チェック済み通番リストを保持
		if (targetDTO.getChkSeqList() == null) {
			List<String> seqList = new ArrayList<>();
			targetDTO.setChkSeqList(seqList);
		}

		// チェックしたか
		boolean chkFlg = false;
		for (int i = 0; i < entityList.size() - 1; i++) {
			if (chkFlg)
				return targetDTO;
			for (int j = i + 1; j < entityList.size(); j++) {
				// チェックを行った通番同士の組み合わせに含まれていればチェックスキップ
				String befSeq = entityList.get(i).getSeq() + "-" + entityList.get(j).getSeq();
				String afSeq = entityList.get(j).getSeq() + "-" + entityList.get(i).getSeq();

				List<String> getSeqList = targetDTO.getChkSeqList();

				if (getSeqList.contains(befSeq))
					continue;

				// チェックを行った通番同士の組み合わせを保持しておく
				getSeqList.add(befSeq);
				getSeqList.add(afSeq);
				targetDTO.setChkSeqList(getSeqList);

				ModifySequenceSubResultDTO subResultDTO = new ModifySequenceSubResultDTO();

				// 通番で昇順にできるなら昇順にする
				//				subResultDTO = compareMethod(i, j, entityList.get(i).getSeq(),
				//						entityList.get(j).getSeq(), entityList.get(i), entityList.get(j), entityList,
				//						subResultDTO);
				//				if (subResultDTO.isExchangeFlg()) {
				//					targetDTO.setExchangeFlg(true);
				//					targetDTO.setExchangeList(entityList);
				//					chkFlg = true;
				//					break;
				//				}

				// 統計情報がない場合,もしくは時間で昇順にできる場合,時間で昇順にし,最終的に時系列順にする
				subResultDTO = compareMethodWithoutData(i, j, entityList.get(i).getTimes(),
						entityList.get(j).getTimes(), entityList.get(i), entityList.get(j), entityList, subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 強制交換フラグがtrueになった(実際は入れ替えが発生していない)場合は同じindexのチェックを防ぐため,クリアしない
					if (!subResultDTO.isForceExchangeFlg()) {
						targetDTO.getChkSeqList().clear();
					}
					chkFlg = true;
					break;
				}

				// 以下直前のEntity情報より直後のEntity情報の数字が増えているか確認,増えていない(減っている)場合は入れ替え対象,
				// 入れ替えが発生した場合は,break
				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeShootAll(),
						entityList.get(j).getHomeShootAll(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayShootAll(),
						entityList.get(j).getAwayShootAll(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeShootIn(),
						entityList.get(j).getHomeShootIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayShootIn(),
						entityList.get(j).getAwayShootIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeShootOut(),
						entityList.get(j).getHomeShootOut(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayShootOut(),
						entityList.get(j).getAwayShootOut(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeBlockShoot(),
						entityList.get(j).getHomeBlockShoot(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayBlockShoot(),
						entityList.get(j).getAwayBlockShoot(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeBigChance(),
						entityList.get(j).getHomeBigChance(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayBigChance(),
						entityList.get(j).getAwayBigChance(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeCorner(),
						entityList.get(j).getHomeCorner(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayCorner(),
						entityList.get(j).getAwayCorner(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeBoxShootIn(),
						entityList.get(j).getHomeBoxShootIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayBoxShootIn(),
						entityList.get(j).getAwayBoxShootIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeBoxShootOut(),
						entityList.get(j).getHomeBoxShootOut(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayBoxShootOut(),
						entityList.get(j).getAwayBoxShootOut(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeKeeperSave(),
						entityList.get(j).getHomeKeeperSave(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayKeeperSave(),
						entityList.get(j).getAwayKeeperSave(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeFreeKick(),
						entityList.get(j).getHomeFreeKick(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayFreeKick(),
						entityList.get(j).getAwayFreeKick(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeOffside(),
						entityList.get(j).getHomeOffside(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayOffside(),
						entityList.get(j).getAwayOffside(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeFoul(),
						entityList.get(j).getHomeFoul(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayFoul(),
						entityList.get(j).getAwayFoul(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeYellowCard(),
						entityList.get(j).getHomeYellowCard(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayYellowCard(),
						entityList.get(j).getAwayYellowCard(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeRedCard(),
						entityList.get(j).getHomeRedCard(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayRedCard(),
						entityList.get(j).getAwayRedCard(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeSlowIn(),
						entityList.get(j).getHomeSlowIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwaySlowIn(),
						entityList.get(j).getAwaySlowIn(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getHomeBoxTouch(),
						entityList.get(j).getHomeBoxTouch(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMethod(i, j, entityList.get(i).getAwayBoxTouch(),
						entityList.get(j).getAwayBoxTouch(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomePassCount(),
						entityList.get(j).getHomePassCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayPassCount(),
						entityList.get(j).getAwayPassCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomeFinalThirdPassCount(),
						entityList.get(j).getHomeFinalThirdPassCount(), entityList.get(i), entityList.get(j),
						entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayFinalThirdPassCount(),
						entityList.get(j).getAwayFinalThirdPassCount(), entityList.get(i), entityList.get(j),
						entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomeCrossCount(),
						entityList.get(j).getHomeCrossCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayCrossCount(),
						entityList.get(j).getAwayCrossCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomeTackleCount(),
						entityList.get(j).getHomeTackleCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayTackleCount(),
						entityList.get(j).getAwayTackleCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomeClearCount(),
						entityList.get(j).getHomeClearCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayClearCount(),
						entityList.get(j).getAwayClearCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getHomeInterceptCount(),
						entityList.get(j).getHomeInterceptCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}

				subResultDTO = compareMultipleDataMethod(i, j, entityList.get(i).getAwayInterceptCount(),
						entityList.get(j).getAwayInterceptCount(), entityList.get(i), entityList.get(j), entityList,
						subResultDTO);
				if (subResultDTO.isExchangeFlg()) {
					targetDTO.setExchangeFlg(true);
					targetDTO.setExchangeList(entityList);
					// 入れ替えるとentityListのindexの順番が変わるためチェックを行った通番同士の組み合わせをリセットする
					targetDTO.getChkSeqList().clear();
					chkFlg = true;
					break;
				}
			}
		}

		// どの条件も満たさない場合入れ替え完了とし,交換必須フラグをfalseに変更
		targetDTO.setExchangeFlg(false);
		return targetDTO;
	}

	/**
	 * パスデータ,クリアデータなど複数が1項目になっているデータを比較して交換するメソッド
	 * @param seq1 for文(i)
	 * @param seq2 for文(j)
	 * @param comp1 比較対象1
	 * @param comp2 比較対象2
	 * @param entity1 比較対象Entity1
	 * @param entity2 比較対象Entity2
	 * @param entityList 交換対象のリスト
	 * @param subResultDTO サブDTO
	 * @return true:交換した, false:交換していない
	 */
	private ModifySequenceSubResultDTO compareMultipleDataMethod(int seq1, int seq2, String comp1, String comp2,
			BookDataSelectEntity entity1, BookDataSelectEntity entity2, List<BookDataSelectEntity> entityList,
			ModifySequenceSubResultDTO subResultDTO) {
		// 統計情報がある場合
		if (!"".equals(comp1) && comp1 != null
				&& !"".equals(comp2) && comp2 != null) {
			// 「/」と「)」の間を取得する
			int firstIndex1 = 0;
			int secondIndex1 = 0;
			if (comp1.contains("/")) {
				firstIndex1 = comp1.indexOf("/");
			}
			if (comp1.contains(")")) {
				secondIndex1 = comp1.indexOf(")");
			}
			comp1 = comp1.substring(firstIndex1 + 1, secondIndex1);
			// 「/」と「)」の間を取得する
			int firstIndex2 = 0;
			int secondIndex2 = 0;
			if (comp2.contains("/")) {
				firstIndex2 = comp2.indexOf("/");
			}
			if (comp2.contains(")")) {
				secondIndex2 = comp2.indexOf(")");
			}
			comp2 = comp2.substring(firstIndex2 + 1, secondIndex2);
			if (Integer.parseInt(comp1) > Integer
					.parseInt(comp2)) {
				BookDataSelectEntity exEntity = entity1;
				BookDataSelectEntity targetEntity = entity2;
				entityList.set(seq1, targetEntity);
				entityList.set(seq2, exEntity);
				subResultDTO.setExchangeFlg(true);
			} else {
				subResultDTO.setExchangeFlg(false);
			}
			subResultDTO.setExchangeList(entityList);
			return subResultDTO;
		}

		subResultDTO.setExchangeFlg(false);
		subResultDTO.setExchangeList(entityList);
		return subResultDTO;
	}

	/**
	 * 比較して交換するメソッド
	 * @param seq1 for文(i)
	 * @param seq2 for文(j)
	 * @param comp1 比較対象1
	 * @param comp2 比較対象2
	 * @param entity1 比較対象Entity1
	 * @param entity2 比較対象Entity2
	 * @param entityList 交換対象のリスト
	 * @param subResultDTO サブDTO
	 * @return true:交換した, false:交換していない
	 */
	private ModifySequenceSubResultDTO compareMethod(int seq1, int seq2, String comp1, String comp2,
			BookDataSelectEntity entity1, BookDataSelectEntity entity2, List<BookDataSelectEntity> entityList,
			ModifySequenceSubResultDTO subResultDTO) {
		// 統計情報がある場合
		if (!"".equals(comp1) && comp1 != null
				&& !"".equals(comp2) && comp2 != null) {
			if (Integer.parseInt(comp1) > Integer
					.parseInt(comp2)) {
				BookDataSelectEntity exEntity = entity1;
				BookDataSelectEntity targetEntity = entity2;
				entityList.set(seq1, targetEntity);
				entityList.set(seq2, exEntity);
				subResultDTO.setExchangeFlg(true);
			} else {
				subResultDTO.setExchangeFlg(false);
			}
			subResultDTO.setExchangeList(entityList);
			return subResultDTO;
		}

		subResultDTO.setExchangeFlg(false);
		subResultDTO.setExchangeList(entityList);
		return subResultDTO;
	}

	/**
	 * 統計データがない場合比較して交換するメソッド
	 * 比較は時間で行う
	 * @param seq1 for文(i)
	 * @param seq2 for文(j)
	 * @param times1 時間1
	 * @param times2 時間2
	 * @param entity1 比較対象Entity1
	 * @param entity2 比較対象Entity2
	 * @param entityList 交換対象のリスト
	 * @param subResultDTO サブDTO
	 * @return true:交換した, false:交換していない
	 */
	private ModifySequenceSubResultDTO compareMethodWithoutData(int seq1, int seq2, String times1, String times2,
			BookDataSelectEntity entity1, BookDataSelectEntity entity2, List<BookDataSelectEntity> entityList,
			ModifySequenceSubResultDTO subResultDTO) {
		// 統計情報がない場合(アフリカなどは基本こちら)
		if (!"".equals(times1) && times1 != null
				&& !"".equals(times2) && times2 != null &&
				!BookMakersCommonConst.HALF_TIME.equals(times1) &&
				!BookMakersCommonConst.FIRST_HALF_TIME.equals(times1) &&
				!BookMakersCommonConst.FIN.equals(times1) && !"---".equals(times1) &&
				!BookMakersCommonConst.HALF_TIME.equals(times2) &&
				!BookMakersCommonConst.FIRST_HALF_TIME.equals(times2) &&
				!BookMakersCommonConst.FIN.equals(times2) && !"---".equals(times2)) {
			// 45+,45+3,90+1のような値の場合,+を0に置き換え
			boolean plusFlg1 = false;
			boolean plusFlg2 = false;
			if (times1.contains("+")) {
				times1 = times1.replace("+", "0");
				plusFlg1 = true;
			}
			if (times2.contains("+")) {
				times2 = times2.replace("+", "0");
				plusFlg2 = true;
			}
			int time1Int = (times1.contains(":")) ? Integer.parseInt(times1.replace(":", ""))
					: Integer.parseInt(times1.replace("\'", ""));
			int time2Int = (times2.contains(":")) ? Integer.parseInt(times2.replace(":", ""))
					: Integer.parseInt(times2.replace("\'", ""));

			// 時間が取得できた場合,入れ替えが発生しなくても交換必須フラグをtrue
			boolean forceFlg = false;
			if (!plusFlg1 && !plusFlg2 && time1Int > 0 && time2Int > 0) {
				forceFlg = true;
			}
			// +付きの時間が取得できた場合,発生しなくても交換必須フラグをtrue
			if (plusFlg1 && plusFlg2) {
				forceFlg = true;
			}

			if (time1Int > time2Int) {
				BookDataSelectEntity exEntity = entity1;
				BookDataSelectEntity targetEntity = entity2;
				entityList.set(seq1, targetEntity);
				entityList.set(seq2, exEntity);
				subResultDTO.setExchangeFlg(true);
			} else {
				subResultDTO.setExchangeFlg(false);
				if (forceFlg) {
					subResultDTO.setExchangeFlg(true);
					subResultDTO.setForceExchangeFlg(true);
				}
			}
			subResultDTO.setExchangeList(entityList);
			return subResultDTO;
		}

		subResultDTO.setExchangeFlg(false);
		// ハーフタイムと終了済と---は特別扱いでtrue
		if (BookMakersCommonConst.HALF_TIME.equals(times1) ||
				BookMakersCommonConst.FIRST_HALF_TIME.equals(times1) ||
				BookMakersCommonConst.FIN.equals(times1) ||
				"---".equals(times1)) {
			subResultDTO.setExchangeFlg(true);
			subResultDTO.setForceExchangeFlg(true);
		} else if (BookMakersCommonConst.HALF_TIME.equals(times2) ||
				BookMakersCommonConst.FIRST_HALF_TIME.equals(times2) ||
				BookMakersCommonConst.FIN.equals(times2) ||
				"---".equals(times2)) {
			subResultDTO.setExchangeFlg(true);
			subResultDTO.setForceExchangeFlg(true);
		}
		subResultDTO.setExchangeList(entityList);
		return subResultDTO;
	}
}
