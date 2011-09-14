package com.neusoft.model.pwreport.web;

import java.util.Map;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;
import com.neusoft.model.pwreport.dao.pojo.PweeklyReport;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {项目周报管理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/pwreport")
public class ProWekReportController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProWekReportController.class);

	public ProWekReportController() {
	}

	/**
	 * {周报填写} /pwreport/filter.ftl
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/filter.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "type", required = true, defaultValue = "projectwrt") String type) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_pro_wrpt_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/" + type;
	}

	/**
	 * {项目周报带有数据权限的列表查看}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list.ftl")
	public String viewPage(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_pwrpt_list_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/prowrlist";
	}

	/**
	 * {项目周报JSON 数据列表}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list.json")
	public Map<String, Object> viewList(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "pwreport_list_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {项目周报修改列表页面}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/edit.ftl")
	public String editList(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "type", required = true, defaultValue = "prowred") String type) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_pro_wrped_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/" + type;
	}

	/**
	 * {通过用户填写的周报基本信息 判断该周报是否已经填写}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/check.json")
	public Map<String, Object> checkReport(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			PweeklyReport report = request2Bean(request, PweeklyReport.class, null);
			WebTools.request2Map(request, context);
			context.put("instance", report);
			initContext(request, context, null);
			executeChain(context, "check_pro_exist_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {通过用户填写的周报基本信息 判断该周报是否已经填写}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/context.json")
	public Map<String, Object> loadReport(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "load_pwreport_byrptId_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {通过用户填写的周报基本信息 判断该周报是否已经填写}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addinfo.json")
	public Map<String, Object> loadAddInfo(HttpServletRequest request) {
		String type = request.getParameter("type");
		Assert.hasLength(type, "[p:type] from request is not length");
		IContext context = creatContext();
		try {
			PweeklyReport report = request2Bean(request, PweeklyReport.class, null);
			context.put("instance", report);
			if ("problem".equals(type)) {
				executeChain(context, "load_prolems_byRepId_chain");
			} else if ("plan".equals(type)) {
				executeChain(context, "load_plans_byRepId_chain");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {通过用户填写的周报基本信息 判断该周报是否已经填写}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/problemAndplan.json")
	public Map<String, Object> loadForSave(HttpServletRequest request) {
		String type = request.getParameter("type");
		Assert.hasLength(type, "[p:type] from request is not length");
		IContext context = creatContext();
		try {
			PweeklyReport report = request2Bean(request, PweeklyReport.class, null);
			context.put("instance", report);
			if ("problem".equals(type)) {
				executeChain(context, "load_last_prolems_byRepId_chain");
			} else if ("plan".equals(type)) {
				executeChain(context, "load_plans_forsave_chain");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

}
