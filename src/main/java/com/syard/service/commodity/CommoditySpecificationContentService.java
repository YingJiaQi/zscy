package com.syard.service.commodity;

import java.util.List;

import com.syard.pojo.CommoditySpecificationContent;

public interface CommoditySpecificationContentService {
	//添加规格参数内容
	boolean addData(List<CommoditySpecificationContent> cscList);
}
