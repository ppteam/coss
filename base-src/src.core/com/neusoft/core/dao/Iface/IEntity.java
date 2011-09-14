package com.neusoft.core.dao.Iface;

import java.io.Serializable;

/**
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {作为单表映射的标识接口} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 19, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IEntity<PK extends Serializable> extends Serializable {

	/**
	 * 
	 * {获取该表的主键值}
	 * 
	 * @return
	 */
	PK getId();

	/**
	 * 
	 * {返回该Entity 对应的表主键}
	 * 
	 * @return
	 */
	String getTVName();

	/**
	 * @function 获取本节点的caache 前缀
	 * @return
	 */
	String getCachePrefix();

	/**
	 * 
	 * {判断该Bean 映射是否是 数据库视图}
	 * 
	 * @return T/F 映射View/Table
	 */
	boolean isView();
}
