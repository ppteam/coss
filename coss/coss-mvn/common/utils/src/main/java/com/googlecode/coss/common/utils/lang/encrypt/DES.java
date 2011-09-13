package com.googlecode.coss.common.utils.lang.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * DES encode and decode operation
 * </p>
 * 
 * 
 */
public class DES {

	// DES algorithm name
	private static final String Algorithm = "DES";

	/**
	 * DES decoder input string
	 * 
	 * @param key
	 * @param stext
	 * @return
	 */
	public static String decode(String key, String stext) {
		return new String(decode(key.getBytes(), Base64.decode(stext.getBytes())));
	}

	/**
	 * DES encoder input string return result using Base64 encrypt
	 * 
	 * @param key
	 * @param text
	 * @return
	 */
	public static String encode(String key, String text) {
		return new String(Base64.encode(encode(key.getBytes(), text.getBytes())));
	}

	/**
	 * DES decoder input bytes
	 * 
	 * @param key
	 * @param stext
	 * @return
	 */
	public static byte[] decode(byte[] key, byte[] stext) {
		SecretKey secretKey = new SecretKeySpec(key, Algorithm);
		try {
			Cipher decoder = Cipher.getInstance(Algorithm);
			decoder.init(Cipher.DECRYPT_MODE, secretKey);
			return decoder.doFinal(stext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * DES encoder input bytes
	 * 
	 * @param key
	 * @param text
	 * @return
	 */
	public static byte[] encode(byte[] key, byte[] text) {
		SecretKey secretKey = new SecretKeySpec(key, Algorithm);
		try {
			Cipher decoder = Cipher.getInstance(Algorithm);
			decoder.init(Cipher.ENCRYPT_MODE, secretKey);
			return decoder.doFinal(text);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
