package com.neusoft.model.report.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.report.dao.pojo.DailyReport;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {日报管理ajax 接口定义} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 17, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
public interface IRemoteAction {

	/**
	 * {新增、修改字个人日报}
	 * 
	 * @param dailyReport
	 *            id:null 新增/ 否则修改
	 * @return CallBackModel
	 */
	CallBackModel saveOrUpdateDailyReport(DailyReport dailyReport);

	/**
	 * {批量提交 请假日报}
	 * 
	 * @param dailyReports
	 * @return
	 */
	CallBackModel batEditDailyReport(DailyReport[] dailyReports);

}
