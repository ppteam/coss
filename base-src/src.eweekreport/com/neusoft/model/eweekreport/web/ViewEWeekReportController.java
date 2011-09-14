package com.neusoft.model.eweekreport.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;
import com.neusoft.model.eweekreport.dao.pojo.EWeekReport;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {个人周报查看} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 20, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/viewWeekReports")
public class ViewEWeekReportController extends AbstractController {
	private static final Logger logger = Logger.getLogger(ViewEWeekReportController.class);

	public ViewEWeekReportController() {
	}

	/**
	 * {初始化页面数据}
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
			executeChain(context, "init_vwk_report_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/viewWeekReport";
	}

	/**
	 * {加载制定条件的周报列表}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reports.json")
	public Map<String, Object> getAllEWeekReport(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			initContext(request, context, null);
			WebTools.request2Map(request, context);
			executeChain(context, "query_view_eWeekReport_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {按照周报获取对应的日报明细}
	 * 
	 * @return List<DailyReport>
	 */
	@RequestMapping(value = "/detailList.json")
	public Map<String, Object> getDailyById(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			EWeekReport weekReport = request2Bean(request, EWeekReport.class, null);
			context.put("instance", weekReport);
			executeChain(context, "query_detail_daily_report_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
}
