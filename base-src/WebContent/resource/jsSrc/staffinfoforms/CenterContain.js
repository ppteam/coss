/**
 * @author 通讯录报表
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var StaffInfo_form = null;
	var StaffInfo_dialog = null;
	var start = null;
	var end = null;
	var _dataModel = new StaffInfoModel();

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};
	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		}
		return contain;
	};
	function buildContain() {
		StaffInfo_form = new Ext.FormPanel({
					region : 'center',
					labelWidth : 53,

					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					items : [{
								id : 'deptGroup',
								name : 'deptGroup',
								xtype : 'fieldset',
								anchor : '70%',
								fieldLabel : '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门'
							}]
				});
		_dataModel.initDeptGroup(StaffInfo_form);
		StaffInfo_dialog = new Ext.Panel({
					title : '人员基本信息报表',
					layout : 'border',
					region : 'center',
					items : [StaffInfo_form],
					buttonAlign : 'center',
					tbar : [{
						text : '生成报表',
						iconCls : 'btn_report',
						handler : function(/* Button */btn,/* Event */
								e) {
							var deptSet = _dataModel.getDeptIds();
							var _arg_ids = new Array();
							for (var i = 0; i <= deptSet.length - 1; i++) {
								if (Ext.getCmp(deptSet[i]).getValue()) {
									_arg_ids.push(deptSet[i]);
								}
							}
							if (_arg_ids.length === 0) {
								Ext.MessageBox.alert("操作提示", "请选择报表数据的部门范围，如果当前没有部门则说明你没有权限，请联系系统管理员。");
							} else {
								window.location.href = 'http://' + window.location.host
										+ '/workbase/report/proxy/view.xls?view=staffInfo&list_deptId='
										+ _arg_ids.join("|");
							}
						}
					}, '-', {
						text : '重置',
						iconCls : 'btn_reset',
						handler : function(/* Button */btn,/* Event */
								e) {
							StaffInfo_form.getForm().reset();
						}
					}, '-']
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [StaffInfo_dialog]
				});
	} // end_fun_buildContain
	/**
	 * @function buildContain
	 */

} // end_class
