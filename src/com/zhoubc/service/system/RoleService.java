package com.zhoubc.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhoubc.bean.system.Role;
import com.zhoubc.dao.impl.DaoSupport;
import com.zhoubc.util.PageData;

/**
 * 类名称：RoleService.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Service("roleService")
public class RoleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询二级角色信息
	 * @return
	 * @throws Exception
	 */
	public List<Role> listAllERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERRoles", null);

	}

	/**
	 * 查询所有顶级角色 
	 * @return
	 * @throws Exception
	 */
	public List<Role> listAllRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRoles", null);
	}

	/**
	 * 通过当前登录用的角色id获取管理权限数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findGLbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findGLbyrid", pd);
	}

	/**
	 * 通过当前登录用的角色id获取用户权限数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findYHbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findYHbyrid", pd);
	}

	/**
	 * 列出此角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllUByRid", pd);

	}

	/**
	 * 列出此部门的所有下级
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);

	}

	/**
	 * 删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);

	}

	/**
	 * 根据id查询角色
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String roleId) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", roleId);

	}
	
	/**
	 * 修改权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}

	/**
	 * 权限(增删改查)
	 * @param msg
	 * @param pd
	 * @throws Exception
	 */
	public void updateQx(String msg, PageData pd) throws Exception {
		dao.update("RoleMapper." + msg, pd);
	}

	/**
	 * 给全部子职位加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
	}

	/**
	 * 新增角色
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.findForList("RoleMapper.insert", pd);
	}

	/**
	 * 通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findObjectById", pd);
	}

	/**
	 * 编辑角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.edit", pd);
	}

}
