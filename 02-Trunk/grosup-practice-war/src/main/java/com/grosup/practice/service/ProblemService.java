package com.grosup.practice.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.dao.ProblemDao;
import com.grosup.practice.dao.RecordDao;
import com.grosup.practice.dao.StatisticsDao;
import com.grosup.practice.util.ObjectUtil;

@Service
public class ProblemService {
	
	private static Logger logger = Logger.getLogger(ProblemService.class);
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
	
	/**检查是否做对
	 * @throws Exception */
	@Transactional
	public boolean checkAnswer(int id, String answer, int userID, String expression1, String expression2, String expression3) throws Exception {
		
		boolean result = true;
		ProblemBean problemBean = problemDao.getProblemByID(id);
		if (ObjectUtil.isNull(answer) || "".equals(problemBean.getAnswer())) {
			logger.error("答案不能为空");
			throw new Exception();
		}
		
		//如果题型是计算题calculateQues
		if ("calculateQues".equals(problemBean.getProKey())) {
			//做题错误
			if (!answer.trim().equals(problemBean.getAnswer())) {
				result = false;
			} 
		} else if("applicationQues".equals(problemBean.getProKey())) {//如果是应用题
			result = checkApplication(expression1, expression2, expression3, answer, problemBean);
		}
		//判断做题记录是否存在
		if (!statisticsDao.checkIsExist(userID, problemBean.getTypeID())) {
			statisticsDao.addOneRecord(userID, problemBean.getTypeID());
		}
		
		//判断做题结果更新做题记录及错误结果
		if (!result) {
			RecordBean record = new RecordBean();
			record.setId(id);
			record.setDescription(problemBean.getDescription());
			record.setCorrect(problemBean.getAnswer());
			record.setUserID(userID);
			record.setResult(answer);
			record.setExpression1(expression1);
			record.setExpression2(expression2);
			record.setExpression3(expression3);
			record.setTypeID(problemBean.getTypeID());
			record.setProKey(problemBean.getProKey());
			record.setAnswerDesc(problemBean.getAnswerDesc());
			//更新做题记录并添加到错题集
			if (recordDao.checkIsExist(record)) {//如果错题存在则更新，否则添加一条新记录
				recordDao.updateRecord(record);
			} else {
				recordDao.addRecord(record);
				statisticsDao.updateUserDoneByUnCorrect(userID, problemBean.getTypeID());
			}
			return result;
		} else {
			// 更正错题状态
			int rows = recordDao.correction(id, userID);
			if (rows != 1) {
				statisticsDao.updateUserDoneByCorrect(userID, problemBean.getTypeID());
			}
			return result;
		}
	}
	
	
	public static boolean checkApplication(String expression1, String expression2, String expression3, String answer, ProblemBean problemBean) {
		if (answer.trim().equals(problemBean.getAnswer()) && expression1.trim().equals(problemBean.getExpression1()) && 
				expression2.trim().equals(problemBean.getExpression2()) && expression3.trim().equals(problemBean.getExpression3())) {
			return true;
		}
		return false;
	}
	
	/**根据题目ID取题目*/
	public ProblemBean getProblemByID(int id) {
		return problemDao.getProblemByID(id);
	}
}
