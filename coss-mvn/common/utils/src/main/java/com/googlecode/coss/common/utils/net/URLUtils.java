/**
 * 
 */
package com.googlecode.coss.common.utils.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.coss.common.utils.lang.Charsets;
import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * URL String Operation
 * </p>
 */
public class URLUtils {

    // default charset name
    private static final String defaultCharSet   = Charsets.UTF_8;

    private static String       httpProtocolStr  = "http";
    private static String       httpsProtocolStr = "https";
    private static String       protocolSuffix   = "://";

    /**
     * <p>
     * eq URLEncoder.encode(String url, String charSetName), Exception
     * insensitive
     * </p>
     * 
     * @param url
     * @param charSetName
     * @return
     */
    public static String encode(String url, String charSetName) {
        try {
            String str = URLEncoder.encode(url, charSetName);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * eq URLEncoder.encode(String url, "utf-8"), Exception insensitive
     * </p>
     * 
     * @param url
     * @param charSetName
     * @return
     */
    public static String encode(String url) {
        return encode(url, defaultCharSet);
    }

    /**
     * <p>
     * eq URLDecoder.decode(String url, String charSetName), Exception
     * insensitive
     * </p>
     * 
     * @param url
     * @param charSetName
     * @return
     */
    public static String decode(String url, String charSetName) {
        try {
            return URLDecoder.decode(url, charSetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * eq UrlEncoder.encode(String url, "utf-8"), Exception insensitive
     * </p>
     * 
     * @param url
     * @param charSetName
     * @return
     */
    public static String decode(String url) {
        return decode(url, defaultCharSet);
    }

    /**
     * <p>
     * check if a url string starts with http:// & https://
     * </p>
     * 
     * @param url url string to check
     * @return <code>true</code> if url starts with http & https
     */
    public static boolean isUseHttpProtocol(String url) {
        if (StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(url)) {
            if (StringUtils.startsWithIgnoreCase(url, httpProtocolStr + protocolSuffix)) {
                return true;
            } else if (StringUtils.startsWithIgnoreCase(url, httpsProtocolStr + protocolSuffix)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * String fromUrlBase = "http://www.mozat.com.cn"; String toUrlBase =
     * "http://www.mozat.com"; String fromUrl =
     * "http://www.mozat.com.cn/abc/zz.jsp";
     * System.out.println(absoluteUrl(fromUrlBase,toUrlBase, fromUrl)); result:
     * http://www.mozat.com/abc/zz.jsp
     * 
     * @param fromBaseUrl
     * @param toBaseUrl
     * @param fromUrl
     * @return
     */
    public static String absoluteUrl(String fromBaseUrl, String toBaseUrl, String fromUrl) {
        int index = fromUrl.length() - fromBaseUrl.length();
        if (index == -1) {
            return fromUrl;
        } else {
            return toBaseUrl + fromUrl.substring(fromUrl.length() - index);
        }
    }

    /**
     * <p>
     * Get parameter from input http URL
     * </p>
     * <p>
     * etc. getParameterValue("age",
     * "http://www.unclepeng.com?name=unclepeng&age=26&like=girl") = 26
     * </p>
     * 
     * @param parameter
     * @param fullUrl
     * @return
     */
    public static String getParameterValue(String parameter, String fullUrl) {
        String[] paramValuePairs = StringUtils.split(fullUrl, '&', false);
        for (String str : paramValuePairs) {
            KeyValuePair pair = KeyValuePair.formString(str);
            if (pair != null && pair.getKey().equals(parameter)) {
                return URLUtils.decode(pair.getValue());
            }
        }
        return null;
    }

    public static List<KeyValuePair> getKeyValueListFromUrl(String fullUrl) {
        List<KeyValuePair> keyValues = new ArrayList<KeyValuePair>();
        String[] paramValuePairs = StringUtils.split(fullUrl, '&', false);
        for (String str : paramValuePairs) {
            KeyValuePair pair = KeyValuePair.formString(str);
            if (pair != null) {
                keyValues.add(pair);
            }
        }
        return keyValues;
    }
}
