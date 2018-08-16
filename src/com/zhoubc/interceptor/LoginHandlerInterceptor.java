package com.zhoubc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhoubc.bean.system.User;
import com.zhoubc.util.Const;
import com.zhoubc.util.Jurisdiction;


/**
 * 类名称：LoginHandlerInterceptor.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path);
				if (!b) {
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return b;
			} else {
				// 登陆拦截
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
		}
	}

}
