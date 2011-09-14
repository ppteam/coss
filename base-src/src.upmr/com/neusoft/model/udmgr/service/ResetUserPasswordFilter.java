package com.neusoft.model.udmgr.service;

import org.apache.commons.codec.digest.DigestUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.neusoft.model.udmgr.exception.HandleUserException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {新增\修改 用户基本信息操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ResetUserPasswordFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		UserBaseInfo userBaseInfo = (UserBaseInfo) context.getValueByAll(userKey);
		String oldpwd = context.getString(oldPsdKey) == null ? "" : context.getString(oldPsdKey);
		if (oldpwd.equals("change")) {
			context.put("password", DigestUtils.md5Hex(userBaseInfo.getLoginId()));
		} else {
			if (!userBaseInfo.getPassword().equals(DigestUtils.md5Hex(oldpwd))) {
				throw new HandleUserException("0001", HandleUserException.EXP_TYPE_EXCPT, "", null, null);
			}
			String newpwd = context.getString(newPsdKey) == null ? userBaseInfo.getLoginId() : context
					.getString(newPsdKey);
			context.put("password", DigestUtils.md5Hex(newpwd));
		}
		int res = userBaseDao.update("update_user_psd", context);
		context.getResultMap().put("resetpsd", res);
		return false;
	} // end_fun

	private String oldPsdKey = "oldPsd";

	private String newPsdKey = "newPsd";

	private String userKey = "user";

	public void setOldPsdKey(String oldPsdKey) {
		this.oldPsdKey = oldPsdKey;
	}

	public void setNewPsdKey(String newPsdKey) {
		this.newPsdKey = newPsdKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
