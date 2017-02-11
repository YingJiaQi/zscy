package com.syard.web.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.common.utils.CustomRuntimeException;
import com.syard.service.user.SystemComponentsService;
import com.syard.vo.VEasyuiTree;

/**
 * 系统模块Controller
 */
@Controller
@RequestMapping("/sys")
public class SysCompController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SystemComponentsService sysService;

	/**
	 * 获取系统模块信息
	 * @return
	 */
	@RequestMapping(value = "/getComponetList", method = RequestMethod.POST)
	public ResponseEntity<?> getComponetList(@RequestBody String userId,HttpServletRequest request) {
		logger.info("获取系统模块信息");
		List<VEasyuiTree> result = null;
		String userid  =request.getParameter("userId");
		try {
			result = sysService.getComponentList(userid);
		} catch (Exception e) {
			logger.error("获取系统模块信息异常", e);
			throw new CustomRuntimeException(e.getMessage());
		}
		return new ResponseEntity<List<VEasyuiTree>>(result, HttpStatus.OK);
	}
	
	/**
	 * 分配权限
	 * @param map
	 * @return
	 */
/*	@RequestMapping(value = "/assginComponet", method = RequestMethod.POST)
	public ResponseEntity<?> assginComponet(@RequestBody Map<String,Object> map) {
		try {
			String userId = map.get("userId").toString();
			List<String> list = (List<String>)map.get("pageList");
			sysService.assignUserAuthor(userId,list);
		} catch (Exception e) {
			throw new RestException(e.getMessage());
		}
		return new ResponseEntity<Map<String,String>>(GetSuccMap(), HttpStatus.OK);
	}*/
	
	/**
	 * 获取当前用胡的menu.json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	public ResponseEntity<?> getMenu(HttpServletRequest request) {
		List<?> menu =null;
		try {
			//menu =sysService.getMenuJsonByUserId(getCurrentUserId());
			menu = sysService.getComponentList("");
		} catch (Exception e) {
			logger.error("获取系统模块信息异常", e);
			throw new CustomRuntimeException(e.getMessage());
		}
		return new ResponseEntity<List<?>>(menu, HttpStatus.OK);
	}
	
}
