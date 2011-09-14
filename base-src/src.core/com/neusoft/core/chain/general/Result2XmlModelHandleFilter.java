package com.neusoft.core.chain.general;

import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.common.Iface.IConstant;
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
public class Result2XmlModelHandleFilter extends FilterBase {

	private static final long serialVersionUID = -7602343997875348456L;

	public Result2XmlModelHandleFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		Assert.hasLength(paramKey, "[p:paramKey] is not null!");
		ModelMap modelMap = context.getObject(IContext.KEY_MODELMAP, ModelMap.class);
		if (modelMap == null) {
			throw new BaseException("0002", BaseException.EXP_TYPE_ERROR, "",
					new NullPointerException(), new String[] { ModelMap.class.toString() });
		} // end_if
		modelMap.addAttribute(IConstant.KEY_BEAN2XML, context.getResultMap().get(paramKey));
		return false;
	}

	private String paramKey;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
