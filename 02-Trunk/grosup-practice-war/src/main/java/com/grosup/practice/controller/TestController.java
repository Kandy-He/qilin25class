package com.grosup.practice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.ClassInfoBean;
import com.grosup.practice.service.ClassInfoService;
import com.grosup.practice.service.UserService;

@Controller
@RequestMapping("/user")
public class TestController {
	
	private Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ClassInfoService classInfoService;
	
	@RequestMapping(method = RequestMethod.GET,value = "helloword")
	public void helloworld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write("welcome to practice");
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "test")
	@ResponseBody
	public JSONObject test(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("imgUrl", "51");
		obj.put("achievement", "2");
		obj.put("flowerNum", "3");
		result.put("data", obj);
		result.put("code", "success");
		
		return result;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "query")
	@ResponseBody
	public JSONObject queryClass() throws IOException {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		List<ClassInfoBean> list = classInfoService.queryClass();
		for (ClassInfoBean classInfoBean : list) {
			JSONObject obj = new JSONObject();
			obj.put("classID", classInfoBean.getClassBean().getId());
			obj.put("className", classInfoBean.getClassBean().getName());
			obj.put("gradeID", classInfoBean.getClassBean().getGradeID());
			obj.put("gradeName", classInfoBean.getGradeBean().getName());
			data.add(obj);
		}
		
		result.put("data", data);
		result.put("code", "success");
		return result;
		
	}
}
