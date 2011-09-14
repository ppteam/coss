package com.neusoft.model.pwreport.facade.Impl;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.pwreport.dao.pojo.PweeklyReport;
import com.neusoft.model.pwreport.facade.Iface.IRemoteAction;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户信息管理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class RemoteActionImpl extends AbstractAjaxAction implements IRemoteAction {

	public RemoteActionImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.pwreport.facade.Iface.IRemoteAction#handlerPweeklyReport
	 * (com.neusoft.model.pwreport.dao.pojo.PweeklyReport)
	 */
	public CallBackModel handlerPweeklyReport(PweeklyReport report) {
		IContext context = creatContext();
		try {
			Assert.notNull(report, "[p:report] is not null");
			context.put("instance", report);
			initContext(context); // 复制用户登录信息
			executeChain(context, "handler_pro_wreport_chain");
		} catch (Exception e) {
			context.put(IContext.KEY_ERROR, e);
			e.printStackTrace();
		}
		return fillByContext(context);
	}

}
