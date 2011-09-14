package com.neusoft.model.eweekreport.facade.Impl;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;
import com.neusoft.model.eweekreport.facade.Iface.IWeekReportAction;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 19, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class WeekReportActionImpl extends AbstractAjaxAction implements IWeekReportAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeekReportActionImpl.class);

	public WeekReportActionImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.model.eweekreport.facade.Iface.IWeekReportAction#
	 * saveOrUpdateEWeekReport
	 * (com.neusoft.model.eweekreport.dao.pojo.EWeekReport)
	 */
	public CallBackModel saveOrUpdateEWeekReport(EWeekReport eWeekReport) {
		IContext context = creatContext();
		try {
			context.put("instance", eWeekReport);
			initContext(context);
			if ("remove".equals(eWeekReport.getWorkSummary())) {
				executeChain(context, "remove_eWeekReport_chain");
			} else {
				eWeekReport.setUserID(context.get(LoginUser.KEY_USERID).toString());
				executeChain(context, "save_update_eWeekReport_chain");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

}
