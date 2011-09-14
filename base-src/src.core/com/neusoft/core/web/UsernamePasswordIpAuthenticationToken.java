package com.neusoft.core.web;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 22, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class UsernamePasswordIpAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = -1582417488816605280L;

	public UsernamePasswordIpAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public UsernamePasswordIpAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	// 记录用户登录的IP地址
	private String ipAdr;

	public String getIpAdr() {
		return ipAdr;
	}

	public void setIpAdr(String ipAdr) {
		this.ipAdr = ipAdr;
	}

}
