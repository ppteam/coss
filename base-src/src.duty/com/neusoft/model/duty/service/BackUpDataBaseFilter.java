package com.neusoft.model.duty.service;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.DateUtil;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {计划备份数据库 目前仅仅支持Window 操作系统} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Sep 1, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class BackUpDataBaseFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BackUpDataBaseFilter.class);

	private static final long serialVersionUID = -3446899712779846028L;

	public BackUpDataBaseFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		if (logger.isDebugEnabled()) {
			logger.debug("execute(IContext) backup database job - start");
		}
		try {
			String cmd = configuration.getString("backup.db.path") + DateUtil.fromatDate(null, null) + ".sql";
			if (logger.isDebugEnabled()) {
				logger.debug("execute(IContext) run command is [" + cmd + "]");
			}
			Runtime.getRuntime().exec(cmd);
			if (logger.isInfoEnabled()) {
				logger.info(cmd + " ---> run successfully");
			}
		} catch (Exception e) {
			logger.error("backup database has error [" + e.getMessage() + "]");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("execute(IContext)  backup database job - end");
		}
		return false;
	}

	// <property name="configuration" ref="configurationImpl" />
	private Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
