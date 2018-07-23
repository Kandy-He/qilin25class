package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.util.AbstractDao;
import com.grosup.practice.util.GrosupException;

@Repository
public class StatisticsDao extends AbstractDao {
	// 做题正确更新
	public boolean updateUserDoneByCorrect(int userID, String knowledgeKey) throws GrosupException{
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		int row = this.getSession().update("com.grosup.practice.statistics.updateUserDoneByCorrect", paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}

	// 做题正确更新
	public boolean updateUserDoneByUnCorrect(int userID, String knowledgeKey) throws GrosupException{
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
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
	public boolean checkIsExist(int userID, String knowledgeKey) throws GrosupException{
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		int row = this.getSession().selectOne("com.grosup.practice.statistics.checkIsExist",paramMap);
		if (row > 0) {
			result = true;
		}
		return result;
	}
	
	public void addOneRecord(int userID, String knowledgeKey) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		paramMap.put("havaDone", 0);
		paramMap.put("onceRight", 0);
		paramMap.put("flower", 0);
		this.getSession().insert("com.grosup.practice.statistics.addOneRecord",paramMap);
	}
	
	public List<StatisticsBean> getUsersRankInfo() throws GrosupException{
		return this.getSession().selectList("com.grosup.practice.statistics.getUsersRankInfo");
	}
	
	public void usersRankInfoAdd(List<StatisticsBean> list) throws GrosupException{
		this.getSession().insert("com.grosup.practice.statistics.usersRankInfoAdd", list);
	}
	/**
	 * 获取用户排名信息
	 * @param userID
	 * @return
	 */
	public StatisticsBean getUserRankInfo(Map<String, Object> params) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.statistics.getUserRankInfo", params);
	}
	
	public int getMaxRank() throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.statistics.getMaxRank");
	}
	
	public String getUserHighestWrong(int userID) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.statistics.getUserHighestWrong", userID);
	}
}
