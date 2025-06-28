package dev.application.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.application.db.UniairConst;

/**
 * DB項目,ヘッダー用マッピングUtilクラス
 * @author shiraishitoshio
 *
 */
public class UniairColumnMapUtil {

	/**
	 * DB項目,テーブル名Mapping
	 */
	public static final Map<String, Map<String, String>> TABLE_MAP;
	static {
		Map<String, Map<String, String>> ListMap = new LinkedHashMap<>();

		Map<String, String> ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M001, "data");
		ListMstDetailMap.put(UniairConst.BM_M002, "condition_result_data");
		ListMstDetailMap.put(UniairConst.BM_M003, "team_statistics_data");
		ListMstDetailMap.put(UniairConst.BM_M004, "game_statistics_detail_data");
		ListMstDetailMap.put(UniairConst.BM_M005, "zero_score_data");
		ListMstDetailMap.put(UniairConst.BM_M006, "type_of_country_league_data");
		ListMstDetailMap.put(UniairConst.BM_M007, "within_data");
		ListMstDetailMap.put(UniairConst.BM_M008, "within_data_20minutes_home_scored");
		ListMstDetailMap.put(UniairConst.BM_M009, "within_data_20minutes_away_scored");
		ListMstDetailMap.put(UniairConst.BM_M010, "within_data_20minutes_same_scored");
		ListMstDetailMap.put(UniairConst.BM_M011, "within_data_45minutes_home_scored");
		ListMstDetailMap.put(UniairConst.BM_M012, "within_data_45minutes_away_scored");
		ListMstDetailMap.put(UniairConst.BM_M013, "within_data_20minutes_home_all_league");
		ListMstDetailMap.put(UniairConst.BM_M014, "within_data_20minutes_away_all_league");
		ListMstDetailMap.put(UniairConst.BM_M015, "within_data_45minutes_home_all_league");
		ListMstDetailMap.put(UniairConst.BM_M016, "within_data_45minutes_away_all_league");
		ListMstDetailMap.put(UniairConst.BM_M017, "within_data_scored_counter");
		ListMstDetailMap.put(UniairConst.BM_M018, "within_data_scored_counter_detail");
		ListMstDetailMap.put(UniairConst.BM_M019, "classify_result_data");
		ListMstDetailMap.put(UniairConst.BM_M020, "classify_result_data_detail");
		ListMstDetailMap.put(UniairConst.BM_M021, "average_feature_data");
		ListMstDetailMap.put(UniairConst.BM_M022, "scoring_playstyle_past_data");
		ListMstDetailMap.put(UniairConst.BM_M023, "average_statistics_data");
		ListMstDetailMap.put(UniairConst.BM_M024, "correlation_data");
		ListMstDetailMap.put(UniairConst.BM_M025, "correlation_ranking_data");
		ListMstDetailMap.put(UniairConst.BM_M026, "average_statistics_data_detail");
		ListMstDetailMap.put(UniairConst.BM_M027, "average_statistics_csv_tmp_data");
		ListMstDetailMap.put(UniairConst.BM_M028, "average_statistics_team_detail_data");
		ListMstDetailMap.put(UniairConst.BM_M029, "collect_range_score");
		ListMstDetailMap.put(UniairConst.BM_M097, "upd_csv_info");
		ListMstDetailMap.put(UniairConst.BM_M098, "file_chk_tmp");
		ListMstDetailMap.put(UniairConst.BM_M099, "file_chk");
		ListMap.put("soccer_bm", ListMstDetailMap);
		TABLE_MAP = Collections.unmodifiableMap(ListMap);
	}

	/**
	 * DB項目,ヘッダー用Mapping
	 */
	public static final Map<String, Map<Map<String, String>, Map<String, String>>> COLUMN_MAP;
	static {
		Map<String, Map<Map<String, String>, Map<String, String>>> columnKeyMap = new LinkedHashMap<>();
		Map<Map<String, String>, Map<String, String>> columnSceneCardMap = new LinkedHashMap<>();
		Map<String, String> columnRestMap = new LinkedHashMap<>();
		Map<String, String> columnMainMap = new LinkedHashMap<>();
		columnMainMap.put("連番", "seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("条件分岐結果ID", "condition_result_data_seq_id");
		columnRestMap.put("VARCHAR(11)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合国カテゴリ", "data_category");
		columnRestMap.put("CHAR(100)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間", "times");
		columnRestMap.put("CHAR(30)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム順位", "home_rank");
		columnRestMap.put("CHAR(3)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム", "home_team_name");
		columnRestMap.put("CHAR(100)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスコア", "home_score");
		columnRestMap.put("CHAR(2)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー順位", "away_rank");
		columnRestMap.put("CHAR(100)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム", "away_team_name");
		columnRestMap.put("CHAR(100)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースコア", "away_score");
		columnRestMap.put("CHAR(2)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値", "home_exp");
		columnRestMap.put("CHAR(5)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値", "away_exp");
		columnRestMap.put("CHAR(5)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム支配率", "home_donation");
		columnRestMap.put("CHAR(5)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー支配率", "away_donation");
		columnRestMap.put("CHAR(5)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート", "home_shoot_all");
		columnRestMap.put("CHAR(3)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート", "away_shoot_all");
		columnRestMap.put("CHAR(3)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュート", "home_shoot_in");
		columnRestMap.put("CHAR(3)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュート", "away_shoot_in");
		columnRestMap.put("CHAR(3)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュート", "home_shoot_out");
		columnRestMap.put("CHAR(3)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュート", "away_shoot_out");
		columnRestMap.put("CHAR(3)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュート", "home_block_shoot");
		columnRestMap.put("CHAR(3)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュート", "away_block_shoot");
		columnRestMap.put("CHAR(3)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビックチャンス", "home_big_chance");
		columnRestMap.put("CHAR(3)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービックチャンス", "away_big_chance");
		columnRestMap.put("CHAR(3)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキック", "home_corner");
		columnRestMap.put("CHAR(3)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキック", "away_corner");
		columnRestMap.put("CHAR(3)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠内シュート", "home_box_shoot_in");
		columnRestMap.put("CHAR(3)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠内シュート", "away_box_shoot_in");
		columnRestMap.put("CHAR(3)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠外シュート", "home_box_shoot_out");
		columnRestMap.put("CHAR(3)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠外シュート", "away_box_shoot_out");
		columnRestMap.put("CHAR(3)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポスト", "home_goal_post");
		columnRestMap.put("CHAR(3)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポスト", "away_goal_post");
		columnRestMap.put("CHAR(3)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴール", "home_goal_head");
		columnRestMap.put("CHAR(3)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴール", "away_goal_head");
		columnRestMap.put("CHAR(3)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブ", "home_keeper_save");
		columnRestMap.put("CHAR(3)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブ", "away_keeper_save");
		columnRestMap.put("CHAR(3)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキック", "home_free_kick");
		columnRestMap.put("CHAR(3)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキック", "away_free_kick");
		columnRestMap.put("CHAR(3)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイド", "home_offside");
		columnRestMap.put("CHAR(3)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイド", "away_offside");
		columnRestMap.put("CHAR(3)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファール", "home_foul");
		columnRestMap.put("CHAR(3)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファール", "away_foul");
		columnRestMap.put("CHAR(3)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカード", "home_yellow_card");
		columnRestMap.put("CHAR(3)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカード", "away_yellow_card");
		columnRestMap.put("CHAR(3)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカード", "home_red_card");
		columnRestMap.put("CHAR(3)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカード", "away_red_card");
		columnRestMap.put("CHAR(3)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローイン", "home_slow_in");
		columnRestMap.put("CHAR(3)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローイン", "away_slow_in");
		columnRestMap.put("CHAR(3)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチ", "home_box_touch");
		columnRestMap.put("CHAR(3)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチ", "away_box_touch");
		columnRestMap.put("CHAR(3)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数", "home_pass_count");
		columnRestMap.put("CHAR(3)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数", "away_pass_count");
		columnRestMap.put("CHAR(3)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数", "home_final_third_pass_count");
		columnRestMap.put("CHAR(3)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数", "away_final_third_pass_count");
		columnRestMap.put("CHAR(3)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数", "home_cross_count");
		columnRestMap.put("CHAR(3)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数", "away_cross_count");
		columnRestMap.put("CHAR(3)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数", "home_tackle_count");
		columnRestMap.put("CHAR(3)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数", "away_tackle_count");
		columnRestMap.put("CHAR(3)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数", "home_clear_count");
		columnRestMap.put("CHAR(3)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数", "away_clear_count");
		columnRestMap.put("CHAR(3)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数", "home_intercept_count");
		columnRestMap.put("CHAR(3)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数", "away_intercept_count");
		columnRestMap.put("CHAR(3)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("記録時間", "record_time");
		columnRestMap.put("TIMESTAMP45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("天気", "weather");
		columnRestMap.put("CHAR(5)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("気温", "temparature");
		columnRestMap.put("CHAR(10)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("湿度", "humid");
		columnRestMap.put("CHAR(10)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("審判", "judge_member");
		columnRestMap.put("CHAR(20)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム監督", "home_manager");
		columnRestMap.put("CHAR(20)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー監督", "away_manager");
		columnRestMap.put("CHAR(20)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフォーメーション", "home_formation");
		columnRestMap.put("CHAR(20)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフォーメーション", "away_formation");
		columnRestMap.put("CHAR(20)73", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スタジアム", "studium");
		columnRestMap.put("CHAR(20)74", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("収容人数", "capacity");
		columnRestMap.put("CHAR(20)75", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("観客数", "audience");
		columnRestMap.put("CHAR(20)76", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者", "home_max_getting_scorer");
		columnRestMap.put("CHAR(20)77", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者", "away_max_getting_scorer");
		columnRestMap.put("CHAR(20)78", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者出場状況", "home_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)79", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者出場状況", "away_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)80", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム得点数", "home_team_home_score");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム失点数", "home_team_home_lost");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム得点数", "away_team_home_score");
		columnRestMap.put("CHAR(20)82", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム失点数", "away_team_home_lost");
		columnRestMap.put("CHAR(20)83", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー得点数", "home_team_away_score");
		columnRestMap.put("CHAR(20)84", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー失点数", "home_team_away_lost");
		columnRestMap.put("CHAR(20)85", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー得点数", "away_team_away_score");
		columnRestMap.put("CHAR(20)86", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー失点数", "away_team_away_lost");
		columnRestMap.put("CHAR(20)87", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("通知フラグ", "notice_flg");
		columnRestMap.put("CHAR(8)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア時間", "goal_time");
		columnRestMap.put("CHAR(8)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア選手", "goal_team_member");
		columnRestMap.put("CHAR(100)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("判定結果", "judge");
		columnRestMap.put("CHAR(50)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームスタイル", "home_team_style");
		columnRestMap.put("CHAR(50)a1", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームスタイル", "away_team_style");
		columnRestMap.put("CHAR(50)a2", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール確率", "probablity");
		columnRestMap.put("CHAR(50)a3", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点予想時間", "prediction_score_time");
		columnRestMap.put("CHAR(50)a4", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M001, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M007, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("連番", "seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合国カテゴリ", "data_category");
		columnRestMap.put("CHAR(100)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間", "times");
		columnRestMap.put("CHAR(30)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム順位", "home_rank");
		columnRestMap.put("CHAR(3)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム", "home_team_name");
		columnRestMap.put("CHAR(100)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスコア", "home_score");
		columnRestMap.put("CHAR(2)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー順位", "away_rank");
		columnRestMap.put("CHAR(100)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム", "away_team_name");
		columnRestMap.put("CHAR(100)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースコア", "away_score");
		columnRestMap.put("CHAR(2)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値", "home_exp");
		columnRestMap.put("CHAR(5)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値", "away_exp");
		columnRestMap.put("CHAR(5)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム支配率", "home_donation");
		columnRestMap.put("CHAR(5)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー支配率", "away_donation");
		columnRestMap.put("CHAR(5)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート", "home_shoot_all");
		columnRestMap.put("CHAR(3)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート", "away_shoot_all");
		columnRestMap.put("CHAR(3)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュート", "home_shoot_in");
		columnRestMap.put("CHAR(3)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュート", "away_shoot_in");
		columnRestMap.put("CHAR(3)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュート", "home_shoot_out");
		columnRestMap.put("CHAR(3)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュート", "away_shoot_out");
		columnRestMap.put("CHAR(3)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュート", "home_block_shoot");
		columnRestMap.put("CHAR(3)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュート", "away_block_shoot");
		columnRestMap.put("CHAR(3)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビックチャンス", "home_big_chance");
		columnRestMap.put("CHAR(3)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービックチャンス", "away_big_chance");
		columnRestMap.put("CHAR(3)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキック", "home_corner");
		columnRestMap.put("CHAR(3)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキック", "away_corner");
		columnRestMap.put("CHAR(3)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠内シュート", "home_box_shoot_in");
		columnRestMap.put("CHAR(3)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠内シュート", "away_box_shoot_in");
		columnRestMap.put("CHAR(3)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠外シュート", "home_box_shoot_out");
		columnRestMap.put("CHAR(3)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠外シュート", "away_box_shoot_out");
		columnRestMap.put("CHAR(3)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポスト", "home_goal_post");
		columnRestMap.put("CHAR(3)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポスト", "away_goal_post");
		columnRestMap.put("CHAR(3)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴール", "home_goal_head");
		columnRestMap.put("CHAR(3)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴール", "away_goal_head");
		columnRestMap.put("CHAR(3)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブ", "home_keeper_save");
		columnRestMap.put("CHAR(3)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブ", "away_keeper_save");
		columnRestMap.put("CHAR(3)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキック", "home_free_kick");
		columnRestMap.put("CHAR(3)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキック", "away_free_kick");
		columnRestMap.put("CHAR(3)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイド", "home_offside");
		columnRestMap.put("CHAR(3)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイド", "away_offside");
		columnRestMap.put("CHAR(3)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファール", "home_foul");
		columnRestMap.put("CHAR(3)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファール", "away_foul");
		columnRestMap.put("CHAR(3)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカード", "home_yellow_card");
		columnRestMap.put("CHAR(3)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカード", "away_yellow_card");
		columnRestMap.put("CHAR(3)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカード", "home_red_card");
		columnRestMap.put("CHAR(3)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカード", "away_red_card");
		columnRestMap.put("CHAR(3)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローイン", "home_slow_in");
		columnRestMap.put("CHAR(3)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローイン", "away_slow_in");
		columnRestMap.put("CHAR(3)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチ", "home_box_touch");
		columnRestMap.put("CHAR(3)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチ", "away_box_touch");
		columnRestMap.put("CHAR(3)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数", "home_pass_count");
		columnRestMap.put("CHAR(3)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数", "away_pass_count");
		columnRestMap.put("CHAR(3)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数", "home_final_third_pass_count");
		columnRestMap.put("CHAR(3)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数", "away_final_third_pass_count");
		columnRestMap.put("CHAR(3)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数", "home_cross_count");
		columnRestMap.put("CHAR(3)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数", "away_cross_count");
		columnRestMap.put("CHAR(3)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数", "home_tackle_count");
		columnRestMap.put("CHAR(3)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数", "away_tackle_count");
		columnRestMap.put("CHAR(3)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数", "home_clear_count");
		columnRestMap.put("CHAR(3)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数", "away_clear_count");
		columnRestMap.put("CHAR(3)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数", "home_intercept_count");
		columnRestMap.put("CHAR(3)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数", "away_intercept_count");
		columnRestMap.put("CHAR(3)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("記録時間", "record_time");
		columnRestMap.put("TIMESTAMP45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("天気", "weather");
		columnRestMap.put("CHAR(5)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("気温", "temparature");
		columnRestMap.put("CHAR(10)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("湿度", "humid");
		columnRestMap.put("CHAR(10)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("審判", "judge_member");
		columnRestMap.put("CHAR(20)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム監督", "home_manager");
		columnRestMap.put("CHAR(20)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー監督", "away_manager");
		columnRestMap.put("CHAR(20)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフォーメーション", "home_formation");
		columnRestMap.put("CHAR(20)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフォーメーション", "away_formation");
		columnRestMap.put("CHAR(20)73", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スタジアム", "studium");
		columnRestMap.put("CHAR(20)74", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("収容人数", "capacity");
		columnRestMap.put("CHAR(20)75", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("観客数", "audience");
		columnRestMap.put("CHAR(20)76", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者", "home_max_getting_scorer");
		columnRestMap.put("CHAR(20)77", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者", "away_max_getting_scorer");
		columnRestMap.put("CHAR(20)78", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者出場状況", "home_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)79", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者出場状況", "away_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)80", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム得点数", "home_team_home_score");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム失点数", "home_team_home_lost");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム得点数", "away_team_home_score");
		columnRestMap.put("CHAR(20)82", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム失点数", "away_team_home_lost");
		columnRestMap.put("CHAR(20)83", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー得点数", "home_team_away_score");
		columnRestMap.put("CHAR(20)84", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー失点数", "home_team_away_lost");
		columnRestMap.put("CHAR(20)85", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー得点数", "away_team_away_score");
		columnRestMap.put("CHAR(20)86", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー失点数", "away_team_away_lost");
		columnRestMap.put("CHAR(20)87", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("通知フラグ", "notice_flg");
		columnRestMap.put("CHAR(8)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア時間", "goal_time");
		columnRestMap.put("CHAR(8)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア選手", "goal_team_member");
		columnRestMap.put("CHAR(100)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("判定結果", "judge");
		columnRestMap.put("CHAR(50)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームスタイル", "home_team_style");
		columnRestMap.put("CHAR(50)a1", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームスタイル", "away_team_style");
		columnRestMap.put("CHAR(50)a2", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール確率", "probablity");
		columnRestMap.put("CHAR(50)a3", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点予想時間", "prediction_score_time");
		columnRestMap.put("CHAR(50)a4", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M005, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("分類モード", "classify_mode");
		columnRestMap.put("VARCHAR(2)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合国カテゴリ", "data_category");
		columnRestMap.put("CHAR(100)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間", "times");
		columnRestMap.put("CHAR(30)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム順位", "home_rank");
		columnRestMap.put("CHAR(3)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム", "home_team_name");
		columnRestMap.put("CHAR(100)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスコア", "home_score");
		columnRestMap.put("CHAR(2)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー順位", "away_rank");
		columnRestMap.put("CHAR(100)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム", "away_team_name");
		columnRestMap.put("CHAR(100)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースコア", "away_score");
		columnRestMap.put("CHAR(2)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値", "home_exp");
		columnRestMap.put("CHAR(5)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値", "away_exp");
		columnRestMap.put("CHAR(5)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム支配率", "home_donation");
		columnRestMap.put("CHAR(5)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー支配率", "away_donation");
		columnRestMap.put("CHAR(5)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート", "home_shoot_all");
		columnRestMap.put("CHAR(3)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート", "away_shoot_all");
		columnRestMap.put("CHAR(3)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュート", "home_shoot_in");
		columnRestMap.put("CHAR(3)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュート", "away_shoot_in");
		columnRestMap.put("CHAR(3)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュート", "home_shoot_out");
		columnRestMap.put("CHAR(3)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュート", "away_shoot_out");
		columnRestMap.put("CHAR(3)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュート", "home_block_shoot");
		columnRestMap.put("CHAR(3)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュート", "away_block_shoot");
		columnRestMap.put("CHAR(3)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビックチャンス", "home_big_chance");
		columnRestMap.put("CHAR(3)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービックチャンス", "away_big_chance");
		columnRestMap.put("CHAR(3)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキック", "home_corner");
		columnRestMap.put("CHAR(3)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキック", "away_corner");
		columnRestMap.put("CHAR(3)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠内シュート", "home_box_shoot_in");
		columnRestMap.put("CHAR(3)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠内シュート", "away_box_shoot_in");
		columnRestMap.put("CHAR(3)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス枠外シュート", "home_box_shoot_out");
		columnRestMap.put("CHAR(3)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス枠外シュート", "away_box_shoot_out");
		columnRestMap.put("CHAR(3)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポスト", "home_goal_post");
		columnRestMap.put("CHAR(3)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポスト", "away_goal_post");
		columnRestMap.put("CHAR(3)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴール", "home_goal_head");
		columnRestMap.put("CHAR(3)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴール", "away_goal_head");
		columnRestMap.put("CHAR(3)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブ", "home_keeper_save");
		columnRestMap.put("CHAR(3)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブ", "away_keeper_save");
		columnRestMap.put("CHAR(3)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキック", "home_free_kick");
		columnRestMap.put("CHAR(3)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキック", "away_free_kick");
		columnRestMap.put("CHAR(3)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイド", "home_offside");
		columnRestMap.put("CHAR(3)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイド", "away_offside");
		columnRestMap.put("CHAR(3)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファール", "home_foul");
		columnRestMap.put("CHAR(3)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファール", "away_foul");
		columnRestMap.put("CHAR(3)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカード", "home_yellow_card");
		columnRestMap.put("CHAR(3)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカード", "away_yellow_card");
		columnRestMap.put("CHAR(3)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカード", "home_red_card");
		columnRestMap.put("CHAR(3)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカード", "away_red_card");
		columnRestMap.put("CHAR(3)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローイン", "home_slow_in");
		columnRestMap.put("CHAR(3)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローイン", "away_slow_in");
		columnRestMap.put("CHAR(3)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチ", "home_box_touch");
		columnRestMap.put("CHAR(3)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチ", "away_box_touch");
		columnRestMap.put("CHAR(3)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数", "home_pass_count");
		columnRestMap.put("CHAR(3)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数", "away_pass_count");
		columnRestMap.put("CHAR(3)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数", "home_final_third_pass_count");
		columnRestMap.put("CHAR(3)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数", "away_final_third_pass_count");
		columnRestMap.put("CHAR(3)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数", "home_cross_count");
		columnRestMap.put("CHAR(3)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数", "away_cross_count");
		columnRestMap.put("CHAR(3)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数", "home_tackle_count");
		columnRestMap.put("CHAR(3)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数", "away_tackle_count");
		columnRestMap.put("CHAR(3)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数", "home_clear_count");
		columnRestMap.put("CHAR(3)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数", "away_clear_count");
		columnRestMap.put("CHAR(3)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数", "home_intercept_count");
		columnRestMap.put("CHAR(3)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数", "away_intercept_count");
		columnRestMap.put("CHAR(3)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("記録時間", "record_time");
		columnRestMap.put("TIMESTAMP45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("天気", "weather");
		columnRestMap.put("CHAR(5)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("気温", "temparature");
		columnRestMap.put("CHAR(10)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("湿度", "humid");
		columnRestMap.put("CHAR(10)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("審判", "judge_member");
		columnRestMap.put("CHAR(20)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム監督", "home_manager");
		columnRestMap.put("CHAR(20)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー監督", "away_manager");
		columnRestMap.put("CHAR(20)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフォーメーション", "home_formation");
		columnRestMap.put("CHAR(20)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフォーメーション", "away_formation");
		columnRestMap.put("CHAR(20)73", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スタジアム", "studium");
		columnRestMap.put("CHAR(20)74", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("収容人数", "capacity");
		columnRestMap.put("CHAR(20)75", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("観客数", "audience");
		columnRestMap.put("CHAR(20)76", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者", "home_max_getting_scorer");
		columnRestMap.put("CHAR(20)77", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者", "away_max_getting_scorer");
		columnRestMap.put("CHAR(20)78", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム最大得点者出場状況", "home_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)79", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム最大得点者出場状況", "away_max_getting_scorer_game_situation");
		columnRestMap.put("CHAR(20)80", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム得点数", "home_team_home_score");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームホーム失点数", "home_team_home_lost");
		columnRestMap.put("CHAR(20)81", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム得点数", "away_team_home_score");
		columnRestMap.put("CHAR(20)82", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームホーム失点数", "away_team_home_lost");
		columnRestMap.put("CHAR(20)83", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー得点数", "home_team_away_score");
		columnRestMap.put("CHAR(20)84", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームアウェー失点数", "home_team_away_lost");
		columnRestMap.put("CHAR(20)85", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー得点数", "away_team_away_score");
		columnRestMap.put("CHAR(20)86", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームアウェー失点数", "away_team_away_lost");
		columnRestMap.put("CHAR(20)87", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("通知フラグ", "notice_flg");
		columnRestMap.put("CHAR(8)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア時間", "goal_time");
		columnRestMap.put("CHAR(8)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア選手", "goal_team_member");
		columnRestMap.put("CHAR(100)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("判定結果", "judge");
		columnRestMap.put("CHAR(50)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームスタイル", "home_team_style");
		columnRestMap.put("CHAR(50)a1", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームスタイル", "away_team_style");
		columnRestMap.put("CHAR(50)a2", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール確率", "probablity");
		columnRestMap.put("CHAR(50)a3", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点予想時間", "prediction_score_time");
		columnRestMap.put("CHAR(50)a4", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M019, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("まとまりデータ連番", "data_seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("メール通知対象数", "mail_target_count");
		columnRestMap.put("CHAR(30)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("メール非通知対象数", "mail_anonymous_target_count");
		columnRestMap.put("CHAR(30)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("メール通知成功数", "mail_target_success_count");
		columnRestMap.put("CHAR(30)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("メール通知失敗数", "mail_target_fail_count");
		columnRestMap.put("CHAR(30)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("前メール通知情報結果不明数", "ex_mail_target_to_no_result_count");
		columnRestMap.put("CHAR(30)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("前終了済データ無し結果不明数", "ex_no_fin_data_to_no_result_count");
		columnRestMap.put("CHAR(30)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール取り消し数", "goal_delete");
		columnRestMap.put("CHAR(30)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール取り消しによる通知非通知変更数", "alter_target_mail_anonymous");
		columnRestMap.put("CHAR(30)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴール取り消しによる成功失敗変更数", "alter_target_mail_fail");
		columnRestMap.put("CHAR(30)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("結果不明数", "no_result_count");
		columnRestMap.put("CHAR(30)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("予期せぬエラーデータ数", "err_data");
		columnRestMap.put("CHAR(30)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("分岐条件データ", "condition_data");
		columnRestMap.put("VARBINARY(3000)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ハッシュ値", "hash");
		columnRestMap.put("VARCHAR(256)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M002, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("通番", "seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(100)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(100)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム名", "team_name");
		columnRestMap.put("CHAR(100)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("homeaway", "HA");
		columnRestMap.put("VARCHAR(1)96", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("年", "year");
		columnRestMap.put("VARCHAR(4)95", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("1月合計スコア数", "jar_sum_score");
		columnRestMap.put("CHAR(8)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("2月合計スコア数", "feb_sum_score");
		columnRestMap.put("CHAR(8)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("3月合計スコア数", "mar_sum_score");
		columnRestMap.put("CHAR(8)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("4月合計スコア数", "apr_sum_score");
		columnRestMap.put("CHAR(8)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("5月合計スコア数", "may_sum_score");
		columnRestMap.put("CHAR(8)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("6月合計スコア数", "jun_sum_score");
		columnRestMap.put("CHAR(8)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("7月合計スコア数", "jul_sum_score");
		columnRestMap.put("CHAR(8)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("8月合計スコア数", "aug_sum_score");
		columnRestMap.put("CHAR(8)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("9月合計スコア数", "sep_sum_score");
		columnRestMap.put("CHAR(8)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("10月合計スコア数", "oct_sum_score");
		columnRestMap.put("CHAR(8)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("11月合計スコア数", "nov_sum_score");
		columnRestMap.put("CHAR(8)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("12月合計スコア数", "dec_sum_score");
		columnRestMap.put("CHAR(8)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M003, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("通番", "seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合国カテゴリ", "data_category");
		columnRestMap.put("CHAR(100)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム名", "team_name");
		columnRestMap.put("CHAR(100)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("相手チーム名", "away_team_name");
		columnRestMap.put("CHAR(100)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分シュート平均数", "team_0_10_mean_shoot_count");
		columnRestMap.put("CHAR(8)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分シュート平均数", "team_11_20_mean_shoot_count");
		columnRestMap.put("CHAR(8)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分シュート平均数", "team_21_30_mean_shoot_count");
		columnRestMap.put("CHAR(8)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分シュート平均数", "team_31_40_mean_shoot_count");
		columnRestMap.put("CHAR(8)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分シュート平均数", "team_41_45_mean_shoot_count");
		columnRestMap.put("CHAR(8)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分シュート平均数", "team_46_50_mean_shoot_count");
		columnRestMap.put("CHAR(8)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分シュート平均数", "team_51_60_mean_shoot_count");
		columnRestMap.put("CHAR(8)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分シュート平均数", "team_61_70_mean_shoot_count");
		columnRestMap.put("CHAR(8)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分シュート平均数", "team_71_80_mean_shoot_count");
		columnRestMap.put("CHAR(8)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分シュート平均数", "team_81_90_mean_shoot_count");
		columnRestMap.put("CHAR(8)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムシュート平均数", "team_addi_mean_shoot_count");
		columnRestMap.put("CHAR(8)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分枠内シュート平均数", "team_0_10_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分枠内シュート平均数", "team_11_20_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分枠内シュート平均数", "team_21_30_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー31-40分枠内シュート平均数", "team_31_40_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー41-45分枠内シュート平均数", "team_41_45_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー46-50分枠内シュート平均数", "team_46_50_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー51-60分枠内シュート平均数", "team_51_60_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー61-70分枠内シュート平均数", "team_61_70_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー71-80分枠内シュート平均数", "team_71_80_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チー81-90分枠内シュート平均数", "team_81_90_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーアディショナルタイム枠内シュート平均数", "team_addi_mean_shoot_in_count");
		columnRestMap.put("CHAR(8)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分ビッグチャンス平均数", "team_0_10_mean_big_chance_count");
		columnRestMap.put("CHAR(8)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分ビッグチャンス平均数", "team_11_20_mean_big_chance_count");
		columnRestMap.put("CHAR(8)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分ビッグチャンス平均数", "team_21_30_mean_big_chance_count");
		columnRestMap.put("CHAR(8)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分ビッグチャンス平均数", "team_31_40_mean_big_chance_count");
		columnRestMap.put("CHAR(8)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分ビッグチャンス平均数", "team_41_45_mean_big_chance_count");
		columnRestMap.put("CHAR(8)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分ビッグチャンス平均数", "team_46_50_mean_big_chance_count");
		columnRestMap.put("CHAR(8)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分ビッグチャンス平均数", "team_51_60_mean_big_chance_count");
		columnRestMap.put("CHAR(8)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分ビッグチャンス平均数", "team_61_70_mean_big_chance_count");
		columnRestMap.put("CHAR(8)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分ビッグチャンス平均数", "team_71_80_mean_big_chance_count");
		columnRestMap.put("CHAR(8)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分ビッグチャンス平均数", "team_81_90_mean_big_chance_count");
		columnRestMap.put("CHAR(8)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムビッグチャンス平均数", "team_addi_mean_big_chance_count");
		columnRestMap.put("CHAR(8)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分フリーキック平均数", "team_0_10_mean_free_kick_count");
		columnRestMap.put("CHAR(8)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分フリーキック平均数", "team_11_20_mean_free_kick_count");
		columnRestMap.put("CHAR(8)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分フリーキック平均数", "team_21_30_mean_free_kick_count");
		columnRestMap.put("CHAR(8)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分フリーキック平均数", "team_31_40_mean_free_kick_count");
		columnRestMap.put("CHAR(8)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分フリーキック平均数", "team_41_45_mean_free_kick_count");
		columnRestMap.put("CHAR(8)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分フリーキック平均数", "team_46_50_mean_free_kick_count");
		columnRestMap.put("CHAR(8)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分フリーキック平均数", "team_51_60_mean_free_kick_count");
		columnRestMap.put("CHAR(8)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分フリーキック平均数", "team_61_70_mean_free_kick_count");
		columnRestMap.put("CHAR(8)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分フリーキック平均数", "team_71_80_mean_free_kick_count");
		columnRestMap.put("CHAR(8)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分フリーキック平均数", "team_81_90_mean_free_kick_count");
		columnRestMap.put("CHAR(8)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムフリーキック平均数", "team_addi_mean_free_kick_count");
		columnRestMap.put("CHAR(8)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分オフサイド平均数", "team_0_10_mean_offside_count");
		columnRestMap.put("CHAR(8)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分オフサイド平均数", "team_11_20_mean_offside_count");
		columnRestMap.put("CHAR(8)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分オフサイド平均数", "team_21_30_mean_offside_count");
		columnRestMap.put("CHAR(8)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分オフサイド平均数", "team_31_40_mean_offside_count");
		columnRestMap.put("CHAR(8)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分オフサイド平均数", "team_41_45_mean_offside_count");
		columnRestMap.put("CHAR(8)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分オフサイド平均数", "team_46_50_mean_offside_count");
		columnRestMap.put("CHAR(8)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分オフサイド平均数", "team_51_60_mean_offside_count");
		columnRestMap.put("CHAR(8)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分オフサイド平均数", "team_61_70_mean_offside_count");
		columnRestMap.put("CHAR(8)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分オフサイド平均数", "team_71_80_mean_offside_count");
		columnRestMap.put("CHAR(8)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分オフサイド平均数", "team_81_90_mean_offside_count");
		columnRestMap.put("CHAR(8)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムオフサイド平均数", "team_addi_mean_offside_count");
		columnRestMap.put("CHAR(8)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分ファウル平均数", "team_0_10_mean_foul_count");
		columnRestMap.put("CHAR(8)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分ファウル平均数", "team_11_20_mean_foul_count");
		columnRestMap.put("CHAR(8)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分ファウル平均数", "team_21_30_mean_foul_count");
		columnRestMap.put("CHAR(8)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分ファウル平均数", "team_31_40_mean_foul_count");
		columnRestMap.put("CHAR(8)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分ファウル平均数", "team_41_45_mean_foul_count");
		columnRestMap.put("CHAR(8)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分ファウル平均数", "team_46_50_mean_foul_count");
		columnRestMap.put("CHAR(8)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分ファウル平均数", "team_51_60_mean_foul_count");
		columnRestMap.put("CHAR(8)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分ファウル平均数", "team_61_70_mean_foul_count");
		columnRestMap.put("CHAR(8)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分ファウル平均数", "team_71_80_mean_foul_count");
		columnRestMap.put("CHAR(8)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分ファウル平均数", "team_81_90_mean_foul_count");
		columnRestMap.put("CHAR(8)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムファウル平均数", "team_addi_mean_foul_count");
		columnRestMap.put("CHAR(8)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム0-10分イエローカード平均数", "team_0_10_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム11-20分イエローカード平均数", "team_11_20_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム21-30分イエローカード平均数", "team_21_30_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム31-40分イエローカード平均数", "team_31_40_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)73", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム41-45分イエローカード平均数", "team_41_45_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)74", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム46-50分イエローカード平均数", "team_46_50_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)75", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム51-60分イエローカード平均数", "team_51_60_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)76", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム61-70分イエローカード平均数", "team_61_70_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)77", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム71-80分イエローカード平均数", "team_71_80_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)78", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チーム81-90分イエローカード平均数", "team_81_90_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)79", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("自チームアディショナルタイムイエローカード平均数", "team_addi_mean_yellow_card_count");
		columnRestMap.put("CHAR(8)80", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M004, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(50)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("データ件数", "data_count");
		columnRestMap.put("VARCHAR(100)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("CSVデータ件数", "csv_count");
		columnRestMap.put("VARCHAR(100)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M006, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("カテゴリ", "category");
		columnRestMap.put("VARCHAR(50)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間範囲", "time_range");
		columnRestMap.put("VARCHAR(50)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("特徴量", "feature");
		columnRestMap.put("VARCHAR(100)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("閾値", "threshold");
		columnRestMap.put("VARCHAR(50)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("該当数", "target");
		columnRestMap.put("VARCHAR(50)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("探索数", "search");
		columnRestMap.put("VARCHAR(50)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("割合", "ratio");
		columnRestMap.put("VARCHAR(50)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M008, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M009, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M010, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M011, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M012, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間範囲", "time_range");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("特徴量", "feature");
		columnRestMap.put("VARCHAR(100)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("閾値", "threshold");
		columnRestMap.put("VARCHAR(50)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("該当数", "target");
		columnRestMap.put("VARCHAR(50)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("探索数", "search");
		columnRestMap.put("VARCHAR(50)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("割合", "ratio");
		columnRestMap.put("VARCHAR(50)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M013, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M014, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M015, columnSceneCardMap);
		columnKeyMap.put(UniairConst.BM_M016, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(50)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスコア", "sum_score_value");
		columnRestMap.put("VARCHAR(50)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("試合時間範囲エリア", "time_range_area");
		columnRestMap.put("VARCHAR(200)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("該当数", "target");
		columnRestMap.put("VARCHAR(50)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("探索数", "search");
		columnRestMap.put("VARCHAR(50)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("割合", "ratio");
		columnRestMap.put("VARCHAR(50)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M017, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(50)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスコア", "home_score_value");
		columnRestMap.put("VARCHAR(50)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースコア", "away_score_value");
		columnRestMap.put("VARCHAR(50)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム試合時間範囲エリア", "home_time_range_area");
		columnRestMap.put("VARCHAR(200)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー試合時間範囲エリア", "away_time_range_area");
		columnRestMap.put("VARCHAR(200)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("該当数", "target");
		columnRestMap.put("VARCHAR(50)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("探索数", "search");
		columnRestMap.put("VARCHAR(50)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("割合", "ratio");
		columnRestMap.put("VARCHAR(50)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M018, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(50)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("カテゴリ", "league");
		columnRestMap.put("VARCHAR(50)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("分類モード", "classify_mode");
		columnRestMap.put("VARCHAR(2)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("件数", "count");
		columnRestMap.put("VARCHAR(50)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("備考", "remarks");
		columnRestMap.put("VARCHAR(50)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M020, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		// 通番 (seq)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("連番", "seq");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// チーム名 (team_name)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム", "team_name");
		columnRestMap.put("VARCHAR(255)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦チーム名 (versus_team_name)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦チーム", "versus_team_name");
		columnRestMap.put("VARCHAR(255)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦場所 (ha)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦場所", "ha");
		columnRestMap.put("VARCHAR(255)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// スコア (score)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア", "score");
		columnRestMap.put("VARCHAR(255)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 結果 (result)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("結果", "result");
		columnRestMap.put("VARCHAR(255)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 途中順位 (game_fin_rank)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("途中順位", "game_fin_rank");
		columnRestMap.put("VARCHAR(255)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手途中順位 (opposite_game_fin_rank)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手途中順位", "opposite_game_fin_rank");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 期待値 (exp)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("期待値", "exp");
		columnRestMap.put("VARCHAR(255)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手期待値 (opposite_exp)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手期待値", "opposite_exp");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ポゼッション (donation)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ポゼッション", "donation");
		columnRestMap.put("VARCHAR(255)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ポゼッション (opposite_donation)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ポゼッション", "opposite_donation");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// シュート数 (shoot_all)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("シュート数", "shoot_all");
		columnRestMap.put("VARCHAR(255)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手シュート数 (opposite_shoot_all)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手シュート数", "opposite_shoot_all");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 枠内シュート (shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("枠内シュート", "shoot_in");
		columnRestMap.put("VARCHAR(255)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手枠内シュート (opposite_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手枠内シュート", "opposite_shoot_in");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 枠外シュート (shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("枠外シュート", "shoot_out");
		columnRestMap.put("VARCHAR(255)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手枠外シュート (opposite_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手枠外シュート", "opposite_shoot_out");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ブロックシュート (block_shoot)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ブロックシュート", "block_shoot");
		columnRestMap.put("VARCHAR(255)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ブロックシュート (opposite_block_shoot)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ブロックシュート", "opposite_block_shoot");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ビックチャンス (big_chance)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ビックチャンス", "big_chance");
		columnRestMap.put("VARCHAR(255)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ビックチャンス (opposite_big_chance)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ビックチャンス", "opposite_big_chance");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// コーナーキック (corner)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("コーナーキック", "corner");
		columnRestMap.put("VARCHAR(255)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手コーナーキック (opposite_corner)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手コーナーキック", "opposite_corner");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ボックス内シュート (box_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ボックス内シュート", "box_shoot_in");
		columnRestMap.put("VARCHAR(255)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ボックス内シュート (opposite_box_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ボックス内シュート", "opposite_box_shoot_in");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ボックス外シュート (box_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ボックス外シュート", "box_shoot_out");
		columnRestMap.put("VARCHAR(255)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ボックス外シュート (opposite_box_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ボックス外シュート", "opposite_box_shoot_out");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ゴールポスト (goal_post)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ゴールポスト", "goal_post");
		columnRestMap.put("VARCHAR(255)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ゴールポスト (opposite_goal_post)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ゴールポスト", "opposite_goal_post");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ヘディングゴール (goal_head)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ヘディングゴール", "goal_head");
		columnRestMap.put("VARCHAR(255)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ヘディングゴール (opposite_goal_head)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ヘディングゴール", "opposite_goal_head");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// キーパーセーブ (keeper_save)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("キーパーセーブ", "keeper_save");
		columnRestMap.put("VARCHAR(255)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手キーパーセーブ (opposite_keeper_save)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手キーパーセーブ", "opposite_keeper_save");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// フリーキック (free_kick)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("フリーキック", "free_kick");
		columnRestMap.put("VARCHAR(255)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手フリーキック (opposite_free_kick)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手フリーキック", "opposite_free_kick");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// オフサイド (offside)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("オフサイド", "offside");
		columnRestMap.put("VARCHAR(255)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手オフサイド (opposite_offside)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手オフサイド", "opposite_offside");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ファウル (foul)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファウル", "foul");
		columnRestMap.put("VARCHAR(255)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ファウル (opposite_foul)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ファウル", "opposite_foul");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// イエローカード (yellow_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("イエローカード", "yellow_card");
		columnRestMap.put("VARCHAR(255)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手イエローカード (opposite_yellow_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手イエローカード", "opposite_yellow_card");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// レッドカード (red_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("レッドカード", "red_card");
		columnRestMap.put("VARCHAR(255)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手レッドカード (opposite_red_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手レッドカード", "opposite_red_card");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// スローイン (slow_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スローイン", "slow_in");
		columnRestMap.put("VARCHAR(255)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手スローイン (opposite_slow_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手スローイン", "opposite_slow_in");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ボックスタッチ (box_touch)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ボックスタッチ", "box_touch");
		columnRestMap.put("VARCHAR(255)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ボックスタッチ (opposite_box_touch)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ボックスタッチ", "opposite_box_touch");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// パス数_成功率 (pass_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("パス数_成功率", "pass_count_success_ratio");
		columnRestMap.put("VARCHAR(255)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// パス数_成功数 (pass_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("パス数_成功数", "pass_count_success_count");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// パス数_試行数 (pass_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("パス数_試行数", "pass_count_try_count");
		columnRestMap.put("VARCHAR(255)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手パス数_成功率 (opposite_pass_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手パス数_成功率", "opposite_pass_count_success_ratio");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手パス数_成功数 (opposite_pass_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手パス数_成功数", "opposite_pass_count_success_count");
		columnRestMap.put("VARCHAR(255)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手パス数_試行数 (opposite_pass_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手パス数_試行数", "opposite_pass_count_try_count");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ファイナルサードパス数_成功率 (final_third_pass_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイナルサードパス数_成功率", "final_third_pass_count_success_ratio");
		columnRestMap.put("VARCHAR(255)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ファイナルサードパス数_成功数 (final_third_pass_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイナルサードパス数_成功数", "final_third_pass_count_success_count");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ファイナルサードパス数_試行数 (final_third_pass_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイナルサードパス数_試行数", "final_third_pass_count_try_count");
		columnRestMap.put("VARCHAR(255)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ファイナルサードパス数_成功率 (opposite_final_third_pass_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ファイナルサードパス数_成功率", "opposite_final_third_pass_count_success_ratio");
		columnRestMap.put("VARCHAR(255)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ファイナルサードパス数_成功数 (opposite_final_third_pass_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ファイナルサードパス数_成功数", "opposite_final_third_pass_count_success_count");
		columnRestMap.put("VARCHAR(255)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手ファイナルサードパス数_試行数 (opposite_final_third_pass_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手ファイナルサードパス数_試行数", "opposite_final_third_pass_count_try_count");
		columnRestMap.put("VARCHAR(255)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// クロス数_成功率 (cross_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("クロス数_成功率", "cross_count_success_ratio");
		columnRestMap.put("VARCHAR(255)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// クロス数_成功数 (cross_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("クロス数_成功数", "cross_count_success_count");
		columnRestMap.put("VARCHAR(255)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// クロス数_試行数 (cross_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("クロス数_試行数", "cross_count_try_count");
		columnRestMap.put("VARCHAR(255)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手クロス数_成功率 (opposite_tackle_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手クロス数_成功率", "opposite_cross_count_success_ratio");
		columnRestMap.put("VARCHAR(255)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手クロス数_成功数 (opposite_tackle_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手クロス数_成功数", "opposite_cross_count_success_count");
		columnRestMap.put("VARCHAR(255)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手クロス数_試行数 (opposite_tackle_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手クロス数_試行数", "opposite_cross_count_try_count");
		columnRestMap.put("VARCHAR(255)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// タックル数_成功率 (tackle_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("タックル数_成功率", "tackle_count_success_ratio");
		columnRestMap.put("VARCHAR(255)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// タックル数_成功数 (tackle_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("タックル数_成功数", "tackle_count_success_count");
		columnRestMap.put("VARCHAR(255)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// タックル数_試行数 (tackle_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("タックル数_試行数", "tackle_count_try_count");
		columnRestMap.put("VARCHAR(255)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手タックル数_成功率 (opposite_tackle_count_success_ratio)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手タックル数_成功率", "opposite_tackle_count_success_ratio");
		columnRestMap.put("VARCHAR(255)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手タックル数_成功数 (opposite_tackle_count_success_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手タックル数_成功数", "opposite_tackle_count_success_count");
		columnRestMap.put("VARCHAR(255)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手タックル数_試行数 (opposite_tackle_count_try_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手タックル数_試行数", "opposite_tackle_count_try_count");
		columnRestMap.put("VARCHAR(255)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// クリア数 (clear_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("クリア数", "clear_count");
		columnRestMap.put("VARCHAR(255)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手クリア数 (opposite_clear_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手クリア数", "opposite_clear_count");
		columnRestMap.put("VARCHAR(255)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// インターセプト数 (intercept_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("インターセプト数", "intercept_count");
		columnRestMap.put("VARCHAR(255)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 対戦相手インターセプト数 (opposite_intercept_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("対戦相手インターセプト数", "opposite_intercept_count");
		columnRestMap.put("VARCHAR(255)73", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 天気 (weather)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("天気", "weather");
		columnRestMap.put("VARCHAR(255)74", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 気温 (temperature)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("気温", "temperature");
		columnRestMap.put("VARCHAR(255)75", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// 湿度 (humid)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("湿度", "humid");
		columnRestMap.put("VARCHAR(255)76", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M021, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		// 通番 (seq)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国カテゴリ", "data_category");
		columnRestMap.put("VARCHAR(255)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム", "home_team_name");
		columnRestMap.put("VARCHAR(255)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム", "away_team_name");
		columnRestMap.put("VARCHAR(255)96", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームの差分 (diff_home_score)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームスコアの差分", "diff_home_score");
		columnRestMap.put("VARCHAR(255)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームの差分 (diff_away_score)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームスコアの差分", "diff_away_score");
		columnRestMap.put("VARCHAR(255)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームポゼッションの差分 (diff_home_donation)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームポゼッションの差分", "diff_home_donation");
		columnRestMap.put("VARCHAR(255)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームポゼッションの差分 (diff_away_donation)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームポゼッションの差分", "diff_away_donation");
		columnRestMap.put("VARCHAR(255)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームシュート数の差分 (diff_home_shoot_all)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームシュート数の差分", "diff_home_shoot_all");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームシュート数の差分 (diff_away_shoot_all)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームシュート数の差分", "diff_away_shoot_all");
		columnRestMap.put("VARCHAR(255)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチーム枠内シュート数の差分 (diff_home_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム枠内シュート数の差分", "diff_home_shoot_in");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチーム枠内シュート数の差分 (diff_away_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム枠内シュート数の差分", "diff_away_shoot_in");
		columnRestMap.put("VARCHAR(255)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチーム枠外シュート数の差分 (diff_home_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチーム枠外シュート数の差分", "diff_home_shoot_out");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチーム枠外シュート数の差分 (diff_away_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチーム枠外シュート数の差分", "diff_away_shoot_out");
		columnRestMap.put("VARCHAR(255)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームブロックシュート数の差分 (diff_home_block_shoot)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームブロックシュート数の差分", "diff_home_block_shoot");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームブロックシュート数の差分 (diff_away_block_shoot)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームブロックシュート数の差分", "diff_away_block_shoot");
		columnRestMap.put("VARCHAR(255)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームビッグチャンス数の差分 (diff_home_big_chance)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームビッグチャンス数の差分", "diff_home_big_chance");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームビッグチャンス数の差分 (diff_away_big_chance)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームビッグチャンス数の差分", "diff_away_big_chance");
		columnRestMap.put("VARCHAR(255)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームコーナーキック数の差分 (diff_home_corner)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームコーナーキック数の差分", "diff_home_corner");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームコーナーキック数の差分 (diff_away_corner)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームコーナーキック数の差分", "diff_away_corner");
		columnRestMap.put("VARCHAR(255)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームボックス内シュート数の差分 (diff_home_box_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームボックス内シュート数の差分", "diff_home_box_shoot_in");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームボックス内シュート数の差分 (diff_away_box_shoot_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームボックス内シュート数の差分", "diff_away_box_shoot_in");
		columnRestMap.put("VARCHAR(255)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームボックス外シュート数の差分 (diff_home_box_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームボックス外シュート数の差分", "diff_home_box_shoot_out");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームボックス外シュート数の差分 (diff_away_box_shoot_out)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームボックス外シュート数の差分", "diff_away_box_shoot_out");
		columnRestMap.put("VARCHAR(255)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームゴールポストに当たったシュート数の差分 (diff_home_goal_post)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームゴールポストに当たったシュート数の差分", "diff_home_goal_post");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームゴールポストに当たったシュート数の差分 (diff_away_goal_post)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームゴールポストに当たったシュート数の差分", "diff_away_goal_post");
		columnRestMap.put("VARCHAR(255)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームの差分 (diff_home_goal_head)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームヘディングゴール数の差分", "diff_home_goal_head");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームの差分 (diff_away_goal_head)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームヘディングゴール数の差分", "diff_away_goal_head");
		columnRestMap.put("VARCHAR(255)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームのキーパーセーブ数の差分 (diff_home_keeper_save)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームキーパーセーブ数の差分", "diff_home_keeper_save");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームのキーパーセーブ数の差分 (diff_away_keeper_save)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームキーパーセーブ数の差分", "diff_away_keeper_save");
		columnRestMap.put("VARCHAR(255)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームフリーキック数の差分 (diff_home_free_kick)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームフリーキック数の差分", "diff_home_free_kick");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームフリーキック数の差分 (diff_away_free_kick)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームフリーキック数の差分", "diff_away_free_kick");
		columnRestMap.put("VARCHAR(255)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームオフサイド数の差分 (diff_home_offside)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームオフサイド数の差分", "diff_home_offside");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームオフサイド数の差分 (diff_away_offside)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームオフサイド数の差分", "diff_away_offside");
		columnRestMap.put("VARCHAR(255)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームファウル数の差分 (diff_home_foul)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームファウル数の差分", "diff_home_foul");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームファウル数の差分 (diff_away_foul)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームファウル数の差分", "diff_away_foul");
		columnRestMap.put("VARCHAR(255)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームイエローカード数の差分 (diff_home_yellow_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームイエローカード数の差分", "diff_home_yellow_card");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームイエローカード数の差分 (diff_away_yellow_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームイエローカード数の差分", "diff_away_yellow_card");
		columnRestMap.put("VARCHAR(255)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームレッドカード数の差分 (diff_home_red_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームレッドカード数の差分", "diff_home_red_card");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームレッドカード数の差分 (diff_away_red_card)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームレッドカード数の差分", "diff_away_red_card");
		columnRestMap.put("VARCHAR(255)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームスローイン数の差分 (diff_home_slow_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームスローイン数の差分", "diff_home_slow_in");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームスローイン数の差分 (diff_away_slow_in)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームスローイン数の差分", "diff_away_slow_in");
		columnRestMap.put("VARCHAR(255)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームボックスタッチ数の差分 (diff_home_box_touch)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームボックスタッチ数の差分", "diff_home_box_touch");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームボックスタッチ数の差分 (diff_away_box_touch)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームボックスタッチ数の差分", "diff_away_box_touch");
		columnRestMap.put("VARCHAR(255)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームパス数の差分 (diff_home_pass_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームパス数の差分", "diff_home_pass_count");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームパス数の差分 (diff_away_pass_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームパス数の差分", "diff_away_pass_count");
		columnRestMap.put("VARCHAR(255)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームファイナルサードパス数の差分 (diff_home_final_third_pass_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームファイナルサードパス数の差分", "diff_home_final_third_pass_count");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームファイナルサードパス数の差分 (diff_away_final_third_pass_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームファイナルサードパス数の差分", "diff_away_final_third_pass_count");
		columnRestMap.put("VARCHAR(255)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームクロス数の差分 (diff_home_cross_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームクロス数の差分", "diff_home_cross_count");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームクロス数の差分 (diff_away_cross_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームクロス数の差分", "diff_away_cross_count");
		columnRestMap.put("VARCHAR(255)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームタックル数の差分 (diff_home_tackle_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームタックル数の差分", "diff_home_tackle_count");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームタックル数の差分 (diff_away_tackle_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームタックル数の差分", "diff_away_tackle_count");
		columnRestMap.put("VARCHAR(255)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームクリア数の差分 (diff_home_clear_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームクリア数の差分", "diff_home_clear_count");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームクリア数の差分 (diff_away_clear_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームクリア数の差分", "diff_away_clear_count");
		columnRestMap.put("VARCHAR(255)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// ホームチームインターセプト数の差分 (diff_home_intercept_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームチームインターセプト数の差分", "diff_home_intercept_count");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームインターセプト数の差分 (diff_away_intercept_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーチームインターセプト数の差分", "diff_away_intercept_count");
		columnRestMap.put("VARCHAR(255)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームプレースタイル", "home_play_style");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// アウェーチームインターセプト数の差分 (diff_away_intercept_count)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェープレースタイル", "away_play_style");
		columnRestMap.put("VARCHAR(255)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M022, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("状況", "situation");
		columnRestMap.put("VARCHAR(10)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点状況", "score");
		columnRestMap.put("VARCHAR(15)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値の統計情報", "home_exp_stat");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値の統計情報", "away_exp_stat");
		columnRestMap.put("VARCHAR(255)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームポゼッションの統計情報", "home_donation_stat");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーポゼッションの統計情報", "away_donation_stat");
		columnRestMap.put("VARCHAR(255)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_all_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート数の統計情報", "home_shoot_all_stat");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_all_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート数の統計情報", "away_shoot_all_stat");
		columnRestMap.put("VARCHAR(255)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュートの統計情報", "home_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュートの統計情報", "away_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュートの統計情報", "home_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュートの統計情報", "away_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_block_shoot_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュートの統計情報", "home_block_shoot_stat");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_block_shoot_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュートの統計情報", "away_block_shoot_stat");
		columnRestMap.put("VARCHAR(255)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_big_chance_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビッグチャンスの統計情報", "home_big_chance_stat");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_big_chance_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービッグチャンスの統計情報", "away_big_chance_stat");
		columnRestMap.put("VARCHAR(255)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_corner_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキックの統計情報", "home_corner_stat");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_corner_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキックの統計情報", "away_corner_stat");
		columnRestMap.put("VARCHAR(255)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス内シュートの統計情報", "home_box_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス内シュートの統計情報", "away_box_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス外シュートの統計情報", "home_box_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス外シュートの統計情報", "away_box_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_post_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポストの統計情報", "home_goal_post_stat");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_post_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポストの統計情報", "away_goal_post_stat");
		columnRestMap.put("VARCHAR(255)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_head_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴールの統計情報", "home_goal_head_stat");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_head_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴールの統計情報", "away_goal_head_stat");
		columnRestMap.put("VARCHAR(255)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_keeper_save_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブの統計情報", "home_keeper_save_stat");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_keeper_save_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブの統計情報", "away_keeper_save_stat");
		columnRestMap.put("VARCHAR(255)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_free_kick_stat (31)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキックの統計情報", "home_free_kick_stat");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_free_kick_stat (32)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキックの統計情報", "away_free_kick_stat");
		columnRestMap.put("VARCHAR(255)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_offside_stat (33)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイドの統計情報", "home_offside_stat");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_offside_stat (34)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイドの統計情報", "away_offside_stat");
		columnRestMap.put("VARCHAR(255)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_foul_stat (35)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファウルの統計情報", "home_foul_stat");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_foul_stat (36)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファウルの統計情報", "away_foul_stat");
		columnRestMap.put("VARCHAR(255)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_yellow_card_stat (37)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカードの統計情報", "home_yellow_card_stat");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_yellow_card_stat (38)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカードの統計情報", "away_yellow_card_stat");
		columnRestMap.put("VARCHAR(255)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_red_card_stat (39)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカードの統計情報", "home_red_card_stat");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_red_card_stat (40)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカードの統計情報", "away_red_card_stat");
		columnRestMap.put("VARCHAR(255)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_slow_in_stat (41)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローインの統計情報", "home_slow_in_stat");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_slow_in_stat (42)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローインの統計情報", "away_slow_in_stat");
		columnRestMap.put("VARCHAR(255)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_touch_stat (43)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチの統計情報", "home_box_touch_stat");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_touch_stat (44)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチの統計情報", "away_box_touch_stat");
		columnRestMap.put("VARCHAR(255)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_pass_count_stat (45)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の統計情報", "home_pass_count_stat");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_stat (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数の統計情報", "away_pass_count_stat");
		columnRestMap.put("VARCHAR(255)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_final_third_pass_count_stat (47)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の統計情報", "home_final_third_pass_count_stat");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_stat (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数の統計情報", "away_final_third_pass_count_stat");
		columnRestMap.put("VARCHAR(255)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_cross_count_stat (49)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の統計情報", "home_cross_count_stat");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_stat (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数の統計情報", "away_cross_count_stat");
		columnRestMap.put("VARCHAR(255)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_tackle_count_stat (51)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の統計情報", "home_tackle_count_stat");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_tackle_count_stat (52)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数の統計情報", "away_tackle_count_stat");
		columnRestMap.put("VARCHAR(255)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_clear_count_stat (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数の統計情報", "home_clear_count_stat");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_clear_count_stat (54)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数の統計情報", "away_clear_count_stat");
		columnRestMap.put("VARCHAR(255)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_intercept_count_stat (55)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数の統計情報", "home_intercept_count_stat");
		columnRestMap.put("VARCHAR(255)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_intercept_count_stat (56)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数の統計情報", "away_intercept_count_stat");
		columnRestMap.put("VARCHAR(255)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M023, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイル", "file");
		columnRestMap.put("VARCHAR(40)94", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム", "home");
		columnRestMap.put("VARCHAR(40)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー", "away");
		columnRestMap.put("VARCHAR(40)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("スコア", "score");
		columnRestMap.put("VARCHAR(40)96", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("導出内容", "chk_body");
		columnRestMap.put("VARCHAR(40)93", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値の相関係数情報", "home_exp_info");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_exp_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値の相関係数情報", "away_exp_info");
		columnRestMap.put("VARCHAR(255)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_donation_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームポゼッションの相関係数情報", "home_donation_info");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_donation_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーポゼッションの相関係数情報", "away_donation_info");
		columnRestMap.put("VARCHAR(255)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_all_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート数の相関係数情報", "home_shoot_all_info");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_all_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート数の相関係数情報", "away_shoot_all_info");
		columnRestMap.put("VARCHAR(255)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_in_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュートの相関係数情報", "home_shoot_in_info");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_in_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュートの相関係数情報", "away_shoot_in_info");
		columnRestMap.put("VARCHAR(255)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_out_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュートの相関係数情報", "home_shoot_out_info");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_out_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュートの相関係数情報", "away_shoot_out_info");
		columnRestMap.put("VARCHAR(255)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_block_shoot_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュートの相関係数情報", "home_block_shoot_info");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_block_shoot_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュートの相関係数情報", "away_block_shoot_info");
		columnRestMap.put("VARCHAR(255)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_big_chance_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビッグチャンスの相関係数情報", "home_big_chance_info");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_big_chance_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービッグチャンスの相関係数情報", "away_big_chance_info");
		columnRestMap.put("VARCHAR(255)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_corner_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキックの相関係数情報", "home_corner_info");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_corner_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキックの相関係数情報", "away_corner_info");
		columnRestMap.put("VARCHAR(255)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_in_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス内シュートの相関係数情報", "home_box_shoot_in_info");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_in_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス内シュートの相関係数情報", "away_box_shoot_in_info");
		columnRestMap.put("VARCHAR(255)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_out_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス外シュートの相関係数情報", "home_box_shoot_out_info");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_out_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス外シュートの相関係数情報", "away_box_shoot_out_info");
		columnRestMap.put("VARCHAR(255)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_post_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポストの相関係数情報", "home_goal_post_info");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_post_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポストの相関係数情報", "away_goal_post_info");
		columnRestMap.put("VARCHAR(255)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_head_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴールの相関係数情報", "home_goal_head_info");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_head_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴールの相関係数情報", "away_goal_head_info");
		columnRestMap.put("VARCHAR(255)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_keeper_save_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブの相関係数情報", "home_keeper_save_info");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_keeper_save_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブの相関係数情報", "away_keeper_save_info");
		columnRestMap.put("VARCHAR(255)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_free_kick_info (31)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキックの相関係数情報", "home_free_kick_info");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_free_kick_info (32)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキックの相関係数情報", "away_free_kick_info");
		columnRestMap.put("VARCHAR(255)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_offside_info (33)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイドの相関係数情報", "home_offside_info");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_offside_info (34)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイドの相関係数情報", "away_offside_info");
		columnRestMap.put("VARCHAR(255)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_foul_info (35)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファウルの相関係数情報", "home_foul_info");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_foul_info (36)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファウルの相関係数情報", "away_foul_info");
		columnRestMap.put("VARCHAR(255)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_yellow_card_info (37)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカードの相関係数情報", "home_yellow_card_info");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_yellow_card_info (38)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカードの相関係数情報", "away_yellow_card_info");
		columnRestMap.put("VARCHAR(255)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_red_card_info (39)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカードの相関係数情報", "home_red_card_info");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_red_card_info (40)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカードの相関係数情報", "away_red_card_info");
		columnRestMap.put("VARCHAR(255)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_slow_in_info (41)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローインの相関係数情報", "home_slow_in_info");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_slow_in_info (42)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローインの相関係数情報", "away_slow_in_info");
		columnRestMap.put("VARCHAR(255)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_touch_info (43)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチの相関係数情報", "home_box_touch_info");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_touch_info (44)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチの相関係数情報", "away_box_touch_info");
		columnRestMap.put("VARCHAR(255)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_pass_count_info (45)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の相関係数情報1", "home_pass_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_info (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の相関係数情報2", "home_pass_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_info (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の相関係数情報3", "home_pass_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_info (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数の相関係数情報1", "away_pass_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_final_third_pass_count_info (47)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数の相関係数情報2", "away_pass_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_final_third_pass_count_info (47)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数の相関係数情報3", "away_pass_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_final_third_pass_count_info (47)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の相関係数情報1", "home_final_third_pass_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_info (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の相関係数情報2", "home_final_third_pass_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_info (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の相関係数情報3", "home_final_third_pass_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_info (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数の相関係数情報1", "away_final_third_pass_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_cross_count_info (49)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数の相関係数情報2", "away_final_third_pass_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_cross_count_info (49)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数の相関係数情報3", "away_final_third_pass_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_cross_count_info (49)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の相関係数情報1", "home_cross_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)57", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_info (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の相関係数情報2", "home_cross_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)58", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_info (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の相関係数情報3", "home_cross_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)59", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_info (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数の相関係数情報1", "away_cross_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)60", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_tackle_count_info (51)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数の相関係数情報2", "away_cross_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)61", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_tackle_count_info (51)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数の相関係数情報3", "away_cross_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)62", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_tackle_count_info (51)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の相関係数情報1", "home_tackle_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)63", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_tackle_count_info (52)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の相関係数情報2", "home_tackle_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)64", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_tackle_count_info (52)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の相関係数情報3", "home_tackle_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)65", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_tackle_count_info (52)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数の相関係数情報1", "away_tackle_count_info_on_success_ratio");
		columnRestMap.put("VARCHAR(255)66", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_clear_count_info (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数の相関係数情報2", "away_tackle_count_info_on_success_count");
		columnRestMap.put("VARCHAR(255)67", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_clear_count_info (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数の相関係数情報3", "away_tackle_count_info_on_try_count");
		columnRestMap.put("VARCHAR(255)68", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_clear_count_info (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数の相関係数情報", "home_clear_count_info");
		columnRestMap.put("VARCHAR(255)69", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_clear_count_info (54)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数の相関係数情報", "away_clear_count_info");
		columnRestMap.put("VARCHAR(255)70", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_intercept_count_info (55)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数の相関係数情報", "home_intercept_count_info");
		columnRestMap.put("VARCHAR(255)71", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_intercept_count_info (56)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数の相関係数情報", "away_intercept_count_info");
		columnRestMap.put("VARCHAR(255)72", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M024, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_info
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム", "home");
		columnRestMap.put("VARCHAR(40)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー", "away");
		columnRestMap.put("VARCHAR(40)98", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点状況", "score");
		columnRestMap.put("VARCHAR(15)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("導出内容", "chk_body");
		columnRestMap.put("VARCHAR(40)93", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("raning1位", "1st_rank");
		columnRestMap.put("VARCHAR(15)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("raning2位", "2nd_rank");
		columnRestMap.put("VARCHAR(15)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("raning3位", "3rd_rank");
		columnRestMap.put("VARCHAR(15)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("raning4位", "4th_rank");
		columnRestMap.put("VARCHAR(15)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("raning5位", "5th_rank");
		columnRestMap.put("VARCHAR(15)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		for (int i = 6; i <= 68; i++) {
		    columnMainMap.put("raning" + i + "位", i + "th_rank");
		    columnRestMap.put("VARCHAR(15)" + String.format("%02d", i + 4), "");
		    columnSceneCardMap.put(columnMainMap, columnRestMap);
		    columnRestMap = new HashMap<>();
		    columnMainMap = new HashMap<>();
		}
		columnKeyMap.put(UniairConst.BM_M025, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("状況", "situation");
		columnRestMap.put("VARCHAR(10)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点状況", "score");
		columnRestMap.put("VARCHAR(15)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム", "team");
		columnRestMap.put("VARCHAR(10)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値の統計情報", "home_exp_stat");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー期待値の統計情報", "away_exp_stat");
		columnRestMap.put("VARCHAR(255)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームポゼッションの統計情報", "home_donation_stat");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーポゼッションの統計情報", "away_donation_stat");
		columnRestMap.put("VARCHAR(255)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_all_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート数の統計情報", "home_shoot_all_stat");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_all_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーシュート数の統計情報", "away_shoot_all_stat");
		columnRestMap.put("VARCHAR(255)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュートの統計情報", "home_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠内シュートの統計情報", "away_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュートの統計情報", "home_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェー枠外シュートの統計情報", "away_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_block_shoot_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュートの統計情報", "home_block_shoot_stat");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_block_shoot_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーブロックシュートの統計情報", "away_block_shoot_stat");
		columnRestMap.put("VARCHAR(255)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_big_chance_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビッグチャンスの統計情報", "home_big_chance_stat");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_big_chance_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェービッグチャンスの統計情報", "away_big_chance_stat");
		columnRestMap.put("VARCHAR(255)18", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_corner_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキックの統計情報", "home_corner_stat");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_corner_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーコーナーキックの統計情報", "away_corner_stat");
		columnRestMap.put("VARCHAR(255)20", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス内シュートの統計情報", "home_box_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス内シュートの統計情報", "away_box_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)22", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス外シュートの統計情報", "home_box_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックス外シュートの統計情報", "away_box_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)24", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_post_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポストの統計情報", "home_goal_post_stat");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_post_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーゴールポストの統計情報", "away_goal_post_stat");
		columnRestMap.put("VARCHAR(255)26", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_goal_head_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴールの統計情報", "home_goal_head_stat");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_head_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーヘディングゴールの統計情報", "away_goal_head_stat");
		columnRestMap.put("VARCHAR(255)28", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_keeper_save_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブの統計情報", "home_keeper_save_stat");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_keeper_save_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーキーパーセーブの統計情報", "away_keeper_save_stat");
		columnRestMap.put("VARCHAR(255)30", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_free_kick_stat (31)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキックの統計情報", "home_free_kick_stat");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_free_kick_stat (32)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーフリーキックの統計情報", "away_free_kick_stat");
		columnRestMap.put("VARCHAR(255)32", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_offside_stat (33)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイドの統計情報", "home_offside_stat");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_offside_stat (34)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーオフサイドの統計情報", "away_offside_stat");
		columnRestMap.put("VARCHAR(255)34", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_foul_stat (35)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファウルの統計情報", "home_foul_stat");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_foul_stat (36)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファウルの統計情報", "away_foul_stat");
		columnRestMap.put("VARCHAR(255)36", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_yellow_card_stat (37)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカードの統計情報", "home_yellow_card_stat");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_yellow_card_stat (38)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーイエローカードの統計情報", "away_yellow_card_stat");
		columnRestMap.put("VARCHAR(255)38", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_red_card_stat (39)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカードの統計情報", "home_red_card_stat");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_red_card_stat (40)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーレッドカードの統計情報", "away_red_card_stat");
		columnRestMap.put("VARCHAR(255)40", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_slow_in_stat (41)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローインの統計情報", "home_slow_in_stat");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_slow_in_stat (42)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェースローインの統計情報", "away_slow_in_stat");
		columnRestMap.put("VARCHAR(255)42", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_box_touch_stat (43)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチの統計情報", "home_box_touch_stat");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_touch_stat (44)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーボックスタッチの統計情報", "away_box_touch_stat");
		columnRestMap.put("VARCHAR(255)44", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_pass_count_stat (45)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の統計情報", "home_pass_count_stat");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_stat (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーパス数の統計情報", "away_pass_count_stat");
		columnRestMap.put("VARCHAR(255)46", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_final_third_pass_count_stat (47)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の統計情報", "home_final_third_pass_count_stat");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_stat (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーファイナルサードパス数の統計情報", "away_final_third_pass_count_stat");
		columnRestMap.put("VARCHAR(255)48", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_cross_count_stat (49)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の統計情報", "home_cross_count_stat");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_stat (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークロス数の統計情報", "away_cross_count_stat");
		columnRestMap.put("VARCHAR(255)50", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_tackle_count_stat (51)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の統計情報", "home_tackle_count_stat");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_tackle_count_stat (52)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェータックル数の統計情報", "away_tackle_count_stat");
		columnRestMap.put("VARCHAR(255)52", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_clear_count_stat (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数の統計情報", "home_clear_count_stat");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_clear_count_stat (54)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェークリア数の統計情報", "away_clear_count_stat");
		columnRestMap.put("VARCHAR(255)54", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_intercept_count_stat (55)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数の統計情報", "home_intercept_count_stat");
		columnRestMap.put("VARCHAR(255)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_intercept_count_stat (56)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("アウェーインターセプト数の統計情報", "away_intercept_count_stat");
		columnRestMap.put("VARCHAR(255)56", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M026, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("得点状況", "score");
		columnRestMap.put("VARCHAR(15)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// home_exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム", "team");
		columnRestMap.put("VARCHAR(10)97", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("件数", "game_count");
		columnRestMap.put("VARCHAR(10)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M027, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)99", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("状況", "situation");
		columnRestMap.put("VARCHAR(10)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム", "team");
		columnRestMap.put("VARCHAR(10)90", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("相手チーム", "opposite_team");
		columnRestMap.put("VARCHAR(10)91", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("HorA", "ha");
		columnRestMap.put("VARCHAR(10)92", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)93", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)94", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// exp_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム期待値の統計情報", "exp_stat");
		columnRestMap.put("VARCHAR(255)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームポゼッションの統計情報", "donation_stat");
		columnRestMap.put("VARCHAR(255)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_donation_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームシュート数の統計情報", "shoot_all_stat");
		columnRestMap.put("VARCHAR(255)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_all_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠内シュートの統計情報", "shoot_in_stat");
		columnRestMap.put("VARCHAR(255)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホーム枠外シュートの統計情報", "shoot_out_stat");
		columnRestMap.put("VARCHAR(255)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームブロックシュートの統計情報", "block_shoot_stat");
		columnRestMap.put("VARCHAR(255)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_block_shoot_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームビッグチャンスの統計情報", "big_chance_stat");
		columnRestMap.put("VARCHAR(255)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_big_chance_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームコーナーキックの統計情報", "corner_stat");
		columnRestMap.put("VARCHAR(255)19", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_corner_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス内シュートの統計情報", "box_shoot_in_stat");
		columnRestMap.put("VARCHAR(255)21", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_in_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックス外シュートの統計情報", "box_shoot_out_stat");
		columnRestMap.put("VARCHAR(255)23", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_shoot_out_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームゴールポストの統計情報", "goal_post_stat");
		columnRestMap.put("VARCHAR(255)25", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_post_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームヘディングゴールの統計情報", "goal_head_stat");
		columnRestMap.put("VARCHAR(255)27", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_goal_head_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームキーパーセーブの統計情報", "keeper_save_stat");
		columnRestMap.put("VARCHAR(255)29", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_keeper_save_stat
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームフリーキックの統計情報", "free_kick_stat");
		columnRestMap.put("VARCHAR(255)31", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_free_kick_stat (32)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームオフサイドの統計情報", "offside_stat");
		columnRestMap.put("VARCHAR(255)33", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_offside_stat (34)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファウルの統計情報", "foul_stat");
		columnRestMap.put("VARCHAR(255)35", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_foul_stat (36)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームイエローカードの統計情報", "yellow_card_stat");
		columnRestMap.put("VARCHAR(255)37", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_yellow_card_stat (38)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームレッドカードの統計情報", "red_card_stat");
		columnRestMap.put("VARCHAR(255)39", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_red_card_stat (40)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームスローインの統計情報", "slow_in_stat");
		columnRestMap.put("VARCHAR(255)41", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_slow_in_stat (42)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームボックスタッチの統計情報", "box_touch_stat");
		columnRestMap.put("VARCHAR(255)43", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_box_touch_stat (44)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームパス数の統計情報", "pass_count_stat");
		columnRestMap.put("VARCHAR(255)45", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_pass_count_stat (46)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームファイナルサードパス数の統計情報", "final_third_pass_count_stat");
		columnRestMap.put("VARCHAR(255)47", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_final_third_pass_count_stat (48)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクロス数の統計情報", "cross_count_stat");
		columnRestMap.put("VARCHAR(255)49", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_cross_count_stat (50)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームタックル数の統計情報", "tackle_count_stat");
		columnRestMap.put("VARCHAR(255)51", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// clear_count_stat (53)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームクリア数の統計情報", "clear_count_stat");
		columnRestMap.put("VARCHAR(255)53", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// intercept_count_stat (55)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ホームインターセプト数の統計情報", "intercept_count_stat");
		columnRestMap.put("VARCHAR(255)55", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		// away_intercept_count_stat (56)
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M028, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(400)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(400)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("キーフラグ", "key_flg");
		columnRestMap.put("VARCHAR(1)17", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("チーム", "team");
		columnRestMap.put("VARCHAR(400)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("相手チーム", "opposite_team");
		columnRestMap.put("VARCHAR(400)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("0-10", "time0_10");
		columnRestMap.put("VARCHAR(400)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("10-20", "time10_20");
		columnRestMap.put("VARCHAR(400)07", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("20-30", "time20_30");
		columnRestMap.put("VARCHAR(400)08", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("30-40", "time30_40");
		columnRestMap.put("VARCHAR(400)09", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("40-45", "time40_45");
		columnRestMap.put("VARCHAR(400)10", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("45-50", "time45_50");
		columnRestMap.put("VARCHAR(400)16", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("50-60", "time50_60");
		columnRestMap.put("VARCHAR(400)11", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("60-70", "time60_70");
		columnRestMap.put("VARCHAR(400)12", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("70-80", "time70_80");
		columnRestMap.put("VARCHAR(400)13", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("80-90", "time80_90");
		columnRestMap.put("VARCHAR(400)14", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("90-100", "time90_100");
		columnRestMap.put("VARCHAR(400)15", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M029, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("国", "country");
		columnRestMap.put("VARCHAR(40)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("リーグ", "league");
		columnRestMap.put("VARCHAR(40)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("テーブルID", "table_id");
		columnRestMap.put("VARCHAR(10)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("備考", "remarks");
		columnRestMap.put("VARCHAR(2)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M097, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイル名", "file_name");
		columnRestMap.put("VARCHAR(400)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("更新前通番リスト", "bef_seq_list");
		columnRestMap.put("VARCHAR(400)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("更新後通番リスト", "af_seq_list");
		columnRestMap.put("VARCHAR(400)04", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("更新前ファイルハッシュ値", "bef_file_hash");
		columnRestMap.put("VARCHAR(256)05", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("更新後ファイルハッシュ値", "af_file_hash");
		columnRestMap.put("VARCHAR(256)06", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M098, columnSceneCardMap);

		columnSceneCardMap = new LinkedHashMap<>();
		columnMainMap.put("ID", "id");
		columnRestMap.put("INT(11)01", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイル名", "file_name");
		columnRestMap.put("VARCHAR(400)02", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnMainMap.put("ファイルハッシュ値", "file_hash");
		columnRestMap.put("VARCHAR(256)03", "");
		columnSceneCardMap.put(columnMainMap, columnRestMap);
		columnRestMap = new HashMap<>();
		columnMainMap = new HashMap<>();
		columnKeyMap.put(UniairConst.BM_M099, columnSceneCardMap);
		COLUMN_MAP = Collections.unmodifiableMap(columnKeyMap);
	}

	/**
	 * DB項目,ヘッダー用PRIMARYKEYMapping
	 */
	public static final Map<String, List<String>> COLUMN_PRIMARY_MAP;
	static {
		Map<String, List<String>> primaryMap = new LinkedHashMap<>();
		List<String> columnPrimaryList = new ArrayList<>();
		columnPrimaryList.add("seq");
		primaryMap.put(UniairConst.BM_M001, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M003, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M004, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M005, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M007, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M021, columnPrimaryList);
		columnPrimaryList = new ArrayList<>();
		columnPrimaryList.add("data_seq");
		primaryMap.put(UniairConst.BM_M002, columnPrimaryList);
		columnPrimaryList = new ArrayList<>();
		columnPrimaryList.add("id");
		primaryMap.put(UniairConst.BM_M006, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M008, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M009, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M010, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M011, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M012, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M013, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M014, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M015, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M016, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M017, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M018, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M019, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M020, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M022, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M023, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M024, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M025, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M097, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M098, columnPrimaryList);
		primaryMap.put(UniairConst.BM_M099, columnPrimaryList);
		COLUMN_PRIMARY_MAP = Collections.unmodifiableMap(primaryMap);
	}

	/**
	 * DB項目,ヘッダー用Mapping(デフォルト項目)
	 */
	public static final Map<String, Map<String, Map<String, String>>> COLUMN_DEFAULT_KEY_MAP;
	static {
		Map<String, Map<String, Map<String, String>>> columnKeyDefaultMap = new HashMap<>();
		Map<String, Map<String, String>> columnDefaultMap = new LinkedHashMap<>();
		Map<String, String> columnRestMap = new HashMap<>();
		columnRestMap.put("register_id", "VARCHAR(100)");
		columnDefaultMap.put("登録ID", columnRestMap);
		columnRestMap = new HashMap<>();
		columnRestMap.put("register_time", "DATETIME");
		columnDefaultMap.put("登録日時", columnRestMap);
		columnRestMap = new HashMap<>();
		columnRestMap.put("update_id", "VARCHAR(100)");
		columnDefaultMap.put("更新ID", columnRestMap);
		columnRestMap = new HashMap<>();
		columnRestMap.put("update_time", "DATETIME");
		columnDefaultMap.put("更新日時", columnRestMap);
		columnRestMap = new HashMap<>();
		columnKeyDefaultMap.put("GET", columnDefaultMap);
		COLUMN_DEFAULT_KEY_MAP = Collections.unmodifiableMap(columnKeyDefaultMap);
	}

	/**
	 * DB項目,ヘッダー用Mapping(デフォルト値項目)
	 */
	public static final Map<String, Map<String, String>> COLUMN_DEFAULT_VALUE_MAP;
	static {
		Map<String, Map<String, String>> columnMap = new HashMap<>();
		Map<String, String> columnDefaultValueMap = new LinkedHashMap<>();
		columnDefaultValueMap.put("登録ID", "BmSystem");
		columnDefaultValueMap.put("登録日時", DateUtil.getSysDate());
		columnDefaultValueMap.put("更新ID", "BmSystem");
		columnDefaultValueMap.put("更新日時", DateUtil.getSysDate());
		columnMap.put("GET", columnDefaultValueMap);
		COLUMN_DEFAULT_VALUE_MAP = Collections.unmodifiableMap(columnMap);
	}

	/**
	 * DB項目,テーブル名Mapping
	 */
	public static final Map<Integer, Map<String, String>> WITHIN_MAP;
	static {
		Map<Integer, Map<String, String>> ListMap = new LinkedHashMap<>();
		Map<String, String> ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M008, "within_data_20minutes_home_scored");
		ListMap.put(0, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M009, "within_data_20minutes_away_scored");
		ListMap.put(1, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M010, "within_data_20minutes_same_scored");
		ListMap.put(2, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M011, "within_data_45minutes_home_scored");
		ListMap.put(3, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M012, "within_data_45minutes_away_scored");
		ListMap.put(4, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M013, "within_data_20minutes_home_all_league");
		ListMap.put(5, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M014, "within_data_20minutes_away_all_league");
		ListMap.put(6, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M015, "within_data_45minutes_home_all_league");
		ListMap.put(7, ListMstDetailMap);
		ListMstDetailMap = new LinkedHashMap<>();
		ListMstDetailMap.put(UniairConst.BM_M016, "within_data_45minutes_away_all_league");
		ListMap.put(8, ListMstDetailMap);
		WITHIN_MAP = Collections.unmodifiableMap(ListMap);
	}

	/**
	 * キーマップ,値マップどちらかを取得する
	 * @param tableId テーブルID
	 * @param searchData 探索データ
	 * @return list キー,値リスト
	 */
	public static List<String> getWhichKeyValueMap(String tableId, String searchData) {
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			String headOrBody = null;
			Iterator<Map<String, String>> itMapKey = map.keySet().iterator();
			while (itMapKey.hasNext()) {
				Map<String, String> defaultKey = (Map<String, String>) itMapKey.next();
				keyList.addAll(defaultKey.keySet());
				valueList.addAll(defaultKey.values());
			}
			headOrBody = (keyList.contains(searchData)) ? "header" : "body";

			if (headOrBody == "header") {
				return keyList;
			} else {
				return valueList;
			}
		}
		return null;
	}

	/**
	 * csvのヘッダーチェック用文字列を取得する
	 * @param tableId テーブルID
	 * @return sb.toString() 文字列
	 */
	public static String mkCsvChkHeader(String tableId) {
		StringBuilder sb = new StringBuilder();
		List<String> list = new ArrayList<String>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			Iterator<Map<String, String>> itMapKey = map.keySet().iterator();
			while (itMapKey.hasNext()) {
				Map<String, String> defaultKey = (Map<String, String>) itMapKey.next();
				list.addAll(defaultKey.keySet());
			}
			for (String val : list) {
				if (sb.toString().length() > 0) {
					sb.append(",");
				}
				sb.append(val);
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * デフォルトのキーを含めた値リストを取得する
	 * @param tableId テーブルID
	 * @return list 値リスト
	 */
	public static List<String> getIncludingDefaultKeyMap(String tableId) {
		List<String> list = new ArrayList<String>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			Iterator<Map<String, String>> itMapKey = map.keySet().iterator();
			while (itMapKey.hasNext()) {
				Map<String, String> defaultKey = (Map<String, String>) itMapKey.next();
				list.addAll(defaultKey.values());
			}
			Map<String, Map<String, String>> defaultMap = COLUMN_DEFAULT_KEY_MAP.get("GET");
			for (Map<String, String> keyMap : defaultMap.values()) {
				list.addAll(keyMap.keySet());
			}
		}
		return list;
	}

	/**
	 * 値リストを取得する
	 * @param tableId テーブルID
	 * @return list 値リスト
	 */
	public static List<String> getKeyMap(String tableId) {
		List<String> list = new ArrayList<String>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			Iterator<Map<String, String>> itMapKey = map.keySet().iterator();
			while (itMapKey.hasNext()) {
				Map<String, String> defaultKey = (Map<String, String>) itMapKey.next();
				list.addAll(defaultKey.values());
			}
		}
		return list;
	}

	/**
	 * デフォルトのキーリストを取得する
	 * @return list 値リスト
	 */
	public static List<String> getDefaultKeyMap() {
		List<String> list = new ArrayList<String>();
		list.addAll(COLUMN_DEFAULT_KEY_MAP.get("GET").keySet());
		return list;
	}

	/**
	 * デフォルトのキーリストを取得する
	 * @return list 値リスト
	 */
	public static List<String> getDefaultCreateTableValueMap() {
		List<String> list = new ArrayList<String>();
		Map<String, Map<String, String>> defMapList = COLUMN_DEFAULT_KEY_MAP.get("GET");
		for (Map<String, String> keyMap : defMapList.values()) {
			list.addAll(keyMap.values());
		}
		return list;
	}

	/**
	 * デフォルトの値リストを取得する
	 * @return list 値リスト
	 */
	public static List<String> getDefaultValueMap() {
		List<String> list = new ArrayList<String>();
		list.addAll(COLUMN_DEFAULT_VALUE_MAP.get("GET").values());
		return list;
	}

	/**
	 * authとtableIdからテーブル名を取得する
	 * @param auth 参照権限
	 * @param tableId テーブルID
	 * @return tableName 文字列
	 */
	public static String getAuthAndTableIdToTableName(String auth, String tableId) {
		StringBuilder tableName = new StringBuilder();
		if (auth != null) {
			tableName.append(auth);
			tableName.append(".");
		}
		String name = getTableIdToTableName(tableId);
		tableName.append(name);
		return tableName.toString();
	}

	/**
	 * tableIdからテーブル名を取得する
	 * @param tableId テーブルID
	 * @return tableName 文字列
	 */
	public static String getTableIdToTableName(String tableId) {
		String schemaName = getTableIdToSchemaName(tableId);
		return TABLE_MAP.get(schemaName).get(tableId);
	}

	/**
	 * 全スキーマリストを取得する
	 * @return スキーマリスト
	 */
	public static List<String> getAllSchemaList() {
		List<String> list = new ArrayList<String>();
		list.addAll(TABLE_MAP.keySet());
		return list;
	}

	/**
	 * 全テーブルリストを取得する
	 * @return テーブルリスト
	 */
	public static List<String> getAllTableList() {
		List<String> list = new ArrayList<String>();
		List<String> schemaList = getAllSchemaList();
		for (String schema : schemaList) {
			list.addAll(TABLE_MAP.get(schema).values());
		}
		return list;
	}

	/**
	 * 全テーブルIDリストを取得する
	 * @return テーブルリスト
	 */
	public static List<String> getAllTableIdList() {
		List<String> list = new ArrayList<String>();
		List<String> schemaList = getAllSchemaList();
		for (String schema : schemaList) {
			list.addAll(TABLE_MAP.get(schema).keySet());
		}
		return list;
	}

	/**
	 * 特定のスキーマ内からテーブル名のリストを取得する
	 * @param schema スキーマ
	 * @return テーブルリスト
	 */
	public static List<String> getSchemaToTableNameList(String schema) {
		List<String> list = new ArrayList<String>();
		if (TABLE_MAP.containsKey(schema)) {
			list.addAll(TABLE_MAP.get(schema).values());
		}
		return list;
	}

	/**
	 * 特定のスキーマ内のテーブルIDのリストを取得する
	 * @param schema スキーマ
	 * @return テーブルIDリスト
	 */
	public static List<String> getSchemaToTableIdList(String schema) {
		List<String> list = new ArrayList<String>();
		if (TABLE_MAP.containsKey(schema)) {
			list.addAll(TABLE_MAP.get(schema).keySet());
		}
		return list;
	}

	/**
	 * テーブルIDからスキーマ名を取得する
	 * @param tableId テーブルID
	 * @return スキーマ名
	 */
	public static String getTableIdToSchemaName(String tableId) {
		List<String> schemalist = getAllSchemaList();
		for (String schema : schemalist) {
			if (TABLE_MAP.get(schema).containsKey(tableId)) {
				return schema;
			}
		}
		return null;
	}

	/**
	 * テーブルIDからプライマリキーを取得する
	 * @param tableId テーブルID
	 * @return list 値リスト
	 */
	public static List<String> getPrimaryKeyMap(String tableId) {
		if (COLUMN_PRIMARY_MAP.containsKey(tableId)) {
			List<String> list = COLUMN_PRIMARY_MAP.get(tableId);
			return list;
		}
		return null;
	}

	/**
	 * WITHIN_MAPからテーブル情報を取得する
	 * @param index インデックス
	 * @return list 値リスト
	 */
	public static List<String> getWithInTableIdMap(Integer index) {
		List<String> list = new ArrayList<>();
		if (WITHIN_MAP.containsKey(index)) {
			Map<String, String> keys = WITHIN_MAP.get(index);
			for (Map.Entry<String, String> keySub : keys.entrySet()) {
				list.add(keySub.getKey());
				list.add(keySub.getValue());
				return list;
			}
		}
		return null;
	}

	/**
	 * テーブル作成用のデータ型を取得する
	 * @param tableId テーブルID
	 * @return list データ型リスト
	 */
	public static List<String> getCreateTableColumnType(String tableId) {
		List<String> list = new ArrayList<>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			for (Map.Entry<Map<String, String>, Map<String, String>> subMap : map.entrySet()) {
				Map<String, String> value = subMap.getValue();
				for (String subsubMap : value.keySet()) {
					list.add(subsubMap.substring(0, subsubMap.length() - 2));
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * テーブル作成用の制約を取得する
	 * @param tableId テーブルID
	 * @return list データ型リスト
	 */
	public static List<String> getCreateTableColumnRest(String tableId) {
		List<String> list = new ArrayList<>();
		if (COLUMN_MAP.containsKey(tableId)) {
			Map<Map<String, String>, Map<String, String>> map = COLUMN_MAP.get(tableId);
			for (Map.Entry<Map<String, String>, Map<String, String>> subMap : map.entrySet()) {
				Map<String, String> value = subMap.getValue();
				for (String subsubMap : value.values()) {
					list.add(subsubMap);
				}
			}
			return list;
		}
		return null;
	}
}
