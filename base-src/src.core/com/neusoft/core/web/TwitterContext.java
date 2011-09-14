package com.neusoft.core.web;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TwitterContext {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TwitterContext.class);

	private static TwitterContext context = null;

	private TwitterContext() {
		counter = new ConcurrentHashMap<String, Integer>();
	}

	/**
	 * {新增一个登录用户tw 数量}
	 * 
	 * @param userId
	 * @param number
	 */
	public synchronized void updateTwitterNum(String userId, Integer number) {
		if (logger.isDebugEnabled()) {
			logger.debug("put [user:" + userId + "] in TwitterContext and Num is [" + number + "]");
		}
		counter.put(userId, number);
	}

	/**
	 * {移除一个tw 数量执针}
	 * 
	 * @param userId
	 */
	public synchronized void removeById(String userId) {
		counter.remove(userId);
		if (logger.isDebugEnabled()) {
			logger.debug("remove [user:" + userId + "] from TwitterContext success");
		}
	}

	/**
	 * {清空登记信息}
	 */
	public synchronized void clear() {
		counter.clear();
	}

	/**
	 * 获取当前数量 制定用户ID的
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getTwitterNum(String userId) {
		Integer res = 0;
		if (counter.containsKey(userId)) {
			res = counter.get(userId);
		} else {
			logger.warn("[user:" + userId + "] is not exits in TwitterContext");
		}
		return res;
	}

	/**
	 * {获取当前用户的 列表 显示}
	 * 
	 * @return
	 */
	public Set<String> getUserIds() {
		return counter.keySet();
	}

	/**
	 * {单例模式}
	 * 
	 * @return
	 */
	public synchronized static TwitterContext getInstance() {
		if (context == null) {
			context = new TwitterContext();
		}
		return context;
	}

	private Map<String, Integer> counter;

}
