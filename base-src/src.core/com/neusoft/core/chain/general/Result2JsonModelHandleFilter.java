package com.neusoft.core.chain.general;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;

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
public class Result2JsonModelHandleFilter extends FilterBase {

	private static final long serialVersionUID = -7602343997875348456L;

	public Result2JsonModelHandleFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Integer total = context.getValueByAll(paramKey + "_count") == null ? 0 : (Integer) context
				.getValueByAll(paramKey + "_count");
		context.getJsonModel().putTotal(total);
		context.getJsonModel().putData(context.getValueByAll(paramKey));
		if (addition != null) {
			context.getJsonModel().putAddition(context.getValueByAll(addition));
		}
		return false;
	}

	private String paramKey;

	private String addition;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

}
