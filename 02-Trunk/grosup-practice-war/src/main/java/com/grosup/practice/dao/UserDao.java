package com.grosup.practice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.StudentBean;
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
	 * 获取老师对应班级下未注册的学生信息
	 * @param userID 老师id
	 * @return
	 */
	public List<UserBean> queryUserUnChecked(int userID) {
		return this.getSession().selectList("com.grosup.practice.user.queryUserUnChecked", userID);
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
