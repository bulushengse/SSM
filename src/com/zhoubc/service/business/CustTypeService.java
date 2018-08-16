package com.zhoubc.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhoubc.bean.Page;
import com.zhoubc.dao.impl.DaoSupport;
import com.zhoubc.util.PageData;

/**
 * 类名称：CustTypeService.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Service("custtypeService")
public class CustTypeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CustTypeMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("CustTypeMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("CustTypeMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustTypeMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustTypeMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustTypeMapper.findById", pd);
	}

}

