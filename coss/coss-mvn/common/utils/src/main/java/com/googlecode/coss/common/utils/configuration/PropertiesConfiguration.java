package com.googlecode.coss.common.utils.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.googlecode.coss.common.utils.collections.CollectionUtils;
import com.googlecode.coss.common.utils.io.FileUtils;
import com.googlecode.coss.common.utils.lang.ArrayUtils;
import com.googlecode.coss.common.utils.lang.BooleanUtils;
import com.googlecode.coss.common.utils.lang.NumberUtils;
import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.encrypt.Unicode;
import com.googlecode.coss.common.utils.text.DateFormatUtils;

/**
 * <p>
 * Configuration use properties file
 * </p>
 * 
 * 
 */
public class PropertiesConfiguration implements Configuration {

	private static final char subSetSplitChar = '.';

	private OrderedProperties properties = null;

	private String fileName;

	/**
	 * <p>
	 * Get property file name
	 * </p>
	 * 
	 * @return property file name
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * <p>
	 * Build PropertiesConfiguration from fileName
	 * </p>
	 * <p>
	 * the fileName can be absolute or relative
	 * </p>
	 * 
	 * @param fileName
	 */
	public PropertiesConfiguration(String fileName) throws IOException {
		properties = new OrderedProperties();
		File file = new File(fileName);
		if (file.isAbsolute() && file.exists()) {
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			properties.load(in);
		} else {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (in == null) {
				in = PropertiesConfiguration.class.getResourceAsStream(fileName);
			}
			if (in == null) {
				throw new FileNotFoundException(String.format("File '%s' not found", fileName));
			}
			properties.load(in);
		}
		this.fileName = fileName;
	}

	// private
	private PropertiesConfiguration(OrderedProperties properties) {
		this.properties = properties;
	}

	/**
	 * <p>
	 * Get sub Configuration
	 * </p>
	 * 
	 * @param prefix
	 * @return
	 */
	public Configuration subset(String prefix) {
		OrderedProperties subProperties = new OrderedProperties();
		String fullPrefix = prefix + subSetSplitChar;
		for (String key : this.keys()) {
			if (key.startsWith(fullPrefix)) {
				subProperties.setProperty(key.replace(fullPrefix, ""), this.getString(key));
			}
		}
		return new PropertiesConfiguration(subProperties);
	}

	/**
	 * <p>
	 * Check properties is empty
	 * </p>
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	/**
	 * <p>
	 * Check properties contains key
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	/**
	 * <p>
	 * Get all keys of sub Configuration
	 * </p>
	 * 
	 * @param prefix
	 * @return
	 */
	public String[] keys(String prefix) {
		return this.subset(prefix).keys();
	}

	/**
	 * <p>
	 * Get all keys
	 * </p>
	 * 
	 * @return
	 */
	public String[] keys() {
		return this.properties.keyArray();
	}

