package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.beans.TestPaperHaveDoneBean;
import com.grosup.practice.beans.TestWrongRecordBean;
import com.grosup.practice.util.AbstractDao;
import com.grosup.practice.util.GrosupException;

@Repository
public class TestPaperDao extends AbstractDao {
	
	public TestPaperBean queryTestPaper(String paperKey) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.test.queryTestPaper", paperKey);
	}
	
	public List<TestPaperBean> queryTestPaperList(int gradeID, int userID) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gradeID", gradeID);
		params.put("userID", userID);
		return this.getSession().selectList("com.grosup.practice.test.queryTestPaperList", params);
	}
	
	public ProblemForTestBean getQuesUserUnDone(int quesPosition, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quesPosition", quesPosition);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getQuesUserUnDone", params);
	}
	
	public QuesDetailBean getQuesUserHaveDone(int quesPosition, String paperKey, int userID) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quesPosition", quesPosition);
		params.put("paperKey", paperKey);
		params.put("userID", userID);
		return this.getSession().selectOne("com.grosup.practice.test.getQuesUserHaveDone", params);
	}
	
	public void userRecordAdd(QuesDetailBean quesDetailBean) throws GrosupException{
		this.getSession().insert("com.grosup.practice.test.userRecordAdd", quesDetailBean);
	}
	
	public void updateUserRecord(QuesDetailBean quesDetailBean) throws GrosupException{
		this.getSession().update("com.grosup.practice.test.updateUserRecord", quesDetailBean);
	}
	
	public TestPaperHaveDoneBean getPaperTotalInfo(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getPaperTotalInfo", params);
	}
	public int getUserScore(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getUserScore", params);
	}
	public int getDoneRight(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getDoneRight", params);
	}
	
	public void userPaperDoneInfoAdd(TestPaperHaveDoneBean infoBean) {
		this.getSession().insert("com.grosup.practice.test.userPaperDoneInfoAdd", infoBean);
	}
	

	/**
	 * 提交试卷之后将数据转移到历史表
	 * @param userID
	 * @param paperKey
	 */
	public void moveRecordToHistory(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		this.getSession().insert("com.grosup.practice.test.moveRecordToHistory", params);
	}
	/**
	 * 删除用户历史记录
	 * @param userID
	 * @param paperKey
	 * @throws GrosupException
	 */
	public void removeUserTestData(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		this.getSession().insert("com.grosup.practice.test.removeUserTestData", params);
	}
	/**
	 * 从试卷结果表获取学生已经做过的试卷信息
	 * @param userID
	 * @return
	 */
	public List<TestPaperHaveDoneBean> queryUserHaveDone(int userID) throws GrosupException{
		return this.getSession().selectList("com.grosup.practice.test.queryUserHaveDone", userID);
	}
	/**
	 * 记录答题开始时间
	 * @param userID
	 * @param paperKey
	 */
	public void userTestStartTime(int userID, String paperKey, String startTime, String endTime) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		this.getSession().insert("com.grosup.practice.test.userTestStartTime", params);
	}
	
	public String getTestEndTime(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getTestEndTime", params);
	}
	
	/**
	 * 增加一道错题
	 * @param record
	 * @return
	 */
	public boolean addRecord(TestWrongRecordBean record) throws GrosupException{
		boolean statu = false;
		int rows = this.getSession().insert("com.grosup.practice.testWrongRecord.addRecord",record);
		if (rows > 0) {
			statu = true;
		}
		return statu;
	}
	
	public List<TestWrongRecordBean> getWrongQues(int userID, String paperKey) throws GrosupException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		return this.getSession().selectList("com.grosup.practice.testWrongRecord.getWrongQues",params);
	}
	
	public RecordBean getOneRecord(int userID ,String paperKey ,int rownum) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("paperKey", paperKey);
		paramMap.put("rownum", rownum);
		return this.getSession().selectOne("com.grosup.practice.testWrongRecord.getOneRecord", paramMap);
	} 
	
	public int queryUserWrongCount(int userID, String paperKey) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.testWrongRecord.queryUserWrongCount", paramMap);
	}
	
	public void updateWrongStatus(Map<String, Object> paramMap) {
		this.getSession().update("com.grosup.practice.testWrongRecord.updateWrongStatus", paramMap);
	}
	
	public void removeRecord(int userID, String paperKey, String problemKey) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("paperKey", paperKey);
		paramMap.put("problemKey", problemKey);
		this.getSession().insert("com.grosup.practice.testWrongRecord.removeRecord",paramMap);
	}
}
