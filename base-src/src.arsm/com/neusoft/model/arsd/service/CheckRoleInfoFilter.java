package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.RoleInfo;
import com.neusoft.model.arsd.exception.DictionaryException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {验证当前对象是否与数据库值有冲突} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckRoleInfoFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckRoleInfoFilter.class);

	private static final long serialVersionUID = -1661357785545171002L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		RoleInfo instance = context.getObject("instance", RoleInfo.class);
		List<String> srcIds = (List<String>) context.getResultMap().get("roleIds");
		if (StringUtils.hasLength(instance.getId())) { // update
			if (!CollectionUtils.isEmpty(srcIds)) {
				for (String id : srcIds) {
					if (!id.equals(instance.getId())) {
						logger.error("[entry:" + instance.getRoleName() + "] or["
								+ instance.getRoleSign() + "] has the same record in db");
						throw new DictionaryException("0000", "", null, new String[] {
								instance.getRoleName() + "] or[" + instance.getRoleSign(),
								"AuthorityInfo" });
					}
				}
			}
		} else { // insert
			if (!CollectionUtils.isEmpty(srcIds)) {
				logger.error("[entry:" + instance.getRoleName() + "] or[" + instance.getRoleSign()
						+ "] has the same record in db");
				throw new DictionaryException("0000", "", null,
						new String[] { instance.getRoleName() + "] or[" + instance.getRoleSign(),
								"AuthorityInfo" });
			}
		}
		return false;
	}

}
