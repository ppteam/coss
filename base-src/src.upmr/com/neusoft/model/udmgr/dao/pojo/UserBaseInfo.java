package com.neusoft.model.udmgr.dao.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

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
public class UserBaseInfo extends AbstractEntity<String> {

	private static final long serialVersionUID = 5027670795133869524L;

	public UserBaseInfo() {
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
	// DEPT_ID char(32) comment '部门ID（UUID）',
	private String deptId;
	// USER_LOGINID varchar(64) not null comment '用户登录帐号',
	private String loginId;
	// USER_SEXED numeric(1) comment '用户性别: 0：女/1：男',
	private Integer sexed;
	// USER_PASSWORD char(32) not null comment '用户登录密码 MD5 加密',
	private String password;
	// USER_NAME varchar(32) not null comment '用户姓名',
	private String name;
	// USER_IDENTITY varchar(18) not null,
	private String identity;
	// ENABLED numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
	private Integer enabled;
	// EXTENSION_NO varchar(12) comment '分机号码',
	private String extensionNo;
	// WORKING_PLACE varchar(256) comment '办公地点',
	private String workPlace;
	// IP_ADDRESS varchar(15) comment '分配给员工的IP地址',
	private String ipAddress;
	// WOEK_EMAIL varchar(64) comment '工作邮箱',
	private String workEmail;
	// CMPY_EMAIL varchar(64) comment '单位邮箱',
	private String cmpyEmail;
	// USER_MOBILENO varchar(18),
	private String mobileNo;
	// POLITICS_STATUS char(5) comment '政治面貌，来自数据字典',
	private String polticsStatus;
	// MARITAL_STATUS numeric(1) default 0 comment '婚姻情况：0 未婚 / 1 已婚 def=0',
	private Integer maritalStatus;
	// BIRTHDAY date comment '生日日期 （yyyy-MM-dd）',
	private Date birthday;
	// EMPLOYEE_NO varchar(32) comment '工卡号码，单位给每个员工的的卡号',
	private String employeeNo;
	// QUALIFICATIONS char(32) comment '职称等级（见数据字典）',
	private String qualifications;
	// COMPANY_NAME varchar(64) comment '公司名称',
	private String companyName;
	// POST_TYPE char(32) comment '公司部门：见数据字典 金融 软开 其他 等',
	private String postType;
	// EDUCATION_BG char(5) comment '本人学历（来自数据字典）',
	private String educationBg;
	// SPECIALTY_KNOW varchar(64) comment '所学专业',
	private String specialtyKnow;
	// GRADUATE_SCHOOL varchar(128) comment '毕业学校',
	private String graduateSchool;
	// TEAM_STATS VARCHAR(32) not null comment '当前团队状态(在场、离场) 见数据字典',
	private Integer teamStats;
	// DEGREE INT(1) not null comment '当前团队状态(在场、离场) 见数据字典',
	private String degree;
	// WORKDATE INT(1) not null comment '当前团队状态(在场、离场) 见数据字典',
	private Date workdate;
	// -----------------extend fileds----------------------
	private String deptName;
	// 角色列表
	private Set<String> roleSet = new HashSet<String>();
	// 数据集列表
	private Set<String> dataSet = new HashSet<String>();
	// 部门所在路径
	private String deptPath;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getDeptPath() {
		return deptPath;
	}

	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getSexed() {
		return sexed;
	}

	public void setSexed(Integer sexed) {
		this.sexed = sexed;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public Set<String> getRoleSet() {
		return roleSet;
	}

	public Set<String> getDataSet() {
		return dataSet;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getExtensionNo() {
		return extensionNo;
	}

	public void setExtensionNo(String extensionNo) {
		this.extensionNo = extensionNo;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPolticsStatus() {
		return polticsStatus;
	}

	public void setPolticsStatus(String polticsStatus) {
		this.polticsStatus = polticsStatus;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getEducationBg() {
		return educationBg;
	}

	public void setEducationBg(String educationBg) {
		this.educationBg = educationBg;
	}

	public String getSpecialtyKnow() {
		return specialtyKnow;
	}

	public void setSpecialtyKnow(String specialtyKnow) {
		this.specialtyKnow = specialtyKnow;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public Integer getTeamStats() {
		return teamStats;
	}

	public void setTeamStats(Integer teamStats) {
		this.teamStats = teamStats;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	public String getCmpyEmail() {
		return cmpyEmail;
	}

	public void setCmpyEmail(String cmpyEmail) {
		this.cmpyEmail = cmpyEmail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Assert.isNull(obj);
		Assert.isInstanceOf(UserBaseInfo.class, obj);
		if (getId() == null || ((UserBaseInfo) obj).getId() == null) {
			return false;
		} else {
			return getId().equals(((UserBaseInfo) obj).getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBaseInfo [userId=" + userId + ", deptId=" + deptId + ", loginId=" + loginId + ", name=" + name
				+ ", enabled=" + enabled + ", ipAddress=" + ipAddress + ", teamStats=" + teamStats + "]";
	}

}