	/**
	 * <p>
	 * Get java.util.Properties
	 * </p>
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * <p>
	 * Get boolean value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return BooleanUtils.toBooleanStrict(value);
	}

	/**
	 * <p>
	 * Get boolean value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = properties.getProperty(key);
		return BooleanUtils.toBoolean(value, defaultValue);
	}

	/**
	 * <p>
	 * Get byte value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public byte getByte(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toByteStrict(value);
	}

	/**
	 * <p>
	 * Get byte value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public byte getByte(String key, byte defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toByte(value, defaultValue);
	}

	/**
	 * <p>
	 * Get double value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toDoubleStrict(value);
	}

	/**
	 * <p>
	 * Get double value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String key, double defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toDouble(value, defaultValue);
	}

	/**
	 * <p>
	 * Get float value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toFloatStrict(value);
	}

	/**
	 * <p>
	 * Get float value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String key, float defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toFloat(value, defaultValue);
	}

	/**
	 * <p>
	 * Get int value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toIntStrict(value);
	}

	/**
	 * <p>
	 * Get int value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toInt(value, defaultValue);
	}

	/**
	 * <p>
	 * Get long value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toLongStrict(value);
	}

	/**
	 * <p>
	 * Get long value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String key, long defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toLong(value, defaultValue);
	}

	/**
	 * <p>
	 * Get short value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public short getShort(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toShortStrict(value);
	}

	/**
	 * <p>
	 * Get short value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public short getShort(String key, short defaultValue) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toShort(value, defaultValue);
	}

	/**
	 * <p>
	 * Get BigDecimal value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toBigDecimalStrict(value);
	}

	/**
	 * <p>
	 * Get BigDecimal value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toBigDecimal(value, defaultValue);
	}

	/**
	 * <p>
	 * Get BigInteger value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public BigInteger getBigInteger(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return NumberUtils.toBigIntegerStrict(value);
	}

	/**
	 * <p>
	 * Get BigInteger value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		String value = properties.getProperty(key);
		return NumberUtils.toBigInteger(value, defaultValue);
	}

	/**
	 * <p>
	 * Get String value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return value;
	}

	/**
	 * <p>
	 * Get String value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * <p>
	 * Get String array value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		return StringUtils.split(value);
	}

	/**
	 * <p>
	 * Get String list value from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getStringList(String key) {
		return CollectionUtils.toStringList(getStringArray(key));
	}

	/**
	 * <p>
	 * Get String list value from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public List<String> getStringList(String key, List<String> defaultValue) {
		try {
			List<String> strList = getStringList(key);
			if (strList == null || strList.size() == 0) {
				return defaultValue;
			} else {
				return strList;
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>
	 * Get int array from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		String[] strs = StringUtils.split(value);
		return NumberUtils.toIntArray(strs);
	}

	/**
	 * <p>
	 * Get int list from properties
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<Integer> getIntList(String key) {
		return CollectionUtils.toIntList(getIntArray(key));
	}

	/**
	 * <p>
	 * Get int list from properties
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public List<Integer> getIntList(String key, List<Integer> defaultValue) {
		try {
			List<Integer> intList = getIntList(key);
			if (intList == null || intList.size() == 0) {
				return defaultValue;
			} else {
				return intList;
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>
	 * Get Date from properties
	 * </p>
	 * Full Date format: 2009-12-23 22:54:21 Short Date format: 2009-12-23
	 * 
	 * @param key
	 * @return
	 */
	public Date getDate(String key) {
		String value = properties.getProperty(key);
		validate(key, value);
		if (value.length() > 11) {
			try {
				return DateFormatUtils.parse(DateFormatUtils.getDefaultTimeFormat(), value);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			try {
				return DateFormatUtils.parse(DateFormatUtils.getDefaultDateFormat(), value);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

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
	public Date getDate(String key, Date defaultValue) {
		String value = properties.getProperty(key);
		validate(key, value);
		if (value.length() > 11) {
			try {
				return DateFormatUtils.parse(DateFormatUtils.getDefaultTimeFormat(), value);
			} catch (ParseException e) {
				return defaultValue;
			}
		} else {
			try {
				return DateFormatUtils.parse(DateFormatUtils.getDefaultDateFormat(), value);
			} catch (ParseException e) {
				return defaultValue;
			}
		}
	}

	/**
	 * <p>
	 * Show key Different between this and appointing configuration
	 * </p>
	 * 
	 * @param conf
	 *            another configuration
	 */
	public void showKeyDifferent(Configuration conf) {
		String[] keyArray1 = this.keys();
		String[] keyArray2 = conf.keys();
		System.out.println(this.fileName);
		for (String key : keyArray1) {
			if (ArrayUtils.indexOf(keyArray2, key) == -1) {
				System.out.println(key);
			}
		}
		System.out.println("\n" + conf.getFileName());
		for (String key : keyArray2) {
			if (ArrayUtils.indexOf(keyArray1, key) == -1) {
				System.out.println(key);
			}
		}
	}

	/**
	 * <p>
	 * Show key and value Different between this and appointing configuration
	 * </p>
	 * 
	 * @param conf
	 *            another configuration
	 */
	public void showDifferent(Configuration conf) {
		String[] keyArray1 = this.keys();
		String[] keyArray2 = conf.keys();
		System.out.println(this.fileName);
		for (String key : keyArray1) {
			if (ArrayUtils.indexOf(keyArray2, key) == -1) {
				System.out.println(key + " = " + this.getString(key));
			} else {
				if (!this.getString(key).equals(conf.getString(key))) {
					System.out.println(this.getFileName() + ": " + key + " = " + this.getString(key) + " "
							+ conf.getFileName() + ": " + key + " = " + conf.getString(key));
				}
			}
		}
		System.out.println("\n" + conf.getFileName());
		for (String key : keyArray2) {
			if (ArrayUtils.indexOf(keyArray1, key) == -1) {
				System.out.println(key + " = " + conf.getString(key));
			}
		}
	}

	/**
	 * <p>
	 * Check properties whether contains appointing value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsValue(String value) {
		return this.properties.containsValue(value);
	}

	public void native2Ascii(String path) {
		native2Ascii(path, false, "utf-8");
	}

	/**
	 * <p>
	 * Store original properties file to appointing path
	 * </p>
	 * 
	 * @param path
	 *            the path to store original file
	 */
	public void ascii2Native(String path) {
		native2Ascii(path, true, "utf-8");
	}

	// for future use
	protected void native2Ascii(String path, boolean reverse, String encode) {
		String[] keys = this.keys();
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key);
			sb.append(" = ");
			String value = this.getString(key);
			if (reverse) {

				sb.append(Unicode.decode(value));
			} else {
				sb.append(Unicode.encode(value));
			}
			sb.append("\r\n");
		}
		FileUtils.saveStringToFile(path, sb, "utf-8");
	}

	/**
	 * <p>
	 * Properties item number
	 * </p>
	 * 
	 * @return
	 */
	public int size() {
		return this.properties.size();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return this.properties.entrySet();
	}

	// private use
	private void validate(String key, String value) {
		if (value == null) {
			throw new java.lang.IllegalArgumentException(String.format("The property which key: '%s' is no found", key));
		}
	}

}
