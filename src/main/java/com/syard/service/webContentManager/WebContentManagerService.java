package com.syard.service.webContentManager;

import java.util.List;
import java.util.Map;

import com.syard.vo.PageBean;
import com.syard.vo.VEasyuiTree;

public interface WebContentManagerService {
	/**
	 * 初始化加载数据
	 * @param pageBean
	 * @return
	 */
	Map<String, Object> getDataList(PageBean pageBean);
	/**
	 * 初始加载前台模块数据
	 * @return
	 */
	List<VEasyuiTree> getModuleList();
	/**
	 * 添加关联
	 * @return
	 */
	Map<String, String> addAssociated(Map<String, Object> param);

}
