package com.neusoft.model.report.exception;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {操作个人日报的业务异常} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
public class DailyReportException extends BaseException {

	private static final long serialVersionUID = -3280292449615972317L;

	public DailyReportException(String expCode,int expType, String mgs, Exception latelyExp,
			String[] replaces) {
		super(key(expCode, DailyReportException.class),expType, mgs, latelyExp, replaces);
	}

}
