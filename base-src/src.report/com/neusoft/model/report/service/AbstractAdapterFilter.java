package com.neusoft.model.report.service;

import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {检测当前操作目录的逻辑判断} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 14, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
public abstract class AbstractAdapterFilter extends FilterBase {

	private static final long serialVersionUID = 1425027424703104660L;

	protected IEntityDao<DailyReport, String> dailyReportDao;

	public AbstractAdapterFilter() {
		super();
	}

	public void setDailyReportDao(IEntityDao<DailyReport, String> dailyReportDao) {
		this.dailyReportDao = dailyReportDao;
	}

}
