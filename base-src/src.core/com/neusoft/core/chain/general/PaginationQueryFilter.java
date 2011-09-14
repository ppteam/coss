package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.List;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {通用的查询节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class PaginationQueryFilter extends AbstractQueryFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PaginationQueryFilter.class);

	private static final long serialVersionUID = -7602343997875348123L;

	public PaginationQueryFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Object _tempParam = verifyQueryParam(context);
		Integer amount = (Integer) sqlMapClientTemplate.queryForObject(statementName + "_count", _tempParam);
		if (logger.isDebugEnabled()) {
			logger.debug("query[" + statementName + "_count] value is [" + amount + "]");
		}
		List<?> dataList = sqlMapClientTemplate.queryForList(statementName, _tempParam);
		if (logger.isDebugEnabled()) {
			logger.debug("query[" + statementName + "] result Size is [" + dataList.size() + "]");
		}
		context.getResultMap().put(resultKey + "_count", amount);
		context.getResultMap().put(resultKey, dataList);
		return false;
	}

}
