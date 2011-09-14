package com.neusoft.core.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {ajax 返回类型数据建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class JsonPageModel implements Serializable {

	private static final long serialVersionUID = 7036610781742788692L;

	private JsonPageModel() {
	}

	public void putData(Object object) {
		model.put("data", object);
	}

	public void putSize(int pageSize) {
		model.put("size", pageSize);
	}

	/**
	 * {记录总数}
	 * 
	 * @param amount
	 */
	public void putTotal(int amount) {
		model.put("total", amount);
	}

	public void putAddition(Object object) {
		model.put("addition", object);
	}

	public void putModle(Map<String, Object> model) {
		this.model = model;
	}

	private Map<String, Object> model = new HashMap<String, Object>();

	public Map<String, Object> returnModel() {
		return this.model;
	}

	public static JsonPageModel getInstance() {
		return new JsonPageModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonPageModel [total=" + model.get("total") + "]";
	}

}
