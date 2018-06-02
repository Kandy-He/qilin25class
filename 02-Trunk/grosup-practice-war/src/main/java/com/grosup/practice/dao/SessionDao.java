package com.grosup.practice.dao;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.SessionBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class SessionDao extends AbstractDao{
	
	public SessionBean querySessionKey(String sessionKey) {
		return this.getSession().selectOne("com.grosup.practice.session.querySessionKey", sessionKey);
	}
	
	public boolean insertSessionValue(SessionBean sessionBean) {
		int rows = this.getSession().insert("com.grosup.practice.session.insertSessionValue", sessionBean);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
}
