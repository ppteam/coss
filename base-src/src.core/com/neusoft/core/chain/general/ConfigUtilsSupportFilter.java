package com.neusoft.core.chain.general;

import org.apache.commons.configuration.Configuration;

import com.neusoft.core.chain.Impl.FilterBase;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Sep 2, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class ConfigUtilsSupportFilter extends FilterBase {

	private static final long serialVersionUID = -815426627113790281L;

	public ConfigUtilsSupportFilter() {
		super();
	}

	protected Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
