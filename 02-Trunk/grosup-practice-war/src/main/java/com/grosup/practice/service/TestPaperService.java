package com.grosup.practice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.beans.TestPaperHaveDoneBean;
import com.grosup.practice.beans.TestWrongRecordBean;
import com.grosup.practice.dao.ProblemDao;
import com.grosup.practice.dao.TestPaperDao;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;

@Service
public class TestPaperService {
	private static Logger logger = Logger.getLogger(TestPaperService.class);
	
	@Autowired
	private TestPaperDao testPaperDao;
	@Autowired
	private ProblemDao problemDao;
	
	public List<TestPaperBean> queryTestPaperList(int gradeID,int userID) throws GrosupException{
		return testPaperDao.queryTestPaperList(gradeID, userID);
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
			if (!quesDetailBean.getUserAnswer().trim().equals(problemBean.getAnswer().trim())) {
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
					.equals(problemBean.getAnswer().trim())) {
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
	@Transactional
	public TestPaperHaveDoneBean getPaperTotalInfo(int userID, String paperKey,int useTime) throws GrosupException{
		TestPaperHaveDoneBean infoBean = testPaperDao.getPaperTotalInfo(userID, paperKey);
		int userScore = testPaperDao.getUserScore(userID, paperKey);
		int doneRight = testPaperDao.getDoneRight(userID, paperKey);
		infoBean.setUserScore(userScore);
		infoBean.setDoneRight(doneRight);
		infoBean.setUseTime(useTime);
		infoBean.setIsDone("Y");
		infoBean.setUserID(userID);
		//将答题数据写入结果表
		testPaperDao.userPaperDoneInfoAdd(infoBean);
		addRecord(userID, paperKey);
		testPaperDao.moveRecordToHistory(userID, paperKey);
		testPaperDao.removeUserTestData(userID, paperKey);
		return infoBean;
	}
	
	/**
	 * 提交试卷之后将数据转移到历史表
	 * @param userID
	 * @param paperKey
	 */
	public void moveRecordToHistory(int userID, String paperKey) throws GrosupException {
		testPaperDao.moveRecordToHistory(userID, paperKey);
		testPaperDao.removeUserTestData(userID, paperKey);
	}
	/**
	 * 从试卷结果表获取学生已经做过的试卷信息
	 * @param userID
	 * @return
	 */
	public List<TestPaperHaveDoneBean> queryUserHaveDone(int userID) throws GrosupException{
		return testPaperDao.queryUserHaveDone(userID);
	}
	
	/**
	 * 记录答题开始时间
	 * @param userID
	 * @param paperKey
	 */
	public void userTestStartTime(int userID, String paperKey) throws GrosupException{
		TestPaperBean testPaperBean = testPaperDao.queryTestPaper(paperKey);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = sdf.format(now);
		String endTime = sdf.format(now.getTime() + 1000*60*(testPaperBean.getTimeLimit()));
		testPaperDao.userTestStartTime(userID, paperKey, startTime, endTime);
	}
	//判断是否超出了试卷时间限制
	public boolean checkIsTimeOut(int userID, String paperKey) throws GrosupException, ParseException {
		String endTimeStr = testPaperDao.getTestEndTime(userID, paperKey);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endTime = sdf.parse(endTimeStr);
		Date now = new Date();
		if (now.after(endTime)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 记录错题
	 * @param record
	 * @return
	 */
	private void addRecord(int userID, String paperKey) throws GrosupException {
		List<TestWrongRecordBean> list = testPaperDao.getWrongQues(userID, paperKey);
		for (TestWrongRecordBean testWrongRecordBean : list) {
			testPaperDao.addRecord(testWrongRecordBean);
		}
	}
	
	public RecordBean getOneRecord(int userID ,String paperKey ,int rownum) throws GrosupException {
		return testPaperDao.getOneRecord(userID, paperKey, rownum);
	}
	
	public int queryUserWrongCount(int userID, String paperKey) throws GrosupException {
		return testPaperDao.queryUserWrongCount(userID, paperKey);
	}
	
	/**检查是否做对
	 * @throws Exception */
	@Transactional
	public boolean checkAnswer(String problemKey, String paperKey,String answer, int userID, String expression1, 
			String expression2, String expression3) throws GrosupException {
		boolean result = true;
		ProblemBean problemBean = problemDao.getProblemByKey(problemKey);
		if (ObjectUtil.isNull(answer) || "".equals(answer)) {
			logger.error("答案不能为空");
			throw new GrosupException(-1, "答案不能为空");
		}
		//如果题型是计算题
		if (!"App-Pr-Grade2".equals(problemBean.getKnowledgeKey())) {
			// 做题错误
			if (!answer.trim().equals(problemBean.getAnswer().trim())) {
				result = false;
			}
		} else {// 如果是应用题
			result = ProblemService.checkApplication(expression1, expression2, expression3,
					answer, problemBean);
		}
		if (result) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userID", userID);
			param.put("paperKey", paperKey);
			param.put("problemKey", problemKey);
			param.put("expression1", expression1);
			param.put("expression2", expression2);
			param.put("expression3", expression3);
			param.put("userAnswer", answer);
			testPaperDao.updateWrongStatus(param);
		}
		return result;
	}
	
	public void removeRecord(int userID, String paperKey, String problemKey) throws GrosupException{
		testPaperDao.removeRecord(userID, paperKey, problemKey);
	}
}
