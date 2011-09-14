package com.neusoft.model.udmgr.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {员工进出厂历史流水记录} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 21, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class TeamTransLogs extends AbstractEntity<String> {

	private static final long serialVersionUID = -6382180637213408005L;

	public TeamTransLogs() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getTransferId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "TEAM_TRANSFER_LOGS";
	}

	// TRANSFER_ID char(32) not null comment '逻辑主键、无实际意义 MD5（UUID）'
	private String transferId;
	// DEPT_ID char(32) comment '部门ID（5 位的定长字母构成）',
	private String deptId;
	// USER_ID char(32) comment '该ID作用范围为整个系统',
	private String userId;
	// JOIN_DATE date not null comment '进场时间',
	private Date joinDate;
	// LEAVE_DATE date default NULL comment '退场时间',
	private Date leaveDate;

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

}
