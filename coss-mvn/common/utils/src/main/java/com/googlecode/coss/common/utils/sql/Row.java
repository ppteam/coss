/**
 * 
 */
package com.googlecode.coss.common.utils.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.coss.common.utils.collections.CollectionUtils;
import com.googlecode.coss.common.utils.lang.NumberUtils;

/**
 * <p>
 * Database table wrapper, wrapper on record form mapping columns String[] and
 * values Object[]
 * </p>
 * <p>
 * Mapping one row of ResultSet,It didn't need connection.
 * </p>
 * <p>
 * String columns and values mapping.
 * </p>
 */
public class Row {

    private Map<String, Object> values = new HashMap<String, Object>();

    /**
     * <p>
     * Generate RowSet by appointing columnNames and values
     * </p>
     * 
     * @param columnNames
     * @param values
     */
    protected Row(String[] columnNames, Object[] os) {
        int len = columnNames.length;
        for (int i = 0; i < len; i++) {
            values.put(columnNames[i], os[i]);
        }
    }

    /**
     * <p>
     * Generate RowSet by Map
     * </p>
     * 
     * @param columnNames
     * @param values
     */
    protected Row(Map<String, Object> values) {
        this.values = values;
    }

    /**
     * <p>
     * Get String by appointing column Name.
     * </p>
     * 
     * @param columnName
     * @return
     */
    public Object getObject(String columnName) {
        if (!values.containsKey(columnName)) {
            throw new java.lang.IllegalArgumentException(String.format(
                    "columnName: %s dosn't exists", columnName));
        } else {
            return values.get(columnName);
        }
    }

    /**
     * <p>
     * Get int by appointing column Name.
     * </p>
     * 
     * @param columnName
     * @param defaultNullValue
     * @return
     */
    public int getInt(String columnName) {
        return getInt(columnName, 0);
    }

    /**
     * <p>
     * Get int by appointing column Name.
     * </p>
     * 
     * @param columnName
     * @param defaultNullValue
     * @return
     */
    public int getInt(String columnName, int defaultNullValue) {
        Object obj = getObject(columnName);
        if (obj != null) {
            if (obj instanceof String) {
                return NumberUtils.toInt((String) obj);
            }
            return (Integer) obj;
        } else {
            return defaultNullValue;
        }
    }

    /**
     * <p>
     * Get String by appointing column Name.
     * </p>
     * 
     * @param columnName
     * @param defaultNullValue
     * @return
     */
    public String getString(String columnName, String defaultNullValue) {
        Object obj = getObject(columnName);
        if (obj != null) {
            return obj.toString();
        } else {
            return defaultNullValue;
        }
    }

    /**
     * <p>
     * Get String by appointing column Name.
     * </p>
     * 
     * @param columnName
     * @return
     */
    public String getString(String columnName) {
        return getString(columnName, null);
    }

    /**
     * <p>
     * Get java.util.Date by appointing columnName.
     * </p>
     * 
     * @param columnName
     * @param defaultNullValue
     * @return
     */
    public Date getDate(String columnName, Date defaultNullValue) {
        Object obj = getObject(columnName);
        if (obj == null) {
            return defaultNullValue;
        }
        if (obj instanceof Timestamp) {
            return new Date(((Timestamp) obj).getTime());
        } else if (obj instanceof java.sql.Date) {
            return new Date(((java.sql.Date) obj).getTime());
        } else if (obj instanceof java.sql.Time) {
            return new Date(((java.sql.Time) obj).getTime());
        }
        return (Date) obj;
    }

    /**
     * @param columnName
     * @return
     */
    public Date getDate(String columnName) {
        return getDate(columnName, null);
    }

    /**
     * <p>
     * Return all column names
     * </p>
     * 
     * @return
     */
    public String[] getColumnNames() {
        return this.values.keySet().toArray(new String[] {});
    }

    /**
     * <p>
     * Return all values
     * </p>
     * 
     * @return
     */
    public Object[] getValues() {
        return this.values.values().toArray();
    }

    /**
     * <p>
     * Return Column Count
     * </p>
     * 
     * @return
     */
    public int getColumnCount() {
        return this.values.size();
    }

    /**
     * <p>
     * to key-value style(Map)
     * </p>
     * 
     * @return
     */
    public Map<String, Object> getMap() {
        return this.values;
    }

    /**
     * <p>
     * Get RowSet From ResultSet.
     * </p>
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Row valueOneOf(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount < 1) {
            return null;
        }
        Map<String, Object> rowValues = new HashMap<String, Object>(columnCount);
        Row rowSet = new Row(rowValues);
        for (int i = 0; i < columnCount; i++) {
            rowValues.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
        }
        return rowSet;
    }

    /**
     * <p>
     * Get RowSet List From ResultSet.
     * </p>
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    public static List<Row> valueOf(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (columnCount < 1) {
            return null;
        }
        List<Row> rowSetList = new ArrayList<Row>();
        while (rs.next()) {
            Map<String, Object> rowValues = new HashMap<String, Object>(columnCount);
            Row rowSet = new Row(rowValues);
            for (int i = 0; i < columnCount; i++) {
                rowValues.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
            }
            rowSetList.add(rowSet);
        }
        return rowSetList;
    }

    public String toString() {
        return this.getMap().toString();
    }

    /**
     * <p>
     * Put key value to this Row
     * </p>
     * 
     * @param key
     * @param value
     * @return
     */
    public Object put(String key, Object value) {
        return this.values.put(key, value);
    }

    /**
     * <p>
     * Get sub Row by appointing prefix
     * </p>
     * 
     * @param prefix
     * @return
     */
    public Row subRow(String prefix) {
        Map<String, Object> newValues = new HashMap<String, Object>();
        CollectionUtils.subMap(this.values, newValues, prefix);
        return new Row(newValues);
    }

    /**
     * <p>
     * Return item count
     * </p>
     * 
     * @return
     */
    public int size() {
        return this.values.size();
    }
}
