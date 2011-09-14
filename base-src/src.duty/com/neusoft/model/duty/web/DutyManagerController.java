package com.neusoft.model.duty.web;

import java.util.Map;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.DateUtil;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;
import com.neusoft.core.web.LoginUser;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {考勤管理} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Mar 17, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/duty")
public class DutyManagerController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DutyManagerController.class);

	public DutyManagerController() {
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/happyDay.ftl")
	public String happyDay(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_dept_base_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/happyday";
	} // end_fun

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/rulemgr.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_checkrule_opts_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/checkrulemgr";
	}

	/**
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/holiday.ftl")
	public String holidayView(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_holidays_view_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/holidaymgr";
	}

	/**
	 * {映射 签到明细页面}
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/ckdetail.ftl")
	public String checkDetailView(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		String viewPath = null;
		String type = request.getParameter("type");
		Assert.hasLength(type, "request type is not null,please init type.");
		try {
			initContext(request, context, modelMap);
			if ("all".equals(type)) {
				executeChain(context, "init_dept_base_chain");
				viewPath = "model/checkdetail";
			} else if ("person".equals(type)) {
				viewPath = "model/pckdetail";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return viewPath;
	}

	/**
	 * {映射 改签 或者 补签}
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/recheck.ftl")
	public String checkAgain(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_recheck_base_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/checkagain";
	}

	/**
	 * @note 签到汇总统计
	 * @param request
	 * @param modelMap
	 * @return String
	 */
	@RequestMapping(value = "/checkStats.ftl")
	public String checkStats(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_dept_base_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/checkstats";
	}

	/**
	 * {用户基本信息列表}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/list.json")
	public Map<String, Object> getRuleList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_checkrule_json_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {用户基本信息列表}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/holiday.json")
	public Map<String, Object> getHolidayList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_holidays_json_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取签到明细记录（数据集权限）}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/ckdetail.json")
	public Map<String, Object> getCheckDetails(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			// 初始化数据集权限
			if (context.get("deptIdSet") == null) {
				context.put("deptIdSet", context.get(LoginUser.KEY_DATASET));
			}
			// 查询日期 默认是当天
			if (context.get("startDate") == null) {
				context.put("startDate", DateUtil.getToday());
			}
			if (context.get("endDate") == null) {
				context.put("endDate", DateUtil.getToday());
			}
			executeChain(context, "list_detail_json_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取个人签到明细记录}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/pckdetail.json")
	public Map<String, Object> getPCheckDetails(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_pck_detail_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取个人签到明细记录}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/smpdetail.json")
	public Map<String, Object> getSampleDetails(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_smp_detail_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {加班-调休对照明细}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/happydays.json")
	public Map<String, Object> getHappyDays(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_happyday_detail_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * @note 签到汇总统计 数据汇总
	 * @param request
	 * @param modelMap
	 * @return String
	 */
	@RequestMapping(value = "/checkStats.json")
	public Map<String, Object> getCheckStats(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			// 初始化数据集权限
			if (context.get("deptIdSet") == null) {
				context.put("deptIdSet", context.get(LoginUser.KEY_DATASET));
			}
			executeChain(context, "list_ckstats_json_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	} // end_fun
}
