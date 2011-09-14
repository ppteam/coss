package com.neusoft.core.web;

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
public class CallBackModel {

	// 正常数据返回指针
	private Object callData;
	// ajax 是否发生错误标志
	private boolean error = false;
	// 发生错误的错误信息
	private String msg;

	private CallBackModel() {
	}

	public Object getCallData() {
		return callData;
	}

	public void setCallData(Object callData) {
		this.callData = callData;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static CallBackModel getInstance() {
		return new CallBackModel();
	}
}
