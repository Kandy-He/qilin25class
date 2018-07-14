package com.grosup.practice.controller;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.service.ProblemService;
import com.grosup.practice.util.ObjectUtil;


@Controller
@RequestMapping("/problem")
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(method = RequestMethod.GET,value = "getRandomOne")
	@ResponseBody
	public JSONObject getRandomOne(@RequestParam int typeID) throws IOException {
		JSONObject result = new JSONObject();
		ProblemBean bean = problemService.getRandomOne(typeID);
		if(ObjectUtil.isNull(bean)) {
			result.put("data", "");
		} else {
			result.put("data", JSONObject.fromObject(bean));
		}
		result.put("code", "success");
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "checkAnswer")
	@ResponseBody
	public JSONObject checkAnswer(@RequestParam int id,@RequestParam String answer,@RequestParam int userID,
			@RequestParam String expression1,@RequestParam String expression2,@RequestParam String expression3) throws Exception {
		JSONObject result = new JSONObject();
		boolean checkResult = problemService.checkAnswer(id, answer, userID, expression1, expression2, expression3);
		if (checkResult) {
			result.put("data", 1);//做对了
		} else {
			result.put("data", 0);//做错了
		}
		result.put("code", "success");
		return result;
	}
}
