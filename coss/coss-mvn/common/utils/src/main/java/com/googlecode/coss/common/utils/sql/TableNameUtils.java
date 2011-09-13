package com.googlecode.coss.common.utils.sql;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * Operation for database table name
 * </p>
 * 
 * 
 */
public class TableNameUtils {

	/**
	 * <p>
	 * Convert database table name to java class style name
	 * <p>
	 * <p>
	 * etc.'account_info' to AccountInfo account to Account
	 * </p>
	 * 
	 * @param tableName
	 * @return
	 */
	public static String toClassName(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			throw new IllegalArgumentException("Table name must not be blank");
		}
		tableName = StringUtils.toLowerCase(tableName);// 'account_info' to
														// AccountInfo account
														// to Account
		char[] cs = tableName.toCharArray();
		int len = cs.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i == 0) {// the first letter should upper case
				sb.append(Character.toUpperCase(cs[i]));
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
