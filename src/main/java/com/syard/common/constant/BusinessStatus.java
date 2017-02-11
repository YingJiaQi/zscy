package com.syard.common.constant;

public class BusinessStatus {
	// success
	public static final String OK = "20000";
	// error
	public static final String ERROR = "40000";
	// 长度限制
	public static final String LENGTH_LIMIT = "10000";
	// 个数限制
	public static final String SIZE_LIMIT = "10001";
	// 违规操作
	public static final String ILLEGAL = "10002";
	// 禁止访问
	public static final String ACCESSDENIED = "10003";
	// 资源不存在
	public static final String NOTFIND = "10004";
	// 非空限制
	public static final String REQUIRE = "10005";
	//名称重复
	public static final String NAMEREPEAT = "10006";
	//参数错误
	public static final String PARAMETERERROR = "10007";
	
	//Admin禁止访问
	public static final String ADMINDENY = "50000";
	//普通用户禁止访问
	public static final String USERDENY = "50001";
	
	//收藏已经存在
	public static final String STOREEXSIST = "10008";

    //外部接口错误
    public static final String INTERFACE_ERROR = "60000";
    //版本冲突
    public static final String VERSION_CONFLICT = "60000";

    public static final String RESULT_TRUE = "true";
    public static final String RESULT_FALSE = "false";


    public static final String SESSION_INVALID = "11111";
}
