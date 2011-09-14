package com.neusoft.core.common.Iface;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目常量定义表} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IConstant {
	// 数据量基本操作在上下文中的指针
	String DB_BASE_ACTION = "DB_BASE_ACTION";
	// 对应新增对象操作
	final int DB_BASE_INSERT = 1;
	// 对应指定对象的修改
	final int DB_BASE_UPDATE = 0;
	// 对应制定对象的删除
	final int DB_BASE_DELETE = -1;

	// bean to xml Key
	String KEY_BEAN2XML = "key_bean2xml";
}
