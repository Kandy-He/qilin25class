package com.grosup.practice.dao;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.User;
import com.grosup.practice.util.AbstractDao;

@Repository
public class UserDao extends AbstractDao{
	
	public User queryTest(int id) {
		return this.getSession().selectOne("com.practice.test.queryTest", id);
	}
	/**
	 * 人员注册
	 * 参数：人员bean
	 */
	public int userRegister(User user) {
		return this.getSession().insert("com.grosup.practice.user.register", user);
	}
}
