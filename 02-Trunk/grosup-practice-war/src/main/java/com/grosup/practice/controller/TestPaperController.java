package com.grosup.practice.controller;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.service.ProblemService;
import com.grosup.practice.service.TestPaperService;
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;

@Controller
@RequestMapping("/testPaper")
public class TestPaperController {
	
	private static Logger logger = Logger.getLogger(TestPaperController.class);
	
	@Autowired
	private TestPaperService testPaperService;
	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(method = RequestMethod.GET, value = "queryList")
	@ResponseBody
	public JSONObject queryTestPaperList(@RequestParam int gradeID) {
		JSONObject result = new JSONObject();
		try {
			List<TestPaperBean> list = testPaperService.queryTestPaperList(gradeID);
			if (ObjectUtil.isNotNull(list)) {
				result.put("data", list);
				result.put("code", CodeUtil.SUCCESS);
			} else {
				result.put("code", CodeUtil.NODATA);
				result.put("msg", CodeUtil.NODATA_MSG);
				result.put("data", "");
			}
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			logger.error("查询试卷列表失败" , e);
		} 
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getQuesByPosition")
	@ResponseBody
	public JSONObject getQuesByPosition(@RequestParam int userID, @RequestParam int quesPosition, @RequestParam String paperKey) {
		JSONObject result = new JSONObject();
		try {
			//查询做题记录表是否已经做过跳回去修改的
			QuesDetailBean quesBean = testPaperService.getQuesUserHaveDone(quesPosition, paperKey, userID);
			if (ObjectUtil.isNotNull(quesBean)) {
				JSONObject data = new JSONObject();
//				data.put("id", quesBean.getId());
//				data.put("userID", quesBean.getUserID());
				data.put("problemKey", quesBean.getProblemKey());
				data.put("paperKey", quesBean.getPaperKey());
				data.put("quesPosition", quesBean.getQuesPosition());
				data.put("quesTypeKey", quesBean.getQuesTypeKey());
				data.put("expression1", quesBean.getExpression1());
				data.put("expression2", quesBean.getExpression2());
				data.put("expression3", quesBean.getExpression3());
				data.put("answerDesc", quesBean.getAnswerDesc());
				data.put("userAnswer", quesBean.getUserAnswer());
				data.put("description", quesBean.getDescription());
				data.put("quesScore", quesBean.getQuesScore());
				data.put("isDone", "Y");
				result.put("data", data);
				result.put("code", CodeUtil.SUCCESS);
			} else {
				ProblemForTestBean ques = testPaperService.getQuesUserUnDone(quesPosition, paperKey);
				if(ObjectUtil.isNull(ques)) {
					result.put("code", CodeUtil.NODATA);
					result.put("msg", CodeUtil.NODATA_MSG);
				}
				JSONObject data = JSONObject.fromObject(ques);
				data.put("isDone", "N");
				result.put("code", CodeUtil.SUCCESS);
				result.put("data", data);
			}
		} catch (Exception e) {
			result.put("code", CodeUtil.ERROR);
			result.put("msg", CodeUtil.ERROR_MSG);
			logger.error("获取试题失败" , e);
		} 
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "checkQues" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject checkQues(@RequestBody QuesDetailBean quesDetailBean) {
		JSONObject result = new JSONObject();
		try {
			//之前还没做过的题
			if ("N".equals(quesDetailBean.getIsDone())) {
				testPaperService.userRecordAdd(quesDetailBean);
			} else {
				testPaperService.updateUserRecord(quesDetailBean);
			}
			result.put("code", CodeUtil.SUCCESS);
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			logger.error("提交失败" , e);
		} 
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "paperSubmit")
	@ResponseBody
	public JSONObject paperSubmit(@RequestParam int userID, @RequestParam String paperKey) {
		JSONObject result = new JSONObject();
		try {
			//
			PaperInfoBean paperInfo = testPaperService.getPaperTotalInfo(userID, paperKey);
//			testPaperService.moveRecordToHistory(userID, paperKey);
			result.put("data", paperInfo);
			result.put("code", CodeUtil.SUCCESS);
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			logger.error("系统异常" , e);
		} 
		return result;
	}
}
