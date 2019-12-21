package com.zhoubc.controller.business;

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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.bean.Page;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.business.CustService;
import com.zhoubc.util.Const;
import com.zhoubc.util.Jurisdiction;
import com.zhoubc.util.ObjectExcelView;
import com.zhoubc.util.PageData;
import com.zhoubc.util.StringUtil;

/**
 * 类名称：CustController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value="/cust")
public class CustController extends BaseController {
	
	String menuUrl = "cust/list.do"; //菜单地址(权限用)
	@Resource(name="custService")
	private CustService custService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger,  "用户"+getUid()+",请求新增Cust");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUST_ID", this.get32UUID());
		custService.save(pd);
		log(logger, "新增Cust  ID:" + pd.getString("CUST_ID"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger,  "用户"+getUid()+",请求删除Cust");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			custService.delete(pd);
			log(logger, "删除Cust  ID:"+pd.getString("CUST_ID"));
			out.write("success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		} finally {
			out.close();
		}
		logAfter(logger);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger,  "用户"+getUid()+",请求修改Cust");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		custService.edit(pd);
		log(logger, "修改Cust  ID:" + pd.getString("CUST_ID"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		logAfter(logger);
		return mv;
	}
	
	/**
	 * 获取客户信息  (全部)
	 */
	@RequestMapping(value="/getCust")
	public void getCust(HttpServletResponse response) throws Exception{
		logBefore(logger, "用户"+getUid()+",请求获取客户信息");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("PROJECT_ID", pd.get("PROJECT_ID").toString());
			List<PageData>	varList = custService.listAll3(pd);	
			String str = "<option disabled=\"disabled\" selected=\"selected\" value=\"empty\">这里选择客户名称</option><option value=\"\">无</option>";
			String CUST_ID;
			String CUST_NAME;
			for(int i = 0; i < varList.size(); i++) {
				pd = varList.get(i);
				CUST_ID = pd.getToString("CUST_ID");
				CUST_NAME = pd.getString("CUST_NAME");
				str += ("<option value='"+CUST_ID+"'>"+CUST_NAME+"</option>");
			}
			response.setContentType("text/html"); 
			response.setCharacterEncoding("UTF-8"); 
			PrintWriter out = response.getWriter(); 
			out.write(str);
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, "用户"+getUid()+",请求显示Cust列表请求");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(!StringUtil.isEmpty(pd.getString("searchStr"))) {
				String temp=pd.getString("searchStr");
				pd.put("search","%"+temp.trim()+"%");
			}
			page.setPd(pd);
			List<PageData>	varList = custService.list(page);	//列出Cust列表
			mv.setViewName("business/cust/cust_list");
			mv.addObject("varList", varList);
			int icount=varList.size(); //terry
			mv.addObject("pd", pd);
			mv.addObject("icount", icount);    //terry
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
			mv.setViewName("business/cust/cust_edit");
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
			pd = custService.findById(pd);	//根据ID读取
			mv.setViewName("business/cust/cust_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger,  "用户"+getUid()+",请求导出Cust到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("客户编号");	//1
			titles.add("客户名称");	//2
			titles.add("客户类别");	//3
			titles.add("地址");		//4
			titles.add("联系人");		//5
			titles.add("联系方式");	//6
			dataMap.put("titles", titles);
			if(!StringUtil.isEmpty(pd.getString("searchStr"))) {
				String temp=pd.getString("searchStr");
				pd.put("search","%"+temp.trim()+"%");
			}
			List<PageData> varOList = custService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("CUST_ID").toString());	//1
				vpd.put("var2", varOList.get(i).getString("CUST_NAME"));	//2
				vpd.put("var5", varOList.get(i).getString("CUST_TYPE_NAME"));//3
				vpd.put("var6", varOList.get(i).getString("CUST_ADDR"));	//4
				vpd.put("var7", varOList.get(i).getString("CUST_CONTECT"));	//5
				vpd.put("var9", varOList.get(i).getString("CUST_PHONE"));	//6
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
