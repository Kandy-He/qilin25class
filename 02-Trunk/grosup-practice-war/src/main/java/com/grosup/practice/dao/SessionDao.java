package com.grosup.practice.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.SessionBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class SessionDao extends AbstractDao{
	
	private static Logger logger = Logger.getLogger(SessionDao.class);
	
	/**
	 * 查询third_session是否存在
	 * @param third_session
	 * @return
	 */
	public boolean checkThirdSession(String third_session) {
		int rows =  this.getSession().selectOne("com.grosup.practice.session.checkThirdSession", third_session);
		logger.info(rows);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean insertSessionValue(SessionBean sessionBean) {
		int rows = this.getSession().insert("com.grosup.practice.session.insertSessionValue", sessionBean);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateThirdSession(SessionBean sessionBean) {
		int rows = this.getSession().insert("com.grosup.practice.session.updateThirdSession", sessionBean);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getOpenIdByThirdSession(String third_session) {
		SessionBean sessionBean = this.getSession().selectOne("com.grosup.practice.session.getOpenIdByThirdSession", third_session);
		if (null != sessionBean) {
			return sessionBean.getOpenId();
		} else {
			return "";
		}
	}
}
