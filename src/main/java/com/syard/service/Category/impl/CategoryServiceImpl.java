package com.syard.service.Category.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.abel533.entity.Example;
import com.syard.common.utils.CustomRuntimeException;
import com.syard.dao.CategoryDao;
import com.syard.dao.CategorySpecificationLinkDao;
import com.syard.pojo.Category;
import com.syard.pojo.CategorySpecificationLink;
import com.syard.service.BaseService;
import com.syard.service.Category.CategoryService;
import com.syard.vo.VEasyuiTree;

@Service
public class CategoryServiceImpl extends BaseService<Category> implements CategoryService{
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private CategorySpecificationLinkDao categorySpecificationLinkDao;
	
	public List<VEasyuiTree> getCategoryList() {
		List<VEasyuiTree> vtList = new ArrayList<VEasyuiTree>();
		List<VEasyuiTree> childList = new ArrayList<VEasyuiTree>();
		Example example = new Example(Category.class);
		example.createCriteria().andEqualTo("isDel", 0);
		List<Category> selectByExample = categoryDao.selectByExample(example);
		VEasyuiTree root = new VEasyuiTree();
		root.setId("1");
		root.setText("根");
		vtList.add(root);
		if(selectByExample.size() >0){
			for(Category ele:selectByExample){
				VEasyuiTree child = new VEasyuiTree();
				child.setId(ele.getId());
				child.setText(ele.getCategoryName());
				childList.add(child);
			}
			root.setChildren(childList);
		}
		return vtList;
	}
	public Map<String, Object> modifyNode(String id, String name,String parentId) {
		Map<String, Object> result = new HashMap<String, Object>();
		//如果id=2，则为添加
		if(StringUtils.equals(parentId, "1") && StringUtils.equals(id, "2")){
			//先查询名称是否重复
			List<Category> selectByExample = categoryDao.findCategoryByName(name);
			if(selectByExample.size() == 0){
				//不重复
				Category category = new Category();
				category.setCategoryName(name);
				category.setId((UUID.randomUUID()+"").replaceAll("-", ""));
				category.setCreateTime(new Date());
				category.setUpdateTime(category.getCreateTime());
				category.setIsDel(0);
				categoryDao.insert(category);
				result.put("msg", "添加成功");
				result.put("flag", true);
			}else{
				//重复报错
				result.put("msg", "类目名不能重复");
			}
		}else{
			//先查询名称是否重复
			List<Category> selectByExample = categoryDao.findCategoryByName(name);
			if(selectByExample.size() == 0 || (selectByExample.size() ==1 && selectByExample.get(0).getId().equals(id))){
				//更新
				Category category = new Category();
				category.setCategoryName(name);
				category.setId(id);
				category.setUpdateTime(new Date());
				categoryDao.updateByPrimaryKey(category);
				result.put("msg", "更新成功");
				result.put("flag", true);
			}else{
				result.put("msg", "类目名已存在");
			}
		}
		return result;
	}
	public Map<String, Object> removeNode(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String[] split = ids.split(",");
		for(int i=0 ; i < split.length; i++){
			//查看该节点是否已存在关联
			List<CategorySpecificationLink> findAssociatedList = categorySpecificationLinkDao.findByCategoryId(split[i]);
			if(findAssociatedList.size() >0){
				//说明有关联文件不能删除
				new CustomRuntimeException("存在关联不可以删除");
			}else{
				Category category = categoryDao.selectByPrimaryKey(split[i]);
				category.setIsDel(1);
				category.setUpdateTime(new Date());
				Example example = new Example(Category.class);
				example.createCriteria().andEqualTo("id", category.getId());
				categoryDao.updateByExample(category, example);
				result.put("msg", "删除成功");
				result.put("flag", true);
			}
		}
		return result;
	}
	public Map<String, Object> addAssociate(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String,Object>();
		String categoryName = param.get("nodeName").toString();
		String categoryId = param.get("nodeId").toString();
		String object = param.get("dataList").toString();
		
		//首先删除该节点的所有关联文件
		List<CategorySpecificationLink> findByCategoryId = categorySpecificationLinkDao.findByCategoryId(categoryId);
		if(findByCategoryId.size() >0){
			for(CategorySpecificationLink ele: findByCategoryId){
				categorySpecificationLinkDao.delete(ele);
			}
		}
		
		//添加新勾选的关联文件
		JSONArray parseArray = JSON.parseArray(object);
		@SuppressWarnings("rawtypes")
		Iterator iterator = parseArray.iterator();
		if(!iterator.hasNext()){
			//此时删除所有关联
			return null;
		}
		while(iterator.hasNext()){  
		    Object next = iterator.next();
		    com.alibaba.fastjson.JSONObject parseObject = JSON.parseObject(next.toString());
		    String specificationId = (String) parseObject.get("id");
		    String specificationName = (String) parseObject.get("title");
			CategorySpecificationLink csl = new CategorySpecificationLink();
			csl.setCreateTime(new Date());
			csl.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			csl.setCategoryId(categoryId);
			csl.setCategoryName(categoryName);
			csl.setCategorySpecificationId(specificationId);
			csl.setCateforySpecificationName(specificationName);
			csl.setIsDel(0);
			csl.setUpdateTime(new Date());
			categorySpecificationLinkDao.insert(csl);
		}  
		result.put("success", "true");
		return result;
	}
	public Map<String, Object> getAssociateListById(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(CategorySpecificationLink.class);
		example.createCriteria().andEqualTo("categoryId", param.get("id"));
		List<CategorySpecificationLink> selectByExample = categorySpecificationLinkDao.selectByExample(example);
		result.put("success", "true");
		result.put("docList", selectByExample);
		return result;
	}
	public Map<String, String> delAssociate(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		Example example = new Example(CategorySpecificationLink.class);
		example.createCriteria().andEqualTo("categoryId", param.get("categoryId").toString());
		example.createCriteria().andEqualTo("categorySpecificationId", param.get("specificationId").toString());
		//先查，取出规格名称
		List<CategorySpecificationLink> selectByExample = categorySpecificationLinkDao.selectByExample(example);
		//删除该条记录
		categorySpecificationLinkDao.deleteByExample(example);
		result.put("success", "true");
		result.put("msg", "删除成功");
		result.put("specificationName", selectByExample.get(0).getCateforySpecificationName());
		return result;
	}
	public List<VEasyuiTree> getCategoryListWithCommodity() {
		List<VEasyuiTree> vtList = new ArrayList<VEasyuiTree>();
		Example example = new Example(Category.class);
		example.createCriteria().andEqualTo("isDel", 0);
		List<Category> selectByExample = categoryDao.selectByExample(example);
		if(selectByExample.size() >0){
			for(Category ele:selectByExample){
				VEasyuiTree child = new VEasyuiTree();
				child.setId(ele.getId());
				child.setText(ele.getCategoryName());
				vtList.add(child);
			}
		}
		return vtList;
	}

}
