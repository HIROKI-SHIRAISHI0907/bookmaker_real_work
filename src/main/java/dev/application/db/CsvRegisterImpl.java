package dev.application.db;

import java.util.ArrayList;
import java.util.List;

import dev.application.common.mapping.CorrelationSummary;
import dev.application.common.mapping.StatSummary;
import dev.application.common.util.UniairColumnMapUtil;
import dev.application.entity.AverageFeatureEntity;
import dev.application.entity.AverageStatisticsCsvTmpDataEntity;
import dev.application.entity.AverageStatisticsDetailEntity;
import dev.application.entity.AverageStatisticsEntity;
import dev.application.entity.AverageStatisticsTeamDetailEntity;
import dev.application.entity.BookDataInsertEntity;
import dev.application.entity.ClassifyResultDataDetailEntity;
import dev.application.entity.ClassifyResultDataEntity;
import dev.application.entity.CollectRangeScoreEntity;
import dev.application.entity.ConditionResultDataEntity;
import dev.application.entity.CorrelationDetailEntity;
import dev.application.entity.CorrelationEntity;
import dev.application.entity.DiffFeatureEntity;
import dev.application.entity.FileChkEntity;
import dev.application.entity.FileChkTmpEntity;
import dev.application.entity.NoScoredEntity;
import dev.application.entity.TeamStatisticsDataEntity;
import dev.application.entity.TypeOfCountryLeagueDataEntity;
import dev.application.entity.UpdCsvInfoEntity;
import dev.application.entity.WithinDataScoredCounterDetailEntity;
import dev.application.entity.WithinDataScoredCounterEntity;
import dev.application.entity.WithinDataXMinutesAllLeagueEntity;
import dev.application.entity.WithinDataXMinutesEntity;

/**
 * Csvから特定のマスタに登録する汎用クラス
 * @author shiraishitoshio
 *
 */
public class CsvRegisterImpl implements CsvRegisterIF {

	/** Logger */
	//private static final Logger logger = LoggerFactory.getLogger(CsvRegisterImpl.class);

	/**
	 * コネクションオブジェクト
	 */
	private JdbcConnection cons;
	/**
	 * SQL作成用ロジッククラス
	 */
	private SqlMainLogic mainLogic;
	/**
	 * 列名用文字列
	 */
	private String columnSql;
	/**
	 * 参照権限
	 */
	public String auth;

