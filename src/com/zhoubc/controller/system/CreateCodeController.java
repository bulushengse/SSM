package com.zhoubc.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhoubc.controller.BaseController;
import com.zhoubc.util.DelAllFile;
import com.zhoubc.util.FileDownload;
import com.zhoubc.util.FileZip;
import com.zhoubc.util.Freemarker;
import com.zhoubc.util.PageData;
import com.zhoubc.util.PathUtil;

/**
 * 类名称：CreateCodeController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/createCode")
public class CreateCodeController extends BaseController {

	/**
	 *  代码生成
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/proCode")
	public void proCode(HttpServletResponse response) throws Exception {
		logBefore(logger, "代码生成请求");
		PageData pd = new PageData();
		pd = this.getPageData();

		/* ============================================================================================= */
		String packageName = pd.getString("packageName"); // 包名 ========1
		String objectName = pd.getString("objectName"); // 类名 ========2
		String tabletop = pd.getString("tabletop"); // 表前缀 ========3
		tabletop = null == tabletop ? "" : tabletop.toUpperCase(); // 表前缀转大写
		String zindext = pd.getString("zindex"); // 属性总数
		int zindex = 0;
		if (null != zindext && !"".equals(zindext)) {
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>(); // 属性集合 ========4
		for (int i = 0; i < zindex; i++) {
			fieldList.add(pd.getString("field" + i).split(",mbfw,")); // 属性放到集合里面
		}

		Map<String, Object> root = new HashMap<String, Object>(); // 创建数据模型
		root.put("fieldList", fieldList);
		root.put("packageName", packageName); // 包名
		root.put("objectName", objectName); // 类名
		root.put("objectNameLower", objectName.toLowerCase()); // 类名(全小写)
		root.put("objectNameUpper", objectName.toUpperCase()); // 类名(全大写)
		root.put("tabletop", tabletop); // 表前缀
		root.put("nowDate", new Date()); // 当前日期

		DelAllFile.delFolder(PathUtil.getClasspath() + "admin/ftl"); // 生成代码前,先清空之前生成的代码
		/* ============================================================================================= */

		String filePath = "admin/ftl/code/"; // 存放路径
		String ftlPath = "createCode"; // ftl路径

		/* 生成controller */
		Freemarker.printFile("controllerTemplate.ftl", root, "com/gtx/controller/" + packageName + "/"  + objectName + "Controller.java", filePath, ftlPath);

		/* 生成service */
		Freemarker.printFile("serviceTemplate.ftl", root, "com/gtx/service/" + packageName + "/" + objectName + "Service.java", filePath, ftlPath);

		/* 生成mybatis xml */
		Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis/" + packageName + "/" + objectName + "Mapper.xml", filePath, ftlPath);

		/* 生成SQL脚本 */
		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);

		/* 生成jsp页面 */
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_edit.jsp", filePath, ftlPath);

		/* 生成的全部代码压缩成zip文件 */
		FileZip.zip(PathUtil.getClasspath() + "admin/ftl/code", PathUtil.getClasspath() + "admin/ftl/code.zip");
		log(logger, "功能模块：  控制层类名  "+objectName+"Controller.java");
		/* 下载代码 */
		FileDownload.fileDownload(response, PathUtil.getClasspath() + "admin/ftl/code.zip", "code.zip");
		logAfter(logger);
	}

}
