package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.util.AbstractDao;
import com.grosup.practice.util.GrosupException;

@Repository
public class RecordDao extends AbstractDao {
	/**
	 * 增加一道错题
	 * @param record
	 * @return
	 */
	public boolean addRecord(RecordBean record) {
		boolean statu = false;
		int rows = this.getSession().insert("com.grosup.practice.record.addRecord",record);
		if (rows > 0) {
			statu = true;
		}
		return statu;
	}
	/**
	 * 查询错题是否已经存在
	 * @param record
	 * @return
	 */
	public boolean checkIsExist(RecordBean record) {
		boolean status = false;
		int rows = this.getSession().selectOne("com.grosup.practice.record.checkIsExist",record);
		if (rows > 0) {
			status = true;
		}
		return status;
	}
	/**
	 * 更新错题记录
	 * @param record
	 */
	public void updateRecord(RecordBean record) {
		this.getSession().update("com.grosup.practice.record.updateRecord",record);
	}
	
	public RecordBean getOneRecord(int userID ,String knowledgeKey ,int rownum) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		paramMap.put("rownum", rownum);
		return this.getSession().selectOne("com.grosup.practice.record.getOneRecord", paramMap);
	}
	
	public int correction(int userID, String problemKey) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("problemKey", problemKey);
		return this.getSession().update("com.grosup.practice.record.correction",paramMap);
	}
	
	public boolean removeRecord(String problemKey, int userID) throws GrosupException{
		boolean statu = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("problemKey", problemKey);
		int rows = this.getSession().insert("com.grosup.practice.record.removeRecord",paramMap);
		if (rows > 0) {
			statu = true;
		}
		return statu;
	}
	
	public int queryUserWrongCount(int userID, String knowledgeKey) throws GrosupException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userID", userID);
		paramMap.put("knowledgeKey", knowledgeKey);
		return this.getSession().selectOne("com.grosup.practice.record.queryUserWrongCount",paramMap);
	}
}
