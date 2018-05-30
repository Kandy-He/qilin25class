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

import com.grosup.practice.beans.StudentBean;

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
		Object obj = session.getAttribute("sessionID");
		if (null == obj) {
			//重新登录
		} else {
			this.afterLogin(sRequest, sResponse);
			chain.doFilter(sRequest, sResponse);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	//判断用户，并刷新用户角色
	private void afterLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		StudentBean student = (StudentBean) session.getAttribute("user");
		if (null == student) {
			session.setAttribute("user", student);
		}
	}
}
