package com.grosup.weixin.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.grosup.practice.util.PracticeUtil;

public class LoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest sRequest = (HttpServletRequest) request;
		HttpServletResponse sResponse = (HttpServletResponse) response;
		HttpSession session = sRequest.getSession();
		//根据sessionID判断用户是否登录
		String sessionID = (String) session.getId();
		if (null == sessionID) {
			//重新登录
			return;
		} else {
			//用sessionID查询sessionKey，验证是否登录
			boolean login = PracticeUtil.checkSessionValue(sessionID);
			if (login) {
				chain.doFilter(sRequest, sResponse);
			}
			return;
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
	
}
