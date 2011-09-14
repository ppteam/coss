package com.neusoft.model.arsd.dao.pojo;

/**
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
public class DictRecord extends DictCatalog {

	private static final long serialVersionUID = -2321553908144829942L;

	public DictRecord() {
	}

	// ENTRY_SIRE char(32) comment '上级条目（针对级联条目设置）'
	private String entrySire;

	// ENTRY_ORDER int not null default 0
	private Integer entryOrder = 0;

	private String catalogName;

	public String getEntrySire() {
		return entrySire;
	}

	public void setEntrySire(String entrySire) {
		this.entrySire = entrySire;
	}

	public Integer getEntryOrder() {
		return entryOrder;
	}

	public void setEntryOrder(Integer entryOrder) {
		this.entryOrder = entryOrder;
	}

	public static DictRecord getInstance() {
		return new DictRecord();
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@Override
	public String toString() {
		return super.toString() + "DictRecord [entrySire=" + entrySire + ", entryOrder="
				+ entryOrder + "]";
	}

}
