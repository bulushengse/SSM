package com.zhoubc.controller.system;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.Page;
import com.zhoubc.bean.system.Role;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.MenuService;
import com.zhoubc.service.system.RoleService;
import com.zhoubc.service.system.UserService;
import com.zhoubc.util.AppUtil;
import com.zhoubc.util.Const;
import com.zhoubc.util.FileDownload;
import com.zhoubc.util.FileUpload;
import com.zhoubc.util.GetPinyin;
import com.zhoubc.util.Jurisdiction;
import com.zhoubc.util.ObjectExcelRead;
import com.zhoubc.util.ObjectExcelView;
import com.zhoubc.util.PageData;
import com.zhoubc.util.PathUtil;
import com.zhoubc.util.Tools;

/**
 * 类名称：UserController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	String menuUrl = "user/listUsers.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 新增用户
	 * @param out
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveU")
	public ModelAndView saveU(PrintWriter out) throws Exception {
		logBefore(logger,"用户"+getUid()+",请求新增用户");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("USER_ID", this.get32UUID());// ID
		pd.put("RIGHTS", ""); 				// 权限
		pd.put("LAST_LOGIN", ""); 		// 最后登录时间
		pd.put("IP", ""); 					        // IP
		pd.put("STATUS", "0"); 				// 状态
		pd.put("SKIN", "default");			// 默认皮肤

		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());

		if (null == userService.findByUId(pd)) {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) { // 判断新增权限
				userService.saveU(pd);
				log(logger,"新增用户  ID:"+pd.getString("USER_ID"));
				mv.addObject("msg", "success");
			}
		} else {
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	/**
	 * 判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUId(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断邮箱是否存在
	 * @return
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			if (userService.findByUE(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU() throws Exception {
		logBefore(logger,"用户"+getUid()+",请求修改用户");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			userService.editU(pd);
			log(logger, "修改用户   ID:"+pd.getString("USER_ID"));
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	/**
	 * 去修改用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			// 顶部修改个人资料
			String fx = pd.getString("fx");
			if ("head".equals(fx)) {
				mv.addObject("fx", "head");
			} else {
				mv.addObject("fx", "user");
			}
			List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
			pd = userService.findByUiId(pd); // 根据ID读取
			mv.setViewName("system/user/user_edit");
			mv.addObject("msg", "editU");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Role> roleList;
		try {
			roleList = roleService.listAllERRoles(); // 列出所有二级角色
			mv.setViewName("system/user/user_edit");
			mv.addObject("msg", "saveU");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 显示用户列表(用户组)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		logBefore(logger,"用户"+getUid()+",请求显示用户列表(用户组)");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String USERNAME = pd.getString("USERNAME");
		if (null != USERNAME && !"".equals(USERNAME)) {
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}
		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");

		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			lastLoginStart = lastLoginStart + " 00:00:00";
			pd.put("lastLoginStart", lastLoginStart);
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			lastLoginEnd = lastLoginEnd + " 00:00:00";
			pd.put("lastLoginEnd", lastLoginEnd);
		}

		page.setPd(pd);
		List<PageData> userList = userService.listPdPageUser(page); // 列出用户列表
		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
		for (int i = 0; i < userList.size(); i++) {
			PageData  p = userList.get(i);
		}
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		
		logAfter(logger);
		return mv;
	}

	/**
	 * 显示用户列表(tab方式)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listtabUsers")
	public ModelAndView listtabUsers(Page page) throws Exception {
		logBefore(logger,"用户"+getUid()+",请求显示用户列表(tab方式)");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> userList = userService.listAllUser(pd); // 列出用户列表
		mv.setViewName("system/user/user_tb_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		logAfter(logger);
		return mv;
	}

	/**
	 * 删除用户
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		logBefore(logger,"用户"+getUid()+",请求删除用户");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
				userService.deleteU(pd);
				log(logger,  "删除用户  ID:"+pd.getString("USER_ID"));
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}

	/**
	 * 批量删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception{
		logBefore(logger,"用户"+getUid()+",请求批量删除用户");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String USER_IDS = pd.getString("USER_IDS");

			if (null != USER_IDS && !"".equals(USER_IDS)) {
				String ArrayUSER_IDS[] = USER_IDS.split(",");
				if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
					userService.deleteAllU(ArrayUSER_IDS);
					for(String str:ArrayUSER_IDS) {
						log(logger, "删除用户  ID:"+str);
					}
				}
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}

			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	// ===================================================================================================

	/**
	 * 导出用户信息到EXCEL
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, "用户"+getUid()+",请求导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				// 检索条件===
				String USERNAME = pd.getString("USERNAME");
				if (null != USERNAME && !"".equals(USERNAME)) {
					USERNAME = USERNAME.trim();
					pd.put("USERNAME", USERNAME);
				}
				String lastLoginStart = pd.getString("lastLoginStart");
				String lastLoginEnd = pd.getString("lastLoginEnd");
				if (lastLoginStart != null && !"".equals(lastLoginStart)) {
					lastLoginStart = lastLoginStart + " 00:00:00";
					pd.put("lastLoginStart", lastLoginStart);
				}
				if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
					lastLoginEnd = lastLoginEnd + " 00:00:00";
					pd.put("lastLoginEnd", lastLoginEnd);
				}
				// 检索条件===

				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<String> titles = new ArrayList<String>();

				titles.add("用户名"); // 1
				titles.add("姓名"); // 2
				titles.add("职位"); // 3
				titles.add("手机"); // 4
				titles.add("邮箱"); // 5
				titles.add("最近登录"); // 6
				titles.add("上次登录IP"); // 7

				dataMap.put("titles", titles);

				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for (int i = 0; i < userList.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME")); // 1
					vpd.put("var2", userList.get(i).getString("NAME")); // 3
					vpd.put("var3", userList.get(i).getString("ROLE_NAME")); // 4
					vpd.put("var4", userList.get(i).getString("PHONE")); // 5
					vpd.put("var5", userList.get(i).getString("EMAIL")); // 6
					vpd.put("var6", userList.get(i).getString("LAST_LOGIN")); // 7
					vpd.put("var7", userList.get(i).getString("IP")); // 8
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
				mv = new ModelAndView(erv, dataMap);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		logAfter(logger);
		return mv;
	}

	/**
	 * 打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}

	/**
	 * 下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {

		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");

	}

	/**
	 * 从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		logBefore(logger, "用户"+getUid()+",请求从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传

			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0); // 执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet

			/* 存入数据库操作====================================== */
			pd.put("RIGHTS", ""); // 权限
			pd.put("LAST_LOGIN", ""); // 最后登录时间
			pd.put("IP", ""); // IP
			pd.put("STATUS", "0"); // 状态
			pd.put("SKIN", "default"); // 默认皮肤

			List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色

			pd.put("ROLE_ID", roleList.get(0).getROLE_ID()); // 设置角色ID 随便第一个
			/**
			 *  var0 :姓名 var1 :手机 var2 :邮箱 var3 :备注
			 */
			for (int i = 0; i < listPd.size(); i++) {
				pd.put("USER_ID", this.get32UUID()); // ID
				
				String NAME = listPd.get(i).getString("var0"); // 姓名
				if(NAME!=null && NAME!="") {//姓名为空就跳过
					pd.put("NAME", NAME);
				}else {
					continue;
				}

				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var0")); // 根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);
				if (userService.findByUId(pd) != null) { // 判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var0")) + Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				
				String BZ = listPd.get(i).getString("var3");// 备注
				if(BZ!=null && BZ!="") {
					pd.put("BZ", listPd.get(i).getString("var3")); 
				}else {
					pd.put("BZ", ""); 		
				}
				
				String email = listPd.get(i).getString("var2");// 邮箱
				if(email!=null && email!="") {
					if (Tools.checkEmail(email)) { // 邮箱格式不对就跳过
						pd.put("EMAIL", email);
						//if (userService.findByUE(pd) != null) { // 邮箱已存在就跳过
						//	continue;
						//}
					} else {
						continue;
					}
				}else {
					pd.put("EMAIL", "");
				}
				
				String PHONE = listPd.get(i).getString("var1");// 手机号
				if(PHONE!=null && PHONE!="") {
					pd.put("PHONE", listPd.get(i).getString("var1")); 
				}else {
					pd.put("PHONE", ""); 
				}
				
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString()); // 默认密码123
				userService.saveU(pd);
				log(logger, "新增用户  ID:"+pd.getString("USER_ID"));
			}
			/* 存入数据库操作====================================== */
			mv.addObject("msg", "success");
		}
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}


	
}

