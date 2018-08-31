package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.dao.RecordDao;
import com.grosup.practice.util.GrosupException;

@Service
public class RecordService {
	
	@Autowired
	private RecordDao recordDao;
	
	public RecordBean getOneRecord(int userID ,String knowledgeKey ,int rownum) throws GrosupException {
		return recordDao.getOneRecord(userID, knowledgeKey, rownum);
	}
	
	public boolean removeRecord(String problemKey, int userID) throws GrosupException {
		return recordDao.removeRecord(problemKey, userID);
	}
	
	public int queryUserWrongCount(int userID, String knowledgeKey) throws GrosupException {
		return recordDao.queryUserWrongCount(userID, knowledgeKey);
	}
}
