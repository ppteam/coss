package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.general.SqlMapClientSupportFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.BatisSqlException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {批量执行具有相同入参的sql语句 支持 update、delete} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 16, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class BatchExecuteStatementFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BatchExecuteStatementFilter.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -6242264668630634987L;

	private String paramKey;

	// sql语句集合
	private List<String> statements;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.notEmpty(statements, "please init sql statements for this Filter");
		Object param = null;
		if (paramKey == null) {
			param = null;
		} else {
			param = "ctxself".equals(paramKey) ? context : context.get(paramKey);
		}
		int[] res = new int[statements.size()];
		int index = 0;
		for (String sql : statements) {
			try {
				res[index] = sqlMapClientTemplate.update(sql, param);
				index++;
				if (logger.isDebugEnabled()) {
					logger.debug("update(" + sql + "," + param + ")  res is [" + res + "]");
				}
			} catch (DataAccessException e) {
				logger.error("update(" + sql + "," + param + ") has err", e);
				throw new BatisSqlException("0005", e, new String[] { sql });
			}
		} // end_for
		context.put("update_row", res);
		return false;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public void setStatements(List<String> statements) {
		this.statements = statements;
	}

} // end_clazz
