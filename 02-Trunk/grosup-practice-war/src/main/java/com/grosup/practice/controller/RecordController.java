package com.grosup.practice.controller;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.service.RecordService;
import com.grosup.practice.util.ObjectUtil;


@Controller
@RequestMapping("/record")
public class RecordController {
	
	@Autowired
	private RecordService recordService;
	
	@RequestMapping(method = RequestMethod.GET,value = "getOneRecord")
	@ResponseBody
	public JSONObject getOneRecord(@RequestParam int typeID, @RequestParam int userID, @RequestParam int rownum) throws IOException {
		JSONObject result = new JSONObject();
		RecordBean bean = recordService.getOneRecord(typeID, userID, rownum);
		int count = recordService.queryUserWrongCountByTypeID(typeID, userID);
		result.put("code", "success");
		result.put("wrongCount", count);
		if (ObjectUtil.isNull(bean)) {
			result.put("data", "");
		} else {
			result.put("data", JSONObject.fromObject(bean));
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "removeRecord")
	@ResponseBody
	public JSONObject removeRecord(@RequestParam int id, @RequestParam int userID) throws IOException {
		JSONObject result = new JSONObject();
		boolean status = recordService.removeRecord(id, userID);
		if (status) {
			result.put("code", "success");
		} else {
			result.put("code", "error");
		}
		return result;
	}

}
