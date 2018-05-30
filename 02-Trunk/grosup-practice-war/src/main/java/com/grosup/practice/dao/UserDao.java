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
	 * ��Աע��
	 * ������Աbean
	 */
	public int userRegister(UserBean user) {
		return this.getSession().insert("com.grosup.practice.user.register", user);
	}
}
