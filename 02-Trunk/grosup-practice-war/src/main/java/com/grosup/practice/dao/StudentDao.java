package com.grosup.practice.dao;

import org.springframework.stereotype.Repository;

import com.grosup.practice.beans.Student;
import com.grosup.practice.util.AbstractDao;

@Repository
public class StudentDao extends AbstractDao {
	
	//学生注册
	public boolean studentAdd(Student student) {
		int rows = this.getSession().insert("com.grosup.practice.student.studentAdd", student);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//测试查询学生信息
	public Student selectTest(int id) {
		return this.getSession().selectOne("com.grosup.practice.student.selectTest", id);
	} 
}
