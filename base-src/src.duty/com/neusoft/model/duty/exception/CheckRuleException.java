package com.neusoft.model.duty.exception;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {操作数据字典的业务异常} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CheckRuleException extends BaseException {

	private static final long serialVersionUID = 7350125159531310986L;

	public CheckRuleException(String expCode, String mgs, Exception latelyExp, String[] replaces) {
		super(key(expCode, CheckRuleException.class), EXP_TYPE_EXCPT, mgs, latelyExp, replaces);
	}

}
