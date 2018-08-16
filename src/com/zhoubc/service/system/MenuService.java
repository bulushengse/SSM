package com.zhoubc.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhoubc.bean.system.Menu;
import com.zhoubc.dao.impl.DaoSupport;
import com.zhoubc.util.PageData;

/**
 * 类名称：MenuService.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Service("menuService")
public class MenuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 删除菜单
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}
	
	/**
	 * 查询菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}
	
	/**
	 * 查询最大id菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}
	
	/**
	 * 查询所有顶级菜单
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllParentMenu() throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAllParentMenu", null);
	}

	/**
	 * 保存
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception {
		if (menu.getMENU_ID() != null && menu.getMENU_ID() != "") {
			dao.save("MenuMapper.insertMenu", menu);
		} else {
			dao.save("MenuMapper.insertMenu", menu);
		}
	}
	
	/**
	 * 查询父类菜单下的所有子菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu() throws Exception {
		List<Menu> rl = this.listAllParentMenu();
		for (Menu menu : rl) {
			List<Menu> subList = this.listSubMenuByParentId(menu.getMENU_ID());
			menu.setSubMenu(subList);
		}
		return rl;
	}

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAll() throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAll", null);
	}

	/**
	 * 编辑
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.updateMenu", pd);
	}

	/**
	 * 保存菜单图标 (顶部菜单)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.editicon", pd);
	}

	/**
	 * 编辑父菜单下的所有子菜单的类型（系统，业务）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editType(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.editType", pd);
	}
}
