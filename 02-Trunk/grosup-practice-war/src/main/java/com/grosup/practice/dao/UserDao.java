package com.grosup.practice.dao;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.UserBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class UserDao extends AbstractDao{
	
	public UserBean queryTest(int id) {
		return this.getSession().selectOne("com.practice.test.queryTest", id);
	}
	/**
	 * 人员注册
	 */
	public boolean userRegister(UserBean user) {
		int rows = this.getSession().insert("com.grosup.practice.user.register", user);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
}
