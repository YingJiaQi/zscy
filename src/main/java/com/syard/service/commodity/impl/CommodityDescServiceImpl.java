package com.syard.service.commodity.impl;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.common.utils.PropsUtil;
import com.syard.dao.CommodityDescDao;
import com.syard.pojo.CommodityDesc;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommodityDescService;
@Service
public class CommodityDescServiceImpl extends BaseService<CommodityDesc>  implements CommodityDescService {
	@Autowired
	private CommodityDescDao commodityDescDao;
	
	public Boolean addCommodityDesc(String commodityDesc,String commodityId) {
		//用于存储更新后的新图片路径加内容
		StringBuffer sourceContent = new StringBuffer();
		//图片新地址
		File DestcommodityImage = new File(PropsUtil.get("commodityImagePath")+File.separatorChar+commodityId+File.separator+"commodityDescImage");
		if(!DestcommodityImage.exists()){
			DestcommodityImage.mkdirs();
		}
		//保存商品描述图片
		List<Map<String, String>> lmap = new ArrayList<Map<String, String>>();
		String[] split = commodityDesc.split("img");
		for(int i=0;i<split.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			if(split[i].contains("http")){
				if(split[i].contains("dialogs")){
					sourceContent.append(split[i]+"img");
				}else{
					//包含图片
					String path = split[i].substring(6, split[i].indexOf("title")-2);
					String substrings = split[i].substring(split[i].indexOf("title")+7);
					String title = substrings.substring(0, substrings.indexOf('"'));
					map.put("path", path);
					map.put("title", title);
					lmap.add(map);
					sourceContent.append(split[i].substring(0, split[i].indexOf('"')+1)+DestcommodityImage+File.separator+title+" \""+split[i].substring(split[i].indexOf("title")-1)+"img");
				}
			}else{
				sourceContent.append(split[i]+"img");
			}
		}
		
		//遍历商品图片，重新存储描述信息中的图片
		for(Map<String, String> img : lmap){
			String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String rootPath = t.substring(0, t.indexOf("ZSCY")+5);
			File sourceCommodityImage = new File(rootPath+img.get("path").substring(img.get("path").indexOf("ZSCY")+5,img.get("path").length()));
			try {
				FileUtils.copyFile(sourceCommodityImage, new File(DestcommodityImage+File.separator+img.get("title")+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		CommodityDesc cd = new CommodityDesc();
		cd.setId((UUID.randomUUID()+"").replaceAll("-", ""));
		cd.setCreateTime(new Date());
		cd.setUpdateTime(cd.getCreateTime());
		cd.setContent(sourceContent.toString().substring(0, sourceContent.toString().length()-3));
		cd.setCommodityId(commodityId);
		Integer save = super.save(cd);
		return save >0 ? true:false;
	}


}
