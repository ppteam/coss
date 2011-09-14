package com.neusoft.core.dao.Impl;

import java.io.Serializable;

/**
 * 
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {通用泛型Dao的实现,为了保证命名空间的唯一性，必须添加多个实例到IOC中} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 19, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class GenericEntityDao<T extends AbstractEntity<PK>, PK extends Serializable> extends
		AbstractEntityDao<T, PK> {

	public GenericEntityDao() {
	}

	// 默认不开启缓存
	private boolean isCached = false;

	public boolean isCached() {
		return isCached;
	}

	public void setCached(boolean isCached) {
		this.isCached = isCached;
	}

}
