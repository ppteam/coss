package com.neusoft.core.web;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.IContext;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {类描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>May 3, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class RunJobsChainSupport extends ChainSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RunJobsChainSupport.class);

	public RunJobsChainSupport() {
	}

	/**
	 * {执行制定的责任链任务}
	 */
	public void runJob() {
		Assert.hasLength(chainName, "please init chianName for this jobs");
		IContext context = creatContext();
		if (logger.isInfoEnabled()) {
			logger.info("Job's chainName is run start");
		}
		executeChain(context, chainName);
		if (logger.isInfoEnabled()) {
			logger.info("Job's chainName is run end");
		}
	}

	private String chainName;

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

}
