package com.neusoft.model.report.facade.Impl;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.report.facade.Iface.IRemoteAction;
import com.springsource.util.common.StringUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {日报管理增改接口的实现类} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 14, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
public class RemoteActionImpl extends AbstractAjaxAction implements IRemoteAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RemoteActionImpl.class);

	public RemoteActionImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.model.report.facade.Iface.RemoteDailyReportActionImpl#
	 * saveOrUpdateDailyReport (com.neusoft.model.report.dao.pojo.DailyReport)
	 */
	public CallBackModel saveOrUpdateDailyReport(DailyReport dailyReport) {
		IContext context = creatContext();
		try {
			context.put("instance", dailyReport);
			if ("remove".equals(dailyReport.getProjectName())) {
				executeChain(context, "remove_eDailyReport_chain");
			} else {
				initContext(context);
				if (!StringUtils.hasLength(dailyReport.getUserID())) {
					dailyReport.setUserID(context.get(LoginUser.KEY_USERID).toString());
				}
				dailyReport.setRecordorID(context.get(LoginUser.KEY_USERID).toString());
				executeChain(context, "saveOrupdate_eDailyReport_chain");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.model.report.facade.Iface.IRemoteDailyReportAction#
	 * batEditDailyReport(java.util.List)
	 */
	public CallBackModel batEditDailyReport(DailyReport[] dailyReports) {
		IContext context = creatContext();
		try {
			context.put("instances", dailyReports);
			initContext(context);
			executeChain(context, "batch_edit_dailyReps_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

}
