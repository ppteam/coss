/**
 * 
 */
package com.googlecode.coss.common.utils.net;

import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.builder.ToStringBuilder;

/**
 * Key value pair for net package
 * 
 * 
 */
public class KeyValuePair {

	private String key;

	private String value;

	public KeyValuePair() {
	}

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * <p>
	 * Generator KeyValuePair for input string
	 * </p>
	 * etc. "a=1" key: a value: 1
	 * 
	 * @param str
	 * @return
	 */
	public static KeyValuePair formString(String str) {

		if (str != null) {
			int pos = str.indexOf('?');
			if (pos != -1) {
				str = StringUtils.subString(str, pos + 1, str.length());
			}
		} else {
			throw new IllegalArgumentException(String.format("Input String Illegal"));
		}
		String[] keyvalues = StringUtils.split(str, '=', false);
		if (keyvalues == null) {
			return null;
		} else if (keyvalues.length == 1) {
			return new KeyValuePair(keyvalues[0], StringUtils.EMPTY);
		} else {
			return new KeyValuePair(keyvalues[0].trim(), keyvalues[1]);
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
