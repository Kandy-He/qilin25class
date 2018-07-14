package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.util.AbstractDao;

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
	
	public RecordBean getOneRecord(int typeID, int userID, int rownum) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		paramMap.put("rownum", rownum);
		return this.getSession().selectOne("com.grosup.practice.record.getOneRecord", paramMap);
	}
	
	public int correction(int id, int userID) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("id", id);
		return this.getSession().update("com.grosup.practice.record.correction",paramMap);
	}
	
	public boolean removeRecord(int id, int userID) {
		boolean statu = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("id", id);
		int rows = this.getSession().insert("com.grosup.practice.record.removeRecord",paramMap);
		if (rows > 0) {
			statu = true;
		}
		return statu;
	}
	/**
	 * 通过userID，typeID查询错题总数
	 * @param typeID
	 * @param userID
	 * @return 当前类型错题数
	 */
	public int queryUserWrongCountByTypeID(int typeID, int userID) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		return this.getSession().selectOne("com.grosup.practice.record.queryUserWrongCountByTypeID",paramMap);
	}
}
