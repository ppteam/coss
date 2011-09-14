package com.neusoft.core.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {Extjs 下拉框建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 18, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ComboxModel {

	public ComboxModel() {
	}

	public ComboxModel(String storName) {
		this.storName = storName;
	}

	// 逻辑值
	private String regValue;
	// 显示值
	private String displayValue;
	// 分类
	private String storName;

	private Integer leafNode = 0;

	private List<ComboxModel> items = new ArrayList<ComboxModel>();

	public String getRegValue() {
		return regValue;
	}

	public void setRegValue(String regValue) {
		this.regValue = regValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public List<ComboxModel> getItems() {
		return items;
	}

	public void setItems(List<ComboxModel> items) {
		this.items = items;
	}

	public String getStorName() {
		return storName;
	}

	public void setStorName(String storName) {
		this.storName = storName;
	}

	public Integer getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(Integer leafNode) {
		this.leafNode = leafNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComboxModel [regValue=" + regValue + ", displayValue=" + displayValue + ", storName=" + storName + "]";
	}

}
