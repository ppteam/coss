package com.neusoft.core.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.springsource.util.common.CollectionUtils;
import com.springsource.util.common.StringUtils;

public abstract class WebTools extends WebUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WebTools.class);

    /**
     * {请求参数转换为Map}
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static void request2Map(ServletRequest request, IContext context) throws BaseException {
        Map<String, Object> instance = new HashMap<String, Object>();
        Map<String, Object> intMap = getParametersStartingWith(request, "int_");
        Map<String, Object> bisMap = getParametersStartingWith(request, "bis_");
        Map<String, Object> dtMap = getParametersStartingWith(request, "date_");
        if (!CollectionUtils.isEmpty(dtMap)) {
            Set<Entry<String, Object>> entrySet = dtMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                String date_str = (String) entry.getValue();
                if (logger.isDebugEnabled()) {
                    logger.debug("put Date to Map[key:" + entry.getKey() + ",value:" + date_str + "]");
                }
                instance.put(entry.getKey(), DateUtil.strToDate(date_str, null));
            }
        }

        if (!CollectionUtils.isEmpty(bisMap)) {
            Set<Entry<String, Object>> entrySet = bisMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                String bis_st = (String) entry.getValue();
                if (logger.isDebugEnabled()) {
                    logger.debug("put int to Map[key:" + entry.getKey() + ",value:" + entry.getValue() + "]");
                }
                String[] params = bis_st.split("[|]");
                int n = params.length == 2 ? Integer.valueOf(params[1]) : 7;
                Map<String, Date> dateMap = DateUtil.start2end(DateUtil.strToDate(params[0], "yyyy-MM-dd"), n);
                instance.put("KEY_START", dateMap.get(DateUtil.KEY_START));
                instance.put("KEY_END", dateMap.get(DateUtil.KEY_END));
            }
        }
        if (!CollectionUtils.isEmpty(intMap)) {
            Set<Entry<String, Object>> entrySet = intMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                String int_str = (String) entry.getValue();
                if (logger.isDebugEnabled()) {
                    logger.debug("put int to Map[key:" + entry.getKey() + ",value:" + entry.getValue() + "]");
                }
                instance.put(entry.getKey(), NumberUtils.createInteger(int_str.toString()));
            }
        }
        Map<String, Object> listMap = getParametersStartingWith(request, "list_");
        if (!CollectionUtils.isEmpty(listMap)) {
            Set<Entry<String, Object>> entrySet = listMap.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                String list_str = (String) entry.getValue();
                if (StringUtils.hasLength(list_str)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("put list to Map[key:" + entry.getKey() + ",value:" + entry.getValue() + "]");
                    }
                    instance.put(entry.getKey(), Arrays.asList(list_str.split("[|]")));
                }
            }
        }
        try {
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                if (logger.isDebugEnabled()) {
                    logger.debug("putReqMap[key:" + name + ",value:" + findParameterValue(request, name) + "]");
                }
                instance.put(name, findParameterValue(request, name));
            }
            context.putAll(instance);
        } catch (Exception ee) {
            logger.error("request2Map has error");
        }
    }
}
