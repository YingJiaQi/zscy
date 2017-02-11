package com.syard.web.Commodity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.pojo.Commodity;
import com.syard.pojo.CommoditySpecification;
import com.syard.service.commodity.CommodityDescService;
import com.syard.service.commodity.CommodityService;
import com.syard.service.commodity.CommoditySpecificationService;
import com.syard.vo.PageBean;


@Controller
@RequestMapping(value="/CommoditySpecification")
public class CommoditySpecificationManage {
	private static Logger logger = LoggerFactory.getLogger(CommoditySpecificationManage.class);
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommodityDescService commodityDescService;
	@Autowired
	private CommoditySpecificationService commoditySpecificationService;
	
	/*@RequestMapping(value="/addCommodity", method = RequestMethod.POST)
	public ResponseEntity<?> addCommodity(@RequestBody Map<String,Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		String title = (String) param.get("title");
		String sellPoint = (String) param.get("sellPoint");
		String priceView = (String) param.get("priceView");
		String num = (String) param.get("num");
		String barcode = (String) param.get("barcode");
		String imageUrls = (String) param.get("imageUrls");
		imageUrls = imageUrls.replaceAll("\\|f\\|", "=");
		StringBuffer sb = new StringBuffer();
		String[] split = imageUrls.split("</p>");
		for(int i=0;i<split.length;i++){
			String[] split2 = split[i].split("=");
			for(int j=0;j<split2.length;j++){
				if(j==1){
					//图片地址
					int indexOf = split2[j].indexOf("http");
					int indexOf2 = split2[j].indexOf("title");
					sb.append(split2[j].substring(indexOf, indexOf2-2)+",");
				}else if(j==2){
					//图片title
					//System.out.println(split2[j].substring(1, split2[j].length()-3));
				}
			}
		}
		sb.length();
		String hot = (String) param.get("hotCommodity");
		String commodityDesc = (String) param.get("commodityDesc");
		commodityDesc = commodityDesc.replaceAll("\\|f\\|", "=");
		String category_id = (String) param.get("category_id");
		Commodity cy = new Commodity();
		cy.setBarcode(barcode);
		if(StringUtils.isNotBlank(hot)){
			cy.setHot(Integer.parseInt(hot));
		}
		cy.setImage(sb.toString());
		if(StringUtils.isNotBlank(num)){
			cy.setNum(Integer.parseInt(num));
		}
		if(StringUtils.isNotBlank(priceView)){
			cy.setPrice(Double.parseDouble(priceView));
		}
		cy.setSell_point(sellPoint);
		cy.setId((UUID.randomUUID()+"").replaceAll("-", ""));
		cy.setTitle(title);
		cy.setStatus(2);
		cy.setCategory_id(category_id);
		Boolean flag = commodityService.addCommodity(cy);
		Boolean tag =commodityDescService.addCommodityDesc(commodityDesc);
		if(flag && tag){
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
	}*/
	/**
	 * 获取分页数据
	 * @return
	 */
	@RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
	public ResponseEntity<?> getMenuList(PageBean pageBean){
		Map<String, Object> vtList = commoditySpecificationService.getCommoditySpecificationList(pageBean);
		return new ResponseEntity<Object>(vtList, HttpStatus.OK);
	}
	@RequestMapping(value="addCommoditySpecification", method = RequestMethod.POST)
	public ResponseEntity<?> addCommoditySpecification(@RequestBody CommoditySpecification commoditySpecification){
		Map<String, Object> result = commoditySpecificationService.addCommoditySpecification(commoditySpecification);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	/**
	 * 根据ID删除
	 * @param param
	 * @author yjq
	 * @return
	 */
	@RequestMapping(value = "/deleteCommoditySpecificationById", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCommoditySpecificationById(@RequestBody Map<String,Object> param){
		Map<String, Object> result = commoditySpecificationService.deleteCommoditySpecificationById(param.get("id").toString());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	/**
	 * 根据ID更新规格参数
	 * @param param
	 * @author yjq
	 * @return
	 */
	@RequestMapping(value = "/updateCommoditySpecificationById", method = RequestMethod.POST)
	public ResponseEntity<?> updateCommoditySpecificationById(@RequestBody CommoditySpecification commoditySpecification){
		Map<String, Object> result = commoditySpecificationService.updateCommoditySpecificationById(commoditySpecification);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	/**
	 * 根据ID查找规格参数
	 * @param param
	 * @author yjq
	 * @return
	 */
	@RequestMapping(value = "/getCommoditySpecificationById", method = RequestMethod.POST)
	public ResponseEntity<?> getCommoditySpecificationById(@RequestBody Map<String,Object> param){
		Map<String, Object> result = commoditySpecificationService.getCommoditySpecificationById(param.get("id").toString());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}
