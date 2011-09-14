/**
 * @author 通讯录报表
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var queryForm = null;
	var winFanel = null;
	var model = new DataModel();

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};
	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		}
		return contain;
	};

	/**
	 * {动态初始化项目列表信息}
	 */
	function initProGroupUI() {
		if (dept_data !== null && dept_data.length !== 0) {
			var ckGroup = Ext.getCmp('deptGroup');
			var ck_groups = {
				xtype : 'checkboxgroup',
				columns : 3,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dept_data.length; i++) {
				ck_groups.items[j] = new Ext.form.Checkbox({
							boxLabel : dept_data[i][1],
							name : dept_data[i][0],
							id : dept_data[i][0]
						});
				j++;
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if
		// init combox
		Ext.getCmp('q_year').setValue(model.getToday().format('Y'));
		Ext.getCmp('q_month').setValue(model.getToday().format('n'));
	} // end_fun

	function buildContain() {
		queryForm = new Ext.FormPanel({
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
									queryForm.getForm().reset();
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
								xtype : 'combo',
								triggerAction : 'all',
								fieldLabel : '年份',
								name : 'q_year',
								id : 'q_year',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								store : model.getYearStore(),
								valueField : 'regValue',
								displayField : 'displayValue',
								anchor : '15%'
							}, {
								xtype : 'combo',
								triggerAction : 'all',
								fieldLabel : '月份',
								name : 'q_month',
								id : 'q_month',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								store : model.getMonthList(),
								valueField : 'regValue',
								displayField : 'displayValue',
								anchor : '15%'
							}, {
								xtype : 'numberfield',
								minValue : 0,
								fieldLabel : '额定工时',
								value : 168,
								allowBlank : false,
								id : 'q_totalHours',
								anchor : '15%'
							}, {
								id : 'deptGroup',
								name : 'deptGroup',
								xtype : 'fieldset',
								anchor : '70%',
								fieldLabel : '可选部门'
							}]
				});

		initProGroupUI();

		winFanel = new Ext.Panel({
					title : '部门-误餐补助报表',
					layout : 'border',
					region : 'center',
					items : [queryForm],
					buttonAlign : 'center'
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [winFanel]
				});
	} // end_fun_buildContain

	/**
	 * @function buildContain
	 */
	function doExportXls() {
		var _totalHours = Ext.getCmp('q_totalHours').getValue();

		if (_totalHours === '') {
			throw new Error('请填写当月额定工时数。');
		}

		var month_val = Ext.getCmp('q_month').getValue();
		var year_val = Ext.getCmp('q_year').getValue();

		var _arg_ids = new Array();
		for (var i = 0; i <= dept_data.length - 1; i++) {
			if (Ext.getCmp(dept_data[i][0]).getValue()) {
				_arg_ids.push(dept_data[i][0]);
			}
		} // end_for

		if (_arg_ids.length === 0) {
			Ext.MessageBox.alert("操作提示", "请选择报表数据的部门范围，如果当前没有部门则说明你没有权限，请联系系统管理员。");
		} else {
			var _title = year_val + '-' + month_val + '_WUCAN_DETAIL';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=wcassit&int_year=' + year_val + '&month=' + month_val
					+ '&int_totalHours=' + _totalHours + '&list_deptIds=' + _arg_ids.join('|') + '&title=' + _title;
		} // end_if
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
