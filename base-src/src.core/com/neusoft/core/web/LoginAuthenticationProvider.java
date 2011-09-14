package com.neusoft.core.web;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

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
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

	public LoginAuthenticationProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.dao.DaoAuthenticationProvider
	 * #
	 * additionalAuthenticationChecks(org.springframework.security.core.userdetails
	 * .UserDetails, org.springframework.security.authentication.
	 * UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;
		UsernamePasswordIpAuthenticationToken token = (UsernamePasswordIpAuthenticationToken) authentication;
		if (getSaltSource() != null) {
			salt = getSaltSource().getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					isIncludeDetailsObject() ? userDetails : null);
		}

		String presentedPassword = token.getCredentials().toString();

		if (!getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					isIncludeDetailsObject() ? userDetails : null);
		}
		String daoIp = ((LoginUser) userDetails).getIp();
		String loginIp = token.getIpAdr();
		if (!"255.255.255.255".equals(daoIp) && !daoIp.equals(loginIp)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
					isIncludeDetailsObject() ? userDetails : null);
		}
	}

}
