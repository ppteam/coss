package com.neusoft.model.twitter.service;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;
import com.neusoft.core.web.TwitterContext;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {请求 Twitter 请求} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Sep 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class TwitterContextServletListener implements ServletRequestListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.
	 * ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent event) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletRequestListener#requestInitialized(javax.servlet
	 * .ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		System.out.println(request.getServletPath());
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		IContext loginUser = (IContext) request.getSession().getAttribute("LoginUser");
		if (loginUser != null) {
			try {
				String userId = loginUser.getString(LoginUser.KEY_USERID);
				if ("facebook".equals(request.getParameter("twitter"))) {
					TwitterContext.getInstance().updateTwitterNum(userId, 0);
				} else {
					TwitterContext.getInstance().removeById(userId);
				}
			} catch (BaseException e) {
				e.printStackTrace();
			}
		}
	}

}
