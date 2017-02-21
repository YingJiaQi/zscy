package com.syard.web.API;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.service.api.PreWebContentManagerService;

@Controller
@RequestMapping("/PreWebContentManager")
public class PreWebContentManager {
	@Autowired
	private PreWebContentManagerService webContentManagerService;
	/**
	 * 前台页面获取关于我们数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getAboutUsCommponyProfile", method = RequestMethod.POST)
	public ResponseEntity<?> getAboutUsCommponyProfile(@RequestBody Map<String,Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getAboutUsCommponyProfile(param),HttpStatus.OK);
	}
	/**
	 * 前台新闻中心页面获取数据
	 * @return
	 */
	@RequestMapping(value="/getNewsCentorData", method = RequestMethod.POST)
	public ResponseEntity<?> getNewsCentorData(){
		return new ResponseEntity<Object>(webContentManagerService.getNewsCentorData(),HttpStatus.OK);
	}
}
