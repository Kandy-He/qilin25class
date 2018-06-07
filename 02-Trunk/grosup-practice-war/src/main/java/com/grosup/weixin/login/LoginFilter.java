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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.grosup.practice.service.SessionService;
import com.grosup.practice.util.PracticeUtil;
import com.grosup.practice.util.StringUtil;

public class LoginFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(LoginFilter.class);
	
	@Autowired
	private SessionService sessionService;
	private String[] excludeUrls;

	public void init(FilterConfig filterConfig) throws ServletException {
		String excludeParam = filterConfig.getInitParameter("excludeurls");
		this.excludeUrls = excludeParam.split(",");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest sRequest = (HttpServletRequest) request;
		HttpServletResponse sResponse = (HttpServletResponse) response;
		//根据third_session判断用户是否登录
		String third_session = sRequest.getHeader("third_session");
		log.info(third_session);
		if (this.isExcludeUrl(sRequest)) {
			log.error("排除之外");
			chain.doFilter(sRequest, sResponse);
			return;
		}
		if (StringUtil.isEmpty(third_session)) {
			//重新登录
			sResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "授权不正确");
		} else {
			log.info("用third_session查询sessionKey，验证是否登录");
			//用third_session查询sessionKey，验证是否登录
			boolean login = PracticeUtil.checkThirdSession(third_session);
			if (login) {
				chain.doFilter(sRequest, sResponse);
			} else {
				sResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "授权不正确");
			}
		}
	}
	
	private boolean isExcludeUrl(HttpServletRequest request) {
		String url = request.getRequestURI();
		if (this.excludeUrls == null) {
			return false;
		}
		for (int i = 0; i < this.excludeUrls.length; i++) {
			String excludeUrl = this.excludeUrls[i];
			//本地为resource
			if (url.matches("/resource/login/decode.do")) {
				log.info("/practice/login/decode.do");
				return true;
			}
			if (url.matches(excludeUrl.replaceAll("\\*", "\\.\\*"))) {
				log.info("匹配到了");
				return true;
			}
		}
		return false;
	}
	
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
}
