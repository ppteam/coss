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
	var detail_Win = null; // 签到明细对话框
	var detail_Grid = null; // 签到明细对话框 表格组件
	var deptForm = null;
	var deptWindow = null;
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
		builderDeptUI();
		model.initDeptGroup();
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
												Ext.getCmp(dept_data[i][0]).setValue(true);
											} // end_for
										} // end_if
									}
								}, {
									text : '重置',
									handler : function(/* Button */btn) {
										if (dept_data.length > 0) {
											for (var i = 0; i < dept_data.length; i++) {
												Ext.getCmp(dept_data[i][0]).setValue(false);
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
										if (dept_data.length > 0) {
											for (var i = 0; i < dept_data.length; i++) {
												if (Ext.getCmp(dept_data[i][0]).getValue()) {
													_selected_deptIds.push(dept_data[i][0]);
												};
											} // end_for
										} // end_if
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
		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					store : model.getStatsStore(),
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
								text : '开始日期:'
							}, {
								xtype : 'datefield',
								id : 'startDate',
								editable : false,
								value : model.getFirstDateOfMonth(),
								format : 'Y-m-d'
							}, {
								xtype : 'label',
								text : '截至日期:'
							}, {
								xtype : 'datefield',
								id : 'endDate',
								editable : false,
								value : model.getLastDateOfMonth(),
								format : 'Y-m-d'
							}, {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(btn) {
									queryCheck();
								}
							}, '->', '-', {
								text : '导出报表',
								iconCls : 'btn_report',
								handler : function(btn) {
									exportReport();
								}
							}],
					columns : [new Ext.grid.RowNumberer(), {
								header : '员工姓名',
								width : 95,
								dataIndex : 'userName'
							}, {
								header : '所在部门',
								width : 105,
								dataIndex : 'deptName'
							}, {
								header : '正常签到',
								width : 95,
								dataIndex : 'bgGod'
							}, {
								header : '迟到',
								width : 95,
								dataIndex : 'bgErr'
							}, {
								header : '未签退',
								width : 95,
								dataIndex : 'bgExp'
							}, {
								header : '正常签退',
								width : 95,
								dataIndex : 'edGod'
							}, {
								header : '早退',
								width : 95,
								dataIndex : 'edErr'
							}, {
								id : 'col_endCheck',
								header : '未签退',
								width : 95,
								dataIndex : 'edErr'
							}],
					autoExpandColumn : 'col_endCheck'
				});
		// rowdblclick : ( Grid this, Number rowIndex, Ext.EventObject e )
		catalogGrid.on('rowdblclick', function(/* Grid */gd, /* Number */rowIndex, /* Ext.EventObject */e) {
					var rec = gd.getSelectionModel().getSelected();
					builderWin();
					detail_Win.setTitle(rec.get('userName') + '签到明细列表');
					queryDetail(rec.get('userId'));
					detail_Win.show();
				});
	} // end_fun

	function builderWin() {
		if (detail_Win === null) {
			detail_Grid = new Ext.grid.GridPanel({
						region : 'center',
						loadMask : true,
						columnLines : true,
						stripeRows : true,
						store : model.getDetailStore(),
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						columns : [new Ext.grid.RowNumberer(), {
									header : '员工姓名',
									width : 95,
									dataIndex : 'userName'
								}, {
									header : '考勤日期',
									width : 95,
									dataIndex : 'checkDate',
									xtype : 'datecolumn',
									format : 'Y-m-d'
								}, {
									header : '签到情况',
									width : 95,
									dataIndex : 'beginStats',
									renderer : function(val) {
										if (val == 0) {
											return '<span style="color:green;">未签到</span>';
										} else if (val == 1) {
											return '<span style="color:blue;">正常</span>';
										} else if (val == 2) {
											return '<span style="color:red;">迟到</span>';
										} else {
											return '<span style="color:red;">未知状态</span>';
										}
									}
								}, {
									header : '签到时间',
									width : 95,
									dataIndex : 'beginCheck'
								}, {
									header : '签退情况',
									width : 95,
									dataIndex : 'endStats',
									renderer : function(val) {
										if (val == 0) {
											return '<span style="color:green;">未签退</span>';
										} else if (val == 1) {
											return '<span style="color:blue;">正常</span>';
										} else if (val == 2) {
											return '<span style="color:red;">早退</span>';
										} else {
											return '<span style="color:red;">未知状态</span>';
										}
									}
								}, {
									id : 'col_end',
									header : '签退时间',
									width : 95,
									dataIndex : 'endCheck'
								}],
						autoExpandColumn : 'col_end'
					});

			detail_Win = new Ext.Window({
						width : 600,
						height : 400,
						closeAction : 'hide',
						plain : true,
						modal : true,
						constrain : true,
						layout : 'border',
						items : [detail_Grid],
						buttons : [{
									text : '确定',
									handler : function() {
										detail_Win.hide();
									}
								}]
					});
		} // end_if
	} // end_fun

	/**
	 * {执行查询操作}
	 */
	function queryDetail(/* String */_userId) {
		var q_startDate = Ext.getCmp('startDate').getValue();
		var q_endDate = Ext.getCmp('endDate').getValue();
		model.getDetailStore().load({
					params : {
						userId : _userId,
						date_startDate : q_startDate.format('Y-m-d'),
						date_endDate : q_endDate.format('Y-m-d')
					}
				});
	} // end_fun

	/**
	 * {执行查询操作}
	 */
	function queryCheck() {
		try {
			var q_startDate = Ext.getCmp('startDate').getValue();
			var q_endDate = Ext.getCmp('endDate').getValue();

			if (_selected_deptIds === null) {
				throw new Error('请先指定统计考虑的部门范围，至少指定一个部门。');
			} else if (_selected_deptIds.length === 0) {
				throw new Error('请先指定统计考虑的部门范围，至少指定一个部门。');
			} // end_if

			model.getStatsStore().load({
						params : {
							date_startDate : q_startDate.format('Y-m-d'),
							date_endDate : q_endDate.format('Y-m-d'),
							list_deptIdSet : _selected_deptIds.join('|')
						}
					});
		} catch (e) {
			Ext.MessageBox.alert('错误提示', e.message);
		}
	} // end_fun

	/**
	 * {导出报表}
	 */
	function exportReport() {
		try {
			var q_startDate = Ext.getCmp('startDate').getValue();
			var q_endDate = Ext.getCmp('endDate').getValue();

			if (_selected_deptIds === null) {
				throw new Error('请先指定统计考虑的部门范围，至少指定一个部门。');
			} else if (_selected_deptIds.length === 0) {
				throw new Error('请先指定统计考虑的部门范围，至少指定一个部门。');
			} // end_if

			var _title = q_startDate.format('Y-m-d') + '_' + q_endDate.format('Y-m-d') + '_CHECK_TOTAL';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=ckTotal&date_startDate=' + q_startDate.format('Y-m-d')
					+ '&date_endDate=' + q_endDate.format('Y-m-d') + '&list_deptIdSet=' + _selected_deptIds.join('|')
					+ '&title=' + _title;
		} catch (e) {
			Ext.MessageBox.alert('错误提示', e.message);
		}
	} // end_fun
} // end_class
