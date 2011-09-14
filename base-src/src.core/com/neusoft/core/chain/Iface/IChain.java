package com.neusoft.core.chain.Iface;

import java.util.List;

import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Nov 18, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IChain extends ICommand {

	/**
	 * 
	 * {当前责任链在其尾部增加新的过滤链节点}
	 * 
	 * @param filter
	 * @throws BaseException
	 */
	void addCommand(IFilter filter) throws BaseException;

	/**
	 * 
	 * @param filters
	 */
	void setFilters(List<IFilter> filters);
}
