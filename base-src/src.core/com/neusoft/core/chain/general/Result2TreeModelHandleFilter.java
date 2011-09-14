package com.neusoft.core.chain.general;

import java.util.List;

import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.TreeHandleUtil;
import com.neusoft.core.web.XmlBeanModel;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {list 转换为 Tree 模型} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class Result2TreeModelHandleFilter extends FilterBase {

	private static final long serialVersionUID = -7602343997875348456L;

	public Result2TreeModelHandleFilter() {
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
		@SuppressWarnings("unchecked")
		List<XmlBeanModel> nodeList = (List<XmlBeanModel>) context.getValueByAll(paramKey);
		XmlBeanModel root = TreeHandleUtil.assembling(nodeList, true);
		context.getResultMap().put(paramKey, root);
		return false;
	}

	private String paramKey;

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
