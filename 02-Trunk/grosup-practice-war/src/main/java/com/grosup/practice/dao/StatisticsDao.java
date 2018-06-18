package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.util.AbstractDao;

@Repository
public class StatisticsDao extends AbstractDao {
	// 做题正确更新
	public boolean updateUserDoneByCorrect(int userID, int typeID) {
		boolean result = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		int row = this.getSession().update("com.grosup.practice.class,updateUserDoneByCorrect", paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}

	// 做题正确更新
	public boolean updateUserDoneByUnCorrect(int userID, int typeID) {
		boolean result = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		int row = this.getSession().update("com.grosup.practice.class,updateUserDoneByUnCorrect",paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}
}
