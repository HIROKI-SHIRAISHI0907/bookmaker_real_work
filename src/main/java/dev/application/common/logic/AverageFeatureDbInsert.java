package dev.application.common.logic;

import java.util.ArrayList;
import java.util.List;

import dev.application.db.CsvRegisterImpl;
import dev.application.db.SqlMainLogic;
import dev.application.db.UniairConst;
import dev.application.entity.AverageFeatureEntity;

/**
 * average_feature_dataに登録するロジック
 * @author shiraishitoshio
 *
 */
public class AverageFeatureDbInsert {

	/**
	 * 登録メソッド
	 * @param entities 登録entityリスト
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void execute(List<AverageFeatureEntity> entities) {
		String[] selList = new String[1];
		selList[0] = "seq";

		List<AverageFeatureEntity> insertEntities = new ArrayList<AverageFeatureEntity>();
		for (AverageFeatureEntity entity : entities) {
			String whereStr = "seq = '" + entity.getSeq() + "'";

			List<List<String>> selectResultList = null;
			SqlMainLogic select = new SqlMainLogic();
			try {
				selectResultList = select.executeSelect(null, UniairConst.BM_M021, selList,
						whereStr, null, "1");
			} catch (Exception e) {
				System.err.println("average_feature_data select err searchData: " + e);
			}

			if (selectResultList.isEmpty()) {
				insertEntities.add(entity);
			}
		}

		if (!insertEntities.isEmpty()) {
			CsvRegisterImpl csvRegisterImpl = new CsvRegisterImpl();
			try {
				csvRegisterImpl.executeInsert(UniairConst.BM_M021,
						insertEntities, 1, insertEntities.size());
			} catch (Exception e) {
				System.err.println("average_feature_data insert err execute: " + e);
			}
		}
	}
}
