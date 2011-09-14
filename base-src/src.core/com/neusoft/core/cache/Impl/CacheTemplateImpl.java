package com.neusoft.core.cache.Impl;

import org.apache.log4j.Logger;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.neusoft.core.cache.Iface.ICacheTemplate;
import com.neusoft.core.common.Impl.ErrorHolderUtil;
import com.neusoft.core.exception.CacheAccessException;

/**
 * 
 * <b>Application name:</b> 工时管理系统 <br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 2, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class CacheTemplateImpl implements ICacheTemplate, InitializingBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CacheTemplateImpl.class);

	public CacheTemplateImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.cache.Iface.ICacheAccess#put(java.lang.String,
	 * java.io.Serializable)
	 */
	public void put(String cacheKey, Serializable cacheInstance) throws CacheAccessException {
		if (hasCacheKey(cacheKey)) {
			if (logger.isDebugEnabled()) {
				logger.debug("[cache key :" + cacheKey + "] has in cache [" + getCache().getName()
						+ "]");
			}
		} else {
			getCache().put(creatElement(cacheKey, cacheInstance));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.cache.Iface.ICacheAccess#get(java.lang.String,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(String cacheKey, Class<T> clazz) {
		T res = null;
		Element element = getCache().get(cacheKey);
		if (element != null) {
			res = (T) element.getValue();
		}
		return res;
	}

	public Serializable update(String cacheKey, Serializable cacheInstance) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.cache.Iface.ICacheAccess#hasCacheKey(java.lang.String)
	 */
	public boolean hasCacheKey(String cacheKey) {
		return getCache().isKeyInCache(cacheKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.cache.Iface.ICacheAccess#remove(java.lang.String)
	 */
	public boolean remove(String cacheKey) {
		return getCache().remove(cacheKey);
	}

	/**
	 * 
	 * {方法描述}
	 * 
	 * @param key
	 * @param cacheInstance
	 * @return
	 */
	protected Element creatElement(String key, Serializable cacheInstance) {
		Assert.hasLength(key, "Cache's Element key is empty");
		Assert.isInstanceOf(Serializable.class, cacheInstance,
				"Cache's Element cacheInstance must implements Serializable");
		Element element = new Element(key, cacheInstance);
		return element;
	}

	// ----------------------------getter and setter ---------------------------
	private Cache cache;

	private CacheManager cacheManager;

	private String cacheName;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return cache;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		try {
			if (this.cache == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("cache is null, so init cache from cacheManager ["
							+ cacheManager.getName() + "] and cacheName [" + this.cacheName + "]");
				}
				Assert.notNull(this.cacheManager, "cacheManager cann't be null");
				Assert.hasLength(this.cacheName, "cacheName is empty,please check config files");
				Cache _cache = cacheManager.getCache(cacheName);
				if (_cache == null) {
					throw new CacheAccessException("0000", CacheAccessException.EXP_TYPE_ERROR,
							null, null, new String[] { cacheName,
									"[" + cacheName + "] not mappiing any cache" });
				}
				setCache(_cache);
			}
		} catch (Exception e) {
			if (ErrorHolderUtil.isNotBaseException(e)) {
				throw new CacheAccessException("0000", CacheAccessException.EXP_TYPE_ERROR, null,
						null, new String[] { cacheName, e.getMessage() });
			}
		}
	}
}
