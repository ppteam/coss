package com.neusoft.model.eweekreport.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {个人周报数据建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 19, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class EWeekReport extends AbstractEntity<String> {

	private static final long serialVersionUID = -2466782208827823429L;

	private String userID;// eweekly_report.USER_ID,
	private String wreportID;// eweekly_report.WREPORT_ID,
	private Date startDate;// eweekly_report.START_DATE,
	private Date endDate;// eweekly_report.END_DATE,
	private String deptID;// eweekly_report.DEPT_ID,
	private String reportNo;// eweekly_report.REPORT_NO,
	private Date recordDate;// eweekly_report.RECORD_DATE,
	private String recordorID;// eweekly_report.RECORDOR_ID,
	private String workSummary;// eweekly_report.WORK_SUMMARY,
	private String nweekPlan;// eweekly_report.NWEEK_PLAN,
	private String unsolveProblem;// eweekly_report.UNSOLVE_PROBLEM,

	// view properity
	private String deptName;
	private Integer norHours;
	private Integer addHours;
	private Integer evlHours;
	private String userName;
	// 是否可以编辑
	private Integer editEnable;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWreportID() {
		return wreportID;
	}

	public void setWreportID(String wreportID) {
		this.wreportID = wreportID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public Integer getNorHours() {
		return norHours;
	}

	public void setNorHours(Integer norHours) {
		this.norHours = norHours;
	}

	public Integer getAddHours() {
		return addHours;
	}

	public void setAddHours(Integer addHours) {
		this.addHours = addHours;
	}

	public Integer getEvlHours() {
		return evlHours;
	}

	public void setEvlHours(Integer evlHours) {
		this.evlHours = evlHours;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordorID() {
		return recordorID;
	}

	public void setRecordorID(String recordorID) {
		this.recordorID = recordorID;
	}

	public String getWorkSummary() {
		return workSummary;
	}

	public void setWorkSummary(String workSummary) {
		this.workSummary = workSummary;
	}

	public String getNweekPlan() {
		return nweekPlan;
	}

	public void setNweekPlan(String nweekPlan) {
		this.nweekPlan = nweekPlan;
	}

	public String getUnsolveProblem() {
		return unsolveProblem;
	}

	public void setUnsolveProblem(String unsolveProblem) {
		this.unsolveProblem = unsolveProblem;
	}

	public Integer getEditEnable() {
		return editEnable;
	}

	public void setEditEnable(Integer editEnable) {
		this.editEnable = editEnable;
	}

	public String getTVName() {
		return "EDAILY_REPORT";
	}

	public String getId() {
		return getWreportID();
	}

}
