package com.neusoft.model.arsd.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.FilterBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.TreeHandleUtil;
import com.neusoft.core.web.LoginUser;
import com.neusoft.core.web.MenuModle;
import com.springsource.util.common.CollectionUtils;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {验证当前对象是否与数据库值有冲突} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 5, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class MenuModelBuilderFilter extends FilterBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuModelBuilderFilter.class);

	private static final long serialVersionUID = -1661357785545171002L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICommand#execute(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	@SuppressWarnings("unchecked")
	public boolean execute(IContext context) throws BaseException {
		List<MenuModle> sigle_Items = (List<MenuModle>) context.getResultMap().get(key);
		List<MenuModle> tree_Menu = TreeHandleUtil.builderMenu(sigle_Items);
		if (CollectionUtils.isEmpty(tree_Menu)) {
			logger.warn("no any source for builder menu");
		}
		if (context instanceof LoginUser) {
			((LoginUser) context).setMenuList(tree_Menu);
		} else {
			context.getResultMap().put(LoginUser.KEY_MENULIST, tree_Menu);
		}
		return false;
	}

	private String key = "menuModel_list";

	public void setKey(String key) {
		this.key = key;
	}

}
