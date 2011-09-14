package com.neusoft.core.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.util.WebUtils;

import com.neusoft.core.chain.Iface.IFilter;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.LoginUser;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {常用工具集合} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 6, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class CommonUtils {

	/**
	 * {随机生成UUID 32 位}
	 * 
	 * @return UUID 序列
	 */
	public static String getUUIDSeq() {
		String id = UUID.randomUUID().toString();
		return id.replaceAll("[-]", "");
	}

	/**
	 * {获取制定Filter 的结果}
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T extends IFilter> String getDefResultKey(Class<T> clazz) {
		return clazz.getSimpleName() + IFilter.RES_SUFFIX;
	}

	/**
	 * {获取当前登录用户信息}
	 * 
	 * @return
	 * @throws BaseException
	 */
	public static LoginUser getLoginUser(HttpServletRequest request) throws BaseException {
		SecurityContext securityContext = (SecurityContext) WebUtils.getSessionAttribute(request,
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (securityContext == null) {
			throw new BaseException("0003", new NullPointerException("login timeOut"), null);
		}
		Object principal = securityContext.getAuthentication().getPrincipal();
		if (principal != null && principal instanceof LoginUser) {
			return (LoginUser) principal;
		} else {
			throw new BaseException("0003", new NullPointerException(), null);
		}
	}

}
