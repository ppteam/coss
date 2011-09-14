package com.neusoft.model.report.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {针对个人日报的增删改操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 13, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
public class DailyReportBaseFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = 3651210578081468795L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DailyReportBaseFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		List<DailyReport> reports = context.getObject(key, List.class);
		if (logger.isDebugEnabled()) {
			logger.debug("Handler  List<DailyReport> size is [" + reports.size() + "]");
		}
		for (DailyReport item : reports) {
			if (StringUtils.hasLength(item.getId())) {
				int rows = dailyReportDao.updateById(item);
				if (logger.isDebugEnabled()) {
					logger.debug("update dailyReport [" + item.getId() + "]and res [" + rows + "]");
				}
			} else {
				item.setDreportID(CommonUtils.getUUIDSeq());
				if (item.getUserID().equals(context.get(LoginUser.KEY_USERID).toString())) {
					item.setDeptID(context.getObject(LoginUser.KEY_USER_BAEN, UserBaseInfo.class).getDeptId());
				}
				dailyReportDao.insert(item);
			}
			context.putResults(getDefResultKey(), item.getId());
		} // end_for
		return false;
	}

	private String key = "todoList";

	public void setKey(String key) {
		this.key = key;
	}

}
