/**
 * 
 */
package com.googlecode.coss.common.utils.lang.builder;

import java.util.List;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * For return stander JSON string
 * <p/>
 * Key can not be null
 * <p/>
 * public static void main(String[] args){ testNoNameEmpty();
 * testWithNameEmpty(); ToJSONStringBuilder builder = new ToJSONStringBuilder();
 * builder.append("nickName", null); builder.append("icon",
 * "http://www.unclepeng.com/a.jpg"); builder.append(null, "fcde");
 * builder.append("state", 3); builder.append(null, 6.0); ToJSONStringBuilder
 * builder2 = new ToJSONStringBuilder("res"); String[] strs = new
 * String[]{"12","35","42"}; builder2.appendArray("tt3", strs, false);
 * builder2.append("a",1); builder2.append("a2","test");
 * builder2.append("a3",null); builder.append(builder2);
 * System.out.println(builder2); System.out.println(builder); }
 * 
 * public static void testNoNameEmpty(){ ToJSONStringBuilder builder = new
 * ToJSONStringBuilder(); System.out.println(builder.toString()); }
 * 
 * public static void testWithNameEmpty(){ ToJSONStringBuilder builder = new
 * ToJSONStringBuilder("test"); System.out.println(builder.toString()); }
 * 
 * result:
 * 
 * null "test":null "res":{"tt3":[12,35,42],"a":1,"a2":"test","a3":null}}
 * {"nickName"
 * :null,"icon":"http://www.unclepeng.com/a.jpg","fcde","state":3,6.0,
 * "res":{"tt3":[12,35,42],"a":1,"a2":"test","a3":null}}
 * 
 * 
 * 
 */
public class ToJSONStringBuilder {

	private static final String PREFIX = "{";

	private static final String SUFFIX = "}";

	private static final String ARRAY_PREFIX = "[";

	private static final String ARRAY_SUFFIX = "]";

	private static final String SPLIT_FIELD_FIELD = ",";

	private static final String SPLIT_NAME_VALUE = ":";

	private static final String DOT = "\"";

	private static final String NULL = "null";

	private StringBuilder sb;

	// field count
	private int fieldNum = 0;

	/**
	 * etc. "name":{"key1":"value1","intkey1",3}
	 * <p/>
	 * for null return "name":null
	 * 
	 * @param name
	 */
	public ToJSONStringBuilder(String name) {
		sb = new StringBuilder();
		// with name --> "key":
		if (name != null) {
			sb.append(DOT);
			sb.append(name);
			sb.append(DOT);
			sb.append(SPLIT_NAME_VALUE);
		}
	}

	/**
	 * etc. {"key1":"value1","intkey1",3}
	 * <p/>
	 * for null return null
	 */
	public ToJSONStringBuilder() {
		this(null);
	}

	/**
	 * Append fieldName & String value
	 * 
	 * @param fieldName
	 *            key
	 * @param value
	 *            value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, String value) {
		return append(fieldName, value, true);
	}

	private void addPrefix() {
		// add at start{
		if (fieldNum == 0) {
			sb.append(PREFIX);
		}
		fieldNum++;
		if (fieldNum > 1) {
			// add at form second field ,
			sb.append(SPLIT_FIELD_FIELD);
		}
	}

	private void addKey(String fieldName) {
		// "key":
		if (fieldName != null) {
			sb.append(DOT);
			sb.append(fieldName);
			sb.append(DOT);
			sb.append(SPLIT_NAME_VALUE);
		}
	}

	/**
	 * append("name","value",true) -> "name":"value"
	 * <p/>
	 * append("name","value",false) -> "name":value
	 * 
	 * @param fieldName
	 *            key
	 * @param value
	 *            value
	 * @param isForStringValue
	 *            if true, add \" between value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, String value, boolean isForStringValue) {
		addPrefix();
		addKey(fieldName);
		// "key":"value"
		if (isForStringValue == true && null != value) {
			sb.append(DOT);
			sb.append(value);
			sb.append(DOT);
		} else {
			// "key":value
			sb.append(value);
		}
		return this;
	}

	/**
	 * append("name",value) -> "name":value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, int value) {
		return append(fieldName, StringUtils.toString(value), false);
	}

	/**
	 * append("name",value) -> "name":value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, float value) {
		return append(fieldName, StringUtils.toString(value), false);
	}

	/**
	 * append("name",value) -> "name":value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, double value) {
		return append(fieldName, StringUtils.toString(value), false);
	}

	/**
	 * append("name",value) -> "name":value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public ToJSONStringBuilder append(String fieldName, long value) {
		return append(fieldName, StringUtils.toString(value), false);
	}

	/**
	 * Equals append(builder.toString())
	 * 
	 * @param builder
	 * @return
	 */
	public ToJSONStringBuilder append(ToJSONStringBuilder builder) {
		addPrefix();
		if (builder != null) {
			sb.append(builder.toString());
		}
		return this;
	}

	/**
	 * Append String
	 * 
	 * @param str
	 * @return
	 */
	public ToJSONStringBuilder append(String str) {
		this.sb.append(str);
		return this;
	}

	/**
	 * appendArray(fieldName,strList,true) -> ["1","2","3"]
	 * <p/>
	 * appendArray(fieldName,strList,false) -> [1,2,3]
	 * 
	 * @param fieldName
	 * @param strList
	 * @param isForStringValue
	 * @return
	 */
	public ToJSONStringBuilder appendArray(String fieldName, List<String> strList, boolean isForStringValue) {
		return appendArray(fieldName, strList.toArray(new String[] {}), true);
	}

	/**
	 * appendArray(fieldName,intList) -> [1,2,3]
	 * 
	 * @param fieldName
	 * @param intList
	 * @return
	 */
	public ToJSONStringBuilder appendArray(String fieldName, List<Integer> intList) {
		List<String> strList = StringUtils.toStringList(intList);
		appendArray(fieldName, strList, false);
		return this;
	}

	/**
	 * appendArray(fieldName,intArray) -> [1,2,3]
	 * 
	 * @param fieldName
	 * @param intArray
	 * @return
	 */
	public ToJSONStringBuilder appendArray(String fieldName, int[] intArray) {
		String[] strArray = StringUtils.toStringArray(intArray);
		return appendArray(fieldName, strArray, false);
	}

	/**
	 * appendArray(fieldName,strArray,true) -> ["1","2","3"]
	 * <p/>
	 * appendArray(fieldName,strArray,false) -> [1,2,3]
	 * 
	 * @param fieldName
	 * @param strArray
	 * @param isForStringValue
	 * @return
	 */
	public ToJSONStringBuilder appendArray(String fieldName, String[] strArray, boolean isForStringValue) {
		addPrefix();
		addKey(fieldName);
		if (strArray != null) {
			sb.append(ARRAY_PREFIX);
			if (strArray != null) {
				int length = strArray.length;
				if (isForStringValue) {
					for (int i = 0; i < length; i++) {
						sb.append(DOT);
						sb.append(strArray[i]);
						sb.append(DOT);
						if (i < length - 1) {
							sb.append(SPLIT_FIELD_FIELD);
						}
					}
				} else {
					for (int i = 0; i < length; i++) {
						sb.append(strArray[i]);
						if (i < length - 1) {
							sb.append(SPLIT_FIELD_FIELD);
						}
					}
				}
			}
			sb.append(ARRAY_SUFFIX);
		} else {
			sb.append(NULL);
		}
		return this;
	}

	public String toString() {
		if (this.fieldNum == 0) {
			sb.append(NULL);
		} else {
			sb.append(SUFFIX);
		}
		return sb.toString();
	}
}
