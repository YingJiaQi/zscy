package com.syard.web.API;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.service.api.WebContentManagerService;

@Controller
@RequestMapping("/webContentManager")
public class WebContentManager {
	@Autowired
	private WebContentManagerService webContentManagerService;
	
	@RequestMapping(value="/getAboutUsCommponyProfile", method = RequestMethod.POST)
	public ResponseEntity<?> getAboutUsCommponyProfile(@RequestBody Map<String,Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getAboutUsCommponyProfile(param),HttpStatus.OK);
	}
}
