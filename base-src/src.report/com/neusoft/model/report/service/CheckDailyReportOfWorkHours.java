package com.neusoft.model.report.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.report.dao.pojo.DailyReport;
import com.neusoft.model.report.exception.DailyReportException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 17, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckDailyReportOfWorkHours extends AbstractAdapterFilter {

	private static final long serialVersionUID = -3155218665523129524L;
	private static final Logger logger = Logger.getLogger(CheckDailyReportOfWorkHours.class);

	public CheckDailyReportOfWorkHours() {
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
		DailyReport instance = context.getObject("instance", DailyReport.class);

		// 根据ID和加班ID返回工时
		String workHoursByidAndOver = (String) context.getResultMap().get("workhoursbyIdAndOvertime");
		if (workHoursByidAndOver == null) {
			workHoursByidAndOver = "0";
		}
		// 根据ID和正常ID返回工时
		String workHoursByidAndNormal = (String) context.getResultMap().get("workhoursbyIdAndNormal");
		if (workHoursByidAndNormal == null) {
			workHoursByidAndNormal = "0";
		}
		// 根据ID和请假ID返回工时
		String workHoursByidAndLeave = (String) context.getResultMap().get("workhoursbyIdAndLeave");
		if (workHoursByidAndLeave == null) {
			workHoursByidAndLeave = "0";
		}
		// 根据ID和调休ID返回工时
		String workHoursByidAndVacation = (String) context.getResultMap().get("workhoursbyIdAndVacation");
		if (workHoursByidAndVacation == null) {
			workHoursByidAndVacation = "0";
		}
		// 加班工时和加班工时ID
		List<String> overTimeList = (List<String>) context.getResultMap().get("dailyReportOverTimeIds");
		String overTime;
		if (overTimeList == null) {
			overTime = null;
		} else {
			if (StringUtils.hasLength(instance.getId())) {// 修改时
				int totalOverTime = 0;
				for (int i = 0; i < overTimeList.size(); i++) {
					totalOverTime += Integer.parseInt(overTimeList.get(i));
				}
				overTime = String.valueOf(totalOverTime - Integer.parseInt(workHoursByidAndOver));
			} else {// 新增时
				int totalOverTime = 0;
				for (int i = 0; i < overTimeList.size(); i++) {
					totalOverTime += Integer.parseInt(overTimeList.get(i));
				}
				overTime = String.valueOf(totalOverTime);
			}
		}
		List<String> overTimeID = (List<String>) context.getResultMap().get("dailyReportOverTimeIdsID");
		// 正常工时和正常工时ID
		List<String> normalTimeList = (List<String>) context.getResultMap().get("dailyReportNormalIds");
		String normalTime;
		if (normalTimeList == null) {
			normalTime = null;
		} else {
			if (StringUtils.hasLength(instance.getId())) {// 修改时
				int totalNormalTime = 0;
				for (int i = 0; i < normalTimeList.size(); i++) {
					totalNormalTime += Integer.parseInt(normalTimeList.get(i));
				}
				normalTime = String.valueOf(totalNormalTime - Integer.parseInt(workHoursByidAndNormal));
			} else {// 新增时
				int totalNormalTime = 0;
				for (int i = 0; i < normalTimeList.size(); i++) {
					totalNormalTime += Integer.parseInt(normalTimeList.get(i));
				}
				normalTime = String.valueOf(totalNormalTime);
			}
		}
		List<String> normalTimeID = (List<String>) context.getResultMap().get("dailyReportNormalIdsID");
		// 请假工时和请假工时ID
		List<String> leaveTimeList = (List<String>) context.getResultMap().get("dailyReportLeaveIds");
		String leaveTime;
		if (leaveTimeList == null) {
			leaveTime = null;
		} else {
			if (StringUtils.hasLength(instance.getId())) {// 修改时
				int totalLeaveTime = 0;
				for (int i = 0; i < leaveTimeList.size(); i++) {
					totalLeaveTime += Integer.parseInt(leaveTimeList.get(i));
				}
				leaveTime = String.valueOf(totalLeaveTime - Integer.parseInt(workHoursByidAndLeave));
			} else {// 新增时
				int totalLeaveTime = 0;
				for (int i = 0; i < leaveTimeList.size(); i++) {
					totalLeaveTime += Integer.parseInt(leaveTimeList.get(i));
				}
				leaveTime = String.valueOf(totalLeaveTime);
			}
		}
		List<String> leaveTimeID = (List<String>) context.getResultMap().get("dailyReportLeaveIdsID");
		// 休假工时和休假工时ID
		List<String> vacationTimeList = (List<String>) context.getResultMap().get("dailyReportVacationIds");
		String vacationTime;
		if (vacationTimeList == null) {
			vacationTime = null;
		} else {
			if (StringUtils.hasLength(instance.getId())) {// 修改时
				int totalVacationTime = 0;
				for (int i = 0; i < vacationTimeList.size(); i++) {
					totalVacationTime += Integer.parseInt(vacationTimeList.get(i));
				}
				vacationTime = String.valueOf(totalVacationTime - Integer.parseInt(workHoursByidAndVacation));
			} else {// 新增时
				int totalVacationTime = 0;
				for (int i = 0; i < vacationTimeList.size(); i++) {
					totalVacationTime += Integer.parseInt(vacationTimeList.get(i));
				}
				vacationTime = String.valueOf(totalVacationTime);
			}
		}
		List<String> vacationTimeID = (List<String>) context.getResultMap().get("dailyReportVacationIdsID");

		if (StringUtils.hasLength(instance.getId())) { // 修改
			if (overTime != null || normalTime != null || leaveTime != null || vacationTime != null) {
				int int_workHours = instance.getWorkHours();// 从页面获取工时数
				String str_workType = instance.getWorkType();// 从页面获取工时类别

				if (normalTimeID != null && overTimeID == null && leaveTimeID == null
						&& vacationTimeID == null) {
					// 非空（正常）空（加班、请假、调休）1
					if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "休假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "休假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "休假" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 800 - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0008", 1, "", null, new String[] { "正常", "8" });
						}
					}
				} else if (normalTimeID == null && overTimeID != null && leaveTimeID == null
						&& vacationTimeID == null) {
					// 非空（加班）空（正常、请假、调休）1
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "休假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "休假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "休假" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)) {
							throw new DailyReportException("0008", 1, "", null, new String[] { "加班", "24" });
						}
					}
				} else if (normalTimeID == null && overTimeID == null && leaveTimeID != null
						&& vacationTimeID == null) {
					// 非空（请假）空（正常、加班、调休）1
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "休假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "休假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "休假" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0008", 1, "", null, new String[] { "请假", "24" });
						}
					}
				} else if (normalTimeID == null && overTimeID == null && leaveTimeID == null
						&& vacationTimeID != null) {
					// 非空（调休）空（正常、加班、请假）1
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "休假", "4" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "休假", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "休假" });
						} else if (int_workHours > 2400 - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0008", 1, "", null, new String[] { "休假", "24" });
						}
					}// end 1
				} else if (normalTimeID != null && overTimeID != null && leaveTimeID == null
						&& vacationTimeID == null) {
					// 非空（正常、加班）空（请假、调休）2
					if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "请假" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "请假" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "调休" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "调休" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
						}
					}
				} else if (normalTimeID != null && overTimeID == null && leaveTimeID != null
						&& vacationTimeID == null) {
					// 非 空（正常、请假）空（加班、调休）2
					if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "请假" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "请假" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)
								- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
						}
					}
				} else if (normalTimeID != null && overTimeID == null && leaveTimeID == null
						&& vacationTimeID != null) {
					// 非空（正常、调休）空（加班、请假）2
					if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "加班" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "加班" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "请假" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "请假" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(vacationTime)
								- Integer.parseInt(normalTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
						}
					}
				} else if (normalTimeID == null && overTimeID != null && leaveTimeID != null
						&& vacationTimeID == null) {
					// 非空（加班、请假）空（正常、调休）2
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "调休" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "调休" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)
								- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "请假" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "请假" });
						}
					}
				} else if (normalTimeID == null && overTimeID != null && leaveTimeID == null
						&& vacationTimeID != null) {
					// 非空（加班、调休）空（正常、请假）2
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "请假" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "请假" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(vacationTime)
								- Integer.parseInt(overTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "调休" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "调休" });
						}
					}
				} else if (normalTimeID == null && overTimeID == null && leaveTimeID != null
						&& vacationTimeID != null) {
					// 非空（请假、调休）空（正常、加班）2
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(vacationTime)
										- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "加班" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "加班" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(vacationTime)
								- Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "调休" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)
								- Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "调休" });
						}
					}// end 2
				} else if (normalTimeID != null && overTimeID != null && leaveTimeID != null
						&& vacationTimeID == null) {
					// 非空（正常、加班、请假）空（调休）3
					if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(leaveTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "请假",
									"调休" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(leaveTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "请假",
									"调休" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常",
									"调休" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"正常" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"正常" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"正常" });
						}
					}
				} else if (normalTimeID == null && overTimeID != null && leaveTimeID != null
						&& vacationTimeID != null) {
					// 非空（加班、请假、调休）空（正常）3
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "请假",
									"调休" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "加班",
									"调休" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "加班",
									"请假" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"调休" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"调休" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"调休" });
						}
					}
				} else if (normalTimeID != null && overTimeID == null && leaveTimeID != null
						&& vacationTimeID != null) {
					// 非空（正常、请假、调休）空（加班）3
					if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(leaveTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "请假",
									"调休" });
						} else if (leaveTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "请假",
									"调休" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常",
									"请假" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常",
									"调休" });
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(leaveTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常",
									"调休" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常",
									"调休" });
						}
					}
				} else if (normalTimeID != null && overTimeID != null && leaveTimeID == null
						&& vacationTimeID != null) {
					// 非空（正常、加班、调休）空（请假）3
					if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (normalTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(overTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "加班",
									"调休" });
						} else if (overTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(vacationTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常",
									"调休" });
						} else if (vacationTimeID.contains(instance.getId()) == true
								&& int_workHours > 2400 - Integer.parseInt(normalTime)
										- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常",
									"加班" });
						}
					} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(normalTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常",
									"调休" });
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(vacationTime) - Integer.parseInt(overTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常",
									"调休" });
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)) {
							throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常",
									"调休" });
						}
					}// end 3
				} else if (normalTimeID != null && overTimeID != null && leaveTimeID != null
						&& vacationTimeID != null) {
					// 非空（正常、加班、请假、调休）4
					if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")) {
						if (int_workHours > 800) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
						} else if (int_workHours > 2400 - Integer.parseInt(overTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)
								- Integer.parseInt(normalTime)) {
							// 加班、请假、调休不为空，则正常工时不能大于2400-加班工时-请假工时-调休工时
							throw new DailyReportException("0005", 1, "", null, null);
						}
					} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")) {
						if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)
								- Integer.parseInt(overTime)) {
							// 正常、请假、调休不为空，则加班工时不能大于2400-正常工时-请假工时-调休工时
							throw new DailyReportException("0005", 1, "", null, null);
						}
					} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)
								- Integer.parseInt(leaveTime)) {
							// 正常、加班、调休不为空，则请假工时不能大于2400-正常工时-加班工时-调休工时
							throw new DailyReportException("0005", 1, "", null, null);
						}
					} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")) {
						if (int_workHours < 400 && int_workHours > 0) {
							throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
						} else if (int_workHours < 0) {
							throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
						} else if (int_workHours > 2400) {
							throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
						} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
								- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)
								- Integer.parseInt(vacationTime)) {
							// 正常、加班、请假不为空，则调休工时不能大于2400-正常工时-加班工时-请假工时
							throw new DailyReportException("0005", 1, "", null, null);
						}
					}
				}
			}
		} else { // 添加
			if (overTime != null && normalTime != null && leaveTime != null && vacationTime != null) {
				logger.error("[entry:" + instance.getId() + "] has the same record in db");
				throw new DailyReportException("0000", 1, "", null, null);
			} else {
				int int_workHours = instance.getWorkHours();
				String str_workType = instance.getWorkType();

				if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c") && normalTime == null
						&& overTime == null && leaveTime == null && vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime != null && overTime == null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 800 - Integer.parseInt(normalTime)) {
						throw new DailyReportException("0008", 1, "", null, new String[] { "正常", "8" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime == null && overTime != null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "加班" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime != null && overTime != null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)
							- Integer.parseInt(normalTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "正常", "加班" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime == null && overTime != null && leaveTime != null
						&& vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)
							- Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "加班", "请假" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime != null && overTime != null && leaveTime != null
						&& vacationTime == null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(normalTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "正常", "加班", "请假" });
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime == null && overTime != null && leaveTime != null
						&& vacationTime != null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}
				} else if (str_workType.equals(/* 正常 */"0d0cc048adf440edb55d8e53d756439c")
						&& normalTime != null && overTime != null && leaveTime != null
						&& vacationTime != null) {
					if (int_workHours > 800) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "正常", "8" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "正常" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)
							- Integer.parseInt(normalTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}// 正常工时判断逻辑结束
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime == null && normalTime == null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime != null && normalTime == null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(overTime)) {
						throw new DailyReportException("0008", 1, "", null, new String[] { "加班", "24" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime == null && normalTime != null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime != null && normalTime != null && leaveTime == null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "加班", "正常" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime == null && normalTime != null && leaveTime != null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常", "请假" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime != null && normalTime != null && leaveTime != null
						&& vacationTime == null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(overTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "加班", "正常", "请假" });
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime == null && normalTime != null && leaveTime != null
						&& vacationTime != null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}
				} else if (str_workType.equals(/* 加班 */"dba8abe0a65945a49ebfc587c947d920")
						&& overTime != null && normalTime != null && leaveTime != null
						&& vacationTime != null) {
					if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "加班", "24" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "加班" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(leaveTime) - Integer.parseInt(vacationTime)
							- Integer.parseInt(overTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}// 加班工时判断逻辑结束
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime == null && normalTime == null && overTime == null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime != null && normalTime == null && overTime == null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0008", 1, "", null, new String[] { "请假", "24" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime == null && normalTime != null && overTime == null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime != null && normalTime != null && overTime == null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "请假", "正常" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime == null && normalTime != null && overTime != null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常", "加班" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime != null && normalTime != null && overTime != null
						&& vacationTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "请假", "正常", "加班" });
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime == null && normalTime != null && overTime != null
						&& vacationTime != null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}
				} else if (str_workType.equals(/* 请假 */"3692b9cd731a452ea9e9c55efa5c9618")
						&& leaveTime != null && normalTime != null && overTime != null
						&& vacationTime != null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "请假", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "请假" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "请假", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)
							- Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}// 请假工时判断逻辑结束
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime == null && normalTime == null && overTime == null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime != null && normalTime == null && overTime == null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0008", 1, "", null, new String[] { "调休", "24" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime == null && normalTime != null && overTime == null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime != null && normalTime != null && overTime == null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0003", 1, "", null, new String[] { "调休", "正常" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime == null && normalTime != null && overTime != null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "调休", "正常", "加班" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime != null && normalTime != null && overTime != null
						&& leaveTime == null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0004", 1, "", null, new String[] { "调休", "正常", "加班" });
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime == null && normalTime != null && overTime != null
						&& leaveTime != null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}
				} else if (str_workType.equals(/* 调休 */"5874f025064a423c935c215874a14f45")
						&& vacationTime != null && normalTime != null && overTime != null
						&& leaveTime != null) {
					if (int_workHours < 400 && int_workHours > 0) {
						throw new DailyReportException("0002", 1, "", null, new String[] { "调休", "4" });
					} else if (int_workHours < 0) {
						throw new DailyReportException("0006", 1, "", null, new String[] { "调休" });
					} else if (int_workHours > 2400) {
						throw new DailyReportException("0001", 1, "", null, new String[] { "调休", "24" });
					} else if (int_workHours > 2400 - Integer.parseInt(normalTime)
							- Integer.parseInt(overTime) - Integer.parseInt(leaveTime)
							- Integer.parseInt(vacationTime)) {
						throw new DailyReportException("0005", 1, "", null, null);
					}
				}// 调休工时判断逻辑结束
			}
		}
		return false;
	}
}
