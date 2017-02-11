package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_commodity_desc")
public class CommodityDesc  extends BasePojo{
	@Column(name="content")
	private String content;
	@Column(name="commodity_id")
	private String commodityId;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

}
