package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_commodity_specification_content")
public class CommoditySpecificationContent  extends BasePojo{
	@Column(name = "commodity_id")
	private String commodityId;
	@Column(name = "category_name")
	private String categoryName;
	@Column(name = "specification_name")
	private String specificationName;
	@Column(name = "specification_content")
	private String specificationContent;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public String getSpecificationContent() {
		return specificationContent;
	}
	public void setSpecificationContent(String specificationContent) {
		this.specificationContent = specificationContent;
	}
}
