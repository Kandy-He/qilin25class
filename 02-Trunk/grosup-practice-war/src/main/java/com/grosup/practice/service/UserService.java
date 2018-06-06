package com.grosup.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	 * 人员注册
	 * @param user
	 * @return 
	 */
	public boolean userRegister(UserBean user) {
		return userDao.userRegister(user);
	}
	
	/**
	 * 注册审核通过
	 */
	public boolean userCheck(int userID) {
		return userDao.userCheck(userID);
	}
	/**
	 * 获取老师对应班级下所有学生信息
	 * @param userID 老师id
	 * @return
	 */
	public List<UserBean> queryStudents(int classID) {
		List<UserBean> list = userDao.queryStudents(classID);
		return list;
	}
	
	/**
	 * 获取未审核的老师信息
	 * @return
	 */
	public List<UserBean> queryTeachers() {
		List<UserBean> list = userDao.queryTeachers();
		return list;
	}
}
