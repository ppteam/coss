package com.neusoft.model.duty.service;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.springsource.util.common.StringUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {保存以及修改考勤规则} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandlerCheckRuleFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandlerCheckRuleFilter.class);

	private static final long serialVersionUID = -3279707848608464806L;

	public HandlerCheckRuleFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		CheckingRule instance = context.getObject(paramKey, CheckingRule.class);
		Assert.notNull(instance, "instance of CheckingRule.class is can't be null");
		if (StringUtils.hasLength(instance.getId())) { // 注销操作
			Integer res = dutyCommonDao.update("cancel_CheckingRule", instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update CheckingRule[" + instance.getId() + " is success]");
			}
			context.getResultMap().put("update_rows", res);
		} else { // insert
			instance.setRuleId(CommonUtils.getUUIDSeq());
			dutyCommonDao.insert("insert_CheckingRule", instance);
			context.getResultMap().put("ruleId", instance.getId());
			if (logger.isDebugEnabled()) {
				logger.debug("insert CheckingRule[" + instance.getId() + " is success]");
			}
		}
		return false;
	}

	private String paramKey = "instance";

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
