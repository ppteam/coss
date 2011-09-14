package com.neusoft.model.arsd.web;

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
 * <b>Application describing:</b> {资源管理 控制器} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@Controller
public class AuthorAndRoleController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AuthorAndRoleController.class);

	public AuthorAndRoleController() {
	}

	@RequestMapping(value = "/author/view.ftl")
	public String autorConsole() {
		return "model/authorinfo";
	}

	@RequestMapping(value = "/role/view.ftl")
	public String roleConsole(ModelMap modelMap) {
		IContext context = creatContext();
		context.put(IContext.KEY_MODELMAP, modelMap);
		try {
			executeChain(context, "init_role_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/roleinfo";
	}

	/**
	 * {获取菜单JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/author/allRecord.json")
	public Map<String, Object> getAuthorList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "list_allauthor_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取菜单JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/role/authors.json")
	public Map<String, Object> getAuthorByRole(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "list_role_author_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取菜单JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/author/enableRec.json")
	public Map<String, Object> getEnableAuthors(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "enabled_authors_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取菜单JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/role/allRecord.json")
	public Map<String, Object> getRoleList(HttpServletRequest request) {
		IContext context = creatContext();
		try {
			WebTools.request2Map(request, context);
			executeChain(context, "list_allRole_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context.getJsonModel().returnModel();
	}
}
