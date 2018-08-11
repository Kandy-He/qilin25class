package com.grosup.practice.controller;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.grosup.practice.beans.GradeDetail;
import com.grosup.practice.service.TypeService;
import com.grosup.practice.util.CodeUtil;
import com.grosup.practice.util.GrosupException;
import com.grosup.practice.util.ObjectUtil;

/**
 * 知识点
 * @author 
 *
 */
@Controller
@RequestMapping("/knowledge")
public class TypeController {
	
	private static Logger logger = Logger.getLogger(TypeController.class);
	
	@Autowired
	private TypeService typeService;
	
	/**
	 * 获取知识点分类
	 * @param userID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "type")
	@ResponseBody
	public JSONObject queryKnowledges(@RequestParam int userID){
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		try {
			List<GradeDetail> knowledges = typeService.queryTypeDetail(userID);
			if (ObjectUtil.isNull(knowledges) || knowledges.size() == 0) {
				result.put("code", CodeUtil.NODATA);
				result.put("msg", "暂无数据");
				return result;
			}
			for (GradeDetail gradeDetail : knowledges) {
				data.add(JSONObject.fromObject(gradeDetail));
			}
			result.put("code", CodeUtil.SUCCESS);
			result.put("data", data);
		} catch (GrosupException e) {
			result.put("code", CodeUtil.ERROR);
			result.put("msg", "程序错误,请联系相关人员");
			logger.error("查询知识点分类失败", e);
		}
		return result;
	}
}
