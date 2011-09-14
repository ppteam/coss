/**
 * 
 */
package com.googlecode.coss.common.utils.lang;

/**
 * <p>
 * Char operation
 * </p>
 * 
 * 
 */
public class CharUtils {

	/**
	 * <p>
	 * Check whether char array contains input char
	 * </p>
	 * 
	 * @param c
	 * @param cs
	 * @return
	 */
	public static boolean inArray(char c, char... cs) {
		if (cs == null) {
			return false;
		}
		for (char ch : cs) {
			if (ch == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Check whether char array not contains input char
	 * </p>
	 * 
	 * @param c
	 * @param cs
	 * @return
	 */
	public static boolean notInArray(char c, char... cs) {
		return !CharUtils.inArray(c, cs);
	}

	public static char toChar(String str) {
		if (str.length() < 2) {
			return str.charAt(0);
		} else {
			throw new IllegalArgumentException(String.format("String %s can not convert to a Character", str));
		}
	}

}
