package com.neusoft.model.reportforms.web;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractController;

/**
 * {项目-日报 导出}
 * 
 * @author thinker
 * 
 */
@Controller
@RequestMapping(value = "/dailyExport")
public class DailyExportController extends AbstractController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DailyExportController.class);

    public DailyExportController() {
        super();
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
            executeChain(context, "init_legal_projects_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/dailyExport";
    }

    /**
     * 加班明细统计
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/overWork.ftl")
    public String overWork(HttpServletRequest request, ModelMap modelMap) {
        IContext context = creatContext();
        try {
            initContext(request, context, modelMap);
            executeChain(context, "init_legal_projects_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/overWork";
    }

    /**
     * 项目 - 工作量明细
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/workdetail.ftl")
    public String workdetail(HttpServletRequest request, ModelMap modelMap) {
        IContext context = creatContext();
        try {
            initContext(request, context, modelMap);
            executeChain(context, "init_legal_projects_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/workdetail";
    }

} // end_class
