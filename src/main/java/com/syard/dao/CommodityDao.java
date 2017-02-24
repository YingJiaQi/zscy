package com.syard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.Commodity;

public interface CommodityDao extends Mapper<Commodity>{
	List<Commodity> getMagnetClassficationDataByName(@Param("start")int start, @Param("size")int size,@Param("categoryName")String categoryName);
}
