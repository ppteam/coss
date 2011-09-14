package com.neusoft.model.report.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * {初始化查询条件 对于 登入人员而言}
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {初始化SQL条件} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 2, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class InitBatchHandlerFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitBatchHandlerFilter.class);

	private static final long serialVersionUID = -318508653678882164L;

	public InitBatchHandlerFilter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		DailyReport[] reports = (DailyReport[]) context.get("instances");
		List<DailyReport> dailyReports = new ArrayList<DailyReport>();
		if (ArrayUtils.isNotEmpty(reports)) {
			for (DailyReport report : reports) {
				report.setUserID(context.get(LoginUser.KEY_USERID).toString());
				report.setRecordorID(context.get(LoginUser.KEY_USERID).toString());
				dailyReports.add(report);
			} // end_for
			context.put("startDate", reports[0].getReportDate());
			context.put("endDate", reports[reports.length - 1].getReportDate());
			if (logger.isDebugEnabled()) {
				logger.debug("[startDate:" + reports[0].getReportDate() + "][endDate:"
						+ reports[reports.length - 1].getReportDate() + "]");
			} // end_if
			context.put("todoList", dailyReports);
		} else {
			return true;
		}
		return false;
	}

} // end_class
