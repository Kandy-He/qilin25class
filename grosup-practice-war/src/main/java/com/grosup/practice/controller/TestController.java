package com.grosup.practice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grosup.practice.beans.User;
import com.grosup.practice.service.UserService;

@Controller
public class TestController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("helloword")
	public void helloworld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write("welcome to practice");
	}
	
	@RequestMapping("test")
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		User user = userService.queryTest(1);
		response.getWriter().write(user.toString());
	}
}
