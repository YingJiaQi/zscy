package com.syard.service.commodity;

import java.util.Map;

import com.syard.pojo.CommoditySpecification;
import com.syard.vo.PageBean;

public interface CommoditySpecificationService {
	//获取规格参数
	Map<String, Object> getCommoditySpecificationList(PageBean pageBean);
	//添加规格参数
	Map<String, Object> addCommoditySpecification(CommoditySpecification commoditySpecification);
	//删除规格参数
	Map<String, Object> deleteCommoditySpecificationById(String string);
	//更新规格参数
	Map<String, Object> updateCommoditySpecificationById(CommoditySpecification commoditySpecification);
	//根据id查找规格参数
	Map<String, Object> getCommoditySpecificationById(String string);
}
