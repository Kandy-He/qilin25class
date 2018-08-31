package com.grosup.practice.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.util.AbstractDao;
import com.grosup.practice.util.GrosupException;

@Repository
public class ProblemDao extends AbstractDao {
	
	/**获得随机一道题*/
	public ProblemBean getRandomOne(Map<String, Object> queryParam) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.problem.getRandomOne", queryParam);
	}
	
	/**根据题目ID取题目答案*/
	public String getAnswerByKey(String problemKey) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.problem.getAnswerByKey", problemKey);
	}
	/**根据题目key取题目*/
	public ProblemBean getProblemByKey(String problemKey) throws GrosupException{
		return this.getSession().selectOne("com.grosup.practice.problem.getProblemByKey", problemKey);
	}
	
}
