package com.zhoubc.controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.system.Menu;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.UserService;
import com.zhoubc.util.AppUtil;
import com.zhoubc.util.Const;
import com.zhoubc.util.PageData;
import com.zhoubc.util.Tools;

/**
 * 类名称：HeadController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/head")
public class HeadController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**
	 * 获取头部信息
	 * @return
	 */
	@RequestMapping(value = "/getUname")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();

			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			PageData pds = new PageData();
			pds = (PageData) session.getAttribute(Const.SESSION_userpds);
			
			if (null == pds) {
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString(); // 获取当前登录者loginname
				pd.put("USERNAME", USERNAME);
				pds = userService.findByUId(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}

			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} 
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 保存皮肤
	 * @param out
	 */
	@RequestMapping(value = "/setSKIN")
	public void setSKIN(PrintWriter out) {
		log(logger, "用户"+getUid()+",请求保存皮肤");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			// shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();

			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();// 获取当前登录者loginname
			pd.put("USERNAME", USERNAME);
			userService.setSKIN(pd);
			log(logger, "修改用户"+USERNAME+"皮肤");
			session.removeAttribute(Const.SESSION_userpds);
			session.removeAttribute(Const.SESSION_USERROL);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}

	/**
	 * 去系统设置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSystem")
	public ModelAndView goEditEmail() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE)); // 读取每页条数
		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存系统设置1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSys")
	public ModelAndView saveSys() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求保存系统设置1");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Tools.writeFile(Const.SYSNAME, pd.getString("YSYNAME")); // 写入系统名称
		Tools.writeFile(Const.PAGE, pd.getString("COUNTPAGE"));  // 写入每页条数
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	/**
	 * 去代码生成器页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goProductCode")
	public ModelAndView goProductCode() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/head/productCode");
		return mv;
	}

}
