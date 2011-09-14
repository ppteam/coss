package com.neusoft.model.reportforms.web;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> 项目-月份工作量统计表 控制器 <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/preweek")
public class PerWeekViewController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PerWeekViewController.class);

	public PerWeekViewController() {
	}

	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, null);
			executeChain(context, "init_dept_base_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/perweekReport";
	}
}
