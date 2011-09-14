package com.googlecode.coss.common.utils.lang.builder;

import java.lang.reflect.Field;

import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.reflect.ClassUtils;

/**
 * <p>
 * Assists in implementing {@link Object#toString()} methods.
 * </p>
 * use case 1: regular type public class Person{ private String name; private
 * int age; private boolean sex;
 * 
 * public Person(String name, int age, boolean sex){ this.name = name; this.age
 * = age; this.sex = sex; } public String toString(){ return new
 * ToStringBuilder(this).append("name", name).append("age", age).append("sex",
 * sex).toString(); } Person@33263331[name=azheng,age=26,sex=true]
 * 
 * use case 2: by reflection public static class Person{
 * 
 * private String name;
 * 
 * private int age;
 * 
 * private boolean sex;
 * 
 * public Person(String name, int age, boolean sex){ this.name = name; this.age
 * = age; this.sex = sex; }
 * 
 * public String toString(){ return ToStringBuilder.reflectionToString(this); }
 * } thre result: Person@33263331[name=azheng,age=26,sex=true]
 * 
 * use case 3: by reflection & appointing exclude field public static class
 * Person{
 * 
 * private String name;
 * 
 * private int age;
 * 
 * private boolean sex;
 * 
 * public Person(String name, int age, boolean sex){ this.name = name; this.age
 * = age; this.sex = sex; }
 * 
 * public String toString(){ return
 * ToStringBuilder.reflectionToString(this,"name"); } } the result:
 * Person@3341232[age=26,sex=true]
 * 
 * 2:43:52 PM Sep 10, 2009
 */
public class ToStringBuilder {

	private static final String FILED_START = "[";
	private static final String FIELD_END = "]";
	private static final String SPLIT_CLASSNAME_HASHCODE = "@";
	private static final String SPLIT_FIELD_FIELD = ",";
	private static final String EQUAL = "=";

	private StringBuilder sb;

	private int fieldNum = 0;

	/**
	 * @param obj
	 *            The object to overwrite toString() method
	 */
	public ToStringBuilder(Object obj) {
		this.sb = new StringBuilder();
		sb.append(obj.getClass().getSimpleName()).append(SPLIT_CLASSNAME_HASHCODE).append(obj.hashCode());
	}

	/**
	 * <p>
	 * Append to the toString
	 * </p>
	 * 
	 * @param filedName
	 *            Field name
	 * @param obj
	 *            the value of field
	 * @return ToStringBuilder
	 */
	public ToStringBuilder append(String filedName, Object obj) {
		fieldNum++;
		if (fieldNum == 1) {
			sb.append(FILED_START).append(filedName).append(EQUAL).append(obj);
		} else {
			sb.append(SPLIT_FIELD_FIELD).append(filedName).append(EQUAL).append(obj);
		}
		return this;
	}

	/**
	 * <p>
	 * Forwards to <code>ReflectionToStringBuilder</code>.
	 * </p>
	 * 
	 * @param obj
	 *            The Object to output
	 * @param excludeFieldName
	 *            the exclude field name list, case sensitive
	 * @return the String result
	 */
	public static String reflectionToString(Object obj, String... excludeFieldName) {
		ToStringBuilder tsb = new ToStringBuilder(obj);
		Field[] fields = ClassUtils.getNoStaticFieldArray(obj);
		try {
			for (Field field : fields) {
				field.setAccessible(true);

				if (StringUtils.isInList(field.getName(), excludeFieldName)) {
					continue;
				}
				tsb.fieldNum++;
				if (tsb.fieldNum == 1) {
					tsb.sb.append(FILED_START);
				} else {
					tsb.sb.append(SPLIT_FIELD_FIELD);
				}

				// append field name
				tsb.sb.append(field.getName());
				tsb.sb.append(EQUAL);
				// append field value
				tsb.sb.append(field.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while reflection the class fields", e);
		}
		return tsb.toString();
	}

	public String toString() {
		if (fieldNum > 0) {
			sb.append(FIELD_END);
		}
		return this.sb.toString();
	}

	public static void removeLastItemIfEqual(StringBuilder sb, char c) {
		if (sb == null) {
			return;
		}
		int len = sb.length();
		if (len == 0) {
			return;
		}
		if (sb.charAt(len - 1) == c) {
			sb.deleteCharAt(len - 1);
		}
	}
}
