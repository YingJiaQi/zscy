package com.syard.web.MagnetClassification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/MagnetClassification")
public class MagnetClassification {
	@RequestMapping("/getList")
	public String getList(){
		return "Pre_magnetClassification";
	}
}
