package com.googlecode.coss.common.core.exception;

/**
 * 自定义改进的Exception对象 覆写了 fillInStackTrace方法 1. 不填充stack 2. 取消同步
 */
public abstract class BaseCheckedExecption extends Exception implements SysExecption {

    protected String errorCode;
    protected String errorMessage;

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public BaseCheckedExecption() {
        super();
    }

    public BaseCheckedExecption(String message) {
        super(message);
    }

    public BaseCheckedExecption(Throwable cause) {
        super(cause);
    }

    public BaseCheckedExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
