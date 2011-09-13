package com.googlecode.coss.common.utils.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.googlecode.coss.common.utils.lang.BytesUtils;
import com.googlecode.coss.common.utils.lang.Charsets;
import com.googlecode.coss.common.utils.lang.StringUtils;

public class HttpClient {

	private boolean showHeaderResponse = false;

	private static final Logger logger = Logger.getLogger("httpClientLog");

	// default time out setting , half minute
	private static final int defaultTimeOut = 30 * 1000;
	// default CHARSET
	private static final String defaultCharset = Charsets.UTF_8;

	private static void validateUrl(String url) {
		if (!URLUtils.isUseHttpProtocol(url)) {
			throw new java.lang.IllegalArgumentException(String.format("The URL %s is illegal", url));
		}
	}

	// store all cookies
	private Map<String, Cookie> cookies = new HashMap<String, Cookie>();

	// response header each request
	private Map<String, List<String>> responseHeaders;

	private void outPutAllHeaders() {
		if (this.responseHeaders == null || this.showHeaderResponse == false) {
			return;
		}
		Set<Entry<String, List<String>>> set = responseHeaders.entrySet();
		for (Entry<String, List<String>> s : set) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.getKey());
			sb.append(" = ");
			sb.append("[");
			List<String> values = s.getValue();
			if (values != null) {
				for (int i = 0; i < values.size(); i++) {
					sb.append(values.get(i));
					sb.append(',');
				}
				if (values.size() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
			}
			sb.append("]");
			logger.info(sb.toString());
		}
	}

	private String genCookieStrToSend() {
		if (cookies.size() == 0) {
			return null;
		} else {
			Collection<Cookie> cs = this.cookies.values();
			StringBuilder sb = new StringBuilder();
			for (Cookie cookie : cs) {
				sb.append(cookie.getName());
				sb.append("=");
				sb.append(cookie.getValue());
				sb.append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
	}

	private void setCookieHeaderToSend(URLConnection con) {
		if (con == null) {
			return;
		}
		String cookieStr4Request = genCookieStrToSend();
		if (cookieStr4Request != null) {
			con.setRequestProperty("Cookie", cookieStr4Request);
		}
	}

	private void setHeadersToSend(URLConnection con, Map<String, String> headers) {
		if (con == null || headers == null) {
			return;
		}
		Set<Entry<String, String>> sets = headers.entrySet();
		for (Entry<String, String> entry : sets) {
			con.addRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	private void storeCookies() {
		List<String> headers = getHeader("Set-Cookie");
		if (headers == null) {
			return;
		}
		for (String str : headers) {
			Cookie cookie = Cookie.fromStr(str);
			if (cookie != null) {
				this.cookies.put(cookie.getKey(), cookie);
			}
		}
	}

	private List<String> getHeader(String headerName) {
		if (responseHeaders == null) {
			return null;
		}
		Set<Entry<String, List<String>>> set = responseHeaders.entrySet();
		for (Entry<String, List<String>> s : set) {
			if (headerName.equalsIgnoreCase(s.getKey())) {
				return s.getValue();
			}
		}
		return null;
	}

	private String getCharSetHeader() {
		List<String> contentType = getHeader("Content-Type");
		if (contentType == null) {
			return null;
		}
		for (String str : contentType) {
			String[] strs = StringUtils.split(str, ';', true);
			for (String s : strs) {
				if (StringUtils.startsWithIgnoreCase(s, "charset")) {
					KeyValuePair kvp = KeyValuePair.formString(s);
					return kvp.getValue();
				}
			}
		}
		return null;
	}

	public String doGet(String url) throws RequestException {
		return doGet(url, null, defaultTimeOut);
	}

	public String doGet(String url, String charSetName) throws RequestException {
		return doGet(url, charSetName, defaultTimeOut);
	}

	public String doGet(String url, String charSetName, int timeOut) throws RequestException {
		return doGet(url, charSetName, defaultTimeOut, null);
	}

	/**
	 * <p>
	 * Do HTTP GET Request
	 * </p>
	 * 
	 * @param url
	 *            the HTTP URL for Request
	 * @param charSetName
	 *            char set name etc. UTF_8,GB3212
	 * @param timeOut
	 *            time out setting
	 * @return response stream
	 * @throws RequestException
	 */
	public String doGet(String url, String charSetName, int timeOut, Map<String, String> headers)
			throws RequestException {
		validateUrl(url);
		try {
			URL ur = new URL(url);
			URLConnection con = ur.openConnection();
			con.setConnectTimeout(timeOut);
			con.setReadTimeout(timeOut);
			setCookieHeaderToSend(con);
			setHeadersToSend(con, headers);
			this.responseHeaders = con.getHeaderFields();
			outPutAllHeaders();
			storeCookies();
			if (charSetName == null) {
				String currentCharSetName = getCharSetHeader();
				if (StringUtils.isNotBlank(currentCharSetName)) {
					charSetName = currentCharSetName;
				} else {
					charSetName = defaultCharset;
				}
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), charSetName));
			StringBuilder sb = new StringBuilder();
			try {
				int k = rd.read();
				while (k != -1) {
					sb.append((char) k);
					k = rd.read();
				}
			} catch (Exception ee) {
			} finally {
				if (rd != null) {
					rd.close();
				}
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RequestException(e);
		}
	}

	public String doPost(String url) throws RequestException {
		return doPost(url, null);
	}

	public String doPost(String url, Map<String, String> parameters) throws RequestException {
		return doPost(url, parameters, null);
	}

	public String doPost(String url, Map<String, String> parameters, String charSetName) throws RequestException {
		return doPost(url, parameters, defaultTimeOut, charSetName);
	}

	public String doPost(String url, Map<String, String> parameters, int timeOut, String charSetName)
			throws RequestException {
		return doPost(url, parameters, timeOut, charSetName, null);
	}

	public String doPost(String url, Map<String, String> parameters, int timeOut, String charSetName,
			Map<String, String> headers) throws RequestException {
		// validate
		validateUrl(url);

		// generate post data form parameters
		StringBuilder sb = new StringBuilder();
		if (parameters != null) {
			for (Map.Entry<String, String> kv : parameters.entrySet()) {
				sb.append(kv.getKey());
				sb.append("=");
				sb.append(URLUtils.decode(kv.getValue()));
				sb.append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		byte[] postData = BytesUtils.toBytes(sb);
		try {
			URL ur = new URL(url);
			URLConnection con = ur.openConnection();

			// setting
			con.setConnectTimeout(timeOut);
			con.setReadTimeout(timeOut);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			con.setRequestProperty("Content-Length", postData.length + "");
			setCookieHeaderToSend(con);
			setHeadersToSend(con, headers);
			OutputStream os = con.getOutputStream();
			os.write(postData);
			os.flush();
			os.close();
			this.responseHeaders = con.getHeaderFields();
			outPutAllHeaders();
			storeCookies();
			if (charSetName == null) {
				String currentCharSetName = getCharSetHeader();
				if (StringUtils.isNotBlank(currentCharSetName)) {
					charSetName = currentCharSetName;
				} else {
					charSetName = defaultCharset;
				}
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), charSetName));
			StringBuilder rsb = new StringBuilder();
			try {
				int k = rd.read();
				while (k != -1) {
					rsb.append((char) k);
					k = rd.read();
				}
			} catch (Exception ee) {
			} finally {
				try {
					rd.close();
				} catch (Exception e) {

				}
			}
			return rsb.toString();
		} catch (Exception e) {
			throw new RequestException(e);
		}
	}

	public boolean isShowHeaderResponse() {
		return showHeaderResponse;
	}

	public void setShowHeaderResponse(boolean showHeaderResponse) {
		this.showHeaderResponse = showHeaderResponse;
	}
}
