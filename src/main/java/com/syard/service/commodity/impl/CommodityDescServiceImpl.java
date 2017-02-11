package com.syard.service.commodity.impl;


import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.dao.CommodityDescDao;
import com.syard.pojo.CommodityDesc;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommodityDescService;
@Service
public class CommodityDescServiceImpl extends BaseService<CommodityDesc>  implements CommodityDescService {
	@Autowired
	private CommodityDescDao commodityDescDao;
	
	public Boolean addCommodityDesc(String commodityDesc,String commodityId) {
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
