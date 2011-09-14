package com.neusoft.model.report.service;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.report.exception.DailyReportException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {判断员工填写工时系统 关于工作时间的合法性} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 18, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckDailyReportOfWorkHoursFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckDailyReportOfWorkHoursFilter.class);

	private static final long serialVersionUID = -3155218665523129524L;

	public CheckDailyReportOfWorkHoursFilter() {
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
		DailyReport instance = context.getObject(keyInstance, DailyReport.class);
		HashMap<String, BigDecimal> workHours_Set = (HashMap<String, BigDecimal>) context
				.getValueByAll(keyHourSet);
		List<DailyReport> todoList = new ArrayList<DailyReport>();
		Integer rule = (Integer) context.getValueByAll("hours_rules");
		if (logger.isDebugEnabled()) {
			logger.debug("current  hours rule is [" + rule + "]");
		}
		rule = rule * 100;
		todoList.add(instance);
		initHourSet(workHours_Set);
		if (logger.isDebugEnabled()) {
			logger.debug("execute(IContext) - HashMap<String,Integer> workHours_Set=" + workHours_Set);
		}
		int tmp_workHours = instance.getWorkHours();
		String tmp_workType = instance.getWorkType();
		// '0d0cc048adf440edb55d8e53d756439c' 正常
		// '3692b9cd731a452ea9e9c55efa5c9618' 请假
		// 'dba8abe0a65945a49ebfc587c947d920' 加班

		if (workHours_Set.get("allHour").intValue() + tmp_workHours > 2400) {
			throw new DailyReportException("1001", DailyReportException.EXP_TYPE_EXCPT, null, null,
					new String[] { "全部", "24" });
		} // end_if

		// 请假
		if ("3692b9cd731a452ea9e9c55efa5c9618".equals(tmp_workType)) {
			if (workHours_Set.get("norHour").intValue() + workHours_Set.get("evlHour").intValue()
					+ tmp_workHours > rule) {
				throw new DailyReportException("1001", DailyReportException.EXP_TYPE_EXCPT, null, null,
						new String[] { "正常、请假", String.valueOf(rule / 100F) });
			}
		} // end_if

		// 正常工时判断
		if ("0d0cc048adf440edb55d8e53d756439c".equals(tmp_workType)) {
			int sumHours = workHours_Set.get("norHour").intValue() + workHours_Set.get("evlHour").intValue();
			if (sumHours >= rule) {
				throw new DailyReportException("0008", DailyReportException.EXP_TYPE_EXCPT, null, null,
						new String[] { "正常、请假", String.valueOf(rule / 100F) });
			} else if (sumHours + tmp_workHours > rule) {
				int norhours = rule - sumHours;
				int addHours = tmp_workHours - norhours;
				if (logger.isDebugEnabled()) {
					logger.debug("sumHours[" + sumHours + "] :: rule[" + rule + "] tmp_workHours["
							+ tmp_workHours + "] should be split [" + norhours + "] and [" + addHours + "]");
				}
				todoList.clear();
				todoList.add(cloneObj(instance, addHours));
				instance.setWorkHours(norhours);
				todoList.add(instance);
			}
		} // end_if
		context.put("todoList", todoList);
		return false;
	} // end_fun

	/**
	 * {克隆新的对象出来}
	 * 
	 * @param src
	 * @return
	 */
	private DailyReport cloneObj(DailyReport source, int hours) {
		DailyReport target = DailyReport.getInstance();
		BeanUtils.copyProperties(source, target);
		target.setDreportID(null);
		// 加班
		target.setWorkType("dba8abe0a65945a49ebfc587c947d920");
		target.setWorkHours(hours);
		target.setProjectID(source.getProjectID());
		return target;
	} // end_if

	/**
	 * {初始化不存在的记录}
	 * 
	 * @param workHours_Set
	 */
	private void initHourSet(HashMap<String, BigDecimal> map) {
		if (map.get("allHour") == null)
			map.put("allHour", BigDecimal.valueOf(0L));
		if (map.get("norHour") == null)
			map.put("norHour", BigDecimal.valueOf(0L));
		if (map.get("addHour") == null)
			map.put("addHour", BigDecimal.valueOf(0L));
		if (map.get("evlHour") == null)
			map.put("evlHour", BigDecimal.valueOf(0L));
	}

	private String keyInstance = "instance";

	private String keyHourSet = "workhours_set";

	public void setKeyInstance(String keyInstance) {
		this.keyInstance = keyInstance;
	}

	public void setKeyHourSet(String keyHourSet) {
		this.keyHourSet = keyHourSet;
	}

}
