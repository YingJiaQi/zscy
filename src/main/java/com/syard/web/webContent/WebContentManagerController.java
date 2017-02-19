package com.syard.web.webContent;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	/**
	 * 添加关联
	 * @return
	 */
	@RequestMapping(value="/addAssociated", method = RequestMethod.POST)
	public ResponseEntity<?> addAssociated(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.addAssociated(param),HttpStatus.OK);
	}
	/**
	 * 根据前台功能模块ID获取其关联的数据
	 * @param param 前台功能模块ID
	 * @return
	 */
	@RequestMapping(value="/getAssociatedListById", method = RequestMethod.POST)
	public ResponseEntity<?> getAssociatedListById(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getAssociatedListById(param),HttpStatus.OK);
	}
	/**
	 * 根据模块ID和数据ID，删除关联
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/delAssociated", method = RequestMethod.POST)
	public ResponseEntity<?> delAssociated(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.delAssociated(param),HttpStatus.OK);
	}
	/**
	 * 添加资源数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/addSourceData", method = RequestMethod.POST)
	public ResponseEntity<?> addSourceData(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.addSourceData(param),HttpStatus.OK);
	}
}
