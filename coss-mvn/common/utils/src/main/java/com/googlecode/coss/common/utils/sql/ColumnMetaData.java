/*
 * @# ResultSetMetaDataWrapper.java.java 3:44:42 PM Feb 7, 2010 2010
 * 
 */
package com.googlecode.coss.common.utils.sql;

/**
 * <p>
 * Database Column Meta
 * </p>
 */
public class ColumnMetaData {

    private String  name;

    private String  label;

    private int     type;

    private boolean isAutoIncrement;

    private boolean isNullAble;

    private boolean isReadOnly;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public boolean isNullAble() {
        return isNullAble;
    }

    public void setNullAble(boolean isNullAble) {
        this.isNullAble = isNullAble;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}
