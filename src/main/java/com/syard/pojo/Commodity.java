package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_commodity")
public class Commodity  extends BasePojo{
	@Column(name="title")
	private String title;
	@Column(name="sell_point")
	private String sellPoint;
	@Column(name="price")
	private Double price;
	@Column(name="num")
	private Integer num;
	@Column(name="used_type")
	private String usedType;//用途分类
	@Column(name="hot")
	private Integer hot;
	@Column(name="image")
	private String image;
	@Column(name="category_name")
	private String categoryName;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getUsedType() {
		return usedType;
	}
	public void setUsedType(String usedType) {
		this.usedType = usedType;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
