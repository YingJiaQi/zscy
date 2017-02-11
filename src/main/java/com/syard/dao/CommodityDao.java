package com.syard.dao;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.Commodity;
import com.syard.vo.PageBean;

public interface CommodityDao extends Mapper<Commodity>{

	public List<Commodity> findDataList(PageBean pageBean);

}
