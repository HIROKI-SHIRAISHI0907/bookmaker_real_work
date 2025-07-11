package dev.application.domain.service.it;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.application.constant.BatchConst;
import dev.application.domain.service.AnalyzeBusinessLogicBatchImpl;

@Tag("IT")
//@SpringBootTest(classes = BookMakersConfig.class)
//@MapperScan("dev.application.repository")
//@ActiveProfiles("test")
class AnalyzeBusinessLogicBatchImplITTest {

    //@Autowired
    //private BusinessLogicBatchImpl businessLogicBatch; // テスト対象クラス

    @Test
    void testExecute_NormalCase_Analyze() throws Exception {
    	// モックの設定: 正常ケース
    	AnalyzeBusinessLogicBatchImpl businessLogicBatch = new AnalyzeBusinessLogicBatchImpl();
        String testPath = "/Users/shiraishitoshio/bookmaker/";

        // 実行
        int result = businessLogicBatch.execute(testPath);
        // 検証
        assertEquals(BatchConst.NORMAL_CD, result);
    }
}