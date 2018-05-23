package com.grosup.practice.util;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public SqlSession getSession() {
		return this.sqlSession;
	}
}
