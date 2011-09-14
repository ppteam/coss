package com.neusoft.model.arsd.dao.pojo;

import org.springframework.util.Assert;

import com.neusoft.core.dao.Impl.AbstractEntity;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {映射 表 DICT_CATALOG} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DictCatalog extends AbstractEntity<String> {

	private static final long serialVersionUID = -2321553908144829942L;

	public DictCatalog() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getTVName()
	 */
	public String getTVName() {
		return "DICT_CATALOG";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Impl.AbstractEntity#getId()
	 */
	public String getId() {
		return getCatalogId();
	}

	// CATALOG_ID char(32) not null comment '目录ID，定长5位数字构成(UUID)',
	private String catalogId;
	// ENTRY_NAME varchar(64) not null comment '条目名称'
	private String entryName;
	// ENTRY_SHUTNAME varchar(32) comment '条目缩写'
	private String entryShutName;
	// ENTRY_DESC varchar(64) not null comment '条目描述'
	private String entryDesc;
	// ENABLED numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用'
	private Integer enabled = 1;

	public void setEntryName(String entryName) {
		Assert.hasLength(entryName, "DictCatalog.entryType value cann't be empty");
		this.entryName = entryName;
	}

	public String getEntryShutName() {
		return entryShutName;
	}

	public void setEntryShutName(String entryShutName) {
		this.entryShutName = entryShutName;
	}

	public String getEntryDesc() {
		return entryDesc;
	}

	public void setEntryDesc(String entryDesc) {
		this.entryDesc = entryDesc;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getEntryName() {
		return entryName;
	}

	@Override
	public String toString() {
		return "AbstractDictBase [catalogId=" + catalogId + ", entryName=" + entryName
				+ ", entryShutName=" + entryShutName + ", entryDesc=" + entryDesc + "]";
	}

	public static DictCatalog getInstance() {
		return new DictCatalog();
	}

}
