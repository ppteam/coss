package com.neusoft.model.udmgr.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目基本信息表建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ProjectInfo extends AbstractEntity<String> {

	private static final long serialVersionUID = 8223754184434450017L;

	public ProjectInfo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getProjectId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "PROJECT_INFO";
	}

	// PROJECT_ID char(32) not null comment '项目记录主键，MD5(项目编号)',
	private String projectId;
	// PROJECT_NO varchar(64) not null comment '项目编号',
	private String projectNo;
	// PROJECT_NAME varchar(128) not null,
	private String projectName;
	// PROJECT_STATS char(32) not null comment '见数据字典：项目状态',
	private String projectStats;
	// DEPT_ID char(32) comment '部门ID（UUID）',
	private String deptId;
	// NEED_REPORT int default 1 comment '是否需要填写周报 0:不需要/1：需要',
	private Integer needReport;
	// PROJECT_DES varchar(256) comment '项目描述',
	private String projectDes;
	// WEEK_BEGIN
	private Integer weekBegin;
	// 项目计划工作量 WORK_DAYS
	private Integer workDays = 0;
	// PROJECT_AGENT varchar(32) comment '经办人',
	private String proAgent;
	// PROJECT_OPER varchar(32) comment '业务负责人',
	private String proOper;
	// 实际发生工作量
	private Integer happenDays = 0;
	// HOURS_RULE int(1) not null default 8 comment '一天工作日度量 默认是8小时为一个工作日',
	private Integer hoursRule = 8;

	private String psmName;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public Integer getHappenDays() {
		return happenDays;
	}

	public void setHappenDays(Integer happenDays) {
		this.happenDays = happenDays;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStats() {
		return projectStats;
	}

	public String getProAgent() {
		return proAgent;
	}

	public void setProAgent(String proAgent) {
		this.proAgent = proAgent;
	}

	public String getProOper() {
		return proOper;
	}

	public void setProOper(String proOper) {
		this.proOper = proOper;
	}

	public void setProjectStats(String projectStats) {
		this.projectStats = projectStats;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getWeekBegin() {
		return weekBegin;
	}

	public void setWeekBegin(Integer weekBegin) {
		this.weekBegin = weekBegin;
	}

	public String getPsmName() {
		return psmName;
	}

	public void setPsmName(String psmName) {
		this.psmName = psmName;
	}

	public Integer getNeedReport() {
		return needReport;
	}

	public void setNeedReport(Integer needReport) {
		this.needReport = needReport;
	}

	public String getProjectDes() {
		return projectDes;
	}

	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}

	public void setProjectDes(String projectDes) {
		this.projectDes = projectDes;
	}

	public Integer getHoursRule() {
		return hoursRule;
	}

	public void setHoursRule(Integer hoursRule) {
		this.hoursRule = hoursRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectInfo [projectId=" + projectId + ", projectNo=" + projectNo + ", projectName="
				+ projectName + ", projectStats=" + projectStats + ", deptId=" + deptId + ", needReport="
				+ needReport + ", projectDes=" + projectDes + "]";
	}

}
