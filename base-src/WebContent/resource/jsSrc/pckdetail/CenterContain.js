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
		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					store : model.getDetailStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : [{
								xtype : 'label',
								text : '开始日期:'
							}, {
								xtype : 'datefield',
								id : 'startDate',
								editable : false,
								format : 'Y-m-d'
							}, {
								xtype : 'label',
								text : '截至日期:'
							}, {
								xtype : 'datefield',
								id : 'endDate',
								editable : false,
								format : 'Y-m-d'
							}, {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(btn) {
									queryCheck();
								}
							}, '-', {
								text : '清空日期',
								iconCls : 'btn_clear',
								handler : function(btn) {
									Ext.getCmp('startDate').setValue('');
									Ext.getCmp('endDate').setValue('');
								}
							}, '-', {
								text : '添加备注',
								iconCls : 'btn_add_cmts',
								handler : function(btn) {
									try {
										selected_rec = catalogGrid.getSelectionModel().getSelected();
										if (typeof selected_rec === 'undefined' || selected_rec === null) {
											throw new Error("请在下面考勤明细中选择你要添加备注的考勤记录。");
										} // end_if
										Ext.MessageBox.show({
													title : '对话框',
													msg : '请填写考勤备注信息:',
													width : 300,
													buttons : Ext.MessageBox.OKCANCEL,
													multiline : true,
													fn : showResultText
												});
									} catch (e) {
										Ext.Msg.alert('操作提示', e.message);
									} // end_try
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
								dataIndex : 'userName'
							}, {
								header : '所在部门',
								width : 105,
								dataIndex : 'deptName'
							}, {
								header : '考勤日期',
								width : 95,
								dataIndex : 'checkDate',
								xtype : 'datecolumn',
								format : 'Y-m-d'
							}, {
								header : '考勤状态',
								width : 95,
								dataIndex : 'dayStats',
								renderer : function(val) {
									if (val === 0) {
										return '<span style="color:green;">正常</span>';
									} else if (val == 1) {
										return '<span style="color:blue;">假期</span>';
									} else if (val == 2) {
										return '<span style="color:red;">请假</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								}
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
									} else if (val == 3) {
										return '<span style="color:blank;">不考核</span>';
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
									} else if (val == 3) {
										return '<span style="color:blank;">不考核</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								}
							}, {
								header : '签退时间',
								width : 95,
								dataIndex : 'endCheck'
							}, {
								id : 'col_comments',
								header : '考勤说明',
								width : 95,
								dataIndex : 'comments'
							}],
					autoExpandColumn : 'col_comments'
				});
	} // end_fun

	/**
	 * {执行查询操作}
	 */
	function queryCheck() {
		var q_startDate = Ext.getCmp('startDate').getValue();
		var q_endDate = Ext.getCmp('endDate').getValue();
		model.getDetailStore().load({
					params : {
						int_start : 0,
						int_limit : model.getPageSize(),
						date_startDate : q_startDate === '' ? '2000-01-01' : q_startDate.format('Y-m-d'),
						date_endDate : q_endDate === '' ? '2099-01-01' : q_endDate.format('Y-m-d')
					}
				});
	} // end_fun

	function showResultText(btn, text) {
		if (btn === 'ok') {
			model.handEditComment(selected_rec.get('detailId'), text, callFun);
		} // end_if
	} // end_fun

	/**
	 * {回调函数}
	 */
	function callFun(comments) {
		selected_rec.set('comments', comments);
		model.getDetailStore().commitChanges();
	}

	var selected_rec = null;
	var deptWindow = null;
	var deptForm = null;
} // end_class
