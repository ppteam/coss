package com.googlecode.coss.common.utils.sql;

/**
 * 数据访问异常
 */
@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {

	public DataAccessException(Throwable cause) {
		super(cause);
	}
}