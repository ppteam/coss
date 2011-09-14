package com.neusoft.core.chain.Impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Iface.IFilter;
import com.neusoft.core.common.Iface.IErrorHolder;
import com.neusoft.core.exception.BatisSqlException;

/**
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-9-30<br>
 * 
 * @author HXJ
 * @version $Revision$
 */
public abstract class FilterBase implements IFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FilterBase.class);

	private static final long serialVersionUID = 2117287827354929833L;

	public FilterBase() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.IFilter#postprocess(com.neusoft.core.chain
	 * .Iface.IContext, java.lang.Exception)
	 */
	public boolean postprocess(IContext context, Exception e) {
		cleanContext(context);
		customClean(context, e);
		return false;
	}// end_fun

	/**
	 * {用户子类重载}
	 * 
	 * @param context
	 * @param e
	 */
	protected void customClean(IContext context, Exception e) {
		if (logger.isInfoEnabled()) {
			logger.info("Node [" + this.getBeanName() + "] has error, postprocess exception");
		}
		String msg = errorHolderUtil.holderException(e);
		context.put(IContext.KEY_ERROR, true);
		context.put(IContext.KEY_MESSAGE, msg);
	}

	/**
	 * {清理上下文中存在的一些特殊变量}
	 * 
	 * @param context
	 */
	private Exception cleanContext(IContext context) {
		Exception _e = null;
		if (context.containsKey(IContext.KEY_TRANSACTIONSTATUS)) {
			try {
				transactionManager.rollback(getTransFromContext(context));
				context.remove(IContext.KEY_TRANSACTIONSTATUS);
				if (logger.isDebugEnabled()) {
					logger.debug("context has TransactionStatus rollback success");
				}
			} catch (TransactionException e) {
				logger.error("transactionManager.rollback(....) happen error");
				_e = new BatisSqlException("0203", e, new String[] { e.getMessage() });
			}
		}
		return _e;
	}

	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * {判断上下问中是否存在未完成的数据库事务，如果存在折返回句柄，否则返回为Null值}
	 * 
	 * @param context
	 *            IContext
	 * @return boolean
	 */
	protected TransactionStatus getTransFromContext(IContext context) {
		TransactionStatus tns = context.getObject(IContext.KEY_TRANSACTIONSTATUS,
				TransactionStatus.class);
		return tns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IFilter#getDefResultKey()
	 */
	public String getDefResultKey() {
		if (defResultKey == null) {
			defResultKey = this.getClass().getSimpleName() + RES_SUFFIX;
		}
		return defResultKey;
	}

	private String defResultKey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang
	 * .String)
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setErrorHolderUtil(IErrorHolder errorHolderUtil) {
		this.errorHolderUtil = errorHolderUtil;
	}

	protected IErrorHolder errorHolderUtil;

	private String beanName;

	public String getBeanName() {
		return beanName;
	}

}
