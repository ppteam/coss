package com.neusoft.model.udmgr.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户角色授权操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 9, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleUserMapRoleFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171987L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		String userId = context.getString(key_userId);
		String[] roleIds = (String[]) context.get(key_roleIds);
		// 移除历史数据
		int recs = userBaseDao.delete("remove_user_map_role", userId);
		context.putResults("user_map_roles", recs);
		if (!ArrayUtils.isEmpty(roleIds)) {
			for (String roleId : roleIds) {
				Map<String, String> saveMap = new HashMap<String, String>();
				saveMap.put("mappId", CommonUtils.getUUIDSeq());
				saveMap.put("userId", userId);
				saveMap.put("roleId", roleId);
				userBaseDao.insert("insert_user_map_role", saveMap);
			}
			context.putResults("user_map_roles", roleIds.length);
		}
		return false;
	} // end_fun

	private String key_userId = "userId";

	private String key_roleIds = "roleIds";

	public void setKey_userId(String key_userId) {
		this.key_userId = key_userId;
	}

	public void setKey_roleIds(String key_roleIds) {
		this.key_roleIds = key_roleIds;
	}

}
