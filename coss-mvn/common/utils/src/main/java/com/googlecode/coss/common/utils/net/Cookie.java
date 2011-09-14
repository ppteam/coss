package com.googlecode.coss.common.utils.net;

import com.googlecode.coss.common.utils.lang.StringUtils;
import com.googlecode.coss.common.utils.lang.builder.ToStringBuilder;

public class Cookie {

    private String name;

    private String value;

    private String path;

    private String domain;

    private String expires;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return "name=" + this.name + ",domain=" + this.domain + ",path=" + this.path;
    }

    public static Cookie fromStr(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        Cookie cookie = new Cookie();
        String[] keyValues = StringUtils.split(str, ';', true);
        for (String keyValue : keyValues) {
            KeyValuePair kvp = KeyValuePair.formString(keyValue);
            if (kvp == null || kvp.getKey() == null) {
                continue;
            }
            if (kvp.getKey().equalsIgnoreCase("path")) {
                cookie.path = kvp.getValue();
            } else if (kvp.getKey().equalsIgnoreCase("expires")) {
                cookie.expires = kvp.getValue();
            } else if (kvp.getKey().equalsIgnoreCase("domain")) {
                cookie.domain = kvp.getValue();
            } else {
                cookie.name = kvp.getKey();
                cookie.value = kvp.getValue();
            }
        }
        return cookie;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(String[] args) {
        String str = "_user=deleted; expires=Mon, 24-Aug-2009 06:39:14 GMT; path=/; domain=.kaixin001.com";
        System.out.println(fromStr(str));
    }

    public String getExpireDate() {
        return expires;
    }

    public void setExpireDate(String expireDate) {
        this.expires = expireDate;
    }
}
