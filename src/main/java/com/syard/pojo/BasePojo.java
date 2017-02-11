package com.syard.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

public class BasePojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
	@Column(name="create_time")
	private Date createTime;
	@Column(name="update_time")
	private Date updateTime;
	@Column(name = "is_deleted")
	private Integer isDel = 0;
	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JSONField(format= "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSONField(format= "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
