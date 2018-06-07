package com.grosup.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.ClassInfoBean;
import com.grosup.practice.dao.ClassInfoDao;

@Service
public class ClassInfoService {
	
	@Autowired
	private ClassInfoDao classInfoDao;
	
	public List<ClassInfoBean> queryClass() {
		return classInfoDao.queryClass();
	}
}
