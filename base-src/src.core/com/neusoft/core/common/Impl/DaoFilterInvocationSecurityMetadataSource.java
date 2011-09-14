package com.neusoft.core.common.Impl;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.neusoft.core.common.Iface.IRefreshSecMetadata;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.InterceptUrlsImpl;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {通过DAO加载Acegi资源控制元数据} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 22, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class DaoFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,
		IRefreshSecMetadata {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DaoFilterInvocationSecurityMetadataSource.class);

	private static final Set<String> HTTP_METHODS = new HashSet<String>(Arrays.asList("DELETE", "GET", "HEAD",
			"OPTIONS", "POST", "PUT", "TRACE"));

	private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMap = new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	private boolean stripQueryStringFromUrls;

	private SqlMapClientTemplate sqlMapClientTemplate;

	private List<InterceptUrlsImpl> fixedInterceptUrls;

	// ~ Constructors
	// ===================================================================================================
	public DaoFilterInvocationSecurityMetadataSource() {
	}

	// ~ Methods
	// ========================================================================================================
	@SuppressWarnings("unchecked")
	protected void init() {
		List<InterceptUrlsImpl> interceptUrlsImpls = sqlMapClientTemplate.queryForList("SourceInfo.query_for_security");
		if (!CollectionUtils.isEmpty(interceptUrlsImpls)) {
			for (InterceptUrlsImpl urlsImpl : interceptUrlsImpls) {
				String pattern = urlsImpl.bulderPattern();
				String access = urlsImpl.bulderAccess();
				if (logger.isDebugEnabled()) {
					logger.debug("[InterceptUrls][pattern:'" + pattern + "'   access:'" + access + "']");
				}
				Collection<ConfigAttribute> attrs = SecurityConfig.createListFromCommaDelimitedString(access);
				addSecureUrl(pattern, null, attrs);
			} // end_for
		} else {
			logger.warn("<sec:intercept-url pattern=\"/console/**\" access=\"ROLE_ADMIN\" /> please init");
		}
		if (!CollectionUtils.isEmpty(fixedInterceptUrls)) {
			for (InterceptUrlsImpl urlsImpl : fixedInterceptUrls) {
				String pattern = urlsImpl.bulderPattern();
				String access = urlsImpl.bulderAccess();
				Collection<ConfigAttribute> attrs = SecurityConfig.createListFromCommaDelimitedString(access);
				addSecureUrl(pattern, null, attrs);
			} // end_for
		}
	}

	/**
	 * Adds a URL,attribute-list pair to the request map, first allowing the
	 * <tt>UrlMatcher</tt> to process the pattern if required, using its
	 * <tt>compile</tt> method. The returned object will be used as the key to
	 * the request map and will be passed back to the <tt>UrlMatcher</tt> when
	 * iterating through the map to find a match for a particular URL.
	 */
	private void addSecureUrl(String pattern, String method, Collection<ConfigAttribute> attrs) {
		Map<Object, Collection<ConfigAttribute>> mapToUse = getRequestMapForHttpMethod(method);

		mapToUse.put(urlMatcher.compile(pattern), attrs);

		if (logger.isDebugEnabled()) {
			logger.debug("Added URL pattern: " + pattern + "; attributes: " + attrs
					+ (method == null ? "" : " for HTTP method '" + method + "'"));
		}
	}

	/**
	 * Return the HTTP method specific request map, creating it if it doesn't
	 * already exist.
	 * 
	 * @param method
	 *            GET, POST etc
	 * @return map of URL patterns to <tt>ConfigAttribute</tt>s for this method.
	 */
	private Map<Object, Collection<ConfigAttribute>> getRequestMapForHttpMethod(String method) {
		if (method != null && !HTTP_METHODS.contains(method)) {
			throw new IllegalArgumentException("Unrecognised HTTP method: '" + method + "'");
		}

		Map<Object, Collection<ConfigAttribute>> methodRequestMap = httpMethodMap.get(method);

		if (methodRequestMap == null) {
			methodRequestMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
			httpMethodMap.put(method, methodRequestMap);
		}

		return methodRequestMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.SecurityMetadataSource#
	 * getAllConfigAttributes()
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<String, Map<Object, Collection<ConfigAttribute>>> entry : httpMethodMap.entrySet()) {
			for (Collection<ConfigAttribute> attrs : entry.getValue().values()) {
				allAttributes.addAll(attrs);
			}
		}

		return allAttributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) {
		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		String url = ((FilterInvocation) object).getRequestUrl();
		String method = ((FilterInvocation) object).getHttpRequest().getMethod();

		return lookupAttributes(url, method);
	}

	/**
	 * Performs the actual lookup of the relevant <tt>ConfigAttribute</tt>s for
	 * the given <code>FilterInvocation</code>.
	 * <p>
	 * By default, iterates through the stored URL map and calls the
	 * {@link UrlMatcher#pathMatchesUrl(Object path, String url)} method until a
	 * match is found.
	 * 
	 * @param url
	 *            the URI to retrieve configuration attributes for
	 * @param method
	 *            the HTTP method (GET, POST, DELETE...), or null for any
	 *            method.
	 * 
	 * @return the <code>ConfigAttribute</code>s that apply to the specified
	 *         <code>FilterInvocation</code> or null if no match is found
	 */
	public final Collection<ConfigAttribute> lookupAttributes(String url, String method) {
		if (stripQueryStringFromUrls) {
			// Strip anything after a question mark symbol, as per SEC-161. See
			// also SEC-321
			int firstQuestionMarkIndex = url.indexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}

		if (urlMatcher.requiresLowerCaseUrl()) {
			url = url.toLowerCase();
		}

		// Obtain the map of request patterns to attributes for this method and
		// lookup the url.
		Collection<ConfigAttribute> attributes = extractMatchingAttributes(url, httpMethodMap.get(method));

		// If no attributes found in method-specific map, use the general one
		// stored under the null key
		if (attributes == null) {
			attributes = extractMatchingAttributes(url, httpMethodMap.get(null));
		}

		return attributes;
	}

	private Collection<ConfigAttribute> extractMatchingAttributes(String url,
			Map<Object, Collection<ConfigAttribute>> map) {
		if (map == null) {
			return null;
		}

		for (Map.Entry<Object, Collection<ConfigAttribute>> entry : map.entrySet()) {
			boolean matched = urlMatcher.pathMatchesUrl(entry.getKey(), url);
			if (matched) {
				return entry.getValue();
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	protected UrlMatcher getUrlMatcher() {
		return urlMatcher;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return urlMatcher.requiresLowerCaseUrl();
	}

	public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
		this.stripQueryStringFromUrls = stripQueryStringFromUrls;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public void setFixedInterceptUrls(List<InterceptUrlsImpl> fixedInterceptUrls) {
		this.fixedInterceptUrls = fixedInterceptUrls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.core.common.Iface.IRefreshSecurityMetadataSource#
	 * refreshMetadataSource()
	 */
	public void refreshMetadataSource() throws BaseException {
		synchronized (httpMethodMap) {
			httpMethodMap.clear();
			init();
		}
	}

}
