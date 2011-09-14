package com.neusoft.model.arsd.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {权限管理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 11, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class AuthorityInfo extends AbstractEntity<String> {

	private static final long serialVersionUID = 156058372778746821L;

	public AuthorityInfo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getAuthorId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "AUTHORITY_INFO";
	}

	// AUTHOR_ID char(32) not null comment '权限记录PK，UUID',
	private String authorId;
	// AUTHOR_SIGN varchar(32) not null comment '权限标识（英文标识）',
	private String authorSign;
	// AUTHOR_DESC varchar(64) not null comment '权限描述，用于页面显示',
	private String authorDesc;
	// ENABLED numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
	private Integer enabled;
	// AUTHOR_NAME varchar(32) not null comment '权限名称，中文名称',
	private String authorName;

	// 权限所属的角色ID
	private String roleId;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorSign() {
		return authorSign;
	}

	public void setAuthorSign(String authorSign) {
		this.authorSign = authorSign;
	}

	public String getAuthorDesc() {
		return authorDesc;
	}

	public void setAuthorDesc(String authorDesc) {
		this.authorDesc = authorDesc;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthorityInfo [authorId=" + authorId + ", authorSign=" + authorSign + ", authorDesc=" + authorDesc
				+ ", enabled=" + enabled + ", authorName=" + authorName + "]";
	}

}
