package com.syard.web.API;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sun.misc.BASE64Encoder;

import com.syard.common.constant.BusinessStatus;
import com.syard.pojo.User;
import com.syard.service.user.UserService;

@Controller
@RequestMapping("/userApi")
public class UserApi {
	private static Logger log = LoggerFactory.getLogger(UserApi.class);
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登陆
	 * 
	 * @param adminUser
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> toLogin( String username,String password,HttpServletRequest request) {
		log.info("用户登录请求参数：{}");
		//String id = "402890bc526232a801526232e7882ba6";
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.getUserByUserNamePassword(username, password);
		if(user != null){
			request.getSession().setAttribute("user", user);
			result.put("code", BusinessStatus.OK);
			result.put("msg", "sucess");
			result.put("data", user);
		}else{
			result.put("code", BusinessStatus.NOTFIND);
			result.put("msg", "未发现此用户，请核对登录信息");
			result.put("data", user);
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	/**
	 * 用户注册
	 * 
	 * @param adminUser
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<?> toRegister(@RequestParam String mobile_phone,@RequestParam String password, String token,@RequestParam String validateCode,HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		//String validCode = (String) request.getSession().getAttribute("validataCode");
		if(StringUtils.equalsIgnoreCase(validateCode, "123456789")){
			Integer flag = 0;
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				BASE64Encoder base64 = new BASE64Encoder();
				String passwd = base64.encode(digest.digest(password.getBytes("utf-8")));
				flag = userService.addUser(mobile_phone, passwd);
			} catch (NoSuchAlgorithmException e) {
				log.error("UserApi的register方法报错，错误信息是："+e.getMessage());
			} catch (UnsupportedEncodingException e) {
				log.error("UserApi的register方法报错，错误信息是："+e.getMessage());
			}
			if(flag == 1){
				result.put("code", BusinessStatus.OK);
				result.put("msg", "注册成功");
			} else if(flag == 3){
				result.put("code", BusinessStatus.ILLEGAL);
				result.put("msg", "手机号已经注册");
			}else{
				result.put("code", BusinessStatus.INTERFACE_ERROR);
				result.put("msg", "添加用户失败");
			}
		}else{
			result.put("code", BusinessStatus.ERROR);
			result.put("msg", "验证码错误");
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}
