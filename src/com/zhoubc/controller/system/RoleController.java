package com.zhoubc.controller.system;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.Page;
import com.zhoubc.bean.system.Menu;
import com.zhoubc.bean.system.Role;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.RoleService;
import com.zhoubc.util.AppUtil;
import com.zhoubc.util.Const;
import com.zhoubc.util.Jurisdiction;
import com.zhoubc.util.PageData;
import com.zhoubc.util.RightsHelper;
import com.zhoubc.util.Tools;

/**
 * 类名称：RoleController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

	String menuUrl = "role.do"; // 菜单地址(权限用)
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "roleService")
	private RoleService roleService;

	/**
	 * 编辑权限(增删改查)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/qx")
	public ModelAndView qx() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求编辑权限");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				roleService.updateQx(msg, pd);
				log(logger, "操作"+msg+",传入数据为"+pd.toString());
			}
			mv.setViewName("save_result");
			mv.addObject("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
		return mv;
	}

	/**
	 * 显示组织角色列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listRoles")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求显示角色列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Role> roleList = roleService.listAllRoles(); // 列出所有部门
		
		String roleId = pd.getString("ROLE_ID");       //默认点击第一个部门
		if (roleId == null || "".equals(roleId)) {       
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());        
		}
		List<Role> roleList_z = roleService.listAllRolesByPId(pd); // 列出此部门的所有下级

		pd = roleService.findObjectById(pd); // 取得点击部门
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("roleList_z", roleList_z);
		mv.setViewName("system/role/role_list");
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		logAfter(logger);
		return mv;
	}

	/**
	 * 请求新增页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			mv.setViewName("system/role/role_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存新增信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求新增角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String parent_id = pd.getString("PARENT_ID"); // 父类角色id
			pd.put("ROLE_ID", parent_id);
			if ("0".equals(parent_id)) {
				pd.put("RIGHTS", "");
			} else {
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights) ? "" : rights);
			}
			String UUID = this.get32UUID();		
			pd.put("QX_ID", UUID);
			pd.put("ROLE_ID", UUID);
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			pd.put("CHECK_QX", "0");
			if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
				roleService.add(pd);
				log(logger, "新增角色   ROLE_ID:"+UUID);
			}
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
	 * 请求编辑页面
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String ROLE_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存编辑信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "用户"+getUid()+",请求修改角色名");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				log(logger, "修改角色  ID:"+pd.getString("ROLE_ID"));
				pd = roleService.edit(pd);
			}
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
	 * 请求角色菜单授权页面
	 * @param ROLE_ID
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auth")
	public String auth(@RequestParam String ROLE_ID, Model model) throws Exception {

		try {
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			String roleRights = role.getRIGHTS();
			if (Tools.notEmpty(roleRights)) {
				for (Menu menu : menuList) {
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if (menu.isHasMenu()) {
						List<Menu> subMenuList = menu.getSubMenu();
						for (Menu sub : subMenuList) {
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", ROLE_ID);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return "system/role/role_auth";
	}

	/**
	 * 请求角色按钮授权页面
	 * @param ROLE_ID
	 * @param msg
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/button")
	public ModelAndView button(@RequestParam String ROLE_ID, @RequestParam String msg) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			
			String roleRights = "";
			if ("add_qx".equals(msg)) {
				roleRights = role.getADD_QX();
			} else if ("del_qx".equals(msg)) {
				roleRights = role.getDEL_QX();
			} else if ("edit_qx".equals(msg)) {
				roleRights = role.getEDIT_QX();
			} else if ("cha_qx".equals(msg)) {
				roleRights = role.getCHA_QX();
			}   

			if (Tools.notEmpty(roleRights)) {
				for (Menu menu : menuList) {
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if (menu.isHasMenu()) {
						List<Menu> subMenuList = menu.getSubMenu();
						for (Menu sub : subMenuList) {
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			//System.out.println(json);
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			mv.addObject("zTreeNodes", json);
			mv.addObject("roleId", ROLE_ID);
			mv.addObject("msg", msg);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/role_button");
		return mv;
	}

	/**
	 * 保存角色菜单权限
	 * @param ROLE_ID
	 * @param menuIds
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID, @RequestParam String menuIds, PrintWriter out) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求保存角色菜单权限");
		PageData pd = new PageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				if (null != menuIds && !"".equals(menuIds.trim())) {
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					Role role = roleService.getRoleById(ROLE_ID);
					role.setRIGHTS(rights.toString());
					roleService.updateRoleRights(role);
					pd.put("rights", rights.toString());
				} else {
					Role role = new Role();
					role.setRIGHTS("");
					role.setROLE_ID(ROLE_ID);
					roleService.updateRoleRights(role);
					pd.put("rights", "");
				}
				pd.put("roleId", ROLE_ID);
				roleService.setAllRights(pd);
				log(logger, "修改角色菜单权限  ID:"+ROLE_ID);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}

	/**
	 * 保存角色按钮权限
	 * @param ROLE_ID
	 * @param menuIds
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/roleButton/save")
	public void orleButton(@RequestParam String ROLE_ID, @RequestParam String menuIds, @RequestParam String msg, PrintWriter out) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求保存角色按钮权限");
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				if (null != menuIds && !"".equals(menuIds.trim())) {
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					pd.put("value", rights.toString());
				} else {
					pd.put("value", "");
				}
				pd.put("ROLE_ID", ROLE_ID);
				roleService.updateQx(msg, pd);
				log(logger, "修改角色按钮权限 ID:"+ROLE_ID);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}

	/**
	 * 删除
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求删除组织角色");
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		String errInfo = "";
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
				pd.put("ROLE_ID", ROLE_ID);
				List<Role> roleList_z = roleService.listAllRolesByPId(pd); // 列出此部门的所有下级
				if (roleList_z.size() > 0) {
					errInfo = "false";
				} else {
					List<PageData> userlist = roleService.listAllUByRid(pd); //列出此角色下的所有用户
					if (userlist.size() > 0) {
						errInfo = "false2";
					} else {
						roleService.deleteRoleById(ROLE_ID);
						log(logger, "删除组织角色  ID:"+ROLE_ID);
						errInfo = "success";
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		logAfter(logger);
		return AppUtil.returnObject(new PageData(), map);
	}


}
