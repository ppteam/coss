package com.neusoft.core.chain.Iface;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {比较接口} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>May 3, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface ICompare {

	/**
	 * {比较方法执行}
	 * 
	 * @param context
	 *            上下文
	 * @throws BaseException
	 * @return
	 */
	boolean compare(IContext context) throws BaseException;
}
