package com.syard.service.user;

import java.util.List;

import com.syard.vo.VEasyuiTree;

public interface SystemComponentsService {
	/**
	 * 获取所有的菜单权限列表
	 * @return
	 */
	public List<VEasyuiTree> getComponentList(String userId);
	
	/**
	 * 分配菜单权限
	 * @param userId
	 * @param componentId
	 */
	public void assignUserAuthor(String userId,List<String> componentId);
	/**
	 * 获取管理员的menu.json
	 * @param userid
	 * @return
	 */
	public List<?> getMenuJsonByUserId(String userid);
	
}
