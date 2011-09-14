package com.neusoft.model.duty.dao.pojo;

import java.util.Date;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 17, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class JobDetail extends AbstractEntity<String> {

	private static final long serialVersionUID = 5506085536766863656L;

	public JobDetail() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "JOB_DETAIL";
	}

	// DETAIL_ID char(32) not null comment '逻辑ID 无实际意义',
	private String detailId;
	// JOB_ID char(32) not null comment '运行任务ID',
	private String jobId;
	// LAST_DATE date not null comment '最后一次运行时间',
	private Date lastDate;
	// SPLIT_DAY int(1) not null default 1 comment '间隔时间 天为单位',
	private int splitDay;
	// CHAIN_NAME VARCHAR(128) not null comment '执行任务的责任链名称',
	private String chainName;

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public int getSplitDay() {
		return splitDay;
	}

	public void setSplitDay(int splitDay) {
		this.splitDay = splitDay;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

}
