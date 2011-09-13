package com.googlecode.coss.common.core.exception;

import java.util.Locale;

import com.googlecode.coss.common.core.context.MessageSourceHolder;

/**
 * 自定义改进的Exception对象 覆写了 fillInStackTrace方法 1. 不填充stack 2. 取消同步
 */
public abstract class BaseRuntimeExecption extends RuntimeException implements SysExecption {

	protected String errorCode;
	protected String errorMessage;

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public String getMessage() {
		return errorMessage;
	}

	public String getMessageI18N() {
		MessageSourceHolder.getMessageSource().getMessage(getErrorCode(),
				new Object[] { getErrorCode(), getMessage() }, Locale.CHINA);
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public BaseRuntimeExecption() {
		super();
		//setErrorMessage(super.getMessage());
	}

	public BaseRuntimeExecption(String message) {
		super(message);
	}

	public BaseRuntimeExecption(Throwable cause) {
		super(cause);
	}

	public BaseRuntimeExecption(String message, Throwable cause) {
		super(message, cause);
	}

}