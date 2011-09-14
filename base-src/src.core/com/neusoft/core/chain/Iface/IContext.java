package com.neusoft.core.chain.Iface;

import java.util.Map;

import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.JsonPageModel;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> 上下文接口声明 <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-10-26<br>
 * 
 * @author HXJ
 * @version $Revision$
 */
public interface IContext extends Map<String, Object> {

	/**
	 * {返回制定类型的对象，否则抛出对象异常}
	 * 
	 * @param <T>
	 * @param Key
	 * @param clazz
	 * @return
	 */
	<T extends Object> T getObject(String Key, Class<T> clazz);

	/**
	 * {获取Key对应的值，如果不是 String 类型则抛出异常}
	 * 
	 * @param key
	 * @return String
	 * @throws BaseException
	 */
	String getString(String key) throws BaseException;

	/**
	 * 
	 * @param key
	 * @return
	 * @throws BaseException
	 */
	Integer getInteger(String key) throws BaseException;

	/**
	 * {是否发生错误}
	 * 
	 * @return
	 */
	boolean hasError();

	Object getValueByAll(String key);

	/**
	 * {发生错误是获取错误消息}
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * {封装返回的数据集合}
	 * 
	 * @param key
	 * @param result
	 */
	void putResults(String key, Object result);

	/**
	 * {获取封装返回结果的结果集}
	 * 
	 * @return
	 */
	Map<String, Object> getResultMap();

	/**
	 * 
	 * @return
	 */
	JsonPageModel getJsonModel();

	public static String KEY_MODELMAP = "KEY_MODELMAP";
	public static String KEY_REQUEST2BEAN = "KEY_REQUEST2BEAN";
	public static String KEY_JSON = "KEY_JSON";
	public static String KEY_ERROR = "KEY_ERROR";
	public static String KEY_LOGIN_USER = "login_user";
	public static String KEY_MESSAGE = "KEY_MESSAGE";
	public static String KEY_TRANSACTIONSTATUS = "KEY_TRANSACTIONSTATUS";
	public static String KEY_HTTPSERVLETREQUEST = "KEY_HTTPSERVLETREQUEST";
}
