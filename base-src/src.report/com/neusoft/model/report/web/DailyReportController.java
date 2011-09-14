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
 * <b>Application describing:</b> {个人日报管理View 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 10, 2011<br>
 * 
 * @author lishijian Email:lishijian@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/edaily")
public class DailyReportController extends AbstractController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DailyReportController.class);

	public DailyReportController() {
	}

	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_dayReport_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/dailyReport";
	}

	/**
	 * {获取当前本周个人日报列表}
	 * 
	 * @return List<DailyReport>
	 */
	@RequestMapping(value = "/dailyReportall.json")
	public Map<String, Object> getAllDailyReport(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			initContext(request, context, null);
			WebTools.request2Map(request, context);
			executeChain(context, "find_all_daily_report_filter");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

}
