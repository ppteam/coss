package com.neusoft.core.dao.Impl;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.neusoft.core.cache.Iface.ICacheTemplate;
import com.neusoft.core.dao.Iface.IEntity;
import com.neusoft.core.dao.Iface.IEntityDao;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.BatisSqlException;
import com.neusoft.core.web.JsonPageModel;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {抽象Dao实现，默认一表一配置} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 18, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractEntityDao<T extends IEntity<PK>, PK extends Serializable> extends SqlMapClientDaoSupport
		implements IEntityDao<T, PK> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractEntityDao.class);

	public AbstractEntityDao() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#insert(java.lang.String,
	 * com.neusoft.core.dao.Iface.IEntity)
	 */
	public PK insert(final T saveInstance) throws BaseException {
		Assert.notNull(saveInstance, "saveInstance is null .....");
		String sqlName = getInsertStatementName();
		if (logger.isDebugEnabled()) {
			logger.debug("insert(String, T) - start \n [statementName:" + sqlName + "] \n [saveInstance:{"
					+ saveInstance + "}]");
		}
		try {
			return getSqlMapClientTemplate().execute(new SqlMapClientCallback<PK>() {

				public PK doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					executor.insert(getInsertStatementName(), saveInstance);
					return saveInstance.getId();
				}

			});
		} catch (DataAccessException e) {
			logger.error("insert Object[" + saveInstance.toString() + "] has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "insert(...)", e.getMessage() });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#insert(java.lang.String,
	 * com.neusoft.core.dao.Iface.IEntity)
	 */
	public Object insert(final String statementName, final Object saveInstance) throws BaseException {
		Assert.notNull(saveInstance, "saveInstance is null .....");
		try {
			return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {

				public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					String sqlName = statementName == null ? getInsertStatementName()
							: fullStatementName(statementName);
					if (logger.isDebugEnabled()) {
						logger.debug("insert(String, T) - start \n [statementName:" + statementName
								+ "] \n [saveInstance:{" + saveInstance + "}]");
					}
					return executor.insert(sqlName, saveInstance);
				}

			});
		} catch (DataAccessException e) {
			logger.error("insert Object[" + saveInstance.toString() + "] has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "insert(...)", e.getMessage() });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#updateByInstace(com.neusoft.core
	 * .dao.Iface.IEntity)
	 */
	public Integer updateById(final T updateInstace) throws BaseException {
		Assert.notNull(updateInstace, "[p:updateInstace] can't be null");
		Assert.notNull(updateInstace.getId(), "[p:updateInstace'id] can't be null");
		int rows = 0;
		try {
			rows = getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

				public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					return executor.update(fullStatementName("updateById"), updateInstace);
				}

			});
		} catch (DataAccessException e) {
			logger.error("update Object[" + updateInstace.toString() + "] has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "update(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("updateById(T updateInstace) - end  and return rows [" + rows + "]");
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#update(java.lang.String,
	 * java.lang.Object)
	 */
	public Integer update(final String statementName, final Object updateInstance) throws BaseException {
		int rows = 0;
		try {
			rows = getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

				public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					String sqlName = statementName == null ? getUpdateStatementName()
							: fullStatementName(statementName);
					if (logger.isDebugEnabled()) {
						logger.debug("update(String, Object) - start\n [statementName:" + sqlName
								+ "] \n [updateInstance:{" + updateInstance + "}]");
					}
					return executor.update(sqlName, updateInstance);
				}

			});
		} catch (DataAccessException e) {
			logger.error("update Object[" + updateInstance.toString() + "] has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "update(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("update(String, Object) - end  and return rows [" + rows + "]");
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#delete(java.lang.String,
	 * java.lang.Object)
	 */
	public Integer delete(final String statementName, final Object parameterObject) throws BaseException {
		int rows = 0;
		try {
			rows = getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

				public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					String sqlName = statementName == null ? getDeleteStatementName()
							: fullStatementName(statementName);
					if (logger.isDebugEnabled()) {
						logger.debug("delete(String, Object) - start\n [statementName:" + sqlName
								+ "] \n [parameterObject:{" + parameterObject + "}]");
					}
					return executor.delete(sqlName, parameterObject);
				}
			});
		} catch (DataAccessException e) {
			logger.error("delete Object[" + parameterObject.toString() + "] has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "delete(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("delete(String, Object) - end and return rows [" + rows + "]");
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#get(java.io.Serializable,
	 * boolean)
	 */
	public T get(final PK pk, boolean nullThrows) throws BaseException {
		T res = get(pk);
		if (res == null) {
			throw new BatisSqlException("0202", null, new String[] { pk.toString(), getNameSpace() });
		} // end_if
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#get(java.io.Serializable)
	 */
	public T get(final PK pk) throws BaseException {
		Assert.notNull(pk, "get(PK pk)  pk is null");
		if (logger.isDebugEnabled()) {
			logger.debug("get(PK) - start and [pk:" + pk + "]");
		}
		T res = null;
		try {
			res = getSqlMapClientTemplate().execute(new SqlMapClientCallback<T>() {

				@SuppressWarnings("unchecked")
				public T doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					return (T) executor.queryForObject(getFindByIdStatementName(), pk);
				}

			});
		} catch (DataAccessException e) {
			logger.error("get(PK:" + pk + ") has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "get(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("get(PK) - end \n [getObject:{" + (res == null ? "null" : res.toString()) + "}]");
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#get(java.lang.String,
	 * java.lang.Object)
	 */
	public T get(String statementName, Object parameterObject) throws BaseException {
		if (logger.isDebugEnabled()) {
			logger.debug("get(String, Object) - start \n[statementName:" + statementName + "] \n[parameterObject:{"
					+ parameterObject + "}]");
		}
		T res = null;
		List<T> list = findByParams(statementName, parameterObject);
		if (list != null && list.size() > 1) {
			logger.error("more then one data in query's resulit, size is [" + list.size() + "]");
			throw new BatisSqlException("0201", null, new String[] { "get(...)", parameterObject.toString() });
		}
		res = list == null ? null : list.get(0);
		if (logger.isDebugEnabled()) {
			logger.debug("get(String, Object) - end and return [" + res.toString() + "]");
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#queryForCount(java.lang.String,
	 * com.neusoft.core.dao.Iface.IEntity)
	 */
	public Long queryForCount(String statementName, Object exampleInstance) throws BaseException {
		Long rows = 0L;
		String sqlName = fullStatementName(statementName);
		try {
			Object number = getSqlMapClientTemplate().queryForObject(sqlName, exampleInstance);
			if (number instanceof Integer) {
				rows = new Long(((Integer) number).intValue());
			} else {
				rows = (Long) number;
			}
		} catch (DataAccessException e) {
			logger.error("queryForCount(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "queryForCount(...)", e.getMessage() });
		}

		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#queryByParams(java.lang.String,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <M extends Object> List<M> queryByParams(String statementName, Object parameterObject) throws BaseException {
		String sqlName = fullStatementName(statementName);
		List<M> res = null;
		try {
			res = (List<M>) getSqlMapClientTemplate().queryForList(sqlName, parameterObject);
		} catch (DataAccessException e) {
			logger.error("queryByParams(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "queryByParams(...)", e.getMessage() });
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#quertForSingle(java.lang.String,
	 * java.lang.Object)
	 */
	public <M> M quertForSingle(final String statementName, final Object parameterObject) throws BaseException {
		Assert.notNull(statementName, "[p:statementName] is null");
		M resObj = null;
		try {
			resObj = getSqlMapClientTemplate().execute(new SqlMapClientCallback<M>() {

				@SuppressWarnings("unchecked")
				public M doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					M res = (M) executor.queryForObject(statementName, parameterObject);
					return res;
				}
			});
		} catch (DataAccessException e) {
			logger.error("quertForSingle(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "quertForSingle(...)", e.getMessage() });
		}
		return resObj;
	} // end_fun

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#queryByPagination(java.lang.String,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JsonPageModel queryByPagination(String pre_statementName, Object parameterObject) throws BaseException {
		JsonPageModel pageModel = JsonPageModel.getInstance();
		try {
			String sqlName = fullStatementName(pre_statementName);
			Integer total = (Integer) getSqlMapClientTemplate().queryForObject(sqlName + "_page", parameterObject);
			List<Object> list = getSqlMapClientTemplate().queryForList(sqlName, parameterObject);
			pageModel.putTotal(total.intValue());
			pageModel.putData(list);
			pageModel.putSize(list == null ? 0 : list.size());
			if (logger.isDebugEnabled()) {
				logger.debug("[total:" + total + ",result size:" + list.size() + "]");
			}
		} catch (DataAccessException e) {
			logger.error("queryByPagination(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "queryByPagination(...)", e.getMessage() });
		}

		return pageModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.dao.Iface.IEntityDao#findByExample(java.lang.String,
	 * com.neusoft.core.dao.Iface.IEntity)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) throws BaseException {
		String sqlName = fullStatementName("findByExample");
		if (logger.isDebugEnabled()) {
			logger.debug("findByExample(String, Object) - start \n[statementName:" + sqlName + "] [parameterObject:{"
					+ exampleInstance + "}]");
		}
		List<T> res = null;
		try {
			res = (List<T>) getSqlMapClientTemplate().queryForList(sqlName, exampleInstance);
		} catch (DataAccessException e) {
			logger.error("findByExample(...) has error," + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "findByExample(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("findByExample(String, Object) - end");
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#findByParams(java.lang.String,
	 * java.lang.Object)
	 */
	public List<T> findByParams(final String statementName, final Object parameterObject) throws BaseException {
		Assert.hasLength(statementName, "[p:statementName is empty]");
		List<T> list = null;
		try {
			list = (List<T>) getSqlMapClientTemplate().execute(new SqlMapClientCallback<List<T>>() {

				@SuppressWarnings("unchecked")
				public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					String sqlName = fullStatementName(statementName);
					if (logger.isDebugEnabled()) {
						logger.debug("findByParams(String, Object) - start \n[statementName:" + sqlName
								+ "] [parameterObject:{" + parameterObject + "}]");
					}
					return executor.queryForList(sqlName, parameterObject);
				}

			});
		} catch (DataAccessException e) {
			logger.error("findByExample(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "findByExample(...)", e.getMessage() });
		}
		if (logger.isDebugEnabled()) {
			logger.debug("query list size is [" + list == null ? 0 : list.size() + "]");
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#find(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String statementName) throws BaseException {
		String sqlName = fullStatementName(statementName);
		if (logger.isDebugEnabled()) {
			logger.debug("findByExample(String, Object) - start \n[statementName:" + sqlName + "] ");
		}
		List<T> res = null;
		try {
			res = (List<T>) getSqlMapClientTemplate().queryForList(sqlName);
		} catch (DataAccessException e) {
			logger.error("findByExample(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "findByExample(...)", e.getMessage() });
		}

		if (logger.isDebugEnabled()) {
			logger.debug("findByExample(String, Object) - end");
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.dao.Iface.IEntityDao#findForMap(java.lang.String,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findForMap(String statementName, Object parameterObject) throws BaseException {
		String sqlName = fullStatementName(statementName);
		List<Map<String, Object>> res = null;
		try {
			res = (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList(sqlName, parameterObject);
		} catch (Exception e) {
			logger.error("findForMap(...) has error" + e.getMessage());
			throw new BatisSqlException("0200", e, new String[] { "findForMap(...)", e.getMessage() });
		}
		return res;
	} // end_fun

	// -----------------------------------------------
	protected String getNameSpace() {
		return nameSpace;
	}

	/**
	 * {返回默认的 getById StatementName}
	 * 
	 * @return
	 */
	protected String getFindByIdStatementName() {
		return getNameSpace() + ".findById";
	}

	/**
	 * {返回默认的 insert StatementName}
	 * 
	 * @return
	 */
	protected String getInsertStatementName() {
		return getNameSpace() + ".insert";
	}

	/**
	 * {返回默认的 Update StatementName}
	 * 
	 * @return
	 */
	protected String getUpdateStatementName() {
		return getNameSpace() + ".update";
	}

	/**
	 * {返回默认的 Delete StatementName}
	 * 
	 * @return
	 */
	protected String getDeleteStatementName() {
		return getNameSpace() + ".delete";
	}

	abstract boolean isCached();

	/**
	 * 
	 * {组装 statementName 添加nameSpace 的限定}
	 * 
	 * @param statementName
	 * @return
	 */
	private String fullStatementName(String statementName) {
		Assert.hasLength(statementName, "fullStatementName(String statementName) statementName is noLength");
		return statementName.indexOf(".") == -1 ? getNameSpace() + "." + statementName : statementName;
	}

	private TransactionTemplate transactionTemplate;

	protected ICacheTemplate cacheTemplate;

	public void setCacheTemplate(ICacheTemplate cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	private String nameSpace;

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

}
