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
public class CheckingRule extends AbstractEntity<String> {

	private static final long serialVersionUID = 5006814809832551436L;

	public CheckingRule() {
	}

	public String getId() {
		return getRuleId();
	}

	public String getTVName() {
		return "CHECKING_RULE";
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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

	public String getBeiginWkTime() {
		return beiginWkTime;
	}

	public void setBeiginWkTime(String beiginWkTime) {
		this.beiginWkTime = beiginWkTime;
	}

	public String getEndWkTime() {
		return endWkTime;
	}

	public void setEndWkTime(String endWkTime) {
		this.endWkTime = endWkTime;
	}

	public Date getEntryIngDate() {
		return entryIngDate;
	}

	public void setEntryIngDate(Date entryIngDate) {
		this.entryIngDate = entryIngDate;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getBeginWeek() {
		return beginWeek;
	}

	public void setBeginWeek(Integer beginWeek) {
		this.beginWeek = beginWeek;
	}

	public Integer getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	// RULE_ID char(32) not null comment '逻辑主键-UUID',
	private String ruleId;
	// DEPT_ID char(32) not null comment '部门ID（UUID），规则适用的部门',
	private String deptId;
	// USER_ID char(32) not null comment '填写人ID',
	private String userId;
	// START_DATE date not null comment '生效日期',
	private Date startDate;
	// END_DATE date not null comment '规则作废日期',
	private Date endDate;
	// BEGINWK_TIME char(5) not null comment '上班时间 格式(mm:dd)',
	private String beiginWkTime;
	// ENDWK_TIME char(5) not null comment '下班时间 格式 mm:dd',
	private String endWkTime;
	// ENTRYING_DATE date not null comment '规则录入日',
	private Date entryIngDate;
	// RULE_NAME 规则名称
	private String ruleName;
	// BEGIN_WEEK int(1) not null comment '开始周（1--7/周一---周日）',
	private Integer beginWeek;
	// END_WEEK int(1) not null comment '开始周（1--7/周一---周日）',
	private Integer endWeek;
	// ENABLED int(1) not null default 1 comment '是否激活 0/1 为激活/激活',
	private Integer enabled;
	// ---------------------------
	private String deptName;

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckingRule [ruleId=" + ruleId + ", deptId=" + deptId + ", userId=" + userId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", beiginWkTime=" + beiginWkTime + ", endWkTime=" + endWkTime
				+ ", entryIngDate=" + entryIngDate + ", ruleName=" + ruleName + "]";
	}

}
