package dev.application.common.logic;

import java.util.ArrayList;
import java.util.List;

import dev.application.db.CsvRegisterImpl;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.DiffFeatureEntity;

/**
 * scoring_playstyle_past_dataに登録するロジック
 * @author shiraishitoshio
 *
 */
public class ScoringPlaystylePastDataDbInsert {

	/**
	 * 登録メソッド
	 * @param entities 登録entityリスト
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void execute(List<DiffFeatureEntity> entities) {
		String[] selList = new String[1];
		selList[0] = "id";

		List<DiffFeatureEntity> insertEntities = new ArrayList<DiffFeatureEntity>();
		for (DiffFeatureEntity entity : entities) {
			String whereStr = "id = '" + entity.getId() + "'";

			List<List<String>> selectResultList = null;
			SqlMainLogic select = new SqlMainLogic();
			try {
				selectResultList = select.executeSelect(null, UniairConst.BM_M022, selList,
						whereStr, null, "1");
			} catch (Exception e) {
				System.err.println("scoring_playstyle_past_data select err searchData: " + e);
			}

			if (selectResultList.isEmpty()) {
				insertEntities.add(entity);
			}
		}

		if (!insertEntities.isEmpty()) {
			CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
			try {
				csvRegisterImpl.executeInsert(UniairConst.BM_M022,
						insertEntities, 1, insertEntities.size());
			} catch (Exception e) {
				System.err.println("scoring_playstyle_past_data insert err execute: " + e);
			}
		}
	}
}
