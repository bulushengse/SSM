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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhoubc.controller.BaseController;
import com.zhoubc.service.business.CustService;
import com.zhoubc.service.business.CustTypeService;
import com.zhoubc.util.Const;
import com.zhoubc.util.Jurisdiction;
import com.zhoubc.util.ObjectExcelView;
import com.zhoubc.util.PageData;

import net.sf.json.JSONArray;

/**
 * 类名称：CustTypeController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value="/custtype")
public class CustTypeController extends BaseController {
	
	String menuUrl = "custtype/list.do"; //菜单地址(权限用)
	@Resource(name="custtypeService")
	private CustTypeService custtypeService;
	
	@Resource(name="custService")
	private CustService custService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public void save(PrintWriter out) throws Exception{
		logBefore(logger,  "用户"+getUid()+",请求新增CustType");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){  //校验权限
			out.write("fail,null");
			out.close();
		} 
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("CUST_TYPE_ID", this.get32UUID());
			pd.put("ICONSKIN", "pIcon01");//ztree节点图标
			custtypeService.save(pd);
			log(logger, "新增CustType  ID:"+pd.getString("CUST_TYPE_ID"));
			String str = "success,"+pd.getString("CUST_TYPE_ID");
			out.write(str);
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger,  "用户"+getUid()+",请求删除CustType");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){   //校验权限
			out.write("fail");
			out.close();
		}
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			custtypeService.delete(pd);
			log(logger, "删除CustType  ID:"+pd.getString("CUST_TYPE_ID"));
			out.write("success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		} finally {
			out.close();
		}
		logAfter(logger);
	}
	
	/**
	 * 删除前验证
	 * @param out
	 * @param id
	 */
	@RequestMapping(value="/confirm")
	public void confirm(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<PageData> custList = custService.listAll(pd);
			if(custList.size() > 0) {
				out.println(1);
			}else {
				out.println(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public void edit(PrintWriter out) throws Exception{
		logBefore(logger,  "用户"+getUid()+",请求修改CustType");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){   //校验权限
			out.write("fail");
			out.close();
		} 
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			custtypeService.edit(pd);
			log(logger, "修改CustType  ID:"+pd.getString("CUST_TYPE_ID"));
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		logAfter(logger);
	}
	
	
	/**
	 * 列表   ztree  
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		logBefore(logger, "用户"+getUid()+",请求显示CustType列表请求");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			List<PageData>	varList = custtypeService.listAll(pd);	//列出CustType列表

			JSONArray arr = JSONArray.fromObject(varList);
			String custType = arr.toString();
			mv.setViewName("business/cust/cust");
			mv.addObject("custType", custType);
			mv.addObject(Const.SESSION_QX,this.getHC());			//按钮权限
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
			mv.setViewName("business/cust/custtype_edit");
			mv.addObject("msg", "save");
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
		logBefore(logger,  "用户"+getUid()+",请求导出CustType到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("编号");	//1
			titles.add("名称");	//2
			titles.add("上级编号");	//3
			dataMap.put("titles", titles);
			List<PageData> varOList = custtypeService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).get("CUST_TYPE_ID").toString());	//1
				vpd.put("var2", varOList.get(i).getString("CUST_TYPE_NAME"));	//2
				vpd.put("var3", varOList.get(i).get("FATHER_ID").toString());	//3
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
