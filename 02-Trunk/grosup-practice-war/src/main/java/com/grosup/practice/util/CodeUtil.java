package com.grosup.practice.util;

public class CodeUtil {
	//请求成功且数据正常返回
	public static final int SUCCESS = 1;
	//请求成功但由于其他原因无数据
	public static final int NODATA = 1;
	//请求失败(程序异常)
	public static final int ERROR = -1;
	
	public static final String NODATA_MSG = "请求成功但由于其他原因无数据";
	
	public static final String ERROR_MSG = "请求失败(程序异常)";
}
