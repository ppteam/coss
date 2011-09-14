package com.neusoft.core.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {抽象Servlet 编写，注入Spring WebIOc 的句柄} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 24, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = -7980784357869835866L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AbstractServlet() {
		super();
	}

	/**
	 * {提供Serlvet访问SpringIOC容器句柄}
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ApplicationContext
	 */
	protected ApplicationContext getApplicationContext(HttpServletRequest request) {
		return RequestContextUtils.getWebApplicationContext(request);
	}

}
