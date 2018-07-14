package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.dao.RecordDao;

@Service
public class RecordService {
	
	@Autowired
	private RecordDao recordDao;
	
	public RecordBean getOneRecord(int typeID, int userID, int rownum) {
		return recordDao.getOneRecord(typeID, userID, rownum);
	}
	/**
	 * 订正
	 * @param id
	 * @param userID
	 * @return
	 */
	public int correction(int id, int userID) {
		return recordDao.correction(id, userID);
	}
	
	public boolean removeRecord(int id, int userID) {
		return recordDao.removeRecord(id, userID);
	}
	/**
	 * 通过userID，typeID查询错题总数
	 * @param typeID
	 * @param userID
	 * @return 当前类型错题数
	 */
	public int queryUserWrongCountByTypeID(int typeID, int userID) {
		return recordDao.queryUserWrongCountByTypeID(typeID, userID);
	}
}
