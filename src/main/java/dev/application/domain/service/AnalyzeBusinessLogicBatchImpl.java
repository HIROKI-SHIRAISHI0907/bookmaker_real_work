package dev.application.domain.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import dev.application.common.logic.BookMakerDataAnalyzeBusinessLogic;
import dev.application.common.logic.BookMakerDataAnalyzeResultLogic;
import dev.application.common.logic.ConditionResultDataLogic;

/**
 * 分析業務ロジッククラス
 * @author shiraishitoshio
 *
 */
@Service
public class AnalyzeBusinessLogicBatchImpl implements BusinessLogicBatch {

	/**
	 * 業務ロジック
	 */
	//@Autowired
	//private BookMakerDataAnalyzeBusinessLogic businessLogic;

	@Override
	public int execute(String path) {
		int result = 0;
		// 条件分岐データ計測
		ConditionResultDataLogic conditionResultDataLogic = new ConditionResultDataLogic();
		String conditionResultDataSeqResult = conditionResultDataLogic.execute(false);

		BookMakerDataAnalyzeBusinessLogic businessLogic = new BookMakerDataAnalyzeBusinessLogic();
		result = businessLogic.execute(conditionResultDataSeqResult);

		// 条件分岐データ計測
		conditionResultDataSeqResult = conditionResultDataLogic.execute(true);

		BookMakerDataAnalyzeResultLogic analyzeLogic = new BookMakerDataAnalyzeResultLogic();
		try {
			analyzeLogic.execute();
		} catch (IOException | InterruptedException e) {
			// エラー
			return 9;
		}
		return result;
	}

}
