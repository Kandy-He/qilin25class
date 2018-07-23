package com.grosup.practice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.GradeDetail;
import com.grosup.practice.util.AbstractDao;
import com.grosup.practice.util.GrosupException;

@Repository
public class TypeDao extends AbstractDao {
	
	public List<GradeDetail> queryTypeDetail(int userID) throws GrosupException{
		return this.getSession().selectList("com.grosup.practice.type.queryKnowledges", userID);
	}
}
