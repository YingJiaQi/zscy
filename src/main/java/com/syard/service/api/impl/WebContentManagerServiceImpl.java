package com.syard.service.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.syard.service.api.WebContentManagerService;

public class WebContentManagerServiceImpl implements WebContentManagerService{

	@Override
	public Map<String, Object> getAboutUsCommponyProfile(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		String moduleName = param.get("moduleName").toString();
		String pageName = param.get("modulePage").toString();
		//取出关联的资源
		return null;
	}

}
