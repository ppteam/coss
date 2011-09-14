package com.neusoft.model.reportforms.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractController;

/**
 * {项目月报表}
 * 
 * @author thinker
 * 
 */

@Controller
@RequestMapping(value = "/projMonth")
public class ProjMonthController extends AbstractController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ProjMonthController.class);

    public ProjMonthController() {
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
        return "model/projMonthReport";
    }

}
