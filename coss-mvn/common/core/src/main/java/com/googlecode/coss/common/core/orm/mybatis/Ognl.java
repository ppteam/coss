package com.googlecode.coss.common.core.orm.mybatis;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * mybatis mapper.xml 解析加强工具 一些常用判断
 */
@SuppressWarnings({ "rawtypes" })
public class Ognl {

    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null)
            return false;
        if (o instanceof Number) {
            return true;
        }
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0)
                return false;
            if (str.trim().length() == 0)
                return false;
            return StringUtils.isNumeric(str);
        }
        return false;
    }

    public static boolean isBlank(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            String str = (String) o;
            return isBlank(str);
        }
        return false;
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
