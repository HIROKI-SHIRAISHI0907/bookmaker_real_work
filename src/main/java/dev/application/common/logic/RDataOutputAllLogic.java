package dev.application.common.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.application.common.dto.RDataOutputDTO;
import dev.application.common.exception.SystemException;
import dev.application.db.BookDataSelectWrapper;
import dev.application.db.UniairConst;

/**
 * R言語用に用意するCSVファイルを作成するメインロジック
 * @author shiraishitoshio
 *
 */
public class RDataOutputAllLogic {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(RDataOutputAllLogic.class);

	/** プロジェクト名 */
	private static final String PROJECT_NAME = RDataOutputAllLogic.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();

	/** クラス名 */
	private static final String CLASS_NAME = RDataOutputAllLogic.class.getSimpleName();

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

		// スレッドセーフな多重リストを作成
		List<List<String>> allSeqGroupList = Collections.synchronizedList(new ArrayList<List<String>>());
		// 並列プール数
		final int SEQ_POOL_PARALLEL = 100;
		// 担当範囲決定
		int rangeSize = (int) Math.ceil((double) allCnt / SEQ_POOL_PARALLEL);
		ForkJoinPool forkJoinPool = new ForkJoinPool(SEQ_POOL_PARALLEL);
		for (int taskId = 1; taskId <= SEQ_POOL_PARALLEL; taskId++) {
			int start = taskId * rangeSize;
			int end = Math.min(start + rangeSize - 1, allCnt - 1);

			final int rangeStart = start;
			final int rangeEnd = end;

			forkJoinPool.submit(() -> {
				TeamStatisticsDataPartsMultiSeparateSequenceLogic teamStatisticsDataPartsMultiSeparateSequenceLogic = new TeamStatisticsDataPartsMultiSeparateSequenceLogic();
				for (int taskSubId = rangeStart; taskSubId <= rangeEnd; taskSubId++) {
					int task = taskSubId;
					// 通番リストを取得
					List<String> separateSeqList = teamStatisticsDataPartsMultiSeparateSequenceLogic
							.separateLogic(task);
					if (separateSeqList != null) {
						allSeqGroupList.add(separateSeqList);
					}
				}
			});
		}
		forkJoinPool.shutdown();
		try {
			if (forkJoinPool.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.SECONDS)) {
				logger.info("task all fin");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("task were interrupted");
		}

		RDataOutputDTO rDataOutputDTO = new RDataOutputDTO();
		rDataOutputDTO.setHeaderFlg(false);
		rDataOutputDTO.setDupList(new ArrayList<String>());
		RDataOutputAllSubLogic rDataOutputAllSubLogic = new RDataOutputAllSubLogic();
		for (List<String> groupList : allSeqGroupList) {
			rDataOutputDTO = rDataOutputAllSubLogic.execute(groupList, rDataOutputDTO);
		}

	}

}
