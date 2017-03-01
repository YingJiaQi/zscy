package com.syard.web.Commodity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.syard.common.utils.PropsUtil;
import com.syard.pojo.CategorySpecificationLink;
import com.syard.pojo.Commodity;
import com.syard.pojo.CommoditySpecificationContent;
import com.syard.service.Category.CategorySpecificationLinkService;
import com.syard.service.commodity.CommodityDescService;
import com.syard.service.commodity.CommodityService;
import com.syard.service.commodity.CommoditySpecificationContentService;
import com.syard.vo.PageBean;


@Controller
@RequestMapping(value="/Commodity")
public class CommodityManage {
	private static Logger logger = LoggerFactory.getLogger(CommodityManage.class);
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommodityDescService commodityDescService;
	@Autowired
	private CategorySpecificationLinkService categorySpecificationLinkService;
	@Autowired
	private CommoditySpecificationContentService commoditySpecificationContentService;
	
	@RequestMapping(value="/addCommodity", method = RequestMethod.POST)
	public ResponseEntity<?> addCommodity(@RequestBody Map<String,Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		String title = (String) param.get("title");
		String sellPoint = (String) param.get("sellPoint");
		String priceView = (String) param.get("priceView");
		String num = (String) param.get("num");
		String usedType = (String) param.get("usedTypes");
		String imageUrls = (String) param.get("imageUrls");
		String categoryName = (String) param.get("category_id");
		/*资源数据标题都不能重复，检测标题是否重复*/
		Boolean ifRepeat = commodityService.checkRepeatWithTitle(title);
		if(ifRepeat){
			result.put("msg", "添加失败,商品标题已存在，请更换");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		}
		
		//类目处理
		String[] categoryList = categoryName.split(",");
		//cscList存储所有的规格参数内容
		List<CommoditySpecificationContent> cscList = new ArrayList<CommoditySpecificationContent>();
		String commodityId = (UUID.randomUUID()+"").replaceAll("-", "");
		//循环取出后台提交的规格参数
		for(int i=0;i<categoryList.length;i++){
			String categoryNames = categoryList[i];
			//根据类目到数据库中取出与之相关联的参数
			List<CategorySpecificationLink> cList = categorySpecificationLinkService.getDataByCategoryName(categoryName);
			//从param中取出相应的规格参数
			for(int j=0;j<cList.size();j++){
				String specificationContent = param.get(categoryName+"_"+cList.get(j).getCateforySpecificationName()).toString();
				CommoditySpecificationContent csc = new CommoditySpecificationContent();
				csc.setCommodityId(commodityId);
				csc.setCreateTime(new Date());
				csc.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				csc.setIsDel(0);
				csc.setCategoryName(categoryNames);
				csc.setSpecificationContent(specificationContent);
				csc.setSpecificationName(cList.get(j).getCateforySpecificationName());
				csc.setUpdateTime(csc.getCreateTime());
				cscList.add(csc);
			}
			
		}
		//将规格参数存储
		boolean flags = commoditySpecificationContentService.addData(cscList);
		//将图片重新保存
		//处理商品图片,转存到本地
		imageUrls = imageUrls.replaceAll("\\|f\\|", "=");
		//用于存储商品图片信息
		List<Map<String, String>> lmap = new ArrayList<Map<String, String>>();
		String[] split = imageUrls.split("img");
		
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
			/*String[] split2 = split[i].split("=");
			for(int j=0;j<split2.length;j++){
				if(j==1){
					//图片地址
					int indexOf = split2[j].indexOf("http");
					int indexOf2 = split2[j].indexOf("title");
					map.put("path", split2[j].substring(indexOf, indexOf2-2));
				}else if(j==2){
					//图片title
					//System.out.println(split2[j].substring(1, split2[j].length()-3));
					map.put("title", split2[j].substring(1, split2[j].lastIndexOf('"')));
				}
			}
			lmap.add(map);*/
		}
		/*File DestcommodityImage = new File(PropsUtil.get("commodityImagePath")+File.separatorChar+commodityId+File.separator+"commodityImages");
		if(!DestcommodityImage.exists()){
			DestcommodityImage.mkdirs();
		}*/
		//遍历商品图片
		String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String rootPath = t.substring(0, t.indexOf("ZSCY")+5);
		File DestcommodityImage = new File(t.substring(0, t.indexOf("ZSCY"))+"images/commodityImage/"+commodityId+"/commodityImages");
		if(!DestcommodityImage.exists()){
			DestcommodityImage.mkdirs();
		}
		StringBuffer sb = new StringBuffer();
		for(Map<String, String> img : lmap){
			sb.append(img.get("title")+"&");
			File sourceCommodityImage = new File(rootPath+img.get("path").substring(img.get("path").indexOf("ZSCY")+5,img.get("path").length()));
			try {
				FileUtils.copyFile(sourceCommodityImage, new File(DestcommodityImage+File.separator+img.get("title")));
				//删除原文件
				FileUtils.deleteQuietly(sourceCommodityImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
		String hot = (String) param.get("hotCommodity");
		String commodityDesc = (String) param.get("commodityDesc");
		commodityDesc = commodityDesc.replaceAll("\\|f\\|", "=");
		Commodity cy = new Commodity();
		cy.setUsedType(usedType);
		if(StringUtils.isNotBlank(hot)){
			cy.setHot(Integer.parseInt(hot));
		}
		cy.setImage("images/commodityImage/"+commodityId+"/commodityImages?"+sb.toString());
		if(StringUtils.isNotBlank(num)){
			cy.setNum(Integer.parseInt(num));
		}
		if(StringUtils.isNotBlank(priceView)){
			cy.setPrice(Double.parseDouble(priceView));
		}
		cy.setSellPoint(sellPoint);
		cy.setId(commodityId);
		cy.setTitle(title);
		cy.setCategoryName(categoryName);
		Boolean flag = commodityService.addCommodity(cy);
		Boolean tag =commodityDescService.addCommodityDesc(commodityDesc,commodityId);
		//删除源文件
		try {
			FileUtils.deleteDirectory(new File(rootPath+"/ueditor/jsp/upload/image"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(flag && tag && flags){
			result.put("msg", "添加成功");
			result.put("flag", "success");
			logger.info("添加商品成功"+cy.getTitle());
		}else{
			result.put("msg", "添加失败");
			//删除记录
			commodityService.deleteCommodity(cy.getId());
			logger.info("添加商品失败"+cy.getTitle());
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	/**
	 * 获取商品列表
	 * @param pageBean
	 * @return easyuivo
	 */
	@RequestMapping(value="getCommodityList", method = RequestMethod.POST)
	public ResponseEntity<?> getCommodityList(PageBean pageBean){
		return new ResponseEntity<Object>(commodityService.getCommodityList(pageBean),HttpStatus.OK);
	}
	/**
	 * 删除商品
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/deleteCommodityById", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCommodityById(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(commodityService.deleteCommodityById(param), HttpStatus.OK);
	}
	@RequestMapping(value="/getCommodityById", method = RequestMethod.POST)
	public ResponseEntity<?> getCommodityById(@RequestBody Map<String, String> param){
		return new ResponseEntity<Object>(commodityService.getCommodityById(param.get("id")+""),HttpStatus.OK);
	}
	/**
	 * 获取商品信息
	 * @param param id
	 * @return 
	 */
	@RequestMapping(value="/getCommodityWithUpdateById", method = RequestMethod.POST)
	public ResponseEntity<?> getCommodityWithUpdateById(@RequestBody Map<String, String> param){
		return new ResponseEntity<Object>(commodityService.getCommodityWithUpdateById(param.get("id")+""),HttpStatus.OK);
	}
}
