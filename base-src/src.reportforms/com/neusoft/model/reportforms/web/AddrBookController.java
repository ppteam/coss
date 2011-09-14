package com.neusoft.model.reportforms.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.web.AbstractController;

/**
 * {项目-人员分布}
 * 
 * @author haoxiaojie
 * 
 */
@Controller
@RequestMapping(value = "/addrBook")
public class AddrBookController extends AbstractController {

    private static final Logger logger = Logger.getLogger(AddrBookController.class);

    public AddrBookController() {

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
        return "model/addrBookReport";
    }

    /**
     * {项目-人员统计报表}
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/member.ftl")
    public String proMember(HttpServletRequest request, ModelMap modelMap) {
        IContext context = creatContext();
        try {
            initContext(request, context, modelMap);
            executeChain(context, "init_dept_base_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/pro_member";
    }
    
    /**
     * {项目-人员统计报表}
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/wcass.ftl")
    public String wcassit(HttpServletRequest request, ModelMap modelMap) {
        IContext context = creatContext();
        try {
            initContext(request, context, modelMap);
            executeChain(context, "init_dept_base_chain");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "model/wcassit";
    }
}
