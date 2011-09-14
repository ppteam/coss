package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.ICompare;
import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {匹配满足默认的条件终止责任链执行} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 14, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class SingleBreakChainFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SingleBreakChainFilter.class);

	private static final long serialVersionUID = -3585915912994282347L;

	public SingleBreakChainFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		if (compare == null) {
			logger.warn("please init List<ICompare> compares, so chain contiue");
		} else {
			return compare.compare(context);
		} // end_if
		return false;
	}

	private ICompare compare;

	public void setCompare(ICompare compare) {
		this.compare = compare;
	}

}
