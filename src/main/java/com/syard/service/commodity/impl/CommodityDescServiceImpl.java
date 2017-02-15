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
		//保存商品描述图片
		List<Map<String, String>> lmap = new ArrayList<Map<String, String>>();
		String[] split = commodityDesc.split("src");
		for(int i=0;i<split.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			if(split[i].contains("http")){
				//包含图片
				String path = split[i].substring(2, split[i].indexOf("title")-2);
				String substrings = split[i].substring(split[i].indexOf("title")+7);
				String title = substrings.substring(0, substrings.indexOf('"'));
				map.put("path", path);
				map.put("title", title);
				lmap.add(map);
			}
		}
		File DestcommodityImage = new File(PropsUtil.get("commodityImagePath")+File.separatorChar+commodityId+File.separator+"commodityDescImage");
		if(!DestcommodityImage.exists()){
			DestcommodityImage.mkdirs();
		}
		//遍历商品图片
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
		cd.setContent(commodityDesc);
		cd.setCommodityId(commodityId);
		Integer save = super.save(cd);
		return save >0 ? true:false;
	}


}
