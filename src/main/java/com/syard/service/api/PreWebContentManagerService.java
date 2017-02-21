package com.syard.service.api;

import java.util.Map;


public interface PreWebContentManagerService {
	/**
	 * 前台页面获取关于我们数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getAboutUsCommponyProfile(Map<String, Object> param);
	/**
	 * 前台新闻中心页面获取数据
	 * @return
	 */
	Map<String, Object> getNewsCentorData();

}
