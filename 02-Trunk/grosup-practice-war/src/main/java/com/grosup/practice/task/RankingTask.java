package com.grosup.practice.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.dao.StatisticsDao;
import com.grosup.practice.util.ObjectUtil;

@Component
public class RankingTask {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	@Scheduled(cron="0 0 6 * * ?") //每天早上六点触发
//	 @Scheduled(cron="0/30 * * * * ? ") //间隔5秒执行  
	public void getRanking() {
		System.out.println("定时任务开始啦，哈哈哈"); 
		List<StatisticsBean> list = statisticsDao.getUsersRankInfo();
		for (StatisticsBean statisticsBean : list) {
			System.out.println(statisticsBean.toString());
		}
		if (ObjectUtil.isNotNull(list) && list.size() > 0) {
			statisticsDao.usersRankInfoAdd(list);
		}
	}
}
