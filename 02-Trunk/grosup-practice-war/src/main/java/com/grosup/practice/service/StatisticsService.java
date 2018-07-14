package com.grosup.practice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.dao.StatisticsDao;

@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	/**
	 * 获取用户排名信息
	 * @param userID
	 * @return
	 */
	public StatisticsBean getUserRankInfo(Map<String, Object> params) {
		return statisticsDao.getUserRankInfo(params);
	}
	
	public int getMaxRank() {
		return statisticsDao.getMaxRank();
	}
}
