package com.neusoft.model.eweekreport.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;

public interface IWeekReportAction {
	/**
	 * {新增、修改字个人周报}
	 * 
	 * @param EWeekReport
	 *            id:null 新增/ 否则修改
	 * @return CallBackModel
	 */
	CallBackModel saveOrUpdateEWeekReport(EWeekReport eWeekReport);
}
