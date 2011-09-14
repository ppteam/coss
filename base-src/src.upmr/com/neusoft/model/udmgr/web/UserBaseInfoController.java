package com.neusoft.model.udmgr.web;

import org.apache.log4j.Logger;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {组织结构管理View 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserBaseInfoController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserBaseInfoController.class);

	public UserBaseInfoController() {
	}

	/**
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
			executeChain(context, "init_user_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/userbaseinfo";
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/urmgr.ftl")
	public String usrolemgr(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_user_role_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/userolemgr";
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/runing.ftl")
	public String useruning(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			executeChain(context, "init_user_runing_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/runing";
	}

	/**
	 * {用户基本信息列表}
	 * 
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/list.json")
	public Map<String, Object> getUserBaseList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_enabled_user_chain");
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
	@RequestMapping(value = "/listAll.json")
	public Map<String, Object> getAllUserList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "list_all_user_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findUserById.json")
	public Map<String, Object> loadUserById(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "find_user_byid_chian");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryForOpts.json")
	public Map<String, Object> loadUserForOpts(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_user_ForOpt_chian");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/todoList.json")
	public Map<String, Object> loadTodoList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			initContext(request, context, null);
			executeChain(context, "query_todo_list_chian");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {加载指定人员的角色ID}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadRoles.json")
	public Map<String, Object> loadRoleByUserId(HttpServletRequest request) {
		String type = request.getParameter("type");
		Assert.hasLength(type, "/loadRoles.json?type=value type can't be null");
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			if ("role".equals(type)) {
				executeChain(context, "load_roles_byUserId_chian");
			} else if ("sign".equals(type)) {
				executeChain(context, "load_sign_byUserId_chian");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {加载指定人员的角色ID}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accessData.json")
	public Map<String, Object> loadAccessDataByUserId(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "load_access_data_byUserId_chian");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

}
