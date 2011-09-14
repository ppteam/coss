package com.neusoft.core.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Logs a principal out.
 * <p>
 * Polls a series of {@link LogoutHandler}s. The handlers should be specified in
 * the order they are required. Generally you will want to call logout handlers
 * <code>TokenBasedRememberMeServices</code> and
 * <code>SecurityContextLogoutHandler</code> (in that order).
 * <p>
 * After logout, a redirect will be performed to the URL determined by either
 * the configured <tt>LogoutSuccessHandler</tt> or the <tt>logoutSuccessUrl</tt>
 * , depending on which constructor was used.
 * 
 * @author Ben Alex
 */
public class DoLogoutFilter extends GenericFilterBean {

	private String filterProcessesUrl = "/j_spring_security_logout";
	private List<LogoutHandler> handlers;
	private LogoutSuccessHandler logoutSuccessHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.GenericFilterBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		if (logoutSuccessHandler == null) {
			Assert.hasLength(filterProcessesUrl, "filterProcessesUrl are required");
			logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
			((SimpleUrlLogoutSuccessHandler) logoutSuccessHandler).setDefaultTargetUrl(filterProcessesUrl);
		}
		Assert.notEmpty(handlers, "LogoutHandlers are required");
		Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
	}

	public DoLogoutFilter() {
	}

	// ~ Methods
	// ========================================================================================================

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (requiresLogout(request, response)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (logger.isDebugEnabled()) {
				logger.debug("Logging out user '" + auth + "' and transferring to logout destination");
			}
			for (LogoutHandler handler : handlers) {
				handler.logout(request, response, auth);
			}
			logoutSuccessHandler.onLogoutSuccess(request, response, auth);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * Allow subclasses to modify when a logout should take place.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * 
	 * @return <code>true</code> if logout should occur, <code>false</code>
	 *         otherwise
	 */
	protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');
		if (pathParamIndex > 0) {
			uri = uri.substring(0, pathParamIndex);
		}
		int queryParamIndex = uri.indexOf('?');
		if (queryParamIndex > 0) {
			uri = uri.substring(0, queryParamIndex);
		}
		if ("".equals(request.getContextPath())) {
			return uri.endsWith(filterProcessesUrl);
		}
		return uri.endsWith(request.getContextPath() + filterProcessesUrl);
	}

	public void setHandlers(List<LogoutHandler> handlers) {
		this.handlers = handlers;
	}

	public void setLogoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler) {
		this.logoutSuccessHandler = logoutSuccessHandler;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl + " isn't a valid value for"
				+ " 'filterProcessesUrl'");
		this.filterProcessesUrl = filterProcessesUrl;
	}

	protected String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
}
