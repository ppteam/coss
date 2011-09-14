package com.neusoft.model.reportforms.web;

import java.util.Map;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.util.WebTools;
import com.neusoft.core.web.AbstractController;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {考勤统计} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Aug 10, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
@RequestMapping(value = "/dutyclt")
public class DutyCollectController extends AbstractController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DutyCollectController.class);

    public DutyCollectController() {
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
            executeChain(context, "init_dept_base_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/dutyclt";
    }

    /**
     * {获取指定的考虑明细列表}
     * 
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/list.json")
    public Map<String, Object> getDutyCltList(HttpServletRequest request) {
        IContext context = creatContext();
        try {
            WebTools.request2Map(request, context);
            initContext(request, context, null);
            executeChain(context, "query_dutyclt_detail_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return context.getJsonModel().returnModel();
    }
}
