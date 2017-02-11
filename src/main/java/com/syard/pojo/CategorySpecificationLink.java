package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_category_specification_link")
public class CategorySpecificationLink  extends BasePojo{
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_specification_id")
	private String categorySpecificationId;
	
	@Column(name = "catefory_specification_name")
	private String cateforySpecificationName;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategorySpecificationId() {
		return categorySpecificationId;
	}

	public void setCategorySpecificationId(String categorySpecificationId) {
		this.categorySpecificationId = categorySpecificationId;
	}

	public String getCateforySpecificationName() {
		return cateforySpecificationName;
	}

	public void setCateforySpecificationName(String cateforySpecificationName) {
		this.cateforySpecificationName = cateforySpecificationName;
	}
	
	
}
