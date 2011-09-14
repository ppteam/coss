package com.neusoft.model.arsd.dao.pojo;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {系统资源注册数据对象建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 11, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class SourceInfo extends AbstractEntity<String> {

	private static final long serialVersionUID = 156058372778746883L;

	public SourceInfo() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getId()
	 */
	public String getId() {
		return getSrcId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "SCOURCE_INFO";
	}

	// SRC_ID char(32) not null comment '资源记录主键 PK，UUID',
	private String srcId;
	// SRC_NAME varchar(128) not null comment '资源名称，用于菜单显示',
	private String srcName;
	// SRC_DESC varchar(256),
	private String srcDesc;
	// SRC_ORDER int not null default 0 comment '资源排序',
	private Integer srcOrder = 0;
	// SRC_SORT char(32) not null comment '资源组别(组菜单ID)',
	private String srcSort;
	// SRC_URL varchar(256) comment '资源访问路径',
	private String srcUrl;
	// AUTHOR_ID char(32) comment '权限记录PK，UUID',
	private String authorId;
	// ENABLED numeric(1) not null default 1 comment '标识该资源是否启用 0：停用/1：启用；默认为 1
	private Integer enabled = 1;
	// SRC_TYPE int(1) not null comment '数据类别（0:主菜单/1：子菜单）',
	private Integer srcType;
	// STYLECSS varchar(64) comment '自定义样式',
	private String stylecss;

	public static int TYPE_MENU = 0;
	public static int TYPE_SUBMENU = 1;

	private List<SourceInfo> items = new ArrayList<SourceInfo>();

	public void addItem(SourceInfo item) {
		items.add(item);
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public Integer getSrcType() {
		return srcType;
	}

	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getSrcDesc() {
		return srcDesc;
	}

	public void setSrcDesc(String srcDesc) {
		this.srcDesc = srcDesc;
	}

	public Integer getSrcOrder() {
		return srcOrder;
	}

	public void setSrcOrder(Integer srcOrder) {
		this.srcOrder = srcOrder;
	}

	public String getSrcSort() {
		return srcSort;
	}

	public void setSrcSort(String srcSort) {
		this.srcSort = srcSort;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public String getStylecss() {
		return stylecss;
	}

	public void setStylecss(String stylecss) {
		this.stylecss = stylecss;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "SourceInfo [srcId=" + srcId + ", srcName=" + srcName + ", srcDesc=" + srcDesc + ", srcOrder="
				+ srcOrder + ", srcSort=" + srcSort + ", srcUrl=" + srcUrl + ", authorId=" + authorId + ", enabled="
				+ enabled + ", srcType=" + srcType + ", items=" + items + "]";
	}

}
