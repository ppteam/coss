package com.neusoft.core.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {系统Xml树结构建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class XmlBeanModel {

	// 节点ID
	private String id;
	// 节点名称
	private String value;
	// 是否叶子节点
	private int leaf;
	// 状态 是否可用
	private int enabled;
	// 父节点
	private String fatherId;
	// 节点深度
	private int deep;

	private int seq;

	private List<XmlBeanModel> nodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public List<XmlBeanModel> getNodes() {
		if (nodes == null) {
			nodes = new ArrayList<XmlBeanModel>();
		}
		return nodes;
	}

	public void setNodes(List<XmlBeanModel> nodes) {
		this.nodes = nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XmlBeanModel [id=" + id + ", value=" + value + ", leaf=" + leaf + ", enabled=" + enabled
				+ ", fatherId=" + fatherId + ", deep=" + deep + ", nodes=" + (nodes == null ? 0 : nodes.size()) + "]";
	}

}
