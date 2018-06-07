package com.grosup.practice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.beans.SessionBean;
import com.grosup.practice.beans.UserBean;
import com.grosup.practice.service.SessionService;
import com.grosup.practice.service.StudentService;
import com.grosup.practice.util.AesCbcUtil;
import com.grosup.practice.util.HttpRequest;
import com.grosup.practice.util.PracticeUtil;

@RequestMapping("/login")
@Controller
public class WeixinInfo {
	
	private Logger logger = Logger.getLogger(WeixinInfo.class);
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private SessionService sessionService;
	/**
	 * @Title: decodeUserInfo
	 * @author��xuelifei
	 * @date��2018��3��25��
	 * @Description: �����û��������
	 * @param encryptedData
	 *            ����,�������
	 * @param iv
	 *            �����㷨�ĳ�ʼ����
	 * @param code
	 *            �û������¼�󣬻ص����ݻ���� code����Ч������ӣ�����������Ҫ�� code ���͵������߷�������̨��ʹ��code ��ȡ
	 *            session_key api���� code ���� openid �� session_key
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map decodeUserInfo(HttpServletRequest request,String encryptedData, String iv, String code) {
		
		HttpSession session = request.getSession();

		Map map = new HashMap();
		// ��¼ƾ֤����Ϊ��tt
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code 不合法");
			return map;
		}

		// С����Ψһ��ʶ (��΢��С��������̨��ȡ)
		String wxspAppid = "wxf1bac238f0e6a7f0";
		// С����� app secret (��΢��С��������̨��ȡ)
		String wxspSecret = "9bec3c2bcb203fcae846d6431401a004";
		// ��Ȩ�����
		String grant_type = "authorization_code";

		// ////////////// 1����΢�ŷ����� ʹ�õ�¼ƾ֤ code ��ȡ session_key �� openid
		// ////////////// ////////////////
		// �������
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret
				+ "&js_code=" + code + "&grant_type=" + grant_type;
		// ��������
		String sr = HttpRequest.sendGet(
				"https://api.weixin.qq.com/sns/jscode2session", params);
		// ������Ӧ���ݣ�ת����json����
		JSONObject json = JSONObject.fromObject(sr);
		// ��ȡ�Ự��Կ��session_key��
		String session_key = json.get("session_key").toString();
		// �û���Ψһ��ʶ��openid��
		String openid = (String) json.get("openid");

		// ////////////// 2����encryptedData������ݽ���AES���� ////////////////
		try {
			String result = AesCbcUtil.decrypt(encryptedData, session_key, iv,
					"UTF-8");
			if (null != result && result.length() > 0) {
				map.put("status", 1);
				map.put("msg", "成功");

				JSONObject userInfoJSON = JSONObject.fromObject(result);
				Map userInfo = new HashMap();
//				userInfo.put("openId", userInfoJSON.get("openId"));
				userInfo.put("nickName", userInfoJSON.get("nickName"));
				userInfo.put("gender", userInfoJSON.get("gender"));
				userInfo.put("city", userInfoJSON.get("city"));
				userInfo.put("province", userInfoJSON.get("province"));
				userInfo.put("country", userInfoJSON.get("country"));
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
				// ����unionId & openId;
				map.put("sessionID", session.getId());
				userInfo.put("unionId", userInfoJSON.get("unionId"));
				//将session保存数据库
//				SessionBean sessionBean = new SessionBean(session.getId(), session_key+"|"+openid);
//				sessionService.insertSessionValue(sessionBean);
				
//				***根据unionId去数据库查询用户是否注册，如果未注册，session返回用户标识未注册
//				并返回用户注册状态----未注册/注册还未通过审核，如果已经注册并通过审核，返回用户信息并写入session
				UserBean userBean = PracticeUtil.getUser((String) userInfoJSON.get("unionId"));
				if (userBean == null) {
					//用户为注册
					userInfo.put("studentStatus", "unRegister");
				} else if ("1".equals(userBean.getStatus())) {
					//用户已经注册还未通过审核
					userInfo.put("studentStatus", "unChecked");
				} else {
					userInfo.put("studentStatus", "checked");
					session.setAttribute("studentInfo", userBean);
				}
				map.put("userInfo", userInfo);
			} else {
				map.put("status", 0);
				map.put("msg", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//@RequestMapping(value = "/decode", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST,value = "decode")
	@ResponseBody
	public Map loginCheck(HttpServletRequest request, @RequestParam String code) {
		HttpSession session = request.getSession();
		logger.info(code);
		Map map = new HashMap();
		// 判断code是否合法
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code 不合法");
			return map;
		}

		// 微信小程序id
		String wxspAppid = "wxf1bac238f0e6a7f0";
		//微信 app secret (小程序秘钥)
		String wxspSecret = "9bec3c2bcb203fcae846d6431401a004";
		// 用户授权
		String grant_type = "authorization_code";

		// 请求微信接口 用code 获取 session_key 和 openid
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret
				+ "&js_code=" + code + "&grant_type=" + grant_type;
		// 发送请求
		String sr = HttpRequest.sendGet(
				"https://api.weixin.qq.com/sns/jscode2session", params);
		// ������Ӧ���ݣ�ת����json����
		JSONObject json = JSONObject.fromObject(sr);
		logger.info(json);
		// 会话密钥
		String session_key = json.get("session_key").toString();
		// 用户唯一标识
		String openid = (String) json.get("openid");
		//微信用户在开放平台的唯一标识符
//		String unionid = (String) json.get("unionId");

		// ////////////// 2����encryptedData������ݽ���AES���� ////////////////
		try {
				// ����unionId & openId;
				String third_session = session.getId();
				map.put("third_session", third_session);
				SessionBean sessionBean = new SessionBean(third_session, session_key, openid);
				if (sessionService.checkThirdSession(third_session)) {
					//更新third_session
				} else {
					//将session保存数据库
					sessionService.insertSessionValue(sessionBean);
				}
//				***根据unionId去数据库查询用户是否注册，如果未注册，session返回用户标识未注册
//				并返回用户注册状态----未注册/注册还未通过审核，如果已经注册并通过审核，返回用户信息并写入session
				UserBean userBean = PracticeUtil.getUser(openid);
				if (userBean == null) {
					//用户为注册
					map.put("userStatus", "unRegister");
				} else if ("1".equals(userBean.getStatus())) {
					//用户已经注册还未通过审核
					map.put("userStatus", "unChecked");
				} else {
					map.put("userStatus", "checked");
					session.setAttribute("userId", userBean.getId());
				}
				map.put("status", "success");
				map.put("msg", "校验登录成功");
		} catch (Exception e) {
			logger.error("校验登录失败");
		}
		return map;
	}
}
