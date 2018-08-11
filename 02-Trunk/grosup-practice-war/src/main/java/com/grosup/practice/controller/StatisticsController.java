package com.grosup.practice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.KnowledgeDTO;
import com.grosup.practice.beans.PaperStatBean;
import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.service.StatisticsService;
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;

/**
 * 学生统计信息类
 * @author xuelifei
 *
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
private static Logger logger = Logger.getLogger(StatisticsController.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/userRank",produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject getUserRankInfo(@RequestParam String userID, @RequestParam String rank) {
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("userID", userID);
			params.put("rank", rank);
			int maxRank = statisticsService.getMaxRank();
			StatisticsBean statisticsBean = statisticsService.getUserRankInfo(params);
			if (ObjectUtil.isNull(statisticsBean) || maxRank == 0) {
				result.put("code", CodeUtil.NODATA);
				result.put("msg", "暂无排名信息");
			} else {
				result.put("code", "1");
				data.put("name", statisticsBean.getName());
				data.put("icon", statisticsBean.getIcon());
				data.put("flower", statisticsBean.getFlower());
				data.put("maxRank", maxRank);
				data.put("rank", statisticsBean.getRank());
				result.put("code", CodeUtil.SUCCESS);
				result.put("data", data);
			}
		} catch (GrosupException e) {
			logger.error("获取排名信息失败" ,e);
			result.put("code", CodeUtil.ERROR);
		}
		return result; 
	}
	/**
	 *  根据学生ID获取学生的练习模块统计情况 
	 * @param userID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/userQuesDoneInfo")
	@ResponseBody
	public JSONObject userQuesDoneInfo(@RequestParam int userID) {
		JSONObject result = new JSONObject();
		JSONObject quesTotalInfo = new JSONObject();
		JSONObject data = new JSONObject();
		try {
			StatisticsBean statBean = statisticsService.getQuesTotalStatInfo(userID);
			quesTotalInfo.put("quesTotalDone", statBean.getQuesTotalDone());
			quesTotalInfo.put("quesCorrectionRate", Math.ceil((double)statBean.getQuesOnceRightTotal()/(double)statBean.getQuesTotalDone()*100));
			quesTotalInfo.put("wrongTotal", statBean.getQuesTotalDone() - statBean.getQuesOnceRightTotal());
			quesTotalInfo.put("wrongCorrectionRate", "无");
			quesTotalInfo.put("wrongHighest", "无");
			data.put("quesTotalInfo", quesTotalInfo);
			//各知识点答题情况
			List<KnowledgeDTO> infoBeans = statisticsService.getStatInfoByUserID(userID);
			if (ObjectUtil.isNull(infoBeans) || infoBeans.size() == 0) {
				data.put("knowledgeInfo", "");
			} else {
				data.put("knowledgeInfo", infoBeans);
			}
			data.put("code", CodeUtil.SUCCESS);
			result.put("data", data);
		} catch (GrosupException e) {
			logger.error("获取统计信息失败" ,e);
			result.put("code", CodeUtil.ERROR);
		}
		return result; 
	}
	
	/**
	 *  根据学生ID获取学生的测试模块统计情况 
	 * @param userID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/userPaperDoneInfo")
	@ResponseBody
	public JSONObject userPaperDoneInfo(@RequestParam int userID) {
		JSONObject result = new JSONObject();
		JSONObject paperTotalInfo = new JSONObject();
		JSONObject data = new JSONObject();
		try {
			PaperStatBean paperStatBean = statisticsService.getPaperTotalStatInfo(userID);
			paperTotalInfo.put("paperDoneTotal", paperStatBean.getPaperDoneTotal());
			paperTotalInfo.put("scoreAvg", Math.ceil((double)paperStatBean.getScore()/(double)paperStatBean.getPaperDoneTotal()));
			paperTotalInfo.put("wrongTotal", paperStatBean.getWrongCount());
			paperTotalInfo.put("wrongCorrectionRate", "无");
			paperTotalInfo.put("wrongHighest", "无");
			data.put("paperTotalInfo", paperTotalInfo);
			//各知识点答题情况
			List<PaperStatBean> infoBeans = statisticsService.getPaperHaveDoneInfo(userID);
			if (ObjectUtil.isNull(infoBeans) || infoBeans.size() == 0) {
				data.put("paperHaveDoneInfo", "");
			} else {
				data.put("paperHaveDoneInfo", infoBeans);
			}
			data.put("code", CodeUtil.SUCCESS);
			result.put("data", data);
		} catch (GrosupException e) {
			logger.error("获取统计信息失败" ,e);
			result.put("code", CodeUtil.ERROR);
		}
		return result; 
	}
}
