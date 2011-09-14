package com.neusoft.core.exception;

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
public class RegularMatchFailException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156777949317636906L;

	public RegularMatchFailException(String expCode, Exception latelyExp, String[] replaces) {
		super(key(expCode, RegularMatchFailException.class), latelyExp, replaces);
	}

}
