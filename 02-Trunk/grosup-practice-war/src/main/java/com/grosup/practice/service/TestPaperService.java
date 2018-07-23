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
import com.grosup.practice.util.GrosupException;

@Service
public class TestPaperService {
	
	@Autowired
	private TestPaperDao testPaperDao;
	@Autowired
	private ProblemDao problemDao;
	
	public List<TestPaperBean> queryTestPaperList(int gradeID) throws GrosupException{
		return testPaperDao.queryTestPaperList(gradeID);
	}
	
	public ProblemForTestBean getQuesUserUnDone(int quesPosition, String paperKey) throws GrosupException{
		return testPaperDao.getQuesUserUnDone(quesPosition, paperKey);
	}
	
	public QuesDetailBean getQuesUserHaveDone(int quesPosition, String paperKey, int userID) throws GrosupException{
		return testPaperDao.getQuesUserHaveDone(quesPosition, paperKey, userID);
	}
	
	public void userRecordAdd(QuesDetailBean quesDetailBean) throws GrosupException {
		//通过quesID查询题库表中题的数据
		ProblemBean problemBean = problemDao.getProblemByKey(quesDetailBean.getProblemKey());
		//判断是否答对了
		boolean result = true;
		// 如果题型是计算题
		if (!"App-Pr-Grade2".equals(problemBean.getKnowledgeKey())) {
			// 做题错误
			if (!quesDetailBean.getUserAnswer().trim().equals(problemBean.getAnswer())) {
				result = false;
			}
		} else {// 如果是应用题
			result = ProblemService.checkApplication(
					quesDetailBean.getExpression1(),
					quesDetailBean.getExpression2(),
					quesDetailBean.getExpression3(),
					quesDetailBean.getUserAnswer(), problemBean);
		}
		//如果答错了
		if (result) {
			quesDetailBean.setQuesToScore(quesDetailBean.getQuesScore()); 
		} else {
			quesDetailBean.setQuesToScore(0);
		}
		testPaperDao.userRecordAdd(quesDetailBean);
	}
	
	public void updateUserRecord(QuesDetailBean quesDetailBean) throws GrosupException {
		// 通过quesID查询题库表中题的数据
		ProblemBean problemBean = problemDao.getProblemByKey(quesDetailBean.getProblemKey());
		// 判断是否答对了
		boolean result = true;
		// 如果题型是计算题
		if (!"App-Pr-Grade2".equals(problemBean.getKnowledgeKey())) {
			// 做题错误
			if (!quesDetailBean.getUserAnswer().trim()
					.equals(problemBean.getAnswer())) {
				result = false;
			}
		} else {// 如果是应用题
			result = ProblemService.checkApplication(
					quesDetailBean.getExpression1(),
					quesDetailBean.getExpression2(),
					quesDetailBean.getExpression3(),
					quesDetailBean.getUserAnswer(), problemBean);
		}
		// 如果答错了
		if (result) {
			quesDetailBean.setQuesToScore(quesDetailBean.getQuesScore());
		} else {
			quesDetailBean.setQuesToScore(0);
		}
		testPaperDao.updateUserRecord(quesDetailBean);
	}
	
	public PaperInfoBean getPaperTotalInfo(int userID, String paperKey) throws GrosupException{
		return testPaperDao.getPaperTotalInfo(userID, paperKey);
	}
	
	/**
	 * 提交试卷之后将数据转移到历史表
	 * @param userID
	 * @param paperKey
	 */
	public void moveRecordToHistory(int userID, String paperKey) throws GrosupException {
		testPaperDao.removeUserTestData(userID, paperKey);
		testPaperDao.moveRecordToHistory(userID, paperKey);
	}
}
