/**
 * @author 周报管理
 * @class
 */

function CenterContain() {
	this.init = function(statusbar) {
		_statusbar = statusbar;
	};

	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		}
		return contain;
	};

	var model = new EWeekReportModel();
	model.initDate();

	function doAdd() {
		builderWindowDialog("add");
		dialog.setTitle("填写个人工作周报");
	}

	function doEdit() {
		var rec = eWeekReportGrid.getSelectionModel().getSelected();
		if (typeof rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的周报列表中选择需要删除的的数据。');
		} else {
			builderWindowDialog("modify", rec);
			dialog.setTitle("查看/修改 工作周报[" + rec.get('reportNo') + "]");
		}
	} // end_fun

	/**
	 * {显示弹出窗口动作代码}
	 * 
	 * @param {}
	 *            type
	 */
	function builderWindowDialog(/* string */type,/* Record */rec) {
		builderWindow();
		detailForm.getForm().reset();
		Ext.getCmp('userName').setValue(loginName);
		if (type === "add") {
			Ext.getCmp('startDate').setValue(model.getMinDay());
			Ext.getCmp('endDate').setValue(model.getMaxDay());
			Ext.getCmp('submit-btn').setDisabled(false);
			Ext.getCmp('reset-btn').setDisabled(false);
			model.getDailyReportStore().removeAll();
			_temp_wreport_id = null;
		} else if (type === "modify") {
			var cpy_rec = rec.copy();
			_temp_wreport_id = cpy_rec.get("wreportID");
			detailForm.getForm().loadRecord(cpy_rec);
		}
		dialog.show();
	} // end_fun

	function doRemove() {
		var rec = eWeekReportGrid.getSelectionModel().getSelected();
		if (typeof rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的周报列表中选择需要删除的的数据。');
		} else {
			var remove = rec.copy();
			Ext.MessageBox.confirm('确认操作', '确认删除编号为[' + rec.get('reportNo') + ']的个人周报吗?', function(btn) {
						if (btn === 'yes') {
							remove.set('workSummary', 'remove');
							model.handleAjax(remove.data, dialog);
						} // end_if
					});
		} // end_if
	} // end_fun

	/**
	 * {导出选中的报表}
	 */
	function exportWeekReport() {
		var rec = eWeekReportGrid.getSelectionModel().getSelected();
		if (typeof rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的周报列表中选择操作数据信息。');
		} else {
			var _title = 'CGB_[NAME]_[' + rec.get('endDate').format('W') + ']WEEKS_PROSEN\'s REPORT';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=personWeek&wreportID=' + rec.get('wreportID') + '&title='
					+ _title;
		}
	}

	function doFresh() {
		eWeekReportGrid.getStore().reload(eWeekReportGrid.getStore().lastOptions.params);
	}

	function builderWindow() {
		if (detailForm === null) {
			detailForm = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						region : 'center',
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
															xtype : 'datefield',
															fieldLabel : '起始日期',
															allowBlank : false,
															msgTarget : 'title',
															editable : false,
															name : 'startDate',
															format : 'Y-m-d',
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'textfield',
															fieldLabel : '文档编号',
															name : 'reportNo',
															id : 'reportNo',
															allowBlank : false,
															msgTarget : 'title',
															emptyText : '请填写周报编号',
															anchor : '95%'
														}, {
															id : 'endDate',
															xtype : 'datefield',
															fieldLabel : '截止日期',
															allowBlank : false,
															msgTarget : 'title',
															editable : false,
															name : 'endDate',
															format : 'Y-m-d',
															anchor : '95%'
														}]
											}]
								}, {
									xtype : 'textarea',
									fieldLabel : '工作总结',
									name : 'workSummary',
									id : 'workSummary',
									emptyText : '请如实填写本周工作总结',
									allowBlank : false,
									msgTarget : 'title',
									height : 50,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '下周工作计划',
									name : 'nweekPlan',
									emptyText : '填写下周工作计划安排',
									id : 'nweekPlan',
									allowBlank : false,
									msgTarget : 'title',
									height : 50,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '问题与帮助',
									emptyText : '填写工作中存在的问题以及帮助信息',
									name : 'unsolveProblem',
									id : 'unsolveProblem',
									height : 50,
									anchor : '98%'
								}]
					});

			detailGrid = new Ext.grid.GridPanel({
						region : 'south',
						loadMask : true,
						columnLines : true,
						stripeRows : true,
						split : true,
						height : 250,
						minSize : 200,
						maxSize : 450,
						title : '工作周工作量明细',
						store : model.getDailyReportStore(),
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						viewConfig : {
							forceFit : true
						},
						columns : [new Ext.grid.RowNumberer(), {
									header : '项目名称',
									width : 35,
									id : 'col_projectName',
									dataIndex : 'projectName'
								}, {
									header : '日报日期',
									width : 25,
									xtype : 'datecolumn',
									dataIndex : 'reportDate',
									format : 'Y-m-d'
								}, {
									header : '星期',
									width : 15,
									renderer : function(value, metaData, record, rowIndex, colIndex, store) {
										var int_date = new Date(record.get('reportDate'));
										var _week = '';
										switch (int_date.format('N')) {
											case 0 :
												_week = '周日';
												break;
											case 1 :
												_week = '周一';
												break;
											case 2 :
												_week = '周二';
												break;
											case 3 :
												_week = '周三';
												break;
											case 4 :
												_week = '周四';
												break;
											case 5 :
												_week = '周五';
												break;
											case 6 :
												_week = '周六';
												break;
											default :
												_week = int_date.format('N');
												break;
										}// end_sw
										return _week;
									}
								}, {
									header : '工作类别',
									width : 15,
									dataIndex : 'workType',
									renderer : function(val) {
										var _val = '';
										for (var i = 0; i < manHourType.length; i++) {
											if (manHourType[i][0] === val) {
												_val = manHourType[i][1];
												break;
											}
										}
										return _val;
									}
								}, {
									header : '项目活动',
									width : 15,
									dataIndex : 'workActivity',
									renderer : function(val) {
										var _val = '';
										for (var i = 0; i < projActivity.length; i++) {
											if (projActivity[i][0] === val) {
												_val = projActivity[i][1];
												break;
											}
										}
										return _val;
									}
								}, {
									header : '工时数',
									width : 15,
									dataIndex : 'workHours',
									renderer : function(val) {
										var _name = parseFloat(val) / 100;
										return _name.toFixed(2);
									}
								}],
						autoExpandColumn : 'col_projectName'
					});

		} // end_if
		if (dialog === null) {
			dialog = new Ext.Window({
						modal : true,
						width : 800,
						height : 550,
						closeAction : 'hide',
						closable : true,
						layout : 'border',
						resizable : false,
						items : [detailForm, detailGrid],
						buttons : [{
									text : '查询明细',
									handler : function(/* Button */btn,/* Event */
											e) {
										var startDay = Ext.getCmp('startDate').getValue();
										var endDay = Ext.getCmp('endDate').getValue();
										model.getDailyReportStore().load({
													params : {
														date_startDate : startDay.format('Y-m-d'),
														date_endDate : endDay.format('Y-m-d')
													}
												});
									}
								}, {
									text : '提交',
									id : 'submit-btn',
									handler : function(/* Button */btn,/* Event */
											e) {
										if (detailForm.getForm().isValid()) {
											var instance = detailForm.getForm().getFieldValues();
											instance.wreportID = _temp_wreport_id;
											if (instance.startDate.add(Date.DAY, 6).format('Y-m-d') !== instance.endDate
													.format('Y-m-d')) {
												Ext.MessageBox.confirm('确认操作', '周报日期跨度不是一周，是否确认提交?', function(btn) {
															if (btn === 'yes') {
																model.handleAjax(instance, dialog);
															} // end_if
														});
											} else {
												model.handleAjax(instance, dialog);
											}
										} // end_if
									}
								}, {
									text : '重置',
									id : 'reset-btn',
									handler : function(/* Button */btn,/* Event */
											e) {
										detailForm.getForm().reset();
									}
								}, {
									text : '取消',
									handler : function(/* Button */btn,/* Event */
											e) {
										dialog.hide();
									}
								}]
					});
		}
	}

	/**
	 * @function buildContain
	 */
	function buildContain() {
		var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
					header : '周报编号',
					width : 150,
					dataIndex : 'reportNo'
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

		var tba = new Ext.Toolbar([{
					text : '新增',
					iconCls : 'btn_add_data',
					handler : doAdd
				}, '-', {
					text : '查看 / 编辑',
					iconCls : 'btn_view_edit',
					handler : doEdit
				}, '-', {
					text : '删除',
					iconCls : 'btn_del_data',
					handler : doRemove
				}, '-', {
					text : '刷新',
					iconCls : 'btn_refresh',
					handler : doFresh
				}, '->', '-', {
					text : '导出报表',
					iconCls : 'btn_report',
					handler : exportWeekReport
				}]);

		eWeekReportGrid = new Ext.grid.GridPanel({
					region : 'center',
					stripeRows : true,
					columnLines : true,
					store : model.getEWeekReportListStore(),
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
								store : model.getEWeekReportListStore(),
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							})
				});

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : eWeekReportGrid
				});
	} // end_fun_buildContain

	var contain = null;
	var _statusbar = null;
	var dialog = null;
	var detailForm = null;
	var detailGrid = null;
	var _temp_wreport_id = null;
	var eWeekReportGrid = null;
} // end_class
