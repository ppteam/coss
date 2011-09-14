package com.neusoft.model.udmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {加载用户角色信息} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 9, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class LoadRoleSetByUserListFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoadRoleSetByUserListFilter.class);

	private static final long serialVersionUID = -1661000785545171376L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		Assert.hasLength(key_userList, "please init [p:key_userList] value....");

		List<UserBaseInfo> userList = (List<UserBaseInfo>) context.getValueByAll(key_userList);
		String needRole = context.getString(key_needRole) == null ? "no" : context.getString(key_needRole);
		if (needRole.equals("yes") && !CollectionUtils.isEmpty(userList)) {
			List<String> userIds = new ArrayList<String>(userList.size());
			for (UserBaseInfo user : userList) {
				userIds.add(user.getId());
			}
			context.put("userIds", userIds);
			List<Map<String, String>> roleList = sqlMapClientTemplate.queryForList("UserBaseInfo.load_role_by_userIds",
					context);
			if (!CollectionUtils.isEmpty(roleList)) {
				for (UserBaseInfo user : userList) {
					for (Map<String, String> map : roleList) {
						if (user.getId().equals(map.get("userId"))) {
							user.getRoleSet().add(map.get("dataId"));
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug("[" + user.getName() + "] roleSet:{" + user.getRoleSet() + "}");
					}
				}
			}
		} // end_if
		return false;
	} // end_fun

	private String key_userList;

	private String key_needRole = "needRole";

	public void setKey_userList(String key_userList) {
		this.key_userList = key_userList;
	}

	public void setKey_needRole(String key_needRole) {
		this.key_needRole = key_needRole;
	}

}