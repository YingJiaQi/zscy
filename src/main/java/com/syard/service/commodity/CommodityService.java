package com.syard.service.commodity;

import java.util.Map;

import com.syard.pojo.Commodity;
import com.syard.vo.PageBean;

public interface CommodityService{
	//添加商品
	Boolean addCommodity(Commodity cy);
	//删除商品
	void deleteCommodity(String id);
	/**
	 * 获取商品列表
	 * @author YQ
	 * @param pageBean
	 * @return
	 */
	Map<String, Object> getCommodityList(PageBean pageBean);

}
