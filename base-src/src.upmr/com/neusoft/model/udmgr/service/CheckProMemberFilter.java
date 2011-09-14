package com.neusoft.model.udmgr.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.udmgr.dao.pojo.PtransferLogs;
import com.neusoft.model.udmgr.exception.HandleProjectException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {校验项目-人员的合法性} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckProMemberFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckProMemberFilter.class);

	private static final long serialVersionUID = -1561098785545171123L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		PtransferLogs instance = context.getObject(paramKey, PtransferLogs.class);
		List<PtransferLogs> members = proMemberDao.findByParams("exist_Logs", instance);

		if (CollectionUtils.isNotEmpty(members)) {
			for (PtransferLogs m : members) {
				if (m.getUserId().equals(instance.getUserId())) {
					logger.error("the same one people put project twice");
					throw new HandleProjectException("0003", HandleProjectException.EXP_TYPE_EXCPT,
							"Project will match more than one pm or tm", null, null);
				} // end_if
			}
		} // end_if

		if (SEQ_ROLETYPES.indexOf(instance.getRoleType()) != -1 && CollectionUtils.isNotEmpty(members)) {
			for (PtransferLogs m : members) {
				if (instance.getRoleType().equals(m.getRoleType())) {
					throw new HandleProjectException("0002", HandleProjectException.EXP_TYPE_EXCPT,
							"Project will match more than one pm or tm", null, null);
				} // end_if
			}
		} // end_if

		return false;
	} // end_fun

	private String paramKey;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	private static String SEQ_ROLETYPES = "c401a1050fe64b29ba60997e2a576d52|9e635bb0864f4833aa06f0f1336cad34";
}
