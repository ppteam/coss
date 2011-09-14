package com.neusoft.model.duty.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckDetail extends AbstractEntity<String> {

	private static final long serialVersionUID = 5006814809832551436L;

	public CheckDetail() {
	}

	public String getId() {
		return getDetailId();
	}

	public String getTVName() {
		return "CHECK_DETAIL";
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getDayStats() {
		return dayStats;
	}

	public void setDayStats(Integer dayStats) {
		this.dayStats = dayStats;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getBeginCheck() {
		return beginCheck;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setBeginCheck(String beginCheck) {
		this.beginCheck = beginCheck;
	}

	public String getEndCheck() {
		return endCheck;
	}

	public void setEndCheck(String endCheck) {
		this.endCheck = endCheck;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	// DETAIL_ID char(32) not null comment '逻辑ID 无实际意义',
	private String detailId;
	// USER_ID char(32) not null comment '签到人员ID',
	private String userId;
	// RULE_ID char(32) comment '采用规则ID',
	private String ruleId;
	// DAY_STATS int(1) not null comment '当日状态 0/1/2/3 正常/请假/调休/加班'
	private Integer dayStats;
	// BEGIN_STATS int(1) not null default 0 comment '签到情况:0/1/2 未签到/正常/迟到',
	private Integer beginStats;
	// END_STATS int(1) not null default 0 comment '签到情况:0/1/2 未签退/正常/早退',
	private Integer endStats;
	// CHECK_DATE date not null comment '签到日期',
	private Date checkDate;
	// BEGIN_CHECK char(5) comment '签到时间',
	private String beginCheck;
	// END_CHECK char(5) comment '签退时间',
	private String endCheck;
	// DEPT_ID char(32) comment '部门ID（UUID）',
	private String deptId;

	// extend properity
	private String deptName;

	private String userName;

	private String comments;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getBeginStats() {
		return beginStats;
	}

	public void setBeginStats(Integer beginStats) {
		this.beginStats = beginStats;
	}

	public Integer getEndStats() {
		return endStats;
	}

	public void setEndStats(Integer endStats) {
		this.endStats = endStats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckDetail [detailId=" + detailId + ", userId=" + userId + ", ruleId=" + ruleId
				+ ", dayStats=" + dayStats + ", checkDate=" + checkDate + ", beginCheck=" + beginCheck
				+ ", endCheck=" + endCheck + "]";
	}

}
