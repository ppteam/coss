package com.neusoft.model.duty.facade.Impl;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractAjaxAction;
import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.duty.dao.pojo.CheckDetail;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.neusoft.model.duty.dao.pojo.HolidayRule;
import com.neusoft.model.duty.facade.Iface.IRemoteAction;
import com.neusoft.model.duty.service.ConstParam;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {考勤管理Ajax} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 17, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
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
	 * @see
	 * com.neusoft.model.duty.facade.Iface.IRemoteAction#handleCkRule(com.neusoft
	 * .model.duty.dao.pojo.CheckingRule)
	 */
	public CallBackModel handlerCkRule(CheckingRule checkingRule) {
		IContext context = creatContext();
		try {
			Assert.notNull(checkingRule, "[p:checkingRule] is not null");
			checkingRule.setUserId(getLoginUser().getUserId());
			context.put("instance", checkingRule);
			executeChain(context, "hanlder_checkingRule_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.duty.facade.Iface.IRemoteAction#handlerHoldayRule(com
	 * .neusoft.model.duty.dao.pojo.HolidayRule)
	 */
	public CallBackModel handlerHoldayRule(HolidayRule holidayRule) {
		IContext context = creatContext();
		try {
			Assert.notNull(holidayRule, "[p:holidayRule] is not null");
			holidayRule.setUserId(getLoginUser().getUserId());
			context.put("instance", holidayRule);
			executeChain(context, "hanlder_holidayRule_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.model.duty.facade.Iface.IRemoteAction#checkAction(int)
	 */
	public CallBackModel checkAction(Integer type) {
		IContext context = creatContext();
		try {
			initContext(context);
			context.put(ConstParam.KEY_CHECK_ACTION, type);
			executeChain(context, "check_in_or_out_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.duty.facade.Iface.IRemoteAction#checkEdited(com.neusoft
	 * .model.duty.dao.pojo.CheckDetail)
	 */
	public CallBackModel checkEdited(CheckDetail checkDetail) {
		if (logger.isInfoEnabled()) {
			logger.info("checkEdited(CheckDetail)[" + checkDetail.toString() + "]");
		}

		IContext context = creatContext();
		try {
			initContext(context);
			context.put("checkDetail", checkDetail);
			executeChain(context, "check_detail_edited_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.model.duty.facade.Iface.IRemoteAction#editComments(java.lang
	 * .String, java.lang.String)
	 */
	public CallBackModel editComments(String detailId, String comments) {
		if (logger.isInfoEnabled()) {
			logger.info("checkEdited(...)[" + detailId + "、"+comments+"]");
		}

		IContext context = creatContext();
		try {
			initContext(context);
			context.put("detailId", detailId);
			context.put("comments", comments);
			executeChain(context, "edit_check_comment_chain");
		} catch (Exception e) {
			logger.error(e);
		}
		return fillByContext(context);
	}

} // end_fun
