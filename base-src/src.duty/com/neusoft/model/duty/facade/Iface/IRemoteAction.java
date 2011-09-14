package com.neusoft.model.duty.facade.Iface;

import com.neusoft.core.web.CallBackModel;
import com.neusoft.model.duty.dao.pojo.CheckDetail;
import com.neusoft.model.duty.dao.pojo.CheckingRule;
import com.neusoft.model.duty.dao.pojo.HolidayRule;

/**
 * 
 * <b>Application name:</b>工时管理系统<br>
 * <b>Application describing:</b> {字典管理ajax 接口定义} <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>Jan 4, 2011<br>
 * 
 * @author HaoXiaoJie Email:haoxj@neusoft.com
 * @version 1.0
 */
public interface IRemoteAction {

	/**
	 * {新增考勤规则}
	 * 
	 * @param checkingRule
	 * @return CallBackModel
	 */
	CallBackModel handlerCkRule(CheckingRule checkingRule);

	/**
	 * {假期管理规则}
	 * 
	 * @param holidayRule
	 * @return CallBackModel
	 */
	CallBackModel handlerHoldayRule(HolidayRule holidayRule);

	/**
	 * {签到/签退操作}
	 * 
	 * @param type
	 *            Integer 0/1 签到/签退
	 * @return CallBackModel
	 */
	CallBackModel checkAction(Integer type);

	/**
	 * {改签操作，包括新增以及修改}
	 * 
	 * @param checkDetail
	 *            CheckDetail 待操作的签到数据
	 * @return CallBackModel
	 */
	CallBackModel checkEdited(CheckDetail checkDetail);

	/**
	 * {备注考勤明细规则}
	 * 
	 * @param detailId
	 * @param comments
	 * @return
	 */
	CallBackModel editComments(String detailId, String comments);
}
