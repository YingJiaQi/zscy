package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_other_sources")
public class OtherSource  extends BasePojo{
	@Column(name="source_type")
	private String sourceType;
	@Column(name="source_title")
	private String sourceTitle;	
	@Column(name="source_url")
	private String sourceUrl;	
	@Column(name="source_content")
	private String sourceContent;
	@Column(name="status")
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceTitle() {
		return sourceTitle;
	}
	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourceContent() {
		return sourceContent;
	}
	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
	}	
}
