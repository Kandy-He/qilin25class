package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.dao.RecordDao;

@Service
public class RecordService {
	
	@Autowired
	private RecordDao recordDao;
	
	public RecordBean getOneRecord(int typeID, int userID) {
		return recordDao.getOneRecord(typeID, userID);
	}
	/**
	 * 订正
	 * @param id
	 * @param userID
	 * @return
	 */
	public boolean correction(int id, int userID) {
		return recordDao.correction(id, userID);
	}
	
	public boolean removeRecord(int id, int userID) {
		return recordDao.removeRecord(id, userID);
	}
}
