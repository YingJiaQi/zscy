package com.syard.web.webContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syard.service.webContentManager.WebContentManagerService;
import com.syard.vo.PageBean;

@RequestMapping("/webContentManager")
public class WebContentManagerController {
	@Autowired
	private WebContentManagerService webContentManagerService;
	/**
	 * 初始化加载数据
	 * @param pageBean
	 * @return
	 */
	public ResponseEntity<?> getDataList(PageBean pageBean){
		return new ResponseEntity<Object>(webContentManagerService.getDataList(pageBean),HttpStatus.OK);
	}
}
