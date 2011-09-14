package com.googlecode.coss.common.utils.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 * The main Configuration interface.
 * </p>
 * 
 */
public interface Configuration {
	/**
	 * <p>
	 * Get sub Configuration
	 * </p>
	 * 
	 * @param prefix
	 * @return
	 */
	public Configuration subset(String prefix);

	/**
	 * <p>
	 * Check properties is empty
	 * </p>
	 * 
	 * @return
	 */
	public boolean isEmpty();

	/**
	 * <p>
	 * Check properties contains key
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * <p>
	 * Get all keys of sub Configuration
	 * </p>
	 * 
	 * @param prefix
	 * @return
	 */
	public String[] keys(String prefix);

	/**
	 * <p>
	 * Get all keys
	 * </p>
	 * 
	 * @return
	 */
	public String[] keys();

	/**
	 * <p>
	 * Get java.util.Properties
	 * </p>
	 * 
	 * @return
	 */
	public Properties getProperties();

	/**
	 * <p>
	 * Get boolean value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key);

	/**
	 * <p>
	 * Get boolean value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	boolean getBoolean(String key, boolean defaultValue);

	/**
	 * <p>
	 * Get byte value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public byte getByte(String key);

	/**
	 * <p>
	 * Get byte value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public byte getByte(String key, byte defaultValue);

	/**
	 * <p>
	 * Get double value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key);

	/**
	 * <p>
	 * Get double value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String key, double defaultValue);

	/**
	 * <p>
	 * Get float value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public float getFloat(String key);

	/**
	 * <p>
	 * Get float value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String key, float defaultValue);

	/**
	 * <p>
	 * Get int value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key);

	/**
	 * <p>
	 * Get int value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue);

	/**
	 * <p>
	 * Get long value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key);

	/**
	 * <p>
	 * Get long value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String key, long defaultValue);

	/**
	 * <p>
	 * Get short value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public short getShort(String key);

	/**
	 * <p>
	 * Get short value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public short getShort(String key, short defaultValue);

	/**
	 * <p>
	 * Get BigDecimal value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key);

	/**
	 * <p>
	 * Get BigDecimal value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

	/**
	 * <p>
	 * Get BigInteger value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public BigInteger getBigInteger(String key);

	/**
	 * <p>
	 * Get BigInteger value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public BigInteger getBigInteger(String key, BigInteger defaultValue);

	/**
	 * <p>
	 * Get String value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * <p>
	 * Get String value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);

	/**
	 * <p>
	 * Get String array value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key);

	/**
	 * <p>
	 * Get String list value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getStringList(String key);

	/**
	 * <p>
	 * Get String list value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public List<String> getStringList(String key, List<String> defaultValue);

	/**
	 * <p>
	 * Get int array from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray(String key);

	/**
	 * <p>
	 * Get int list from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<Integer> getIntList(String key);

	/**
	 * <p>
	 * Get int list from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public List<Integer> getIntList(String key, List<Integer> defaultValue);

	/**
	 * <p>
	 * Get Date from properties
	 * </p>
	 * Full Date format: 2009-12-23 22:54:21 Short Date format: 2009-12-23
	 * 
	 * @param key
	 * @return
	 */
	public Date getDate(String key);

	/**
	 * <p>
	 * Get Date from properties
	 * </p>
	 * Full Date format: 2009-12-23 22:54:21 Short Date format: 2009-12-23
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Date getDate(String key, Date defaultValue);

	/**
	 * <p>
	 * Show key and value Different between this and appointing configuration
	 * </p>
	 * 
	 * @param conf
	 *            another configuration
	 */
	public void showDifferent(Configuration conf);

	/**
	 * <p>
	 * Show key Different between this and appointing configuration
	 * </p>
	 * 
	 * @param conf
	 *            another configuration
	 */
	public void showKeyDifferent(Configuration conf);

	/**
	 * <p>
	 * Get property file name
	 * </p>
	 * 
	 * @return property file name
	 */
	public String getFileName();

	/**
	 * <p>
	 * Check properties whether contains appointing value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsValue(String value);

	/**
	 * <p>
	 * Store original properties file to appointing path
	 * </p>
	 * 
	 * @param path
	 *            the path to store original file
	 */
	public void ascii2Native(String path);

	/**
	 * <p>
	 * Properties item number
	 * </p>
	 * 
	 * @return
	 */
	public int size();

	public Set<Entry<Object, Object>> entrySet();

}