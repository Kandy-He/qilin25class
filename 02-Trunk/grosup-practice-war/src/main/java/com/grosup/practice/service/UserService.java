package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.StudentBean;
import com.grosup.practice.beans.UserBean;
import com.grosup.practice.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public UserBean queryTest(int id) {
		return userDao.queryTest(id);
	}
	
	/**
	 * ��Աע��
	 * @param user
	 * @return �仯����
	 */
	public int userRegister(UserBean user) {
		return userDao.userRegister(user);
	}
}
