package com.syard.dao;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.PreModuleContentLink;

public interface PreModuleContentLinkDao extends Mapper<PreModuleContentLink> {

	PreModuleContentLink getAssociatedData(@Param("moduleID")String moduleId,@Param("sourceID") String sourceId);
	
}
