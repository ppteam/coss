package com.neusoft.core.common.Iface;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {刷新绑定资源访问控制集合} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 23, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IRefreshSecMetadata {

	/**
	 * {执行刷新动作函数定义}
	 * 
	 * @throws BaseException
	 */
	void refreshMetadataSource() throws BaseException;
}
