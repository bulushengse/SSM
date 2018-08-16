package com.zhoubc.controller.system;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.system.Menu;
import com.zhoubc.bean.system.Role;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.RoleService;
import com.zhoubc.util.Const;
import com.zhoubc.util.PageData;
import com.zhoubc.util.RightsHelper;

/**
 * 类名称：MenuController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "roleService")
	private RoleService roleService;
	
	/**
	 * 显示菜单列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.setViewName("system/menu/menu_list");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return mv;
	}

	/**
	 * 请求新增菜单页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.setViewName("system/menu/menu_add");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(Menu menu) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求新增菜单");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			menu.setMENU_ID(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString()) + 1));

			String PARENT_ID = menu.getPARENT_ID();
			if (!"0".equals(PARENT_ID)) {
				// 处理菜单类型====
				pd.put("MENU_ID", PARENT_ID);
				pd = menuService.getMenuById(pd);
				menu.setMENU_TYPE(pd.getString("MENU_TYPE"));
				// 处理菜单类型====
			}
			menuService.saveMenu(menu);
			//系统管理员修改菜单权限
			Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
			Session session = currentUser.getSession();
			PageData user = (PageData) session.getAttribute(Const.SESSION_userpds);
			if("1".equals(user.getString("USER_ID"))) {
				List<Menu> menus = menuService.listAll();
				String menuIds[] = new String[menus.size()];  
				for(int i = 0; i < menus.size(); i++) {
					menuIds[i] = menus.get(i).getMENU_ID();
				}
				BigInteger rights = RightsHelper.sumRights(menuIds);
				Role role = roleService.getRoleById("1");
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);
			}
			log(logger, "新增菜单  ID:"+menu.getMENU_ID());
			mv.addObject("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;

	}

	/**
	 * 请求编辑菜单页面
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String MENU_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("MENU_ID", MENU_ID);
			pd = menuService.getMenuById(pd);
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_edit");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 请求编辑菜单图标页面
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEditicon")
	public ModelAndView toEditicon(String MENU_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("MENU_ID", MENU_ID);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_icon");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存菜单图标 (顶部菜单)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editicon")
	public ModelAndView editicon() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求保存菜单图标");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			log(logger, "修改菜单  ID:"+pd.getString("MENU_ID"));
			pd = menuService.editicon(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	/**
	 * 保存编辑
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求修改菜单");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String PARENT_ID = pd.getString("PARENT_ID");
			if (null == PARENT_ID || "".equals(PARENT_ID)) {
				PARENT_ID = "0";
				pd.put("PARENT_ID", PARENT_ID);
			}

			if ("0".equals(PARENT_ID)) {
				// 处理菜单类型====
				menuService.editType(pd);
				// 处理菜单类型====
			}
			log(logger, "修改菜单  ID:"+pd.getString("MENU_ID"));
			pd = menuService.edit(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	/**
	 * 获取当前菜单的所有子菜单
	 * @param MENU_ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sub")
	public void getSub(@RequestParam String MENU_ID, HttpServletResponse response) throws Exception {
		try {
			List<Menu> subMenu = menuService.listSubMenuByParentId(MENU_ID);
			JSONArray arr = JSONArray.fromObject(subMenu);
			PrintWriter out;

			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 删除菜单
	 * @param MENU_ID
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public void delete(@RequestParam String MENU_ID, PrintWriter out) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求删除菜单");
		try {
			menuService.deleteMenuById(MENU_ID);
			log(logger, "删除菜单  ID:"+MENU_ID);
			out.write("success");
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}
	

	
}
