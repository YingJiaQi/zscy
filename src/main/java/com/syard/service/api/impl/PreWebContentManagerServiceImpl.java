package com.syard.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.dao.CategoryDao;
import com.syard.dao.CommodityDao;
import com.syard.dao.OtherSourceDao;
import com.syard.dao.PreModuleContentLinkDao;
import com.syard.dao.PreSystemComponentsDao;
import com.syard.pojo.Category;
import com.syard.pojo.Commodity;
import com.syard.pojo.OtherSource;
import com.syard.pojo.PreModuleContentLink;
import com.syard.pojo.PreSystemComponents;
import com.syard.service.api.PreWebContentManagerService;

@Service
public class PreWebContentManagerServiceImpl implements PreWebContentManagerService{
	@Autowired
	private PreSystemComponentsDao preSystemComponentsDao;
	@Autowired
	private PreModuleContentLinkDao preModuleContentLinkDao;
	@Autowired
	private OtherSourceDao otherSourceDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private CommodityDao commodityDao;
	
	@Override
	public Map<String, Object> getAboutUsCommponyProfile(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<OtherSource> osList = new ArrayList<OtherSource>();
		//取出关联的资源
		//先获取该模块的id
		String moduleID = "";
		Example example = new Example(PreSystemComponents.class);
		example.createCriteria().andEqualTo("moduleName", param.get("modulePage").toString());
		List<PreSystemComponents> selectByExample = preSystemComponentsDao.selectByExample(example);
		if(selectByExample.size() == 1){
			Example ee = new Example(PreSystemComponents.class);
			ee.createCriteria().andEqualTo("moduleName", param.get("moduleName").toString()).andEqualTo("moduleParentId", selectByExample.get(0).getId());
			moduleID = preSystemComponentsDao.selectByExample(ee).get(0).getId();
		}
		//从tbl_pre_module_content_link表中获取关联资源集合，根据moduleID
		Example plinkExample = new Example(PreModuleContentLink.class);
		plinkExample.createCriteria().andEqualTo("moduleId", moduleID);
		plinkExample.createCriteria().andEqualTo("moduleName",  param.get("moduleName").toString());
		List<PreModuleContentLink> plists = preModuleContentLinkDao.selectByExample(plinkExample);
		//遍历关联集合，从资源表中获取详细数据
		for(PreModuleContentLink ele: plists){
			OtherSource os = otherSourceDao.selectByPrimaryKey(ele.getSourceId());
			osList.add(os);
		}
		if(osList.size() >0){
			result.put("success", "true");
			result.put("datas", osList);
		}else{
			result.put("success", "false");
		}
		return result;
	}

	@Override
	public Map<String, Object> getNewsCentorData() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<OtherSource> osList = new ArrayList<OtherSource>();
		//取出关联的资源
		//先获取该模块的id
		String moduleID = "";
		Example example = new Example(PreSystemComponents.class);
		example.createCriteria().andEqualTo("moduleName", "新闻");
		List<PreSystemComponents> selectByExample = preSystemComponentsDao.selectByExample(example);
		moduleID = selectByExample.get(0).getId();
		//从tbl_pre_module_content_link表中获取关联资源集合，根据moduleID
		Example plinkExample = new Example(PreModuleContentLink.class);
		plinkExample.createCriteria().andEqualTo("moduleId", moduleID);
		List<PreModuleContentLink> plists = preModuleContentLinkDao.selectByExample(plinkExample);
		//遍历关联集合，从资源表中获取详细数据
		for(PreModuleContentLink ele: plists){
			OtherSource os = otherSourceDao.selectByPrimaryKey(ele.getSourceId());
			osList.add(os);
		}
		if(osList.size() >0){
			result.put("success", "true");
			result.put("datas", osList);
		}else{
			result.put("success", "false");
		}
		return result;
	}

	@Override
	public Map<String, Object> getMagnetClassficationData() {
		Map<String, Object> result = new HashMap<String,Object>();
		Example example = new Example(Category.class);
		example.setOrderByClause("componentpriority ASC");
		List<Category> selectByExample = categoryDao.selectByExample(example);
		result.put("success", "true");
		result.put("datas", selectByExample);
		return result;
	}

	@Override
	public Map<String, Object> getMagnetClassficationDataByTitle(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(Commodity.class);
		example.setOrderByClause("createTime DESC");
		example.createCriteria().andLike("categoryName", "%"+param.get("categoryTitle").toString()+"%");
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		if(selectByExample.size()>0){
			result.put("success", "true");
			result.put("datas",selectByExample);
		}else{
			result.put("success", "false");
		}
		return result;
	}

	@Override
	public Map<String, Object> getMagnetDataByUsedName(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(Commodity.class);
		example.setOrderByClause("createTime DESC");
		example.createCriteria().andLike("usedType", "%"+param.get("usedFunction").toString()+"%");
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		if(selectByExample.size()>0){
			result.put("success", "true");
			result.put("datas",selectByExample);
		}else{
			result.put("success", "false");
		}
		return result;
	}

}
