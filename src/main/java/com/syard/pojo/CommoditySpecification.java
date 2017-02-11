package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_commodity_specification")
public class CommoditySpecification  extends BasePojo{
	@Column(name = "specification_name")
	private String specificationName;
	
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
}
