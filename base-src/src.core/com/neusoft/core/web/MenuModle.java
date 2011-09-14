package com.neusoft.core.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {系统导航菜单建模} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 20, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class MenuModle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2798811603247282657L;

	public MenuModle() {
	}

	private String menuId;

	private String menuName;

	private String menuValue;

	private String superName;

	private String stylecss;

	private String menuCss;

	private List<MenuModle> items = new ArrayList<MenuModle>();

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuValue() {
		return menuValue;
	}

	public void setMenuValue(String menuValue) {
		this.menuValue = menuValue;
	}

	public List<MenuModle> getItems() {
		return items;
	}

	public void setItems(List<MenuModle> items) {
		this.items = items;
	}

	public String getSuperName() {
		return superName;
	}

	public String getMenuCss() {
		return menuCss;
	}

	public void setMenuCss(String menuCss) {
		this.menuCss = menuCss;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public String getStylecss() {
		return stylecss;
	}

	public void setStylecss(String stylecss) {
		this.stylecss = stylecss;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuModle [menuId=" + menuId + ", menuName=" + menuName + ", menuValue=" + menuValue + ", superName="
				+ superName + ",items size = " + items.size() + "]";
	}

}
