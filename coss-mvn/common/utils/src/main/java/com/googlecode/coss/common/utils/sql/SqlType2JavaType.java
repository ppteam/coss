/*
 * @# SqlType2JavaType.java.java 9:57:01 PM Feb 7, 2010 2010
 * 
 */
package com.googlecode.coss.common.utils.sql;

import java.sql.Types;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * </p>
 */
public class SqlType2JavaType {

    public static String getJavaType(int sqlType) {
        if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR || sqlType == Types.CLOB
                || sqlType == Types.NCHAR || sqlType == Types.NVARCHAR
                || sqlType == Types.LONGNVARCHAR || sqlType == Types.LONGVARCHAR
                || sqlType == Types.CHAR) {// STRING
            return "String";
        } else if (sqlType == Types.INTEGER || sqlType == Types.BIT || sqlType == Types.TINYINT
                || sqlType == Types.BIGINT) {// INT
            return "int";
        } else if (sqlType == Types.DATE || sqlType == Types.TIME || sqlType == Types.TIMESTAMP
                || sqlType == Types.NUMERIC) {// DATE
            return "Date";
        } else if (sqlType == Types.VARBINARY || sqlType == Types.BINARY || sqlType == Types.BLOB) {
            return "byte[]";
        } else if (sqlType == Types.BOOLEAN) {
            return "boolean";
        } else if (sqlType == Types.FLOAT) {
            return "float";
        } else if (sqlType == Types.DOUBLE) {
            return "double";
        } else {
            return StringUtils.EMPTY;
        }
    }
}
