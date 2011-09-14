package com.neusoft.model.pwreport.dao.pojo;

import java.util.Date;
import java.util.List;

import com.neusoft.core.dao.Impl.AbstractEntity;
import com.neusoft.model.udmgr.dao.pojo.ProjectStats;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {mapping 项目周报主表} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class PweeklyReport extends AbstractEntity<String> {

	private static final long serialVersionUID = -1104865590089330511L;

	public PweeklyReport() {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
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

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getEfficiencyExecute() {
		return efficiencyExecute;
	}

	public void setEfficiencyExecute(String efficiencyExecute) {
		this.efficiencyExecute = efficiencyExecute;
	}

	public String getHumanResource() {
		return humanResource;
	}

	public void setHumanResource(String humanResource) {
		this.humanResource = humanResource;
	}

	public String getRequirementAlter() {
		return requirementAlter;
	}

	public void setRequirementAlter(String requirementAlter) {
		this.requirementAlter = requirementAlter;
	}

	public String getClientRelation() {
		return clientRelation;
	}

	public void setClientRelation(String clientRelation) {
		this.clientRelation = clientRelation;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getLeaderAppraise() {
		return leaderAppraise;
	}

	public void setLeaderAppraise(String leaderAppraise) {
		this.leaderAppraise = leaderAppraise;
	}

	public String getResultsShow() {
		return resultsShow;
	}

	public void setResultsShow(String resultsShow) {
		this.resultsShow = resultsShow;
	}

	public String getCweekPlan() {
		return cweekPlan;
	}

	public void setCweekPlan(String cweekPlan) {
		this.cweekPlan = cweekPlan;
	}

	public String getCompletePlan() {
		return completePlan;
	}

	public void setCompletePlan(String completePlan) {
		this.completePlan = completePlan;
	}

	public String getNweekPlan() {
		return nweekPlan;
	}

	public void setNweekPlan(String nweekPlan) {
		this.nweekPlan = nweekPlan;
	}

	public String getPsmName() {
		return psmName;
	}

	public void setPsmName(String psmName) {
		this.psmName = psmName;
	}

	public String getPsmerId() {
		return psmerId;
	}

	public void setPsmerId(String psmerId) {
		this.psmerId = psmerId;
	}

	public Integer getWeekBegin() {
		return weekBegin;
	}

	public void setWeekBegin(Integer weekBegin) {
		this.weekBegin = weekBegin;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	// REPORT_ID char(32) not null comment '周报ID :MD5（UUID）',
	private String reportId;
	// PROJECT_ID char(32) comment '项目记录主键，MD5(项目编号)',
	private String projectId;
	// USER_ID char(32) not null comment '项目指定的PSM（报表字段）',
	private String userId;
	// START_DATE date not null comment '起始日期',
	private Date startDate;
	// END_DATE date not null comment '截至日期',
	private Date endDate;
	// RECORD_DATE date not null comment '填写日期',
	private Date recordDate;
	// REPORT_NO varchar(64) not null,
	private String reportNo;
	// EFFICIENCY_EXECUTE char(32) not null comment '执行效率（见数据字典）',
	private String efficiencyExecute;
	// HUMAN_RESOURCE char(32) not null comment '项目人力资源（见数据字典）',
	private String humanResource;
	// REQUIREMENT_ALTER char(32) not null comment '需求变更频率（见数据字典）',
	private String requirementAlter;
	// CLIENT_RELATION char(32) not null comment '客户关系（见数据字典）',
	private String clientRelation;
	// PROJECT_DESC text comment '项目描述',
	private String projectDesc;
	// LEADER_APPRAISE text comment '项目评价',
	private String leaderAppraise;
	// RESULTS_SHOW text comment '成果物',
	private String resultsShow;
	// CWEEK_PLAN text comment '本周计划',
	private String cweekPlan;
	// COMPLETE_PLAN text comment '计划完成情况',
	private String completePlan;
	// NWEEK_PLAN text comment '下周工作计划',
	private String nweekPlan;
	// REPORT_TYPE int(1) not null default 0 comment '周报类型 0/1 测试/开发 def=0',
	private Integer reportType;

	private String psmerId;

	private String psmName; // 项目组长名称

	private String projectName; // 项目名称

	private Integer weekBegin = 7;
	
	private List<PweeklyProblems> problems;

	private List<PweeklyMemberplan> memberplans;

	private List<ProjectStats> stats;

	public List<ProjectStats> getStats() {
		return stats;
	}

	public void setStats(List<ProjectStats> stats) {
		this.stats = stats;
	}

	public List<PweeklyProblems> getProblems() {
		return problems;
	}

	public void setProblems(List<PweeklyProblems> problems) {
		this.problems = problems;
	}

	public List<PweeklyMemberplan> getMemberplans() {
		return memberplans;
	}

	public void setMemberplans(List<PweeklyMemberplan> memberplans) {
		this.memberplans = memberplans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return reportId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "PWEEKLY_REPORT";
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PweeklyReport [reportId=" + reportId + ", projectId=" + projectId + ", userId=" + userId
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", recordDate=" + recordDate + ", reportNo="
				+ reportNo + ", projectDesc=" + projectDesc + ", leaderAppraise=" + leaderAppraise + "]";
	}

}
