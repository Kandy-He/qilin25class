package com.grosup.practice.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class RecordDao extends AbstractDao {
	
	public boolean addRecord(RecordBean record) {
		boolean statu = false;
		int rows = this.getSession().insert("com.grosup.practice.record.addRecord",record);
		if (rows > 0) {
			statu = true;
		}
		return statu;
	}
	
	public RecordBean getOneRecord(int typeID, int userID) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("typeID", typeID);
		return this.getSession().selectOne("com.grosup.practice.record.getOneRecord", paramMap);
	}
	
	public boolean correction(int id, int userID) {
		boolean statu = false;
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userID", userID);
		paramMap.put("id", id);
		int rows = this.getSession().insert("com.grosup.practice.record.correction",paramMap);
		if (rows > 0) {
			statu = true;
		}
		return statu;
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
}
