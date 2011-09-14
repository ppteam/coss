/**
 * @author 个人日报管理
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

	var model = new DailyReportModel();// 定义数据模板

	/**
	 * {创建批量填写日报信息}
	 */
	function builderBatWin() {
		if (formBat === null) {
			formBat = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						region : 'center',
						autoScroll : true,
						buttonAlign : 'center',
						bodyStyle : 'padding:5px 5px 0',
						frame : true,
						items : [{
									layout : 'column',
									items : [{
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'combo',
															triggerAction : 'all',
															fieldLabel : '项目名称',
															emptyText : '请选择',
															allowBlank : false,
															msgTarget : 'title',// 显示错误信息
															name : 'bat_projectID',
															id : 'bat_projectID',
															mode : 'local',
															triggerAction : 'all',
															editable : false,
															store : model.getDailyReportProNameSelfStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															anchor : '95%'
														}, {
															id : 'bat_startDate',
															xtype : 'datefield',
															fieldLabel : '开始日期',
															allowBlank : false,
															msgTarget : 'title',
															editable : false,
															name : 'bat_startDate',
															value : new Date(),
															format : 'Y-m-d',
															anchor : '95%'
														}, {
															id : 'bat_workHours',
															xtype : 'numberfield',
															allowNegative : false,
															fieldLabel : '工时数',
															name : 'bat_workHours',
															value : 8,
															maxValue : 24,
															allowBlank : false,
															msgTarget : 'title',
															anchor : '95%'
														}, {
															id : 'bat_cludeWeek',
															xtype : 'checkbox',
															fieldLabel : '忽略周末',
															checked : true,
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : "combo",
															fieldLabel : '日报类型',
															msgTarget : 'title',
															triggerAction : 'all',
															name : 'bat_reportType',
															id : 'bat_reportType',
															allowBlank : false,
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getRepTypeStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															anchor : '95%'
														}, {
															id : 'bat_endDate',
															xtype : 'datefield',
															fieldLabel : '截至日期',
															allowBlank : false,
															msgTarget : 'title',
															editable : false,
															name : 'bat_endDate',
															value : new Date(),
															format : 'Y-m-d',
															anchor : '95%'
														}, {
															xtype : "combo",
															fieldLabel : '休假类别',
															triggerAction : 'all',
															name : 'bat_begType',
															id : 'bat_begType',
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															emptyText : '请假时需指定',
															store : model.getBegoffTypeStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															emptyText : '请选择',
															anchor : '95%'
														}]
											}]
								}]
					});

		} // end_if

		if (dialogBat === null) {
			dialogBat = new Ext.Window({
						constrain : true,
						modal : true,
						width : 600,
						height : 200,
						closeAction : 'hide',
						closable : true,
						layout : 'fit',
						activeItem : 0,
						autoScroll : true,
						resizable : false,
						shadow : true,
						bodyStyle : 'padding:5 5 5 5',
						animCollapse : true,
						items : [formBat],
						buttons : [{
									text : '提交',
									handler : function(/* Button */btn,/* Event */
											e) {
										doBatUpdate();
									}
								}, {
									text : '重置',
									handler : function(/* Button */btn,/* Event */
											e) {
										formBat.getForm().reset();
									}
								}, {
									text : '取消',
									handler : function(/* Button */btn,/* Event */
											e) {
										dialogBat.hide();
									}
								}]
					});
		} // end_if
		if (project_info_data.length > 0) {
			Ext.getCmp('bat_projectID').setValue(project_info_data[0][0]);
		}
		dialogBat.show();
	} // end_fun

	function builderWindow() {
		if (formDetail === null) {
			formDetail = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						region : 'center',
						autoScroll : true,
						buttonAlign : 'center',
						bodyStyle : 'padding:5px 5px 0',
						frame : true,
						items : [{
									layout : 'column',
									items : [{
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : 'combo',
															triggerAction : 'all',
															fieldLabel : '项目名称',
															emptyText : '请选择',
															allowBlank : false,
															msgTarget : 'title',// 显示错误信息
															name : 'projectID',
															id : 'projectID',
															mode : 'local',
															triggerAction : 'all',
															editable : false,
															store : model.getDailyReportProNameSelfStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															anchor : '95%'
														}, {
															id : 'reportDate',
															xtype : 'datefield',
															fieldLabel : '日报日期',
															allowBlank : false,
															msgTarget : 'title',
															editable : false,
															name : 'reportDate',
															value : new Date(),
															format : 'Y-m-d',
															anchor : '95%'
														}, {
															id : 'workHours',
															xtype : 'numberfield',
															allowNegative : false,
															fieldLabel : '工时数',
															name : 'workHours',
															value : 8,
															minValue : 0,
															maxValue : 24,
															allowBlank : false,
															msgTarget : 'title',
															anchor : '95%'
														}, {
															xtype : "combo",
															fieldLabel : '状态',
															msgTarget : 'title',
															triggerAction : 'all',
															name : 'workStats',
															id : 'workStats',
															editable : false,
															emptyText : '请选择',
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getDailyReportWorkStatusStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															anchor : '95%'
														}, {
															xtype : "numberfield",
															fieldLabel : '项目进度',
															msgTarget : 'title',
															triggerAction : 'all',
															minValue : 0,
															emptyText : '主机日报填写',
															maxValue : 100,
															name : 'workSchde',
															id : 'workSchde',
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
															xtype : "combo",
															fieldLabel : '日报类型',
															msgTarget : 'title',
															triggerAction : 'all',
															name : 'reportType',
															id : 'reportType',
															allowBlank : false,
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getRepTypeStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															anchor : '95%'
														}, {
															id : 'workType',
															xtype : "combo",
															fieldLabel : '工时类别',
															allowBlank : false,
															msgTarget : 'title',// 显示错误信息
															name : 'workType',
															triggerAction : 'all',
															editable : false,
															value : 0,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getManHourTypeStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															emptyText : '请选择',
															anchor : '95%',
															listeners : {
																change : function(cmb, newValue, oldValue) {
																	setFormByType(newValue);
																}
															}
														}, {
															xtype : "combo",
															fieldLabel : '项目活动',
															triggerAction : 'all',
															name : 'workActivity',
															id : 'workActivity',
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getProjActivityStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															emptyText : '请选择',
															anchor : '95%'
														}, {
															xtype : "combo",
															fieldLabel : '项目子活动',
															triggerAction : 'all',
															name : 'workSubActivity',
															id : 'workSubActivity',
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															store : model.getSubProjActivityStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															emptyText : '请选择',
															anchor : '95%'
														}, {
															xtype : "combo",
															fieldLabel : '休假类别',
															triggerAction : 'all',
															name : 'begType',
															id : 'begType',
															editable : false,
															typeAhead : true,
															forceSelection : true,
															mode : 'local',
															emptyText : '请假时需指定',
															store : model.getBegoffTypeStore(),
															valueField : 'regValue',
															displayField : 'displayValue',
															emptyText : '请选择',
															anchor : '95%'
														}]
											}]
								}, {
									xtype : 'textarea',
									fieldLabel : '工作内容',
									emptyText : '输入本周工作内容总结,（主机对应工作计划）',
									name : 'workContent',
									id : 'workContent',
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '提交成果物',
									emptyText : '输入本周工作可提交的成果物,（主机对应工作内容）',
									name : 'resultsShow',
									id : 'resultsShow',
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '备注',
									emptyText : '输入本周工作其他情况说明',
									name : 'repComment',
									id : 'repComment',
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '延迟原因',
									emptyText : '输入该功能延迟原因，主机日报填写',
									name : 'delayAffect',
									id : 'delayAffect',
									height : 80,
									anchor : '98%'
								}, {
									xtype : 'textarea',
									fieldLabel : '延迟解决',
									emptyText : '输入该功能延迟解决方案，主机日报填写',
									name : 'delaySolve',
									id : 'delaySolve',
									height : 80,
									anchor : '98%'
								}]
					});
		} // end_if

		if (dialog === null) {
			dialog = new Ext.Window({
						constrain : true,
						modal : true,
						width : 600,
						height : 470,
						closeAction : 'hide',
						closable : true,
						layout : 'fit',
						activeItem : 0,
						autoScroll : true,
						resizable : false,
						shadow : true,
						bodyStyle : 'padding:5 5 5 5',
						animCollapse : true,
						items : [formDetail],
						buttons : [{
							text : '提交',
							id : 'submit-btn',
							handler : function(/* Button */btn,/* Event */
									e) {
								if (formDetail.getForm().isValid()) {
									var instance = formDetail.getForm().getFieldValues();
									instance.dreportID = _temp_dreport_id;
									instance.reportDate = Ext.getCmp('reportDate').getValue();
									/* 正常 以及 加班 */
									if (instance.workType === "0d0cc048adf440edb55d8e53d756439c"
											|| instance.workType === "dba8abe0a65945a49ebfc587c947d920") {
										if (instance.workHours > 24 || instance.workHours <= 0) {
											Ext.Msg.alert('提示', '一天的正常工时应当为 （0,24] 之间的数值');
											return;
										}

										if (instance.workActivity === '') {
											Ext.Msg.alert('提示', '请完善 项目活动 项数据。');
											return;
										}
										if (instance.workSubActivity === '') {
											Ext.Msg.alert('提示', '请完善 项目子活动 项数据。');
											return;
										}
										if (instance.workContent === '') {
											Ext.Msg.alert('提示', '请填写工作内容。');
											return;
										}
										if (instance.resultsShow === '') {
											Ext.Msg.alert('提示', '请填写成果物提交项。');
											return;
										}

										// 网银类交易
										if (instance.reportType === '0' && instance.workStats === '') {
											Ext.Msg.alert('提示', '网银日报请完善 工作状态 项数据。');
											return;
										}

										if (instance.reportType === '1' && instance.workSchde === '') {
											Ext.Msg.alert('提示', '主机日报请完善 工作进度(%) 项数据。');
											return;
										}
									}

									/* 请假 */
									if (instance.workType === "3692b9cd731a452ea9e9c55efa5c9618") {
										if (instance.workHours <= 0 || instance.workHours % 4 != 0) {
											Ext.Msg.alert('提示', '请假工时必须是4的倍数。');
											return;
										} else if (instance.workHours > 8) {
											Ext.Msg.alert('提示', '一天请假工时不能大于8小时。');
											return;
										}

										if (instance.begType === '') {
											Ext.Msg.alert('提示', '请注明请假类别。');
											return;
										}
									} else {
										instance.begType = null;
									}

									/* 加班 */
									if (instance.workType === "dba8abe0a65945a49ebfc587c947d920") {
										if (instance.workHours <= 0 || instance.workHours > 24) {
											Ext.Msg.alert('提示', '一天的加班工时范围 应当为 （0，24] 之间');
											return;
										}
									}
									instance.workHours = parseInt(parseFloat(instance.workHours) * 100, 10);
									if (formDetail.getForm().isDirty()) {
										if (action_type === 'modify') {
											var rec = dailyReportGrid.getSelectionModel().getSelected();
											instance.projectID = rec.get('projectID');
											instance.reportDate = rec.get('reportDate');
										}
										model.handleAjax(instance, action_type, dialog);
									} else {
										dialog.hide();
									}
								}
							}
						}, {
							text : '重置',
							id : 'reset-btn',
							handler : function(/* Button */btn,/* Event */
									e) {
								formDetail.getForm().reset();
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
	} // end_fun

	function builderWindowDialog(/* string */type) {
		builderWindow();
		action_type = type;
		formDetail.getForm().reset();
		if (type === 'add') {
			_temp_dreport_id = null;
			setFormByType('add');
			var rec = dailyReportGrid.getSelectionModel().getSelected();
			if (typeof rec === 'undefined' && dailyReportGrid.getStore().getTotalCount() !== 0) {
				rec = dailyReportGrid.getStore().getAt(0);
			}

			if (typeof rec !== 'undefined') {
				rec = rec.copy();
				rec.set('reportDate', new Date());
				rec.set('workHours', rec.get('workHours') / 100);
				formDetail.getForm().loadRecord(rec);
			} else {
				if (project_info_data.length > 0) {
					Ext.getCmp('projectID').setValue(project_info_data[0][0]);
				}
				Ext.getCmp('workHours').setValue(8);
				Ext.getCmp('reportType').setValue(0);
				Ext.getCmp('reportDate').setValue(new Date());
			} // end_if

			Ext.getCmp('projectID').setDisabled(false);
			Ext.getCmp('reportDate').setDisabled(false);
			Ext.getCmp('submit-btn').setDisabled(false);
			Ext.getCmp('reset-btn').setDisabled(false);
		} else if (type === 'modify') {
			var rec = dailyReportGrid.getSelectionModel().getSelected().copy();
			_temp_dreport_id = rec.get('dreportID');
			Ext.getCmp('projectID').setDisabled(true);
			Ext.getCmp('reportDate').setDisabled(true);
			var hours = parseFloat(rec.get('workHours') / 100).toFixed(2);
			rec.set('workHours', hours);
			setFormByType(rec.get('workType'));
			// var isEdit = !rec.get('reportDate').between(model.getMinDay(),
			// model.getMaxDay());
			// Ext.getCmp('reset-btn').setDisabled(isEdit);
			// Ext.getCmp('submit-btn').setDisabled(isEdit);
			formDetail.getForm().loadRecord(rec);
		} // end_if
		dialog.show();
	} // end_fun

	/**
	 * {按照选择的工时状态绘制表单}
	 */
	function setFormByType(/* String */_typeVal) {
		if (_typeVal === '3692b9cd731a452ea9e9c55efa5c9618') {
			Ext.getCmp('workStats').setDisabled(true);
			Ext.getCmp('workActivity').setDisabled(true);
			Ext.getCmp('workSubActivity').setDisabled(true);
			Ext.getCmp('workContent').setDisabled(true);
			Ext.getCmp('resultsShow').setDisabled(true);
			Ext.getCmp('delaySolve').setDisabled(true);
			Ext.getCmp('delayAffect').setDisabled(true);
			Ext.getCmp('repComment').setDisabled(true);
			Ext.getCmp('workSchde').setDisabled(true);
			Ext.getCmp('begType').setDisabled(false);
		} else {
			Ext.getCmp('workStats').setDisabled(false);
			Ext.getCmp('workActivity').setDisabled(false);
			Ext.getCmp('workSubActivity').setDisabled(false);
			Ext.getCmp('workContent').setDisabled(false);
			Ext.getCmp('resultsShow').setDisabled(false);
			Ext.getCmp('delaySolve').setDisabled(false);
			Ext.getCmp('delayAffect').setDisabled(false);
			Ext.getCmp('repComment').setDisabled(false);
			Ext.getCmp('workSchde').setDisabled(false);
			Ext.getCmp('begType').setDisabled(true);
		}
	} // end_if

	// 显示新增窗口
	function doAdd() {
		builderWindowDialog("add");
		dialog.setTitle("新增日报");
	}

	/**
	 * {批量提交请求规则}
	 */
	function doBatUpdate() {
		var args = new Array();
		var startDate = Ext.getCmp('bat_startDate').getValue();
		var endDate = Ext.getCmp('bat_endDate').getValue();
		var _projectId = Ext.getCmp('bat_projectID').getValue();
		var _reportType = Ext.getCmp('bat_reportType').getValue();
		var _begType = Ext.getCmp('bat_begType').getValue();
		var _workHours = Ext.getCmp('bat_workHours').getValue();
		var igoreWeek = Ext.getCmp('bat_cludeWeek').getValue();
		try {
			if (_workHours <= 0 || _workHours > 8) {
				throw new Error('工时填写范围在 0--8 小时内的整数。');
			} // end_if

			if (endDate.format('U') < startDate.format('U')) {
				throw new Error('截至日期大于起始日期，请重新选择日期。');
			} // end_if

			do {
				var obj = {
					projectID : _projectId,
					reportType : _reportType,
					begType : _begType,
					workHours : _workHours * 100,
					reportDate : endDate,
					workType : '3692b9cd731a452ea9e9c55efa5c9618'
				};

				// 校验
				for (var p in obj) {
					if (obj[p] === null || obj[p] === '') {
						throw new Error('请完善提交数据内容。');
					}
				} // end_for
				if (igoreWeek) {
					if (endDate.format('N') !== 6 && endDate.format('N') !== 7) {
						args.push(obj);
					}
				} else {
					args.push(obj);
				}
				endDate = endDate.add(Date.DAY, -1);
			} while (startDate.add(Date.DAY, -1).format('Y-m-d') !== endDate.format('Y-m-d'));
			if (args.length === 0) {
				throw new Error('当前日期选择不对，请校验起止日期。');
			}

			model.handleBatchAjax(args, dialogBat);
		} catch (e) {
			Ext.Msg.alert('操作提示', e.message);
		} // end_try
	} // end_fun

	// 显示修改的窗口
	function doEdit() {
		var rec = dailyReportGrid.getSelectionModel().getSelected();
		if (typeof rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的日报列表中选择需要编辑的数据项。');
		} else {
			builderWindowDialog("modify");
			dialog.setTitle("编辑日报");
		} // end_if
	}

	// 删除一条记录
	function doRemove() {
		var rec = dailyReportGrid.getSelectionModel().getSelected();
		if (typeof rec === 'undefined') {
			Ext.Msg.alert('操作提示', '请在如下的日报列表中选择需要删除的的数据。');
			// if (rec.get('reportDate').between(model.getMinDay(),
			// model.getMaxDay()))
		} else {
			var remove = rec.copy();
			Ext.MessageBox.confirm('确认操作', '确认删除项目[' + rec.get('projectName') + ']于['
							+ rec.get('reportDate').format('Y-m-d') + ']的工作日志吗?', function(btn) {
						if (btn === 'yes') {
							remove.set('projectName', 'remove');
							model.handleAjax(remove.data, 'remove', null);
						} // end_if
					});
		}
	} // end_fun

	// 刷新列表
	function doFresh() {
		dailyReportGrid.getStore().reload(dailyReportGrid.getStore().lastOptions.params);
	}
	/**
	 * @function buildContain
	 */
	function buildContain() {
		var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
					header : '项目名称',
					width : 35,
					dataIndex : 'projectName'
				}, {
					header : '用户姓名',
					width : 15,
					dataIndex : 'userName'
				}, {
					header : '部门',
					width : 15,
					dataIndex : 'deptName'
				}, {
					header : '日报日期',
					width : 20,
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
							case 7 :
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
						var _name = parseFloat(val) / 100;
						return _name.toFixed(2);
					}
				}, {
					header : '工作类别',
					width : 15,
					dataIndex : 'workType',
					renderer : function(val) {
						var _val = '';
						if ('0d0cc048adf440edb55d8e53d756439c' === val) {
							_val = '<span style="color:green;">正常</span>';
						} else if ('3692b9cd731a452ea9e9c55efa5c9618' === val) {
							_val = '<span style="color:red;">请假</span>';
						} else if ('dba8abe0a65945a49ebfc587c947d920' === val) {
							_val = '<span style="color:blue;">加班</span>';
						} else {
							_val = '<span style="color:blank;">未知状态</span>';
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
					id : 'col_workContent',
					header : '工作内容',
					dataIndex : 'workContent'
				}]);

		var tba = new Ext.Toolbar([{
					text : '新增',
					iconCls : 'btn_add_data',
					handler : doAdd
				}, '-', {
					text : '查看-编辑',
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
				}, '-', {
					text : '批量填写',
					iconCls : 'btn_bat_xj',
					handler : builderBatWin
				}, '-', '->', {
					xtype : 'label',
					text : '项目名称：'
				}, {
					id : 'sel_projectId',
					xtype : "combo",
					msgTarget : 'title',
					name : 'sel_projectId',
					triggerAction : 'all',
					editable : false,
					typeAhead : true,
					forceSelection : true,
					emptyText : '请选择项目...',
					mode : 'local',
					store : model.getDailyReportProNameSelfStore(),
					valueField : 'regValue',
					displayField : 'displayValue',
					width : 105
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
						Ext.getCmp('sel_projectId').clearValue();
						model.getDailyReportStore().load({
									params : {
										int_start : 0,
										int_limit : model.getPageSize(),
										projectId : null
									}
								});
					}
				}]);

		dailyReportGrid = new Ext.grid.GridPanel({
					region : 'center',
					stripeRows : true,
					columnLines : true,
					autoExpandColumn : 'col_workContent',
					store : model.getDailyReportStore(),
					cm : cm,
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					autoScroll : true,
					loadMask : {
						msg : '正在加载数据,请稍候......'
					},
					viewConfig : {
						forceFit : true
					},
					tbar : tba,
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getDailyReportStore(),
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
		dailyReportGrid.getBottomToolbar().on('beforechange',
				function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getDailyReportStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.projectId = old_params.projectId;
					}
				});

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : dailyReportGrid
				});

	} // end_fun_buildContain

	/**
	 * {执行查询}
	 */
	function queryAction() {
		var val_projectId = Ext.getCmp('sel_projectId').getValue();
		model.getDailyReportStore().load({
					params : {
						int_start : 0,
						int_limit : model.getPageSize(),
						projectId : val_projectId
					}
				});
	} // end_fun

	var contain = null;
	var _statusbar = null;
	var dialog = null;
	var dialogBat = null;
	var formDetail = null;
	var formBat = null;
	var _temp_dreport_id = null;
	var dailyReportGrid = null;
	var action_type = null;
} // end_class
