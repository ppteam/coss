/**
 * @author 通讯录报表
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var AddrBook_form = null;
	var AddrBook_dialog = null;
	var start = null;
	var end = null;
	var _dataModel = new AddrBookModel();

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
		AddrBook_form = new Ext.FormPanel({
					region : 'center',
					labelWidth : 53,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					buttons : [{
								text : '全选',
								handler : function(e) {
									selectAllProject();
								}
							}, {
								text : '重置',
								handler : function(e) {
									AddrBook_form.getForm().reset();
								}
							}, {
								text : '报表导出',
								handler : function(/* Button */btn,/* Event */
										e) {
									try {
										doExportXls();
									} catch (e) {
										Ext.MessageBox.alert("操作提示", e.message);
									}
								}
							}],
					items : [{
								id : 'deptGroup',
								name : 'deptGroup',
								xtype : 'fieldset',
								anchor : '30%',
								fieldLabel : '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门'
							}]
				});
		_dataModel.initDeptGroup(AddrBook_form);
		AddrBook_dialog = new Ext.Panel({
					title : '通讯录报表',
					layout : 'border',
					region : 'center',
					items : [AddrBook_form],
					buttonAlign : 'center'
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [AddrBook_dialog]
				});
	} // end_fun_buildContain

	/**
	 * 
	 */
	function doExportXls() {
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
			var _title = 'ADDRESS_DETAIL';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=addrBook&list_deptId=' + _arg_ids.join("|") + '&title='
					+ _title;
		}
	}

	/**
	 * {全选}
	 */
	function selectAllProject() {
		for (var i = 0; i < dept_data.length; i++) {
			Ext.getCmp(dept_data[i][0]).setValue(true);
		}
	}
} // end_class
