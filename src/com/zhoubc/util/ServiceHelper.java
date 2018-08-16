package com.zhoubc.util;

import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.RoleService;
import com.zhoubc.service.system.UserService;

/**
 * @author Administrator 获取Spring容器中的service bean
 */
public final class ServiceHelper {

	public static Object getService(String serviceName) {
		// WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}

	public static UserService getUserService() {
		return (UserService) getService("userService");
	}

	public static RoleService getRoleService() {
		return (RoleService) getService("roleService");
	}

	public static MenuService getMenuService() {
		return (MenuService) getService("menuService");
	}
}
