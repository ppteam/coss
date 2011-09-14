package com.neusoft.model.arsd.dao.pojo;

import java.util.ArrayList;
import java.util.List;

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
public class RoleInfo extends AbstractEntity<String> {

	private static final long serialVersionUID = 151258372778746821L;

	public RoleInfo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getRoleId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "ROLE_INFO";
	}

	// ROLE_ID char(32) not null comment '角色记录PK，UUID',
	private String roleId;
	// ROLE_NAME varchar(32) not null comment '角色名称，中文描述',
	private String roleName;
	// ROLE_SIGN varchar(32) not null comment '权限标识（英文标识）',
	private String roleSign;
	// ROLE_DESC varchar(64) comment '权限描述，用于页面显示',
	private String roleDesc;
	// ENABLED numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
	private Integer enabled;

	private List<AuthorityInfo> authorityInfos = new ArrayList<AuthorityInfo>();

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * {添加权限 绑定角色}
	 * 
	 * @param authorityInfo
	 */
	public void putAuthorityInfo(AuthorityInfo authorityInfo) {
		authorityInfos.add(authorityInfo);
	}

	public List<AuthorityInfo> getAuthorityInfos() {
		return authorityInfos;
	}

	public void setAuthorityInfos(List<AuthorityInfo> authorityInfos) {
		this.authorityInfos = authorityInfos;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

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
		return "RoleInfo [roleId=" + roleId + ", roleName=" + roleName + ", roleSign=" + roleSign + ", roleDesc="
				+ roleDesc + ", enabled=" + enabled + "]";
	}

}
