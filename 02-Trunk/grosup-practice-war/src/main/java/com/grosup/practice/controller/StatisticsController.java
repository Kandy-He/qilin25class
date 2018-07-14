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

import com.grosup.practice.beans.StatisticsBean;
import com.grosup.practice.service.StatisticsService;
import com.grosup.practice.util.ObjectUtil;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
private static Logger logger = Logger.getLogger(StatisticsController.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/userRank",produces="application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject studentAdd(@RequestParam String userID, @RequestParam String rank) {
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("userID", userID);
			params.put("rank", rank);
			int maxRank = statisticsService.getMaxRank();
			StatisticsBean statisticsBean = statisticsService.getUserRankInfo(params);
			if (ObjectUtil.isNull(statisticsBean)) {
				result.put("code", "success");
				result.put("data", data);
			}
			data.put("name", statisticsBean.getUserBean().getName());
			data.put("icon", statisticsBean.getUserBean().getIcon());
			data.put("flower", statisticsBean.getFlower());
			data.put("over", maxRank - statisticsBean.getRank());
			data.put("maxRank", maxRank);
			result.put("code", "success");
			result.put("data", data);
		} catch (Exception e) {
			logger.error("获取排名信息失败" + e);
			e.printStackTrace();
			result.put("code", "error");
		}
		return result; 
	}
}
