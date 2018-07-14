package com.grosup.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.dao.ProblemDao;
import com.grosup.practice.dao.TestPaperDao;

@Service
public class TestPaperService {
	
	@Autowired
	private TestPaperDao testPaperDao;
	@Autowired
	private ProblemDao problemDao;
	
	public List<TestPaperBean> queryTestPaperList(int gradeID) throws Exception {
		return testPaperDao.queryTestPaperList(gradeID);
	}
	
	public ProblemForTestBean getQuesUserUnDone(int quesPosition, String paperKey) throws Exception{
		return testPaperDao.getQuesUserUnDone(quesPosition, paperKey);
	}
	
	public QuesDetailBean getQuesUserHaveDone(int quesPosition, String paperKey, int userID) throws Exception {
		return testPaperDao.getQuesUserHaveDone(quesPosition, paperKey, userID);
	}
	
	public void userRecordAdd(int userID, String isDone, String paperKey,int quesPosition,
			String expression1,String expression2,String expression3, String userAnswer, int quesID, int quesScore) throws Exception {
		//通过quesID查询题库表中题的数据
		ProblemBean problemBean = problemDao.getProblemByID(quesID);
		//判断是否答对了
		boolean result = true;
		//如果题型是计算题calculateQues
		if ("calculateQues".equals(problemBean.getProKey())) {
			//做题错误
			if (!userAnswer.trim().equals(problemBean.getAnswer())) {
				//答错了，得分0
				result = false;
			} 
		} else if("applicationQues".equals(problemBean.getProKey())) {//如果是应用题
			result = ProblemService.checkApplication(expression1, expression2, expression3, userAnswer, problemBean);
		}
		
		QuesDetailBean quesDetailBean = new QuesDetailBean();
		quesDetailBean.setUserID(userID);
		quesDetailBean.setPaperKey(paperKey);
		quesDetailBean.setQuesPosition(quesPosition);
		quesDetailBean.setQuesID(quesID);
		quesDetailBean.setExpression1(expression1);
		quesDetailBean.setExpression2(expression2);
		quesDetailBean.setExpression3(expression3);
		quesDetailBean.setUserAnswer(userAnswer);
		quesDetailBean.setAnswer(problemBean.getAnswer());
		quesDetailBean.setAnswerDesc(problemBean.getAnswerDesc());
		quesDetailBean.setQuesScore(quesScore);
		quesDetailBean.setQuesToScore(result == true ? quesScore:0); 
		testPaperDao.userRecordAdd(quesDetailBean);
	}
	
	public void updateUserRecord(int userID, String isDone, String paperKey,int quesPosition,
			String expression1,String expression2,String expression3, String userAnswer, int quesID, int quesScore) throws Exception {
		//通过quesID查询题库表中题的数据
		ProblemBean problemBean = problemDao.getProblemByID(quesID);
		// 判断是否答对了
		boolean result = true;
		// 如果题型是计算题calculateQues
		if ("calculateQues".equals(problemBean.getProKey())) {
			// 做题错误
			if (!userAnswer.trim().equals(problemBean.getAnswer())) {
				// 答错了，得分0
				result = false;
			}
		} else if ("applicationQues".equals(problemBean.getProKey())) {// 如果是应用题
			result = ProblemService.checkApplication(expression1, expression2,
					expression3, userAnswer, problemBean);
		}
		QuesDetailBean quesDetailBean = new QuesDetailBean();
		quesDetailBean.setUserID(userID);
		quesDetailBean.setExpression1(expression1);
		quesDetailBean.setExpression2(expression2);
		quesDetailBean.setExpression3(expression3);
		quesDetailBean.setUserAnswer(userAnswer);
		quesDetailBean.setQuesToScore(result == true ? quesScore:0); 
		quesDetailBean.setPaperKey(paperKey);
		quesDetailBean.setQuesPosition(quesPosition);
		testPaperDao.updateUserRecord(quesDetailBean);
	}
	
	public PaperInfoBean getPaperTotalInfo(int userID, String paperKey) throws Exception {
		return testPaperDao.getPaperTotalInfo(userID, paperKey);
	}
	
	/**
	 * 提交试卷之后将数据转移到历史表
	 * @param userID
	 * @param paperKey
	 */
	public void moveRecordToHistory(int userID, String paperKey) throws Exception {
		testPaperDao.moveRecordToHistory(userID, paperKey);
	}
}
