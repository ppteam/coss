package com.neusoft.model.udmgr.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目成员异动表} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 27, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class PtransferLogs extends AbstractEntity<String> {

	private static final long serialVersionUID = 6815369850882780744L;

	public PtransferLogs() {
	}

	// STATS_ID char(32) not null comment '记录ID。无实际意义 MD5(UUID)',
	private String statsId;
	// PROJECT_ID char(32) not null comment '项目记录主键，MD5(项目编号)',
	private String projectId;
	// USER_ID char(32) comment '该ID作用范围为整个系统 MD5(身份证)',
	private String userId;
	// ROLE_TYPE char(32) comment '角色记录PK，UUID',
	private String roleType;
	// JOININ_DATE date not null comment '加入时间',
	private String joinDate;
	// LEAVE_DATE date,
	private String leaveDate;
	// CURRT_STATS int(1) not null comment '当前状态（0：离开/1：在场）',
	private Integer curtStats;
	// --- addation filed
	private String userName;

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
		return "PRO_TRANSFER_LOGS";
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Integer getCurtStats() {
		return curtStats;
	}

	public void setCurtStats(Integer curtStats) {
		this.curtStats = curtStats;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
