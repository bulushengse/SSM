package com.zhoubc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhoubc.controller.BaseController;

/**
 * 类名称：LoginFilter.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class LoginFilter extends BaseController implements Filter {

	/**
	 * 初始化
	 */
	@Override
	public void init(FilterConfig fc) throws ServletException {
		
	}
	
	/**
	 * 销毁
	 */
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//System.out.println("------------------loginfilter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		chain.doFilter(req, res); // 调用下一过滤器
	}

}
