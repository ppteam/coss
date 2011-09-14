package com.neusoft.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.ChainRuner;
import com.neusoft.core.chain.Impl.ContextBase;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {抽象的报表控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractMultiActionController extends MultiActionController {

	public AbstractMultiActionController() {
	}

	/**
	 * {创建责任链运行上下文实例}
	 * 
	 * @return
	 */
	protected IContext creatContext() {
		return new ContextBase();
	}

	/**
	 * {工厂方式获取chian 实例}
	 * 
	 * @return IChain
	 */
	protected void executeChain(IContext context, String filterName, HttpServletRequest request) {
		try {
			Assert.hasLength(filterName, "filterName not empty");
			ChainRuner.runner(RequestContextUtils.getWebApplicationContext(request), filterName, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {错误访问提示}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView errorRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * {创建返回MV}
	 * 
	 * @param viewName
	 * @return
	 */
	protected ModelAndView creatModelAndView(String viewName, IContext context) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("IContext", context);
		return modelAndView;
	}

}
