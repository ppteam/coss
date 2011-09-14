package com.neusoft.core.exception;

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
public class NotSupportException extends BaseException {

	private static final long serialVersionUID = 2350405101945956812L;

	public NotSupportException(String mgs, String[] replaces) {
		super(key("0000", NotSupportException.class), EXP_TYPE_ERROR, mgs, null, replaces);
	}

}
