package com.grosup.practice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.ClassInfoBean;
import com.grosup.practice.beans.UserBean;
import com.grosup.practice.service.ClassInfoService;
import com.grosup.practice.service.SessionService;
import com.grosup.practice.service.UserService;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;
import com.grosup.practice.util.PracticeUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private ClassInfoService classInfoService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/add",produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject userRegister(HttpServletRequest request ,@RequestBody UserBean userBean) {
		//打印出前台接收到的学生信息
		JSONObject result = new JSONObject();
		String third_session = request.getHeader("third_session");
		try {
			String openId = sessionService.getOpenIdByThirdSession(third_session);
			userBean.setWxID(openId);
			//TODO 判断是否是被拒绝之后再次提交
			boolean status = userService.userRegister(userBean);
			if (status) {
				result.put("code", "success");
			} else {
				result.put("code", "fail");
			}
		} catch (GrosupException e) {
			logger.error("注册失败", e);
			result.put("code", "error");
		} 
		return result;
	}
	/**
	 * 人员审核通过
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/check")
	@ResponseBody
	public JSONObject studentCheck(@RequestParam int userID) {
		JSONObject result = new JSONObject();
		boolean status = userService.userCheck(userID);
		if (status) {
			result.put("code", "success");
		} else {
			result.put("code", "fail");
		}
		return result;
	}
	/**
	 * 人员审核拒绝
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/checkRefused")
	@ResponseBody
	public JSONObject studentCheckRefused(@RequestParam int userID) {
		JSONObject result = new JSONObject();
		boolean status = userService.userCheckRefused(userID);
		if (status) {
			result.put("code", "success");
		} else {
			result.put("code", "fail");
		}
		return result;
	}
	/**
	 * 获取管理员权限下对应老师信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teachers")
	@ResponseBody
	public List<UserBean> queryTeachers() {
		List<UserBean> teachers = userService.queryTeachers();
		return teachers;
	}
	/**
	 * 获取老师对应班级所有学生信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/students")
	@ResponseBody
	public List<UserBean> queryStudents(@RequestParam int classID) {
		logger.info("传进来的班级ID" + classID);
		List<UserBean> teachers = userService.queryStudents(classID);
		return teachers;
	} 
	
	/**
	 * 通过wxID获取用户信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/info")
	@ResponseBody
	public JSONObject queryUserBywxId(HttpServletRequest request) {
		logger.info("进入到方法queryUserBywxId");
		JSONObject obj = new JSONObject();
		String third_session = request.getHeader("third_session");
		logger.info(third_session);
		try {
			UserBean userbean = PracticeUtil.getUser(third_session);
			if (ObjectUtil.isNull(userbean)) {
				//请求正常(成功)
				obj.put("code", "1");
				obj.put("msg", "当前用户未注册");
			} else {
				//请求正常(成功)
				obj.put("code", "2");
				obj.put("data", userbean);
			}
		} catch (Exception e) {
			obj.put("code", "0");
			obj.put("msg", "获取用户信息异常");
			logger.error(e);
		}
		return obj;
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
