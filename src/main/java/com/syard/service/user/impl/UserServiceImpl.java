package com.syard.service.user.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.syard.dao.UserDao;
import com.syard.pojo.User;
import com.syard.service.user.UserService;
import com.syard.vo.PageBean;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public User findUserByUP(String username, String password) {
		User user = new User();
		user.setUserName(username);
		user.setUserPassword(password);
		return userDao.selectOne(user);
	}

	public List<User> getUserList(PageBean pageBean) {
		return userDao.queryUserAll(pageBean);
	}

	public User getUserById(String id) {
		return userDao.selectByPrimaryKey(id);
	}

	public Integer addUser(String mobile_phone, String password) {
		User users = new User();
		users.setUserPhone(mobile_phone);
		List<User> uList = userDao.select(users);
		if(uList.size() >0){
			return 3;
		}
		User user = new User();
		UUID uid = UUID.randomUUID();
		String id = (uid+"").replaceAll("-", "");
		user.setId(id);
		user.setUserPhone(mobile_phone);
		user.setUserPassword(password);
		//acquire userCode
		int userCode = Integer.parseInt(userDao.getMaxUserCode());
		user.setUserCode(userCode+1+"");
		user.setLastLoginTime(new Date());
		user.setLoginTimes(1);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
 		return userDao.insert(user);
	}

	public User getUserByUserNamePassword(String username, String password) {
		/*User record = new User();
		record.setUserPhone(username);
		record.setUserPassword(password);*/
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("userPhone", username);
		example.createCriteria().andEqualTo("userPassword", password);
		return userDao.selectByExample(example).get(0);
	}

}
