package com.neusoft.model.udmgr.dao.pojo;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {系统组织结构建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 7, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class Department extends AbstractEntity<String> {

	private static final long serialVersionUID = 2214738651091527861L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getDeptId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "DEPARTMENT_INFO";
	}

	// DEPT_ID char(32) not null comment '部门ID（UUID）'
	private String deptId;
	// NODE_DEEP int not null comment '节点深度，树节点深度（从0开始计数）'
	private Integer nodeDeep;
	// FATHER_ID char(32) not null comment '上级节点ID，根节点默认为‘ROOT’'
	private String fatherId;
	// DEPT_NAME varchar(64) not null comment '部门名称'
	private String deptName;
	// DEPT_ORDER int not null default 0 comment '部门排序'
	private Integer deptOrder;
	// ENABLED numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用'
	private Integer enabled;
	// NODE_XPATH varchar(256) not null comment '节点路径,采用 - 分隔'
	private String nodeXpath;
	// LEAFNODE int not null default 0 comment '是否叶子节点 0：是/1：否',
	private Integer leafNode;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getNodeDeep() {
		return nodeDeep;
	}

	public void setNodeDeep(Integer nodeDeep) {
		this.nodeDeep = nodeDeep;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDeptOrder() {
		return deptOrder;
	}

	public void setDeptOrder(Integer deptOrder) {
		this.deptOrder = deptOrder;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getNodeXpath() {
		return nodeXpath;
	}

	public void setNodeXpath(String nodeXpath) {
		this.nodeXpath = nodeXpath;
	}

	public Integer getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(Integer leafNode) {
		this.leafNode = leafNode;
	}

}
