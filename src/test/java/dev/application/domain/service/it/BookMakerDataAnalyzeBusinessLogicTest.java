package dev.application.domain.service.it;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import dev.application.common.logic.BookMakerDataAnalyzeBusinessLogic;
import dev.application.db.BookDataSelectWrapper;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.db.UpdateWrapper;
import dev.application.entity.BookDataSelectEntity;

@Tag("UT")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookMakerDataAnalyzeBusinessLogicTest {

	@Mock
	private BookDataSelectWrapper bookDataSelectWrapper;

	@Mock
	private UpdateWrapper updateWrapper;

	@Mock
	private SqlMainLogic select;

	@InjectMocks
	private BookMakerDataAnalyzeBusinessLogic businessLogic;

	@Test
	void testExecute_CheckJudgeValue1() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("10:34");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("1");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("未通知");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("1");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("未通知");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("45:10");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("終了済");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("1");
		entity4.setAwayScore("2");
		entity4.setNoticeFlg("未通知");
		entity4.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'メール非通知対象'");
			assertTrue(hasExpectedJudge1, "メール通知対象");
			assertTrue(hasExpectedJudge2, "メール通知対象");
			assertTrue(hasExpectedJudge3, "メール通知対象");
			assertTrue(hasExpectedJudge4, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue2() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("10:34");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("1");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("未通知");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("1");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("未通知");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("45:10");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = '前終了済データ無し結果不明'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = '前終了済データ無し結果不明'");
			assertTrue(hasExpectedJudge1, "メール通知対象");
			assertTrue(hasExpectedJudge2, "前終了済データ無し結果不明");
			assertTrue(hasExpectedJudge3, "前終了済データ無し結果不明");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue3() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("36:12");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("0");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("未通知");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("47:11");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("0");
		entity2.setNoticeFlg("通知済");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("86:35");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("0");
		entity3.setAwayScore("0");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("終了済");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("0");
		entity4.setAwayScore("0");
		entity4.setNoticeFlg("未通知");
		entity4.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール非通知対象'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知失敗'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'メール非通知対象'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'メール非通知対象'");
			assertTrue(hasExpectedJudge1, "メール非通知対象");
			assertTrue(hasExpectedJudge2, "メール通知失敗");
			assertTrue(hasExpectedJudge3, "メール非通知対象");
			assertTrue(hasExpectedJudge4, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue4() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("14:36");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("0");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("未通知");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("18:25");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("0");
		entity2.setNoticeFlg("通知済");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("---");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("0");
		entity3.setAwayScore("0");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("36:19");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("0");
		entity4.setAwayScore("0");
		entity4.setNoticeFlg("通知済");
		entity4.setJudge(null);

		BookDataSelectEntity entity5 = new BookDataSelectEntity();
		entity5.setSeq("5");
		entity5.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity5.setTimes("42:57");
		entity5.setHomeTeamName("Team A");
		entity5.setAwayTeamName("Team B");
		entity5.setHomeScore("0");
		entity5.setAwayScore("0");
		entity5.setNoticeFlg("未通知");
		entity5.setJudge(null);

		BookDataSelectEntity entity6 = new BookDataSelectEntity();
		entity6.setSeq("6");
		entity6.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity6.setTimes("ハーフタイム");
		entity6.setHomeTeamName("Team A");
		entity6.setAwayTeamName("Team B");
		entity6.setHomeScore("0");
		entity6.setAwayScore("0");
		entity6.setNoticeFlg("通知済");
		entity6.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);
		mockData.add(entity5);
		mockData.add(entity6);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = '前終了済データ無し結果不明'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = '前メール通知情報結果不明'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = '結果不明'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = '前メール通知情報結果不明'");
			boolean hasExpectedJudge5 = updatedValues.get(4).contains("judge = '前終了済データ無し結果不明'");
			boolean hasExpectedJudge6 = updatedValues.get(5).contains("judge = '前メール通知情報結果不明'");
			assertTrue(hasExpectedJudge1, "前終了済データ無し結果不明");
			assertTrue(hasExpectedJudge2, "前メール通知情報結果不明");
			assertTrue(hasExpectedJudge3, "結果不明");
			assertTrue(hasExpectedJudge4, "前メール通知情報結果不明");
			assertTrue(hasExpectedJudge5, "前終了済データ無し結果不明");
			assertTrue(hasExpectedJudge6, "前メール通知情報結果不明");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue5() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("10:34");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("1");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("通知済");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("1");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("未通知");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("45:10");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("終了済");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("2");
		entity4.setAwayScore("3");
		entity4.setNoticeFlg("未通知");
		entity4.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'メール非通知対象'");
			assertTrue(hasExpectedJudge1, "メール通知成功");
			assertTrue(hasExpectedJudge2, "メール通知対象");
			assertTrue(hasExpectedJudge3, "メール通知対象");
			assertTrue(hasExpectedJudge4, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue6() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("---");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("1");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("通知済");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("未通知");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity(); //ダミーデータ
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("23:12");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("26:30");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("0");
		entity4.setAwayScore("1");
		entity4.setNoticeFlg("通知済");
		entity4.setJudge(null);

		BookDataSelectEntity entity5 = new BookDataSelectEntity(); //ダミーデータ
		entity5.setSeq("5");
		entity5.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity5.setTimes("72:35");
		entity5.setHomeTeamName("Team A");
		entity5.setAwayTeamName("Team B");
		entity5.setHomeScore("1");
		entity5.setAwayScore("1");
		entity5.setNoticeFlg("通知済");
		entity5.setJudge(null);

		BookDataSelectEntity entity6 = new BookDataSelectEntity();
		entity6.setSeq("6");
		entity6.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity6.setTimes("76:11");
		entity6.setHomeTeamName("Team A");
		entity6.setAwayTeamName("Team B");
		entity6.setHomeScore("0");
		entity6.setAwayScore("1");
		entity6.setNoticeFlg("通知済");
		entity6.setJudge(null);

		BookDataSelectEntity entity7 = new BookDataSelectEntity();
		entity7.setSeq("7");
		entity7.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity7.setTimes("終了済");
		entity7.setHomeTeamName("Team A");
		entity7.setAwayTeamName("Team B");
		entity7.setHomeScore("1");
		entity7.setAwayScore("2");
		entity7.setNoticeFlg("未通知");
		entity7.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);
		mockData.add(entity5);
		mockData.add(entity6);
		mockData.add(entity7);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = '結果不明'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge5 = updatedValues.get(4).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge6 = updatedValues.get(5).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge7 = updatedValues.get(6).contains("judge = 'メール非通知対象'");
			assertTrue(hasExpectedJudge1, "結果不明");
			assertTrue(hasExpectedJudge2, "メール通知対象");
			assertTrue(hasExpectedJudge3, "ゴール取り消し");
			assertTrue(hasExpectedJudge4, "メール通知成功");
			assertTrue(hasExpectedJudge5, "ゴール取り消し");
			assertTrue(hasExpectedJudge6, "メール通知成功");
			assertTrue(hasExpectedJudge7, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue7() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("---");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("1");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("通知済");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("通知済");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity(); // ダミーデータ
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("23:12");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("26:30");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("0");
		entity4.setAwayScore("1");
		entity4.setNoticeFlg("通知済");
		entity4.setJudge(null);

		BookDataSelectEntity entity5 = new BookDataSelectEntity();
		entity5.setSeq("5");
		entity5.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity5.setTimes("72:35");
		entity5.setHomeTeamName("Team A");
		entity5.setAwayTeamName("Team B");
		entity5.setHomeScore("0");
		entity5.setAwayScore("1");
		entity5.setNoticeFlg("通知済");
		entity5.setJudge(null);

		BookDataSelectEntity entity6 = new BookDataSelectEntity();
		entity6.setSeq("6");
		entity6.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity6.setTimes("76:11");
		entity6.setHomeTeamName("Team A");
		entity6.setAwayTeamName("Team B");
		entity6.setHomeScore("0");
		entity6.setAwayScore("1");
		entity6.setNoticeFlg("通知済");
		entity6.setJudge(null);

		BookDataSelectEntity entity7 = new BookDataSelectEntity();
		entity7.setSeq("7");
		entity7.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity7.setTimes("終了済");
		entity7.setHomeTeamName("Team A");
		entity7.setAwayTeamName("Team B");
		entity7.setHomeScore("0");
		entity7.setAwayScore("1");
		entity7.setNoticeFlg("未通知");
		entity7.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);
		mockData.add(entity5);
		mockData.add(entity6);
		mockData.add(entity7);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = '結果不明'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'メール通知失敗'");
			boolean hasExpectedJudge5 = updatedValues.get(4).contains("judge = 'メール通知失敗'");
			boolean hasExpectedJudge6 = updatedValues.get(5).contains("judge = 'メール通知失敗'");
			boolean hasExpectedJudge7 = updatedValues.get(6).contains("judge = 'メール非通知対象'");
			boolean hasExpectedJudge2upd = updatedValues.get(7).contains("judge = 'ゴール取り消しによる成功失敗変更'");
			assertTrue(hasExpectedJudge1, "結果不明");
			assertTrue(hasExpectedJudge2, "メール通知成功");
			assertTrue(hasExpectedJudge2upd, "ゴール取り消しによる成功失敗変更");
			assertTrue(hasExpectedJudge3, "ゴール取り消し");
			assertTrue(hasExpectedJudge4, "メール通知失敗");
			assertTrue(hasExpectedJudge5, "メール通知失敗");
			assertTrue(hasExpectedJudge6, "メール通知失敗");
			assertTrue(hasExpectedJudge7, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue8() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("1:45");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("0");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("通知済");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("7:36");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("1");
		entity2.setNoticeFlg("通知済");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity(); // ダミーデータ
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("12:35");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("1");
		entity3.setAwayScore("1");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity(); // ダミーデータ
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("16:30");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("1");
		entity4.setAwayScore("1");
		entity4.setNoticeFlg("通知済");
		entity4.setJudge(null);

		BookDataSelectEntity entity5 = new BookDataSelectEntity();
		entity5.setSeq("5");
		entity5.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity5.setTimes("21:35");
		entity5.setHomeTeamName("Team A");
		entity5.setAwayTeamName("Team B");
		entity5.setHomeScore("0");
		entity5.setAwayScore("1");
		entity5.setNoticeFlg("通知済");
		entity5.setJudge(null);

		BookDataSelectEntity entity6 = new BookDataSelectEntity(); // ダミーデータ
		entity6.setSeq("6");
		entity6.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity6.setTimes("28:11");
		entity6.setHomeTeamName("Team A");
		entity6.setAwayTeamName("Team B");
		entity6.setHomeScore("1");
		entity6.setAwayScore("1");
		entity6.setNoticeFlg("通知済");
		entity6.setJudge(null);

		BookDataSelectEntity entity7 = new BookDataSelectEntity();
		entity7.setSeq("7");
		entity7.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity7.setTimes("35:11");
		entity7.setHomeTeamName("Team A");
		entity7.setAwayTeamName("Team B");
		entity7.setHomeScore("0");
		entity7.setAwayScore("1");
		entity7.setNoticeFlg("未通知");
		entity7.setJudge(null);

		BookDataSelectEntity entity8 = new BookDataSelectEntity();
		entity8.setSeq("8");
		entity8.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity8.setTimes("終了済");
		entity8.setHomeTeamName("Team A");
		entity8.setAwayTeamName("Team B");
		entity8.setHomeScore("0");
		entity8.setAwayScore("1");
		entity8.setNoticeFlg("未通知");
		entity8.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);
		mockData.add(entity5);
		mockData.add(entity6);
		mockData.add(entity7);
		mockData.add(entity8);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge5 = updatedValues.get(4).contains("judge = 'メール通知成功'");
			boolean hasExpectedJudge6 = updatedValues.get(5).contains("judge = 'ゴール取り消し'");
			boolean hasExpectedJudge7 = updatedValues.get(6).contains("judge = 'メール非通知対象'");
			boolean hasExpectedJudge8 = updatedValues.get(7).contains("judge = 'メール非通知対象'");
			boolean hasExpectedJudge2upd = updatedValues.get(8).contains("judge = 'ゴール取り消しによる成功失敗変更'");
			boolean hasExpectedJudge5upd = updatedValues.get(9).contains("judge = 'ゴール取り消しによる成功失敗変更'");
			assertTrue(hasExpectedJudge1, "メール通知成功");
			assertTrue(hasExpectedJudge2, "メール通知成功");
			assertTrue(hasExpectedJudge2upd, "ゴール取り消しによる成功失敗変更");
			assertTrue(hasExpectedJudge3, "ゴール取り消し");
			assertTrue(hasExpectedJudge4, "ゴール取り消し");
			assertTrue(hasExpectedJudge5, "メール通知成功");
			assertTrue(hasExpectedJudge5upd, "ゴール取り消しによる成功失敗変更");
			assertTrue(hasExpectedJudge6, "ゴール取り消し");
			assertTrue(hasExpectedJudge7, "メール非通知対象");
			assertTrue(hasExpectedJudge8, "メール非通知対象");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}

	@Test
	void testExecute_CheckJudgeValue9() throws Exception {
		// データ
		List<BookDataSelectEntity> mockData = new ArrayList<>();
		BookDataSelectEntity entity1 = new BookDataSelectEntity();
		entity1.setSeq("1");
		entity1.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity1.setTimes("10:34");
		entity1.setHomeTeamName("Team A");
		entity1.setAwayTeamName("Team B");
		entity1.setHomeScore("0");
		entity1.setAwayScore("0");
		entity1.setNoticeFlg("未通知");
		entity1.setJudge(null); // 初期値はnull

		BookDataSelectEntity entity2 = new BookDataSelectEntity();
		entity2.setSeq("2");
		entity2.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity2.setTimes("20:56");
		entity2.setHomeTeamName("Team A");
		entity2.setAwayTeamName("Team B");
		entity2.setHomeScore("0");
		entity2.setAwayScore("0");
		entity2.setNoticeFlg("未通知");
		entity2.setJudge(null);

		BookDataSelectEntity entity3 = new BookDataSelectEntity();
		entity3.setSeq("3");
		entity3.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity3.setTimes("45:10");
		entity3.setHomeTeamName("Team A");
		entity3.setAwayTeamName("Team B");
		entity3.setHomeScore("0");
		entity3.setAwayScore("0");
		entity3.setNoticeFlg("未通知");
		entity3.setJudge(null);

		BookDataSelectEntity entity4 = new BookDataSelectEntity();
		entity4.setSeq("4");
		entity4.setDataCategory("ケニア :プレミアリーグ -ラウンド13");
		entity4.setTimes("65:45");
		entity4.setHomeTeamName("Team A");
		entity4.setAwayTeamName("Team B");
		entity4.setHomeScore("0");
		entity4.setAwayScore("1");
		entity4.setNoticeFlg("未通知");
		entity4.setJudge(null);

		mockData.add(entity1);
		mockData.add(entity2);
		mockData.add(entity3);
		mockData.add(entity4);

		when(bookDataSelectWrapper.executeMinSeqChkNoUpdateRecord()).thenReturn(1);

		when(bookDataSelectWrapper.executeCountSelect(eq(UniairConst.BM_M001), isNull())).thenReturn(1);

		// `executeSelect` のモック（初回データ取得）
		when(bookDataSelectWrapper.executeSelect(eq(1), isNull(), eq(false))).thenReturn(mockData);
		when(bookDataSelectWrapper.executeSelect(eq(2), isNull(), eq(false))).thenReturn(new ArrayList<>());

		// `updateExecute` のモック（更新成功を模擬）
		when(updateWrapper.updateExecute(anyString(), anyString(), anyString())).thenReturn(1);

		try {
			// 実行
			int result = businessLogic.execute("1");

			// 結果が正常終了であることを確認
			assertEquals(0, result, "正常に終了するはず");

			// `updateExecute` で更新される seq,judge の値をキャプチャ
			ArgumentCaptor<String> seqCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> updateCaptor = ArgumentCaptor.forClass(String.class);
			verify(updateWrapper, atLeastOnce()).updateExecute(anyString(), seqCaptor.capture(),
					updateCaptor.capture());

			// すべての `seq, judge` の値を取得
			List<String> seqValues = seqCaptor.getAllValues();
			List<String> updatedValues = updateCaptor.getAllValues();
			// 実際の値
			for (int i = 0; i < updatedValues.size(); i++) {
				System.out.println("実際のseqの値:" + seqValues.get(i));
				System.out.println("実際のjudgeの値:" + updatedValues.get(i));
			}

			// `judge` に特定の値が含まれているか確認
			boolean hasExpectedJudge1 = updatedValues.get(0).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge2 = updatedValues.get(1).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge3 = updatedValues.get(2).contains("judge = 'メール通知対象'");
			boolean hasExpectedJudge4 = updatedValues.get(3).contains("judge = '前終了済データ無し結果不明'");
			assertTrue(hasExpectedJudge1, "メール通知対象");
			assertTrue(hasExpectedJudge2, "メール通知対象");
			assertTrue(hasExpectedJudge3, "メール通知対象");
			assertTrue(hasExpectedJudge4, "前終了済データ無し結果不明");

			// `executeSelect` が 1 回以上呼ばれていることを確認
			verify(bookDataSelectWrapper, atLeastOnce()).executeSelect(anyInt(), any(), anyBoolean());

		} catch (Exception e) {
			e.printStackTrace();
			fail("例外が発生しました: " + e.getMessage());
		}
	}
}