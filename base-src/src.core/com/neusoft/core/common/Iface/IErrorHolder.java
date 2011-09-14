package com.neusoft.core.common.Iface;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 16, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IErrorHolder {

	/**
	 * {处理制定的异常}
	 * 
	 * @param e
	 * @return
	 */
	String holderException(Exception e);

}
