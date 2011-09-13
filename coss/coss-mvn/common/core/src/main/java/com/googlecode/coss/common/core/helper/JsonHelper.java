package com.googlecode.coss.common.core.helper;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonHelper {
	/**
	 * 生成Json string
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 将JavaBean转换为JSONObject或者JSONArray
	 * */
	public static Object toJSON(Object object) {
		return JSON.toJSON(object);
	}

	/**
	 * 解析json字符串为对象集合
	 */
	public static <T> List<T> parseList(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * 解析json字符串为对象集合
	 */
	public static JSONArray parseArray(String json) {
		return JSON.parseArray(json);
	}

	/**
	 * 解析json字符串为对象
	 */
	public static Object parse(String json) {
		return JSON.parse(json);
	}

	/**
	 * 解析json字符串为指定类型对象
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * 解析json字符串为JSONObject对象
	 */
	public static JSONObject parseObject(String json) {
		return JSON.parseObject(json);
	}

	/**
	 * 处理异常
	 */
	public static void handleException(Throwable e) {
		e.printStackTrace();
		throw new RuntimeException(e.getMessage());
	}

	/*public static void main(String[] args) {
		String json = "[{\"contactName\":\"ccc\",\"gender\":\"eer\",\"position\":\"1001\",\"mobile\":\"123124\",\"workPhone\":\"532423\",\"email\":\"asdas@adf.com\",\"homePhone\":\"3231231\",\"qq\":\"323123\",\"msn\":\"asdfadsg\",\"other\":\"hhhh\"},{\"contactName\":\"ccc\",\"gender\":\"eer\",\"position\":\"1001\",\"mobile\":\"123124\",\"workPhone\":\"532423\",\"email\":\"asdas@adf.com\",\"homePhone\":\"3231231\",\"qq\":\"323123\",\"msn\":\"asdfadsg\",\"other\":\"hhhh\"}]";
		List<Contact> as = parseList(json, Contact.class);
		System.out.println(as.get(0).getContactName());
	}*/
}
