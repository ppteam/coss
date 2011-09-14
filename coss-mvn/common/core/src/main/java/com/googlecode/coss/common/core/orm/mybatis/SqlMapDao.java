package com.googlecode.coss.common.core.orm.mybatis;

import java.io.Serializable;
import java.util.List;

public interface SqlMapDao<T, PK extends Serializable> {

    /** 查询主键返回唯一对象 */
    public T getById(PK id);

    public T findUnique(Object param);

    /** 查询所有记录数 */
    public Long getCount();

    /** 根据筛选条件查询记录数 */
    public Long getCount(Object filters);

    /** 查询SQL返回唯一对象 */
    // public T selectOne(String sql);

    /** 查询绑定参数的SQL返回唯一对象 */
    // public T selectOne(String sql, Object param);

    /** 根据主键删除持久化对象 */
    public void deleteById(PK id);

    /** 删除此持久化对象 */
    public void deleteByIds(PK[] entity);

    /** 删除此持久化对象 */
    public void delete(T entity);

    /** 持久化此对象 */
    public void save(T entity);

    /** 更新持久化此对象 */
    public void update(T entity);

    /** 保存或更新持久化对象 */
    public void saveOrUpdate(T entity);

    /** 刷新新持久化此对象状态 */
    public void flush();

    /**
     * 查找所有持久化此对象 并排序
     * 
     * @param sortConditions 排序参数数组 例： String[]{user_age desc, user_level asc}
     * @return
     */
    public List<T> findAll(String... sortConditions);

    /**
     * 通过查询请求对象查找持久化此对象 并排序
     * 
     * @param queryRequest
     * @return
     */
    public List<T> find(QueryRequest queryRequest);

    /**
     * 通过过滤请对象查找所有持久化此对象 并排序
     * 
     * @param filter
     * @param sortConditions
     * @return
     */
    public List<T> find(Object filter, String... sortConditions);

    /**
     * 分页查找
     * 
     * @param queryRequest 分页参数,查询条件,排序条件
     * @return
     */
    public Page<T> findPage(QueryRequest queryRequest);

    /**
     * 分页查找 使用自定义语句 *非1对1复杂嵌套结ResultMapper无法正常分页
     * 
     * @param findPageStatement 查询语句
     * @param queryRequest 分页参数,查询条件,排序条件
     * @return
     */
    public Page<T> findPage(String findPageStatement, QueryRequest queryRequest);

    /**
     * 分页查找 使用自定义语句 和自定义记录数查询语句 *非1对1复杂嵌套结ResultMapper无法正常分页
     * 
     * @param findPageStatement
     * @param countStatement
     * @param queryRequest 分页参数,查询条件,排序条件
     * @return
     */
    public Page<T> findPage(String findPageStatement, String countStatement,
                            QueryRequest queryRequest);

}
