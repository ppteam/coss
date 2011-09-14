package com.neusoft.model.duty.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {节假日数据表建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 15, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HolidayRule extends AbstractEntity<String> {

	private static final long serialVersionUID = 5006814809832551378L;

	public HolidayRule() {
	}

	public String getId() {
		return getHolidayId();
	}

	public String getTVName() {
		return "HOLIDAY_RULE";
	}

	public String getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
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

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getAdditionInfo() {
		return additionInfo;
	}

	public void setAdditionInfo(String additionInfo) {
		this.additionInfo = additionInfo;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getDayType() {
		return dayType;
	}

	public void setDayType(Integer dayType) {
		this.dayType = dayType;
	}

	// HOLIDAY_ID char(32) not null comment '逻辑主键-UUID',
	private String holidayId;
	// USER_ID char(32) not null comment '填写人ID',
	private String userId;
	// HOLIDAY_NAME varchar(64) not null comment '规则名称',
	private String holidayName;
	// START_DATE date not null comment '生效日期',
	private Date startDate;
	// END_DATE date not null comment '规则作废日期',
	private Date endDate;
	// ENTRY_DATE date not null comment '规则录入日'
	private Date entryDate;
	// ADDITION_INFO varchar(128) comment '附加说明'
	private String additionInfo;
	// ENABLED
	private Integer enabled;

	private Integer dayType = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HolidayRule [holidayId=" + holidayId + ", userId=" + userId + ", holidayName=" + holidayName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", entryDate=" + entryDate + ", additionInfo="
				+ additionInfo + "dayType=" + dayType + "]";
	}

}
