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

import com.grosup.practice.beans.GradeDetail;
import com.grosup.practice.service.TypeService;

@Controller
@RequestMapping("/type")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping(method = RequestMethod.GET,value = "detail")
	@ResponseBody
	public JSONObject test(@RequestParam int userID) throws IOException {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		List<GradeDetail> typeDetails = typeService.queryTypeDetail(userID);
		for (GradeDetail gradeDetail : typeDetails) {
			data.add(JSONObject.fromObject(gradeDetail));
		}
		result.put("code", "success");
		result.put("data", data);
		return result;
	}
}
