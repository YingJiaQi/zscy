package com.syard.web.MessageInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/MessageInfo")
public class MessageInfo {
	@RequestMapping("/aboutUs")
	public String toAboutUs(){
		return "Pre_AboutUs";
	}
}
