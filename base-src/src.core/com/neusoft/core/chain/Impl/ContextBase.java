package com.neusoft.core.chain.Impl;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.ChainBaseException;
import com.neusoft.core.web.JsonPageModel;

public class ContextBase extends HashMap<String, Object> implements IContext {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContextBase.class);

	private static final long serialVersionUID = -1790226382425350395L;

	public ContextBase() {
		super();
	}

	public ContextBase(Map<String, Object> map) {
		super(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#putResults(java.lang.String,
	 * java.lang.Object)
	 */
	public void putResults(String key, Object result) {
		resultMap.put(key, result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		Assert.hasLength(key, "[p:key] not empty");
		return super.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getInteger(java.lang.String)
	 */
	public Integer getInteger(String key) throws BaseException {
		String _formatKey = formatKey(key);
		if (containsKey(_formatKey)) {
			if (get(_formatKey) instanceof Integer) {
				return getObject(_formatKey, Integer.class);
			} else if (get(_formatKey) instanceof String) {
				if (NumberUtils.isNumber(get(_formatKey).toString())) {
					return NumberUtils.createInteger(get(_formatKey).toString());
				} else {
					logger.error("[value:" + get(_formatKey).toString() + "] cann't to int");
					throw new BaseException("0000", new IllegalArgumentException(),
							new String[] { key });
				}
			} else {
				logger.error("[value:" + get(_formatKey).toString() + "] cann't to int");
				throw new BaseException("0000", new IllegalArgumentException(),
						new String[] { key });
			}
		} else {
			logger.error("[key:" + key + "] is not in Context");
			throw new ChainBaseException("0003", new NullPointerException(), new String[] { key });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getString(java.lang.String)
	 */
	public String getString(String key) throws BaseException {
		String _formatKey = formatKey(key);
		if (containsKey(_formatKey)) {
			if (get(_formatKey) instanceof String) {
				return get(_formatKey).toString();
			} else {
				throw new BaseException("0004", new ClassCastException(), new String[] {
						get(key).getClass().getName(), String.class.getName(), key });
			}
		}
		return null;
	}

	/**
	 * {对传入的key进行格式化操作}
	 * 
	 * @param key
	 * @return
	 */
	private String formatKey(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		} else if (key instanceof String) {
			return ((String) key);
		} else {
			return (key.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getObject(java.lang.String,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(String Key, Class<T> clazz) {
		String _formatKey = formatKey(Key);
		Object resObj = get(_formatKey);
		if (resObj != null) {
			Assert.isInstanceOf(clazz, resObj);
		}
		return (T) resObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#hasError()
	 */
	public boolean hasError() {
		return containsKey(KEY_ERROR) ? (Boolean) get(KEY_ERROR) : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getMessage()
	 */
	public String getMessage() {
		return (String) get(KEY_MESSAGE);
	}

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getResultMap()
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.chain.Iface.IContext#getJsonModel()
	 */
	public JsonPageModel getJsonModel() {
		if (jsonPageModel == null) {
			jsonPageModel = JsonPageModel.getInstance();
		}
		return jsonPageModel;
	}

	private JsonPageModel jsonPageModel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.IContext#getValueByAll(java.lang.String)
	 */
	public Object getValueByAll(String key) {
		if (containsKey(key)) {
			return get(key);
		} else if (resultMap.containsKey(key)) {
			return resultMap.get(key);
		}
		return null;
	}

}
