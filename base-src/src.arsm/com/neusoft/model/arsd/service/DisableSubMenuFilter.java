package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {判断是否禁用全部子菜单} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DisableSubMenuFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DisableSubMenuFilter.class);

	private static final long serialVersionUID = -1661357785545171889L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		SourceInfo instance = context.getObject("instance", SourceInfo.class);
		if (StringUtils.hasLength(instance.getId())) { // update
			if (instance.getSrcType() == SourceInfo.TYPE_MENU && instance.getEnabled() == 0) {
				int rows = sourceInfoDao.update("disable_all_submenu", instance);
				if (logger.isDebugEnabled()) {
					logger.debug("update SourceInfo_submenu and res [" + rows + "]");
				}
				context.putResults(getDefResultKey(), rows);
			}
		}
		return false;
	}

}
