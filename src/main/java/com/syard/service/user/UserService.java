package com.syard.service.user;

import java.util.List;

import com.syard.pojo.User;
import com.syard.vo.PageBean;

public interface UserService {

	User findUserByUP(String username, String password);
	//获取用户信息
	List<User> getUserList(PageBean pageBean);
	//根据id获取User
	User getUserById(String id);
	//前台添加用户
	Integer addUser(String mobile_phone, String password);
	//用户登录
	User getUserByUserNamePassword(String userName, String password);

}
