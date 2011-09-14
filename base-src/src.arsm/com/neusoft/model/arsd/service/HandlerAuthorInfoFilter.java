package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.arsd.dao.pojo.AuthorityInfo;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {执行 增删改 操作} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandlerAuthorInfoFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HandlerAuthorInfoFilter.class);

	private static final long serialVersionUID = -1661357785545171098L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		AuthorityInfo instance = context.getObject("instance", AuthorityInfo.class);
		if (StringUtils.hasLength(instance.getId())) { // update
			int rows = authorityDao.updateById(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update author and res [" + rows + "]");
			}
			context.putResults(getDefResultKey(), rows);
		} else { // insert
			instance.setAuthorId(CommonUtils.getUUIDSeq());
			String id = authorityDao.insert(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("insert author and res [" + id + "]");
			}
			context.putResults(getDefResultKey(), id);
		}
		return false;
	}

}
