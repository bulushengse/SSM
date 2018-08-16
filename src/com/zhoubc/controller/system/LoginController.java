package com.zhoubc.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.Page;
import com.zhoubc.bean.system.Menu;
import com.zhoubc.bean.system.Role;
import com.zhoubc.bean.system.User;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.RoleService;
import com.zhoubc.service.system.UserService;
import com.zhoubc.util.AppUtil;
import com.zhoubc.util.Const;
import com.zhoubc.util.DateUtil;
import com.zhoubc.util.PageData;
import com.zhoubc.util.RightsHelper;
import com.zhoubc.util.Tools;

/**
 * 类名称：LoginController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
public class LoginController extends BaseController {

	String menuUrl = "ticket/list.do"; //菜单地址(权限用)
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "roleService")
	private RoleService roleService;

	/**
	 * 获取登录用户的IP
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		log(logger, "登录用户"+USERNAME+" ip:"+ip);
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}

	/**
	 * 访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/admin/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception {
		logBefore(logger,"请求登录");
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("gzgtx123", "").replaceAll("gzgtx123", "").split(",gtx,");
		if (null != KEYDATA && KEYDATA.length == 3) {
			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); // 获取session中的验证码
			String code = KEYDATA[2];
			if (null == code || "".equals(code)) {
				errInfo = "nullcode"; // 验证码为空
			} else {
				String USERNAME = KEYDATA[0];
				String PASSWORD = KEYDATA[1];
				log(logger,"验证用户"+USERNAME);
				pd.put("USERNAME", USERNAME);
				if (Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
					// 密码加密
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);
					if (pd != null) {
						pd.put("LAST_LOGIN", DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						session.setAttribute(Const.SESSION_USER, user);
						session.removeAttribute(Const.SESSION_SECURITY_CODE);
						log(logger, "用户"+pd.getString("USERNAME")+" ID:"+pd.getString("USER_ID"));
						// shiro加入身份验证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							errInfo = "shiroerror";
						}
						
					} else {
						errInfo = "usererror"; // 用户名或密码有误
					}
				} else {
					errInfo = "codeerror"; // 验证码输入有误
				}
				if (Tools.isEmpty(errInfo)) {
					errInfo = "success"; // 验证成功
				}
			}

		} else {
			errInfo = "error"; // 缺少参数
		}
		
		log(logger,"登录结果："+errInfo);
		logAfter(logger);
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 访问系统首页
	 * @param changeMenu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		try {
			 //shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				User userr = (User) session.getAttribute(Const.SESSION_USERROL);
				if (null == userr) {
					user = userService.getUserAndRoleById(user.getUSER_ID());
					session.setAttribute(Const.SESSION_USERROL, user);
				} else {
					user = userr;
				}
				
				Role role = user.getRole();
				String roleRights = role != null ? role.getRIGHTS() : "";
				//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, user.getUSERNAME()); // 放入用户名
				//获取菜单数据
				List<Menu> allmenuList = new ArrayList<Menu>();
				if (null == session.getAttribute(Const.SESSION_allmenuList)) {
					allmenuList = menuService.listAllMenu();
					if (Tools.notEmpty(roleRights)) {
						for (Menu menu : allmenuList) {
							menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
							if (menu.isHasMenu()) {
								List<Menu> subMenuList = menu.getSubMenu();
								for (Menu sub : subMenuList) {
									sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
								}
							}
						}
					}
					session.setAttribute(Const.SESSION_allmenuList, allmenuList); // 菜单权限放入session中
				} else {
					allmenuList = (List<Menu>) session.getAttribute(Const.SESSION_allmenuList);
				}
				
				// 按钮权限放到session中 
				if (null == session.getAttribute(Const.SESSION_QX)) {
					session.setAttribute(Const.SESSION_QX, this.getUQX(session)); 
				}
				// 读取websocket配置=====================================
				String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);// 读取WEBSOCKET配置
				if (null != strWEBSOCKET && !"".equals(strWEBSOCKET)) {
					String strIW[] = strWEBSOCKET.split(",gzgtx,");
					if (strIW.length == 4) {
						pd.put("WIMIP", strIW[0]);
						pd.put("WIMPORT", strIW[1]);
						pd.put("OLIP", strIW[2]);
						pd.put("OLPORT", strIW[3]);
					}
				}
				// 读取websocket配置=====================================
				
				mv.setViewName("system/admin/index");
				mv.addObject("user", user);
				mv.addObject("menuList", allmenuList);
			} else {
				mv.setViewName("system/admin/login");// session失效后跳转登录页面
			}
			log(logger,"用户"+session.getAttribute(Const.SESSION_USERNAME)+"访问系统首页"+" ID:"+user.getUSER_ID());
		} catch (Exception e) {
			mv.setViewName("system/admin/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.addObject("pd", pd);
		
		return mv;
	}

	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/admin/tab";
	}

	/**
	 * 进入首页后的默认页面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_default")
	public ModelAndView defaultPage(Page page) throws Exception{
		logBefore(logger,  "用户"+getUid()+",登录首页的默认页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			mv.setViewName("system/admin/default");
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 用户注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() throws Exception{
		logBefore(logger,"用户"+getUid()+",请求注销");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		// shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		log(logger,"注销成功");
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_allmenuList);
		session.removeAttribute(Const.SESSION_menuList);
		session.removeAttribute(Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");

		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		pd = this.getPageData();
		String msg = pd.getString("msg");
		pd.put("msg", msg);

		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/admin/login");
		mv.addObject("pd", pd);
		logAfter(logger);
		return mv;
	}

	/**
	 * 获取用户权限 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getUQX(Session session) throws Exception{
		PageData pd = new PageData();    
		Map<String, String> map = new HashMap<String, String>();
		try {
			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();
			pd.put(Const.SESSION_USERNAME, USERNAME);
			String ROLE_ID = userService.findByUId(pd).get("ROLE_ID").toString();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			map.put("adds", pd.getString("ADD_QX"));
			map.put("dels", pd.getString("DEL_QX"));
			map.put("edits", pd.getString("EDIT_QX"));
			map.put("chas", pd.getString("CHA_QX"));
			this.getRemortIP(USERNAME);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}
	
}
