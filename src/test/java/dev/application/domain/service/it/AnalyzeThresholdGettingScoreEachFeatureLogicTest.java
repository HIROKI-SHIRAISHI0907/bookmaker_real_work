package dev.application.domain.service.it;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.application.common.logic.AnalyzeThresholdGettingScoreEachFeatureLogic;

@Tag("IT")
//@SpringBootTest(classes = BookMakersConfig.class)
//@MapperScan("dev.application.repository")
//@ActiveProfiles("test")
class AnalyzeThresholdGettingScoreEachFeatureLogicTest {

    //@Autowired
    //private BusinessLogicBatchImpl businessLogicBatch; // テスト対象クラス

    @Test
    void testExecute_NormalCase_Register() throws Exception {
        // モックの設定: 正常ケース
    	AnalyzeThresholdGettingScoreEachFeatureLogic logicBatch = new AnalyzeThresholdGettingScoreEachFeatureLogic();

        // 実行
        logicBatch.execute();
        // 検証
        //assertEquals(BatchConst.NORMAL_CD, result);
    }
}