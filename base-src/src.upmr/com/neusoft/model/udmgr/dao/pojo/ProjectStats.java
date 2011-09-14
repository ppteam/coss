package com.neusoft.model.udmgr.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目里程碑记录明细建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ProjectStats extends AbstractEntity<String> {

	private static final long serialVersionUID = 8223754184434450017L;

	public ProjectStats() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getStatsId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "PROJECT_STATS";
	}

	// STATS_ID char(32) not null comment '记录ID。无实际意义 MD5(UUID)',
	private String statsId;
	// PROJECT_ID char(32) not null comment '项目记录主键，MD5(项目编号)',
	private String projectId;
	// MILESTONE_NAME char(32) not null comment '里程碑名称（MILESTONE_NAME）见数据字典',
	private String milestoneName;
	// PLAN_START date comment '计划开始日期',
	private Date planStart;
	// PLAN_END date comment '计划结束日期',
	private Date planEnd;
	// REALITY_START date comment '实际开始日期',
	private Date realityStart;
	// REALITY_END date comment '实际结束日期',
	private Date realityEnd;
	// MILESTONE_STATS char(32) not null comment '里程碑状态（见数据字典）',
	private String milestomeStats;
	// PLAN_VERSION varchar(32) comment '项目计划版本号',
	private String planVersion;
	// -------- 附加字段
	private String milestoneDes;

	public String getStatsId() {
		return statsId;
	}

	public void setStatsId(String statsId) {
		this.statsId = statsId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMilestoneName() {
		return milestoneName;
	}

	public String getMilestoneDes() {
		return milestoneDes;
	}

	public void setMilestoneDes(String milestoneDes) {
		this.milestoneDes = milestoneDes;
	}

	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Date getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}

	public Date getRealityStart() {
		return realityStart;
	}

	public void setRealityStart(Date realityStart) {
		this.realityStart = realityStart;
	}

	public Date getRealityEnd() {
		return realityEnd;
	}

	public void setRealityEnd(Date realityEnd) {
		this.realityEnd = realityEnd;
	}

	public String getMilestomeStats() {
		return milestomeStats;
	}

	public void setMilestomeStats(String milestomeStats) {
		this.milestomeStats = milestomeStats;
	}

	public String getPlanVersion() {
		return planVersion;
	}

	public void setPlanVersion(String planVersion) {
		this.planVersion = planVersion;
	}

}
