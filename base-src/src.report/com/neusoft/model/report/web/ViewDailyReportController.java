package com.neusoft.model.report.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {查看个人日报管理View 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 27, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/viewdaily")
public class ViewDailyReportController extends AbstractController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ViewDailyReportController.class);

	public ViewDailyReportController() {
	}

	/**
	 * {初始化 日报查看页面数据初始化--该页面享有数据集权限约束}
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_viewReport_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/viewdailyReport";
	}

	/**
	 * {获取当前全部个人日报列表}
	 * 
	 * @return List<DailyReport>
	 */
	@RequestMapping(value = "/viewAllDailyReport.json")
	public Map<String, Object> getAllDailyReportInfo(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			initContext(request, context, null);
			WebTools.request2Map(request, context);
			executeChain(context, "find_all_daily_report_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {按照项目ID加载员工信息}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userList.json")
	public Map<String, Object> getUserByProjectId(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_project_userlist_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
}
