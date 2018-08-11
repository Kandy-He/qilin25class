package com.grosup.practice.controller;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.RecordBean;
import com.grosup.practice.service.RecordService;
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;


@Controller
@RequestMapping("/record")
public class RecordController {
	
	private static Logger logger = Logger.getLogger(RecordController.class);
	
	@Autowired
	private RecordService recordService;
	
	@RequestMapping(method = RequestMethod.GET,value = "getOneRecord")
	@ResponseBody
	public JSONObject getOneRecord(@RequestParam String knowledgeKey, @RequestParam int userID, @RequestParam int rownum) {
		JSONObject result = new JSONObject();
		try {
			RecordBean bean = recordService.getOneRecord(userID, knowledgeKey, rownum);
			if (ObjectUtil.isNull(bean)) {
				result.put("code", CodeUtil.NODATA);
				result.put("msg", CodeUtil.NODATA_MSG);
				return result;
			}
			int wrongCount = recordService.queryUserWrongCount(userID, knowledgeKey);
			result.put("code", CodeUtil.SUCCESS);
			result.put("wrongCount", wrongCount);
			result.put("data", JSONObject.fromObject(bean));
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			result.put("msg", CodeUtil.ERROR_MSG);
			logger.error("获取错题异常", e);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "removeRecord")
	@ResponseBody
	public JSONObject removeRecord(@RequestParam String problemKey, @RequestParam int userID) {
		JSONObject result = new JSONObject();
		try {
			boolean status = recordService.removeRecord(problemKey,userID);
			if (status) {
				result.put("code", CodeUtil.SUCCESS);
			} else {
				result.put("code", CodeUtil.ERROR);
			}
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			result.put("msg", CodeUtil.ERROR_MSG);
			logger.error("移除错题异常", e);
		}
		return result;
	}

}
