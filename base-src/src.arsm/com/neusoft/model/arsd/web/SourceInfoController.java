package com.neusoft.model.arsd.web;

import org.apache.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.CommonUtils;
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
@RequestMapping(value = "/source")
public class SourceInfoController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SourceInfoController.class);

	public SourceInfoController() {
	}

	@RequestMapping(value = "/view.ftl")
	public String console(HttpServletRequest request, ModelMap modelMap) {
		IContext context = creatContext();
		context.put(IContext.KEY_MODELMAP, modelMap);
		try {
			executeChain(context, "init_source_data_chain");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/sourcesinfo";
	}

	/**
	 * {获取资源全部信息JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/allsource.json")
	public Map<String, Object> sourceList() {
		IContext context = creatContext();
		executeChain(context, "list_allsource_chain");
		return context.getJsonModel().returnModel();
	}

	/**
	 * {获取菜单JSON建模}
	 * 
	 * @param catalogId
	 * @return List<DictRecord>
	 */
	@RequestMapping(value = "/menus.json")
	public Map<String, Object> menuItems(HttpServletRequest request) {
		IContext context = null;
		try {
			context = CommonUtils.getLoginUser(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		executeChain(context, "init_menu_toJson_chain");
		return context.getJsonModel().returnModel();
	}

}
