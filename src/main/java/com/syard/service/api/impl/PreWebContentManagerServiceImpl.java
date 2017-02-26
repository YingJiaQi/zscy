package com.syard.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		int page = Integer.parseInt(param.get("pageIndex").toString());
		int rows = Integer.parseInt(param.get("pageSize").toString());
		int start = (page-1)*rows;
		int size = start + rows;
		List<Commodity> clist = commodityDao.getMagnetClassficationDataByName(start, size, param.get("categoryTitle").toString());
		Example example = new Example(Commodity.class);
		example.createCriteria().andLike("categoryName", "%"+param.get("categoryTitle").toString()+"%");
		int cCount = commodityDao.selectCountByExample(example);
		if(cCount>0){
			result.put("success", "true");
			result.put("datas",clist);
			result.put("total", cCount);
		}else{
			result.put("success", "false");
		}
		return result;
	}

	@Override
	public Map<String, Object> getMagnetDataByUsedName(Map<String, Object> param) {
		int page = Integer.parseInt(param.get("pageIndex").toString());
		int rows = Integer.parseInt(param.get("pageSize").toString());
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(Commodity.class);
		example.setOrderByClause("createTime DESC");
		example.createCriteria().andLike("usedType", "%"+param.get("usedFunction").toString()+"%");
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		
		//分页
		int size = 0;
		if((page-1)*rows > selectByExample.size()){
			//开始行大于总记录数，此时无数据
			size = 0;
		}else{
			if((page-1)*rows+rows < selectByExample.size()){
				//前面所有页数总和 + 当前页数 < 总记录数，
				size = (page-1)*rows+rows;
			}else{
				//此时取集合大小
				size = selectByExample.size();
			}
		}
		//分页
		List<Commodity> resultList = new ArrayList<Commodity>();
		for(int j =0; j < selectByExample.size(); j++){
			if(j >= (page-1)*rows && j <= size-1){
				resultList.add(selectByExample.get(j));
			}
		}
		
		
		if(selectByExample.size()>0){
			result.put("success", "true");
			result.put("datas",resultList);
			result.put("total", selectByExample.size());
		}else{
			result.put("success", "false");
		}
		return result;
	}

	@Override
	public Map<String, Object> getHeadlinePromoteData() {
		List<OtherSource> osList = new ArrayList<OtherSource>();
		//获取与新闻中心关联的数据
		Example example = new Example(PreSystemComponents.class);
		example.createCriteria().andEqualTo("moduleName", "新闻");
		List<PreSystemComponents> pscs = preSystemComponentsDao.selectByExample(example);
		//从tbl_pre_module_content_link表中获取关联资源集合，根据moduleID
		Example plinkExample = new Example(PreModuleContentLink.class);
		plinkExample.createCriteria().andEqualTo("moduleId", pscs.get(0).getId());
		List<PreModuleContentLink> plists = preModuleContentLinkDao.selectByExample(plinkExample);
		//遍历关联集合，从资源表中获取详细数据
		for(PreModuleContentLink ele: plists){
			OtherSource os = otherSourceDao.selectByPrimaryKey(ele.getSourceId());
			osList.add(os);
		}
		//遍历获取分类新闻
		List<Map<String, Object>> hotList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		List<OtherSource> newsList = new ArrayList<OtherSource>();
		int i = 0;
		int j = 0;
		for(OtherSource os:osList){
			if(os.getViewCount() == 1){
				//是热门新闻
				if(i<=8){
					map = new HashMap<String, Object>();
					map.put("sourceType", os.getSourceType());
					map.put("sourceTitle", os.getSourceTitle());
					map.put("sourceUrl", os.getSourceUrl());
					map.put("sourceContent", os.getSourceContent());
					map.put("viewCount", os.getViewCount());
					map.put("id", os.getId());
					hotList.add(map);
					i++;
				}
			}
			if(j<=8){
				newsList.add(os);
				j++;
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		/*List<OtherSource> hotList = otherSourceDao.getHotMsgData();
		List<OtherSource> newsList = otherSourceDao.getNewsMsgData();*/
		result.put("hotMsgData", hotList);
		result.put("newsMsgData", newsList);
		result.put("success", "true");
		return result;
	}

	@Override
	public Map<String, Object> getProductCentorData(Map<String, Object> param) {
		Map<String, Object>  result = new HashMap<String,Object>();
		List<Commodity> cList = new ArrayList<Commodity>();
		//先去关联表中取数据
		Example example = new Example(PreModuleContentLink.class);
		example.createCriteria().andEqualTo("moduleName", param.get("categoryName").toString());
		List<PreModuleContentLink> pList = preModuleContentLinkDao.selectByExample(example);
		//由于产品中心包含四个子类目，所以先取出产品中心的数据，然后再依次遍历，判断属于哪个类目
		for(PreModuleContentLink ele:pList){
			//因为该资源都是磁铁制品，所以只在commodity表中查找 
			Commodity selectByPrimaryKey = commodityDao.selectByPrimaryKey(ele.getSourceId());
			cList.add(selectByPrimaryKey);
		}
		result.put("success", "true");
		result.put("datas", cList);
		return result;
	}

	@Override
	public Map<String, Object> getOurAdviceData() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		//先到前后台关联表中查找关联数据
		//获取优势1数据
		getOurAdviceData(resultMap,  "优势1");
		//获取优势2数据
		getOurAdviceData(resultMap,  "优势2");
		//获取优势3数据
		getOurAdviceData(resultMap,  "优势3");
		//获取优势4数据
		getOurAdviceData(resultMap,  "优势4");
		result.put("success", "true");
		result.put("datas", resultMap);
		return result;
	}

	private void getOurAdviceData(List<Map<String, Object>> resultMap, String advanceParam) {
		
		Example advance_one = new Example(PreModuleContentLink.class);
		advance_one.createCriteria().andEqualTo("moduleName", advanceParam);
		List<PreModuleContentLink> advanceOneList = preModuleContentLinkDao.selectByExample(advance_one);
		if(advanceOneList.size() >0){
			Map<String, Object> map = new HashMap<String, Object>();
			for(PreModuleContentLink pcOne: advanceOneList){
				if(StringUtils.equals(pcOne.getSourceType(),"图片")){
					OtherSource osOne = otherSourceDao.selectByPrimaryKey(pcOne.getSourceId());
					map.put("picture", osOne);
				}else if(StringUtils.equals(pcOne.getSourceType(),"文档")){
					OtherSource osOnes = otherSourceDao.selectByPrimaryKey(pcOne.getSourceId());
					map.put("artical", osOnes);
				}
			}
			resultMap.add(map);
		}
	}

}
