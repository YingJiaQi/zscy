package com.syard.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syard.pojo.User;
import com.syard.service.user.UserService;
import com.syard.vo.PageBean;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger log= LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public ResponseEntity<?> getUserList(PageBean pageBean){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
			List<User> uList = userService.getUserList(pageBean);
			PageInfo<User> po = new PageInfo<User>(uList);
			result.put("total", po.getTotal());
			result.put("rows", po.getList());
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}
	@RequestMapping(value="/getUserById", method = RequestMethod.POST)
	public ResponseEntity<?> getUserById(@RequestBody Map<String,Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getUserById(param.get("id").toString());
		result.put("flag", "success");
		result.put("result", user);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	
}
