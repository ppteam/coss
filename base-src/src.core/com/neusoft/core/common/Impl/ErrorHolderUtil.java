package com.neusoft.core.common.Impl;

import org.apache.log4j.Logger;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import com.neusoft.core.common.Iface.IErrorHolder;
import com.neusoft.core.exception.BaseException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 16, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class ErrorHolderUtil implements IErrorHolder, MessageSourceAware {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ErrorHolderUtil.class);

	public ErrorHolderUtil() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.common.Iface.IErrorHolder#isNotBaseException(java.lang
	 * .Exception)
	 */
	public static boolean isNotBaseException(Exception e) throws BaseException {
		Validate.notNull(e, "[p:Exception] can't be null");
		if (e instanceof BaseException) {
			throw (BaseException) e;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.common.Iface.IErrorHolder#holderException(java.lang.
	 * Exception, java.lang.String)
	 */
	public String holderException(Exception e) {
		String errMgs = null;
		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			if (be.getExpType() == BaseException.EXP_TYPE_EXCPT) {// 业务异常
				errMgs = processExp(be);
			} else if (be.getExpType() == BaseException.EXP_TYPE_ERROR) {
				errMgs = processErr(be);
			} else {
				logger.warn("BaseException errType is Evil, it's value [" + be.getExpType() + "]");
				errMgs = processErr(be);
			} // end_if
		} else {
			errMgs = processErr(e);
		}
		return errMgs;
	} // end_fun

	/**
	 * 
	 * function:{方法描述}
	 * 
	 * @param e
	 * @param nameSpace
	 * @return
	 */
	private String processErr(Exception e) {
		if (e instanceof BaseException) {
			String code = ((BaseException) e).getExpCode();
			String errMsg = messageSource.getMessage(code, ((BaseException) e).getReplaces(), null);
			logger.error(errMsg);
		} else {
			logger.error(e.getMessage(), e);
		}
		String message = messageSource.getMessage("error.system", null, null);
		return message;
	}

	/**
	 * 
	 * function:{方法描述}
	 * 
	 * @param e
	 * @param nameSpace
	 * @return
	 */
	private String processExp(BaseException exception) {
		String message = null;
		String code = exception.getExpCode();
		try {
			message = messageSource.getMessage(code, exception.getReplaces(), null);
			logger.error(message);
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			message = messageSource.getMessage("error.unknown", new String[] { code, Locale.getDefault().toString() },
					null);
		}
		return message;
	}

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
