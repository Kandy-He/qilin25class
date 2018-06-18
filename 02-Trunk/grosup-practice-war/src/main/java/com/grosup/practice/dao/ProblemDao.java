package com.grosup.practice.dao;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class ProblemDao extends AbstractDao {
	
	/**获得随机一道题*/
	public ProblemBean getRandomOne(int typeID) {
		return this.getSession().selectOne("com.grosup.practice.problem.getRandomOne", typeID);
	}
	
	/**根据题目ID取题目答案*/
	public String getAnswerByID(int id) {
		return this.getSession().selectOne("com.grosup.practice.problem.getAnswerByID", id);
	}
	
}
