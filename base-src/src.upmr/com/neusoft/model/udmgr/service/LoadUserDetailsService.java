package com.neusoft.model.udmgr.service;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.ChainSupport;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.udmgr.dao.pojo.UserBaseInfo;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {用户登录处理流程} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 25, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class LoadUserDetailsService extends ChainSupport implements UserDetailsService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoadUserDetailsService.class);

	public LoadUserDetailsService() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException, DataAccessException {
		Assert.hasLength(loginId, "[loginId] is not null,please check code");
		LoginUser user = null;
		IContext context = creatContext();
		context.put("loginId", loginId);
		executeChain(context, "user_login_check_chain");
		// has some err in run chain
		if (context.get(IContext.KEY_ERROR) != null) {
			throw new UsernameNotFoundException(loginId);
		}
		UserBaseInfo baseInfo = (UserBaseInfo) context.getResultMap().get("login_user_basic");
		if (baseInfo == null) {
			throw new UsernameNotFoundException(loginId);
		} else {
			user = new LoginUser(loginId, baseInfo.getPassword(), baseInfo.getEnabled(), baseInfo.getUserId(),
					baseInfo.getName(), baseInfo.getIpAddress(), baseInfo);
			user.getAuthorities().add(new GrantedAuthorityImpl(rolePrefix + "LOGINOR"));
		}
		List<String> roleSet = (List<String>) context.getResultMap().get("login_user_roles");
		if (!CollectionUtils.isEmpty(roleSet)) {
			((List<String>) user.get(LoginUser.KEY_ROLES)).addAll(roleSet);
			if (logger.isDebugEnabled()) {
				logger.debug("[loginUser roleSet][" + roleSet + "]");
			}
		}
		List<String> authors = (List<String>) context.getResultMap().get("login_user_authors");
		if (authors != null) {
			user.getAuthors().addAll(authors);
			for (String author : user.getAuthors()) {
				user.getAuthorities().add(new GrantedAuthorityImpl(rolePrefix + author.toUpperCase()));
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("[" + user.getUsername() + "] has authors [" + user.getAuthorities() + "]");
		}
		return user;
	}

	private String rolePrefix = "";

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

}
