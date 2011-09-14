package com.neusoft.model.reportforms.dao.pojo;

public class WorkDetail {

	private int jobHours = 0;

	private int overHours = 0;

	private int levHours = 0;

	private int dayNum;

	private String begType;

	private String userId;

	private int hoursRule = 8;

	public WorkDetail() {
	}

	public int getJobHours() {
		return jobHours;
	}

	public void setJobHours(int jobHours) {
		this.jobHours = jobHours;
	}

	public int getOverHours() {
		return overHours;
	}

	public String getBegType() {
		return begType;
	}

	public int getHoursRule() {
		return hoursRule;
	}

	public void setHoursRule(int hoursRule) {
		this.hoursRule = hoursRule;
	}

	public void setBegType(String begType) {
		this.begType = begType;
	}

	public void setOverHours(int overHours) {
		this.overHours = overHours;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLevHours() {
		return levHours;
	}

	public void setLevHours(int levHours) {
		this.levHours = levHours;
	}

	/**
	 * {合计}
	 * 
	 * @return
	 */
	public int total() {
		return jobHours + overHours;
	}
}
