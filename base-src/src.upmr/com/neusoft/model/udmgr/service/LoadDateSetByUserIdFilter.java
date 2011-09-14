package com.neusoft.model.udmgr.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.udmgr.dao.pojo.Department;

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
public class LoadDateSetByUserIdFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoadDateSetByUserIdFilter.class);

	private static final long serialVersionUID = -1661357785545171376L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		String userId = context.getString(key_userId);
		Set<String> mapping_ids = new HashSet<String>();
		List<Department> dept_list = sqlMapClientTemplate.queryForList("Department.load_access_by_userId",
				userId);
		List<Department> tempList = null;
		if (CollectionUtils.isNotEmpty(dept_list)) {
			for (Department item : dept_list) {
				tempList = sqlMapClientTemplate.queryForList("Department.query_leaf_dept_byid",
						"%" + item.getDeptId() + "%");
				for (Department dept : tempList) {
					mapping_ids.add(dept.getDeptId());
				}
			}
		} // end_if
		if (logger.isDebugEnabled()) {
			logger.debug("[user:" + userId + "]'s dataSet {" + mapping_ids + "}");
		}
		((List<String>) context.get(LoginUser.KEY_DATASET)).addAll(mapping_ids);
		return false;
	} // end_fun

	private String key_userId = LoginUser.KEY_USERID;

	public void setKey_userId(String key_userId) {
		this.key_userId = key_userId;
	}

}
