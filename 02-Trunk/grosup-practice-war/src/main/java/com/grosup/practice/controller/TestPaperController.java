package com.grosup.practice.controller;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.PaperInfoBean;
import com.grosup.practice.beans.ProblemBean;
import com.grosup.practice.beans.ProblemForTestBean;
import com.grosup.practice.beans.QuesDetailBean;
import com.grosup.practice.beans.TestPaperBean;
import com.grosup.practice.service.ProblemService;
import com.grosup.practice.service.TestPaperService;
import com.grosup.practice.util.ObjectUtil;

@Controller
@RequestMapping("/testPaper")
public class TestPaperController {
	
	private static Logger logger = Logger.getLogger(TestPaperController.class);
	
	@Autowired
	private TestPaperService testPaperService;
	@Autowired
	private ProblemService problemService;
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.GET, value = "queryList")
	@ResponseBody
	public JSONObject queryTestPaperList(@RequestParam int gradeID) {
		JSONObject result = new JSONObject();
		try {
			List<TestPaperBean> list = testPaperService.queryTestPaperList(gradeID);
			if (ObjectUtil.isNotNull(list)) {
				result.put("data", list);
			} else {
				result.put("data", "");
			}
			result.put("code", "success");
		} catch (Exception e) {
			result.put("code", "error");
			logger.error("查询试卷列表失败" , e);
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.GET, value = "getQues")
	@ResponseBody
	public JSONObject getQuesByPosition(@RequestParam int userID, @RequestParam int quesPosition, @RequestParam String paperKey) {
		JSONObject result = new JSONObject();
		try {
			//查询做题记录表是否已经做过跳回去修改的
			QuesDetailBean quesBean = testPaperService.getQuesUserHaveDone(quesPosition, paperKey, userID);
			if (ObjectUtil.isNotNull(quesBean)) {
				ProblemBean bean = problemService.getProblemByID(quesBean.getQuesID());
				bean.setExpression1(quesBean.getExpression1());
				bean.setExpression2(quesBean.getExpression2());
				bean.setExpression3(quesBean.getExpression3());
				result.put("data", bean);
				result.put("userAnswer", quesBean.getUserAnswer());
				result.put("quesScore", quesBean.getQuesScore());
				result.put("isDone", "Y");
			} else {
				ProblemForTestBean ques = testPaperService.getQuesUserUnDone(quesPosition, paperKey);
				if (ObjectUtil.isNotNull(ques)) {
					result.put("data", ques);
					result.put("isDone", "N");
				} else {
					result.put("data", "");
					result.put("isDone", "N");
				}
			}
			result.put("code", "success");
		} catch (Exception e) {
			result.put("code", "error");
			logger.error("获取试题失败" , e);
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.POST, value = "checkQues")
	@ResponseBody
	public JSONObject checkQues(@RequestParam int userID, @RequestParam String isDone, @RequestParam String paperKey,@RequestParam int quesID,@RequestParam int quesScore,
			@RequestParam int quesPosition,@RequestParam String expression1,@RequestParam String expression2,@RequestParam String expression3, @RequestParam String userAnswer) {
		JSONObject result = new JSONObject();
		try {
			//之前还没做过的题
			if ("N".equals(isDone)) {
				testPaperService.userRecordAdd(userID, isDone, paperKey, quesPosition, expression1, expression2, expression3, userAnswer, quesID, quesScore);
			} else {
				testPaperService.updateUserRecord(userID, isDone, paperKey, quesPosition, expression1, expression2, expression3, userAnswer, quesID, quesScore);
			}
			result.put("code", "success");
		} catch (Exception e) {
			result.put("code", "error");
			logger.error("提交失败" , e);
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(method = RequestMethod.GET, value = "paperSubmit")
	@ResponseBody
	public JSONObject paperSubmit(@RequestParam int userID, @RequestParam String paperKey) {
		JSONObject result = new JSONObject();
		try {
			//之前还没做过的题
			PaperInfoBean paperInfo = testPaperService.getPaperTotalInfo(userID, paperKey);
			testPaperService.moveRecordToHistory(userID, paperKey);
			result.put("data", paperInfo);
			result.put("code", "success");
		} catch (Exception e) {
			result.put("code", "error");
			logger.error("系统异常" , e);
		} finally {
			return result;
		}
	}
}
