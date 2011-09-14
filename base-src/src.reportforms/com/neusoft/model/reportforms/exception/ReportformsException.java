package com.neusoft.model.reportforms.exception;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {报表业务异常} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@neusoft.com
 * @version 1.0
 */
public class ReportformsException extends BaseException {

	private static final long serialVersionUID = 830239517861619036L;

	public ReportformsException(String expCode, String mgs, Exception latelyExp, String[] replaces) {
		super(key(expCode, ReportformsException.class), mgs, latelyExp, replaces);
	}

}
