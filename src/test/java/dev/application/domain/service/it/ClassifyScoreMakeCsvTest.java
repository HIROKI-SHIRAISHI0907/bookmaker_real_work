package dev.application.domain.service.it;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.application.common.file.MakeClassifyScoreCsv;
import dev.application.common.file.MakeClassifyScoreDataCsv;
import dev.application.common.logic.ClassifyScoreMakeCsvHelperLogic;

@Tag("IT")
//@SpringBootTest(classes = BookMakersConfig.class)
//@MapperScan("dev.application.repository")
//@ActiveProfiles("test")
class ClassifyScoreMakeCsvTest {

    @Test
    void testExecute_NormalCase_Analyze() throws Exception {
    	// モックの設定: 正常ケース
    	ClassifyScoreMakeCsvHelperLogic classifyScoreMakeCsvHelperLogic
    		= new ClassifyScoreMakeCsvHelperLogic();
        // 実行
    	//classifyScoreMakeCsvHelperLogic.execute();

    	MakeClassifyScoreDataCsv makeClassifyScoreDataCsv = new MakeClassifyScoreDataCsv();
    	//makeClassifyScoreDataCsv.execute();

    	MakeClassifyScoreCsv makeClassifyScoreCsv = new MakeClassifyScoreCsv();
    	makeClassifyScoreCsv.execute();
    }
}