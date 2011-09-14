/**
 * @author 考勤管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var monthStatistics_form = null;
	var monthStatistics_dialog = null;
	var start = null;
	var end = null;
	var _dataModel = new StatisticsModel();

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
		monthStatistics_form = new Ext.FormPanel({
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
								handler : function(/* Button */btn,/* Event */
										e) {
									monthStatistics_form.getForm().reset();
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
					items : [new Ext.form.DateField({
										fieldLabel : '开始时间',
										id : 'startDate',
										name : 'startDate',
										anchor : '30%',
										format : 'Y-m-d',
										allowBlank : false
									}), new Ext.form.DateField({
										fieldLabel : '结束时间',
										id : 'endDate',
										name : 'endDate',
										anchor : '30%',
										format : 'Y-m-d',
										allowBlank : false
									}), {
								id : 'deptGroup',
								name : 'deptGroup',
								xtype : 'fieldset',
								anchor : '70%',
								fieldLabel : '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门'
							}]
				});
		_dataModel.initDeptGroup(monthStatistics_form);
		monthStatistics_dialog = new Ext.Panel({
					title : '人员-月工作量统计表',
					layout : 'border',
					region : 'center',
					items : [monthStatistics_form],
					buttonAlign : 'center'
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [monthStatistics_dialog]
				});

	} // end_fun_buildContain

	/**
	 * {导出报表}
	 */
	function doExportXls() {
		if (Ext.getCmp('startDate').getValue() === '') {
			Ext.MessageBox.alert("消息", "开始日期不能为空");
			return;
		} else {
			start = Ext.getCmp('startDate').getValue().format('Y-m-d');
		}
		if (Ext.getCmp('endDate').getValue() === '') {
			Ext.MessageBox.alert("消息", "结束日期不能为空");
			return;
		} else {
			end = Ext.getCmp('endDate').getValue().format('Y-m-d');
		}
		if (start > end) {
			Ext.MessageBox.alert("消息", "结束日期不能小于开始时间");
			return;
		}
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
			var _title = 'WORKOR_BY_MONTH_WORKTOTAL';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=staffMonth&startDate=' + start + '&endDate=' + end
					+ '&list_deptId=' + _arg_ids.join("|") + '&title=' + _title;
		}
	} // end_fun

	/**
	 * {全选}
	 */
	function selectAllProject() {
		for (var i = 0; i < dept_data.length; i++) {
			Ext.getCmp(dept_data[i][0]).setValue(true);
		}
	}
} // end_class
