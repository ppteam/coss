package com.googlecode.coss.common.utils.lang;

/**
 * <p>
 * Boolean Operation
 * </p>
 * 
 */
public class BooleanUtils {

	/** 0 means false **/
	public static final int NUM_FALSE = 0;
	/** 1 means true **/
	public static final int NUM_TRUE = 1;
	/** "false" **/
	public static final String STR_FALSE = "false";
	/** "true" **/
	public static final String STR_TRUE = "true";

	/**
	 * <p>
	 * Convert a number to boolean
	 * </p>
	 * 
	 * @param num
	 *            the number to convert
	 * @return <code>false</code> if number is 0, <code>true</code>for otherwise
	 */
	public static boolean toBoolean(int num) {
		if (num == 0) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Convert a String to boolean
	 * </p>
	 * 
	 * @param str
	 *            the string to convert
	 * @param defaultValue
	 * @return <code>false</code> if input string is "true" or "TRUE", return
	 *         <code>false</code> if input string is "false" or "FALSE", else
	 *         return default Value
	 */
	public static boolean toBoolean(String str, boolean defaultValue) {
		if (STR_FALSE.equalsIgnoreCase(str)) {
			return false;
		} else if (STR_TRUE.equalsIgnoreCase(str)) {
			return true;
		} else {
			return defaultValue;
		}
	}

	/**
	 * <p>
	 * Convert a String to boolean
	 * </p>
	 * 
	 * @param str
	 *            the string to convert
	 * @return <code>false</code> if input string is "true" or "TRUE", return
	 *         <code>false</code>for otherwise.
	 */
	public static boolean toBoolean(String str) {
		return toBoolean(str, false);
	}

	/**
	 * <p>
	 * Convert a String to boolean, if input string is not "true" ignore style,
	 * throw Exception instead of return a value
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean toBooleanStrict(String str) {
		if (STR_FALSE.equalsIgnoreCase(str)) {
			return false;
		} else if (STR_TRUE.equalsIgnoreCase(str)) {
			return true;
		} else {
			throw new IllegalArgumentException(String.format("String '%s' can not parse to a boolean object", str));
		}
	}
}
