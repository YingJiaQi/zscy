package com.syard.service.Category;

import java.util.List;
import java.util.Map;


import com.syard.vo.VEasyuiTree;

public interface CategoryService {
	//获取所有类目
	public List<VEasyuiTree> getCategoryList();
	//类目变动
	public Map<String, Object> modifyNode(String id, String name,String parentId);
	//删除类目
	public Map<String, Object> removeNode(String string);
	//商品类目添加规格关联
	public Map<String, Object> addAssociate(Map<String, Object> param);
	//根据类目的id到类目规格关联表中查找所有的记录
	public Map<String, Object> getAssociateListById(Map<String, Object> param);
	//删除关联
	public Map<String, String> delAssociate(Map<String, Object> param);
	//商品添加页面，获取所有类目
	public List<VEasyuiTree> getCategoryListWithCommodity();
	
}
