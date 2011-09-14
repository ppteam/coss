package com.neusoft.core.exception;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 14, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ChainBaseException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 192192842085121298L;

	public ChainBaseException(String expCode, Exception latelyExp, String[] replaces) {
		super(key(expCode, ChainBaseException.class), latelyExp, replaces);
	}

}
