package com.googlecode.coss.common.core.exception;

/**
 * 异常处理工具类
 */
public class ExceptionHandler extends BaseRuntimeExecption implements SysExecption {
	private static final long serialVersionUID = 7037212871019778645L;

	public ExceptionHandler() {
		this.errorCode = "00000000F";
		this.errorMessage = "Not yet implement";
	}

	public ExceptionHandler(String errorMessage) {
		this.errorCode = "00000000F";
		this.errorMessage = errorMessage;
	}

	private static void handleUnexpectedException(Throwable ex) {
		throw new IllegalStateException("Unexpected exception thrown", ex);
	}

	/**
	 * 作为Runtime异常再抛出
	 * */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException)
			throw (RuntimeException) ex;
		if (ex instanceof Error) {
			throw (Error) ex;
		} else {
			handleUnexpectedException(ex);
			return;
		}
	}
}