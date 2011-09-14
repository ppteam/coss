package com.neusoft.core.web;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.util.WebUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.BeanTools;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.util.DateUtil;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class AbstractController extends ChainSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractController.class);

	private String DEF_PREFIX = "param_";

	/**
	 * {通过反射返回制定的结果}
	 * 
	 * @param <T>
	 * @param request
	 * @param clazz
	 * @return
	 */
	protected <T> T request2Bean(ServletRequest request, Class<T> clazz, String prefix) throws BaseException {
		T instance = null;
		try {
			Map<String, Object> params = WebUtils.getParametersStartingWith(request, prefix == null ? DEF_PREFIX
					: prefix);
			if (!CollectionUtils.isEmpty(params)) {
				instance = BeanTools.instantiate(clazz);
				Iterator<Entry<String, Object>> itor = params.entrySet().iterator();
				while (itor.hasNext()) {
					Entry<String, Object> entry = itor.next();
					String value = entry.getValue().toString();
					BeanUtils.setProperty(instance, entry.getKey(), covert(value.split("[|]")));
				}
			}
		} catch (BeanInstantiationException e) {
			logger.error("instantiate class [" + clazz.getName() + "] has error");
			throw new BaseException("0000", e, null);
		} catch (Exception ee) {
			logger.error("instantiate class [" + clazz.getName() + "] has error");
			throw new BaseException("0000", ee, null);
		}
		return instance;
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private Object covert(String[] args) throws BaseException {
		Object res = null;
		if (args.length == 1) {
			res = args[0];
		} else {
			String type = args[1];
			if ("Date".equals(type)) {
				res = DateUtil.strToDate(args[0], args.length == 3 ? args[2] : null);
			} else if ("Int".equals(type)) {
				res = Integer.valueOf(args[0]);
			} else {
				res = args[0];
			}
		}
		return res;
	}

	/**
	 * {复制登录用户信息}
	 * 
	 * @param request
	 * @param context
	 * @throws BaseException
	 */
	protected void initContext(HttpServletRequest request, IContext context, ModelMap modelMap) throws BaseException {
		LoginUser loginUser = CommonUtils.getLoginUser(request);
		if (modelMap != null) {
			context.put(IContext.KEY_MODELMAP, modelMap);
		}
		context.putAll(loginUser);
	}

}
