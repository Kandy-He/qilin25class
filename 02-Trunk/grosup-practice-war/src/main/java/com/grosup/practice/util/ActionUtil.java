package com.grosup.practice.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 统一返回响应类
 * @author xuelifei
 */
public final class ActionUtil {
	
	public static void writeResponse(HttpServletResponse response, Object msg) {
		
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
