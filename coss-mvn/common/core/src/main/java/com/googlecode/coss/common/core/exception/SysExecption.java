package com.googlecode.coss.common.core.exception;

/**
 * @function 系统异常处理接口
 */
public interface SysExecption {

    /**
     * @function: 获得异常消息
     * @return
     */
    public String getMessage();

    /**
     * @function 获取异常错误码
     * @return
     */
    public String getErrorCode();

    /**
     * @function: 设置异常消息
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage);

    /**
     * @function: 设置异常错误码
     * @param ErrorCode
     */
    public void setErrorCode(String sysErrorCode);
} // end_clazz
