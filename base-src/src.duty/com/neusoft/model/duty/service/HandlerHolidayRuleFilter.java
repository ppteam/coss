package com.neusoft.model.duty.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.duty.dao.pojo.HolidayRule;
import com.neusoft.model.duty.exception.CheckRuleException;
import com.springsource.util.common.CollectionUtils;
import com.springsource.util.common.StringUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {定义假期规则--只有新增与修改 没有删除 保证数据的完整性} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandlerHolidayRuleFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandlerHolidayRuleFilter.class);

	private static final long serialVersionUID = -3279707848608464234L;

	public HandlerHolidayRuleFilter() {
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
		HolidayRule instance = context.getObject(paramKey, HolidayRule.class);
		List<HolidayRule> exits = (List<HolidayRule>) context.getValueByAll(exitsKey);
		Assert.notNull(instance, "saveInstace of CheckingRule.class is can't be null");
		if (StringUtils.hasLength(instance.getId())) {
			if (!CollectionUtils.isEmpty(exits)) {
				if (exits.size() > 1) {
					throw new CheckRuleException("0002", "rule date is repeat", null, new String[] { exits.get(0)
							.getHolidayName() });
				} else if (exits.size() == 1 && !exits.get(0).getId().equals(instance.getId())) {
					throw new CheckRuleException("0002", "rule date is repeat", null, new String[] { exits.get(0)
							.getHolidayName() });
				}
			}
			Integer res = dutyCommonDao.update("update_HolidayRule", instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update HolidayRule[" + instance.getId() + " is success]");
			}
			context.getResultMap().put("update", res);
		} else {
			if (!CollectionUtils.isEmpty(exits)) {
				throw new CheckRuleException("0002", "rule date is repeat", null, new String[] { exits.get(0)
						.getHolidayName() });
			}
			instance.setHolidayId(CommonUtils.getUUIDSeq());
			dutyCommonDao.insert("insert_HolidayRule", instance);
			context.getResultMap().put("saveId", instance.getId());
			if (logger.isDebugEnabled()) {
				logger.debug("insert HolidayRule[" + instance.getId() + " is success]");
			}
		}
		return false;
	} // end_fun

	private String paramKey = "instance";

	private String exitsKey = "extis_holiday_data";

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public void setExitsKey(String exitsKey) {
		this.exitsKey = exitsKey;
	}

}
