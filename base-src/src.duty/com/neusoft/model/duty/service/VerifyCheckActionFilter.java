package com.neusoft.model.duty.service;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.duty.dao.pojo.CheckDetail;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.neusoft.model.duty.exception.CheckRuleException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户签到规则} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class VerifyCheckActionFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VerifyCheckActionFilter.class);

	private static final long serialVersionUID = -3279707848608464234L;

	public VerifyCheckActionFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Integer checkType = context.getInteger(ConstParam.KEY_CHECK_ACTION);
		CheckDetail checkDetail = (CheckDetail) sqlMapClientTemplate.queryForObject(
				"CheckingRule.query_checkRec_by_loginUser", context);
		if (checkDetail == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("name's [" + context.get(LoginUser.KEY_USERNAME) + "] is no rec in db");
			}
			checkDetail = init(context);
		} else {
			if (checkType == 0 && checkDetail.getBeginStats() != 0) {
				throw new CheckRuleException("0004", "check again", null, new String[] { "checkIn" });
			}
			if (checkType == 1 && checkDetail.getEndStats() != 0) {
				throw new CheckRuleException("0004", "check again", null, new String[] { "checkOut" });
			}
			if (checkType == 1 && checkDetail.getBeginStats() == 0) {
				throw new CheckRuleException("0005", "check again", null, new String[] { "checkIn", "checkOut" });
			}
			if (ConstParam.NULL_CHECK_RULE.equals(checkDetail.getRuleId())) {
				context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.HOLIDAY_DAY);
			} else {
				context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.WORKING_DAY);
				CheckingRule checkingRule = (CheckingRule) sqlMapClientTemplate.queryForObject(
						"CheckingRule.query_checkrule_byId", checkDetail.getRuleId());
				if (checkingRule != null) {
					context.put(ConstParam.KEY_CHECK_RULE, checkingRule);
				} 
			}
		} // end_if
		context.put(ConstParam.KEY_CHECK_DETAIL, checkDetail);
		return false;
	} // end_fun

	/**
	 * {初始化一个全新的签到规则}
	 * 
	 * @param context
	 * @return
	 */
	private CheckDetail init(IContext context) throws BaseException {
		CheckDetail checkDetail = new CheckDetail();
		checkDetail.setUserId(context.getString(LoginUser.KEY_USERID));
		checkDetail.setDeptId(context.getString(LoginUser.KEY_DEPT));
		checkDetail.setBeginStats(0);
		checkDetail.setEndStats(0);
		return checkDetail;
	} // end_fun

}
