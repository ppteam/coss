package com.neusoft.core.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.core.chain.Impl.ContextBase;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户登录信息} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 25, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class LoginUser extends ContextBase implements UserDetails {

	private static final long serialVersionUID = 2261990876015241655L;

	public static String KEY_USER_BAEN = "KEY_USER_BAEN";
	// 用户Id
	public static String KEY_USERID = "KEY_USERID";
	// 用户密码
	public static String KEY_PASSWORD = "KEY_PASSWORD";
	// 登录机器IP
	public static String KEY_REMOTEIP = "KEY_REMOTEIP";
	// 用户登录中文，名称
	public static String KEY_USERNAME = "KEY_USERNAME";
	// 用户登录帐号
	public static String KEY_LOGINID = "KEY_LOGINID";
	// 是否启用标志
	public static String KEY_ENABLED = "KEY_ENABLED";
	// 封装数据集权限的部门
	public static String KEY_DEPT_SET = "KEY_DEPT_SET";
	// 用户所在部门
	public static String KEY_DEPT = "KEY_DEPT_ID";

	public static String KEY_DATASET = "access_data_set";
	private List<String> dataSet = new ArrayList<String>();

	public static String KEY_AUTHORS = "KEY_AUTHORS";
	private List<String> authors = new ArrayList<String>();

	public static String KEY_AUTHORITYS = "KEY_AUTHORITYS";
	
	public static String KEY_TODAY = "KEY_TODAY";
	
	private Set<GrantedAuthority> authoritys = new HashSet<GrantedAuthority>();

	public static String KEY_MENULIST = "KEY_MENULIST";

	// 该用户的角色列表
	public static String KEY_ROLES = "KEY_ROLES";
	
	private List<String> rolesSet = new ArrayList<String>();

	/*
	 * {构造函数}
	 */
	public LoginUser(String loginId, String pwd, Integer enabled, String userId, String userName, String ipValue,
			UserBaseInfo baseInfo) {
		// init
		dataSet.add("NOTDATASET");
		authors.add("LOGINOR");
		put(KEY_LOGINID, loginId);
		put(KEY_PASSWORD, pwd);
		put(KEY_ENABLED, enabled);
		put(KEY_USERID, userId);
		put(KEY_USERNAME, userName);
		put(KEY_REMOTEIP, ipValue);
		put(KEY_AUTHORS, authors);
		put(KEY_AUTHORITYS, authoritys);
		put(KEY_DATASET, dataSet);
		put(KEY_DEPT, baseInfo.getDeptId());
		put(KEY_USER_BAEN, baseInfo);
		put(KEY_ROLES, rolesSet);
		put(KEY_TODAY, new Date());
	}

	public UserBaseInfo getUserBase() {
		return getObject(KEY_USER_BAEN, UserBaseInfo.class);
	}

	/**
	 * {返回当前登录用户部门ID}
	 */
	public String getDeptId() {
		return getObject(KEY_USER_BAEN, UserBaseInfo.class).getDeptId();
	}

	public void setMenuList(List<MenuModle> mentList) {
		put(KEY_MENULIST, mentList);
	}

	public void setAuthoritys(Set<GrantedAuthority> authoritys) {
		this.authoritys = authoritys;
	}

	public List<String> getDataSet() {
		return dataSet;
	}

	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * {返回用户登录之后的部门路径}
	 * 
	 * @return
	 */
	public String[] getDeptPath() {
		String[] res = null;
		if (getObject(KEY_USER_BAEN, UserBaseInfo.class) != null) {
			res = getObject(KEY_USER_BAEN, UserBaseInfo.class).getDeptPath().split("[-]");
		}
		return res;
	}

	public String getUserId() {
		return (String) get(KEY_USERID);
	}

	public String getName() {
		return (String) get(KEY_USERNAME);
	}

	public String getIp() {
		return (String) get(KEY_REMOTEIP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities
	 * ()
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		return authoritys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return (String) get(KEY_PASSWORD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return (String) get(KEY_LOGINID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked
	 * ()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		boolean res = false;
		try {
			res = getInteger(KEY_ENABLED) == 1 ? true : false;
		} catch (Exception e) {

		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginUser [userId=" + get(KEY_USERID) + ", password=" + get(KEY_PASSWORD) + ", name="
				+ get(KEY_USERNAME) + ", loginId=" + get(KEY_LOGINID) + ", enabled=" + get(KEY_ENABLED)
				+ ", authoritys=" + authoritys + ",IP=" + get(KEY_REMOTEIP) + "]";
	}

}
