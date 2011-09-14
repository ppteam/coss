/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var catalogGrid = null;
	var _statusbar = null;
	var contain = null;
	var old_rec = null;
	var _selected_deptIds = null;

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
		builderDeptUI();
		model.initDeptGroup(deptForm);
	} // end_fun_buildContain

	/**
	 * {创建部门选择对话框UI}
	 */
	function builderDeptUI() {
		if (deptForm === null) {
			deptForm = new Ext.FormPanel({
						region : 'center',
						labelWidth : 55,
						frame : true,
						bodyStyle : 'padding:5px 5px 0',
						items : [{
									id : 'deptGroup',
									name : 'deptGroup',
									xtype : 'fieldset',
									anchor : '100%',
									fieldLabel : '可选部门'
								}]
					});
		} // end_if

		if (deptWindow === null) {
			deptWindow = new Ext.Window({
						title : '部门选择对话框（数据权限约束）',
						width : 700,
						height : 300,
						closeAction : 'hide',
						plain : true,
						modal : true,
						constrain : true,
						layout : 'border',
						items : [deptForm],
						buttons : [{
									text : '全选',
									handler : function(/* Button */btn) {
										if (dept_data.length > 0) {
											for (var i = 0; i < dept_data.length; i++) {
												if (dept_data[i][2] === 0) {
													Ext.getCmp(dept_data[i][0]).setValue(true);
												}
											} // end_for
										} // end_if
									}
								}, {
									text : '重置',
									handler : function(/* Button */btn) {
										if (dept_data.length > 0) {
											for (var i = 0; i < dept_data.length; i++) {
												if (dept_data[i][2] === 0) {
													Ext.getCmp(dept_data[i][0]).setValue(false);
												}
											} // end_for
										} // end_if
									}
								}, {
									text : '取消',
									handler : function(/* Button */btn) {
										deptWindow.hide();
									}
								}, {
									text : '确定',
									handler : function(/* Button */btn) {
										_selected_deptIds = new Array();
										var title = new Array();
										if (dept_data.length > 0) {
											for (var i = 0; i < dept_data.length; i++) {
												if (dept_data[i][2] === 0) {
													if (Ext.getCmp(dept_data[i][0]).getValue()) {
														_selected_deptIds.push(dept_data[i][0]);
														title.push(dept_data[i][1]);
													};
												}
											} // end_for
										} // end_if
										if (_selected_deptIds.length === 0) {
											_selected_deptIds = null;
											Ext.getCmp('btn_dept_sel').setTooltip('尚未指定部门');
										} else {
											Ext.getCmp('btn_dept_sel').setTooltip(title.join());
										}
										deptWindow.hide();
									}
								}]
					});
		}// end_if
	} // end_fun

	/**
	 * {创建CatalogUI}
	 */
	function builderCatalogGrid() {
		var editor = new Ext.ux.grid.RowEditor();
		editor.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					model.checkEditedAjax(rec);
				});
		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					plugins : [editor],
					store : model.getDetailStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : [{
								text : '可选部门',
								id : 'btn_dept_sel',
								iconCls : 'btn_deption',
								handler : function(btn) {
									deptWindow.show(btn);
								}
							}, '-', {
								xtype : 'label',
								text : '员工姓名:'
							}, {
								id : 'userName',
								xtype : 'textfield',
								emptyText : '请输入姓名',
								enableKeyEvents : true,
								listeners : {
									'keypress' : function(/* Ext.form.TextField */txf, /* Ext.EventObject */e) {
										if (e.keyCode === 13) {
											queryCheck();
										} // end_if (e.keyCode === 13)
									}
								}
							}, {
								xtype : 'label',
								text : '开始日期:'
							}, {
								xtype : 'datefield',
								id : 'startDate',
								editable : false,
								format : 'Y-m-d',
								value : new Date()
							}, {
								xtype : 'label',
								text : '截至日期:'
							}, {
								xtype : 'datefield',
								id : 'endDate',
								editable : false,
								format : 'Y-m-d',
								value : new Date()
							}, {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(btn) {
									queryCheck();
								}
							}, '-'],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getDetailStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '员工姓名',
								width : 95,
								dataIndex : 'userName',
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '考勤规则',
								width : 95,
								dataIndex : 'ruleId',
								renderer : function(val) {
									var _name = '<span style="color:red;">请指定规则</span>';
									if (val !== null) {
										model.getRuleStore().each(function(
														/* Ext.data.Record */rec) {
													if (rec.get('regValue') === val) {
														_name = rec.get('displayValue');
														return false;
													} else {
														return true;
													}
												});
									} // end_if
									return _name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									allowBlank : false,
									forceSelection : true,
									triggerAction : 'all',
									store : model.getRuleStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '所在部门',
								width : 105,
								dataIndex : 'deptName',
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '考勤日期',
								width : 95,
								dataIndex : 'checkDate',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								editor : {
									xtype : 'datefield',
									format : 'Y-m-d',
									allowBlank : false
								}
							}, {
								header : '考勤状态',
								width : 75,
								dataIndex : 'dayStats',
								renderer : function(val) {
									if (val === 0) {
										return '<span style="color:green;">上班</span>';
									} else if (val == 1) {
										return '<span style="color:blue;">假期</span>';
									} else if (val == 2) {
										return '<span style="color:red;">请假</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '签到情况',
								width : 95,
								dataIndex : 'beginStats',
								renderer : function(val) {
									if (val == 0) {
										return '<span style="color:red;">未签到</span>';
									} else if (val == 1) {
										return '<span style="color:green;">正常</span>';
									} else if (val == 2) {
										return '<span style="color:red;">迟到</span>';
									} else if (val == 3) {
										return '<span style="color:blank;">不考核</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '签到时间',
								width : 95,
								dataIndex : 'beginCheck',
								editor : {
									xtype : 'textfield',
									allowBlank : false,
									regex : /[0-2][0-9]:[0-5][0-9]/,
									regexText : '请输入指定格式的时间',
									emptyText : '09:00'
								}
							}, {
								header : '签退情况',
								width : 95,
								dataIndex : 'endStats',
								renderer : function(val) {
									if (val == 0) {
										return '<span style="color:red;">未签退</span>';
									} else if (val == 1) {
										return '<span style="color:green;">正常</span>';
									} else if (val == 2) {
										return '<span style="color:red;">早退</span>';
									} else if (val == 3) {
										return '<span style="color:blank;">不考核</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '签退时间',
								width : 95,
								dataIndex : 'endCheck',
								editor : {
									xtype : 'textfield',
									allowBlank : false,
									regex : /[0-2][0-9]:[0-5][0-9]/,
									regexText : '请输入指定格式的时间',
									emptyText : '09:00'
								}
							}, {
								id : 'col_comments',
								header : '签退情况',
								width : 95,
								dataIndex : 'comments',
								editor : {
									xtype : 'textfield'
								}
							}],
					autoExpandColumn : 'col_comments'
				});
		catalogGrid.getBottomToolbar().on('beforechange', function( /* Ext.PagingToolbar */pbr,/* Object */params) {
					var old_params = model.getDetailStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.userName = old_params.userName;
						params.date_startDate = old_params.date_startDate;
						params.date_endDate = old_params.date_endDate;
						params.list_deptIdSet = old_params.list_deptIdSet;
					}
				});
	} // end_fun

	/**
	 * {执行查询操作}
	 */
	function queryCheck() {
		// init query 条件
		var q_userName = Ext.getCmp('userName').getValue();
		var q_startDate = Ext.getCmp('startDate').getValue().format('Y-m-d');
		var q_endDate = Ext.getCmp('endDate').getValue().format('Y-m-d');
		model.getDetailStore().load({
					params : {
						int_start : 0,
						int_limit : model.getPageSize(),
						userName : q_userName === '' ? null : q_userName,
						date_startDate : q_startDate,
						date_endDate : q_endDate,
						list_deptIdSet : _selected_deptIds === null ? null : _selected_deptIds.join('|')
					}
				});
	} // end_fun

	var deptWindow = null;
	var deptForm = null;
} // end_class
