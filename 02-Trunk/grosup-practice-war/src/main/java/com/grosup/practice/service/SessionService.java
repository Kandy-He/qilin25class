package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.SessionBean;
import com.grosup.practice.dao.SessionDao;

@Service
public class SessionService {
	
	@Autowired
	private SessionDao sessionDao;
	
	@SuppressWarnings("null")
	public boolean querySessionKey(String sessionKey) {
		SessionBean sessionBean = sessionDao.querySessionKey(sessionKey);
		if (null == sessionBean) {
			if (null == sessionBean.getSessionValue() || sessionBean.getSessionValue().equals("")) {
				return false;
			}
			return false;
		}
		return true;
	}
	
	public boolean insertSessionValue(SessionBean sessionBean) {
		return sessionDao.insertSessionValue(sessionBean);
	}
}
