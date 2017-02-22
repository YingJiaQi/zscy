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
	/**
	 * 前台磁性制品页面请求磁铁分类数据
	 * @return
	 */
	@RequestMapping(value="/getMagnetClassficationData", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetClassficationData(){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetClassficationData(),HttpStatus.OK);
	}
	/**
	 * 前台磁性制品页面请求 ---根据分类名，查找该分类下的所有数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getMagnetClassficationDataByTitle", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetClassficationDataByTitle(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetClassficationDataByTitle(param),HttpStatus.OK);
	}
	/**
	 * 前台磁性制品页面请求 ---根据用途名，查找该用途下的所有数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getMagnetDataByUsedName", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetDataByUsedName(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetDataByUsedName(param),HttpStatus.OK);
	}
}
