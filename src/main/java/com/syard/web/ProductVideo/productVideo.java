package com.syard.web.ProductVideo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productVideo")
public class productVideo {
	@RequestMapping(value="/toProductJsp")
	public String toProductJsp(){
		return "Pre_productVideo";
	}
}
