package com.syard.web.commons;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.syard.common.utils.PropsUtil;


@Controller
@RequestMapping("/fileUploadServlet")
public class FileUploadServlet {
	@RequestMapping("/imageUpload")
	public void imageUpload(@RequestParam("upfile") CommonsMultipartFile file,HttpServletResponse  response,HttpServletRequest request){
		response.setContentType("application/json; charset=UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		String fileName = file.getOriginalFilename();
		String[] split = fileName.split("\\.");
		File newFile = new File(PropsUtil.get("CommodityImagePath")+File.separatorChar+fileName);
		if(!new File(PropsUtil.get("CommodityImagePath")).exists()){
			new File(PropsUtil.get("CommodityImagePath")).mkdirs();
		}
	}
}
