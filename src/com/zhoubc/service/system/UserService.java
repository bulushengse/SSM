package com.zhoubc.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhoubc.bean.Page;
import com.zhoubc.bean.system.User;
import com.zhoubc.dao.impl.DaoSupport;
import com.zhoubc.util.PageData;

/**
 * 类名称：UserService.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUiId", pd);
	}

	/**
	 * 通过loginname获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUId", pd);
	}

	/**
	 * 通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUE", pd);
	}

	/**
	 * 新增用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("UserXMapper.saveU", pd);
	}

	/**
	 * 修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("UserXMapper.editU", pd);
	}

	/**
	 * 换皮肤
	 * @param pd
	 * @throws Exception
	 */
	public void setSKIN(PageData pd) throws Exception {
		dao.update("UserXMapper.setSKIN", pd);
	}

	/**
	 * 删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd) throws Exception {
		dao.delete("UserXMapper.deleteU", pd);
	}

	/**
	 * 批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("UserXMapper.deleteAllU", USER_IDS);
	}

	/**
	 * 用户列表(用户组)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}

	/**
	 * 用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.listAllUser", pd);
	}

	/**
	 * 用户列表(供应商用户)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listGPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userGlistPage", page);
	}

	/**
	 * 保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserXMapper.saveIP", pd);
	}

	/**
	 * 登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.getUserInfo", pd);
	}

	/**
	 * 更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserXMapper.updateLastLogin", pd);
	}

	/**
	 * 通过id获取数据
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

}
