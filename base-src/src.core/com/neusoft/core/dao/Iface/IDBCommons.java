package com.neusoft.core.dao.Iface;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> {数据库共用函数接口定义} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 20, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IDBCommons {

	/**
	 * {获取硬编码方式的事务处理句柄}
	 * 
	 * @return
	 */
	TransactionTemplate getTransactionTemplate();

}