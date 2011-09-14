package com.neusoft.model.udmgr.service;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.common.Iface.IConstant;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {新增\修改用户基本信息操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleTeamLogsFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.notNull(userKey, "HandleTeamLogsFilter [p:userKey] can't be null");
		UserBaseInfo baseInfo = (UserBaseInfo) context.getValueByAll(userKey);
		Assert.notNull(baseInfo, "HandleTeamLogsFilter UserBaseInfo can't be null");
		// 新增用户情况
		if (context.containsKey(IConstant.DB_BASE_ACTION)) {
			if (context.getInteger(IConstant.DB_BASE_ACTION) == IConstant.DB_BASE_INSERT) {
				teamTranLogDao.insert(initTransLogs(baseInfo));
			}
		}
		return false;
	} // end_fun

	private String userKey;

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
