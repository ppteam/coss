package com.neusoft.model.udmgr.web;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.neusoft.core.chain.Iface.IContext;
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
@RequestMapping(value = "/multipart")
public class UpLoadFilesController extends AbstractController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UpLoadFilesController.class);

	public UpLoadFilesController() {
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/upload.ftl")
	public String uploadView() {
		return "model/upload";
	}

	/**
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/file.ftl", method = RequestMethod.POST)
	public String handleFormUpload(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
		IContext context = creatContext();
		try {
			initContext(request, context, modelMap);
			byte[] bytes = file.getBytes();
			if (logger.isDebugEnabled()) {
				logger.debug("upload file'name [" + fileName + "] size is [" + bytes.length + "]");
			} // end_if
			context.put("fileBts", bytes);
			executeChain(context, "batch_import_user_chain");
			modelMap.put("success", !context.hasError());
			if (context.hasError()) {
				modelMap.put("msg", context.getMessage());
			} else {
				modelMap.put("msg", "o(∩∩)o...哈哈，人员导入成功。");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "model/upload_ok";
	} // end_fun

}
