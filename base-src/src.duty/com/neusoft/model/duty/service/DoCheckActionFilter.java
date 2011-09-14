package com.neusoft.model.duty.service;

import org.springframework.util.NumberUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.util.DateUtil;
import com.neusoft.model.duty.dao.pojo.CheckDetail;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.springsource.util.common.StringUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户签到 记录存储} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DoCheckActionFilter extends SqlMapClientSupportFilter {

	private static final long serialVersionUID = -3279707848608464234L;

	public DoCheckActionFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		CheckingRule rule = context.getObject(ConstParam.KEY_CHECK_RULE, CheckingRule.class);
		CheckDetail detail = context.getObject(ConstParam.KEY_CHECK_DETAIL, CheckDetail.class);
		if (context.getInteger(ConstParam.IS_HOLIDAY_TAG) == ConstParam.HOLIDAY_DAY) {
			if (context.getInteger(ConstParam.KEY_CHECK_ACTION) == ConstParam.ACTION_CHECK_IN) {
				detail.setBeginCheck(DateUtil.fromatDate(null, "HH:mm"));
				detail.setDayStats(1);
				detail.setBeginStats(3);
				detail.setRuleId(ConstParam.NULL_CHECK_RULE);
			} else if (context.getInteger(ConstParam.KEY_CHECK_ACTION) == ConstParam.ACTION_CHECK_OUT) {
				detail.setEndCheck(DateUtil.fromatDate(null, "HH:mm"));
				detail.setEndStats(3);
			}
		} else {
			detail.setRuleId(rule.getId());
			detail.setDayStats(0);
			if (context.getInteger(ConstParam.KEY_CHECK_ACTION) == ConstParam.ACTION_CHECK_IN) {
				detail.setBeginCheck(DateUtil.fromatDate(null, "HH:mm"));
				if (compareTime(detail.getBeginCheck(), rule.getBeiginWkTime()) <= 0) {
					detail.setBeginStats(1); // 正常
				} else {
					detail.setBeginStats(2); // 迟到
				}
			} else if (context.getInteger(ConstParam.KEY_CHECK_ACTION) == ConstParam.ACTION_CHECK_OUT) {
				detail.setEndCheck(DateUtil.fromatDate(null, "HH:mm"));
				if (compareTime(detail.getEndCheck(), rule.getEndWkTime()) >= 0) {
					detail.setEndStats(1); // 正常
				} else {
					detail.setEndStats(2); // 早退
				}
			}
		} // end_if

		// 保存结果
		if (StringUtils.hasLength(detail.getId())) {
			sqlMapClientTemplate.update("CheckingRule.update_CheckDetail", detail);
		} else {
			detail.setDetailId(CommonUtils.getUUIDSeq());
			sqlMapClientTemplate.insert("CheckingRule.insert_CheckDetail", detail);
		}
		return false;
	} // end_fun

	/**
	 * {比较两个时间}
	 * 
	 * @param nowTime
	 * @param ruleTime
	 * @return -1/0/1 nowTime>ruleTime/nowTime = ruleTime/nowTime<ruleTime
	 */
	private int compareTime(String nowTime, String ruleTime) {
		Integer int_nowTime = NumberUtils.parseNumber(nowTime.replaceAll("[:]", ""), Integer.class);
		Integer int_ruleTime = NumberUtils.parseNumber(ruleTime.replaceAll("[:]", ""), Integer.class);
		return int_nowTime - int_ruleTime;
	} // end_if

}
