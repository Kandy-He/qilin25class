package com.grosup.practice.controller;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.StudentBean;
import com.grosup.practice.beans.UserBean;
import com.grosup.practice.service.StudentService;
import com.grosup.practice.service.UserService;

public class UserController {
	
private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public JSONObject studentAdd(UserBean userBean) {
		JSONObject result = new JSONObject();
		try {
			boolean status = userService.userRegister(userBean);
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
}
