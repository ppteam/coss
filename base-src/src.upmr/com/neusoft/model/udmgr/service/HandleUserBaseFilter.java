package com.neusoft.model.udmgr.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.common.Iface.IConstant;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.TeamTransLogs;
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
public class HandleUserBaseFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		UserBaseInfo instance = context.getObject(userKey, UserBaseInfo.class);
		if (StringUtils.hasLength(instance.getId())) { // 更新
			Integer rows = userBaseDao.update("update_runing_user", instance);
			context.put(IConstant.DB_BASE_ACTION, IConstant.DB_BASE_UPDATE);
			context.putResults("update_rows", rows);
		} else { // 新增
			instance.setUserId(CommonUtils.getUUIDSeq());
			// 处理密码 默认与登录名一致
			instance.setPassword(DigestUtils.md5Hex(instance.getLoginId()));
			String userId = userBaseDao.insert(instance);
			instance.setUserId(userId);
			logInserUser(instance);
			context.putResults("saveRes", "success");
		}
		return false;
	} // end_fun

	/**
	 * {记录进场记录}
	 * 
	 * @param baseInfo
	 * @throws BaseException
	 */
	private void logInserUser(UserBaseInfo baseInfo) throws BaseException {
		TeamTransLogs logs = new TeamTransLogs();
		logs.setDeptId(baseInfo.getDeptId());
		logs.setUserId(baseInfo.getUserId());
		logs.setTransferId(CommonUtils.getUUIDSeq());
		teamTranLogDao.insert(logs);
	}

	private String userKey;

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
