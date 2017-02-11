package com.syard.service.commodity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syard.dao.CommoditySpecificationContentDao;
import com.syard.pojo.CommoditySpecificationContent;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommoditySpecificationContentService;

@Service
public class CommoditySpecificationServiceImpl extends BaseService<CommoditySpecificationContent>  implements CommoditySpecificationContentService {
	@Autowired
	private CommoditySpecificationContentDao commoditySpecificationContentDao;
	
	
	public boolean addData(List<CommoditySpecificationContent> cscList) {
		for(CommoditySpecificationContent csc: cscList){
			commoditySpecificationContentDao.insert(csc);
		}
		return true;
	}
}
