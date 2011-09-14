package com.neusoft.model.arsd.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {执行 增删改 操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class RoleMapAuthorFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleMapAuthorFilter.class);

	private static final long serialVersionUID = -1661357785545121098L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		String roleId = context.getString("roleId");
		String[] authorIds = (String[]) context.get("authorIds");
		// 删除全部映射
		int del_row = roleDao.delete("delete_map_by_roleId", roleId);
		if (logger.isDebugEnabled()) {
			logger.debug("[delete rows] value is [" + del_row + "]");
		}
		if (!ArrayUtils.isEmpty(authorIds)) {
			for (String authorId : authorIds) {
				Map<String, String> instance = new HashMap<String, String>();
				instance.put("mappingId", CommonUtils.getUUIDSeq());
				instance.put("authorId", authorId);
				instance.put("roleId", roleId);
				roleDao.insert("RoleInfo.role_map_author", instance);
			}
		}
		return false;
	}

}
