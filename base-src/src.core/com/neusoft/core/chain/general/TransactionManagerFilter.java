package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.IllegalityParamException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {事务节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class TransactionManagerFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TransactionManagerFilter.class);

	private static final long serialVersionUID = -7602343997875348529L;

	public TransactionManagerFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		TransactionStatus status = null;
		switch (statusAction) {
		case TRANS_LAUNCCH:
			if (context.get(IContext.KEY_TRANSACTIONSTATUS) == null) {
				status = getTransactionManager().getTransaction(transactionDefLocal.get());
				context.put(IContext.KEY_TRANSACTIONSTATUS, status);
				if (logger.isDebugEnabled()) {
					logger.debug("init new TransactionStatus for this context");
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("TransactionStatus allways in context");
				}
			}
			break;
		case TRANS_COMMINT:
			getTransactionManager().commit(getTransFromContext(context));
			context.remove(IContext.KEY_TRANSACTIONSTATUS);
			break;
		default:
			throw new IllegalityParamException("0000", new String[] { String.valueOf(statusAction),
					"0,1" });
		}

		return false;
	}

	private int statusAction;

	public void setStatusAction(int statusAction) {
		this.statusAction = statusAction;
	}

	public final int TRANS_LAUNCCH = 0;
	public final int TRANS_COMMINT = 1;

	private static ThreadLocal<TransactionDefinition> transactionDefLocal = new ThreadLocal<TransactionDefinition>() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected TransactionDefinition initialValue() {
			return new DefaultTransactionDefinition();
		}

	};

}
