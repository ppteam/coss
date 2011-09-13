package com.googlecode.coss.common.utils.sql;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * Operation of database table column
 * </p>
 * 
 */
public class ColumnNameUtils {

	/**
	 * <p>
	 * Convert database table column name to java field style name
	 * </p>
	 * <p>
	 * etc.Account to account Account_info to accountInfo and ACCOUNT_INFO to
	 * accountInfo
	 * </p>
	 * 
	 * @param columnName
	 * @return
	 */
	public static String toFieldName(String columnName) {
		if (StringUtils.isBlank(columnName)) {
			throw new IllegalArgumentException("Column name must not be blank");
		}
		if (StringUtils.isUpperCase(columnName)) {// ACCOUNT_INFO
			columnName = StringUtils.toLowerCase(columnName);// Account to
																// account
																// Account_info
																// to
																// accountInfo
																// ACCOUNT_INFO
																// to
																// accountInfo
		} else {
			columnName = StringUtils.firstLetterUpper(columnName);
		}
		char[] cs = columnName.toCharArray();
		int len = cs.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i == 0) {// the first letter should lower case
				sb.append(Character.toLowerCase(cs[i]));
			} else if (cs[i] == '_') {

			} else {
				if (cs[i - 1] == '_') {
					sb.append(Character.toUpperCase(cs[i]));
				} else {
					sb.append(cs[i]);
				}
			}
		}
		return sb.toString();
	}
}
