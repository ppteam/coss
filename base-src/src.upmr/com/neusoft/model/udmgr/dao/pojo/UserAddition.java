package com.neusoft.model.udmgr.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {人员基本信息建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class UserAddition extends AbstractEntity<String> {

	private static final long serialVersionUID = 5027670795133869524L;

	public UserAddition() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "USER_BASIC";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getUserId();
	}

	// USER_ID char(32) not null comment '该ID作用范围为整个系统 MD5(身份证)',
	private String userId;
	// JOIN_WORK text comment '参与过的项目',
	private String joinWork;
	// JOIN_TRAIN text comment '参加过的培训',
	private String joinTrain;
	// SPECIALITY text comment '个人特长',
	// SKILL_SPECIALITY text comment '技术专长和业务专长',
	// MANAGE_EMPIRICAL text comment '从事过的技术及管理工作',
	// CERTIFICATE text comment '获得专业证书',

	// -----------------extend fileds----------------------
	private String deptName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJoinWork() {
		return joinWork;
	}

	public void setJoinWork(String joinWork) {
		this.joinWork = joinWork;
	}

	public String getJoinTrain() {
		return joinTrain;
	}

	public void setJoinTrain(String joinTrain) {
		this.joinTrain = joinTrain;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
