package com.grosup.practice.util;

public class StringUtil {
	
	public static boolean isEmpty(String str) {
		if (null == str) {
			return true;
		}
		if (str.trim().equals("")) {
			return true;
		}
		return false;
	}
}
