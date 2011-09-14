package com.neusoft.core.chain.general;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.BatisSqlException;
import com.neusoft.core.exception.IllegalityParamException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {数据库批处理节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 20, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ExecuteBatchFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExecuteBatchFilter.class);

	private static final long serialVersionUID = 7939808798578580682L;

	public ExecuteBatchFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.notNull(status, "ExecuteBatch cann,t be null");
		try {
			switch (status) {
			case BATCH_LAUNCCH:
				sqlMapClientTemplate.getSqlMapClient().startBatch();
				if (logger.isDebugEnabled()) {
					logger.debug("ExecuteBatch Action:BATCH_LAUNCCH is success");
				}
				break;
			case BATCH_COMMINT:
				sqlMapClientTemplate.getSqlMapClient().executeBatch();
				if (logger.isDebugEnabled()) {
					logger.debug("ExecuteBatch Action:BATCH_COMMINT is success");
				}
				break;
			default:
				throw new IllegalityParamException("0000", new String[] { String.valueOf(status), "0,1" });
			}
		} catch (SQLException e) {
			throw new BatisSqlException("0200", e, new String[] { "ExecuteBatch(...)", e.getMessage() });
		}
		return false;
	}

	private Integer status;

	public void setStatus(Integer status) {
		this.status = status;
	}

	public final int BATCH_LAUNCCH = 0;
	public final int BATCH_COMMINT = 1;

}
