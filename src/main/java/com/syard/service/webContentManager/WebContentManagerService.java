package com.syard.service.webContentManager;

import java.util.Map;

import com.syard.vo.PageBean;

public interface WebContentManagerService {
	/**
	 * 初始化加载数据
	 * @param pageBean
	 * @return
	 */
	Map<String, Object> getDataList(PageBean pageBean);

}
