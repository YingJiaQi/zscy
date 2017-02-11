package com.syard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.User;
import com.syard.vo.PageBean;



public interface UserDao extends Mapper<User>{

    
    public Integer addUser(User user);
    public Integer deleteUsers(@Param("ids")List<Long> ids);
	public List<User> queryUserAll(PageBean pageBean);
	public String getMaxUserCode();
}
