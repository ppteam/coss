package com.neusoft.model.pwreport.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目周报数据问题列表建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class PweeklyMemberplan extends AbstractEntity<String> {

	private static final long serialVersionUID = 5947329151048531450L;

	public PweeklyMemberplan() {
	}

	// DATA_ID char(32) not null comment '周报ID :MD5（UUID）',
	private String dataId;
	// REPORT_ID char(32) not null comment '周报ID :MD5（UUID）',
	private String reportId;
	// USER_ID char(32) not null comment '该ID作用范围为整个系统',
	private String userId;
	// WEEKLY_SUMMARY text comment '本周总结',
	private String weeklySummary;
	// WEEKLY_PLAN text comment '下周工作计划计划',
	private String weeklyPlan;
	// BRANCH_ID 员工坐在部门ID
	private String branchId;

	private String branchName;
	// view prop
	private Integer normalTime;
	private Integer overTime;
	private String userName;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getUserId() {
		return userId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWeeklySummary() {
		return weeklySummary;
	}

	public void setWeeklySummary(String weeklySummary) {
		this.weeklySummary = weeklySummary;
	}

	public String getWeeklyPlan() {
		return weeklyPlan;
	}

	public void setWeeklyPlan(String weeklyPlan) {
		this.weeklyPlan = weeklyPlan;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Integer getNormalTime() {
		return normalTime;
	}

	public void setNormalTime(Integer normalTime) {
		this.normalTime = normalTime;
	}

	public Integer getOverTime() {
		return overTime;
	}

	public void setOverTime(Integer overTime) {
		this.overTime = overTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return dataId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "PWEEKLY_MEMBERPLAN";
	}

}
