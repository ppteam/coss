package com.neusoft.core.cache.Iface;

import java.io.Serializable;

import com.neusoft.core.exception.CacheAccessException;

/**
 * 
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {缓冲操作接口定义:Cache is threadsafe} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 26, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface ICacheTemplate {

	/**
	 * {制定对象注入缓冲容器，除入参异常之外，cache 异常将被该方法隔离}
	 * 
	 * @param cacheKey
	 *            not null or empty
	 * @param cacheInstance
	 *            not null
	 */
	void put(String cacheKey, Serializable cacheInstance) throws CacheAccessException;

	/**
	 * {通过cacheKey 获取缓存制定的对象}
	 * 
	 * @param cacheKey
	 * @return Serializable
	 */
	<T extends Serializable> T get(String cacheKey, Class<T> clazz);

	/**
	 * 
	 * {方法描述}
	 * 
	 * @param cacheKey
	 * @param cacheInstance
	 * @return
	 */
	Serializable update(String cacheKey, Serializable cacheInstance);

	/**
	 * {当前缓冲中是否存在 cacheKey }
	 * 
	 * @param cacheKey
	 * @return
	 */
	boolean hasCacheKey(String cacheKey);

	/**
	 * 
	 * {移除指定key的缓冲对象}
	 * 
	 * @param cacheKey
	 * @return boolean
	 */
	boolean remove(String cacheKey);
}
