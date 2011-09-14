package com.neusoft.core.dao.Impl;

import java.io.Serializable;

import com.neusoft.core.dao.Iface.IEntity;

/**
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 19, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractEntity<PK extends Serializable> implements IEntity<PK> {

	private static final long serialVersionUID = -492240649162096036L;

	public AbstractEntity() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#isView()
	 */
	public boolean isView() {
		return false;
	}

	// 分页起始游标位置
	private Integer start = 0;
	// 页面大小
	private Integer limit = 20;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntity#getCachePrefix()
	 */
	public String getCachePrefix() {
		if (cachePrefix == null) {
			cachePrefix = this.getClass().getSimpleName() + "_";
		}
		return cachePrefix;
	}

	private String cachePrefix = null;

}
