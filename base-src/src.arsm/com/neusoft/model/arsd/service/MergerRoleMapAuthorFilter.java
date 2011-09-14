package com.neusoft.model.arsd.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;
import com.neusoft.model.arsd.dao.pojo.RoleInfo;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {合并角色与权限映射关系} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class MergerRoleMapAuthorFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MergerRoleMapAuthorFilter.class);

	private static final long serialVersionUID = -1661357785545171964L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		List<RoleInfo> roleList = (List<RoleInfo>) context.getValueByAll(keyRole);
		List<AuthorityInfo> authList = (List<AuthorityInfo>) context.getValueByAll(keyAuth);
		if (!CollectionUtils.isEmpty(roleList) && !CollectionUtils.isEmpty(authList)) {
			if (logger.isDebugEnabled()) {
				logger.debug("List<RoleInfo> size [" + roleList.size() + "] and List<AuthorityInfo> size ["
						+ authList.size() + "]");
			}

			for (AuthorityInfo auth : authList) {
				for (RoleInfo role : roleList) {
					if (role.getId().equals(auth.getRoleId())) {
						role.putAuthorityInfo(auth);
					}
				}
			}
		} // end_if
		return false;
	} // end_fun

	private String keyRole = "json_roles";

	private String keyAuth = "author_list";

	public void setKeyRole(String keyRole) {
		this.keyRole = keyRole;
	}

	public void setKeyAuth(String keyAuth) {
		this.keyAuth = keyAuth;
	}

}
