package com.neusoft.model.reportforms.dao.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.core.util.DateUtil;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {考勤汇总} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DutyCollect {

	private String recId;

	private String projectName;

	private String projectId;

	private String userId;

	private String userName;
	// 病假
	private int sickDays = 0;
	// 事假
	private int thindDays = 0;
	// 迟到
	private int laterDay = 0;
	// 旷工
	private int leaveDay = 0;
	// 婚假
	private int marryDays = 0;
	// 丧假
	private int deadDays = 0;
	// 产假
	private int laborDays = 0;
	// 年假
	private int happyDays = 0;
	// 探亲
	private int homeDays = 0;
	// 培训
	private int learnDays = 0;
	// 调休
	private int changeDays = 0;
	// 考勤统计
	private int dutyTotal = 0;
	// 全部合计
	private int allTotal = 0;

	// 备注
	private String comments;

	private StringBuffer cmtBuffer;

	private int wkofYear;

	// 周开始
	private Date weekStart;
	// 周结束
	private Date weekEnd;

	private String groupBy;

	private List<DutyCollect> collects;

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	private DutyCollect() {
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public void setCmtBuffer(String comments) {
		if (this.cmtBuffer == null) {
			this.cmtBuffer = new StringBuffer(comments);
		} else {
			this.cmtBuffer.append("/").append(comments);
		}
	}

	public static DutyCollect getInstance() {
		return new DutyCollect();
	}

	/**
	 * {统计对象生成}
	 * 
	 * @return
	 */
	public static DutyCollect getTotalInstance() {
		DutyCollect collect = new DutyCollect();
		collect.setRecId("neusoft_duty_collect");
		collect.setUserName("合计");
		collect.setGroupBy("total");
		collect.setComments("人日");
		return collect;
	}

	/**
	 * {内部二次处理数据}
	 * 
	 * @return
	 */
	public void builderSelf(boolean isall) {
		dutyTotal = sickDays + thindDays + laterDay + leaveDay + marryDays + deadDays + laborDays + happyDays
				+ homeDays + learnDays;
		allTotal = dutyTotal + changeDays;
		if (isall) {
			comments = cmtBuffer == null ? "" : cmtBuffer.toString();
			groupBy = DateUtil.fromatDate(weekStart, "MM-dd") + "/" + DateUtil.fromatDate(weekEnd, "MM-dd");
			recId = getUserId() + wkofYear;
		}
	}

	public int getWkofYear() {
		return wkofYear;
	}

	public void setWkofYear(int wkofYear) {
		this.wkofYear = wkofYear;
	}

	public String getProjectName() {
		return projectName;
	}

	public Date getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(Date weekStart) {
		this.weekStart = weekStart;
	}

	public Date getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(Date weekEnd) {
		this.weekEnd = weekEnd;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSickDays() {
		return sickDays;
	}

	public void setSickDays(int sickDays) {
		this.sickDays += sickDays;
	}

	public int getThindDays() {
		return thindDays;
	}

	public int getDutyTotal() {
		return dutyTotal;
	}

	public void setDutyTotal(int dutyTotal) {
		this.dutyTotal = dutyTotal;
	}

	public StringBuffer getCmtBuffer() {
		return cmtBuffer;
	}

	public void setCmtBuffer(StringBuffer cmtBuffer) {
		this.cmtBuffer = cmtBuffer;
	}

	public void setThindDays(int thindDays) {
		this.thindDays += thindDays;
	}

	public int getLaterDay() {
		return laterDay;
	}

	public void setLaterDay(int laterDay) {
		this.laterDay += laterDay;
	}

	public int getLeaveDay() {
		return leaveDay;
	}

	public void setLeaveDay(int leaveDay) {
		this.leaveDay += leaveDay;
	}

	public int getMarryDays() {
		return marryDays;
	}

	public void setMarryDays(int marryDays) {
		this.marryDays += marryDays;
	}

	public int getDeadDays() {
		return deadDays;
	}

	public void setDeadDays(int deadDays) {
		this.deadDays += deadDays;
	}

	public int getLaborDays() {
		return laborDays;
	}

	public void setLaborDays(int laborDays) {
		this.laborDays += laborDays;
	}

	public int getHappyDays() {
		return happyDays;
	}

	public void setHappyDays(int happyDays) {
		this.happyDays += happyDays;
	}

	public int getHomeDays() {
		return homeDays;
	}

	public void setHomeDays(int homeDays) {
		this.homeDays += homeDays;
	}

	public int getLearnDays() {
		return learnDays;
	}

	public void setLearnDays(int learnDays) {
		this.learnDays += learnDays;
	}

	public int getChangeDays() {
		return changeDays;
	}

	public void setChangeDays(int changeDays) {
		this.changeDays += changeDays;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<DutyCollect> getCollects() {
		return collects;
	}

	public void setCollects(List<DutyCollect> collects) {
		this.collects = collects;
	}

	public void setSubDuty(DutyCollect dutyCollect) {
		if (collects == null) {
			collects = new ArrayList<DutyCollect>();
		}
		collects.add(dutyCollect);

	}

}
