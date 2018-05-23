package com.grosup.practice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.grosup.practice.beans.User;
import com.grosup.practice.service.UserService;
import com.grosup.practice.util.ActionUtil;

@Controller
@RequestMapping("/user")
public class TestController {
	
	private Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET,value = "helloword")
	public void helloworld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write("welcome to practice");
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "test")
	public void test(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("imgUrl", "1");
		obj.put("achievement", "2");
		obj.put("flowerNum", "3");
		result.put("data", obj);
		result.put("code", "success");
		
		ActionUtil.writeResponse(response, result);
		
	}
	
	@RequestMapping(method = RequestMethod.POST,name= "register")
	public void register (@ModelAttribute("pojo")User user) throws IOException {
		//数据库执行完毕结果
		boolean status = true;
		try {
			int count = userService.userRegister(user);
			if (count != 1) {
				status = false;
			}
		} catch (Exception e) {
			logger.error("人员注册失败");
		} finally {

		}
	}
}
