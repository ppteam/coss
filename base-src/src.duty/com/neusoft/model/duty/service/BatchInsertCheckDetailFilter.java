package com.neusoft.model.duty.service;

import org.apache.log4j.Logger;

import java.util.List;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.duty.dao.pojo.CheckDetail;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户签到 记录存储} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class BatchInsertCheckDetailFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BatchInsertCheckDetailFilter.class);

	private static final long serialVersionUID = -3279707848608464234L;

	public BatchInsertCheckDetailFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		List<CheckDetail> details = (List<CheckDetail>) context.getValueByAll(detailsKey);
		if (!CollectionUtils.isEmpty(details)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Total [" + details.size() + "] person init to check list");
			}
			for (CheckDetail detail : details) {
				detail.setDetailId(CommonUtils.getUUIDSeq());
				sqlMapClientTemplate.insert("CheckingRule.insert_batch_CheckDetail", detail);
			} // end_for
		} // end_if
		return false;
	} // end_fun

	private String detailsKey;

	public void setDetailsKey(String detailsKey) {
		this.detailsKey = detailsKey;
	}

}
