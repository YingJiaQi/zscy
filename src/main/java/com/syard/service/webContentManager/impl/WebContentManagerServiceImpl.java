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
		//获取其它数据信息
		Example os = new Example(OtherSource.class);
		os.createCriteria().andNotEqualTo("status", 3);
		List<OtherSource> osList = otherSourceDao.selectByExample(os);
		for(OtherSource ele: osList){
			map = new HashMap<String, Object>();
			map.put("id",ele.getId());
			map.put("sourceType", ele.getSourceType());
			map.put("sourceTitle", ele.getSourceTitle());
			map.put("createTime", ele.getCreateTime());
			map.put("updateTime", ele.getUpdateTime());
			map.put("status", ele.getStatus());
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
			ose.setSourceUrl(newVideoUrl);
			ose.setUpdateTime(ose.getCreateTime());
			ose.setStatus(2);
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
			osa.setStatus(2);
			osa.setUpdateTime(osa.getCreateTime());
			otherSourceDao.insert(osa);
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
			os.setStatus(3);
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
			cy.setStatus(3);
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
