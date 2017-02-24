package com.syard.dao;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.PreSystemComponents;

public interface PreSystemComponentsDao extends Mapper<PreSystemComponents> {

	PreSystemComponents getNewsID(@Param("moduleName")String moduleName);
	
}
