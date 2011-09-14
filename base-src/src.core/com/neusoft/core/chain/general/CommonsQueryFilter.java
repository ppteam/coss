package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.springsource.util.common.CollectionUtils;

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
public class CommonsQueryFilter extends AbstractQueryFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonsQueryFilter.class);

	private static final long serialVersionUID = -7602343997875348123L;

	public CommonsQueryFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.notNull(statementName, "[p:statementName] is not null");
		if (logger.isDebugEnabled()) {
			logger.debug("--->[statementName:" + statementName + "]");
		}
		Object _tempParam = verifyQueryParam(context);
		if (single) {
			Object queryObj = sqlMapClientTemplate.queryForObject(statementName, _tempParam);
			if (queryObj != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("query[" + statementName + "] result is [" + queryObj.toString() + "]");
				}
				context.putResults(resultKey, queryObj);
			}
		} else {
			List<?> list = sqlMapClientTemplate.queryForList(statementName, _tempParam);
			if (!CollectionUtils.isEmpty(list)) {
				if (logger.isDebugEnabled()) {
					logger.debug("query[" + statementName + "] result Size is [" + list.size() + "]");
				}
				context.putResults(resultKey, list);
			}
		}
		return false;
	}

	// 返回结果是否是单值
	private boolean single = false;

	public void setSingle(boolean single) {
		this.single = single;
	}

}
