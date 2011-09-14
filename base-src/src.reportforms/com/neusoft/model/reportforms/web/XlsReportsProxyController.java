package com.neusoft.model.reportforms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.chain.Impl.ContextBase;
import com.neusoft.core.exception.BaseException;
import com.neusoft.core.util.CommonUtils;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractMultiActionController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {报表统计模板Controller} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 24, 2011<br>
 * 
 * @author xiaoshuaiping Email:xiaoshp@neusoft.com
 * @version 1.0
 */
public final class XlsReportsProxyController extends AbstractMultiActionController {

	private static final Logger logger = Logger.getLogger(XlsReportsProxyController.class);

	public XlsReportsProxyController() {
	}

	/**
	 * 
	 * {部门-考勤汇总}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView ckTotal(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_ckstats_json_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("CheckDetailTotalViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {部门-误餐统计报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView wcassit(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_wcassit_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("WuCanDetailViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {项目-人员统计报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView proMember(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_proMember_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("ProjectMemberViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {项目-工作量明细报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView workDetail(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_worktime_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("WorkDetailViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {加班明细报表数据收集}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView overWorker(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_overWorker_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("OverWorkViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {日报数据导出}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView dailyExport(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_dailyExport_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("DailyExportViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {考勤 导出}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 郝晓杰
	 */
	public ModelAndView dutyCollect(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "query_dutyclt_detail_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("DutyCollectViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {个人周报报表,开发与测试统一}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 肖帅平
	 */
	public ModelAndView personWeek(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_perWeek_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return creatModelAndView("PerWeekViewResolvers", context);
	} // end_fun

	/**
	 * 
	 * {人员-月份工作量统计表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BaseException
	 * @author: xiaoshuaiping
	 */
	public ModelAndView staffMonth(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_staffMonth_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return creatModelAndView("StaffMonthCollectViewResolvers", context);
	}

	/**
	 * 
	 * {项目-人员工作量统计表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: xiaoshuaiping
	 */
	public ModelAndView ProjectStaff(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_projectStaff_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return creatModelAndView("ProjectStaffViewResolvers", context);
	}

	/**
	 * 
	 * {项目-月份工作量统计表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: xiaoshuaiping
	 */
	public ModelAndView ProjMonth(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_projMonth_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return creatModelAndView("ProjMonthViewResolvers", context);
	}

	/**
	 * 
	 * {人员基本信息报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 肖帅平
	 */
	public ModelAndView staffInfo(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		// IContext context = creatContext();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_staffInfo_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return creatModelAndView("StaffInfoViewResolvers", context);
	}

	/**
	 * 
	 * {通讯录报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 肖帅平
	 */
	public ModelAndView addrBook(HttpServletRequest request, HttpServletResponse response) {
		IContext context = new ContextBase();
		try {
			context.putAll(CommonUtils.getLoginUser(request));
			WebTools.request2Map(request, context);
			executeChain(context, "list_addrBook_chain", request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return creatModelAndView("AddrBookViewResolvers", context);
	}

	/**
	 * 
	 * {项目周报报表}
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: 肖帅平
	 */
	public ModelAndView projWeek(HttpServletRequest request, HttpServletResponse response) {
		IContext context = creatContext();
		ModelAndView mav = null;
		try {
			WebTools.request2Map(request, context);
			int type = context.getInteger("type");
			switch (type) {
			case 0:
				executeChain(context, "list_projWeek_chain", request);
				mav = creatModelAndView("ProjWeekViewResolvers", context);
				break;
			case 1:
				executeChain(context, "list_dep_projWeek_chain", request);
				mav = creatModelAndView("DepProjWeekViewResolvers", context);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mav;
	}

}
