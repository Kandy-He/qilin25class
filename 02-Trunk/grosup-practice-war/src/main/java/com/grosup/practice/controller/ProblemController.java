package com.grosup.practice.controller;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.service.ProblemService;


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
		result.put("code", "success");
		result.put("data", JSONObject.fromObject(bean));
		return result;
	}
}
