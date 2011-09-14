package com.neusoft.core.web;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractAjaxAction extends ChainSupport {

	/**
	 * {处理返回的结果，封装结果到ajax 返回建模对象中}
	 * 
	 * @param context
	 * @return
	 */
	protected CallBackModel fillByContext(IContext context) {
		CallBackModel backModel = CallBackModel.getInstance();
		if (context.hasError()) {
			backModel.setError(true);
			backModel.setMsg(context.getMessage());
		} else {
			backModel.setCallData(context.getResultMap());
		}
		return backModel;
	}

	/**
	 * {复制登录用户信息}
	 * 
	 * @param request
	 * @param context
	 * @throws BaseException
	 */
	protected void initContext(IContext context) throws BaseException {
		LoginUser loginUser = getLoginUser();
		context.putAll(loginUser);
	}

	/**
	 * {获取当前登录用户信息}
	 * 
	 * @return
	 * @throws BaseException
	 */
	protected LoginUser getLoginUser() throws BaseException {
		WebContext ctx = WebContextFactory.get();
		return CommonUtils.getLoginUser(ctx.getHttpServletRequest());
	}
}
