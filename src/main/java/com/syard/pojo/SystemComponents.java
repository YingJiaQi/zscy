package com.syard.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统模块清单
 */
@Table(name = "tbl_s_system_components")
public class SystemComponents implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	@Column(name="component_code")
	private String componentCode;
	@Column(name="component_name")
	private String componentName;
	@Column(name="component_type")
	private int componentType;
	@Column(name="component_priority")
	private int componentPriority;
	@Column(name="component_url")
	private String componentUrl;
	@Column(name="parent_component")
	private String parentComponent;
	@Column(name="is_deleted")
	private int isDeleted;
	@Column(name="create_time")
	private Date createTime;
	@Column(name="update_time")
	private Date updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public int getComponentType() {
		return componentType;
	}
	public void setComponentType(int componentType) {
		this.componentType = componentType;
	}
	public int getComponentPriority() {
		return componentPriority;
	}
	public void setComponentPriority(Integer componentPriority) {
		
		this.componentPriority = componentPriority==null?0:componentPriority;
	}
	public String getParentComponent() {
		return parentComponent;
	}
	public void setParentComponent(String parentComponent) {
		this.parentComponent = parentComponent;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getComponentUrl() {
		return componentUrl;
	}
	public void setComponentUrl(String componentUrl) {
		this.componentUrl = componentUrl;
	}
	
}
