package com.grosup.practice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.GradeDetail;
import com.grosup.practice.util.AbstractDao;

@Repository
public class TypeDao extends AbstractDao {
	
	public List<GradeDetail> queryTypeDetail(int userID) {
		return this.getSession().selectList("com.grosup.practice.type.queryTypeDetail", userID);
	}
}
