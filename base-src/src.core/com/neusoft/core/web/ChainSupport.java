package com.neusoft.core.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.ChainRuner;
import com.neusoft.core.chain.Impl.ContextBase;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class ChainSupport implements ApplicationContextAware {

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
	protected void executeChain(IContext context, String filterName) {
		try {
			Assert.hasLength(filterName, "filterName not empty");
			ChainRuner.runner(applicationContext, filterName, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ApplicationContext applicationContext;

}
