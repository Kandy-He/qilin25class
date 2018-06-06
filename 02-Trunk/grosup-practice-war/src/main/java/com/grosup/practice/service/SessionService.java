package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.SessionBean;
import com.grosup.practice.dao.SessionDao;

@Service
public class SessionService {
	
	@Autowired
	private SessionDao sessionDao;
	
	public boolean checkThirdSession(String third_session) {
		return sessionDao.checkThirdSession(third_session);
	}
	
	public boolean insertSessionValue(SessionBean sessionBean) {
		return sessionDao.insertSessionValue(sessionBean);
	}
	
	public boolean updateThirdSession(SessionBean sessionBean) {
		return sessionDao.updateThirdSession(sessionBean);
	}
	
	public String getOpenIdByThirdSession(String third_session) {
		return sessionDao.getOpenIdByThirdSession(third_session);
	}
}