	/**
	 * コンストラクタ
	 */
	public CsvRegisterImpl() {
		// メイン
		this.mainLogic = new SqlMainLogic();
		this.cons = new JdbcConnection();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeCreateDB(String db) throws Exception {
		this.mainLogic.executeCreateDataBase(db);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeDrop(String auth, String tableId) throws Exception {
		this.mainLogic.executeDrop(auth, tableId);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeCreate(String auth, String tableId) throws Exception {
		this.mainLogic.executeCreate(auth, tableId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeTruncate(String auth, String tableId) throws Exception {
		this.mainLogic.executeTruncate(auth, tableId);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void executeInsert(String tableId, List<?> dataList, int eachCount, int allCount)
			throws Exception {
		//logger.info(" register start : {} " , CLASS_NAME);

		// コネクション情報
		this.cons.setNoneAutoCommitCon(UniairConst.DB);
		this.mainLogic.con = this.cons.getCon();

		// INSERT SQL生成
		String sqlTableQuery = getColumnSql(this.auth, tableId);
		// 登録件数取得
		int sumCount = 0;
		// リスト変換
		List<String[]> workList = null;
		if (dataList instanceof List) {
			if (!dataList.isEmpty() && dataList.get(0) instanceof BookDataInsertEntity) {
				List<BookDataInsertEntity> dataConvertList = (List<BookDataInsertEntity>) dataList;
				workList = convertBookDataInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof ConditionResultDataEntity) {
				List<ConditionResultDataEntity> dataConvertList = (List<ConditionResultDataEntity>) dataList;
				workList = convertResultDataInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof TeamStatisticsDataEntity) {
				List<TeamStatisticsDataEntity> dataConvertList = (List<TeamStatisticsDataEntity>) dataList;
				workList = convertTeamStatisticsDataInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof TypeOfCountryLeagueDataEntity) {
				List<TypeOfCountryLeagueDataEntity> dataConvertList = (List<TypeOfCountryLeagueDataEntity>) dataList;
				workList = convertTypeOfCountryLeagueDataInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof WithinDataXMinutesEntity) {
				List<WithinDataXMinutesEntity> dataConvertList = (List<WithinDataXMinutesEntity>) dataList;
				workList = convertWithinDataXMinutesInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof WithinDataXMinutesAllLeagueEntity) {
				List<WithinDataXMinutesAllLeagueEntity> dataConvertList = (List<WithinDataXMinutesAllLeagueEntity>) dataList;
				workList = convertWithinDataXMinutesWithAllLeagueInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof WithinDataScoredCounterEntity) {
				List<WithinDataScoredCounterEntity> dataConvertList = (List<WithinDataScoredCounterEntity>) dataList;
				workList = convertWithinDataScoredCounterInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof WithinDataScoredCounterDetailEntity) {
				List<WithinDataScoredCounterDetailEntity> dataConvertList = (List<WithinDataScoredCounterDetailEntity>) dataList;
				workList = convertWithinDataScoredCounterDetailInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof FileChkEntity) {
				List<FileChkEntity> dataConvertList = (List<FileChkEntity>) dataList;
				workList = convertFileChkInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof ClassifyResultDataEntity) {
				List<ClassifyResultDataEntity> dataConvertList = (List<ClassifyResultDataEntity>) dataList;
				workList = convertClassifyResultDataInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof ClassifyResultDataDetailEntity) {
				List<ClassifyResultDataDetailEntity> dataConvertList = (List<ClassifyResultDataDetailEntity>) dataList;
				workList = convertClassifyResultDataDetailInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof FileChkTmpEntity) {
				List<FileChkTmpEntity> dataConvertList = (List<FileChkTmpEntity>) dataList;
				workList = convertFileChkTmpInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof NoScoredEntity) {
				List<NoScoredEntity> dataConvertList = (List<NoScoredEntity>) dataList;
				workList = convertNoScoredInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof AverageFeatureEntity) {
				List<AverageFeatureEntity> dataConvertList = (List<AverageFeatureEntity>) dataList;
				workList = convertAverageFeatureInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof DiffFeatureEntity) {
				List<DiffFeatureEntity> dataConvertList = (List<DiffFeatureEntity>) dataList;
				workList = convertDiffEntityInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof AverageStatisticsEntity) {
				List<AverageStatisticsEntity> dataConvertList = (List<AverageStatisticsEntity>) dataList;
				workList = convertAverageStatisticsEntityInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof UpdCsvInfoEntity) {
				List<UpdCsvInfoEntity> dataConvertList = (List<UpdCsvInfoEntity>) dataList;
				workList = convertUpdCsvInfoInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof CorrelationEntity) {
				List<CorrelationEntity> dataConvertList = (List<CorrelationEntity>) dataList;
				workList = convertCorrelationInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof CorrelationDetailEntity) {
				List<CorrelationDetailEntity> dataConvertList = (List<CorrelationDetailEntity>) dataList;
				workList = convertCorrelationDetailInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof AverageStatisticsDetailEntity) {
				List<AverageStatisticsDetailEntity> dataConvertList = (List<AverageStatisticsDetailEntity>) dataList;
				workList = convertAverageStatisticsDetailEntityInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof AverageStatisticsCsvTmpDataEntity) {
				List<AverageStatisticsCsvTmpDataEntity> dataConvertList = (List<AverageStatisticsCsvTmpDataEntity>) dataList;
				workList = convertAverageStatisticsCsvTmpDataEntityInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof AverageStatisticsTeamDetailEntity) {
				List<AverageStatisticsTeamDetailEntity> dataConvertList = (List<AverageStatisticsTeamDetailEntity>) dataList;
				workList = convertAverageStatisticsTeamDetailEntityInsertList(dataConvertList);
			} else if (!dataList.isEmpty() && dataList.get(0) instanceof CollectRangeScoreEntity) {
				List<CollectRangeScoreEntity> dataConvertList = (List<CollectRangeScoreEntity>) dataList;
				workList = convertCollectRangeScoreEntityInsertList(dataConvertList);
			}
		}
		while (true) {
			List<String[]> workInsertList = new ArrayList<String[]>();
			// 登録する実データを抜き出す
			int insIndex = Math.min(eachCount, workList.size() - sumCount);
			for (int addIndex = sumCount; addIndex < (sumCount + insIndex); addIndex++) {
				workInsertList.add(workList.get(addIndex));
			}

			int insertCount = 0;
			// 処理単位件数ごとに登録を行う
			while (insertCount < workInsertList.size()) {
				// トランザクションコミット
				int result = 0;
				try {
					result = this.mainLogic.executeInsert(this.auth, tableId, sqlTableQuery,
							workInsertList.get(insertCount));
				} catch (Exception e) {
					//logger.error("register error -> " , e);
					throw e;
				}

				// 登録件数導出
				insertCount += result;
				// 単位処理件数に一時的に到達すれば登録
				if (insertCount % eachCount == 0) {
					this.cons.getCon().commit();
					//logger.info(" register count info : {} 件登録しました。" , insertCount);
					sumCount += insertCount;
					workInsertList.clear();
				}
			}

			// 残レコードを登録
			if (workInsertList.size() > 0) {
				this.cons.getCon().commit();
				//logger.info(" register count info : {} 件登録しました。" , insertCount);
				sumCount += insertCount;
				workInsertList.clear();
			}

			// 全体が登録できていればbreak
			if (sumCount == allCount) {
				//logger.info(" register count all info : 全 {} 件登録しました。" , sumCount);
				break;
			}
		}
		// SQL,テーブル名,各行のデータ数を初期化
		this.columnSql = null;
		this.cons.tableName = null;
		this.cons.getCon().close();

		//logger.info(" register end : {} " , CLASS_NAME);
	}

	/**
	 * 列名SQL作成
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @param row 何行目か
	 */
	private String getColumnSql(String auth, String tableId) {
		// すでに作成ずみならreturn
		if (this.columnSql != null) {
			return this.columnSql;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(UniairColumnMapUtil.getAuthAndTableIdToTableName(auth, tableId));
		sb.append(" (");
		// 列名取得
		List<String> rowList = UniairColumnMapUtil.getIncludingDefaultKeyMap(tableId);
		// ?サイズに格納
		this.cons.valSize = rowList.size();
		int rowCount = 0;
		for (String row : rowList) {
			sb.append(row);
			if (rowCount == this.cons.valSize - 1) {
				break;
			}
			if (sb.toString().length() > 0) {
				sb.append(", ");
			}
			rowCount += 1;
		}
		sb.append(") ");
		this.columnSql = sb.toString();
		return sb.toString();
	}

	/**
	 * BookDataInsertEntityをString型のリストに変換
	 * @param entity BookDataEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertBookDataInsertList(List<BookDataInsertEntity> entity) throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			if ("".equals(entity.get(i).getGoalTime()) || entity.get(i).getGoalTime() == null) {
				throw new Exception("ゴール時間が記録されていません。(" + i + "行目)");
			}
			if ("".equals(entity.get(i).getGoalTeamMember()) || entity.get(i).getGoalTeamMember() == null) {
				throw new Exception("ゴール取得選手が記録されていません。(" + i + "行目)");
			}

			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getConditionResultDataSeqId());
			returnList.add(entity.get(i).getGameTeamCategory());
			// 試合時間がXX:XXの形式ではない場合修正
			String modifyTime = entity.get(i).getTime();
			if (!"ハーフタイム".equals(modifyTime) || !"終了済".equals(modifyTime)) {
				if (modifyTime != null && entity.get(i).getTime().endsWith(":00")) {
					modifyTime = entity.get(i).getTime().substring(0, modifyTime.length() - 3);
				}
			}
			returnList.add(modifyTime);
			// ホーム順位が?.の形式ではない場合修正
			String modifyHomeRank = entity.get(i).getHomeRank();
			if (modifyHomeRank != null && entity.get(i).getHomeRank().contains(".0")) {
				modifyHomeRank = entity.get(i).getHomeRank().replace(".0", ".");
			}
			returnList.add(modifyHomeRank);
			returnList.add(entity.get(i).getHomeTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyHomeScore = entity.get(i).getHomeScore();
			if (modifyHomeScore != null && entity.get(i).getHomeScore().contains(".0")) {
				modifyHomeScore = entity.get(i).getHomeScore().replace(".0", "");
			}
			returnList.add(modifyHomeScore);
			// アウェー順位が?.の形式ではない場合修正
			String modifyAwayRank = entity.get(i).getAwayRank();
			if (modifyAwayRank != null && entity.get(i).getHomeRank().contains(".0")) {
				modifyAwayRank = entity.get(i).getAwayRank().replace(".0", ".");
			}
			returnList.add(modifyAwayRank);
			returnList.add(entity.get(i).getAwayTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyAwayScore = entity.get(i).getAwayScore();
			if (modifyAwayScore != null && entity.get(i).getAwayScore().contains(".0")) {
				modifyAwayScore = entity.get(i).getAwayScore().replace(".0", "");
			}
			returnList.add(modifyAwayScore);
			// ホーム期待値が.00000000の形式の場合修正
			String modifyHomeExp = entity.get(i).getHomeExp();
			if (modifyHomeExp != null && entity.get(i).getHomeExp().contains(".0000")) {
				modifyHomeExp = entity.get(i).getHomeExp().replace(".0000", "");
			}
			returnList.add(modifyHomeExp);
			// アウェー期待値が.00000000の形式の場合修正
			String modifyAwayExp = entity.get(i).getAwayExp();
			if (modifyAwayExp != null && entity.get(i).getAwayExp().contains(".0000")) {
				modifyAwayExp = entity.get(i).getAwayExp().replace(".0000", "");
			}
			returnList.add(modifyAwayExp);
			// ホーム支配率が%の形式ではない場合修正
			String modifyHomePossesion = entity.get(i).getHomeBallPossesion();
			if (!"".equals(modifyHomePossesion) && !entity.get(i).getHomeBallPossesion().contains("%")) {
				modifyHomePossesion = String.valueOf((Double.parseDouble(modifyHomePossesion) * 100)).replace(".0", "")
						+ "%";
			}
			returnList.add(modifyHomePossesion);
			// アウェー支配率が%の形式ではない場合修正
			String modifyAwayPossesion = entity.get(i).getAwayBallPossesion();
			if (!"".equals(modifyAwayPossesion) && !entity.get(i).getAwayBallPossesion().contains("%")) {
				modifyAwayPossesion = String.valueOf((Double.parseDouble(modifyAwayPossesion) * 100)).replace(".0", "")
						+ "%";
			}
			returnList.add(modifyAwayPossesion);
			// ホームシュート数が?.?の形式の場合修正
			String modifyHomeShootAll = entity.get(i).getHomeShootAll();
			if (modifyHomeShootAll != null && entity.get(i).getHomeShootAll().contains(".0")) {
				modifyHomeShootAll = entity.get(i).getHomeShootAll().replace(".0", "");
			}
			returnList.add(modifyHomeShootAll);
			// アウェーシュート数が?.?の形式の場合修正
			String modifyAwayShootAll = entity.get(i).getAwayShootAll();
			if (modifyAwayShootAll != null && entity.get(i).getAwayShootAll().contains(".0")) {
				modifyAwayShootAll = entity.get(i).getAwayShootAll().replace(".0", "");
			}
			returnList.add(modifyAwayShootAll);
			// ホーム枠内シュート数が?.?の形式の場合修正
			String modifyHomeShootIn = entity.get(i).getHomeShootIn();
			if (modifyHomeShootIn != null && entity.get(i).getHomeShootIn().contains(".0")) {
				modifyHomeShootIn = entity.get(i).getHomeShootIn().replace(".0", "");
			}
			returnList.add(modifyHomeShootIn);
			// アウェー枠内シュート数が?.?の形式の場合修正
			String modifyAwayShootIn = entity.get(i).getAwayShootIn();
			if (modifyAwayShootIn != null && entity.get(i).getAwayShootIn().contains(".0")) {
				modifyAwayShootIn = entity.get(i).getAwayShootIn().replace(".0", "");
			}
			returnList.add(modifyAwayShootIn);
			// ホーム枠外シュート数が?.?の形式の場合修正
			String modifyHomeShootOut = entity.get(i).getHomeShootOut();
			if (modifyHomeShootOut != null && entity.get(i).getHomeShootOut().contains(".0")) {
				modifyHomeShootOut = entity.get(i).getHomeShootOut().replace(".0", "");
			}
			returnList.add(modifyHomeShootOut);
			// アウェー枠外シュート数が?.?の形式の場合修正
			String modifyAwayShootOut = entity.get(i).getAwayShootOut();
			if (modifyAwayShootOut != null && entity.get(i).getAwayShootOut().contains(".0")) {
				modifyAwayShootOut = entity.get(i).getAwayShootOut().replace(".0", "");
			}
			returnList.add(modifyAwayShootOut);
			// ホームブロックシュート数が?.?の形式の場合修正
			String modifyHomeBlock = entity.get(i).getHomeShootBlocked();
			if (modifyHomeBlock != null && entity.get(i).getHomeShootBlocked().contains(".0")) {
				modifyHomeBlock = entity.get(i).getHomeShootBlocked().replace(".0", "");
			}
			returnList.add(modifyHomeBlock);
			// アウェーブロックシュート数が?.?の形式の場合修正
			String modifyAwayBlock = entity.get(i).getAwayShootBlocked();
			if (modifyAwayBlock != null && entity.get(i).getAwayShootBlocked().contains(".0")) {
				modifyAwayBlock = entity.get(i).getAwayShootBlocked().replace(".0", "");
			}
			returnList.add(modifyAwayBlock);
			// ホームビックチャンス数が?.?の形式の場合修正
			String modifyHomeChance = entity.get(i).getHomeBigChance();
			if (modifyHomeChance != null && entity.get(i).getHomeBigChance().contains(".0")) {
				modifyHomeChance = entity.get(i).getHomeBigChance().replace(".0", "");
			}
			returnList.add(modifyHomeChance);
			// アウェービックチャンス数が?.?の形式の場合修正
			String modifyAwayChance = entity.get(i).getAwayBigChance();
			if (modifyAwayChance != null && entity.get(i).getAwayBigChance().contains(".0")) {
				modifyAwayChance = entity.get(i).getAwayBigChance().replace(".0", "");
			}
			returnList.add(modifyAwayChance);
			// ホームコーナーキック数が?.?の形式の場合修正
			String modifyHomeCorner = entity.get(i).getHomeCornerKick();
			if (modifyHomeCorner != null && entity.get(i).getHomeCornerKick().contains(".0")) {
				modifyHomeCorner = entity.get(i).getHomeCornerKick().replace(".0", "");
			}
			returnList.add(modifyHomeCorner);
			// アウェーコーナーキック数が?.?の形式の場合修正
			String modifyAwayCorner = entity.get(i).getAwayCornerKick();
			if (modifyAwayCorner != null && entity.get(i).getAwayCornerKick().contains(".0")) {
				modifyAwayCorner = entity.get(i).getAwayCornerKick().replace(".0", "");
			}
			returnList.add(modifyAwayCorner);
			// ホームボックスシュート数が?.?の形式の場合修正
			String modifyHomeBoxIn = entity.get(i).getHomeBoxShootIn();
			if (modifyHomeBoxIn != null && entity.get(i).getHomeBoxShootIn().contains(".0")) {
				modifyHomeBoxIn = entity.get(i).getHomeBoxShootIn().replace(".0", "");
			}
			returnList.add(modifyHomeBoxIn);
			// アウェーボックスシュート数が?.?の形式の場合修正
			String modifyAwayBoxIn = entity.get(i).getAwayBoxShootIn();
			if (modifyAwayBoxIn != null && entity.get(i).getAwayBoxShootIn().contains(".0")) {
				modifyAwayBoxIn = entity.get(i).getAwayBoxShootIn().replace(".0", "");
			}
			returnList.add(modifyAwayBoxIn);
			// ホームボックス外シュート数が?.?の形式の場合修正
			String modifyHomeBoxOut = entity.get(i).getHomeBoxShootOut();
			if (modifyHomeBoxOut != null && entity.get(i).getHomeBoxShootOut().contains(".0")) {
				modifyHomeBoxOut = entity.get(i).getHomeBoxShootOut().replace(".0", "");
			}
			returnList.add(modifyHomeBoxOut);
			// アウェーボックス外シュート数が?.?の形式の場合修正
			String modifyAwayBoxOut = entity.get(i).getAwayBoxShootOut();
			if (modifyAwayBoxOut != null && entity.get(i).getAwayBoxShootOut().contains(".0")) {
				modifyAwayBoxOut = entity.get(i).getAwayBoxShootOut().replace(".0", "");
			}
			returnList.add(modifyAwayBoxOut);
			// ホームゴールポスト数が?.?の形式の場合修正
			String modifyHomePost = entity.get(i).getHomeGoalPost();
			if (modifyHomePost != null && entity.get(i).getHomeGoalPost().contains(".0")) {
				modifyHomePost = entity.get(i).getHomeGoalPost().replace(".0", "");
			}
			returnList.add(modifyHomePost);
			// アウェーゴールポスト数が?.?の形式の場合修正
			String modifyAwayPost = entity.get(i).getAwayGoalPost();
			if (modifyAwayPost != null && entity.get(i).getAwayGoalPost().contains(".0")) {
				modifyAwayPost = entity.get(i).getAwayGoalPost().replace(".0", "");
			}
			returnList.add(modifyAwayPost);
			// ホームゴールヘッド数が?.?の形式の場合修正
			String modifyHomeGoalHead = entity.get(i).getHomeGoalHead();
			if (modifyHomeGoalHead != null && entity.get(i).getHomeGoalHead().contains(".0")) {
				modifyHomeGoalHead = entity.get(i).getHomeGoalHead().replace(".0", "");
			}
			returnList.add(modifyHomeGoalHead);
			// アウェーゴールヘッド数が?.?の形式の場合修正
			String modifyAwayGoalHead = entity.get(i).getAwayGoalHead();
			if (modifyAwayGoalHead != null && entity.get(i).getAwayGoalHead().contains(".0")) {
				modifyAwayGoalHead = entity.get(i).getAwayGoalHead().replace(".0", "");
			}
			returnList.add(modifyAwayGoalHead);
			// ホームゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyHomeKeeper = entity.get(i).getHomeKeeperSave();
			if (modifyHomeKeeper != null && entity.get(i).getHomeKeeperSave().contains(".0")) {
				modifyHomeKeeper = entity.get(i).getHomeKeeperSave().replace(".0", "");
			}
			returnList.add(modifyHomeKeeper);
			// アウェーゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyAwayKeeper = entity.get(i).getAwayKeeperSave();
			if (modifyAwayKeeper != null && entity.get(i).getAwayKeeperSave().contains(".0")) {
				modifyAwayKeeper = entity.get(i).getAwayKeeperSave().replace(".0", "");
			}
			returnList.add(modifyAwayKeeper);
			// ホームフリーキック数が?.?の形式の場合修正
			String modifyHomeFree = entity.get(i).getHomeFreeKick();
			if (modifyHomeFree != null && entity.get(i).getHomeFreeKick().contains(".0")) {
				modifyHomeFree = entity.get(i).getHomeFreeKick().replace(".0", "");
			}
			returnList.add(modifyHomeFree);
			// アウェーフリーキック数が?.?の形式の場合修正
			String modifyAwayFree = entity.get(i).getAwayFreeKick();
			if (modifyAwayFree != null && entity.get(i).getAwayFreeKick().contains(".0")) {
				modifyAwayFree = entity.get(i).getAwayFreeKick().replace(".0", "");
			}
			returnList.add(modifyAwayFree);
			// ホームオフサイド数が?.?の形式の場合修正
			String modifyHomeOffside = entity.get(i).getHomeOffSide();
			if (modifyHomeOffside != null && entity.get(i).getHomeOffSide().contains(".0")) {
				modifyHomeOffside = entity.get(i).getHomeOffSide().replace(".0", "");
			}
			returnList.add(modifyHomeOffside);
			// アウェーオフサイド数が?.?の形式の場合修正
			String modifyAwayOffside = entity.get(i).getAwayOffSide();
			if (modifyAwayOffside != null && entity.get(i).getAwayOffSide().contains(".0")) {
				modifyAwayOffside = entity.get(i).getAwayOffSide().replace(".0", "");
			}
			returnList.add(modifyAwayOffside);
			// ホームファール数が?.?の形式の場合修正
			String modifyHomeFoul = entity.get(i).getHomeFoul();
			if (modifyHomeFoul != null && entity.get(i).getHomeFoul().contains(".0")) {
				modifyHomeFoul = entity.get(i).getHomeFoul().replace(".0", "");
			}
			returnList.add(modifyHomeFoul);
			// アウェーファール数が?.?の形式の場合修正
			String modifyAwayFoul = entity.get(i).getAwayFoul();
			if (modifyAwayFoul != null && entity.get(i).getAwayFoul().contains(".0")) {
				modifyAwayFoul = entity.get(i).getAwayFoul().replace(".0", "");
			}
			returnList.add(modifyAwayFoul);
			// ホームイエローカード数が?.?の形式の場合修正
			String modifyHomeYellowCard = entity.get(i).getHomeYellowCard();
			if (modifyHomeYellowCard != null && entity.get(i).getHomeYellowCard().contains(".0")) {
				modifyHomeYellowCard = entity.get(i).getHomeYellowCard().replace(".0", "");
			}
			returnList.add(modifyHomeYellowCard);
			// アウェーイエローカード数が?.?の形式の場合修正
			String modifyAwayYellowCard = entity.get(i).getAwayYellowCard();
			if (modifyAwayYellowCard != null && entity.get(i).getAwayYellowCard().contains(".0")) {
				modifyAwayYellowCard = entity.get(i).getAwayYellowCard().replace(".0", "");
			}
			returnList.add(modifyAwayYellowCard);
			// ホームレッドカード数が?.?の形式の場合修正
			String modifyHomeRedCard = entity.get(i).getHomeRedCard();
			if (modifyHomeRedCard != null && entity.get(i).getHomeRedCard().contains(".0")) {
				modifyHomeRedCard = entity.get(i).getHomeRedCard().replace(".0", "");
			}
			returnList.add(modifyHomeRedCard);
			// アウェーレッドカード数が?.?の形式の場合修正
			String modifyAwayRedCard = entity.get(i).getAwayRedCard();
			if (modifyAwayRedCard != null && entity.get(i).getAwayRedCard().contains(".0")) {
				modifyAwayRedCard = entity.get(i).getAwayRedCard().replace(".0", "");
			}
			returnList.add(modifyAwayRedCard);
			// ホームスローインが?.?の形式の場合修正
			String modifyHomeSlowIn = entity.get(i).getHomeSlowIn();
			if (modifyHomeSlowIn != null && entity.get(i).getHomeSlowIn().contains(".0")) {
				modifyHomeSlowIn = entity.get(i).getHomeSlowIn().replace(".0", "");
			}
			returnList.add(modifyHomeSlowIn);
			// アウェースローインが?.?の形式の場合修正
			String modifyAwaySlowIn = entity.get(i).getAwaySlowIn();
			if (modifyAwaySlowIn != null && entity.get(i).getAwaySlowIn().contains(".0")) {
				modifyAwaySlowIn = entity.get(i).getAwaySlowIn().replace(".0", "");
			}
			returnList.add(modifyAwaySlowIn);
			// ホームボックスタッチが?.?の形式の場合修正
			String modifyHomeBoxTouch = entity.get(i).getHomeBoxTouch();
			if (modifyHomeBoxTouch != null && entity.get(i).getHomeBoxTouch().contains(".0")) {
				modifyHomeBoxTouch = entity.get(i).getHomeBoxTouch().replace(".0", "");
			}
			returnList.add(modifyHomeBoxTouch);
			// アウェーボックスタッチが?.?の形式の場合修正
			String modifyAwayBoxTouch = entity.get(i).getAwayBoxTouch();
			if (modifyAwayBoxTouch != null && entity.get(i).getAwayBoxTouch().contains(".0")) {
				modifyAwayBoxTouch = entity.get(i).getAwayBoxTouch().replace(".0", "");
			}
			returnList.add(modifyAwayBoxTouch);
			// ホームパス
			String modifyHomePass = entity.get(i).getHomePassCount();
			returnList.add(modifyHomePass);
			// アウェーパス
			String modifyAwayPass = entity.get(i).getAwayPassCount();
			returnList.add(modifyAwayPass);
			// ホームファイナルサードパス
			String modifyHomeFinalThirdPass = entity.get(i).getHomeFinalThirdPassCount();
			returnList.add(modifyHomeFinalThirdPass);
			// アウェーファイナルサードパス
			String modifyAwayFinalThirdPass = entity.get(i).getAwayFinalThirdPassCount();
			returnList.add(modifyAwayFinalThirdPass);
			// ホームクロス
			String modifyHomeCross = entity.get(i).getHomeCrossCount();
			returnList.add(modifyHomeCross);
			// アウェークロス
			String modifyAwayCross = entity.get(i).getAwayCrossCount();
			returnList.add(modifyAwayCross);
			// ホームタックル
			String modifyHomeTackle = entity.get(i).getHomeTackleCount();
			returnList.add(modifyHomeTackle);
			// アウェータックル
			String modifyAwayTackle = entity.get(i).getAwayTackleCount();
			returnList.add(modifyAwayTackle);
			// ホームクリアが?.?の形式の場合修正
			String modifyHomeClear = entity.get(i).getHomeClearCount();
			if (modifyHomeClear != null && entity.get(i).getHomeClearCount().contains(".0")) {
				modifyHomeClear = entity.get(i).getHomeClearCount().replace(".0", "");
			}
			returnList.add(modifyHomeClear);
			// アウェークリアが?.?の形式の場合修正
			String modifyAwayClear = entity.get(i).getAwayClearCount();
			if (modifyAwayClear != null && entity.get(i).getAwayClearCount().contains(".0")) {
				modifyAwayClear = entity.get(i).getAwayClearCount().replace(".0", "");
			}
			returnList.add(modifyAwayClear);
			// ホームインターセプトが?.?の形式の場合修正
			String modifyHomeIntercept = entity.get(i).getHomeInterceptCount();
			if (modifyHomeIntercept != null && entity.get(i).getHomeInterceptCount().contains(".0")) {
				modifyHomeIntercept = entity.get(i).getHomeInterceptCount().replace(".0", "");
			}
			returnList.add(modifyHomeIntercept);
			// アウェーインターセプトが?.?の形式の場合修正
			String modifyAwayIntercept = entity.get(i).getAwayInterceptCount();
			if (modifyAwayIntercept != null && entity.get(i).getAwayInterceptCount().contains(".0")) {
				modifyAwayIntercept = entity.get(i).getAwayInterceptCount().replace(".0", "");
			}
			returnList.add(modifyAwayIntercept);
			returnList.add(String.valueOf(entity.get(i).getRecordTime()));
			returnList.add(entity.get(i).getWeather());
			returnList.add(entity.get(i).getTemparature());
			returnList.add(entity.get(i).getHumid());
			returnList.add(entity.get(i).getJudgeMember());
			returnList.add(entity.get(i).getHomeManager());
			returnList.add(entity.get(i).getAwayManager());
			returnList.add(entity.get(i).getHomeFormation());
			returnList.add(entity.get(i).getAwayFormation());
			returnList.add(entity.get(i).getStudium());
			returnList.add(entity.get(i).getCapacity());
			returnList.add(entity.get(i).getAudience());
			returnList.add(entity.get(i).getHomeMaxGettingScorer());
			returnList.add(entity.get(i).getAwayMaxGettingScorer());
			returnList.add(entity.get(i).getHomeMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getAwayMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getHomeTeamHomeScore());
			returnList.add(entity.get(i).getHomeTeamHomeLost());
			returnList.add(entity.get(i).getAwayTeamHomeScore());
			returnList.add(entity.get(i).getAwayTeamHomeLost());
			returnList.add(entity.get(i).getHomeTeamAwayScore());
			returnList.add(entity.get(i).getHomeTeamAwayLost());
			returnList.add(entity.get(i).getAwayTeamAwayScore());
			returnList.add(entity.get(i).getAwayTeamAwayLost());
			returnList.add(entity.get(i).getNoticeFlg());
			returnList.add(entity.get(i).getGoalTime());
			returnList.add(entity.get(i).getGoalTeamMember());
			returnList.add(entity.get(i).getJudge());
			returnList.add(entity.get(i).getHomeTeamStyle());
			returnList.add(entity.get(i).getAwayTeamStyle());
			returnList.add(entity.get(i).getProbablity());
			returnList.add(entity.get(i).getPredictionScoreTime());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * ConditionResultDataInsertEntityをString型のリストに変換
	 * @param entity ConditionResultDataInsertEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertResultDataInsertList(List<ConditionResultDataEntity> entity) throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getMailTargetCount());
			returnList.add(entity.get(i).getMailAnonymousTargetCount());
			returnList.add(entity.get(i).getMailTargetSuccessCount());
			returnList.add(entity.get(i).getMailTargetFailCount());
			returnList.add(entity.get(i).getMailTargetFailToNoResultCount());
			returnList.add(entity.get(i).getMailFinDataToNoResultCount());
			returnList.add(entity.get(i).getGoalDelate());
			returnList.add(entity.get(i).getAlterTargetMailAnonymous());
			returnList.add(entity.get(i).getAlterTargetMailFail());
			returnList.add(entity.get(i).getNoResultCount());
			returnList.add(entity.get(i).getErrData());
			returnList.add(entity.get(i).getConditionData());
			returnList.add(entity.get(i).getHashData());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * TeamStatisticsDataInsertEntityをString型のリストに変換
	 * @param entity TeamStatisticsDataInsertEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertTeamStatisticsDataInsertList(List<TeamStatisticsDataEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getTeamName());
			returnList.add(entity.get(i).getHa());
			returnList.add(entity.get(i).getYear());
			returnList.add(entity.get(i).getJanuaryScoreSumCount());
			returnList.add(entity.get(i).getFebruaryScoreSumCount());
			returnList.add(entity.get(i).getMarchScoreSumCount());
			returnList.add(entity.get(i).getAprilScoreSumCount());
			returnList.add(entity.get(i).getMayScoreSumCount());
			returnList.add(entity.get(i).getJuneScoreSumCount());
			returnList.add(entity.get(i).getJulyScoreSumCount());
			returnList.add(entity.get(i).getAugustScoreSumCount());
			returnList.add(entity.get(i).getSeptemberScoreSumCount());
			returnList.add(entity.get(i).getOctoberScoreSumCount());
			returnList.add(entity.get(i).getNovemberScoreSumCount());
			returnList.add(entity.get(i).getDecemberScoreSumCount());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * TypeOfCountryLeagueDataEntityをString型のリストに変換
	 * @param entity TypeOfCountryLeagueDataEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertTypeOfCountryLeagueDataInsertList(List<TypeOfCountryLeagueDataEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getDataCount());
			returnList.add(entity.get(i).getCsvCount());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * WithinDataXMinutesEntityをString型のリストに変換
	 * @param entity WithinDataXMinutesEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertWithinDataXMinutesInsertList(List<WithinDataXMinutesEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getTimeRange());
			returnList.add(entity.get(i).getFeature());
			returnList.add(entity.get(i).getThresHold());
			returnList.add(entity.get(i).getTarget());
			returnList.add(entity.get(i).getSearch());
			returnList.add(entity.get(i).getRatio());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * WithinDataScoredCounterEntityをString型のリストに変換
	 * @param entity WithinDataScoredCounterEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertWithinDataScoredCounterInsertList(List<WithinDataScoredCounterEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getSumScoreValue());
			returnList.add(entity.get(i).getTimeRangeArea());
			returnList.add(entity.get(i).getTarget());
			returnList.add(entity.get(i).getSearch());
			returnList.add(entity.get(i).getRatio());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * WithinDataScoredCounterDetailEntityをString型のリストに変換
	 * @param entity WithinDataScoredCounterDetailEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertWithinDataScoredCounterDetailInsertList(
			List<WithinDataScoredCounterDetailEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getHomeScoreValue());
			returnList.add(entity.get(i).getAwayScoreValue());
			returnList.add(entity.get(i).getHomeTimeRangeArea());
			returnList.add(entity.get(i).getAwayTimeRangeArea());
			returnList.add(entity.get(i).getTarget());
			returnList.add(entity.get(i).getSearch());
			returnList.add(entity.get(i).getRatio());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * WithinDataXMinutesAllLeagueEntityをString型のリストに変換
	 * @param entity WithinDataXMinutesAllLeagueEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertWithinDataXMinutesWithAllLeagueInsertList(
			List<WithinDataXMinutesAllLeagueEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getTimeRange());
			returnList.add(entity.get(i).getFeature());
			returnList.add(entity.get(i).getThresHold());
			returnList.add(entity.get(i).getTarget());
			returnList.add(entity.get(i).getSearch());
			returnList.add(entity.get(i).getRatio());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * FileChkEntityをString型のリストに変換
	 * @param entity FileChkEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertFileChkInsertList(List<FileChkEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getFileName());
			returnList.add(entity.get(i).getFileHash());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * FileChkTmpEntityをString型のリストに変換
	 * @param entity FileChkTmpEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertFileChkTmpInsertList(List<FileChkTmpEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getFileName());
			returnList.add(entity.get(i).getBefSeqList());
			returnList.add(entity.get(i).getAfSeqList());
			returnList.add(entity.get(i).getBefFileHash());
			returnList.add(entity.get(i).getAfFileHash());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * ClassifyResultDataEntityをString型のリストに変換
	 * @param entity ClassifyResultDataEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertClassifyResultDataInsertList(List<ClassifyResultDataEntity> entity) throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getClassifyMode());
			returnList.add(entity.get(i).getDataCategory());
			// 試合時間がXX:XXの形式ではない場合修正
			String modifyTime = entity.get(i).getTimes();
			returnList.add(modifyTime);
			// ホーム順位が?.の形式ではない場合修正
			String modifyHomeRank = entity.get(i).getHomeRank();
			returnList.add(modifyHomeRank);
			returnList.add(entity.get(i).getHomeTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyHomeScore = entity.get(i).getHomeScore();
			returnList.add(modifyHomeScore);
			// アウェー順位が?.の形式ではない場合修正
			String modifyAwayRank = entity.get(i).getAwayRank();
			returnList.add(modifyAwayRank);
			returnList.add(entity.get(i).getAwayTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyAwayScore = entity.get(i).getAwayScore();
			returnList.add(modifyAwayScore);
			// ホーム期待値が.00000000の形式の場合修正
			String modifyHomeExp = entity.get(i).getHomeExp();
			returnList.add(modifyHomeExp);
			// アウェー期待値が.00000000の形式の場合修正
			String modifyAwayExp = entity.get(i).getAwayExp();
			returnList.add(modifyAwayExp);
			// ホーム支配率が%の形式ではない場合修正
			String modifyHomePossesion = entity.get(i).getHomeDonation();
			returnList.add(modifyHomePossesion);
			// アウェー支配率が%の形式ではない場合修正
			String modifyAwayPossesion = entity.get(i).getAwayDonation();
			returnList.add(modifyAwayPossesion);
			// ホームシュート数が?.?の形式の場合修正
			String modifyHomeShootAll = entity.get(i).getHomeShootAll();
			returnList.add(modifyHomeShootAll);
			// アウェーシュート数が?.?の形式の場合修正
			String modifyAwayShootAll = entity.get(i).getAwayShootAll();
			returnList.add(modifyAwayShootAll);
			// ホーム枠内シュート数が?.?の形式の場合修正
			String modifyHomeShootIn = entity.get(i).getHomeShootIn();
			returnList.add(modifyHomeShootIn);
			// アウェー枠内シュート数が?.?の形式の場合修正
			String modifyAwayShootIn = entity.get(i).getAwayShootIn();
			returnList.add(modifyAwayShootIn);
			// ホーム枠外シュート数が?.?の形式の場合修正
			String modifyHomeShootOut = entity.get(i).getHomeShootOut();
			returnList.add(modifyHomeShootOut);
			// アウェー枠外シュート数が?.?の形式の場合修正
			String modifyAwayShootOut = entity.get(i).getAwayShootOut();
			returnList.add(modifyAwayShootOut);
			// ホームブロックシュート数が?.?の形式の場合修正
			String modifyHomeBlock = entity.get(i).getHomeBlockShoot();
			returnList.add(modifyHomeBlock);
			// アウェーブロックシュート数が?.?の形式の場合修正
			String modifyAwayBlock = entity.get(i).getAwayBlockShoot();
			returnList.add(modifyAwayBlock);
			// ホームビックチャンス数が?.?の形式の場合修正
			String modifyHomeChance = entity.get(i).getHomeBigChance();
			returnList.add(modifyHomeChance);
			// アウェービックチャンス数が?.?の形式の場合修正
			String modifyAwayChance = entity.get(i).getAwayBigChance();
			returnList.add(modifyAwayChance);
			// ホームコーナーキック数が?.?の形式の場合修正
			String modifyHomeCorner = entity.get(i).getHomeCorner();
			returnList.add(modifyHomeCorner);
			// アウェーコーナーキック数が?.?の形式の場合修正
			String modifyAwayCorner = entity.get(i).getAwayCorner();
			returnList.add(modifyAwayCorner);
			// ホームボックスシュート数が?.?の形式の場合修正
			String modifyHomeBoxIn = entity.get(i).getHomeBoxShootIn();
			returnList.add(modifyHomeBoxIn);
			// アウェーボックスシュート数が?.?の形式の場合修正
			String modifyAwayBoxIn = entity.get(i).getAwayBoxShootIn();
			returnList.add(modifyAwayBoxIn);
			// ホームボックス外シュート数が?.?の形式の場合修正
			String modifyHomeBoxOut = entity.get(i).getHomeBoxShootOut();
			returnList.add(modifyHomeBoxOut);
			// アウェーボックス外シュート数が?.?の形式の場合修正
			String modifyAwayBoxOut = entity.get(i).getAwayBoxShootOut();
			returnList.add(modifyAwayBoxOut);
			// ホームゴールポスト数が?.?の形式の場合修正
			String modifyHomePost = entity.get(i).getHomeGoalPost();
			returnList.add(modifyHomePost);
			// アウェーゴールポスト数が?.?の形式の場合修正
			String modifyAwayPost = entity.get(i).getAwayGoalPost();
			returnList.add(modifyAwayPost);
			// ホームゴールヘッド数が?.?の形式の場合修正
			String modifyHomeGoalHead = entity.get(i).getHomeGoalHead();
			returnList.add(modifyHomeGoalHead);
			// アウェーゴールヘッド数が?.?の形式の場合修正
			String modifyAwayGoalHead = entity.get(i).getAwayGoalHead();
			returnList.add(modifyAwayGoalHead);
			// ホームゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyHomeKeeper = entity.get(i).getHomeKeeperSave();
			returnList.add(modifyHomeKeeper);
			// アウェーゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyAwayKeeper = entity.get(i).getAwayKeeperSave();
			returnList.add(modifyAwayKeeper);
			// ホームフリーキック数が?.?の形式の場合修正
			String modifyHomeFree = entity.get(i).getHomeFreeKick();
			returnList.add(modifyHomeFree);
			// アウェーフリーキック数が?.?の形式の場合修正
			String modifyAwayFree = entity.get(i).getAwayFreeKick();
			returnList.add(modifyAwayFree);
			// ホームオフサイド数が?.?の形式の場合修正
			String modifyHomeOffside = entity.get(i).getHomeOffside();
			returnList.add(modifyHomeOffside);
			// アウェーオフサイド数が?.?の形式の場合修正
			String modifyAwayOffside = entity.get(i).getAwayOffside();
			returnList.add(modifyAwayOffside);
			// ホームファール数が?.?の形式の場合修正
			String modifyHomeFoul = entity.get(i).getHomeFoul();
			returnList.add(modifyHomeFoul);
			// アウェーファール数が?.?の形式の場合修正
			String modifyAwayFoul = entity.get(i).getAwayFoul();
			returnList.add(modifyAwayFoul);
			// ホームイエローカード数が?.?の形式の場合修正
			String modifyHomeYellowCard = entity.get(i).getHomeYellowCard();
			returnList.add(modifyHomeYellowCard);
			// アウェーイエローカード数が?.?の形式の場合修正
			String modifyAwayYellowCard = entity.get(i).getAwayYellowCard();
			returnList.add(modifyAwayYellowCard);
			// ホームレッドカード数が?.?の形式の場合修正
			String modifyHomeRedCard = entity.get(i).getHomeRedCard();
			returnList.add(modifyHomeRedCard);
			// アウェーレッドカード数が?.?の形式の場合修正
			String modifyAwayRedCard = entity.get(i).getAwayRedCard();
			returnList.add(modifyAwayRedCard);
			// ホームスローインが?.?の形式の場合修正
			String modifyHomeSlowIn = entity.get(i).getHomeSlowIn();
			returnList.add(modifyHomeSlowIn);
			// アウェースローインが?.?の形式の場合修正
			String modifyAwaySlowIn = entity.get(i).getAwaySlowIn();
			returnList.add(modifyAwaySlowIn);
			// ホームボックスタッチが?.?の形式の場合修正
			String modifyHomeBoxTouch = entity.get(i).getHomeBoxTouch();
			returnList.add(modifyHomeBoxTouch);
			// アウェーボックスタッチが?.?の形式の場合修正
			String modifyAwayBoxTouch = entity.get(i).getAwayBoxTouch();
			returnList.add(modifyAwayBoxTouch);
			// ホームパス
			String modifyHomePass = entity.get(i).getHomePassCount();
			returnList.add(modifyHomePass);
			// アウェーパス
			String modifyAwayPass = entity.get(i).getAwayPassCount();
			returnList.add(modifyAwayPass);
			// ホームファイナルサードパス
			String modifyHomeFinalThirdPass = entity.get(i).getHomeFinalThirdPassCount();
			returnList.add(modifyHomeFinalThirdPass);
			// アウェーファイナルサードパス
			String modifyAwayFinalThirdPass = entity.get(i).getAwayFinalThirdPassCount();
			returnList.add(modifyAwayFinalThirdPass);
			// ホームクロス
			String modifyHomeCross = entity.get(i).getHomeCrossCount();
			returnList.add(modifyHomeCross);
			// アウェークロス
			String modifyAwayCross = entity.get(i).getAwayCrossCount();
			returnList.add(modifyAwayCross);
			// ホームタックル
			String modifyHomeTackle = entity.get(i).getHomeTackleCount();
			returnList.add(modifyHomeTackle);
			// アウェータックル
			String modifyAwayTackle = entity.get(i).getAwayTackleCount();
			returnList.add(modifyAwayTackle);
			// ホームクリアが?.?の形式の場合修正
			String modifyHomeClear = entity.get(i).getHomeClearCount();
			returnList.add(modifyHomeClear);
			// アウェークリアが?.?の形式の場合修正
			String modifyAwayClear = entity.get(i).getAwayClearCount();
			returnList.add(modifyAwayClear);
			// ホームインターセプトが?.?の形式の場合修正
			String modifyHomeIntercept = entity.get(i).getHomeInterceptCount();
			returnList.add(modifyHomeIntercept);
			// アウェーインターセプトが?.?の形式の場合修正
			String modifyAwayIntercept = entity.get(i).getAwayInterceptCount();
			returnList.add(modifyAwayIntercept);
			returnList.add(String.valueOf(entity.get(i).getRecordTime()));
			returnList.add(entity.get(i).getWeather());
			returnList.add(entity.get(i).getTemparature());
			returnList.add(entity.get(i).getHumid());
			returnList.add(entity.get(i).getJudgeMember());
			returnList.add(entity.get(i).getHomeManager());
			returnList.add(entity.get(i).getAwayManager());
			returnList.add(entity.get(i).getHomeFormation());
			returnList.add(entity.get(i).getAwayFormation());
			returnList.add(entity.get(i).getStudium());
			returnList.add(entity.get(i).getCapacity());
			returnList.add(entity.get(i).getAudience());
			returnList.add(entity.get(i).getHomeMaxGettingScorer());
			returnList.add(entity.get(i).getAwayMaxGettingScorer());
			returnList.add(entity.get(i).getHomeMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getAwayMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getHomeTeamHomeScore());
			returnList.add(entity.get(i).getHomeTeamHomeLost());
			returnList.add(entity.get(i).getAwayTeamHomeScore());
			returnList.add(entity.get(i).getAwayTeamHomeLost());
			returnList.add(entity.get(i).getHomeTeamAwayScore());
			returnList.add(entity.get(i).getHomeTeamAwayLost());
			returnList.add(entity.get(i).getAwayTeamAwayScore());
			returnList.add(entity.get(i).getAwayTeamAwayLost());
			returnList.add(entity.get(i).getNoticeFlg());
			returnList.add(entity.get(i).getGoalTime());
			returnList.add(entity.get(i).getGoalTeamMember());
			returnList.add(entity.get(i).getJudge());
			returnList.add(entity.get(i).getHomeTeamStyle());
			returnList.add(entity.get(i).getAwayTeamStyle());
			returnList.add(entity.get(i).getProbablity());
			returnList.add(entity.get(i).getPredictionScoreTime());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * ClassifyResultDataDetailEntityをString型のリストに変換
	 * @param entity ClassifyResultDataDetailEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertClassifyResultDataDetailInsertList(
			List<ClassifyResultDataDetailEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getClassifyMode());
			returnList.add(entity.get(i).getCount());
			returnList.add(entity.get(i).getRemarks());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * NoScoredEntityをString型のリストに変換
	 * @param entity NoScoredEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertNoScoredInsertList(List<NoScoredEntity> entity) throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getDataCategory());
			// 試合時間がXX:XXの形式ではない場合修正
			String modifyTime = entity.get(i).getTimes();
			returnList.add(modifyTime);
			// ホーム順位が?.の形式ではない場合修正
			String modifyHomeRank = entity.get(i).getHomeRank();
			returnList.add(modifyHomeRank);
			returnList.add(entity.get(i).getHomeTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyHomeScore = entity.get(i).getHomeScore();
			returnList.add(modifyHomeScore);
			// アウェー順位が?.の形式ではない場合修正
			String modifyAwayRank = entity.get(i).getAwayRank();
			returnList.add(modifyAwayRank);
			returnList.add(entity.get(i).getAwayTeamName());
			// ホームスコアが?.の形式ではない場合修正
			String modifyAwayScore = entity.get(i).getAwayScore();
			returnList.add(modifyAwayScore);
			// ホーム期待値が.00000000の形式の場合修正
			String modifyHomeExp = entity.get(i).getHomeExp();
			returnList.add(modifyHomeExp);
			// アウェー期待値が.00000000の形式の場合修正
			String modifyAwayExp = entity.get(i).getAwayExp();
			returnList.add(modifyAwayExp);
			// ホーム支配率が%の形式ではない場合修正
			String modifyHomePossesion = entity.get(i).getHomeDonation();
			returnList.add(modifyHomePossesion);
			// アウェー支配率が%の形式ではない場合修正
			String modifyAwayPossesion = entity.get(i).getAwayDonation();
			returnList.add(modifyAwayPossesion);
			// ホームシュート数が?.?の形式の場合修正
			String modifyHomeShootAll = entity.get(i).getHomeShootAll();
			returnList.add(modifyHomeShootAll);
			// アウェーシュート数が?.?の形式の場合修正
			String modifyAwayShootAll = entity.get(i).getAwayShootAll();
			returnList.add(modifyAwayShootAll);
			// ホーム枠内シュート数が?.?の形式の場合修正
			String modifyHomeShootIn = entity.get(i).getHomeShootIn();
			returnList.add(modifyHomeShootIn);
			// アウェー枠内シュート数が?.?の形式の場合修正
			String modifyAwayShootIn = entity.get(i).getAwayShootIn();
			returnList.add(modifyAwayShootIn);
			// ホーム枠外シュート数が?.?の形式の場合修正
			String modifyHomeShootOut = entity.get(i).getHomeShootOut();
			returnList.add(modifyHomeShootOut);
			// アウェー枠外シュート数が?.?の形式の場合修正
			String modifyAwayShootOut = entity.get(i).getAwayShootOut();
			returnList.add(modifyAwayShootOut);
			// ホームブロックシュート数が?.?の形式の場合修正
			String modifyHomeBlock = entity.get(i).getHomeBlockShoot();
			returnList.add(modifyHomeBlock);
			// アウェーブロックシュート数が?.?の形式の場合修正
			String modifyAwayBlock = entity.get(i).getAwayBlockShoot();
			returnList.add(modifyAwayBlock);
			// ホームビックチャンス数が?.?の形式の場合修正
			String modifyHomeChance = entity.get(i).getHomeBigChance();
			returnList.add(modifyHomeChance);
			// アウェービックチャンス数が?.?の形式の場合修正
			String modifyAwayChance = entity.get(i).getAwayBigChance();
			returnList.add(modifyAwayChance);
			// ホームコーナーキック数が?.?の形式の場合修正
			String modifyHomeCorner = entity.get(i).getHomeCorner();
			returnList.add(modifyHomeCorner);
			// アウェーコーナーキック数が?.?の形式の場合修正
			String modifyAwayCorner = entity.get(i).getAwayCorner();
			returnList.add(modifyAwayCorner);
			// ホームボックスシュート数が?.?の形式の場合修正
			String modifyHomeBoxIn = entity.get(i).getHomeBoxShootIn();
			returnList.add(modifyHomeBoxIn);
			// アウェーボックスシュート数が?.?の形式の場合修正
			String modifyAwayBoxIn = entity.get(i).getAwayBoxShootIn();
			returnList.add(modifyAwayBoxIn);
			// ホームボックス外シュート数が?.?の形式の場合修正
			String modifyHomeBoxOut = entity.get(i).getHomeBoxShootOut();
			returnList.add(modifyHomeBoxOut);
			// アウェーボックス外シュート数が?.?の形式の場合修正
			String modifyAwayBoxOut = entity.get(i).getAwayBoxShootOut();
			returnList.add(modifyAwayBoxOut);
			// ホームゴールポスト数が?.?の形式の場合修正
			String modifyHomePost = entity.get(i).getHomeGoalPost();
			returnList.add(modifyHomePost);
			// アウェーゴールポスト数が?.?の形式の場合修正
			String modifyAwayPost = entity.get(i).getAwayGoalPost();
			returnList.add(modifyAwayPost);
			// ホームゴールヘッド数が?.?の形式の場合修正
			String modifyHomeGoalHead = entity.get(i).getHomeGoalHead();
			returnList.add(modifyHomeGoalHead);
			// アウェーゴールヘッド数が?.?の形式の場合修正
			String modifyAwayGoalHead = entity.get(i).getAwayGoalHead();
			returnList.add(modifyAwayGoalHead);
			// ホームゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyHomeKeeper = entity.get(i).getHomeKeeperSave();
			returnList.add(modifyHomeKeeper);
			// アウェーゴールキーパーセーブ数が?.?の形式の場合修正
			String modifyAwayKeeper = entity.get(i).getAwayKeeperSave();
			returnList.add(modifyAwayKeeper);
			// ホームフリーキック数が?.?の形式の場合修正
			String modifyHomeFree = entity.get(i).getHomeFreeKick();
			returnList.add(modifyHomeFree);
			// アウェーフリーキック数が?.?の形式の場合修正
			String modifyAwayFree = entity.get(i).getAwayFreeKick();
			returnList.add(modifyAwayFree);
			// ホームオフサイド数が?.?の形式の場合修正
			String modifyHomeOffside = entity.get(i).getHomeOffside();
			returnList.add(modifyHomeOffside);
			// アウェーオフサイド数が?.?の形式の場合修正
			String modifyAwayOffside = entity.get(i).getAwayOffside();
			returnList.add(modifyAwayOffside);
			// ホームファール数が?.?の形式の場合修正
			String modifyHomeFoul = entity.get(i).getHomeFoul();
			returnList.add(modifyHomeFoul);
			// アウェーファール数が?.?の形式の場合修正
			String modifyAwayFoul = entity.get(i).getAwayFoul();
			returnList.add(modifyAwayFoul);
			// ホームイエローカード数が?.?の形式の場合修正
			String modifyHomeYellowCard = entity.get(i).getHomeYellowCard();
			returnList.add(modifyHomeYellowCard);
			// アウェーイエローカード数が?.?の形式の場合修正
			String modifyAwayYellowCard = entity.get(i).getAwayYellowCard();
			returnList.add(modifyAwayYellowCard);
			// ホームレッドカード数が?.?の形式の場合修正
			String modifyHomeRedCard = entity.get(i).getHomeRedCard();
			returnList.add(modifyHomeRedCard);
			// アウェーレッドカード数が?.?の形式の場合修正
			String modifyAwayRedCard = entity.get(i).getAwayRedCard();
			returnList.add(modifyAwayRedCard);
			// ホームスローインが?.?の形式の場合修正
			String modifyHomeSlowIn = entity.get(i).getHomeSlowIn();
			returnList.add(modifyHomeSlowIn);
			// アウェースローインが?.?の形式の場合修正
			String modifyAwaySlowIn = entity.get(i).getAwaySlowIn();
			returnList.add(modifyAwaySlowIn);
			// ホームボックスタッチが?.?の形式の場合修正
			String modifyHomeBoxTouch = entity.get(i).getHomeBoxTouch();
			returnList.add(modifyHomeBoxTouch);
			// アウェーボックスタッチが?.?の形式の場合修正
			String modifyAwayBoxTouch = entity.get(i).getAwayBoxTouch();
			returnList.add(modifyAwayBoxTouch);
			// ホームパス
			String modifyHomePass = entity.get(i).getHomePassCount();
			returnList.add(modifyHomePass);
			// アウェーパス
			String modifyAwayPass = entity.get(i).getAwayPassCount();
			returnList.add(modifyAwayPass);
			// ホームファイナルサードパス
			String modifyHomeFinalThirdPass = entity.get(i).getHomeFinalThirdPassCount();
			returnList.add(modifyHomeFinalThirdPass);
			// アウェーファイナルサードパス
			String modifyAwayFinalThirdPass = entity.get(i).getAwayFinalThirdPassCount();
			returnList.add(modifyAwayFinalThirdPass);
			// ホームクロス
			String modifyHomeCross = entity.get(i).getHomeCrossCount();
			returnList.add(modifyHomeCross);
			// アウェークロス
			String modifyAwayCross = entity.get(i).getAwayCrossCount();
			returnList.add(modifyAwayCross);
			// ホームタックル
			String modifyHomeTackle = entity.get(i).getHomeTackleCount();
			returnList.add(modifyHomeTackle);
			// アウェータックル
			String modifyAwayTackle = entity.get(i).getAwayTackleCount();
			returnList.add(modifyAwayTackle);
			// ホームクリアが?.?の形式の場合修正
			String modifyHomeClear = entity.get(i).getHomeClearCount();
			returnList.add(modifyHomeClear);
			// アウェークリアが?.?の形式の場合修正
			String modifyAwayClear = entity.get(i).getAwayClearCount();
			returnList.add(modifyAwayClear);
			// ホームインターセプトが?.?の形式の場合修正
			String modifyHomeIntercept = entity.get(i).getHomeInterceptCount();
			returnList.add(modifyHomeIntercept);
			// アウェーインターセプトが?.?の形式の場合修正
			String modifyAwayIntercept = entity.get(i).getAwayInterceptCount();
			returnList.add(modifyAwayIntercept);
			returnList.add(String.valueOf(entity.get(i).getRecordTime()));
			returnList.add(entity.get(i).getWeather());
			returnList.add(entity.get(i).getTemparature());
			returnList.add(entity.get(i).getHumid());
			returnList.add(entity.get(i).getJudgeMember());
			returnList.add(entity.get(i).getHomeManager());
			returnList.add(entity.get(i).getAwayManager());
			returnList.add(entity.get(i).getHomeFormation());
			returnList.add(entity.get(i).getAwayFormation());
			returnList.add(entity.get(i).getStudium());
			returnList.add(entity.get(i).getCapacity());
			returnList.add(entity.get(i).getAudience());
			returnList.add(entity.get(i).getHomeMaxGettingScorer());
			returnList.add(entity.get(i).getAwayMaxGettingScorer());
			returnList.add(entity.get(i).getHomeMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getAwayMaxGettingScorerGameSituation());
			returnList.add(entity.get(i).getHomeTeamHomeScore());
			returnList.add(entity.get(i).getHomeTeamHomeLost());
			returnList.add(entity.get(i).getAwayTeamHomeScore());
			returnList.add(entity.get(i).getAwayTeamHomeLost());
			returnList.add(entity.get(i).getHomeTeamAwayScore());
			returnList.add(entity.get(i).getHomeTeamAwayLost());
			returnList.add(entity.get(i).getAwayTeamAwayScore());
			returnList.add(entity.get(i).getAwayTeamAwayLost());
			returnList.add(entity.get(i).getNoticeFlg());
			returnList.add(entity.get(i).getGoalTime());
			returnList.add(entity.get(i).getGoalTeamMember());
			returnList.add(entity.get(i).getJudge());
			returnList.add(entity.get(i).getHomeTeamStyle());
			returnList.add(entity.get(i).getAwayTeamStyle());
			returnList.add(entity.get(i).getProbablity());
			returnList.add(entity.get(i).getPredictionScoreTime());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
	 * AverageFeatureEntityをString型のリストに変換
	 * @param entity AverageFeatureEntity型のリスト
	 * @return 変換されたリスト
	 * @throws Exception
	 */
	private List<String[]> convertAverageFeatureInsertList(List<AverageFeatureEntity> entity) throws Exception {
	    List<String[]> returnAllList = new ArrayList<String[]>();  // 最終的に返すリスト
	    for (int i = 0; i < entity.size(); i++) {
	        List<String> returnList = new ArrayList<String>();  // 各エンティティのフィールドを保持するリスト
	        // AverageFeatureEntityの各フィールドを取得
	        returnList.add(entity.get(i).getTeamName());               // チーム
	        returnList.add(entity.get(i).getVersusTeamName());         // 対戦チーム
	        returnList.add(entity.get(i).getHa());                     // 対戦場所
	        returnList.add(entity.get(i).getScore());                  // スコア
	        returnList.add(entity.get(i).getResult());                 // 結果
	        returnList.add(entity.get(i).getGameFinRank());            // 途中順位
	        returnList.add(entity.get(i).getOppositeGameFinRank());   // 対戦相手途中順位
	        returnList.add(entity.get(i).getExp());                    // 期待値
	        returnList.add(entity.get(i).getOppositeExp());            // 対戦相手期待値
	        returnList.add(entity.get(i).getDonation());               // ポゼッション
	        returnList.add(entity.get(i).getOppositeDonation());       // 対戦相手ポゼッション
	        returnList.add(entity.get(i).getShootAll());               // シュート数
	        returnList.add(entity.get(i).getOppositeShootAll());       // 対戦相手シュート数
	        returnList.add(entity.get(i).getShootIn());                // 枠内シュート
	        returnList.add(entity.get(i).getOppositeShootIn());        // 対戦相手枠内シュート
	        returnList.add(entity.get(i).getShootOut());               // 枠外シュート
	        returnList.add(entity.get(i).getOppositeShootOut());       // 対戦相手枠外シュート
	        returnList.add(entity.get(i).getBlockShoot());             // ブロックシュート
	        returnList.add(entity.get(i).getOppositeBlockShoot());     // 対戦相手ブロックシュート
	        returnList.add(entity.get(i).getBigChance());              // ビックチャンス
	        returnList.add(entity.get(i).getOppositeBigChance());      // 対戦相手ビックチャンス
	        returnList.add(entity.get(i).getCorner());                 // コーナーキック
	        returnList.add(entity.get(i).getOppositeCorner());         // 対戦相手コーナーキック
	        returnList.add(entity.get(i).getBoxShootIn());             // ボックス内シュート
	        returnList.add(entity.get(i).getOppositeBoxShootIn());     // 対戦相手ボックス内シュート
	        returnList.add(entity.get(i).getBoxShootOut());            // ボックス外シュート
	        returnList.add(entity.get(i).getOppositeBoxShootOut());    // 対戦相手ボックス外シュート
	        returnList.add(entity.get(i).getGoalPost());               // ゴールポスト
	        returnList.add(entity.get(i).getOppositeGoalPost());       // 対戦相手ゴールポスト
	        returnList.add(entity.get(i).getGoalHead());               // ヘディングゴール
	        returnList.add(entity.get(i).getOppositeGoalHead());       // 対戦相手ヘディングゴール
	        returnList.add(entity.get(i).getKeeperSave());             // キーパーセーブ
	        returnList.add(entity.get(i).getOppositeKeeperSave());     // 対戦相手キーパーセーブ
	        returnList.add(entity.get(i).getFreeKick());               // フリーキック
	        returnList.add(entity.get(i).getOppositeFreeKick());       // 対戦相手フリーキック
	        returnList.add(entity.get(i).getOffside());                // オフサイド
	        returnList.add(entity.get(i).getOppositeOffside());        // 対戦相手オフサイド
	        returnList.add(entity.get(i).getFoul());                   // ファウル
	        returnList.add(entity.get(i).getOppositeFoul());           // 対戦相手ファウル
	        returnList.add(entity.get(i).getYellowCard());             // イエローカード
	        returnList.add(entity.get(i).getOppositeYellowCard());     // 対戦相手イエローカード
	        returnList.add(entity.get(i).getRedCard());                // レッドカード
	        returnList.add(entity.get(i).getOppositeRedCard());        // 対戦相手レッドカード
	        returnList.add(entity.get(i).getSlowIn());                 // スローイン
	        returnList.add(entity.get(i).getOppositeSlowIn());         // 対戦相手スローイン
	        returnList.add(entity.get(i).getBoxTouch());               // ボックスタッチ
	        returnList.add(entity.get(i).getOppositeBoxTouch());       // 対戦相手ボックスタッチ
	        returnList.add(entity.get(i).getPassCountSuccessRatio());  // パス数_成功率
	        returnList.add(entity.get(i).getPassCountSuccessCount());  // パス数_成功数
	        returnList.add(entity.get(i).getPassCountTryCount());      // パス数_試行数
	        returnList.add(entity.get(i).getOppositePassCountSuccessRatio()); // 対戦相手パス数_成功率
	        returnList.add(entity.get(i).getOppositePassCountSuccessCount()); // 対戦相手パス数_成功数
	        returnList.add(entity.get(i).getOppositePassCountTryCount()); // 対戦相手パス数_試行数
	        returnList.add(entity.get(i).getFinalThirdPassCountSuccessRatio()); // ファイナルサードパス数_成功率
	        returnList.add(entity.get(i).getFinalThirdPassCountSuccessCount()); // ファイナルサードパス数_成功数
	        returnList.add(entity.get(i).getFinalThirdPassCountTryCount()); // ファイナルサードパス数_試行数
	        returnList.add(entity.get(i).getOppositeFinalThirdPassCountSuccessRatio()); // 対戦相手ファイナルサードパス数_成功率
	        returnList.add(entity.get(i).getOppositeFinalThirdPassCountSuccessCount()); // 対戦相手ファイナルサードパス数_成功数
	        returnList.add(entity.get(i).getOppositeFinalThirdPassCountTryCount()); // 対戦相手ファイナルサードパス数_試行数
	        returnList.add(entity.get(i).getCrossCountSuccessRatio());  // クロス数_成功率
	        returnList.add(entity.get(i).getCrossCountSuccessCount());  // クロス数_成功数
	        returnList.add(entity.get(i).getCrossCountTryCount());      // クロス数_試行数
	        returnList.add(entity.get(i).getOppositeCrossCountSuccessRatio()); // 対戦相手クロス数_成功率
	        returnList.add(entity.get(i).getOppositeCrossCountSuccessCount()); // 対戦相手クロス数_成功数
	        returnList.add(entity.get(i).getOppositeCrossCountTryCount()); // 対戦相手クロス数_試行数
	        returnList.add(entity.get(i).getTackleCountSuccessRatio()); // タックル数_成功率
	        returnList.add(entity.get(i).getTackleCountSuccessCount()); // タックル数_成功数
	        returnList.add(entity.get(i).getTackleCountTryCount());     // タックル数_試行数
	        returnList.add(entity.get(i).getOppositeTackleCountSuccessRatio()); // 対戦相手タックル数_成功率
	        returnList.add(entity.get(i).getOppositeTackleCountSuccessCount()); // 対戦相手タックル数_成功数
	        returnList.add(entity.get(i).getOppositeTackleCountTryCount()); // 対戦相手タックル数_試行数
	        returnList.add(entity.get(i).getClearCount());              // クリア数
	        returnList.add(entity.get(i).getOppositeClearCount());      // 対戦相手クリア数
	        returnList.add(entity.get(i).getInterceptCount());          // インターセプト数
	        returnList.add(entity.get(i).getOppositeInterceptCount());  // 対戦相手インターセプト数
	        returnList.add(entity.get(i).getWeather());                // 天気
	        returnList.add(entity.get(i).getTemparature());            // 気温
	        returnList.add(entity.get(i).getHumid());                  // 湿度
	        // List<String>からString[]に変換
	        String[] list = new String[returnList.size()];
	        for (int j = 0; j < returnList.size(); j++) {
	            list[j] = returnList.get(j);
	        }
	        // 変換したリストを最終リストに追加
	        returnAllList.add(list);
	    }
	    return returnAllList;  // 最終的に返すリスト
	}

	private List<String[]> convertDiffEntityInsertList(List<DiffFeatureEntity> entityList) throws Exception {
	    List<String[]> returnAllList = new ArrayList<String[]>();  // 最終的に返すリスト

	    for (int i = 0; i < entityList.size(); i++) {
	        List<String> returnList = new ArrayList<String>();  // 各エンティティのフィールドを保持するリスト
	        returnList.add(entityList.get(i).getDataCategory());     // 国カテゴリ
	        returnList.add(entityList.get(i).getHomeTeamName());     // ホームチーム
	        returnList.add(entityList.get(i).getAwayTeamName());     // アウェーチーム
	        // ホームフィールドの差分
	        returnList.add(entityList.get(i).getDiffHomeScore());        // ホームチームのスコアの差分
	        returnList.add(entityList.get(i).getDiffAwayScore());        // アウェーチームのスコアの差分
	        returnList.add(entityList.get(i).getDiffHomeDonation());     // ホームチームのポゼッションの差分
	        returnList.add(entityList.get(i).getDiffAwayDonation());     // アウェーチームのポゼッションの差分
	        returnList.add(entityList.get(i).getDiffHomeShootAll());     // ホームチームのシュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayShootAll());     // アウェーチームのシュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeShootIn());      // ホームチームの枠内シュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayShootIn());      // アウェーチームの枠内シュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeShootOut());     // ホームチームの枠外シュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayShootOut());     // アウェーチームの枠外シュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeBlockShoot());   // ホームチームのブロックシュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayBlockShoot());   // アウェーチームのブロックシュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeBigChance());    // ホームチームのビッグチャンス数の差分
	        returnList.add(entityList.get(i).getDiffAwayBigChance());    // アウェーチームのビッグチャンス数の差分
	        returnList.add(entityList.get(i).getDiffHomeCorner());       // ホームチームのコーナーキック数の差分
	        returnList.add(entityList.get(i).getDiffAwayCorner());       // アウェーチームのコーナーキック数の差分
	        returnList.add(entityList.get(i).getDiffHomeBoxShootIn());   // ホームチームのボックス内シュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayBoxShootIn());   // アウェーチームのボックス内シュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeBoxShootOut());  // ホームチームのボックス外シュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayBoxShootOut());  // アウェーチームのボックス外シュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeGoalPost());     // ホームチームのゴールポストに当たったシュート数の差分
	        returnList.add(entityList.get(i).getDiffAwayGoalPost());     // アウェーチームのゴールポストに当たったシュート数の差分
	        returnList.add(entityList.get(i).getDiffHomeGoalHead());     // ホームチームのヘディングゴール数の差分
	        returnList.add(entityList.get(i).getDiffAwayGoalHead());     // アウェーチームのヘディングゴール数の差分
	        returnList.add(entityList.get(i).getDiffHomeKeeperSave());   // ホームチームのキーパーセーブ数の差分
	        returnList.add(entityList.get(i).getDiffAwayKeeperSave());   // アウェーチームのキーパーセーブ数の差分
	        returnList.add(entityList.get(i).getDiffHomeFreeKick());     // ホームチームのフリーキック数の差分
	        returnList.add(entityList.get(i).getDiffAwayFreeKick());     // アウェーチームのフリーキック数の差分
	        returnList.add(entityList.get(i).getDiffHomeOffside());      // ホームチームのオフサイド数の差分
	        returnList.add(entityList.get(i).getDiffAwayOffside());      // アウェーチームのオフサイド数の差分
	        returnList.add(entityList.get(i).getDiffHomeFoul());         // ホームチームのファウル数の差分
	        returnList.add(entityList.get(i).getDiffAwayFoul());         // アウェーチームのファウル数の差分
	        returnList.add(entityList.get(i).getDiffHomeYellowCard());   // ホームチームのイエローカード数の差分
	        returnList.add(entityList.get(i).getDiffAwayYellowCard());   // アウェーチームのイエローカード数の差分
	        returnList.add(entityList.get(i).getDiffHomeRedCard());      // ホームチームのレッドカード数の差分
	        returnList.add(entityList.get(i).getDiffAwayRedCard());      // アウェーチームのレッドカード数の差分
	        returnList.add(entityList.get(i).getDiffHomeSlowIn());       // ホームチームのスローイン数の差分
	        returnList.add(entityList.get(i).getDiffAwaySlowIn());       // アウェーチームのスローイン数の差分
	        returnList.add(entityList.get(i).getDiffHomeBoxTouch());     // ホームチームのボックスタッチ数の差分
	        returnList.add(entityList.get(i).getDiffAwayBoxTouch());     // アウェーチームのボックスタッチ数の差分
	        returnList.add(entityList.get(i).getDiffHomePassCount());    // ホームチームのパス数の差分
	        returnList.add(entityList.get(i).getDiffAwayPassCount());    // アウェーチームのパス数の差分
	        returnList.add(entityList.get(i).getDiffHomeFinalThirdPassCount()); // ホームチームのファイナルサードパス数の差分
	        returnList.add(entityList.get(i).getDiffAwayFinalThirdPassCount()); // アウェーチームのファイナルサードパス数の差分
	        returnList.add(entityList.get(i).getDiffHomeCrossCount());   // ホームチームのクロス数の差分
	        returnList.add(entityList.get(i).getDiffAwayCrossCount());   // アウェーチームのクロス数の差分
	        returnList.add(entityList.get(i).getDiffHomeTackleCount());  // ホームチームのタックル数の差分
	        returnList.add(entityList.get(i).getDiffAwayTackleCount());  // アウェーチームのタックル数の差分
	        returnList.add(entityList.get(i).getDiffHomeClearCount());   // ホームチームのクリア数の差分
	        returnList.add(entityList.get(i).getDiffAwayClearCount());   // アウェーチームのクリア数の差分
	        returnList.add(entityList.get(i).getDiffHomeInterceptCount()); // ホームチームのインターセプト数の差分
	        returnList.add(entityList.get(i).getDiffAwayInterceptCount()); // アウェーチームのインターセプト数の差分
	        returnList.add(entityList.get(i).getHomePlayStyle()); // ホームチームのインターセプト数の差分
	        returnList.add(entityList.get(i).getAwayPlayStyle()); // アウェーチームのインターセプト数の差分
	        // List<String>からString[]に変換
	        String[] list = new String[returnList.size()];
	        for (int j = 0; j < returnList.size(); j++) {
	            list[j] = returnList.get(j);
	        }
	        // 変換したリストを最終リストに追加
	        returnAllList.add(list);
	    }
	    return returnAllList;  // 最終的に返すリスト
	}

	/**
     * CorrelationEntityをString型のリストに変換
     * @param entity CorrelationEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertCorrelationInsertList(
            List<CorrelationEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getFile());
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(entity.get(i).getHome());
            returnList.add(entity.get(i).getAway());
            returnList.add(entity.get(i).getScore());
            returnList.add(entity.get(i).getChkBody());
            returnList.add(corrConnectData(entity.get(i).getHomeExpInfo())); // HomeExpStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayExpInfo())); // AwayExpStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeDonationInfo())); // HomeDonationStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayDonationInfo())); // AwayDonationStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeShootAllInfo())); // HomeShootAllStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayShootAllInfo())); // AwayShootAllStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeShootInInfo())); // HomeShootInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayShootInInfo())); // AwayShootInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeShootOutInfo())); // HomeShootOutStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayShootOutInfo())); // AwayShootOutStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeBlockShootInfo())); // HomeBlockShootStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayBlockShootInfo())); // AwayBlockShootStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeBigChanceInfo())); // HomeBigChanceStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayBigChanceInfo())); // AwayBigChanceStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeCornerInfo())); // HomeCornerStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayCornerInfo())); // AwayCornerStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeBoxShootInInfo())); // HomeBoxShootInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayBoxShootInInfo())); // AwayBoxShootInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeBoxShootOutInfo())); // HomeBoxShootOutStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayBoxShootOutInfo())); // AwayBoxShootOutStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeGoalPostInfo())); // HomeGoalPostStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayGoalPostInfo())); // AwayGoalPostStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeGoalHeadInfo())); // HomeGoalHeadStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayGoalHeadInfo())); // AwayGoalHeadStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeKeeperSaveInfo())); // HomeKeeperSaveStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayKeeperSaveInfo())); // AwayKeeperSaveStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeFreeKickInfo())); // HomeFreeKickStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayFreeKickInfo())); // AwayFreeKickStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeOffsideInfo())); // HomeOffsideStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayOffsideInfo())); // AwayOffsideStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeFoulInfo())); // HomeFoulStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayFoulInfo())); // AwayFoulStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeYellowCardInfo())); // HomeYellowCardStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayYellowCardInfo())); // AwayYellowCardStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeRedCardInfo())); // HomeRedCardStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayRedCardInfo())); // AwayRedCardStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeSlowInInfo())); // HomeSlowInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwaySlowInInfo())); // AwaySlowInStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeBoxTouchInfo())); // HomeBoxTouchStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayBoxTouchInfo())); // AwayBoxTouchStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomePassCountInfoOnSuccessRatio())); // HomePassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomePassCountInfoOnSuccessCount())); // HomePassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomePassCountInfoOnTryCount())); // HomePassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayPassCountInfoOnSuccessRatio())); // AwayPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayPassCountInfoOnSuccessCount())); // AwayPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayPassCountInfoOnTryCount())); // AwayPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeFinalThirdPassCountInfoOnSuccessRatio())); // HomeFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeFinalThirdPassCountInfoOnSuccessCount())); // HomeFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeFinalThirdPassCountInfoOnTryCount())); // HomeFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayFinalThirdPassCountInfoOnSuccessRatio())); // AwayFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayFinalThirdPassCountInfoOnSuccessCount())); // AwayFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayFinalThirdPassCountInfoOnTryCount())); // AwayFinalThirdPassCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeCrossCountInfoOnSuccessRatio())); // HomeCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeCrossCountInfoOnSuccessCount())); // HomeCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeCrossCountInfoOnTryCount())); // HomeCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayCrossCountInfoOnSuccessRatio())); // AwayCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayCrossCountInfoOnSuccessCount())); // AwayCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayCrossCountInfoOnTryCount())); // AwayCrossCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeTackleCountInfoOnSuccessRatio())); // HomeTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeTackleCountInfoOnSuccessCount())); // HomeTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeTackleCountInfoOnTryCount())); // HomeTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayTackleCountInfoOnSuccessRatio())); // AwayTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayTackleCountInfoOnSuccessCount())); // AwayTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayTackleCountInfoOnTryCount())); // AwayTackleCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeClearCountInfo())); // HomeClearCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayClearCountInfo())); // AwayClearCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getHomeInterceptCountInfo())); // HomeInterceptCountStatのデータ
            returnList.add(corrConnectData(entity.get(i).getAwayInterceptCountInfo())); // AwayInterceptCountStatのデータ

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

    /**
	 * UpdCsvInfoEntityをString型のリストに変換
	 * @param entity UpdCsvInfoEntity型のリスト
	 * @return
	 * @throws Exception
	 */
	private List<String[]> convertUpdCsvInfoInsertList(
			List<UpdCsvInfoEntity> entity)
			throws Exception {
		List<String[]> returnAllList = new ArrayList<String[]>();
		for (int i = 0; i < entity.size(); i++) {
			List<String> returnList = new ArrayList<String>();
			returnList.add(entity.get(i).getCountry());
			returnList.add(entity.get(i).getLeague());
			returnList.add(entity.get(i).getTableId());
			returnList.add(entity.get(i).getRemarks());
			String[] list = new String[returnList.size()];
			for (int j = 0; j < returnList.size(); j++) {
				list[j] = returnList.get(j);
			}
			returnAllList.add(list);
		}
		return returnAllList;
	}

	/**
     * CorrelationDetailEntityをString型のリストに変換
     * @param entity CorrelationDetailEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertCorrelationDetailInsertList(
            List<CorrelationDetailEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(entity.get(i).getHome());
            returnList.add(entity.get(i).getAway());
            returnList.add(entity.get(i).getScore());
            returnList.add(entity.get(i).getChkBody());
            returnList.add(entity.get(i).getRank1st());
            returnList.add(entity.get(i).getRank2nd());
            returnList.add(entity.get(i).getRank3rd());
            returnList.add(entity.get(i).getRank4th());
            returnList.add(entity.get(i).getRank5th());
            returnList.add(entity.get(i).getRank6th());
            returnList.add(entity.get(i).getRank7th());
            returnList.add(entity.get(i).getRank8th());
            returnList.add(entity.get(i).getRank9th());
            returnList.add(entity.get(i).getRank10th());
            returnList.add(entity.get(i).getRank11th());
            returnList.add(entity.get(i).getRank12th());
            returnList.add(entity.get(i).getRank13th());
            returnList.add(entity.get(i).getRank14th());
            returnList.add(entity.get(i).getRank15th());
            returnList.add(entity.get(i).getRank16th());
            returnList.add(entity.get(i).getRank17th());
            returnList.add(entity.get(i).getRank18th());
            returnList.add(entity.get(i).getRank19th());
            returnList.add(entity.get(i).getRank20th());
            returnList.add(entity.get(i).getRank21th());
            returnList.add(entity.get(i).getRank22th());
            returnList.add(entity.get(i).getRank23th());
            returnList.add(entity.get(i).getRank24th());
            returnList.add(entity.get(i).getRank25th());
            returnList.add(entity.get(i).getRank26th());
            returnList.add(entity.get(i).getRank27th());
            returnList.add(entity.get(i).getRank28th());
            returnList.add(entity.get(i).getRank29th());
            returnList.add(entity.get(i).getRank30th());
            returnList.add(entity.get(i).getRank31th());
            returnList.add(entity.get(i).getRank32th());
            returnList.add(entity.get(i).getRank33th());
            returnList.add(entity.get(i).getRank34th());
            returnList.add(entity.get(i).getRank35th());
            returnList.add(entity.get(i).getRank36th());
            returnList.add(entity.get(i).getRank37th());
            returnList.add(entity.get(i).getRank38th());
            returnList.add(entity.get(i).getRank39th());
            returnList.add(entity.get(i).getRank40th());
            returnList.add(entity.get(i).getRank41th());
            returnList.add(entity.get(i).getRank42th());
            returnList.add(entity.get(i).getRank43th());
            returnList.add(entity.get(i).getRank44th());
            returnList.add(entity.get(i).getRank45th());
            returnList.add(entity.get(i).getRank46th());
            returnList.add(entity.get(i).getRank47th());
            returnList.add(entity.get(i).getRank48th());
            returnList.add(entity.get(i).getRank49th());
            returnList.add(entity.get(i).getRank50th());
            returnList.add(entity.get(i).getRank51th());
            returnList.add(entity.get(i).getRank52th());
            returnList.add(entity.get(i).getRank53th());
            returnList.add(entity.get(i).getRank54th());
            returnList.add(entity.get(i).getRank55th());
            returnList.add(entity.get(i).getRank56th());
            returnList.add(entity.get(i).getRank57th());
            returnList.add(entity.get(i).getRank58th());
            returnList.add(entity.get(i).getRank59th());
            returnList.add(entity.get(i).getRank60th());
            returnList.add(entity.get(i).getRank61th());
            returnList.add(entity.get(i).getRank62th());
            returnList.add(entity.get(i).getRank63th());
            returnList.add(entity.get(i).getRank64th());
            returnList.add(entity.get(i).getRank65th());
            returnList.add(entity.get(i).getRank66th());
            returnList.add(entity.get(i).getRank67th());
            returnList.add(entity.get(i).getRank68th());
            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }


	/**
     * AverageStatisticsDetailEntityをString型のリストに変換
     * @param entity AverageStatisticsDetailEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertAverageStatisticsDetailEntityInsertList(
            List<AverageStatisticsDetailEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getSituation());
            returnList.add(entity.get(i).getScore());
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(entity.get(i).getTeam());
            returnList.add(connectData(entity.get(i).getHomeExpStat())); // HomeExpStatのデータ
            returnList.add(connectData(entity.get(i).getAwayExpStat())); // AwayExpStatのデータ
            returnList.add(connectData(entity.get(i).getHomeDonationStat())); // HomeDonationStatのデータ
            returnList.add(connectData(entity.get(i).getAwayDonationStat())); // AwayDonationStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootAllStat())); // HomeShootAllStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootAllStat())); // AwayShootAllStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootInStat())); // HomeShootInStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootInStat())); // AwayShootInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootOutStat())); // HomeShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootOutStat())); // AwayShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBlockShootStat())); // HomeBlockShootStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBlockShootStat())); // AwayBlockShootStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBigChanceStat())); // HomeBigChanceStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBigChanceStat())); // AwayBigChanceStatのデータ
            returnList.add(connectData(entity.get(i).getHomeCornerStat())); // HomeCornerStatのデータ
            returnList.add(connectData(entity.get(i).getAwayCornerStat())); // AwayCornerStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxShootInStat())); // HomeBoxShootInStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxShootInStat())); // AwayBoxShootInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxShootOutStat())); // HomeBoxShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxShootOutStat())); // AwayBoxShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getHomeGoalPostStat())); // HomeGoalPostStatのデータ
            returnList.add(connectData(entity.get(i).getAwayGoalPostStat())); // AwayGoalPostStatのデータ
            returnList.add(connectData(entity.get(i).getHomeGoalHeadStat())); // HomeGoalHeadStatのデータ
            returnList.add(connectData(entity.get(i).getAwayGoalHeadStat())); // AwayGoalHeadStatのデータ
            returnList.add(connectData(entity.get(i).getHomeKeeperSaveStat())); // HomeKeeperSaveStatのデータ
            returnList.add(connectData(entity.get(i).getAwayKeeperSaveStat())); // AwayKeeperSaveStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFreeKickStat())); // HomeFreeKickStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFreeKickStat())); // AwayFreeKickStatのデータ
            returnList.add(connectData(entity.get(i).getHomeOffsideStat())); // HomeOffsideStatのデータ
            returnList.add(connectData(entity.get(i).getAwayOffsideStat())); // AwayOffsideStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFoulStat())); // HomeFoulStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFoulStat())); // AwayFoulStatのデータ
            returnList.add(connectData(entity.get(i).getHomeYellowCardStat())); // HomeYellowCardStatのデータ
            returnList.add(connectData(entity.get(i).getAwayYellowCardStat())); // AwayYellowCardStatのデータ
            returnList.add(connectData(entity.get(i).getHomeRedCardStat())); // HomeRedCardStatのデータ
            returnList.add(connectData(entity.get(i).getAwayRedCardStat())); // AwayRedCardStatのデータ
            returnList.add(connectData(entity.get(i).getHomeSlowInStat())); // HomeSlowInStatのデータ
            returnList.add(connectData(entity.get(i).getAwaySlowInStat())); // AwaySlowInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxTouchStat())); // HomeBoxTouchStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxTouchStat())); // AwayBoxTouchStatのデータ
            returnList.add(connectData(entity.get(i).getHomePassCountStat())); // HomePassCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayPassCountStat())); // AwayPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFinalThirdPassCountStat())); // HomeFinalThirdPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFinalThirdPassCountStat())); // AwayFinalThirdPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeCrossCountStat())); // HomeCrossCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayCrossCountStat())); // AwayCrossCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeTackleCountStat())); // HomeTackleCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayTackleCountStat())); // AwayTackleCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeClearCountStat())); // HomeClearCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayClearCountStat())); // AwayClearCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeInterceptCountStat())); // HomeInterceptCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayInterceptCountStat())); // AwayInterceptCountStatのデータ

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

    /**
     * AverageStatisticsEntityをString型のリストに変換
     * @param entity AverageStatisticsEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertAverageStatisticsEntityInsertList(
            List<AverageStatisticsEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getSituation());
            returnList.add(entity.get(i).getScore());
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(connectData(entity.get(i).getHomeExpStat())); // HomeExpStatのデータ
            returnList.add(connectData(entity.get(i).getAwayExpStat())); // AwayExpStatのデータ
            returnList.add(connectData(entity.get(i).getHomeDonationStat())); // HomeDonationStatのデータ
            returnList.add(connectData(entity.get(i).getAwayDonationStat())); // AwayDonationStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootAllStat())); // HomeShootAllStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootAllStat())); // AwayShootAllStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootInStat())); // HomeShootInStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootInStat())); // AwayShootInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeShootOutStat())); // HomeShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getAwayShootOutStat())); // AwayShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBlockShootStat())); // HomeBlockShootStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBlockShootStat())); // AwayBlockShootStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBigChanceStat())); // HomeBigChanceStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBigChanceStat())); // AwayBigChanceStatのデータ
            returnList.add(connectData(entity.get(i).getHomeCornerStat())); // HomeCornerStatのデータ
            returnList.add(connectData(entity.get(i).getAwayCornerStat())); // AwayCornerStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxShootInStat())); // HomeBoxShootInStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxShootInStat())); // AwayBoxShootInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxShootOutStat())); // HomeBoxShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxShootOutStat())); // AwayBoxShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getHomeGoalPostStat())); // HomeGoalPostStatのデータ
            returnList.add(connectData(entity.get(i).getAwayGoalPostStat())); // AwayGoalPostStatのデータ
            returnList.add(connectData(entity.get(i).getHomeGoalHeadStat())); // HomeGoalHeadStatのデータ
            returnList.add(connectData(entity.get(i).getAwayGoalHeadStat())); // AwayGoalHeadStatのデータ
            returnList.add(connectData(entity.get(i).getHomeKeeperSaveStat())); // HomeKeeperSaveStatのデータ
            returnList.add(connectData(entity.get(i).getAwayKeeperSaveStat())); // AwayKeeperSaveStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFreeKickStat())); // HomeFreeKickStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFreeKickStat())); // AwayFreeKickStatのデータ
            returnList.add(connectData(entity.get(i).getHomeOffsideStat())); // HomeOffsideStatのデータ
            returnList.add(connectData(entity.get(i).getAwayOffsideStat())); // AwayOffsideStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFoulStat())); // HomeFoulStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFoulStat())); // AwayFoulStatのデータ
            returnList.add(connectData(entity.get(i).getHomeYellowCardStat())); // HomeYellowCardStatのデータ
            returnList.add(connectData(entity.get(i).getAwayYellowCardStat())); // AwayYellowCardStatのデータ
            returnList.add(connectData(entity.get(i).getHomeRedCardStat())); // HomeRedCardStatのデータ
            returnList.add(connectData(entity.get(i).getAwayRedCardStat())); // AwayRedCardStatのデータ
            returnList.add(connectData(entity.get(i).getHomeSlowInStat())); // HomeSlowInStatのデータ
            returnList.add(connectData(entity.get(i).getAwaySlowInStat())); // AwaySlowInStatのデータ
            returnList.add(connectData(entity.get(i).getHomeBoxTouchStat())); // HomeBoxTouchStatのデータ
            returnList.add(connectData(entity.get(i).getAwayBoxTouchStat())); // AwayBoxTouchStatのデータ
            returnList.add(connectData(entity.get(i).getHomePassCountStat())); // HomePassCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayPassCountStat())); // AwayPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeFinalThirdPassCountStat())); // HomeFinalThirdPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayFinalThirdPassCountStat())); // AwayFinalThirdPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeCrossCountStat())); // HomeCrossCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayCrossCountStat())); // AwayCrossCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeTackleCountStat())); // HomeTackleCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayTackleCountStat())); // AwayTackleCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeClearCountStat())); // HomeClearCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayClearCountStat())); // AwayClearCountStatのデータ
            returnList.add(connectData(entity.get(i).getHomeInterceptCountStat())); // HomeInterceptCountStatのデータ
            returnList.add(connectData(entity.get(i).getAwayInterceptCountStat())); // AwayInterceptCountStatのデータ

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

    /**
     * AverageStatisticsCsvTmpDataEntityをString型のリストに変換
     * @param entity AverageStatisticsCsvTmpDataEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertAverageStatisticsCsvTmpDataEntityInsertList(
            List<AverageStatisticsCsvTmpDataEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getScore());
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(entity.get(i).getTeam());
            returnList.add(entity.get(i).getGameCount());

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

    /**
     * AverageStatisticsTeamDetailEntityをString型のリストに変換
     * @param entity AverageStatisticsTeamDetailEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertAverageStatisticsTeamDetailEntityInsertList(
            List<AverageStatisticsTeamDetailEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getSituation());
            returnList.add(entity.get(i).getTeam());
            returnList.add(entity.get(i).getOppositeTeam());
            returnList.add(entity.get(i).getHa());
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(connectData(entity.get(i).getExpStat())); // ExpStatのデータ
            returnList.add(connectData(entity.get(i).getDonationStat())); // DonationStatのデータ
            returnList.add(connectData(entity.get(i).getShootAllStat())); // ShootAllStatのデータ
            returnList.add(connectData(entity.get(i).getShootInStat())); // ShootInStatのデータ
            returnList.add(connectData(entity.get(i).getShootOutStat())); // ShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getBlockShootStat())); // BlockShootStatのデータ
            returnList.add(connectData(entity.get(i).getBigChanceStat())); // BigChanceStatのデータ
            returnList.add(connectData(entity.get(i).getCornerStat())); // CornerStatのデータ
            returnList.add(connectData(entity.get(i).getBoxShootInStat())); // BoxShootInStatのデータ
            returnList.add(connectData(entity.get(i).getBoxShootOutStat())); // BoxShootOutStatのデータ
            returnList.add(connectData(entity.get(i).getGoalPostStat())); // GoalPostStatのデータ
            returnList.add(connectData(entity.get(i).getGoalHeadStat())); // GoalHeadStatのデータ
            returnList.add(connectData(entity.get(i).getKeeperSaveStat())); // KeeperSaveStatのデータ
            returnList.add(connectData(entity.get(i).getFreeKickStat())); // FreeKickStatのデータ
            returnList.add(connectData(entity.get(i).getOffsideStat())); // OffsideStatのデータ
            returnList.add(connectData(entity.get(i).getFoulStat())); // FoulStatのデータ
            returnList.add(connectData(entity.get(i).getYellowCardStat())); // YellowCardStatのデータ
            returnList.add(connectData(entity.get(i).getRedCardStat())); // RedCardStatのデータ
            returnList.add(connectData(entity.get(i).getSlowInStat())); // SlowInStatのデータ
            returnList.add(connectData(entity.get(i).getBoxTouchStat())); // BoxTouchStatのデータ
            returnList.add(connectData(entity.get(i).getPassCountStat())); // PassCountStatのデータ
            returnList.add(connectData(entity.get(i).getFinalThirdPassCountStat())); // FinalThirdPassCountStatのデータ
            returnList.add(connectData(entity.get(i).getCrossCountStat())); // CrossCountStatのデータ
            returnList.add(connectData(entity.get(i).getTackleCountStat())); // TackleCountStatのデータ
            returnList.add(connectData(entity.get(i).getClearCountStat())); // ClearCountStatのデータ
            returnList.add(connectData(entity.get(i).getInterceptCountStat())); // InterceptCountStatのデータ

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

    /**
     * CollectRangeScoreEntityをString型のリストに変換
     * @param entity CollectRangeScoreEntity型のリスト
     * @return String型のリスト
     * @throws Exception
     */
    private List<String[]> convertCollectRangeScoreEntityInsertList(
            List<CollectRangeScoreEntity> entity)
            throws Exception {
        List<String[]> returnAllList = new ArrayList<String[]>();
        for (int i = 0; i < entity.size(); i++) {
            List<String> returnList = new ArrayList<String>();
            returnList.add(entity.get(i).getCountry());
            returnList.add(entity.get(i).getLeague());
            returnList.add(entity.get(i).getKeyFlg());
            returnList.add(entity.get(i).getTeam());
            returnList.add(entity.get(i).getOppositeTeam());
            returnList.add(entity.get(i).getTime0_10());
            returnList.add(entity.get(i).getTime10_20());
            returnList.add(entity.get(i).getTime20_30());
            returnList.add(entity.get(i).getTime30_40());
            returnList.add(entity.get(i).getTime40_45());
            returnList.add(entity.get(i).getTime45_50());
            returnList.add(entity.get(i).getTime50_60());
            returnList.add(entity.get(i).getTime60_70());
            returnList.add(entity.get(i).getTime70_80());
            returnList.add(entity.get(i).getTime80_90());
            returnList.add(entity.get(i).getTime90_100());

            String[] list = new String[returnList.size()];
            for (int j = 0; j < returnList.size(); j++) {
                list[j] = returnList.get(j);
            }
            returnAllList.add(list);
        }
        return returnAllList;
    }

	/**
	 * 統計データを連結する
	 * @param summary
	 * @return
	 */
	private String connectData(StatSummary summary) {
		if (summary == null) {
			return "";
		}
		return summary.getMin() + "," + summary.getMax() + "," +
				summary.getMean() + "," + summary.getSigma() + "," +
				String.valueOf(summary.getCount()) + "," +
				summary.getFeatureTimeMin() + "," + summary.getFeatureTimeMax() +
				"," + summary.getFeatureTimeMean() + "," + summary.getFeatureTimeSigma() +
				"," + String.valueOf(summary.getFeatureCount());
	}

	/**
	 * 統計データを連結する
	 * @param summary
	 * @return
	 */
	private String corrConnectData(CorrelationSummary summary) {
		if (summary == null) {
			return "";
		}
		return summary.getSummationOfSecondPowerX() + "," + summary.getSummationOfTimes() + "," +
				summary.getSummationOfSecondPowerY() + "," + summary.getCorrelation() + "," +
				String.valueOf(summary.getCount());
	}
}
