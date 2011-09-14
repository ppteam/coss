package com.neusoft.core.common.Impl;

import org.apache.log4j.Logger;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.Validate;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b>{读却配置文件 适配器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 16, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractConfigurationAdapter extends PropertiesConfiguration implements ResourceLoaderAware {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractConfigurationAdapter.class);

	public AbstractConfigurationAdapter() {
	}

	/**
	 * {初始化配置文件读取引擎}
	 */
	public void init() {
		Validate.notEmpty(cfgPath, "config file path is not empty");
		if (logger.isDebugEnabled()) {
			logger.debug("load config from [" + cfgPath + "]");
		}
		try {
			super.load(resloader.getResource(cfgPath).getFile());
		} catch (Exception e) {
			logger.error("[load file :" + cfgPath + "] is fail ...", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ResourceLoaderAware#setResourceLoader(org
	 * .springframework.core.io.ResourceLoader)
	 */
	public void setResourceLoader(ResourceLoader resloader) {
		this.resloader = resloader;
	}

	private ResourceLoader resloader;

	// 配置文件路径
	private String cfgPath;

	public void setCfgPath(String cfgPath) {
		this.cfgPath = cfgPath;
	}
}
