package com.syard.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

@Table(name = "tbl_s_user")
public class User  extends BasePojo{
	@Column(name="user_code")
    private String userCode;
	
	@Column(name="login_times")
    private Integer loginTimes;
	
	@Column(name="user_password")
    private String userPassword;
	
	@Column(name="user_name")
    private String userName;
	
	@Column(name="user_gender")
    private Integer userGender;
	
	@Column(name="user_phone")
    private String userPhone;
	
	@Column(name="user_email")
    private String userEmail;
	
	@Column(name="birthday")
    private Date birthday;
	
	@Column(name="last_login_time")
    private Date lastLoginTime;
	
	@Column(name="is_deleted")
    private Integer isDeleted=0;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserGender() {
		return userGender;
	}
	public void setUserGender(Integer userGender) {
		this.userGender = userGender;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@JSONField(format= "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}
