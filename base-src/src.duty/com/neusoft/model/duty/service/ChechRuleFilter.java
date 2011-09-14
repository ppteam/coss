package com.neusoft.model.duty.service;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.duty.dao.pojo.HolidayRule;
import com.springsource.util.common.StringUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ChechRuleFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ChechRuleFilter.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449392552370667951L;

	public ChechRuleFilter() {
		super();
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
		boolean check_res = false;
		if (StringUtils.hasLength(key)) {
			List<HolidayRule> rules = (List<HolidayRule>) context.getValueByAll(key);
			if (CollectionUtils.isNotEmpty(rules)) { // 今天有假期规则限制
				// 检测是否为特别上班日
				boolean isWorking = false;
				for (HolidayRule rule : rules) {
					if (rule.getDayType() == 1) {
						isWorking = true;
						if (logger.isInfoEnabled()) {
							logger.info("Today matched HolidayRule [" + rule.getHolidayName() + "],So Go on job");
						}
						break;
					} // end_if
				} // end_for
					// 判断是否是假期
				if (!isWorking) {
					for (HolidayRule rule : rules) {
						if (rule.getDayType() == 0) {
							check_res = true;
							if (logger.isInfoEnabled()) {
								logger.info("Today matched HolidayRule [" + rule.getHolidayName() + "],So Break job");
							}
							break;
						} // end_if
					} // end_for
				}
			} else {
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				int index = calendar.get(Calendar.DAY_OF_WEEK);
				if (index == 1 || index == 7) {
					check_res = true;
					if (logger.isInfoEnabled()) {
						logger.info("Today matched weekDays,So Break job");
					}
				}
			}
		} // end_if
		return check_res;
	}

	// holiday_list
	private String key;

	public void setKey(String key) {
		this.key = key;
	}

}
