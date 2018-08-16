package com.zhoubc.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.util.Logger;

/**
 * 类名称：MyExceptionResolver.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
	
	private  Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		logger.info("===>异常开始===============================================");
		logger.error(ex.toString(),ex);
		logger.info("===>异常结束===============================================");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

}
