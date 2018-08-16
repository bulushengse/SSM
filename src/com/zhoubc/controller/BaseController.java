package com.zhoubc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.Page;
import com.zhoubc.util.Const;
import com.zhoubc.util.Logger;
import com.zhoubc.util.PageData;
import com.zhoubc.util.UuidUtil;

/**
 * 类名称：BaseController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	private static final String STR1 = "===>";
	
	private static final String STR2 = "=============start=================";
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {

		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {

		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info(STR1+interfaceName+STR2);
	}
	
	public static void log(Logger logger, String str) {
		logger.info(STR1+str);
	}

	public static void logAfter(Logger logger) {
		logger.info("===>end");
		logger.info("");
	}
	
	/**
	 * 获取登录用户名  
	 * @return
	 */
	protected String getUid() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		String uname = (String) session.getAttribute(Const.SESSION_USERNAME);
		return uname;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

}
