package com.grosup.practice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.KnowledgeDTO;
import com.grosup.practice.beans.PaperStatBean;
import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.dao.StatisticsDao;
import com.grosup.practice.util.GrosupException;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	/**
	 * 获取用户排名信息
	 * @param userID
	 * @return
	 * @throws GrosupException 
	 */
	public StatisticsBean getUserRankInfo(Map<String, Object> params) throws GrosupException {
		return statisticsDao.getUserRankInfo(params);
	}
	
	public int getMaxRank() throws GrosupException {
		return statisticsDao.getMaxRank();
	}
	
	/**
	 * 根据学生ID获取学生的练习模块统计情况 
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public List<KnowledgeDTO> getStatInfoByUserID(int userID) throws GrosupException {
		return statisticsDao.getStatInfoByUserID(userID);
	}
	
	public StatisticsBean getQuesTotalStatInfo(int userID) throws GrosupException{
		return statisticsDao.getQuesTotalStatInfo(userID);
	}
	/**
	 * 获取试卷haveDone信息
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public List<PaperStatBean> getPaperHaveDoneInfo(int userID) throws GrosupException{
		return statisticsDao.getPaperHaveDoneInfo(userID);
	}
	
	/**
	 * 根据学生ID获取学生的测试模块统计情况 
	 * @param userID
	 * @return
	 * @throws GrosupException
	 */
	public PaperStatBean getPaperTotalStatInfo(int userID) throws GrosupException{
		return statisticsDao.getPaperTotalStatInfo(userID);
	}
}
