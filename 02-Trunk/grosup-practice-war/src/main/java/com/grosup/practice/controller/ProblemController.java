package com.grosup.practice.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.service.ProblemService;
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;


@Controller
@RequestMapping("/problem")
public class ProblemController {
	
	private static Logger logger = Logger.getLogger(ProblemController.class);
	
	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(method = RequestMethod.GET,value = "getRandomOne")
	@ResponseBody
	//TODO 可以在第二个参数加非必须属性
	public JSONObject getRandomOne(@RequestParam String knowledgeKey, @RequestParam String quesTypeKey) {
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		try {
			queryParams.put("knowledgeKey", knowledgeKey);
			queryParams.put("quesTypeKey", quesTypeKey);
			ProblemBean bean = problemService.getRandomOne(queryParams);
			if(ObjectUtil.isNull(bean)) {
				result.put("code", CodeUtil.NODATA);
				result.put("msg", CodeUtil.NODATA_MSG);
				return result;
			}
			data.put("problemKey", bean.getProblemKey());
			data.put("knowledgeKey", bean.getKnowledgeKey());
			data.put("quesTypeKey", bean.getQuesTypeKey());
			data.put("description", bean.getDescription());
			data.put("answerDesc", bean.getAnswerDesc());
			result.put("data", data);
			result.put("code", CodeUtil.SUCCESS);
		} catch (GrosupException e) {
			logger.error("获取题目失败", e);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "checkAnswer")
	@ResponseBody
	public JSONObject checkAnswer(@RequestParam String problemKey,@RequestParam String answer,@RequestParam int userID,
			@RequestParam String expression1,@RequestParam String expression2,@RequestParam String expression3) {
		JSONObject result = new JSONObject();
		try {
			boolean checkResult = problemService.checkAnswer(problemKey, answer, userID, expression1, expression2, expression3);
			if (checkResult) {
				result.put("data", 1);//做对了
			} else {
				result.put("data", 0);//做错了
			}
			result.put("code", CodeUtil.SUCCESS);
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			result.put("msg", e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
