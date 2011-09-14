/**
 * @author 考勤管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var queryForm = null;
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
		if (dataSet_prolist !== null && dataSet_prolist.length !== 0) {
			var ckGroup = Ext.getCmp('proGroup');
			var ck_groups = {
				xtype : 'radiogroup',
				id : 'projectSet',
				columns : 3,
				autoScroll : true,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dataSet_prolist.length; i++) {
				if (i === 0) {
					ck_groups.items[j] = new Ext.form.Radio({
								name : 'project_names',
								boxLabel : dataSet_prolist[i][1],
								inputValue : dataSet_prolist[i][0],
								checked : true
							});
				} else {
					ck_groups.items[j] = new Ext.form.Radio({
								name : 'project_names',
								boxLabel : dataSet_prolist[i][1],
								inputValue : dataSet_prolist[i][0]
							});
				} // end_if
				j++;
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if

		// init combox
		Ext.getCmp('q_year').setValue(model.getToday().format('Y'));
		Ext.getCmp('q_month').setValue(model.getToday().format('n'));
	};

	/**
	 * {绘制UI组件}
	 */
	function buildContain() {
		queryForm = new Ext.FormPanel({
					region : 'center',
					labelWidth : 55,
					title : '项目-日报统计表',
					frame : true,
					buttons : [{
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
								id : 'proGroup',
								name : 'proGroup',
								xtype : 'fieldset',
								anchor : '85%',
								autoScroll : true,
								fieldLabel : '项目列表'
							}]
				});

		initProGroupUI();

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [queryForm]
				});
	} // end_fun_buildContain

	/**
	 * {导出报表}
	 */
	function doExportXls() {
		var proectId_val = Ext.getCmp('projectSet').getValue().getGroupValue();
		var month_val = Ext.getCmp('q_month').getValue();
		var year_val = Ext.getCmp('q_year').getValue();
		var proName = '';
		for (var i = 0; i < dataSet_prolist.length; i++) {
			if (proectId_val === dataSet_prolist[i][0]) {
				proName = dataSet_prolist[i][1];
				break;
			}
		}
		var _title = year_val + '-' + month_val + '_WORK_DETAIL';
		window.location.href = 'http://' + window.location.host
				+ '/workbase/report/proxy/view.xls?view=workDetail&int_year=' + year_val + '&month=' + month_val
				+ '&projectId=' + proectId_val + '&title=' + _title;
	} // end_fun
} // end_class

