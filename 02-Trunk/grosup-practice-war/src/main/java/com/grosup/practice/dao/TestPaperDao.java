package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class TestPaperDao extends AbstractDao {
	
	public List<TestPaperBean> queryTestPaperList(int gradeID) throws Exception {
		return this.getSession().selectList("com.grosup.practice.test.queryTestPaperList", gradeID);
	}
	
	public ProblemForTestBean getQuesUserUnDone(int quesPosition, String paperKey) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quesPosition", quesPosition);
		params.put("paperKey", paperKey);
		return this.getSession().selectOne("com.grosup.practice.test.getQuesUserUnDone", params);
	}
	
	public QuesDetailBean getQuesUserHaveDone(int quesPosition, String paperKey, int userID) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quesPosition", quesPosition);
		params.put("paperKey", paperKey);
		params.put("userID", userID);
		return this.getSession().selectOne("com.grosup.practice.test.getQuesUserHaveDone", params);
	}
	
	public void userRecordAdd(QuesDetailBean quesDetailBean) throws Exception{
		this.getSession().insert("com.grosup.practice.test.userRecordAdd", quesDetailBean);
	}
	
	public void updateUserRecord(QuesDetailBean quesDetailBean) throws Exception{
		this.getSession().update("com.grosup.practice.test.updateUserRecord", quesDetailBean);
	}
	
	public PaperInfoBean getPaperTotalInfo(int userID, String paperKey) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		PaperInfoBean infoBean = this.getSession().selectOne("com.grosup.practice.test.getPaperTotalInfo", params);
		int count = this.getSession().selectOne("com.grosup.practice.test.getPaperQuesCountUnRight", params);
		infoBean.setQuesCountUnRight(count);
		return infoBean;
	}
	/**
	 * 提交试卷之后将数据转移到历史表
	 * @param userID
	 * @param paperKey
	 */
	public void moveRecordToHistory(int userID, String paperKey) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", userID);
		params.put("paperKey", paperKey);
		this.getSession().insert("com.grosup.practice.test.moveRecordToHistory", params);
	}
}
