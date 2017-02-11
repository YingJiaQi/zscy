package com.syard.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.CategorySpecificationLink;

public interface CategorySpecificationLinkDao extends Mapper<CategorySpecificationLink>{

	List<CategorySpecificationLink> findByCategoryId(@Param("categoryId")String categoryId);
}
