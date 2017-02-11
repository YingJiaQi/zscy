package com.syard.web.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.syard.pojo.User;
import com.syard.service.user.UserService;

@Controller
@RequestMapping("/toLogin")
public class login {
	private Logger log = LoggerFactory.getLogger(login.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String toLogin(){
		return "Back_login";
	}
	@RequestMapping(value="/toMainBack", method = RequestMethod.POST)
	public ModelAndView toMainBack(String username, String password, HttpServletRequest request){
		//确定计算方法
        MessageDigest md5;
        User user = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			//加密后的字符串
			password = base64en.encode(md5.digest(password.getBytes("utf-8")));
			user = userService.findUserByUP(username, password);
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException"+e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException"+e.getMessage());
			e.printStackTrace();
		}
		if(user != null){
			request.getSession().setAttribute("user", user);
			return new ModelAndView("main_back");
		}else{
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("msg","用户名或密码错误");
			return mv;
		}
	}
	/**
	 * 其他子页面
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "{value}")
	public String toPage(@PathVariable("value") String value) {
		return value;
	}
}
