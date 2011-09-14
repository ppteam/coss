package com.neusoft.core.exception;

/**
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {操作Cache发生异常} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 26, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class CacheAccessException extends BaseException {

	private static final long serialVersionUID = -2498182193914858126L;

	public CacheAccessException(String expCode, int expType, String mgs, Exception latelyExp,
			String[] replaces) {
		super(key(expCode, CacheAccessException.class), expType, mgs, latelyExp, replaces);
	}

}
