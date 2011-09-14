package com.neusoft.model.duty.service;

import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
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
public class VerifyByRuleActionFilter extends SqlMapClientSupportFilter {

	private static final long serialVersionUID = -3279707848608464234L;

	public VerifyByRuleActionFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		CheckDetail detail = context.getObject(detailKey, CheckDetail.class);
		if (ConstParam.NULL_CHECK_RULE.equals(detail.getRuleId())) {
			detail.setDayStats(1); // 假期
			detail.setBeginStats(3);
			detail.setEndStats(3);
		} else {
			CheckingRule rule = (CheckingRule) context.getValueByAll(ruleKey);
			Assert.notNull(rule, "Check Rule cann't be null");
			detail.setEndStats(1);
			detail.setDayStats(0);
			if (StringUtils.hasLength(detail.getBeginCheck())) {
				if (compareTime(detail.getBeginCheck(), rule.getBeiginWkTime()) <= 0) {
					detail.setBeginStats(1); // 正常
				} else {
					detail.setBeginStats(2); // 迟到
				}
			}else{
				detail.setBeginStats(0); // 正常
			}

			if (StringUtils.hasLength(detail.getEndCheck())) {
				if (compareTime(detail.getEndCheck(), rule.getEndWkTime()) >= 0) {
					detail.setEndStats(1);
				} else {
					detail.setEndStats(2);
				}
			}else{
				detail.setEndStats(0);
			}
		} // end_if

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

	private String detailKey;

	private String ruleKey;

	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}

	public void setDetailKey(String detailKey) {
		this.detailKey = detailKey;
	}

}
