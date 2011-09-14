package com.neusoft.model.udmgr.service;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.udmgr.dao.pojo.PtransferLogs;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {新增用户操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleProMemberFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandleProMemberFilter.class);

	private static final long serialVersionUID = -1661098785545171123L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		PtransferLogs instance = context.getObject(paramKey, PtransferLogs.class);
		if (StringUtils.hasLength(instance.getId())) { // update
			int row = proMemberDao.updateById(instance);
			context.getResultMap().put("update_rows", row);
			if (logger.isDebugEnabled()) {
				logger.debug("update PtransferLogs [" + instance.getId() + "] is success ...");
			}
		} else { // insert
			instance.setStatsId(CommonUtils.getUUIDSeq());
			String id = proMemberDao.insert(instance);
			context.getResultMap().put("instance_id", id);
			if (logger.isDebugEnabled()) {
				logger.debug("insert PtransferLogs [" + instance.getId() + "] is success ...");
			}
		}
		return false;
	} // end_fun

	private String paramKey;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
