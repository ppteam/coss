package com.googlecode.coss.common.utils.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.coss.common.utils.lang.reflect.ClassUtils;
import com.googlecode.coss.common.utils.lang.reflect.ReflectUtils;

public class ResultSetUtils {

	/**
	 * <p>
	 * Iterator ResultSet, inject all fields to appointing class, then return
	 * the instance
	 * </p>
	 * 
	 * @param c
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjectListUseReflection(Class<T> c, ResultSet rs) throws Exception {
		if (rs == null) {
			return null;
		}
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		if (columnCount < 1) {
			return null;
		} else if (columnCount == 1) {
			if (ClassUtils.isSystemClass(c)) {
				return getJDKObjectListFromResultSet(c, rs);
			}
		}
		Map<String, Object> keyValues = new HashMap<String, Object>(columnCount);
		List<T> objectList = new ArrayList<T>();
		while (rs.next()) {
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				if (columnName == null) {
					columnName = rsmd.getColumnLabel(i + 1);
				}
				keyValues.put(ColumnNameUtils.toFieldName(columnName), rs.getObject(i + 1));
			}
			objectList.add((T) ReflectUtils.inject(c, keyValues));
		}
		return objectList;
	}

	// get JDK Class instance from ResultSet
	@SuppressWarnings("unchecked")
	private static <T> List<T> getJDKObjectListFromResultSet(Class<T> c, ResultSet rs) throws Exception {
		List<T> objectList = new ArrayList<T>();
		while (rs.next()) {
			objectList.add((T) rs.getObject(1));
		}
		return objectList;
	}

	/**
	 * <p>
	 * Iterator ResultSet, inject all fields to appointing class, if not result
	 * found, return null directory
	 * </p>
	 * 
	 * @param c
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectUseReflection(Class<T> c, ResultSet rs) throws Exception {
		if (rs == null) {
			return null;
		}
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		if (columnCount < 1) {
			return null;
		} else if (columnCount == 1) {
			if (ClassUtils.isSystemClass(c)) {
				return (T) getJDKObjectFromResultSet(rs);
			}
		}
		Map<String, Object> keyValues = new HashMap<String, Object>(columnCount);
		if (rs.next()) {
			for (int i = 0; i < columnCount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				if (columnName == null) {
					columnName = rsmd.getColumnLabel(i + 1);
				}
				ColumnNameUtils.toFieldName(columnName);
				keyValues.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
			}

		} else {
			return null;
		}
		return (T) ReflectUtils.inject(c, keyValues);
	}

	// //get JDK Class instance from ResultSet
	private static Object getJDKObjectFromResultSet(ResultSet rs) throws Exception {
		if (rs.next()) {
			return rs.getObject(1);
		}
		return null;
	}
}
