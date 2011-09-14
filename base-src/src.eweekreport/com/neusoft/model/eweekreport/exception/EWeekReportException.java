package com.neusoft.model.eweekreport.exception;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 19, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class EWeekReportException extends BaseException {

	private static final long serialVersionUID = -2697749106270393557L;

	public EWeekReportException(String expCode, int expType, String mgs, Exception latelyExp, String[] replaces) {
		super(key(expCode, EWeekReportException.class), expType, mgs, latelyExp, replaces);
	}
}
