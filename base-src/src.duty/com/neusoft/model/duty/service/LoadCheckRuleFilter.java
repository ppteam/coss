package com.neusoft.model.duty.service;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.neusoft.model.duty.dao.pojo.HolidayRule;
import com.neusoft.model.duty.exception.CheckRuleException;
import com.springsource.util.common.StringUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {加载当前登录用户适用的签到规则} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class LoadCheckRuleFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoadCheckRuleFilter.class);

	private static final long serialVersionUID = -3279707848608464806L;

	public LoadCheckRuleFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		Assert.notNull(context.get(paramKey), "[key:" + paramKey + "] mapping value not null");
		List<HolidayRule> rules = (List<HolidayRule>) sqlMapClientTemplate
				.queryForList("CheckingRule.query_holidayrule_by_today");
		if (!context.containsKey(ConstParam.IS_HOLIDAY_TAG)) {
			if (CollectionUtils.isEmpty(rules)) {
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				int index = calendar.get(Calendar.DAY_OF_WEEK);
				if (index != 1 && index != 7) {
					context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.WORKING_DAY);
					if (logger.isInfoEnabled()) {
						logger.info("Today is not weekDays,So for loading rule");
					}
				}else{
					context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.HOLIDAY_DAY);
				}
			} else {
				for (HolidayRule rule : rules) {
					if (rule.getDayType() == 1) {
						context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.WORKING_DAY);
						if (!context.containsKey("CheckingRule")) {
							loadRulebyDB(context);
						}
						break;
					} else {
						context.put(ConstParam.IS_HOLIDAY_TAG, ConstParam.HOLIDAY_DAY);
						if (logger.isInfoEnabled()) {
							logger.info("Today matched HolidayRule [" + rule.getHolidayName() + "],So break job");
						}
					}
				}
			} // end_if
		}
		if (context.getInteger(ConstParam.IS_HOLIDAY_TAG) == ConstParam.WORKING_DAY
				&& !context.containsKey(ConstParam.KEY_CHECK_RULE)) {
			loadRulebyDB(context);
		}
		return false;
	}

	/**
	 * {加载 考勤规则}
	 * 
	 * @param context
	 */
	private void loadRulebyDB(IContext context) throws CheckRuleException {
		String deptPath = (String) sqlMapClientTemplate.queryForObject("CheckingRule.query_dept_path_userId",
				context.get(paramKey));
		if (StringUtils.hasLength(deptPath)) {
			context.put("deptPath", deptPath.split("[-]"));
			CheckingRule checkingRule = (CheckingRule) sqlMapClientTemplate.queryForObject(
					"CheckingRule.query_checkrule_by_loginUser", context);
			if (logger.isDebugEnabled()) {
				logger.debug("find checkRule's name is ["
						+ (checkingRule == null ? "null" : checkingRule.getRuleName()) + "]");
			} // end_if
			if (checkingRule == null) {
				throw new CheckRuleException("0000", "", new NullPointerException(), null);
			}
			context.put(ConstParam.KEY_CHECK_RULE, checkingRule);
		} else {
			logger.warn("no dept in user'id is  [" + context.get(LoginUser.KEY_USERNAME) + "]");
			throw new CheckRuleException("0001", "", new NullPointerException(), null);

		}
	} // end_if

	private String paramKey = LoginUser.KEY_USERID;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
