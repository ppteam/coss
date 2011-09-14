package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.model.arsd.dao.pojo.SourceInfo;
import com.neusoft.model.arsd.exception.SourceInfoException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {针对字典明细的增删改操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class RemoveSourceBaseFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RemoveSourceBaseFilter.class);

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		SourceInfo sourceInfo = context.getObject(key_instance, SourceInfo.class);
		if (StringUtils.hasLength(sourceInfo.getId())) { // update
			Long count = sourceInfoDao.queryForCount("select_remove_count", sourceInfo);
			if (count > 1) {
				throw new SourceInfoException("0000", "remove faile", null, null);
			}
			Integer row = sourceInfoDao.delete("remove_by_id", sourceInfo.getId());
			context.getResultMap().put("remove_by_id", row);
			if (logger.isDebugEnabled()) {
				logger.debug("remove source_info by Id [" + sourceInfo.getId() + "] and res = " + row + "");
			}
		}
		return false;
	}

	private String key_instance = "instance";

	public void setKey_instance(String key_instance) {
		this.key_instance = key_instance;
	}

}
