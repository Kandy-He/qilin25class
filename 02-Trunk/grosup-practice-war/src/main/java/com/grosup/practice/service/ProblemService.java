package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.dao.ProblemDao;
import com.grosup.practice.dao.RecordDao;
import com.grosup.practice.dao.StatisticsDao;
import com.grosup.practice.util.ObjectUtil;

@Service
public class ProblemService {
	
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private StatisticsDao statisticsDao;
	@Autowired
	private RecordDao recordDao;
	
	/**获得随机一道题*/
	public ProblemBean getRandomOne(int typeID){
		return problemDao.getRandomOne(typeID);
	}
	
	/**根据题目ID取题目答案
	 * @throws Exception */
	public boolean checkAnswer(int id, String answer, int userID, int typeID) throws Exception {
		
		boolean result = true;
		ProblemBean problemBean = problemDao.getProblemByID(id);
		String correct = problemBean.getAnswer();
		if (ObjectUtil.isNull(answer) || correct.equals("")) {
			throw new Exception();
		}
		if (!answer.trim().equals(correct)) {
			RecordBean record = new RecordBean();
			record.setId(id);
			record.setDescription(problemBean.getDescription());
			record.setCorrect(problemBean.getAnswer());
			record.setTypeID(typeID);
			record.setUserID(userID);
			//更新做题记录并添加到错题集
			recordDao.addRecord(record);
			statisticsDao.updateUserDoneByUnCorrect(userID, typeID);
			result = false;
			return result;
		}
		//TODO 更新做题记录
		statisticsDao.updateUserDoneByCorrect(userID, typeID);
		return result;
	}
}
