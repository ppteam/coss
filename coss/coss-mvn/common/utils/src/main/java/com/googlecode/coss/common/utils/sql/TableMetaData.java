/*
 * @# TableMetaData.java.java 3:58:49 PM Feb 7, 2010 2010
 * 
 */
package com.googlecode.coss.common.utils.sql;

import java.util.List;

import com.googlecode.coss.common.utils.text.JavaBean;

/**
 * <p>
 * Database Table Meta
 * </p>
 */
public class TableMetaData {

	public String name;

	private List<ColumnMetaData> columnMetaDatas;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnMetaData> getColumnMetaDatas() {
		return columnMetaDatas;
	}

	public void setColumnMetaDatas(List<ColumnMetaData> columnMetaDatas) {
		this.columnMetaDatas = columnMetaDatas;
	}

	public int getCount() {
		return columnMetaDatas.size();
	}

	public String getSqlScriptInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(this.name + " (");
		int i = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (!cmd.isAutoIncrement() && !cmd.isReadOnly()) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(cmd.getName());
				i++;
			}
		}
		sb.append(" ) VALUES (");
		for (int j = 0; j < i; j++) {
			if (j > 0) {
				sb.append(",");
			}
			sb.append("?");
		}
		sb.append(")");
		sb.toString();
		return sb.toString();
	}

	public String getSqlScriptSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		int i = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(cmd.getName());
			i++;
		}
		sb.append(" FROM ");
		sb.append(this.getName());
		return sb.toString();
	}

	public String getSqlScriptUpdateByFirstField() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + this.getName() + " SET ");
		int i = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (!cmd.isAutoIncrement() && !cmd.isReadOnly()) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(cmd.getName() + " = ?");
				i++;
			}
		}
		sb.append(" WHERE ");
		int j = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (j == 0) {
				sb.append(cmd.getName() + " = ?");
				j++;
			}
		}
		return sb.toString();
	}

	public String getSqlScriptUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + this.getName() + " SET ");
		int i = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (!cmd.isAutoIncrement()) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(cmd.getName() + " = ?");
				i++;
			}
		}
		sb.append(" WHERE ");
		int j = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (!cmd.isAutoIncrement()) {
				if (j > 0) {
					sb.append(" AND ");
				}
				sb.append(cmd.getName() + " = ?");
				j++;
			}
		}
		return sb.toString();
	}

	public String getSqlScriptDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM " + this.getName() + " WHERE ");
		int j = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (j > 0) {
				sb.append(" AND ");
			}
			sb.append(cmd.getName() + " = ?");
			j++;
		}
		return sb.toString();
	}

	public String getSqlScriptDeleteByFirstField() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM " + this.getName() + " WHERE ");
		int j = 0;
		for (ColumnMetaData cmd : getColumnMetaDatas()) {
			if (j == 0) {
				sb.append(cmd.getName() + " = ?");
				j++;
			}
		}
		return sb.toString();
	}

	public JavaBean toJavaBean() {
		JavaBean bean = new JavaBean();
		bean.setBeanClassName(TableNameUtils.toClassName(name));
		List<ColumnMetaData> cmds = this.getColumnMetaDatas();
		for (ColumnMetaData cmd : cmds) {
			bean.addField(SqlType2JavaType.getJavaType(cmd.getType()), ColumnNameUtils.toFieldName(cmd.getName()));
		}
		return bean;
	}
}
