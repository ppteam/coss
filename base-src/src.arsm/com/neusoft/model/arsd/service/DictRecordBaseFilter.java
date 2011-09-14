package com.neusoft.model.arsd.service;

import org.apache.log4j.Logger;

import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.model.arsd.dao.pojo.DictRecord;

/**
 * 
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
public class DictRecordBaseFilter extends AbstractAdapterFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DictRecordBaseFilter.class);

	private static final long serialVersionUID = -1661357785545171667L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		DictRecord instance = context.getObject("instance", DictRecord.class);
		if (StringUtils.hasLength(instance.getId())) { // update
			int rows = recordDao.updateById(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("update catalog and res [" + rows + "]");
			}
			context.putResults(getDefResultKey(), rows);
		} else { // insert
			instance.setCatalogId(CommonUtils.getUUIDSeq());
			String catalogId = recordDao.insert(instance);
			if (logger.isDebugEnabled()) {
				logger.debug("insert catalog and res [" + catalogId + "]");
			}
			context.putResults(getDefResultKey(), catalogId);
		}
		return false;
	}

}
