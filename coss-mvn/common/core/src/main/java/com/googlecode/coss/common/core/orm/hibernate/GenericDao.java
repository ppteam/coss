package com.googlecode.coss.common.core.orm.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Hibernate通用泛型Dao
 * 
 * @author Azheng
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {

	// -------------------- 基本检索、增加、修改、删除操作 --------------------

	/** 根据主键获取实体。如果没有相应的实体，返回 null */
	public T get(PK id);

	/** 根据主键获取实体并加锁。如果没有相应的实体，返回 null */
	public T getWithLock(PK id, LockMode lock);

	/** 根据主键获取实体。如果没有相应的实体，抛出异常。 */
	public T load(PK id);

	/** 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。 */
	public T loadWithLock(PK id, LockMode lock);

	/** 获取全部实体。 */
	public List<T> loadAll();

	// public List<T> loadAllWithLock();

	/** 更新实体 */
	public void update(T entity);

	/** 更新实体并加锁 */
	public void updateWithLock(T entity, LockMode lock);

	/** 存储实体到数据库 */
	public void save(T entity);

	// public void saveWithLock();

	/** 增加或更新实体 */
	public void saveOrUpdate(T entity);

	/** 增加或更新集合中的全部实体 */
	public void saveOrUpdateAll(Collection<T> entities);

	/** 删除指定的实体 */
	public void delete(T entity);

	/** 加锁并删除指定的实体 */
	public void deleteWithLock(T entity, LockMode lock);

	/** 根据主键删除指定实体 */
	public void deleteByKey(PK id);

	/** 根据主键加锁并删除指定的实体 */
	public void deleteByKeyWithLock(PK id, LockMode lock);

	/** 删除集合中的全部实体 */
	public void deleteAll(Collection<T> entities);

	// -------------------- HSQL ----------------------------------------------

	/** 使用HSQL语句直接增加、更新、删除实体 */
	public int bulkUpdate(String queryString);

	/** 使用带参数的HSQL语句增加、更新、删除实体 */
	public int bulkUpdate(String queryString, Object[] values);

	/** 使用HSQL语句检索数据 */
	public List<T> find(String queryString);

	/** 使用带参数的HSQL语句检索数据 */
	public List<T> find(String queryString, Object[] values);

	/** 对实例的 非主键 非关联 非NULL 字段进行匹配查询 */
	public List<T> findByExample(T exampleEntity);

	/** 使用带命名的参数的HSQL语句检索数据 */
	public List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values);

	/** 使用命名的HSQL语句检索数据 */
	public List<T> findByNamedQuery(String queryName);

	/** 使用带参数的命名HSQL语句检索数据 */
	public List<T> findByNamedQuery(String queryName, Object[] values);

	/** 使用带命名参数的命名HSQL语句检索数据 */
	public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);

	/** 使用HSQL语句检索数据，返回 Iterator */
	public Iterator<T> iterate(String queryString);

	/** 使用带参数HSQL语句检索数据，返回 Iterator */
	public Iterator<T> iterate(String queryString, Object[] values);

	/** 关闭检索返回的 Iterator */
	public void closeIterator(Iterator<T> it);

	// -------------------------------- Criteria ------------------------------

	/** 创建与会话无关的检索标准对象 */
	public DetachedCriteria createDetachedCriteria();

	/** 创建与会话绑定的检索标准对象 */
	public Criteria createCriteria();

	/** 使用指定的检索标准检索数据 */
	public List<T> findByCriteria(DetachedCriteria criteria);

	/** 使用指定的检索标准检索数据，返回部分记录 */
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

	/** 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据 */
	public List<T> findEqualByEntity(T entity, String[] propertyNames);

	/** 使用指定的实体及属性(非主键)检索（满足属性 like 串实体值）数据 */
	public List<T> findLikeByEntity(T entity, String[] propertyNames);

	/** 使用指定的检索标准检索数据，返回指定范围的记录 */
	public Integer getRowCount(DetachedCriteria criteria);

	/** 使用指定的检索标准检索数据，返回指定统计值 */
	public Object getStatValue(DetachedCriteria criteria, String propertyName, String StatName);

	// -------------------------------- Others --------------------------------

	/** 加锁指定的实体 */
	public void lock(T entity, LockMode lockMode);

	/** 强制初始化指定的实体 */
	public void initialize(Object proxy);

	/** 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新） */
	public void flush();

	// ------------------------------------

	/** 取得对象的主键名 */
	public String getIdName();

	/** 为Criteria添加distinct transformer */
	public Criteria distinct(Criteria criteria);

	/** 为Query添加distinct transformer */
	public Query distinct(Query query);

	/** @see #initEntity(Object) */
	public void initEntity(List<T> entityList);

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,可实现新的函数,执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize
	 * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initEntity(T entity);

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions);

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public T findUnique(final Criterion... criterions);

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions);

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(String queryString, Map<String, ?> values);

	/** 获取全部对象 */
	public List<T> getAll();

	/** 获取全部对象,支持排序 */
	public List<T> getAll(String orderBy, boolean isAsc);

	/** 按属性查找对象列表,匹配方式为相等 */
	public List<T> findBy(String propertyName, Object value);

	/** 按属性查找唯一对象,匹配方式为相等 */
	public T findUniqueBy(String propertyName, Object value);

	/** 按id列表获取对象 */
	public List<T> findByIds(List<PK> ids);

	/**  */
	public int batchExecute(final String hql, final Object... values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @return 更新记录数.
	 */
	public int batchExecute(String hql, Map<String, ?> values);

	/**
	 * 根据查询HQL与参数列表创建Query对象. 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values);

}