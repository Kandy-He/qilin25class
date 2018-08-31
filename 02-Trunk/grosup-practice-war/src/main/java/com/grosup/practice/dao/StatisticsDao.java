package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.KnowledgeDTO;
import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.PaperStatBean;
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
	
	public List<KnowledgeDTO> getStatInfoByUserID(int userID) throws GrosupException{
		return this.getSession().selectList("com.grosup.practice.statistics.getStatInfoByUserID", userID);
	}
	/**
	 * 根据学生ID获取学生的测试模块统计情况 
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public PaperStatBean getPaperTotalStatInfo(int userID) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.statistics.getPaperTotalStatInfo", userID);
	}
	/**
	 * 根据学生ID获取学生的练习模块统计情况 
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public StatisticsBean getQuesTotalStatInfo(int userID) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.statistics.getQuesTotalStatInfo", userID);
	}
	/**
	 * 获取试卷haveDone信息
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public List<PaperStatBean> getPaperHaveDoneInfo(int userID) throws GrosupException{
		return this.getSession().selectList("com.grosup.practice.statistics.getPaperHaveDoneInfo", userID);
	}
	/**
	 * 增加指定数量小红花
	 * @param userID        
	 * @param knowledgeKey
	 * @param increase      小红花增加数
	 */
	public void flowrIncrease(int userID, String knowledgeKey, int increase) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		paramMap.put("increase", increase);
		this.getSession().update("com.grosup.practice.statistics.flowrIncrease", paramMap);
	}
}
