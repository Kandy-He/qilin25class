package com.grosup.practice.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.UserBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class UserDao extends AbstractDao{
	
	private static Logger logger = Logger.getLogger(UserDao.class);
	
	public UserBean queryTest(int id) {
		return this.getSession().selectOne("com.practice.test.queryTest", id);
	}
	/**
	 * 人员注册
	 */
	public boolean userRegister(UserBean user) {
		logger.info(user.getName());
		int rows = this.getSession().insert("com.grosup.practice.user.register", user);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 注册审核通过
	 */
	public boolean userCheck(int userID) {
		int rows = this.getSession().update("com.grosup.practice.user.userCheck", userID);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 获取老师对应班级下所有学生信息
	 * @param userID 老师id
	 * @return
	 */
	public List<UserBean> queryStudents(int classID) {
		return this.getSession().selectList("com.grosup.practice.user.queryStudents", classID);
	}
	/**
	 * 获取老师信息
	 * @return
	 */
	public List<UserBean> queryTeachers() {
		return this.getSession().selectList("com.grosup.practice.user.queryTeachers");
	}
	
	
	/**
	 * 根据wxID查询用户信息
	 * @param wxID
	 * @return
	 */
	public UserBean queryUserBywxID(String wxID) {
		return this.getSession().selectOne(
				"com.grosup.practice.user.queryUserBywxID", wxID);
	}
}
