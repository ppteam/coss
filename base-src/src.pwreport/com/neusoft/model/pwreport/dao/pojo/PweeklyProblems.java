package com.neusoft.model.pwreport.dao.pojo;

import java.util.Date;

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
public class PweeklyProblems extends AbstractEntity<String> {

	private static final long serialVersionUID = 5947329151048531450L;

	public PweeklyProblems() {
	}

	// PROBLEM_ID char(32) not null comment '周报ID :MD5（UUID）',
	private String problemId;
	// REPORT_ID char(32) comment '周报ID :MD5（UUID）',
	private String reportId;
	// DISCOVER_DATE date not null comment '提出日期',
	private Date discoverDate;
	// SOLVE_DATE date comment '计划解决日期',
	private Date solveDate;
	// USER_NAME varchar(32) not null comment '提出人（描述）',
	private String userName;
	// PROBLEM_DESC text not null,
	private String problemDesc;
	// RESOLVE_WAY text comment '解决措施',
	private String resolveWay;
	// PROBLEM_STATS char(32) not null comment '问题状态（见数据字典）',
	private String problemStats;
	// RESPOSIBLER varchar(32) comment '该问题的负责人',
	private String resposibler;

	private String projectId;

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDiscoverDate() {
		return discoverDate;
	}

	public void setDiscoverDate(Date discoverDate) {
		this.discoverDate = discoverDate;
	}

	public String getUserName() {
		return userName;
	}

	public Date getSolveDate() {
		return solveDate;
	}

	public void setSolveDate(Date solveDate) {
		this.solveDate = solveDate;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProblemDesc() {
		return problemDesc;
	}

	public String getResposibler() {
		return resposibler;
	}

	public void setResposibler(String resposibler) {
		this.resposibler = resposibler;
	}

	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}

	public String getResolveWay() {
		return resolveWay;
	}

	public void setResolveWay(String resolveWay) {
		this.resolveWay = resolveWay;
	}

	public String getProblemStats() {
		return problemStats;
	}

	public void setProblemStats(String problemStats) {
		this.problemStats = problemStats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return problemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "PWEEKLY_PROBLEMS";
	}

}
