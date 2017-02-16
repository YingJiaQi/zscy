package com.syard.web.webContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.service.webContentManager.WebContentManagerService;
import com.syard.vo.PageBean;
@Controller
@RequestMapping("/webContentManager")
public class WebContentManagerController {
	@Autowired
	private WebContentManagerService webContentManagerService;
	/**
	 * 初始化加载数据
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/getDataList", method = RequestMethod.POST)
	public ResponseEntity<?> getDataList(PageBean pageBean){
		return new ResponseEntity<Object>(webContentManagerService.getDataList(pageBean),HttpStatus.OK);
	}
	/**
	 * 初始加载前台模块数据
	 * @return
	 */
	@RequestMapping(value="/getModuleList", method = RequestMethod.POST)
	public ResponseEntity<?> getModuleList(){
		return new ResponseEntity<Object>(webContentManagerService.getModuleList(),HttpStatus.OK);
	}
}
