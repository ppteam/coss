package com.neusoft.core.exception;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Dec 14, 2010<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -7183559774041054613L;

	public static final int EXP_TYPE_ERROR = 0;

	public static final int EXP_TYPE_EXCPT = 1;

	public BaseException(String mgs, String[] replaces) {
		super(mgs);
		this.replaces = replaces;
	}

	public BaseException(String expCode, int expType, String mgs, Exception latelyExp,
			String[] replaces) {
		super(mgs);
		this.expCode = key(expCode, BaseException.class);
		this.expType = expType;
		this.latelyExp = latelyExp;
		this.replaces = replaces;
	}

	public BaseException(String expCode, String mgs, Exception latelyExp, String[] replaces) {
		super(mgs);
		this.expCode = key(expCode, BaseException.class);
		this.latelyExp = latelyExp;
		this.replaces = replaces;
	}

	public BaseException(String expCode, Exception latelyExp, String[] replaces) {
		this.expCode = key(expCode, BaseException.class);
		this.latelyExp = latelyExp;
		this.replaces = replaces;
	}

	public BaseException(String message) {
		super(message);
	}

	private String expCode;
	// 默认为系统异常
	private int expType = EXP_TYPE_ERROR;
	private Exception latelyExp;

	private String printMgs;
	private String[] replaces;

	public String getExpCode() {
		return expCode;
	}

	public int getExpType() {
		return expType;
	}

	public Exception getLatelyExp() {
		return latelyExp;
	}

	public String getPrintMgs() {
		return printMgs;
	}

	public void setPrintMgs(String printMgs) {
		this.printMgs = printMgs;
	}

	public String[] getReplaces() {
		return replaces;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public void setExpType(int expType) {
		this.expType = expType;
	}

	public void setLatelyExp(Exception latelyExp) {
		this.latelyExp = latelyExp;
	}

	public void setReplaces(String[] replaces) {
		this.replaces = replaces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		StringBuffer sb = new StringBuffer("[expType:").append(expType).append("][expCode:")
				.append(expCode).append("]");
		if (latelyExp != null) {
			sb.append("[message:").append(latelyExp.getMessage()).append("]");
		} else {
			sb.append("[message:").append(super.getMessage()).append("]");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param errCode
	 * @return
	 */
	protected static <T extends BaseException> String key(String expCode, Class<T> clazz) {
		String fullCode = expCode;
		if (expCode != null && expCode.indexOf('.') == -1) {
			fullCode = clazz.getSimpleName() + "." + expCode;
		}
		return fullCode;
	}
}
