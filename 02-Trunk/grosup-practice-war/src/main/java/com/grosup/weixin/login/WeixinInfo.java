package com.grosup.weixin.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grosup.practice.util.AesCbcUtil;
import com.grosup.practice.util.HttpRequest;

public class WeixinInfo {
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
			map.put("msg", "code ����Ϊ��");
			return map;
		}

		// С����Ψһ��ʶ (��΢��С��������̨��ȡ)
		String wxspAppid = "wx18385lalalala";
		// С����� app secret (��΢��С��������̨��ȡ)
		String wxspSecret = "bef47459d81a6eflalalalal";
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
				map.put("msg", "���ܳɹ�");

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
//				userInfo.put("unionId", userInfoJSON.get("unionId"));
				map.put("userInfo", userInfo);
				//session保存
				session.setAttribute("sessionID", session_key+"|"+openid);
			} else {
				map.put("status", 0);
				map.put("msg", "����ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
