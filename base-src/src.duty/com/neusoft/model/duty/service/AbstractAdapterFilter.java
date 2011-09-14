package com.neusoft.model.duty.service;

import org.apache.commons.configuration.Configuration;

import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.model.duty.dao.pojo.CheckingRule;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {检测当前操作目录的逻辑判断} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractAdapterFilter extends FilterBase {

	private static final long serialVersionUID = -1661357785545171667L;

	protected IEntityDao<CheckingRule, String> dutyCommonDao;

	public AbstractAdapterFilter() {
	}

	public void setDutyCommonDao(IEntityDao<CheckingRule, String> dutyCommonDao) {
		this.dutyCommonDao = dutyCommonDao;
	}

	protected Configuration configuration;

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
