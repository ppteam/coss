package com.neusoft.model.udmgr.service;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.ChainRuner;
import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {登录成功之后的初始化用户信息} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 26, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements
		ApplicationContextAware {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AuthenticationSuccessHandler.class);

	private RequestCache requestCache = new HttpSessionRequestCache();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * SimpleUrlAuthenticationSuccessHandler
	 * #onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		// init user other infos
		if (logger.isDebugEnabled()) {
			logger.debug("loginUser baseinfo is [" + authentication.getPrincipal().toString() + "]");
		}
		// load login_user add info for system
		try {
			IContext loginUser = (IContext) authentication.getPrincipal();
			ChainRuner.runner(context, "load_loginor_addinfo_chain", loginUser);
			request.getSession().setAttribute("LoginUser", loginUser);
		} catch (BaseException e) {
			logger.error("load login user has error", e);
			throw new ServletException(e.getMessage());
		}
		// init user other infos
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		if (isAlwaysUseDefaultTargetUrl()
				|| StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		clearAuthenticationAttributes(request);
		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		}
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	private ApplicationContext context;
}
