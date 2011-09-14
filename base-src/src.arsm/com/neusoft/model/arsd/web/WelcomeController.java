package com.neusoft.model.arsd.web;

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
 * <b>Application describing:</b> {资源管理 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/welcome")
public class WelcomeController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WelcomeController.class);

	public WelcomeController() {
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		try {
			IContext context = creatContext();
			initContext(request, context, modelMap);
			modelMap.put("USER", context);
		} catch (Exception e) {
			logger.error("system err", e);
		}
		return "model/welcome";
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/download.ftl")
	public String download(HttpServletRequest request, ModelMap modelMap) {
		try {
			IContext context = creatContext();
			initContext(request, context, modelMap);
			modelMap.put("USER", context);
		} catch (Exception e) {
			logger.error("system err", e);
		}
		return "model/download";
	}

}
