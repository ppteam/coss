package com.neusoft.core.chain.Iface;

import java.io.Serializable;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-9-27<br>
 * 
 * @class 责任链节点接口声明
 * @author HXJ
 * @version $Revision$
 */
public interface ICommand extends Serializable {

	/**
	 * 
	 * {过滤链执行方法，这也是整个责任连的核心方法}
	 * 
	 * @param context
	 * @return
	 * @throws BaseException
	 */
	boolean execute(IContext context) throws BaseException;

}
