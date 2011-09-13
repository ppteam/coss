package com.googlecode.coss.common.utils.text;

/**
 * <p>
 * Display class for ignore null
 * </p>
 * 
 * 
 */
public class Display {

	/**
	 * <p>
	 * If object is null return defaultString
	 * </p>
	 * 
	 * @param object
	 * @param defaultString
	 * @return
	 */
	public static String display(String object, String defaultString) {
		if (null == object) {
			return defaultString;
		} else {
			return object.toString();
		}
	}

	/**
	 * <p>
	 * If object is null return blank string
	 * </p>
	 * 
	 * @param object
	 * @return
	 */
	public static String display(String object) {
		return display(object, "");
	}
}
