package com.grosup.practice.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grosup.practice.beans.UserBean;
import com.grosup.practice.dao.UserDao;
import com.grosup.practice.service.SessionService;

@Component  
public class PracticeUtil {
	
	private static Logger logger = Logger.getLogger(PracticeUtil.class);
	
	private static SessionService sessionService;
	
	private static PracticeUtil practiceUtil;
	
	private static UserDao userDao;
	
	public void init() {
		practiceUtil = this;  
		PracticeUtil.sessionService = this.sessionService;  
		PracticeUtil.userDao = this.userDao;
	}
	public void setSessionService(SessionService sessionService) {
		PracticeUtil.sessionService = sessionService;
	}
	
	public void setUserDao(UserDao userDao) {
		PracticeUtil.userDao = userDao;
	}
	/**
	 * 用绑定的唯一Id查询用户信息
	 * @param wxID
	 * @return
	 */
	public static UserBean getUser(String third_session) {
		String wxID = sessionService.getOpenIdByThirdSession(third_session);
		UserBean userBean = userDao.queryUserBywxID(wxID);
		return userBean;
	}
	/**
	 * 查询third_session记录验证登录状态
	 * @param third_session
	 * @return
	 */
	public static boolean checkThirdSession(String third_session) {
		logger.info("进入到checkThirdSession方法");
		return sessionService.checkThirdSession(third_session);
	}
	
}
