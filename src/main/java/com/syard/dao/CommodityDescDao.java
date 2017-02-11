package com.syard.dao;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.CommodityDesc;

public interface CommodityDescDao extends Mapper<CommodityDesc>{

	int insertEntity(CommodityDesc cd);

	

}
