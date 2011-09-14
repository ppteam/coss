package com.neusoft.core.chain.Impl;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

import com.neusoft.core.chain.Iface.ICompare;
import com.neusoft.core.chain.Iface.IContext;
import com.neusoft.core.exception.BaseException;

/**
 * 
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {基于数字的比较} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>May 3, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public class NumberCompare implements ICompare {

	public NumberCompare() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.core.chain.Iface.ICompare#compare(com.neusoft.core.chain.
	 * Iface.IContext)
	 */
	public boolean compare(IContext context) throws BaseException {
		boolean res = false;
		Assert.notNull(type, "type can not be null.please init this parm");
		Assert.notNull(key, "key can not be null.please init this parm");
		Assert.notNull(cmpVal, "cmpVal can not be null.please init this parm");
		if (!NumberUtils.isNumber(cmpVal)) {
			throw new BaseException("0004", BaseException.EXP_TYPE_ERROR, null, null, new String[] { cmpVal });
		}
		String str_value = context.getValueByAll(key) == null ? "" : context.getValueByAll(key).toString();
		if (!NumberUtils.isNumber(str_value)) {
			throw new BaseException("0004", BaseException.EXP_TYPE_ERROR, null, null, new String[] { str_value });
		}
		switch (type) {
		case 0:
			int int_target = NumberUtils.createInteger(str_value);
			int int_source = NumberUtils.createInteger(cmpVal);
			switch (rule) {
			case 0:
				res = int_target == int_source;
				break;
			case 1:
				res = int_target > int_source;
				break;
			case 2:
				res = int_target < int_source;
				break;
			case 3:
				res = int_target != int_source;
				break;
			case 4:
				res = int_target >= int_source;
				break;
			case 5:
				res = int_target <= int_source;
				break;
			default:
				throw new BaseException("0005", BaseException.EXP_TYPE_ERROR, null, null, new String[] {
						String.valueOf(rule), "0-5" });
			} // end_switch (rule)
			break;
		case 1:
			long lng_target = NumberUtils.createLong(str_value).longValue();
			long lng_source = NumberUtils.createLong(cmpVal).longValue();
			switch (rule) {
			case 0:
				res = lng_target == lng_source;
				break;
			case 1:
				res = lng_target > lng_source;
				break;
			case 2:
				res = lng_target < lng_source;
				break;
			case 3:
				res = lng_target != lng_source;
				break;
			case 4:
				res = lng_target >= lng_source;
				break;
			case 5:
				res = lng_target <= lng_source;
				break;
			default:
				throw new BaseException("0005", BaseException.EXP_TYPE_ERROR, null, null, new String[] {
						String.valueOf(rule), "0-5" });
			} // end_switch (rule)
			break;
		case 2:
			float flt_target = NumberUtils.createFloat(str_value).floatValue();
			float flt_source = NumberUtils.createFloat(cmpVal).floatValue();
			switch (rule) {
			case 0:
				// NumberUtils.compare(lhs, rhs)
				// res = flt_source == flt_source;
				break;
			case 1:
				res = flt_target > flt_source;
				break;
			case 2:
				res = flt_target < flt_source;
				break;
			case 3:
				res = flt_target != flt_source;
				break;
			case 4:
				res = flt_target >= flt_source;
				break;
			case 5:
				res = flt_target <= flt_source;
				break;
			default:
				throw new BaseException("0005", BaseException.EXP_TYPE_ERROR, null, null, new String[] {
						String.valueOf(rule), "0-5" });
			} // end_switch (rule)
			break;
		default:
			throw new BaseException("0005", BaseException.EXP_TYPE_ERROR, null, null, new String[] {
					String.valueOf(type), "0-2" });
		}
		return res;
	}

	// 0/1/2 : int/long/float
	private int type;

	private String key;
	// 比较的值
	private String cmpVal;
	// 判断规则 等于 0/大于 1/小于 2/不等于 3/大于等于 4/小于等于 5
	private int rule;

	public void setType(int type) {
		this.type = type;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setCmpVal(String cmpVal) {
		this.cmpVal = cmpVal;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

}
