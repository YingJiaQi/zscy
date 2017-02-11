package com.syard.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.syard.common.constant.BusinessStatus;
import com.syard.common.constant.MobileStatus;
/**
 * 自定义的运行异常
 * @author Administrator
 *
 */
public class CustomRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = -1401593546385403720L;

	public HttpStatus status;

	public CustomRuntimeException() {
	}

	public CustomRuntimeException(HttpStatus status) {
		this.status = status;
	}

	public CustomRuntimeException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	/**
	 * messageMap.put("code","202");
	 * messageMap.put("msg","XXXXXXXXXX");
	 * status 此处禁止用200，否则手机端不错异常处理
	 * @param status
	 * @param messageMap
	 */
	public CustomRuntimeException(HttpStatus status, Map<String,String> messageMap) {
		super(JSON.toJSONString(messageMap));
		this.status = status;
	}
	/**
	 * 默认stauts:202, 表示服务器已接受请求，但尚未成功处理
	 * @param messageMap
	 */
	public CustomRuntimeException (Map<String,String> messageMap) {
		super(JSON.toJSONString(chkMessageMap(messageMap)));
		this.status = HttpStatus.ACCEPTED;
	}
	
	public CustomRuntimeException(String message) {
		super(JSON.toJSONString(getMessageMap(message)));
		this.status = HttpStatus.ACCEPTED;
	}
	
	private static Map<String,String> chkMessageMap(Map<String,String> messageMap){
		if(messageMap.size()==1 && messageMap.get(MobileStatus.CODE)==null){
			messageMap.put(MobileStatus.CODE, BusinessStatus.ERROR);
		}
		return messageMap;
	}
	
	private static Map<String,Object> getMessageMap(String message){
		Map<String,Object> messageMap = new HashMap<String,Object>();
		System.out.println(messageMap.size());
		messageMap.put(MobileStatus.CODE, BusinessStatus.ERROR);
		messageMap.put(MobileStatus.MSG,message);
		messageMap.put(MobileStatus.SUCESS, BusinessStatus.RESULT_FALSE);
		return messageMap;
	}
}
