package com.syard.service.Category.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.dao.CategoryDao;
import com.syard.dao.CategorySpecificationLinkDao;
import com.syard.pojo.CategorySpecificationLink;
import com.syard.service.BaseService;
import com.syard.service.Category.CategorySpecificationLinkService;
@Service
public class CategorySpecificationLinkServiceImpl extends BaseService<CategorySpecificationLink> implements CategorySpecificationLinkService{
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private CategorySpecificationLinkDao categorySpecificationLinkDao;
	
	public List<CategorySpecificationLink> getDataByCategoryName(String categoryName) {
		Example example = new Example(CategorySpecificationLink.class);
		example.createCriteria().andEqualTo("categoryName", categoryName);
		return categorySpecificationLinkDao.selectByExample(example);
	}
}
