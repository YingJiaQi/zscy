package com.syard.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.common.utils.CustomRuntimeException;
import com.syard.dao.CategoryDao;
import com.syard.dao.CommodityDao;
import com.syard.dao.CommodityDescDao;
import com.syard.dao.CommoditySpecificationContentDao;
import com.syard.dao.OtherSourceDao;
import com.syard.dao.PreModuleContentLinkDao;
import com.syard.dao.PreSystemComponentsDao;
import com.syard.pojo.Category;
import com.syard.pojo.Commodity;
import com.syard.pojo.CommodityDesc;
import com.syard.pojo.CommoditySpecificationContent;
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
	@Autowired
	private CommoditySpecificationContentDao commoditySpecificationContentDao;
	@Autowired
	private CommodityDescDao commodityDescDao;
	
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
		//圆形磁铁
		String circleMagnet = "圆柱磁铁,圆环磁铁,圆形沉头孔磁铁,圆片磁铁";
		//方形磁铁
		String quartzMagnet = "方形沉头孔磁铁,长方形磁铁,方形磁铁";
		//异形磁铁
		String yxMagnet = "大小头磁铁,梯形磁铁,异形磁铁,瓦形磁铁";
		//按用途分类
		String usedMagnet = "五金类,电机类,皮套类,喇叭磁铁,玩具类,真空模机类,LED磁铁,其它";
		
		String childTwo = "";//第二子节点
		//先找到该模块的ID
		String parentModuleName = param.get("parentModule").toString();//该模块名在数据库中是独一无二的
		Example parent = new Example(PreSystemComponents.class);
		parent.createCriteria().andEqualTo("moduleName", parentModuleName);
		List<PreSystemComponents> pscs = preSystemComponentsDao.selectByExample(parent);
		if(StringUtils.contains(circleMagnet, param.get("categoryName").toString())){
			childTwo = "圆形磁铁";
		}else if(StringUtils.contains(quartzMagnet, param.get("categoryName").toString())){
			childTwo = "方形磁铁";
		}else if(StringUtils.contains(yxMagnet, param.get("categoryName").toString())){
			childTwo = "异形磁铁";
		}else if(StringUtils.contains(usedMagnet, param.get("categoryName").toString())){
			childTwo = "按用途分类";
		}
			//根据顶级父类id，和次级父类模块名，查找到次级父类
		Example parentTwo = new Example(PreSystemComponents.class);
		parentTwo.createCriteria().andEqualTo("moduleParentId", pscs.get(0).getId()).andEqualTo("moduleName", childTwo);
		//parentTwo.createCriteria().andEqualTo("moduleName", childTwo);
		List<PreSystemComponents> parentTwoObject = preSystemComponentsDao.selectByExample(parentTwo);
			//根据二级父节点ID和目标模块名，获取目标模块ID
		Example selfModule = new Example(PreSystemComponents.class);
		selfModule.createCriteria().andEqualTo("moduleParentId", parentTwoObject.get(0).getId()).andEqualTo("moduleName", param.get("categoryName").toString());
		//selfModule.createCriteria().andEqualTo("moduleName", param.get("categoryName").toString());
		List<PreSystemComponents> selfObject = preSystemComponentsDao.selectByExample(selfModule);
		String moduleID = selfObject.get(0).getId();//获取该模块的ID
		
		//从tbl_pre_module_content_link表中获取关联资源集合，根据moduleID
		Example plinkExample = new Example(PreModuleContentLink.class);
		plinkExample.setOrderByClause("createTime DESC");
		plinkExample.createCriteria().andEqualTo("moduleId", moduleID);
		List<PreModuleContentLink> plists = preModuleContentLinkDao.selectByExample(plinkExample);
		
		if(pscs.size() <=0){
			new CustomRuntimeException("该模块暂时没有关联数据");
			return result;
		}
		
		//根据产品ID，到产品表中查找数据，该分类的数据，只存在commodity表中
		//List<Commodity> clist = commodityDao.getMagnetClassficationDataByName(start, size, param.get("categoryTitle").toString());
		List<Commodity> clist = new ArrayList<Commodity>();
		int i = 0;
		for(PreModuleContentLink ele:plists){
			i++;
			if(i>=start && i< size){
				Commodity comm = commodityDao.selectByPrimaryKey(ele.getSourceId());
				clist.add(comm);
			}
		}
		
		result.put("success", "true");
		result.put("datas",clist);
		result.put("total", clist.size());
		if(clist.size() <= 0){
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
		//先找到该模块的ID
		String parentModuleName = param.get("parentModule").toString();//该模块名在数据库中是独一无二的
		Example parent = new Example(PreSystemComponents.class);
		parent.createCriteria().andEqualTo("moduleName", parentModuleName);
		List<PreSystemComponents> pscs = preSystemComponentsDao.selectByExample(parent);
		String parentid = pscs.get(0).getId();
		Example child = new Example(PreSystemComponents.class);
		child.createCriteria().andEqualTo("moduleName", param.get("categoryName").toString()).andEqualTo("moduleParentId", parentid);
		//child.createCriteria().andEqualTo("moduleParentId", parentid);
		List<PreSystemComponents> pscsChild = preSystemComponentsDao.selectByExample(child);
		String objectModuleID = pscsChild.get(0).getId();
		//根据id去关联表中取数据
		Example example = new Example(PreModuleContentLink.class);
		example.createCriteria().andEqualTo("moduleId", objectModuleID);
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

	@Override
	public Map<String, Object> getCommodityDetailByID(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		String commodityId = param.get("id").toString();
		
		//从产品表获取主要信息
		Commodity comm = commodityDao.selectByPrimaryKey(commodityId);
		
		//从商品描述表中获取该商品的描述信息
		Example desc = new Example(CommodityDesc.class);
		desc.createCriteria().andEqualTo("commodityId", commodityId);
		List<CommodityDesc> descList = commodityDescDao.selectByExample(desc);
		CommodityDesc commodityDesc = descList.get(0);
		
		//从商品规格表中取出该 商品的规格参数
		Example spec = new Example(CommoditySpecificationContent.class);
		spec.createCriteria().andEqualTo("commodityId", commodityId);
		List<CommoditySpecificationContent> specList = commoditySpecificationContentDao.selectByExample(spec);
		
		result.put("commodityMain", comm);
		result.put("commodityDesc", commodityDesc);
		result.put("commoditySpec", specList);
		result.put("success", "true");
		return result;
	}

}
