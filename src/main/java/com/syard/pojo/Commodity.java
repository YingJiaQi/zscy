package com.syard.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tbl_s_commodity")
public class Commodity  extends BasePojo{
	@Column(name="title")
	private String title;
	@Column(name="sell_point")
	private String sell_point;
	@Column(name="price")
	private Double price;
	@Column(name="num")
	private Integer num;
	@Column(name="barcode")
	private String barcode;
	@Column(name="hot")
	private Integer hot;
	@Column(name="image")
	private String image;
	@Column(name="category_name")
	private String categoryName;
	@Column(name="status")//商品状态，1-上架，2-待上架，3-删除
	private Integer status;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
