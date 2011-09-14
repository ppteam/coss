package com.neusoft.model.pwreport.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.pwreport.dao.pojo.PweeklyReport;

public interface IRemoteAction {

	/**
	 * {保存 修改 项目周报}
	 * 
	 * @param report
	 * @return CallBackModel
	 */
	CallBackModel handlerPweeklyReport(PweeklyReport report);

}
