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

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {个人周报填写View} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Apr 19, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/eWeekReports")
public class EWeekReportController extends AbstractController {
	private static final Logger logger = Logger.getLogger(EWeekReportController.class);

	public EWeekReportController() {
	}

	/**
	 * {填写默认页面菜单}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_ewReport_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/eWeekReport";
	}

	/**
	 * {加载当前登录用户的可以访问的周报列表}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/datalist.json")
	public Map<String, Object> getAllEWeekReport(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			initContext(request, context, null);
			WebTools.request2Map(request, context);
			executeChain(context, "find_all_eWeekReport_byself_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	@RequestMapping(value = "/eWeekReportById.json")
	public Map<String, Object> getEWeekReportById(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "find_eWeekReport_byid_filter");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
}
