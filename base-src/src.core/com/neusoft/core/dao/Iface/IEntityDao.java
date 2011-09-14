package com.neusoft.core.dao.Iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.JsonPageModel;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 针对单表操作的范型Dao接口定义 <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 17, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IEntityDao<T extends IEntity<PK>, PK extends Serializable> extends IDBCommons {

	/**
	 * 
	 * {保存指定的对象}
	 * 
	 * @param statementName
	 *            默认为 ${nameSpace}.insert
	 * @param saveInstance
	 * @return Ibatis insert 的返回引用
	 * @throws BaseException
	 */
	Object insert(String statementName, Object saveInstance) throws BaseException;

	/**
	 * {采用默认的 statementName 保存对象 def=insert}
	 * 
	 * @param saveInstance
	 * @return
	 * @throws BaseException
	 */
	PK insert(T saveInstance) throws BaseException;

	/**
	 * 
	 * {针对对象执行更新操作}
	 * 
	 * @param statementName
	 *            默认为 ${nameSpace}.update
	 * @param updateInstance
	 * @return int
	 * @throws BaseException
	 */
	Integer update(String statementName, Object updateInstance) throws BaseException;

	/**
	 * {通过 给定对象的Id 进行更新操作}
	 * 
	 * @param updateInstace
	 * @return
	 * @throws BaseException
	 */
	Integer updateById(T updateInstace) throws BaseException;

	/**
	 * 
	 * {删除指定条件的数据对象}
	 * 
	 * @param statementName
	 *            默认为 ${nameSpace}.delete
	 * @param parameterObject
	 * @return int
	 * @throws BaseException
	 */
	Integer delete(String statementName, Object parameterObject) throws BaseException;

	/**
	 * 
	 * {通过pk 查询指定的java 对象，如果查询失败则返回null，语法标识 ${nameSpace}.findById}
	 * 
	 * @param pk
	 * @return T
	 * @throws BaseException
	 */
	T get(PK pk) throws BaseException;

	/**
	 * 
	 * {通过pk 查询指定的java 对象，如果查询失败则返回null，语法标识 ${nameSpace}.findById}
	 * 
	 * @param pk
	 * @param nullThrows
	 *            查询失败是否抛出异常 T/F 抛出/反之则返回null
	 * @return T
	 * @throws BaseException
	 */
	T get(PK pk, boolean nullThrows) throws BaseException;

	/**
	 * 
	 * {通过指定的条件查询制定的对象，如果按照条件返回结果有多个，则抛出异常，查询失败则返回Null}
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @return
	 * @throws BaseException
	 */
	T get(String statementName, Object parameterObject) throws BaseException;

	/**
	 * {查询制定条件的 数据数量}
	 * 
	 * @param statementName
	 * @param exampleInstance
	 * @return
	 * @throws BaseException
	 */
	Long queryForCount(String statementName, Object exampleInstance) throws BaseException;

	/**
	 * 
	 * {通过指定的条件查询指定的Sql 执行SQL：findByExample}
	 * 
	 * @param exampleInstance
	 * @return
	 * @throws BaseException
	 */
	List<T> findByExample(T exampleInstance) throws BaseException;

	/**
	 * {制定任意条件的查询操作}
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @return
	 * @throws BaseException
	 */
	List<T> findByParams(String statementName, Object parameterObject) throws BaseException;

	/**
	 * 
	 * @param statementName
	 * @return
	 * @throws BaseException
	 */
	List<T> find(String statementName) throws BaseException;

	/**
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @return
	 * @throws BaseException
	 */
	<M extends Object> List<M> queryByParams(String statementName, Object parameterObject) throws BaseException;

	/**
	 * 
	 * @return
	 * @throws BaseException
	 */
	<M extends Object> M quertForSingle(String statementName, Object parameterObject) throws BaseException;

	/**
	 * {执行任何制定的查询，并且结果封装为 Map 类型}
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @throws BaseException
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findForMap(String statementName, Object parameterObject) throws BaseException;

	/**
	 * 
	 * @param pre_statementName
	 * @param parameterObject
	 * @return Map<String, Object>
	 * @throws BaseException
	 */
	JsonPageModel queryByPagination(String pre_statementName, Object parameterObject) throws BaseException;

} // end_interface
