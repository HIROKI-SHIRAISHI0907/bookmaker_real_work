package dev.application.common.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.application.common.exception.SystemException;
import dev.application.common.mapping.CorrelationSummary;
import dev.application.db.CsvRegisterImpl;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.CorrelationEntity;

/**
 * Kmeans-Clusterを実行する
 * @author shiraishitoshio
 *
 */
public class ExecuteClusterLogic {

	/**
	 * 特徴量MAP
	 */
	public static final Map<String, List<String>> FEATUREFIELDMAP;
	static {
		Map<String, List<String>> featureToFieldMap = new HashMap<>();
		featureToFieldMap.put("期待値", Arrays.asList("homeExpInfo", "awayExpInfo"));
		featureToFieldMap.put("支配率", Arrays.asList("homeDonationInfo", "awayDonationInfo"));
		featureToFieldMap.put("シュート", Arrays.asList("homeShootAllInfo", "awayShootAllInfo"));
		featureToFieldMap.put("枠内シュート", Arrays.asList("homeShootInInfo", "awayShootInInfo"));
		featureToFieldMap.put("枠外シュート", Arrays.asList("homeShootOutInfo", "awayShootOutInfo"));
		featureToFieldMap.put("ブロックシュート", Arrays.asList("homeBlockShootInfo", "awayBlockShootInfo"));
		featureToFieldMap.put("ビックチャンス", Arrays.asList("homeBigChanceInfo", "awayBigChanceInfo"));
		featureToFieldMap.put("コーナーキック", Arrays.asList("homeCornerInfo", "awayCornerInfo"));
		featureToFieldMap.put("ボックス枠内シュート", Arrays.asList("homeBoxShootInInfo", "awayBoxShootInInfo"));
		featureToFieldMap.put("ボックス枠外シュート", Arrays.asList("homeBoxShootOutInfo", "awayBoxShootOutInfo"));
		featureToFieldMap.put("ゴールポスト", Arrays.asList("homeGoalPostInfo", "awayGoalPostInfo"));
		featureToFieldMap.put("ヘディングゴール", Arrays.asList("homeGoalHeadInfo", "awayGoalHeadInfo"));
		featureToFieldMap.put("キーパーセーブ", Arrays.asList("homeKeeperSaveInfo", "awayKeeperSaveInfo"));
		featureToFieldMap.put("フリーキック", Arrays.asList("homeFreeKickInfo", "awayFreeKickInfo"));
		featureToFieldMap.put("オフサイド", Arrays.asList("homeOffsideInfo", "awayOffsideInfo"));
		featureToFieldMap.put("ファール", Arrays.asList("homeFoulInfo", "awayFoulInfo"));
		featureToFieldMap.put("イエローカード", Arrays.asList("homeYellowCardInfo", "awayYellowCardInfo"));
		featureToFieldMap.put("レッドカード", Arrays.asList("homeRedCardInfo", "awayRedCardInfo"));
		featureToFieldMap.put("スローイン", Arrays.asList("homeSlowInInfo", "awaySlowInInfo"));
		featureToFieldMap.put("ボックスタッチ", Arrays.asList("homeBoxTouchInfo", "awayBoxTouchInfo"));
		featureToFieldMap.put("クリア数", Arrays.asList("homeClearCountInfo", "awayClearCountInfo"));
		featureToFieldMap.put("インターセプト数", Arrays.asList("homeInterceptCountInfo", "awayInterceptCountInfo"));
		featureToFieldMap.put("パス数_成功",
				Arrays.asList("homePassCountInfoOnSuccessCount", "awayPassCountInfoOnSuccessCount"));
		featureToFieldMap.put("パス数_試行", Arrays.asList("homePassCountInfoOnTryCount", "awayPassCountInfoOnTryCount"));
		featureToFieldMap.put("ファイナルサードパス数_成功", Arrays.asList("homeFinalThirdPassCountInfoOnSuccessCount",
				"awayFinalThirdPassCountInfoOnSuccessCount"));
		featureToFieldMap.put("ファイナルサードパス数_試行",
				Arrays.asList("homeFinalThirdPassCountInfoOnTryCount", "awayFinalThirdPassCountInfoOnTryCount"));
		featureToFieldMap.put("クロス数_成功",
				Arrays.asList("homeCrossCountInfoOnSuccessCount", "awayCrossCountInfoOnSuccessCount"));
		featureToFieldMap.put("クロス数_試行", Arrays.asList("homeCrossCountInfoOnTryCount", "awayCrossCountInfoOnTryCount"));
		featureToFieldMap.put("タックル数_成功",
				Arrays.asList("homeTackleCountInfoOnSuccessCount", "awayTackleCountInfoOnSuccessCount"));
		featureToFieldMap.put("タックル数_試行",
				Arrays.asList("homeTackleCountInfoOnTryCount", "awayTackleCountInfoOnTryCount"));
		FEATUREFIELDMAP = Collections.unmodifiableMap(featureToFieldMap);
	}

