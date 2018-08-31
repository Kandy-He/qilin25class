package com.grosup.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.GradeDetail;
import com.grosup.practice.dao.TypeDao;
import com.grosup.practice.util.GrosupException;

@Service
public class TypeService {
	
	@Autowired
	private TypeDao typeDao;
	
	public List<GradeDetail> queryTypeDetail(int userID) throws GrosupException {
		return typeDao.queryTypeDetail(userID);
	}
}
