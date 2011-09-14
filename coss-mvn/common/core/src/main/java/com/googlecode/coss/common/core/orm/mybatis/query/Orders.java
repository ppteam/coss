package com.googlecode.coss.common.core.orm.mybatis.query;

import java.io.Serializable;

public class Orders implements Serializable {

	private static final long serialVersionUID = -1418466738961167523L;

	public String toString() {
		return (new StringBuilder()).append(propertyName).append(' ').append(ascending ? "asc" : "desc").toString();
	}

	public Orders ignoreCase() {
		ignoreCase = true;
		return this;
	}

	protected Orders(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	public static Orders asc(String propertyName) {
		return new Orders(propertyName, true);
	}

	public static Orders desc(String propertyName) {
		return new Orders(propertyName, false);
	}

	private boolean ascending;
	private boolean ignoreCase;
	private String propertyName;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}