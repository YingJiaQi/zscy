package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_pre_module_content_link")
public class PreModuleContentLink  extends BasePojo{
	@Column(name="module_id")
	private String moduleId;//模块ID
	@Column(name="module_name")
	private String moduleName;
	@Column(name="source_type")
	private String sourceType;
	@Column(name="source_id")
	private String sourceId;
	@Column(name="source_title")
	private String sourceTitle;
	
	
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceTitle() {
		return sourceTitle;
	}
	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}
}
