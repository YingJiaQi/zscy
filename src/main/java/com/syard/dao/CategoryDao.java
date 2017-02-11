package com.syard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.Category;

public interface CategoryDao extends Mapper<Category>{
	//根据类名查找相应数据
	List<Category> findCategoryByName(@Param("name")String name);

}
