package com.neusoft.model.report.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {映射 表 EDAILY_REPORT} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author lishijian
 * @version 1.0
 */
public class DailyReport extends AbstractEntity<String> {

	private static final long serialVersionUID = 888491498555333885L;

	public DailyReport() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getDreportID();
	}

	// DREPORT_ID char(32) not null comment '记录ID，无实际意义 MD5(UUID)',
	private String dreportID;

	// PROJECT_ID char(32) not null comment '项目记录主键，MD5(项目编号)',
	private String projectID;

	// USER_ID char(32) not null comment '日报属主',
	private String userID;

	// DEPT_ID char(32) not null comment '部门ID 与 人员ID 为组合主键 冗余',
	private String deptID;

	// REPORT_DATE date not null comment '日报日期',
	private Date reportDate;

	// WORK_HOURS float not null default 0.0 comment '工时数 （浮点 格式为 00.00）',
	private Integer workHours;

	// WORK_TYPE char(32) not null comment '工作类别:见数据字典',
	private String workType;

	// WORK_ACTIVITY char(32) not null comment '项目活动：见数据字典：',
	private String workActivity;

	// WORK_SUB_ACTIVITY char(32) comment '见数据字典：项目子活动',
	private String workSubActivity;

	// WORK_STATS char(32) not null comment '工作状态:见数据字典',
	private String workStats;

	// RECORDOR_ID char(32) not null comment '填写人ID',
	private String recordorID;

	// RECORD_DATE date not null comment '填写日期',
	private Date recordDate;

	// WORK_CONTENT text not null comment '工作内容',
	private String workContent;

	// RESULTS_SHOW text comment '成果物',
	private String resultsShow;

	// REP_COMMENT text comment '备注',
	private String repComment;

	// BEG_TYPE
	private String begType;

	// DELAY_AFFECT text comment '延迟影响原因',
	private String delayAffect;

	// DELAY_SOLVE text comment '延迟影响解决方案',
	private String delaySolve;

	// REPORT_TYPE 0/1 网银/主机
	private Integer reportType = 0;
	// WORK_SCHDE float default 0 comment '主机工作进度',
	private Float workSchde = 0f;

	private String userName;

	private String projectName;

	private String deptName;

	// 报表统计使用
	private int dayOfWeek;

	// 统计考勤需要字段
	private int wkOfYear;

	// 周开始
	private Date weekStart;

	// 周结束
	private Date weekEnd;

	// 签到标志
	private String ckRuleId;

	public String getCkRuleId() {
		return ckRuleId;
	}

	public void setCkRuleId(String ckRuleId) {
		this.ckRuleId = ckRuleId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDelayAffect() {
		return delayAffect;
	}

	public void setDelayAffect(String delayAffect) {
		this.delayAffect = delayAffect;
	}

	public String getDelaySolve() {
		return delaySolve;
	}

	public void setDelaySolve(String delaySolve) {
		this.delaySolve = delaySolve;
	}

	public String getUserName() {
		return userName;
	}

	public String getBegType() {
		return begType;
	}

	public Date getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(Date weekStart) {
		this.weekStart = weekStart;
	}

	public Float getWorkSchde() {
		return workSchde;
	}

	public void setWorkSchde(Float workSchde) {
		this.workSchde = workSchde;
	}

	public Date getWeekEnd() {
		return weekEnd;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public void setWeekEnd(Date weekEnd) {
		this.weekEnd = weekEnd;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public void setBegType(String begType) {
		this.begType = begType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWkOfYear() {
		return wkOfYear;
	}

	public void setWkOfYear(int wkOfYear) {
		this.wkOfYear = wkOfYear;
	}

	public String getDreportID() {
		return dreportID;
	}

	public void setDreportID(String dreportID) {
		this.dreportID = dreportID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public Integer getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Integer workHours) {
		this.workHours = workHours;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkActivity() {
		return workActivity;
	}

	public void setWorkActivity(String workActivity) {
		this.workActivity = workActivity;
	}

	public String getWorkSubActivity() {
		return workSubActivity;
	}

	public void setWorkSubActivity(String workSubActivity) {
		this.workSubActivity = workSubActivity;
	}

	public String getWorkStats() {
		return workStats;
	}

	public void setWorkStats(String workStats) {
		this.workStats = workStats;
	}

	public String getRecordorID() {
		return recordorID;
	}

	public void setRecordorID(String recordorID) {
		this.recordorID = recordorID;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getResultsShow() {
		return resultsShow;
	}

	public void setResultsShow(String resultsShow) {
		this.resultsShow = resultsShow;
	}

	public String getRepComment() {
		return repComment;
	}

	public void setRepComment(String repComment) {
		this.repComment = repComment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractDailyReportBase [dreportID=" + dreportID + ", projectID=" + projectID + ", userID="
				+ userID + ", deptID=" + deptID + ", reportDate=" + reportDate + ", workHours=" + workHours
				+ ", workType=" + workType + ", workActivity=" + workActivity + ", workSubActivity="
				+ workSubActivity + ", workStats=" + workStats + ", recordorID=" + recordorID
				+ ", recordDate=" + recordDate + ", workContent=" + workContent + ", resultsShow="
				+ resultsShow + ", repComment=" + repComment + "]";
	}

	public static DailyReport getInstance() {
		return new DailyReport();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "EDAILY_REPORT";
	}

}
