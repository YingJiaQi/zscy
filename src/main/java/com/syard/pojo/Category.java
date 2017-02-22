package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_category")
public class Category  extends BasePojo{
	@Column(name="category_name")
	private String categoryName;
	@Column(name="component_priority")
	private Integer componentPriority;
	

	public Integer getComponentPriority() {
		return componentPriority;
	}

	public void setComponentPriority(Integer componentPriority) {
		this.componentPriority = componentPriority;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
