package com.zhoubc.service.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhoubc.bean.Page;
import com.zhoubc.dao.impl.DaoSupport;
import com.zhoubc.util.PageData;

/**
 * 类名称：CustService.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Service("custService")
public class CustService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CustMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("CustMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("CustMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustMapper.datalistPage", page);
	}
	
	/**
	*列表(全部) 客户+客户类别
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustMapper.listAll", pd);
	}
	
	/**
	*列表(全部) 客户+客户类别
	*/
	public List<PageData> listAll3(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustMapper.listAll3", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustMapper.findById", pd);
	}
	
}

