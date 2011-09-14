package com.neusoft.model.udmgr.service;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.web.LoginUser;
import com.neusoft.model.udmgr.dao.pojo.TodoList;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {代办事项处理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Feb 9, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class HandleTodoListFilter extends AbstractAdapterFilter {

	private static final long serialVersionUID = -1661357785545171987L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean execute(IContext context) throws BaseException {
		TodoList instance = context.getObject(paramsKey, TodoList.class);
		if (instance.getId() == null) { // insert
			instance.setTodoId(CommonUtils.getUUIDSeq());
			instance.setUserId(context.getString(LoginUser.KEY_USERID));
			todoListDao.insert(instance);
			context.getResultMap().put("saveId", instance.getId());
		} else {// update
			int res = todoListDao.updateById(instance);
			context.getResultMap().put("updateRow", res);
		} // end_if
		return false;
	} // end_fun

	private String paramsKey = "instance";

	public void setParamsKey(String paramsKey) {
		this.paramsKey = paramsKey;
	}

}
