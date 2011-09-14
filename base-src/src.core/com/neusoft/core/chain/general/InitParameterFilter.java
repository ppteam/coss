package com.neusoft.core.chain.general;

import java.util.Map;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {初始化默认的基础数据} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 14, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class InitParameterFilter extends FilterBase {

	private static final long serialVersionUID = -3585915843994282347L;

	public InitParameterFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		if (!CollectionUtils.isEmpty(paramMaps)) {
			context.putAll(paramMaps);
		}
		return false;
	}

	private Map<String, ?> paramMaps;

	public void setParamMaps(Map<String, ?> paramMaps) {
		this.paramMaps = paramMaps;
	}

}
