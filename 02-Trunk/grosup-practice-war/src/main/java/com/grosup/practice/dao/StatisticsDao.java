package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class StatisticsDao extends AbstractDao {
	// 做题正确更新
	public boolean updateUserDoneByCorrect(int userID, int typeID) {
		boolean result = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		int row = this.getSession().update("com.grosup.practice.statistics.updateUserDoneByCorrect", paramMap);
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
		int row = this.getSession().update("com.grosup.practice.statistics.updateUserDoneByUnCorrect",paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}
	/**
	 * 检查是否存在记录
	 * @param userID
	 * @param typeID
	 * @return
	 */
	public boolean checkIsExist(int userID, int typeID) {
		boolean result = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		int row = this.getSession().selectOne("com.grosup.practice.statistics.checkIsExist",paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}
	
	public void addOneRecord(int userID, int typeID) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		paramMap.put("havaDone", 0);
		paramMap.put("onceRight", 0);
		paramMap.put("flower", 0);
		this.getSession().insert("com.grosup.practice.statistics.addOneRecord",paramMap);
	}
	
	public List<StatisticsBean> getUsersRankInfo() {
		return this.getSession().selectList("com.grosup.practice.statistics.getUsersRankInfo");
	}
	
	public void usersRankInfoAdd(List<StatisticsBean> list) {
		this.getSession().insert("com.grosup.practice.statistics.usersRankInfoAdd", list);
	}
	/**
	 * 获取用户排名信息
	 * @param userID
	 * @return
	 */
	public StatisticsBean getUserRankInfo(Map<String, Object> params) {
		return this.getSession().selectOne("com.grosup.practice.statistics.getUserRankInfo", params);
	}
	
	public int getMaxRank() {
		return this.getSession().selectOne("com.grosup.practice.statistics.getMaxRank");
	}
}
