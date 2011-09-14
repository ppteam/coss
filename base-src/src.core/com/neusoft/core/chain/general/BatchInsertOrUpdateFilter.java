package com.neusoft.core.chain.general;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {批量执行 相同SQL 的 方法} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 16, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class BatchInsertOrUpdateFilter extends SqlMapClientSupportFilter {

	private static final Logger logger = Logger.getLogger(BatchInsertOrUpdateFilter.class);

	private static final long serialVersionUID = -6112264668630634987L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.hasLength(statement, "please init sql statements for this Filter");
		List<?> list = (List<?>) context.get(listKey);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object param : list) {
				sqlMapClientTemplate.update(statement, param);
			}
			if (logger.isInfoEnabled()) {
				logger.info("list size is [" + list.size() + "] has bean handle by DB");
			}
		} else {
			logger.warn("not any objectList for handle,please init ObjectList");
		}
		return false;
	}

	private String statement;

	private String listKey;

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

} // end_clazz
