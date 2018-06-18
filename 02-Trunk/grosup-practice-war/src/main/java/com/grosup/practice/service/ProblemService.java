package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.UserBean;
import com.grosup.practice.dao.ProblemDao;
import com.grosup.practice.dao.StatisticsDao;
import com.grosup.practice.util.ObjectUtil;

@Service
public class ProblemService {
	
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private StatisticsDao statisticsDao;
	
	/**获得随机一道题*/
	public ProblemBean getRandomOne(int typeID){
		return problemDao.getRandomOne(typeID);
	}
	
	/**根据题目ID取题目答案
	 * @throws Exception */
	public boolean checkAnswer(int id, String answer, int userID, int typeID) throws Exception {
		
		boolean result = true;
		String correct = problemDao.getAnswerByID(id);
		if (ObjectUtil.isNull(answer) || correct.equals("")) {
			throw new Exception();
		}
		if (!answer.trim().equals(correct)) {
			result = false;
			//TODO 更新做题记录并添加到错题集
			statisticsDao.updateUserDoneByUnCorrect(userID, typeID);
			return result;
		}
		//TODO 更新做题记录
		statisticsDao.updateUserDoneByCorrect(userID, typeID);
		return result;
	}
}
