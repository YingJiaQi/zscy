package com.syard.service.commodity.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.syard.pojo.Commodity;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommodityService;
import com.syard.vo.PageBean;
@Service
public class CommodityServiceImpl extends BaseService<Commodity>  implements CommodityService {

	public Boolean addCommodity(Commodity cy) {
		cy.setCreateTime(new Date());
		cy.setUpdateTime(cy.getCreateTime());
		Integer save = super.save(cy);
		return save > 0 ? true: false;
	}

	public void deleteCommodity(String id) {
		super.deleteById(Long.parseLong(id));
	}

	public Map<String, Object> getCommodityList(PageBean pageBean) {
		Map<String, Object> result = new HashMap<String,Object>();
		Commodity record = new Commodity();
		
		PageInfo<Commodity> queryPageListByWhere = super.queryPageListByWhere(record, pageBean.getPage(), pageBean.getRows());
		
		result.put("total", queryPageListByWhere.getTotal());
		result.put("rows", queryPageListByWhere.getList());
		return result;
	}

}
