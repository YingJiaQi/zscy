package com.syard.vo;

import java.util.List;

import javax.persistence.Column;

import com.syard.pojo.BasePojo;
import com.syard.pojo.CommodityDesc;
import com.syard.pojo.CommoditySpecificationContent;

public class CommodityVo  extends BasePojo{
	private String categoryName;
	private Integer hot;
	private String title;
	private String sellPoint;
	private Double price;
	private Integer num;
	private String barcode;
	private String image;
	private Integer status;//商品状态，1-上架，2-待上架，3-删除
	private CommodityDesc describe;
	private List<CommoditySpecificationContent> categorySpecification;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CommodityDesc getDescribe() {
		return describe;
	}
	public void setDescribe(CommodityDesc describe) {
		this.describe = describe;
	}
	public List<CommoditySpecificationContent> getCategorySpecification() {
		return categorySpecification;
	}
	public void setCategorySpecification(List<CommoditySpecificationContent> categorySpecification) {
		this.categorySpecification = categorySpecification;
	}
}
