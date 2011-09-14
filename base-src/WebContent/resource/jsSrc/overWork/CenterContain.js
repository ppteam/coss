/**
 * @author 考勤管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var queryForm = null;
	var _dataModel = new DataModel();
	var projectIds = new Array();

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
				xtype : 'checkboxgroup',
				columns : 3,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dataSet_prolist.length; i++) {
				projectIds.push(dataSet_prolist[i][0]);
				ck_groups.items[j] = new Ext.form.Checkbox({
							boxLabel : dataSet_prolist[i][1],
							name : dataSet_prolist[i][0],
							id : dataSet_prolist[i][0]
						});
				j++;
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if
	};

	/**
	 * {绘制UI组件}
	 */
	function buildContain() {
		queryForm = new Ext.FormPanel({
					region : 'center',
					labelWidth : 55,
					title : '项目-月份-加班明细统计',
					frame : true,
					buttons : [{
								text : '全选',
								handler : function(e) {
									selectAllProject();
								}
							}, {
								text : '取消全选',
								handler : function(e) {
									unselectAllProject();
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
										anchor : '15%',
										format : 'Y-m-d',
										value : new Date(),
										allowBlank : false
									}), new Ext.form.DateField({
										fieldLabel : '结束时间',
										id : 'endDate',
										name : 'endDate',
										anchor : '15%',
										format : 'Y-m-d',
										value : new Date(),
										allowBlank : false
									}), {
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
		var start = Ext.getCmp('startDate').getValue(); // 开始日期
		var end = Ext.getCmp('endDate').getValue();// 结束日期
		var ids = new Array();
		if (start > end) {
			throw new Error("结束日期不能小于开始时间。");
		}
		if (!end.between(start, start.add(Date.DAY, 31))) {
			throw new Error("开始日期 与 结束日期不跨度最大为一个月。");
		}

		if (projectIds.length === 0) {
			throw new Error("您没有权限导出该类型的报表，请联系管理人员。");
		}

		for (var i = 0; i < projectIds.length; i++) {
			if (Ext.getCmp(projectIds[i]).getValue()) {
				ids.push(projectIds[i]);
			}
		} // end_for

		if (ids.length === 0) {
			throw new Error("请先指定报表所涉及的项目合集。");
		}
		var _title = start.format('Y-m-d') + '_' + end.format('Y-m-d') + '_加班明细明细';
		window.location.href = 'http://' + window.location.host
				+ '/workbase/report/proxy/view.xls?view=overWorker&date_startDate=' + start.format('Y-m-d')
				+ '&date_endDate=' + end.format('Y-m-d') + '&list_projectIds=' + ids.join('|') + '&title=' + _title;
	} // end_fun

	/**
	 * {全选}
	 */
	function selectAllProject() {
		for (var i = 0; i < projectIds.length; i++) {
			Ext.getCmp(projectIds[i]).setValue(true);
		}
	}

	/**
	 * {取消全选}
	 */
	function unselectAllProject() {
		for (var i = 0; i < projectIds.length; i++) {
			Ext.getCmp(projectIds[i]).setValue(false);
		}
	}
} // end_class

