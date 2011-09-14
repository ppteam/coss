package com.neusoft.core.chain.Impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

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
public abstract class ChainRuner {

	private ChainRuner() {
	}

	/**
	 * 
	 * @param filters
	 * @param context
	 * @throws BaseException
	 */
	public static void runner(List<IFilter> filters, IContext context) throws BaseException {
		IChain _chain = ChainBase.creatInstance();
		_chain.setFilters(filters);
		_chain.execute(context);
	}

	/**
	 * 
	 * @param filters
	 * @param context
	 * @throws BaseException
	 */
	public static void runner(ApplicationContext factory, String filterName, IContext context)
			throws BaseException {
		IChain _chain = ChainBase.creatInstance();
		_chain.setFilters(getFilters(factory, filterName));
		_chain.execute(context);
	}

	/**
	 * 
	 * @param context
	 * @param filterName
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public static List<IFilter> getFilters(ApplicationContext context, String filterName)
			throws BaseException {
		List<IFilter> res = null;
		if (context.containsBean(filterName)) {
			res = context.getBean(filterName, List.class);
		} else {
			throw new ChainBaseException("0000", null, new String[] { filterName });
		}
		return res;
	}

}
