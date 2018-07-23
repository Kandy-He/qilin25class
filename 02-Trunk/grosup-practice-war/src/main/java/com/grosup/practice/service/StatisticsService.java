package com.grosup.practice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
