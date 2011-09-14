package com.neusoft.model.pwreport.service;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.pwreport.dao.pojo.PweeklyMemberplan;
import com.neusoft.model.pwreport.dao.pojo.PweeklyProblems;
import com.neusoft.model.pwreport.dao.pojo.PweeklyReport;
import com.neusoft.model.pwreport.exception.HandleprowReportException;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 25, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandlerPweeklyReportFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandlerPweeklyReportFilter.class);

	private static final long serialVersionUID = -1960799806762822074L;

	public HandlerPweeklyReportFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		PweeklyReport instance = context.getObject(key, PweeklyReport.class);
		if (instance.getId() == null) {
			Integer count = (Integer) context.getValueByAll(key_count);
			if (count != 0) {
				throw new HandleprowReportException("0000", HandleprowReportException.EXP_TYPE_EXCPT, null, null,
						new String[] { instance.getReportNo() });
			}
			instance.setReportId(CommonUtils.getUUIDSeq());
			String userId = pweeklyReportDao.quertForSingle("PtransferLogs.query_psmer_by_projectId",
					instance.getProjectId());
			instance.setPsmerId(userId);
			instance.setUserId(context.getString(LoginUser.KEY_USERID));
			pweeklyReportDao.insert("insert-Report", instance);
			context.getResultMap().put("insert_id", instance.getId());
			if (logger.isDebugEnabled()) {
				logger.debug("save PweeklyReport is ok,Id is [" + instance.getId() + "]");
			}
		} else {
			int row = pweeklyReportDao.update("update_report", instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update PweeklyReport is ok,Id is [" + instance.getId() + "]");
			}
			context.getResultMap().put("update_row", row);
		}
		updateProblems(instance);
		updatetPlans(instance);
		updatetStats(instance);
		return false;
	}

	/**
	 * {保存更新周报问题列表}
	 * 
	 * @param instance
	 */
	private void updateProblems(PweeklyReport instance) throws BaseException {
		pweeklyReportDao.delete("delete_prolems_by_rep", instance.getId());
		if (!CollectionUtils.isEmpty(instance.getProblems())) {
			for (PweeklyProblems problems : instance.getProblems()) {
				problems.setReportId(instance.getId());
				problems.setProjectId(instance.getProjectId());
				problems.setProblemId(CommonUtils.getUUIDSeq());
				pweeklyReportDao.insert("insert-Problems", problems);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[" + instance.getId() + "] update Problems size [" + instance.getProblems().size() + "]");
			}
		}
	}

	/**
	 * {保存更新周报问题列表}
	 * 
	 * @param instance
	 */
	private void updatetPlans(PweeklyReport instance) throws BaseException {
		pweeklyReportDao.delete("delete_plans_by_rep", instance.getId());
		if (!CollectionUtils.isEmpty(instance.getMemberplans())) {
			for (PweeklyMemberplan plan : instance.getMemberplans()) {
				plan.setReportId(instance.getId());
				plan.setDataId(CommonUtils.getUUIDSeq());
				pweeklyReportDao.insert("insert-Memberplan", plan);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[" + instance.getId() + "] update projectPlan size [" + instance.getMemberplans().size()
						+ "]");
			}
		}
	}

	/**
	 * {更新被修改的项目状态列表}
	 * 
	 * @param instance
	 */
	private void updatetStats(PweeklyReport instance) throws BaseException {
		if (!CollectionUtils.isEmpty(instance.getStats())) {
			for (ProjectStats stats : instance.getStats()) {
				proStatsDao.updateById(stats);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[" + instance.getProjectId() + "] update projectStats size ["
						+ instance.getStats().size() + "]");
			}
		}
	}

	private String key_count;

	private String key = "instance";

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey_count(String key_count) {
		this.key_count = key_count;
	}

}
