package com.syard.service.commodity;

import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.syard.pojo.Commodity;
import com.syard.vo.CommodityVo;
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
	/**
	 * 删除商品
	 * @param param
	 * @return
	 */
	Map<String, String> deleteCommodityById(Map<String, Object> param);
	/**
	 * 根据ID查找商品
	 * @param id
	 * @return
	 */
	Commodity getCommodityById(String id);
	/**
	 * 获取商品完整信息信息
	 * @param param id
	 * @return 
	 */
	CommodityVo getCommodityWithUpdateById(String id);

}
