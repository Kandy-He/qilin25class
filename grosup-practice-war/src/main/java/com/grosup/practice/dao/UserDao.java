package com.grosup.practice.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.User;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public User queryTest(int id) {
		return sqlSessionTemplate.selectOne("com.practice.test.queryTest", id);
	}
}
