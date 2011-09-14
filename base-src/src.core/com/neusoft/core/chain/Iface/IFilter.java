package com.neusoft.core.chain.Iface;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-9-27<br>
 * 
 * @author HXJ
 * @version $Revision$
 * @class 责任链节点接口
 */
public interface IFilter extends ICommand, BeanNameAware {

	/**
	 * 
	 * {当前节点执行出现异常之后的需要对结果进行处理的方法}
	 * 
	 * @param context
	 * @param exception
	 * @return
	 */
	boolean postprocess(IContext context, Exception exception);

	/**
	 * {获取当前节点在spring 配置文件中的id}
	 * 
	 * @return
	 */
	String getBeanName();

	/**
	 * {节点处理完毕之后，默认的存放与上下文结果的 key 名称}
	 * 
	 * @return
	 */
	String getDefResultKey();

	String RES_SUFFIX = "_RESULT_";
}
