package com.googlecode.coss.common.core.orm.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.googlecode.coss.common.core.helper.ReflectionHelper;

/**
 * AbsGenericDao 继承 HibernateDaoSupport，简单封装 HibernateTemplate 各项功能， 简化
 * Hibernate Dao 的编写。
 */
@SuppressWarnings("unchecked")
public abstract class BaseHibernateDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    // 实体类类型(由构造方法自动赋值)
    protected Class<T>          clazz;
    protected SessionFactory    sessionFactory;
    protected HibernateTemplate hibernateTemplate;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候Override本函数.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 构造方法，根据实例类自动获取实体类类型
    public BaseHibernateDao() {
        this.clazz = ReflectionHelper.getSuperClassGenricType(getClass());
    }

    // ------------------------------------

    // -------------------- 基本检索、增加、修改、删除操作 --------------------

    // 根据主键获取实体。如果没有相应的实体，返回 null。
    @Override
    public T get(PK id) {
        return (T) getHibernateTemplate().get(clazz, id);
    }

    // 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
    @Override
    public T getWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().get(clazz, id, lock);
        if (t != null) {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    // 根据主键获取实体。如果没有相应的实体，抛出异常。
    @Override
    public T load(PK id) {
        return (T) getHibernateTemplate().load(clazz, id);
    }

    // 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
    @Override
    public T loadWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().load(clazz, id, lock);
        if (t != null) {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    // 获取全部实体。
    @Override
    public List<T> loadAll() {
        return (List<T>) getHibernateTemplate().loadAll(clazz);
    }

    // loadAllWithLock() ?

    // 更新实体
    @Override
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    // 更新实体并加锁
    @Override
    public void updateWithLock(T entity, LockMode lock) {
        getHibernateTemplate().update(entity, lock);
        this.flush(); // 立即刷新，否则锁不会生效。
    }

    // 存储实体到数据库
    @Override
    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    // saveWithLock()

    // 增加或更新实体
    @Override
    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    // 增加或更新集合中的全部实体
    @Override
    public void saveOrUpdateAll(Collection<T> entities) {
        getHibernateTemplate().saveOrUpdateAll(entities);
    }

    // 删除指定的实体
    @Override
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 按id删除对象.
     */
    public void delete(final PK id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        // logger.debug("delete entity {},id is {}", clazz.getSimpleName(), id);
    }

    // 加锁并删除指定的实体
    @Override
    public void deleteWithLock(T entity, LockMode lock) {
        getHibernateTemplate().delete(entity, lock);
        this.flush(); // 立即刷新，否则锁不会生效。
    }

    // 根据主键删除指定实体
    @Override
    public void deleteByKey(PK id) {
        this.delete(this.load(id));
    }

    // 根据主键加锁并删除指定的实体
    @Override
    public void deleteByKeyWithLock(PK id, LockMode lock) {
        this.deleteWithLock(this.load(id), lock);
    }

    // 删除集合中的全部实体
    @Override
    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    // ----------------------------

    /*
     * 获取全部对象.
     */
    @Override
    public List<T> getAll() {
        return find();
    }

    /*
     * 获取全部对象,支持排序.
     */
    @Override
    public List<T> getAll(String orderBy, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderBy));
        } else {
            c.addOrder(Order.desc(orderBy));
        }
        return c.list();
    }

    /*
     * 按属性查找对象列表,匹配方式为相等.
     */
    @Override
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /*
     * 按属性查找唯一对象,匹配方式为相等.
     */
    @Override
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /*
     * 按id列表获取对象.
     */
    @Override
    public List<T> findByIds(List<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /*
     * 执行HQL进行批量修改/删除操作.
     */
    @Override
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /*
     * 执行HQL进行批量修改/删除操作.
     * @return 更新记录数.
     */
    @Override
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /*
     * 根据查询HQL与参数列表创建Query对象. 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * @param values 数量可变的参数,按顺序绑定.
     */
    @Override
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /*
     * 根据查询HQL与参数列表创建Query对象.
     * @param values 命名参数,按名称绑定.
     */
    @Override
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /*
     * 按Criteria查询对象列表.
     * @param criterions 数量可变的Criterion.
     */
    @Override
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /*
     * 按Criteria查询唯一对象.
     * @param criterions 数量可变的Criterion.
     */
    @Override
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /*
     * 根据Criterion条件创建Criteria. 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * @param criterions 数量可变的Criterion.
     */
    @Override
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(clazz);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /*
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,可实现新的函数,执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    @Override
    public void initEntity(T entity) {
        Hibernate.initialize(entity);
    }

    @Override
    public void initEntity(List<T> entityList) {
        for (T entity : entityList) {
            Hibernate.initialize(entity);
        }
    }

    /* 为Query添加distinct transformer */
    @Override
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /* 为Criteria添加distinct transformer */
    @Override
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /* 取得对象的主键名 */
    @Override
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
        return meta.getIdentifierPropertyName();
    }

    // ----------------------------

    // -------------------- HSQL ----------------------------------------------

    // 使用HSQL语句直接增加、更新、删除实体
    @Override
    public int bulkUpdate(String queryString) {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    // 使用带参数的HSQL语句增加、更新、删除实体
    @Override
    public int bulkUpdate(String queryString, Object[] values) {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    // 使用HSQL语句检索数据
    @Override
    public List<T> find(String queryString) {
        return getHibernateTemplate().find(queryString);
    }

    // 使用带参数的HSQL语句检索数据
    @Override
    public List<T> find(String queryString, Object[] values) {
        return getHibernateTemplate().find(queryString, values);
    }

    // 对实例的 非主键 非关联 非NULL 字段进行匹配查询
    @Override
    public List<T> findByExample(T exampleEntity) {
        return this.getHibernateTemplate().findByExample(exampleEntity);
    }

    // 使用带命名的参数的HSQL语句检索数据
    @Override
    public List<T> findByNamedParam(String queryString, String[] paramNames, Object[] values) {
        return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
    }

    // 使用命名的HSQL语句检索数据
    @Override
    public List<T> findByNamedQuery(String queryName) {
        return getHibernateTemplate().findByNamedQuery(queryName);
    }

    // 使用带参数的命名HSQL语句检索数据
    @Override
    public List<T> findByNamedQuery(String queryName, Object[] values) {
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    // 使用带命名参数的命名HSQL语句检索数据
    @Override
    public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames,
                                                 Object[] values) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    // 使用HSQL语句检索数据，返回 Iterator
    @Override
    public Iterator<T> iterate(String queryString) {
        return getHibernateTemplate().iterate(queryString);
    }

    // 使用带参数HSQL语句检索数据，返回 Iterator
    @Override
    public Iterator<T> iterate(String queryString, Object[] values) {
        return getHibernateTemplate().iterate(queryString, values);
    }

    // 关闭检索返回的 Iterator
    @Override
    public void closeIterator(Iterator<T> it) {
        getHibernateTemplate().closeIterator(it);
    }

    // -------------------------------- Criteria ------------------------------

    // 创建与会话无关的检索标准
    @Override
    public DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(this.clazz);
    }

    // 创建与会话绑定的检索标准
    @Override
    public Criteria createCriteria() {
        return this.createDetachedCriteria().getExecutableCriteria(this.getSession());
    }

    // 检索满足标准的数据
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    // 检索满足标准的数据，返回指定范围的记录
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
    @Override
    public List<T> findEqualByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        Example exam = Example.create(entity);
        exam.excludeZeroes();
        String[] defPropertys = getSessionFactory().getClassMetadata(clazz).getPropertyNames();
        for (String defProperty : defPropertys) {
            int ii = 0;
            for (ii = 0; ii < propertyNames.length; ++ii) {
                if (defProperty.equals(propertyNames[ii])) {
                    criteria.addOrder(Order.asc(defProperty));
                    break;
                }
            }
            if (ii == propertyNames.length) {
                exam.excludeProperty(defProperty);
            }
        }
        criteria.add(exam);
        return (List<T>) criteria.list();
    }

    // 使用指定的实体及属性检索（满足属性 like 串实体值）数据
    @Override
    public List<T> findLikeByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        for (String property : propertyNames) {
            try {
                Object value = PropertyUtils.getProperty(entity, property);
                if (value instanceof String) {
                    criteria.add(Restrictions.like(property, (String) value, MatchMode.ANYWHERE));
                    criteria.addOrder(Order.asc(property));
                } else {
                    criteria.add(Restrictions.eq(property, value));
                    criteria.addOrder(Order.asc(property));
                }
            } catch (Exception ex) {
                // 忽略无效的检索参考数据。
            }
        }
        return (List<T>) criteria.list();
    }

    // 使用指定的检索标准获取满足标准的记录数
    @Override
    public Integer getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        List<T> list = this.findByCriteria(criteria, 0, 1);
        return (Integer) list.get(0);
    }

    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
    @Override
    public Object getStatValue(DetachedCriteria criteria, String propertyName, String StatName) {
        if (StatName.toLowerCase().equals("max"))
            criteria.setProjection(Projections.max(propertyName));
        else if (StatName.toLowerCase().equals("min"))
            criteria.setProjection(Projections.min(propertyName));
        else if (StatName.toLowerCase().equals("avg"))
            criteria.setProjection(Projections.avg(propertyName));
        else if (StatName.toLowerCase().equals("sum"))
            criteria.setProjection(Projections.sum(propertyName));
        else
            return null;
        List<T> list = this.findByCriteria(criteria, 0, 1);
        return list.get(0);
    }

    // -------------------------------- Others --------------------------------

    // 加锁指定的实体
    @Override
    public void lock(T entity, LockMode lock) {
        getHibernateTemplate().lock(entity, lock);
    }

    // 强制初始化指定的实体
    @Override
    public void initialize(Object proxy) {
        getHibernateTemplate().initialize(proxy);
    }

    // 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    /**
     * 按HQL查询对象列表.
     * 
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

}
