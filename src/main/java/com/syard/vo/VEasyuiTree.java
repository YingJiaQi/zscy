package com.syard.vo;

import java.util.List;

public class VEasyuiTree {
	
	private String id;
	private String text;
	private String state;
	private boolean ischecked;
	private Object attributes;
	private  List<VEasyuiTree> children;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public List<VEasyuiTree> getChildren() {
		return children;
	}
	public void setChildren(List<VEasyuiTree> children) {
		this.children = children;
	}
	
}
