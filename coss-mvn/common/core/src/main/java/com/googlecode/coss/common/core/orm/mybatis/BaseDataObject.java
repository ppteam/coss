package com.googlecode.coss.common.core.orm.mybatis;

import java.lang.reflect.Field;

import com.googlecode.coss.common.core.helper.BeanHelper;
import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.reflect.ClassUtils;

//静态导入日期转换方法

/**
 * 
 */
public abstract class BaseDataObject implements java.io.Serializable {

	private static final long serialVersionUID = -157500188374535260L;

	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 得到主键字段(查找"类名+Id"字段)
	 */
	public Field findPkField() {
		String pkFieldName = StringUtils.lowerFirst(ClassUtils.getShortClassName(this.getClass()) + "Id");
		Field pkField = ClassUtils.getField(this, pkFieldName);
		return pkField;
	}

	/**
	 * 得到主键字段的值
	 */
	public Object getPkValue() {
		Field pkField = findPkField();
		if (pkField == null)
			return null;
		else
			return BeanHelper.getProperty(this, pkField.getName());
	}

	/**
	 * 设置主键字段的值
	 * 
	 * @param pkValue
	 */
	public void setPkValue(java.io.Serializable pkValue) {
		Field pkField = findPkField();
		if (pkField != null)
			BeanHelper.setProperty(this, pkField.getName(), pkValue);
	}

}
