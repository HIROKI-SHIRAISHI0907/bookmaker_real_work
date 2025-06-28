package dev.application.common.book.delete.ut;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import dev.application.TestFileUtils;
import dev.application.common.book.delete.DeleteBook;
import dev.application.common.constant.BookMakersCommonConst;
import dev.application.common.dto.DeleteBookInputDTO;
import dev.application.common.dto.DeleteBookOutputDTO;

class DeleteBookTest {

	private DeleteBook deleteBook;

	private String targetFilePath;

	@BeforeEach
	void setUp() {
		deleteBook = new DeleteBook();
		targetFilePath = "/Users/shiraishitoshio/bookmaker/test/";
	}

	@Test
	void testExecute_NormalCase(TestInfo testInfo) throws IOException {
		String targetPath = targetFilePath + "output_1.xlsx";
		TestFileUtils.copyResourceTo(
				"src/test/resources/testExecute_NormalCase/output_1.xlsx",
				targetFilePath,
				targetPath);

		// 入力DTOの準備
		DeleteBookInputDTO inputDTO = new DeleteBookInputDTO();
		inputDTO.setDataPath(targetPath);

		// テスト実行
		DeleteBookOutputDTO outputDTO = deleteBook.execute(inputDTO);

		// 検証
		assertEquals(BookMakersCommonConst.NORMAL_CD, outputDTO.getResultCd());
		assertNull(outputDTO.getExceptionProject());
		assertNull(outputDTO.getExceptionClass());
		assertNull(outputDTO.getExceptionMethod());
		assertNull(outputDTO.getErrMessage());
		assertNull(outputDTO.getThrowAble());

		TestFileUtils.deleteResourceTo(targetFilePath);
	}
}