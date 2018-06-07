package com.grosup.practice.controller;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.StudentBean;
import com.grosup.practice.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	private static Logger logger = Logger.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public JSONObject studentAdd(StudentBean student) {
		JSONObject result = new JSONObject();
		try {
			boolean status = studentService.studentAdd(student);
			if (status) {
				result.put("code", "success");
			} else {
				result.put("code", "fail");
			}
		} catch (Exception e) {
			logger.error("注册失败");
			e.printStackTrace();
			result.put("code", "error");
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.GET, value = "/selectTest")
	@ResponseBody
	public JSONObject selectTest() {
		JSONObject result = new JSONObject();
		try {
			StudentBean student = studentService.selectTest(1);
			if (null != student) {
				result.put("code", "success");
				result.put("data", student);
			} else {
				result.put("code", "fail");
			}
		} catch (Exception e) {
			logger.error("注册失败");
			e.printStackTrace();
			result.put("code", "error");
		} finally {
			return result;
		}
	}
}
