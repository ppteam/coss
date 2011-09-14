package com.neusoft.core.web;

public class InterceptUrlsImpl {

	public InterceptUrlsImpl() {
	}

	private String pattern;

	private String access;

	private String prefix = "";

	private String suffix = "*";

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * {构建完整的Ant路径}
	 * 
	 * @return
	 */
	public String bulderPattern() {
		StringBuffer sb = new StringBuffer(prefix);
		sb.append(pattern).append(suffix);
		return sb.toString();
	}

	/**
	 * {构建完整的Ant路径}
	 * 
	 * @return
	 */
	public String bulderAccess() {
		return "ROLE_" + access.toUpperCase();
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "InterceptUrlsImpl [pattern=" + prefix + pattern + suffix + ", access=" + access + "]";
	}

}
