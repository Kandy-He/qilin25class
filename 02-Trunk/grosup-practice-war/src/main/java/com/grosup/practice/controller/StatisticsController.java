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
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;

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
}
