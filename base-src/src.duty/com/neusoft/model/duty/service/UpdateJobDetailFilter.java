package com.neusoft.model.duty.service;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.duty.dao.pojo.JobDetail;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {保存以及修改考勤规则} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class UpdateJobDetailFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UpdateJobDetailFilter.class);

	private static final long serialVersionUID = -3111707848608464806L;

	public UpdateJobDetailFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		if (logger.isDebugEnabled()) {
			logger.debug("execute(IContext) - start");
		}
		String detailId = (String) sqlMapClientTemplate.queryForObject("CheckingRule.query_exits_detail",
				jobId);
		if (detailId != null) {
			JobDetail detail = new JobDetail();
			detail.setDetailId(CommonUtils.getUUIDSeq());
			detail.setJobId(jobId);
			detail.setSplitDay(splitDay);
			detail.setChainName(chainName);
			sqlMapClientTemplate.insert("CheckingRule.insert_job", detail);
		} else {
			sqlMapClientTemplate.update("CheckingRule.update_job", detailId);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("execute(IContext) - end");
		}
		return false;
	} // end_fun

	private String jobId;

	private int splitDay;

	private String chainName;

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setSplitDay(int splitDay) {
		this.splitDay = splitDay;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

}
