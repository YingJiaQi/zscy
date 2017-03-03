package com.syard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.syard.pojo.User;

public class LogInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 预处理,Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User)request.getSession().getAttribute("user");
		if(user != null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 生成视图之前,调用了Service并返回ModelAndView，但未进行页面渲染
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception{

		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 最后执行，可用于释放资源
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
	
		super.afterCompletion(request, response, handler, ex);
	}
	
	

}
