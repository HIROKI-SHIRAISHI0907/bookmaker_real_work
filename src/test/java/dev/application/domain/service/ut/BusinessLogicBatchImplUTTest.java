package dev.application.domain.service.ut;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.application.common.logic.BookMakerDataRegisterBusinessLogic;
import dev.application.constant.BatchConst;
import dev.application.domain.service.RegisterBusinessLogicBatchImpl;

@Tag("UT")
@ExtendWith(MockitoExtension.class)
class BusinessLogicBatchImplUTTest {

    @Mock
	private BookMakerDataRegisterBusinessLogic businessLogic; // モックの依存クラス

    @InjectMocks
    private RegisterBusinessLogicBatchImpl businessLogicBatch; // テスト対象クラス

    @Test
    void testExecute_NormalCase() throws Exception {
        // モックの設定: 正常ケース
        String testPath = "valid/path";
        when(this.businessLogic.execute(testPath)).thenReturn(BatchConst.NORMAL_CD);

        // 実行
        int result = businessLogicBatch.execute(testPath);

        // 検証
        assertEquals(BatchConst.NORMAL_CD, result);
        verify(this.businessLogic, times(1)).execute(testPath); // businessLogic.executeが1回呼ばれたことを確認
    }
}