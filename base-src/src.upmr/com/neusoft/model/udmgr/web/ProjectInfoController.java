package com.neusoft.model.udmgr.web;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目管理View 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectInfoController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProjectInfoController.class);

	public ProjectInfoController() {
	}

	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_project_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/project";
	}

	/**
	 * {项目信息列表}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/list.json")
	public Map<String, Object> getProjectsList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_project_list_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {项目相关人员列表}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member.json")
	public Map<String, Object> getProMembnersList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_project_member_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {项目相关人员列表}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/allMember.json")
	public Map<String, Object> getAllProMember(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_project_allmember_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
	/**
	 * {项目里程碑列表}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stats.json")
	public Map<String, Object> getProStatsList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "query_project_stats_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
}
