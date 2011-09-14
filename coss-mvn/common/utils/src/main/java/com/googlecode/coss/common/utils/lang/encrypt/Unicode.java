/**
 * 
 */
package com.googlecode.coss.common.utils.lang.encrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode encrypt
 * 
 * 
 */
public class Unicode {

	/**
	 * <p>
	 * Encode input string, return the Unicode style
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 127 || c < 0) {
				sb.append("\\u");
				String hexStr = Integer.toHexString(str.charAt(i)).toUpperCase();
				for (int j = hexStr.length(); j < 4; j++) {
					hexStr = "0" + hexStr;
				}
				sb.append(hexStr);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * Decode input Unicode style String, return the original string
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
}
