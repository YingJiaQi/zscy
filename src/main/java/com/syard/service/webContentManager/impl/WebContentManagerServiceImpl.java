package com.syard.service.webContentManager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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

}
