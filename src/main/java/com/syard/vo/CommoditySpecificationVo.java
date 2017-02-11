package com.syard.vo;

import java.util.Date;

public class CommoditySpecificationVo {
	//用于关联文件
	private Boolean checked;
	
	private String specificationName;
	private String id;
	private Date createTime;
	private Date updateTime;
	private Integer isDel = 0;
	
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
