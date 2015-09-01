package com.sdu.utils;

public class StaticVar {

	public static final String DB_URL = "jdbc:mysql://localhost/googleshop_db?useUnicode=true&characterEncoding=UTF-8";
	public static final String USER_NAME = "chen";
	public static final String DB_PASSWD = "0504zhao,";
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public static final String ERROR_LABEL = "error";
	public static final String APPS_LABEL = "apps";
	
	public static final String AD_LABEL = "adver";
	
	//表示没有错误
	public static final int ERROR_NO = 0;
	
	//表示数据库错误
	public static final int ERROR_SQL = 1;
	
	//表示有错误
	public static final int ERROR_ERROR = 2;
	
	//表示已经没有数据
	public static final int ERROR_NULL = 3;
	
	public static final String PAGE = "page";
	
	//当前的地址
	public static final String CURRENT_URL = "http://192.168.199.239:8080/file/";
}
