package dev.application.common.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.application.common.util.UniairColumnMapUtil;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;

/**
 * 渡された通番リストで同一の組み合わせであるものを紐付ける
 * @author shiraishitoshio
 *
 */
public class SameDataSeqChkLogic {

	/** キーマップ */
	private Map<String, List<String>> keyMap = new HashMap<String, List<String>>();

	/**
	 * 実行メソッド
	 * @param list 通番リスト
	 * @return 通番まとめリスト
	 */
	public List<List<String>> chkLogic(Set<String> list) {
		List<List<String>> sepaAllList = new ArrayList<>();

		// data DBからデータカテゴリ,ホームチーム,アウェーチームを取得
		List<String> select1List = UniairColumnMapUtil.getKeyMap(UniairConst.BM_M001);
		String[] sel1List = new String[select1List.size()];
		for (int i = 0; i < select1List.size(); i++) {
			sel1List[i] = select1List.get(i);
		}

		SqlMainLogic select = new SqlMainLogic();
		int chk = 1;
		for (String seq : list) {
			System.out.println("SameDataSeqChkLogic list:" + chk + " / " + list.size());
			List<List<String>> selectResultList = null;
			try {
				String searchWhere = "seq = '" + seq + "'";
				selectResultList = select.executeSelect(null, UniairConst.BM_M001, sel1List,
						searchWhere, null, "1");
			} catch (Exception e) {
				System.err.println("SameDataSeqChkLogic chkLogic select err searchData: seq = " +
						seq + ", err: " + e);
				return new ArrayList<>();
			}
			String team = selectResultList.get(0).get(5) + "-" + selectResultList.get(0).get(8);
			setKeyMap(team, seq);
			chk++;
		}

		for (Map.Entry<String, List<String>> map : this.keyMap.entrySet()) {
			sepaAllList.add(map.getValue());
		}

		return sepaAllList;
	}

	/**
	 * マップに設定する
	 * @param team チーム名
	 * @param seq 通番
	 */
	private void setKeyMap(String team, String seq) {
		if (this.keyMap.containsKey(team)) {
			List<String> keyValueList = this.keyMap.get(team);
			keyValueList.add(seq);
			this.keyMap.put(team, keyValueList);
		} else {
			List<String> seqList = new ArrayList<>();
			seqList.add(seq);
			this.keyMap.put(team, seqList);
		}
	}
}
