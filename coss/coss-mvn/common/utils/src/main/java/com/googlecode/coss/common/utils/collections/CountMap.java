/**
 * 
 */
package com.googlecode.coss.common.utils.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple Map for counting, it is very useful for counting words etc.
 * put("love"); put("love"); put("you"); get("love") = 2; get("you") = 1;
 * 
 * Note: this class is not thread safe
 * 
 * 
 */
public class CountMap<K> {

	private Map<Object, Integer> values = new HashMap<Object, Integer>();

	/**
	 * <p>
	 * Put new/old key to Map, return original count for this key
	 * </p>
	 * <p>
	 * If putting new key, then return 0
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int put(K key) {
		if (values.containsKey(key)) {
			return values.put(key, values.get(key) + 1);
		} else {
			return values.put(key, 1) == null ? 0 : values.put(key, 1);
		}
	}

	/**
	 * <p>
	 * Get count of appointing key
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int get(K key) {
		if (values.containsKey(key)) {
			return values.get(key);
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * Return packing Map<Object, Integer>
	 * </p>
	 * 
	 * @return
	 */
	public Map<Object, Integer> toMap() {
		return this.values;
	}

	public String toString() {
		return this.values.toString();
	}
}
