package com.neusoft.core.chain.general;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.neusoft.core.chain.Impl.FilterBase;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {Dao 抽象节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 20, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class SqlMapClientSupportFilter extends FilterBase {

	private static final long serialVersionUID = -6467723596883146311L;

	public SqlMapClientSupportFilter() {
	}

	// ibatis Dao 模板类 线程安全
	protected SqlMapClientTemplate sqlMapClientTemplate;

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	/**
	 * {访问Ibatis Sql 引擎}
	 * 
	 * @return SqlMapClient
	 */
	protected SqlMapClient getSqlMapClient() {
		Assert.notNull(sqlMapClientTemplate, "sqlMapClientTemplate is null,please init this param!");
		return sqlMapClientTemplate.getSqlMapClient();
	}
}
