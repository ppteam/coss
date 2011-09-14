package com.googlecode.coss.common.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.coss.common.core.orm.mybatis.BaseDataObject;
import com.googlecode.coss.common.core.orm.mybatis.Page;
import com.googlecode.coss.common.core.orm.mybatis.QueryRequest;
import com.googlecode.coss.common.core.orm.mybatis.SqlMapDao;

@Transactional
public abstract class BaseService<T extends BaseDataObject, PK extends Serializable> {

	protected abstract SqlMapDao<T, PK> getSqlMapDao();

	@Transactional(readOnly = true)
	protected T getById(PK id) throws DataAccessException {
		return (T) getSqlMapDao().getById(id);
	}

	@Transactional(readOnly = true)
	protected List<T> findAll() throws DataAccessException {
		return (List<T>) getSqlMapDao().findAll();
	}

	/** 根据id检查是否插入或是更新数据 */
	protected void saveOrUpdate(T entity) throws DataAccessException {
		getSqlMapDao().saveOrUpdate(entity);
	}

	/** 插入数据 */
	protected void save(T entity) throws DataAccessException {
		getSqlMapDao().save(entity);
	}

	protected void removeById(PK id) throws DataAccessException {
		getSqlMapDao().deleteById(id);
	}

	protected void removeByIds(PK[] ids) throws DataAccessException {
		getSqlMapDao().deleteByIds(ids);
	}

	protected void update(T entity) throws DataAccessException {
		getSqlMapDao().update(entity);
	}

	@Transactional(readOnly = true)
	protected Page<T> findPage(QueryRequest pr) {
		return getSqlMapDao().findPage(pr);
	}

}
