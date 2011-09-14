package com.neusoft.model.eweekreport.service;

import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;

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
public abstract class AbstractEWeekReport extends FilterBase {

	private static final long serialVersionUID = 5242492865723135596L;

	public AbstractEWeekReport() {
	}

	protected IEntityDao<EWeekReport, String> eWeekReportDao;

	public void seteWeekReportDao(IEntityDao<EWeekReport, String> eWeekReportDao) {
		this.eWeekReportDao = eWeekReportDao;
	}

}
