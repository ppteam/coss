/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var catalogGrid = null;
	var recordGrid = null;
	var _statusbar = null;
	var contain = null;
	var _combax_seleted_Id = null;

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
	 * @function {构造UI函数}
	 */
	function buildContain() {
		builderCatalogGrid();
		var grids_Panel = new Ext.Panel({
					region : 'center',
					layout : 'border',
					items : [catalogGrid]
				});

		// create the Grid
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [grids_Panel]
				});

	} // end_fun_buildContain

	/**
	 * {创建CatalogUI}
	 */
	function builderCatalogGrid() {
		var combo = new Ext.form.ComboBox({
					mode : 'local',
					editable : false,
					typeAhead : true,
					label : '项目列表',
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择项目...',
					store : model.getDateSetProsStore(),
					valueField : 'regValue',
					displayField : 'displayValue',
					width : 250,
					listeners : {
						select : function(/* Ext.form.ComboBox */cb,/* Ext.data.Record */
								rec, /* Number */index) {
							_combax_seleted_Id = rec.get('regValue');
						}
					}
				});

		var expander = new Ext.ux.grid.RowExpander({
			tpl : new Ext.Template(
					'<div style="padding-left:50px"><span style="color:blue;"><b>项目总结：</b></span>{leaderApp}',
					'</div><br/><div style="padding-left:50px"><span style="color:blue;"><b>进展描述：</b></span>{projectDesc}</div>')
		});

		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					plugins : expander,
					store : model.getReportStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : ['-', {
						text : '周报导出',
						iconCls : 'btn_report',
						handler : function(/* Button */btn) {
							var rec = catalogGrid.getSelectionModel().getSelected();
							if (typeof rec === 'undefined') {
								Ext.Msg.alert('操作提示', '请先在下面列表中选择需要导出的周报数据。');
							} else {
								var _title = 'WEEKREPORT_CGB_[PROJECTNAME]_' + rec.get('startDate') + '_'
										+ rec.get('endDate');
								window.location.href = 'http://' + window.location.host
										+ '/workbase/report/proxy/view.xls?view=projWeek&reportID='
										+ rec.get('reportId') + '&int_type=' + rec.get('reportType') + '&title='
										+ _title;
							}
						}
					}, '-', '->', {
						xtype : 'label',
						text : '周报日期:'
					}, {
						xtype : 'datefield',
						id : 'q_week',
						value : null,
						editable : false,
						format : 'Y-m-d'
					}, '-', {
						xtype : 'label',
						text : '项目列表：'
					}, combo, '-', {
						text : '查询',
						iconCls : 'btn_found',
						handler : function() {
							_combax_seleted_Ids = combo.getValue() === '' ? model.initProjectIds() : combo.getValue();
							var q_date = Ext.getCmp('q_week').getValue() === '' ? null : Ext.getCmp('q_week')
									.getValue().format('Y-m-d');
							model.getReportStore().load({
										params : {
											list_projectIds : _combax_seleted_Ids,
											date_week : q_date
										}
									});
						} // end_handler
					}, '-', {
						text : '重置条件',
						iconCls : 'btn_clear',
						handler : function() {
							combo.clearValue();
							Ext.getCmp('q_week').setValue(null);
							model.getReportStore().removeAll();
						}
					}],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getReportStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					columns : [new Ext.grid.RowNumberer(), expander, {
								header : '周报编号',
								width : 125,
								dataIndex : 'reportNo'
							}, {
								header : '项目名称',
								id : 'col_projectName',
								width : 125,
								dataIndex : 'projectName'
							}, {
								header : '项目经理',
								width : 75,
								dataIndex : 'psmerName'
							}, {
								header : '起始日期',
								width : 75,
								dataIndex : 'startDate'
							}, {
								header : '截止日期',
								width : 75,
								dataIndex : 'endDate'
							}, {
								xtype : 'numbercolumn',
								header : '问题数',
								align : 'right',
								format : '0',
								width : 75,
								dataIndex : 'plmCnt'
							}, {
								xtype : 'numbercolumn',
								align : 'right',
								header : '成员人数',
								width : 75,
								format : '0',
								dataIndex : 'members'
							}, {
								header : '项目状态',
								width : 75,
								dataIndex : 'proStats',
								renderer : function(val) {
									var res = null;
									for (var i = 0; i < projectStatus.length; i++) {
										if (val === projectStatus[i][0]) {
											res = projectStatus[i][1];
											break;
										}
									} // end_for
									return res;
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_projectName'
				});

		catalogGrid.getBottomToolbar().on('beforechange', function( /* Ext.PagingToolbar */pbr,/* Object */params) {
					var old_params = model.getReportStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.list_projectIds = old_params.list_projectIds;
						params.date_week = old_params.date_week;
					}
				});
	} // end_fun

} // end_class
