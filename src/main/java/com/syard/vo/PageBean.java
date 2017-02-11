package com.syard.vo;

import java.util.Date;



import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;


public class PageBean {
	
	private Integer page;
	private Integer rows;
	private String startTime;
	private String endTime;
	private Integer isDeleted=0;
	private String id;
	
	private String key;
	private String key2;
	private String key3;
	
	private String nodeID;
	private String docId;
	
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
