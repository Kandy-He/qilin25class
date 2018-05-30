package com.grosup.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grosup.practice.beans.StudentBean;
import com.grosup.practice.dao.StudentDao;

@Service
public class StudentService {
	
	@Autowired
	private StudentDao studentDao;
	
	//学生注册
	public boolean studentAdd(StudentBean student) {
		return studentDao.studentAdd(student);
	}
	
	//查询测试
	public StudentBean selectTest(int id) {
		return studentDao.selectTest(id);
	}
}
