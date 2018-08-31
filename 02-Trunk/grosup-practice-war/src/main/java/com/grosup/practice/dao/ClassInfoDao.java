package com.grosup.practice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.ClassInfoBean;
import com.grosup.practice.util.AbstractDao;

@Repository
public class ClassInfoDao extends AbstractDao{
	
	public List<ClassInfoBean> queryClass() {
		return this.getSession().selectList("com.grosup.practice.class.queryClassInfo");
	}
}
