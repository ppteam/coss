/**
 * @author 查看日报
 * @class dailyReport Manager Panle
 */

function CenterContain() {

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
	 * @function buildContain
	 */
	function buildContain() {
		var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
					header : '周报编号',
					width : 150,
					dataIndex : 'reportNo'
				}, {
					header : '姓名',
					width : 65,
					dataIndex : 'userName'
				}, {
					header : '部门名称',
					width : 85,
					dataIndex : 'deptName'
				}, {
					xtype : 'datecolumn',
					width : 85,
					format : 'Y-m-d',
					header : '起始日期',
					dataIndex : 'startDate'
				}, {
					xtype : 'datecolumn',
					width : 85,
					format : 'Y-m-d',
					header : '截止日期',
					dataIndex : 'endDate'
				}, {
					xtype : 'datecolumn',
					width : 85,
					format : 'Y-m-d',
					header : '填写日期',
					dataIndex : 'recordDate'
				}, {
					header : '总工时',
					width : 85,
					dataIndex : 'norHours',
					renderer : function(val) {
						if (val !== 0) {
							return (parseFloat(val) / 100).toFixed(2);
						} else {
							return '0.00';
						}
					}
				}, {
					header : '加班工时',
					width : 85,
					dataIndex : 'addHours',
					renderer : function(val) {
						if (val !== 0) {
							return (parseFloat(val) / 100).toFixed(2);
						} else {
							return '0.00';
						}
					}
				}, {
					header : '休假工时',
					width : 85,
					dataIndex : 'evlHours',
					renderer : function(val) {
						if (val !== 0) {
							return (parseFloat(val) / 100).toFixed(2);
						} else {
							return '0.00';
						}
					}
				}, {
					id : 'col_workSummary',
					header : '工作内容',
					dataIndex : 'workSummary'
				}]);

		var tba = new Ext.Toolbar(['-', {
					text : '查看明细',
					iconCls : 'btn_detail',
					handler : function(btn) {
						viewDetail();
					}
				}, '-', '->', {
					xtype : 'label',
					text : '项目名称：'
				}, {
					id : 'q_projectId',
					xtype : "combo",
					msgTarget : 'title',
					name : 'q_projectId',
					width : 150,
					triggerAction : 'all',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					emptyText : '请选择项目...',
					mode : 'local',
					store : model.getProjectListStore(),
					valueField : 'regValue',
					displayField : 'displayValue'
				}, '-', {
					xtype : 'label',
					text : '员工姓名：'
				}, {
					id : 'q_userName',
					xtype : "textfield",
					emptyText : '模糊匹配...',
					width : 95
				}, '-', {
					text : '查询',
					iconCls : 'btn_found',
					handler : function(/* Button */btn) {
						queryAction();
					}
				}, '-', {
					text : '重置条件',
					iconCls : 'btn_clear',
					handler : function() {
						Ext.getCmp('q_projectId').clearValue();
						Ext.getCmp('q_userName').setValue(null);
						model.getReportInfoStore().removeAll();
					}
				}]);

		reportGrid = new Ext.grid.GridPanel({
					region : 'center',
					stripeRows : true,
					columnLines : true,
					store : model.getReportInfoStore(),
					cm : cm,
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					autoExpandColumn : 'col_workSummary',
					loadMask : {
						msg : '正在加载数据,请稍候......'
					},
					tbar : tba,
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getReportInfoStore(),
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							})
				});

		reportGrid.getBottomToolbar().on('beforechange', function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getReportInfoStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.projectId = old_params.projectId;
						params.userId = old_params.userId;
						params.isSelf = old_params.isSelf;
					}
				});

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : reportGrid
				});

	} // end_fun_buildContain

	/**
	 * {查看周报数据明细}
	 */
	function viewDetail() {
		builderDetailUI();
		var selected_rec = reportGrid.getSelectionModel().getSelected();
		if (typeof selected_rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的周报列表中选择需要查看的数据。');
			return;
		} else {
			p_record = selected_rec.copy();
			var _startDate = selected_rec.get('startDate').format('Y-m-d');
			var _endDate = selected_rec.get('endDate').format('Y-m-d');
			p_record.set('startDate', _startDate);
			p_record.set('endDate', _endDate);
			// alert(dwr.util.toDescriptiveString(p_record.data, 2));
			detailForm.getForm().loadRecord(p_record);
			model.getDetailStore().load({
						params : {
							param_userID : selected_rec.get('userID'),
							param_startDate : _startDate + '|Date|yyyy-MM-dd',
							param_endDate : _endDate + '|Date|yyyy-MM-dd'
						}
					});
		} // end_if
		dialog.show();
	} // end_fun

	/**
	 * {执行查询事件}
	 */
	function queryAction() {
		var _projectIds = Ext.getCmp('q_projectId').getValue();
		var _userName = Ext.getCmp('q_userName').getValue();
		_userName = _userName === '' ? null : '%' + _userName + '%';
		_projectIds = _projectIds === '' ? model.getProjectList() : _projectIds;
		model.getReportInfoStore().load({
					params : {
						int_start : 0,
						int_limit : model.getPageSize(),
						list_projectIds : _projectIds,
						userName : _userName
					}
				});
	} // end_fun

	/**
	 * {加载项目明细UI 初始化}
	 */
	function builderDetailUI() {
		if (detailForm === null) {
			detailForm = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						title : '周报总结',
						frame : true,
						items : [{
									layout : 'column',
									items : [{
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'textfield',
															fieldLabel : '姓名',
															name : 'userName',
															id : 'userName',
															disabled : true,
															anchor : '95%'
														}, {
															id : 'startDate',
															xtype : 'textfield',
															fieldLabel : '起始日期',
															name : 'startDate',
															disabled : true,
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'textfield',
															fieldLabel : '文档编号',
															name : 'reportNo',
															disabled : true,
															anchor : '95%'
														}, {
															id : 'endDate',
															xtype : 'textfield',
															fieldLabel : '截至日期',
															name : 'endDate',
															disabled : true,
															anchor : '95%'
														}]
											}]
								}, {
									xtype : 'textarea',
									fieldLabel : '工作总结',
									name : 'workSummary',
									disabled : true,
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '下周工作计划',
									name : 'nweekPlan',
									disabled : true,
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '问题与帮助',
									disabled : true,
									name : 'unsolveProblem',
									height : 80,
									anchor : '98%'
								}]
					}); // end_new
		} // end_if

		if (detailGrid === null) {
			var detailCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
						header : '项目名称',
						width : 150,
						dataIndex : 'projectName'
					}, {
						header : '姓名',
						width : 55,
						dataIndex : 'userName'
					}, {
						xtype : 'datecolumn',
						format : 'Y-m-d',
						header : '日期',
						width : 65,
						dataIndex : 'reportDate'
					}, {
						header : '工时数',
						width : 45,
						dataIndex : 'workHours',
						renderer : function(val) {
							if (val !== 0) {
								return (parseFloat(val) / 100).toFixed(2);
							} else {
								return '0.00';
							}
						}
					}, {
						header : '工作类别',
						width : 55,
						dataIndex : 'workType'
					}, {
						header : '项目活动',
						width : 50,
						dataIndex : 'workSubActivity'
					}, {
						id : 'col_workContent',
						header : '工作内容',
						dataIndex : 'workContent'
					}]);
			detailGrid = new Ext.grid.GridPanel({
						title : '日报明细',
						stripeRows : true,
						columnLines : true,
						store : model.getDetailStore(),
						cm : detailCm,
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						autoExpandColumn : 'col_workContent',
						loadMask : {
							msg : '正在加载数据,请稍候......'
						}
					});
		} // end_if

		if (dialog === null) {
			dialog = new Ext.Window({
						modal : true,
						width : 750,
						height : 440,
						title : '周报明细对话框',
						closeAction : 'hide',
						closable : true,
						layout : 'border',
						resizable : false,
						items : [{
									xtype : 'tabpanel',
									activeTab : 0,
									region : 'center',
									items : [detailForm, detailGrid]
								}],
						buttons : [{
									text : '确定',
									handler : function(/* Button */btn,/* Event */
											e) {
										dialog.hide();
									}
								}]
					});
		} // end_fun
	} // end_fun
	var contain = null;
	var _statusbar = null;
	var detailForm = null;
	var dialog = null;
	var p_record = null;// 全局指针
	var reportGrid = null;
	var detailGrid = null;
} // end_class
