package com.neusoft.model.udmgr.web;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {组织结构管理View 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/dept")
public class DepartmentController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentController.class);

	public DepartmentController() {
	}

	@RequestMapping(value = "/view.ftl")
	public String console() {
		return "model/department";
	}

	/**
	 * {返回部门树}
	 * 
	 * @param modelMap
	 */
	@RequestMapping(value = "/tree.xml")
	public void getDeptTree(ModelMap modelMap) {
		IContext context = creatContext();
		context.put(IContext.KEY_MODELMAP, modelMap);
		try {
			executeChain(context, "dept_tree_xml_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	} // end_fun

}
