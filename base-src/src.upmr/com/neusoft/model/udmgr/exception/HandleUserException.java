package com.neusoft.model.udmgr.exception;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {增、删、改 UserBase 异常} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 11, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleUserException extends BaseException {

	private static final long serialVersionUID = -428120331209529592L;

	public HandleUserException(String expCode, int expType, String mgs, Exception latelyExp,
			String[] replaces) {
		super(key(expCode, HandleUserException.class), expType, mgs, latelyExp, replaces);
	}

}
