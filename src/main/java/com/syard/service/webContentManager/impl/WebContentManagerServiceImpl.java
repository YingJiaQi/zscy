package com.syard.service.webContentManager.impl;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.entity.Example;
import com.syard.dao.CommodityDao;
import com.syard.dao.PreModuleContentLinkDao;
import com.syard.dao.PreSystemComponentsDao;
import com.syard.pojo.Commodity;
import com.syard.pojo.PreModuleContentLink;
import com.syard.pojo.PreSystemComponents;
import com.syard.service.webContentManager.WebContentManagerService;
import com.syard.vo.PageBean;
import com.syard.vo.VEasyuiTree;

@Service
public class WebContentManagerServiceImpl implements WebContentManagerService{
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private PreSystemComponentsDao preSystemComponentsDao;
	@Autowired
	private PreModuleContentLinkDao preModuleContentLinkDao;
	
	public Map<String, Object> getDataList(PageBean pageBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		//slist用于存放所有资料数据
		List<Object> slist = new ArrayList<Object>();
		//是否传递了模块ID，若有则向后台查询该模块是否关联了资源，有则取出，用于后台管理页面初加载自动勾选其关联文件
		List<PreModuleContentLink> associatedList = null;
		if(StringUtils.isNoneBlank(pageBean.getNodeID())){
			Example example = new Example(PreModuleContentLink.class);
			example.createCriteria().andEqualTo("moduleId", pageBean.getNodeID());
			associatedList = preModuleContentLinkDao.selectByExample(example);
		}
		
		
		Map<String, Object> map;
		//首先获取商品信息
		Example example = new Example(Commodity.class);
		example.createCriteria().andNotEqualTo("status", 3);
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		for(Commodity ele:selectByExample){
			map = new HashMap<String, Object>();
			map.put("id",ele.getId());
			map.put("sourceType", "产品");
			map.put("sourceTitle", ele.getTitle());
			map.put("createTime", ele.getCreateTime());
			map.put("updateTime", ele.getUpdateTime());
			map.put("status", ele.getStatus());
			//关联加选中
			if(associatedList != null && associatedList.size() > 0 ){
				for(PreModuleContentLink pm:associatedList){
					if(!StringUtils.equals(pm.getSourceType(), "产品"))continue;
					if(StringUtils.equals(pm.getSourceTitle(), ele.getTitle())){
						map.put("checked", "true");
						break;
					}
				}
			}
			slist.add(map);
		}
		
		
		//获取视频数据
		//获取文档数据
		
		//分页
		int size = 0;
		if((pageBean.getPage()-1)*pageBean.getRows() > slist.size()){
			//开始行大于总记录数，此时无数据
			size = 0;
		}else{
			if((pageBean.getPage()-1)*pageBean.getRows()+pageBean.getRows() < slist.size()){
				//前面所有页数总和 + 当前页数 < 总记录数，
				size = (pageBean.getPage()-1)*pageBean.getRows()+pageBean.getRows();
			}else{
				//此时取集合大小
				size = slist.size();
			}
		}
		//分页
		List<Object> resultList = new ArrayList<Object>();
		for(int j =0; j < slist.size(); j++){
			if(j >= (pageBean.getPage()-1)*pageBean.getRows() && j <= size-1){
				resultList.add(slist.get(j));
			}
		}
		result.put("total", slist.size());
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<VEasyuiTree> getModuleList() {
		List<VEasyuiTree> resultList = new ArrayList<VEasyuiTree>();
		List<VEasyuiTree> lchild = new ArrayList<VEasyuiTree>();
		VEasyuiTree root = new VEasyuiTree();
		root.setId("2");
		root.setText("根");
		resultList.add(root);
		//获取所有父元素，也就是每个页面
		Example example =  new Example(PreSystemComponents.class);
		example.createCriteria().andEqualTo("moduleParentId", "");
		List<PreSystemComponents> selectByExample = preSystemComponentsDao.selectByExample(example);
		//遍历每个父元素，获取其所有子元素
		for(PreSystemComponents pc: selectByExample){
			VEasyuiTree vts = new VEasyuiTree();
			vts.setId(pc.getId());
			vts.setText(pc.getModuleName());
			//获取子元素
			Example exampleChild =  new Example(PreSystemComponents.class);
			exampleChild.createCriteria().andEqualTo("moduleParentId", pc.getId());
			List<PreSystemComponents> selectByExampleChild = preSystemComponentsDao.selectByExample(exampleChild);
			if(selectByExampleChild.size()>0)vts.setState("closed");
			List<VEasyuiTree> lchilds = new ArrayList<VEasyuiTree>();
			for(PreSystemComponents pcChild: selectByExampleChild){
				VEasyuiTree vtChild = new VEasyuiTree();
				vtChild.setId(pcChild.getId());
				vtChild.setText(pcChild.getModuleName());
				lchilds.add(vtChild);
			}
			vts.setChildren(lchilds);
			lchild.add(vts);
		}
		root.setChildren(lchild);
		return resultList;
	}

	@Override
	public Map<String, String> addAssociated(Map<String, Object> param) {
		Map<String, String> results = new HashMap<String, String>();
		JSONArray parseArray = JSONArray.parseArray(param.get("dataList").toString());
		//检查该模块是否已存在关联，有则清空关联，重新添加
		Example example = new Example(PreModuleContentLink.class);
		example.createCriteria().andEqualTo("moduleId", param.get("nodeId").toString());
		example.createCriteria().andEqualTo("moduleName", param.get("nodeName").toString());
		preModuleContentLinkDao.deleteByExample(example);
		
		Iterator<Object> iterator = parseArray.iterator();
		while(iterator.hasNext()){
			Object next = iterator.next();
			JSONObject parseObject = JSONObject.parseObject(next+"");
			PreModuleContentLink pmcl = new PreModuleContentLink();
			pmcl.setCreateTime(new Date());
			pmcl.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			pmcl.setIsDel(0);
			pmcl.setModuleId(param.get("nodeId").toString());
			pmcl.setModuleName(param.get("nodeName").toString());
			pmcl.setSourceId(parseObject.get("id")+"");
			pmcl.setSourceTitle(parseObject.get("title")+"");
			pmcl.setSourceType(parseObject.get("sourceType")+"");
			pmcl.setUpdateTime(pmcl.getCreateTime());
			preModuleContentLinkDao.insert(pmcl);
		}
		results.put("success", "true");
		return results;
	}

	@Override
	public Map<String, Object> getAssociatedListById(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(PreModuleContentLink.class);
		example.createCriteria().andEqualTo("moduleId", param.get("id").toString());
		List<PreModuleContentLink> selectByExample = preModuleContentLinkDao.selectByExample(example);
		result.put("success", "true");
		result.put("pmcl", selectByExample);
		return result;
	}

	@Override
	public Map<String, String> delAssociated(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		PreModuleContentLink selectByPrimaryKey = preModuleContentLinkDao.selectByPrimaryKey(param.get("sourceAssociatedID").toString());
		preModuleContentLinkDao.deleteByPrimaryKey(param.get("sourceAssociatedID").toString());
		result.put("success", "true");
		result.put("sourceTitle", selectByPrimaryKey.getSourceTitle());
		result.put("msg", "删除成功");
		return result;
	}

}
