package com.gtx.controller.${packageName};

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gtx.controller.BaseController;
import com.gtx.bean.Page;
import com.gtx.util.AppUtil;
import com.gtx.util.ObjectExcelView;
import com.gtx.util.Const;
import com.gtx.util.PageData;
import com.gtx.util.Tools;
import com.gtx.util.Jurisdiction;
import com.gtx.service.${packageName}.${objectName}Service;

/** 
 * 类名称：${objectName}Controller
 * 创建人：研发中心 
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * 只要新增菜单url  ${objectNameLower}/list.do  代码生成的模块就完成了
 */
@Controller
@RequestMapping(value="/${objectNameLower}")
public class ${objectName}Controller extends BaseController {
	
	String menuUrl = "${objectNameLower}/list.do"; //菜单地址(权限用)
	@Resource(name="${objectNameLower}Service")
	private ${objectName}Service ${objectNameLower}Service;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger,  "用户ID:"+getUid()+",请求新增${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("${objectNameUpper}_ID", this.get32UUID());	//主键
<#list fieldList as var>
	<#if var[3] == "否">
		<#if var[1] == 'Date'>
		pd.put("${var[0]}", Tools.date2Str(new Date()));	//${var[2]}
		<#else>
		pd.put("${var[0]}", "${var[4]?replace("无","")}");	//${var[2]}
		</#if>
	</#if>
</#list>
		${objectNameLower}Service.save(pd);
		log(logger, "新增${objectName}  ID:"+pd.getString("${objectNameUpper}_ID"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger,  "用户ID:"+getUid()+"请求删除${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			${objectNameLower}Service.delete(pd);
			log(logger, "删除${objectName}  ID:"+pd.getString("${objectNameUpper}_ID"));
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger,  "用户ID:"+getUid()+"请求修改${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		${objectNameLower}Service.edit(pd);
		log(logger, "修改${objectName}  ID:"+pd.getString("${objectNameUpper}_ID"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "用户ID:"+getUid()+"请求显示${objectName}列表请求");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = ${objectNameLower}Service.list(page);	//列出${objectName}列表
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = ${objectNameLower}Service.findById(pd);	//根据ID读取
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger,  "用户ID:"+getUid()+"请求批量删除${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				${objectNameLower}Service.deleteAll(ArrayDATA_IDS);
				for(String str:ArrayDATA_IDS) {
						log(logger, "删除${objectName} ID:"+str);
					}
				pd.put("msg", "ok");
			}else{
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
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger,  "用户ID:"+getUid()+"请求导出${objectName}到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
	<#list fieldList as var>
			titles.add("${var[2]}");	//${var_index+1}
	</#list>
			dataMap.put("titles", titles);
			List<PageData> varOList = ${objectNameLower}Service.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
	<#list fieldList as var>
			<#if var[1] == 'Integer'>
				vpd.put("var${var_index+1}", varOList.get(i).get("${var[0]}").toString());	//${var_index+1}
			<#else>
				vpd.put("var${var_index+1}", varOList.get(i).getString("${var[0]}"));	//${var_index+1}
			</#if>
	</#list>
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
		return mv;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
