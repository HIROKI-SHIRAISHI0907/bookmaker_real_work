package dev.application.domain.service.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.application.common.book.delete.DeleteFolderInFile;
import dev.application.common.book.read.FindThresHoldCsv;
import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.FindBookInputDTO;
import dev.application.common.dto.FindBookOutputDTO;
import dev.application.common.dto.ReadFileOutputDTO;
import dev.application.common.exception.BusinessException;
import dev.application.common.file.ReadThresHoldFile;
import dev.application.common.logic.CollectScoringDataStandardWrapperLogic;
import dev.application.entity.ThresHoldEntity;

@Tag("IT")
//@SpringBootTest(classes = BookMakersConfig.class)
//@MapperScan("dev.application.repository")
//@ActiveProfiles("test")
class AnyTest {

	@Test
	void testExecute_NormalCase_Analyze() throws Exception {
		/**
		 * CSV原本パス
		 */
		String PATH = "/Users/shiraishitoshio/bookmaker/csv/";

		/**
		 * CSVコピー先パス
		 */
		String COPY_PATH = "/Users/shiraishitoshio/bookmaker/csv/threshold/";

		DeleteFolderInFile deleteFolderInFile = new DeleteFolderInFile();
		deleteFolderInFile.delete(COPY_PATH);

		// 1. ブック探索クラスから特定のパスに存在する全ブックをリストで取得
		FindBookInputDTO findBookInputDTO = new FindBookInputDTO();
		findBookInputDTO.setDataPath(PATH);
		findBookInputDTO.setCopyFlg(false);
		FindThresHoldCsv findThresHoldCsv = new FindThresHoldCsv();
		FindBookOutputDTO findBookOutputDTO = findThresHoldCsv.execute(findBookInputDTO);
		// エラーの場合,戻り値の例外を業務例外に集約してスロー
		if (!BookMakersCommonConst.NORMAL_CD.equals(findBookOutputDTO.getResultCd())) {
			throw new BusinessException(
					findBookOutputDTO.getExceptionProject(),
					findBookOutputDTO.getExceptionClass(),
					findBookOutputDTO.getExceptionMethod(),
					findBookOutputDTO.getErrMessage(),
					findBookOutputDTO.getThrowAble());
		}

		// 2. コピー先にコピーしたCSVリストを集計し,1ファイルずつ読み込む
		ReadThresHoldFile readThresHoldFile = new ReadThresHoldFile();
		int allcount = 1;
		int count = 1;
		List<Integer> searchList = new ArrayList<Integer>();
		searchList.add(1);
		searchList.add(1);
		for (String file : findBookOutputDTO.getBookList()) {
			System.out.println("all chk book: " + allcount + "/" + findBookOutputDTO.getBookList().size());
			//System.out.println("chk book: " + count + "/" + findBookOutputDTO.getBookList().size());

			//			if (!file.contains("/6.csv") && !file.contains("/174.csv") && !file.contains("/1798.csv") &&
			//					!file.contains("/2213.csv") && !file.contains("/3364.csv")) {
			//				continue;
			//			}

			//			if (!file.contains("/3738.csv")) {
			//				continue;
			//			}

			ReadFileOutputDTO readFileOutputDTO = readThresHoldFile.getFileBody(file);
			if (!BookMakersCommonConst.NORMAL_CD.equals(readFileOutputDTO.getResultCd())) {
				throw new BusinessException(
						readFileOutputDTO.getExceptionProject(),
						readFileOutputDTO.getExceptionClass(),
						readFileOutputDTO.getExceptionMethod(),
						readFileOutputDTO.getErrMessage(),
						readFileOutputDTO.getThrowAble());
			}

			List<ThresHoldEntity> entityList = readFileOutputDTO.getReadHoldDataList();
			System.out.println("このファイルを確認します。:" + file);
			System.out.println("国,リーグ: " + entityList.get(0).getDataCategory());

			//			TeamStatisticsDataSubLogic teamStatisticsDataSubLogic = new
			//					TeamStatisticsDataSubLogic();
			//			try {
			//				teamStatisticsDataSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("ClassifyScoreSubLogic err: " + e);
			//			}
			//
			//			ZeroScoreDataSubLogic zeroScoreDataSubLogic = new
			//					ZeroScoreDataSubLogic();
			//			try {
			//				zeroScoreDataSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("ClassifyScoreSubLogic err: " + e);
			//			}

			//			AverageFeatureSubLogic averageFeatureSubLogic = new
			//					AverageFeatureSubLogic();
			//			try {
			//				averageFeatureSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("ClassifyScoreSubLogic err: " + e);
			//			}

			//			ScoringPlaystylePastDiffDataSubLogic scoringPlaystylePastDiffDataSubLogic = new
			//					ScoringPlaystylePastDiffDataSubLogic();
			//			try {
			//				scoringPlaystylePastDiffDataSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("ScoringPlaystylePastDiffDataSubLogic err: " + e);
			//			}

			//			DecidePlayStyleAndThresHoldLogic decidePlayStyleAndThresHoldLogic = new DecidePlayStyleAndThresHoldLogic();
			//			try {
			//				decidePlayStyleAndThresHoldLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("DecidePlayStyleAndThresHoldLogic err: file: " + file + ", " + e);
			//			}

			//			CalcCorrelationSubLogic calcCorrelationSubLogic = new CalcCorrelationSubLogic();
			//			try {
			//				calcCorrelationSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("CalcCorrelationSubLogic err: file: " + file + ", " + e);
			//			}
			//
			//			List<String> splitInfo = ExecuteMainUtil.getCountryLeagueByRegex(entityList.get(0).getDataCategory());
			//
			//			ExecuteClusterLogic executeClusterLogic = new ExecuteClusterLogic();
			//			executeClusterLogic.execute(file, splitInfo.get(0), splitInfo.get(1),
			//					entityList.get(0).getHomeTeamName(), entityList.get(0).getAwayTeamName());
			//
			//			CalcCorrelationDetailSubLogic calcCorrelationDetailSubLogic = new CalcCorrelationDetailSubLogic();
			//			try {
			//				calcCorrelationDetailSubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("CalcCorrelationDetailSubLogic err: " + e);
			//			}

			//			AverageStatsDetailLogic averageStatsDetailLogic = new AverageStatsDetailLogic();
			//			averageStatsDetailLogic.execute(entityList, file);

			//			ClassifyScoreAISubLogic classifyScoreAISubLogic = new ClassifyScoreAISubLogic();
			//			try {
			//				classifyScoreAISubLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("ClassifyScoreSubLogic err: " + e);
			//			}

			//			CollectRangeScoreLogic collectRangeScoreLogic = new CollectRangeScoreLogic();
			//			try {
			//				collectRangeScoreLogic.execute(entityList, file);
			//			} catch (Exception e) {
			//				System.err.println("CollectRangeScoreLogic err: " + e);
			//			}

			allcount++;
			count++;

		}

		// レコード件数を取得する
//		BookDataSelectWrapper selectWrapper = new BookDataSelectWrapper();
//		int cnt = -1;
//		try {
//			cnt = selectWrapper.executeCountSelect(UniairConst.BM_M006, null);
//		} catch (Exception e) {
//			return;
//		}
//
//		List<String> select6List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M006);
//		String[] sel6List = new String[select6List.size()];
//		for (int i = 0; i < select6List.size(); i++) {
//			sel6List[i] = select6List.get(i);
//		}
//
//		boolean updCsvFlg = ExistsUpdCsvInfo.exist();
//		// 更新CSVテーブルに存在したものは更新対象
//		List<List<String>> selectsList = new ArrayList<List<String>>();
//		if (updCsvFlg) {
//			selectsList = ExistsUpdCsvInfo.allCountryLeagueGet(UniairConst.BM_M028, null);
//			cnt = selectsList.size();
//		} else {
//			selectsList = ExistsUpdCsvInfo.allCountryLeagueGet(UniairConst.BM_M006, "ALL");
//		}
//
//		// スレッドプール生成（適宜スレッド数は調整）
//		ExecutorService executor = Executors.newFixedThreadPool(cnt);
//		List<Future<?>> futures = new ArrayList<>();
//
//		for (int i = 0; i < cnt; i++) {
//			final String country = selectsList.get(i).get(0);
//			final String category = selectsList.get(i).get(1);
//			futures.add(executor.submit(() -> {
//				try {
//					AverageStatsDetailLogic averageStatsDetailLogic = new AverageStatsDetailLogic();
//					averageStatsDetailLogic.updateRankingData(country, category);
//				} catch (Exception e) {
//					System.err.println("Error processing record country: " + country +
//							", category: " + category);
//					e.printStackTrace();
//				}
//			}));
//		}
//
//		// スレッド終了待機
//		executor.shutdown();
//		try {
//			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}

//		AverageStatsDetailCsvLogic averageStatsDetailCsvLogic = new AverageStatsDetailCsvLogic();
//		averageStatsDetailCsvLogic.execute();

		CollectScoringDataStandardWrapperLogic collectScoringDataStandardWrapperLogic =
				new CollectScoringDataStandardWrapperLogic();
		collectScoringDataStandardWrapperLogic.execute();

		//		MakeStatisticsDataCsvLogic makeStatisticsDataCsvLogic = new MakeStatisticsDataCsvLogic();
		//		makeStatisticsDataCsvLogic.execute();

		//MakeStatisticsGameCountCsvLogic makeStatisticsGameCountCsvLogic = new MakeStatisticsGameCountCsvLogic();
		//makeStatisticsGameCountCsvLogic.makeLogic();
	}

}