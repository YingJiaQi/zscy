package com.syard.service.Category;

import java.util.List;

import com.syard.pojo.CategorySpecificationLink;

public interface CategorySpecificationLinkService {
	//根据类目名称获取关联参数
	List<CategorySpecificationLink> getDataByCategoryName(String categoryName);

}