	/** チェックボディ */
	public static final String KMEANS_CHKBODY = "KmeansCluster";

	/** チェックボディ */
	public static final String HIERARCY_CHKBODY = "hierarchical";

	/**
	 * 実行
	 * @param csvPath
	 * @param country
	 * @param league
	 * @param home
	 * @param away
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void execute(String csvPath, String country, String league,
			String home, String away)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (int chk = 0; chk <= 1; chk++) {
			String chkBody = "";
			switch (chk) {
			case 0:
				chkBody = KMEANS_CHKBODY;
				break;
			case 1:
				chkBody = HIERARCY_CHKBODY;
				break;
			}

			// DBに保存済みか
			if (getTeamStaticsData(country, league, home, away, "", chkBody)) {
				// 以降処理しない
				return;
			}

			Map<String, List<String>> homeChkMap = new HashMap<String, List<String>>();
			Map<String, List<String>> awayChkMap = new HashMap<String, List<String>>();
			Map<String, String> homeClusterMap = new LinkedHashMap<String, String>();
			Map<String, String> awayClusterMap = new LinkedHashMap<String, String>();
			try {
				// 実行環境インタプリタ,スクリプト
				String pythonPath = "/Library/Frameworks/Python.framework/Versions/3.12/bin/python3"; // または python
				String scriptPath = "/Users/shiraishitoshio/bookmaker/cluster/clustering.py";

				ProcessBuilder pb = new ProcessBuilder(pythonPath, scriptPath, csvPath, chkBody);
				pb.redirectErrorStream(true);
				Process process = pb.start();

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;

				int clusterChange = 0;
				boolean clusterClassificationFlg = true;
				int classification = 0;
				int cluster = 0;
				while ((line = reader.readLine()) != null) {
					String[] tokens = line.trim().split("\\s+");
					if (clusterClassificationFlg) {
						if ("順位".equals(tokens[0])) {
							continue;
						}
						if ("*****".equals(line)) {
							clusterChange++;
							classification++;
						} else {
							if (clusterChange == 0) {
								homeClusterMap.put(tokens[0],
										tokens[tokens.length - 1]);
							} else {
								awayClusterMap.put(tokens[0],
										tokens[tokens.length - 1]);
							}
						}
						if (classification == 2) {
							clusterClassificationFlg = false;
						}
					} else {
						if ("クラスタ".equals(tokens[0])) {
							cluster++;
							continue;
						}
						List<String> homelineList = new ArrayList<String>();
						List<String> awaylineList = new ArrayList<String>();
						if (cluster == 1) {
							int hB = 0;
							for (String token : tokens) {
								if (hB == 0) {
									hB++;
									continue;
								}
								homelineList.add(token);
								hB++;
							}
							homeChkMap.put(tokens[0], homelineList);
						} else {
							int aB = 0;
							for (String token : tokens) {
								if (aB == 0) {
									aB++;
									continue;
								}
								awaylineList.add(token);
								aB++;
							}
							awayChkMap.put(tokens[0], awaylineList);
						}
					}
					System.out.println(line); // Pythonの出力をJavaで表示
				}

				int exitCode = process.waitFor();
				System.out.println("Python script exited with code: " + exitCode);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// pythonで実行したデータをEntityに設定
			CorrelationEntity correlationEntity = new CorrelationEntity();
			correlationEntity.setFile(csvPath);
			correlationEntity.setCountry(country);
			correlationEntity.setLeague(league);
			correlationEntity.setHome(home);
			correlationEntity.setAway(away);
			correlationEntity.setScore("");
			correlationEntity.setChkBody(chkBody);
			correlationEntity = chkFeature(homeChkMap, correlationEntity, homeClusterMap, 1);
			if (correlationEntity == null) {
				return;
			}
			correlationEntity = chkFeature(awayChkMap, correlationEntity, awayClusterMap, 2);
			if (correlationEntity == null) {
				return;
			}

			List<CorrelationEntity> insertEntities = new ArrayList<CorrelationEntity>();
			insertEntities.add(correlationEntity);

			CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
			try {
				csvRegisterImpl.executeInsert(UniairConst.BM_M024,
						insertEntities, 1, insertEntities.size());
			} catch (Exception e) {
				System.err.println("correlation_data insert err execute: " + e);
			}

		}
	}

	/**
	 * 特徴量のチェック
	 * @param chkMap
	 * @param correlationEntity
	 * @param clusterMap
	 * @param homeAwayFlg
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private CorrelationEntity chkFeature(Map<String, List<String>> chkMap, CorrelationEntity correlationEntity,
			Map<String, String> clusterMap, int homeAwayFlg)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// cluster値を連結する
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> map : clusterMap.entrySet()) {
			if (sb.toString().length() > 0) {
				sb.append(",");
			}
			sb.append(map.getValue());
		}

		for (Map.Entry<String, List<String>> map : chkMap.entrySet()) {
			// superMapに登録済みの時
			if (FEATUREFIELDMAP.containsKey(map.getKey())) {
				List<String> list = FEATUREFIELDMAP.get(map.getKey());
				String ha_feature = "";
				if (homeAwayFlg == 1) {
					ha_feature = list.get(0);
				} else {
					ha_feature = list.get(1);
				}

				if (map.getValue().size() != 4) {
					System.err.println("4列存在しないデータがありましたので登録をskipします。");
					return correlationEntity;
				}

				// 登録したい値をsummaryに設定
				CorrelationSummary correlationSummary = null;
				try {
					correlationSummary = new CorrelationSummary(
							map.getValue().get(0),
							map.getValue().get(1),
							map.getValue().get(2),
							map.getValue().get(3),
							sb.toString());
				} catch (Exception e) {
					return null;
				}

				// 元のフィールドを取得
				Field field = correlationEntity.getClass().getDeclaredField(ha_feature);
				field.setAccessible(true); // privateフィールドにもアクセスできるようにする
				// newEntityの対応するフィールドに値を設定
				field.set(correlationEntity, correlationSummary);
			}
		}
		return correlationEntity;
	}

	/**
	 * 取得メソッド
	 * @param country 国
	 * @param league リーグ
	 * @param home ホーム
	 * @param away アウェー
	 * @param flg フラグ
	 * @param chkBody 検証内容
	 */
	private boolean getTeamStaticsData(String country, String league, String home, String away, String flg,
			String chkBody) {
		String[] selDataList = new String[1];
		selDataList[0] = "id";

		String where = "country = '" + country + "' and league = '" + league + "' "
				+ "and home = '" + home + "' and away = '" + away + "' and score = '" + flg + "' and "
				+ "chk_body = '" + chkBody + "'";

		List<List<String>> selectResultList = null;
		SqlMainLogic select = new SqlMainLogic();
		try {
			selectResultList = select.executeSelect(null, UniairConst.BM_M024, selDataList,
					where, null, "1");
		} catch (Exception e) {
			throw new SystemException("", "", "", "err");
		}

		if (!selectResultList.isEmpty()) {
			return true;
		}
		return false;
	}

}
