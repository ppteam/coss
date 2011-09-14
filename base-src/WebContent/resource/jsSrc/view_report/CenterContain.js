/**
 * @author 查看日报
 * @class dailyReport Manager Panle
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

	var model = new ViewDailyReportModel();// 定义数据模板

	function viewDetail() {
		if (detailForm === null) {
			detailForm = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						region : 'center',
						buttonAlign : 'center',
						bodyStyle : 'padding:5px 5px 0',
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
															anchor : '95%',
															readOnly : true
														}, {
															xtype : 'textfield',
															fieldLabel : '项目名称',
															name : 'projectName',
															anchor : '95%',
															readOnly : true
														}, {
															xtype : 'datefield',
															fieldLabel : '日报日期',
															name : 'reportDate',
															format : 'Y-m-d',
															anchor : '95%',
															readOnly : true
														}, {
															xtype : 'textfield',
															fieldLabel : '工时数',
															name : 'workHours',
															anchor : '95%',
															readOnly : true
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : "textfield",
															fieldLabel : '工时类别',
															name : 'workType',
															anchor : '95%',
															readOnly : true
														}, {
															xtype : "textfield",
															fieldLabel : '项目活动',
															name : 'workActivity',
															anchor : '95%',
															readOnly : true
														}, {
															xtype : "textfield",
															fieldLabel : '项目子活动',
															name : 'workSubActivity',
															anchor : '95%',
															readOnly : true
														}, {
															xtype : "textfield",
															fieldLabel : '状态',
															name : 'workStats',
															anchor : '95%',
															readOnly : true
														}]
											}]
								}, {
									xtype : 'textarea',
									fieldLabel : '工作内容',
									name : 'workContent',
									height : 80,
									anchor : '98%',
									readOnly : true
								}, {
									xtype : 'textarea',
									fieldLabel : '提交成果物',
									name : 'resultsShow',
									height : 80,
									anchor : '98%',
									readOnly : true
								}, {
									xtype : 'textarea',
									fieldLabel : '备注',
									name : 'repComment',
									height : 80,
									anchor : '98%',
									readOnly : true
								}]
					});
		} // end_if
		if (win === null) {
			win = new Ext.Window({
						title : '日报详情',
						layout : 'border',
						width : 600,
						height : 440,
						constrain : true,
						closeAction : 'hide',
						resizable : false,
						shadow : true,
						modal : true,
						closable : true,
						bodyStyle : 'padding:5 5 5 5',
						items : [detailForm],
						buttons : [{
									text : '确定',
									handler : function(/* Button */btn,/* Event */
											e) {
										win.hide();
									}
								}]
					});
		} // end_if
		var record = viewDailyReportGrid.getSelectionModel().getSelected().copy();
		// alert(dwr.util.toDescriptiveString(record.data, 2));
		// format workhours
		var _workHours = (record.data.workHours / 100).toFixed(2);
		record.set('workHours', _workHours);
		// workType
		record.set('workType', getDictValue(manHourType, record.data.workType));
		// workActivity
		record.set('workActivity', getDictValue(projActivity, record.data.workActivity));
		// workSubActivity
		record.set('workSubActivity', getDictValue(subProjActivity, record.data.workSubActivity));
		// workStats
		record.set('workStats', getDictValue(dailyReportWorkStatus, record.data.workStats));
		detailForm.getForm().reset();
		detailForm.getForm().loadRecord(record);
		win.show();
	} // end_fun

	/**
	 * {初始化数据字典信息}
	 * 
	 * @param {}
	 *            dict
	 * @param {}
	 *            key
	 * @return {}
	 */
	function getDictValue(/* Array */dict,/* String */key) {
		var res = '';
		for (var i = 0; i < dict.length; i++) {
			if (key === dict[i][0]) {
				res = dict[i][1];
				break;
			}
		}
		return res;
	}

	/**
	 * @function buildContain
	 */
	function buildContain() {
		var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
					header : '项目名称',
					width : 75,
					dataIndex : 'projectName'
				}, {
					header : '用户姓名',
					width : 30,
					dataIndex : 'userName'
				}, {
					header : '部门名称',
					width : 30,
					dataIndex : 'deptName'
				}, {
					header : '日报日期',
					width : 25,
					xtype : 'datecolumn',
					dataIndex : 'reportDate',
					format : 'Y-m-d'
				}, {
					header : '星期',
					width : 20,
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
					header : '工时数',
					width : 15,
					dataIndex : 'workHours',
					renderer : function(val) {
						var _name = null;
						model.getAllDailyReportInfoStore().each(function(/* Ext.data.Record */rec) {
									if (rec.get('workHours') === val) {
										_name = parseFloat(rec.get('workHours')) / 100;
									}
								});
						return _name.toFixed(2);
					}
				}, {
					header : '工作类别',
					width : 30,
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
					width : 30,
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
					id : 'col_workContent',
					header : '工作内容',
					dataIndex : 'workContent'
				}]);

		var tba = new Ext.Toolbar(['->', {
					xtype : 'label',
					text : '项目名称：'
				}, {
					id : 'q_projectId',
					xtype : "combo",
					msgTarget : 'title',
					name : 'q_projectId',
					triggerAction : 'all',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					emptyText : '请选择项目(可以为空)...',
					mode : 'local',
					store : model.getProjectListStore(),
					valueField : 'regValue',
					displayField : 'displayValue',
					width : 250
				}, '-', {
					xtype : 'label',
					text : '员工姓名：'
				}, {
					id : 'q_userName',
					xtype : "textfield",
					emptyText : '模糊匹配...',
					width : 95
				}, '-', {
					xtype : 'label',
					text : '日报日期：'
				}, {
					id : 'q_reportDate',
					xtype : "datefield",
					value : null,
					editable : false,
					format : 'Y-m-d',
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
						Ext.getCmp('q_userName').reset();
						Ext.getCmp('q_reportDate').reset();
						model.getAllDailyReportInfoStore().removeAll();
					}
				}]);

		viewDailyReportGrid = new Ext.grid.GridPanel({
					region : 'center',
					stripeRows : true,
					columnLines : true,
					store : model.getAllDailyReportInfoStore(),
					cm : cm,
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					autoScroll : true,
					autoExpandColumn : 'col_workContent',
					loadMask : {
						msg : '正在加载数据,请稍候......'
					},
					viewConfig : {
						forceFit : true
					},
					tbar : tba,
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getAllDailyReportInfoStore(),
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							})
				});
		// 查询时必须有此方法，才能正常分页
		viewDailyReportGrid.getBottomToolbar().on('beforechange',
				function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getAllDailyReportInfoStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.projectId = old_params.projectId;
						params.userName = old_params.userName;
						params.reportDate = old_params.reportDate;
					}
				});

		// 双击打开日报详情窗口
		viewDailyReportGrid.on('dblclick', function() {
					viewDetail();
				});

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : viewDailyReportGrid
				});

	} // end_fun_buildContain

	/**
	 * {执行查询事件}
	 */
	function queryAction() {
		var _projectId = Ext.getCmp('q_projectId').getValue() === '' ? null : Ext.getCmp('q_projectId').getValue();
		var _userName = Ext.getCmp('q_userName').getValue() === '' ? null : '%' + Ext.getCmp('q_userName').getValue()
				+ '%';
		var _reportDate = Ext.getCmp('q_reportDate').getValue() === '' ? null : Ext.getCmp('q_reportDate').getValue()
				.format('Y-m-d');
		model.getAllDailyReportInfoStore().load({
					params : {
						int_start : 0,
						int_limit : model.getPageSize(),
						projectId : _projectId,
						reportDate : _reportDate,
						userName : _userName
					}
				});
	} // end_fun

	var contain = null;
	var _statusbar = null;
	var detailForm = null;
	var p_self = false;
	var win = null;
	var viewDailyReportGrid = null;
} // end_class
