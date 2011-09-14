package com.neusoft.core.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.neusoft.core.exception.BaseException;
import com.neusoft.core.web.MenuModle;
import com.neusoft.core.web.XmlBeanModel;
import com.springsource.util.common.CollectionUtils;

/**
 * {树形控件的抽象封装}
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 24, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public abstract class TreeHandleUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TreeHandleUtil.class);

	public TreeHandleUtil() {
	}

	/**
	 * {封装用户登录菜单}
	 * 
	 * @param srcList
	 * @return
	 * @throws BaseException
	 */
	public static List<MenuModle> builderMenu(List<MenuModle> srcList) throws BaseException {
		List<MenuModle> targetList = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Menu List size is [" + (srcList == null ? 0 : srcList.size()) + "]");
		}
		if (!CollectionUtils.isEmpty(srcList)) {
			targetList = new ArrayList<MenuModle>();
			for (MenuModle item : srcList) {
				MenuModle fathor = null;
				for (MenuModle element : targetList) {
					if (element.getMenuName().equals(item.getSuperName())) {
						fathor = element;
						break;
					}
				}
				if (fathor == null) {
					fathor = new MenuModle();
					fathor.setMenuName(item.getSuperName());
					fathor.getItems().add(item);
					fathor.setMenuCss(item.getMenuCss());
					targetList.add(fathor);
				} else {
					fathor.getItems().add(item);
				}
			}
		}
		return targetList;
	}

	/**
	 * {拼装树结构}
	 * 
	 * @param nodes
	 * @return
	 */
	public static XmlBeanModel assembling(List<XmlBeanModel> nodes, boolean hasRoot) throws BaseException {
		XmlBeanModel res = null;
		XmlBeanModel root = null;
		Stack<XmlBeanModel> stack = new Stack<XmlBeanModel>();
		// get root node
		for (XmlBeanModel node : nodes) {
			if (node.getId().equals(node.getFatherId())) {
				root = node;
				stack.push(root);
				break;
			}
		} // end_for

		while (!stack.isEmpty()) {
			XmlBeanModel pop = stack.pop();
			nodes.remove(pop);
			for (XmlBeanModel node : nodes) {
				if (node.getFatherId().equals(pop.getId())) {
					pop.getNodes().add(node);
					stack.push(node);
				}
			}
		} // end_while
		if (hasRoot) {
			res = new XmlBeanModel();
			res.setId("root");
			res.setValue("root");
			res.getNodes().add(root);
		} else {
			res = root;
		}
		return res;
	}
}
