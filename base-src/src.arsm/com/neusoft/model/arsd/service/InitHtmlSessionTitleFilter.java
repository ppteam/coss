package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import org.apache.commons.lang.math.RandomUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.ConfigUtilsSupportFilter;
import com.neusoft.core.exception.BaseException;

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
public class InitHtmlSessionTitleFilter extends ConfigUtilsSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitHtmlSessionTitleFilter.class);

	private static final long serialVersionUID = 2005284181436642089L;

	public InitHtmlSessionTitleFilter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		String def_title = configuration.getString("note.def.title");
		int seed = RandomUtils.nextInt(configuration.getInt("note.lizhi.size"));
		String title = configuration.getString(key + seed, def_title);
		if (logger.isDebugEnabled()) {
			logger.debug("this title is [" + title + "]");
		}
		context.put("KEY_TITLE", ":: Neusoft ::" + title);
		return false;
	} // end_if

	private String key = "note.lizhi.num";

	public void setKey(String key) {
		this.key = key;
	}

}
