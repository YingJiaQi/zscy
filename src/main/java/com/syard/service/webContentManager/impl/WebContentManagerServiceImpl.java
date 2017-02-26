package com.syard.service.webContentManager.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.entity.Example;
import com.syard.common.utils.PropsUtil;
import com.syard.dao.CommodityDao;
import com.syard.dao.OtherSourceDao;
import com.syard.dao.PreModuleContentLinkDao;
import com.syard.dao.PreSystemComponentsDao;
import com.syard.pojo.Commodity;
import com.syard.pojo.OtherSource;
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
	@Autowired
	private OtherSourceDao otherSourceDao;
	
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
		example.createCriteria().andNotEqualTo("isDel", 1);
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		for(Commodity ele:selectByExample){
			map = new HashMap<String, Object>();
			map.put("id",ele.getId());
			map.put("sourceType", "产品");
			map.put("sourceTitle", ele.getTitle());
			map.put("createTime", ele.getCreateTime());
			map.put("updateTime", ele.getUpdateTime());
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
		//获取其它数据信息
		Example os = new Example(OtherSource.class);
		os.createCriteria().andNotEqualTo("isDel", 1);
		List<OtherSource> osList = otherSourceDao.selectByExample(os);
		for(OtherSource ele: osList){
			map = new HashMap<String, Object>();
			map.put("id",ele.getId());
			map.put("sourceType", ele.getSourceType());
			map.put("sourceTitle", ele.getSourceTitle());
			map.put("createTime", ele.getCreateTime());
			map.put("updateTime", ele.getUpdateTime());
			//关联加选中
			if(associatedList != null && associatedList.size() > 0 ){
				for(PreModuleContentLink pm:associatedList){
					if(!StringUtils.equals(pm.getSourceType(), ele.getSourceType()))continue;
					if(StringUtils.equals(pm.getSourceTitle(), ele.getSourceTitle())){
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
		List<VEasyuiTree> root_child = new ArrayList<VEasyuiTree>();
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
			VEasyuiTree et_first = new VEasyuiTree();
			et_first.setId(pc.getId());
			et_first.setText(pc.getModuleName());
			//获取子元素
			Example exampleChild =  new Example(PreSystemComponents.class);
			exampleChild.createCriteria().andEqualTo("moduleParentId", pc.getId());
			List<PreSystemComponents> pc_first_list = preSystemComponentsDao.selectByExample(exampleChild);
			List<VEasyuiTree> et_first_child = null;
			if(pc_first_list.size()>0){
				et_first.setState("closed");
				et_first_child = new ArrayList<VEasyuiTree>();
				
				for(PreSystemComponents pc_first: pc_first_list){
					VEasyuiTree et_second = new VEasyuiTree();
					et_second.setId(pc_first.getId());
					et_second.setText(pc_first.getModuleName());
					//获取第二子元素
					Example exampleChilds =  new Example(PreSystemComponents.class);
					exampleChilds.createCriteria().andEqualTo("moduleParentId", pc_first.getId());
					List<PreSystemComponents> pc_second_list = preSystemComponentsDao.selectByExample(exampleChilds);
					List<VEasyuiTree> et_second_child = null;
					if(pc_second_list.size()>0){
						et_second.setState("closed");
						et_second_child = new ArrayList<VEasyuiTree>();
						for(PreSystemComponents pc_second: pc_second_list){
							VEasyuiTree et_third = new VEasyuiTree();
							et_third.setId(pc_second.getId());
							et_third.setText(pc_second.getModuleName());
							et_third.setChildren(null);
							et_second_child.add(et_third);
						}
					}
					et_second.setChildren(et_second_child);
					et_first_child.add(et_second);
				}
			}
			et_first.setChildren(et_first_child);
			root_child.add(et_first);
		}
		root.setChildren(root_child);
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
			//检查该关联是否已存在
			String moduleId = param.get("nodeId").toString();
			String moduleName = param.get("nodeName").toString();
			String sourceTitle = parseObject.get("title")+"";
			String sourceId = parseObject.get("id")+"";
			PreModuleContentLink pmc = preModuleContentLinkDao.getAssociatedData(moduleId,sourceId);
			if(pmc == null){
				//不存在时就添加
				PreModuleContentLink pmcl = new PreModuleContentLink();
				pmcl.setCreateTime(new Date());
				pmcl.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				pmcl.setIsDel(0);
				pmcl.setModuleId(moduleId);
				pmcl.setModuleName(moduleName);
				pmcl.setSourceId(sourceId);
				pmcl.setSourceTitle(sourceTitle);
				pmcl.setSourceType(parseObject.get("sourceType")+"");
				pmcl.setUpdateTime(pmcl.getCreateTime());
				preModuleContentLinkDao.insert(pmcl);
			}
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

	@Override
	public Map<String, String> addSourceData(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		String title = (String) param.get("title");
		//首先查看标题是否重复
		Example example = new Example(Commodity.class);
		example.createCriteria().andEqualTo("title", title);
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		Example os = new Example(OtherSource.class);
		os.createCriteria().andEqualTo("sourceTitle", title);
		List<OtherSource> selectByExample2 = otherSourceDao.selectByExample(os);
		if(selectByExample.size() > 0 || selectByExample2.size() >0){
			result.put("success", "false");
			result.put("msg", "标题名重复");
			return result;
		}
		
		String SourceData = (String)param.get("SourceData");
		SourceData = SourceData.replaceAll("\\|f\\|", "=");
		String dataType = param.get("sourceTypes").toString();
		//视频资源存储
		if(StringUtils.equals(dataType, "video")){
			String[] split = SourceData.split("<video");
			//储存视频地址
			String videoUrl = "";
			for(int i=0;i<split.length;i++){
				if(i == 1){
					int startVideoUrl = split[i].indexOf("src=\"");
					String urls = split[i].substring(startVideoUrl+5);
					videoUrl = urls.substring(0, urls.indexOf('"'));
				}
			}
			//文件重命名
			String oldVideoName = videoUrl.substring(videoUrl.lastIndexOf("/"));
			String newVideoName = UUID.randomUUID().toString().replaceAll("-", "");
			String newVideoUrl = PropsUtil.get("uploadVideoPath") +"/"+newVideoName+".mp4";
//			重新存储视频
			File DestVideoUrl = new File(PropsUtil.get("uploadVideoPath"));
			if(!DestVideoUrl.exists()){
				DestVideoUrl.mkdirs();
			}
			String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String rootPath = t.substring(0, t.indexOf("ZSCY")+5);
			File sourceVideoUrl = new File(rootPath+videoUrl);
			try {
				FileUtils.copyFile(sourceVideoUrl, new File(newVideoUrl));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//清空源视频
			try {
				FileUtils.deleteDirectory(new File(rootPath+"/ueditor/jsp/upload/video"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//保存视频信息
			OtherSource ose = new OtherSource();
			ose.setId(newVideoName);
			ose.setCreateTime(new Date());
			ose.setIsDel(0);
			ose.setSourceTitle(title);
			ose.setSourceType("视频");
			ose.setSourceUrl("videos/"+newVideoName+".mp4");
			ose.setUpdateTime(ose.getCreateTime());
			ose.setViewCount(param.get("hot").toString() == "1"? 1:null);//本是用来记录，文件被浏览次数，这是暂且用于标记是否是执门商品
			otherSourceDao.insert(ose);
		}else if(StringUtils.equals(dataType, "artical")){
		//文档文件存储
			OtherSource osa = new OtherSource();
			osa.setCreateTime(new Date());
			osa.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			osa.setIsDel(0);
			osa.setSourceContent(SourceData);
			osa.setSourceTitle(title);
			osa.setSourceType("文档");
			if(StringUtils.isNoneBlank(param.get("hot").toString())){
				osa.setViewCount(Integer.parseInt(param.get("hot").toString()));//本是用来记录，文件被浏览次数，这是暂且用于标记是否是执门商品
			}
			osa.setUpdateTime(osa.getCreateTime());
			otherSourceDao.insert(osa);
		} else if(StringUtils.equals(dataType, "picture")){
			//图片上传
			OtherSource ose = new OtherSource();
			ose.setCreateTime(new Date());
			ose.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			ose.setIsDel(0);
			ose.setSourceTitle(title);
			ose.setSourceType("图片");
			//用于存储商品图片信息
			List<Map<String, String>> lmap = new ArrayList<Map<String, String>>();
			String[] split = SourceData.split("img");
			for(int i=0;i<split.length;i++){
				Map<String, String> map = new HashMap<String, String>();
				if(split[i].contains("http") && !split[i].contains("dialogs")){
					//包含图片
					String path = split[i].substring(6, split[i].indexOf("title")-2);
					String substrings = split[i].substring(split[i].indexOf("title")+7);
					String titles = substrings.substring(0, substrings.indexOf('"'));
					map.put("path", path);
					map.put("title", titles);
					lmap.add(map);
				}
			}
			File destPicture = new File(PropsUtil.get("picturePath"));
			if(!destPicture.exists()){
				destPicture.mkdirs();
			}
			//遍历商品图片
			String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String rootPath = t.substring(0, t.indexOf("ZSCY")+5);
			int j =0;
			for(Map<String, String> img : lmap){
				if(j ==1){
					continue;
				}
				File sourcePicture = new File(rootPath+img.get("path").substring(img.get("path").indexOf("ZSCY")+5,img.get("path").length()));
				try {
					FileUtils.copyFile(sourcePicture, new File(destPicture+File.separator+ose.getId()+".png"));
					//删除原文件
					FileUtils.deleteQuietly(sourcePicture);
					j++;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			ose.setSourceUrl("pictures"+"/"+ose.getId()+".png");
			ose.setUpdateTime(ose.getCreateTime());
			otherSourceDao.insert(ose);
		}
		result.put("success", "true");
		result.put("msg", "添加成功");
		return result;
	}

	@Override
	public Map<String, String> deleteSourceDataById(Map<String, String> param) {
		Map<String, String> result = new HashMap<String, String>();
		String id = param.get("id").toString();
		OtherSource os = otherSourceDao.selectByPrimaryKey(id);
		if(os != null){
			os.setUpdateTime(new Date());
			os.setIsDel(1);
			os.setSourceTitle(os.getSourceTitle()+"deleted");
			int updateByPrimaryKeySelective = otherSourceDao.updateByPrimaryKey(os);
			if(updateByPrimaryKeySelective > 0){
				result.put("success", "true");
				result.put("msg", "删除成功");
			}else{
				result.put("msg", "删除失败");
			}
		}else{
			Commodity cy = commodityDao.selectByPrimaryKey(id);
			cy.setUpdateTime(new Date());
			cy.setIsDel(1);
			cy.setTitle(cy.getTitle()+"deleted");
			int updateByPrimaryKey = commodityDao.updateByPrimaryKey(cy);
			if(updateByPrimaryKey > 0){
				result.put("success", "true");
				result.put("msg", "删除成功");
			}else{
				result.put("msg", "删除失败");
			}
		}
		return result;
	}

}
