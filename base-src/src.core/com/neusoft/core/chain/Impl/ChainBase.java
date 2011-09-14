package com.neusoft.core.chain.Impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang.Validate;

import com.neusoft.core.chain.Iface.IChain;
import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Iface.IFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.ChainBaseException;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-9-27<br>
 * 
 * @class 责任链定义Class 描述整个责任链的结构 以及执行规则
 * @author HXJ
 * @version $Revision$
 */
public class ChainBase implements IChain {

	private static final Logger logger = Logger.getLogger(ChainBase.class);

	private static final long serialVersionUID = -5993207404204481362L;

	protected List<IFilter> filters = new ArrayList<IFilter>(10);

	public ChainBase() {
	}

	public ChainBase(List<IFilter> filters) {
		this.filters.addAll(filters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Validate.notNull(context, "[p:context] is not null");
		ArrayStack _arrayStack = initRunStack();
		boolean _result = false;
		Exception _exception = null;
		IFilter _filter = null;
		// 当栈不为空的时候执行如下的操作
		while (!_arrayStack.empty()) {
			try {
				_filter = (IFilter) _arrayStack.pop();
				if (logger.isDebugEnabled()) {
					logger.debug("[filter][" + _filter.getBeanName() + "] is run start");
				}
				_result = _filter.execute(context);
				if (logger.isDebugEnabled()) {
					logger.debug("[filter][" + _filter.getBeanName() + "] is run end");
				}
				if (_result) {
					break;
				}
			} catch (Exception e) {
				context.put(IContext.KEY_ERROR, e);
				logger.error("run filter [" + _filter.getBeanName() + "] has err." + e.getMessage());
				_exception = e;
				break;
			}
		} // end_while

		// 判断是否存在尚未完成的事务
		if (_exception == null && context.containsKey(IContext.KEY_TRANSACTIONSTATUS)) {
			logger.error("Context exist  transactionstatus ,so throw ChainBaseException[0002]");
			_exception = new ChainBaseException("0002", null, null);
		}
		// 责任链发生异常
		if (_exception != null) {
			_filter.postprocess(context, _exception);
		}
		return _result;
	} // end_fun

	/**
	 * {初始化运行时的栈}
	 * 
	 * @return
	 */
	private ArrayStack initRunStack() {
		ArrayStack _arrayStack = new ArrayStack();
		for (int i = filters.size(); i > 0; i--) {
			_arrayStack.push(filters.get(i - 1));
		}
		return _arrayStack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.IChain#addCommand(com.neusoft.core.chain
	 * .Iface.IFilter)
	 */
	public void addCommand(IFilter filter) throws BaseException {
		filters.add(0, filter);
	}

	public void setFilters(List<IFilter> filters) {
		this.filters = filters;
	}

	/**
	 * {工厂类，创建自身实例}
	 * 
	 * @return
	 */
	public static IChain creatInstance() {
		return new ChainBase();
	}
}
