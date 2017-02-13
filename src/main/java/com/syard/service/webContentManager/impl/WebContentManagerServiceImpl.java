package com.syard.service.webContentManager.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syard.dao.CommodityDao;
import com.syard.pojo.Commodity;
import com.syard.service.webContentManager.WebContentManagerService;
import com.syard.vo.PageBean;

@Service
public class WebContentManagerServiceImpl implements WebContentManagerService{
	@Autowired
	private CommodityDao commodityDao;
	
	public Map<String, Object> getDataList(PageBean pageBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		Commodity cy = new Commodity();
		return result;
	}

}
