package com.neusoft.core.chain.general;

import org.apache.log4j.Logger;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.BatisSqlException;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {抽象的查询数据库过滤节点} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractQueryFilter extends SqlMapClientSupportFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractQueryFilter.class);

	private static final long serialVersionUID = -7602343997875348529L;

	public AbstractQueryFilter() {
	}

	/**
	 * {校验入参 以及返回实际参数指针}
	 * 
	 * @param context
	 * @return
	 */
	protected Object verifyQueryParam(IContext context) throws BaseException {
		Object query_param = null;
		// check out statementName
		if (!StringUtils.hasLength(statementName)) {
			throw new BatisSqlException("0004", new NullPointerException(),
					new String[] { statementName, statementName });
		}
		// check out is null
		if (notNull) {
			if (paramKey == null) {
				throw new BatisSqlException("0004", new NullPointerException(), new String[] { "paramKey", "paramKey" });
			}
			String[] keys = paramKey.split("[.]");
			Object _ioc = context;
			for (int i = 0; i < keys.length; i++) {
				_ioc = getValueByKey(_ioc, keys[i]);
				if (_ioc == null) {
					throw new BatisSqlException("0004", new NullPointerException(), new String[] { statementName,
							keys[0] });
				}
			}
			query_param = _ioc;
		} else {
			if (StringUtils.hasLength(paramKey)) {
				String[] keys = paramKey.split("[.]");
				Object _ioc = context;
				for (int i = 0; i < keys.length; i++) {
					_ioc = getValueByKey(_ioc, keys[i]);
					if (_ioc == null) {
						break;
					}
				}
				query_param = _ioc;
			}
		}
		return query_param;
	}

	/**
	 * {从制定的容器中获取制定key对应的属性}
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Object getValueByKey(Object context, String key) throws BaseException {
		Assert.notNull(context, "AbstractQueryFilter.getPValue (Object context ...) is not null");
		Assert.notNull(context, "AbstractQueryFilter.getPValue (....,String key) is not null");
		Object res = null;
		if (KEY_CONTEXT_SELF.equals(key)) {
			res = context;
		} else if (context instanceof Map) {
			if (context instanceof IContext) {
				res = ((IContext) context).getValueByAll(key);
			} else {
				res = ((Map) context).get(key);
			}
		} else {
			try {
				res = PropertyAccessorFactory.forBeanPropertyAccess(context).getPropertyValue(key);
			} catch (BeansException e) {
				logger.error("getValue by [" + key + "] form [" + context.getClass() + "] has error");
				throw new BaseException("0001", e, new String[] { key, context.getClass().getSimpleName() });
			}
		}
		return res;
	}

	// 对应配置到Ibatis 中的sql id （全路径）
	protected String statementName;
	// 在上下文中的Key值
	protected String paramKey;
	// 入参是否可以为空值 T：不可以为null /F 可以为空值
	protected boolean notNull = false;
	// 返回结果封装
	protected String resultKey;

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public static String KEY_CONTEXT_SELF = "ctxself";
}
