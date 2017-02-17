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
	/**
	 * 根据前台功能模块ID获取其关联的数据
	 * @param param 前台功能模块ID
	 * @return
	 */
	Map<String, Object> getAssociatedListById(Map<String, Object> param);
	/**
	 * 根据模块ID和数据ID，删除关联
	 * @param param
	 * @return
	 */
	Map<String, String> delAssociated(Map<String, Object> param);

}
