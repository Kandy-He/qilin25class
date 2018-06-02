package com.grosup.practice.util;

import com.grosup.practice.beans.UserBean;
import com.grosup.practice.dao.UserDao;
import com.grosup.practice.service.SessionService;

public class PracticeUtil {

	public static UserBean getUser(String wxID) {
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryUserBywxID(wxID);
		return userBean;
	}

	public static boolean checkSessionValue(String sessionKey) {
		SessionService sessionService = new SessionService();
		return sessionService.querySessionKey(sessionKey);
	}
}
