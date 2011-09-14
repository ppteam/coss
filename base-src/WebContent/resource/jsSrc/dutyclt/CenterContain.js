/**
 * @author 资源管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {

	var model = new DataModel();
	var sourceGrid = null;
	var _statusbar = null;
	var contain = null;
	var col_len = 20;
	var dialog = null;
	var depForm = null;
	var deptIds = new Array();

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
	 * {弹出部门选择}
	 */
	function buildWin() {
		if (depForm === null) {
			depForm = new Ext.form.FormPanel({
						labelAlign : 'left',
						labelWidth : 75,
						region : 'center',
						frame : true,
						items : [{
									id : 'deptGroup',
									name : 'deptGroup',
									xtype : 'fieldset',
									anchor : '80%',
									fieldLabel : '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门'
								}]
					});
			if (dept_data !== null && dept_data.length !== 0) {
				var ckGroup = Ext.getCmp('deptGroup');
				var ck_groups = {
					xtype : 'checkboxgroup',
					columns : 2,
					autoWidth : true,
					items : []
				};
				var j = 0;
				for (var i = 0; i < dept_data.length; i++) {
					deptIds.push(dept_data[i][0]);
					ck_groups.items[j] = new Ext.form.Checkbox({
								boxLabel : dept_data[i][1],
								name : dept_data[i][0],
								id : dept_data[i][0]
							});
					j++;
				} // end_for
				ckGroup.add(ck_groups);
				ckGroup.doLayout();
			} // end_if
		} // end_if
		if (dialog === null) {
			dialog = new Ext.Window({
						constrain : true,
						modal : true,
						width : 700,
						height : 300,
						closeAction : 'hide',
						closable : true,
						layout : 'border',
						resizable : false,
						shadow : true,
						bodyStyle : 'padding:5 5 5 5',
						items : [depForm],
						buttons : [{
									text : '确定',
									handler : function(/* Button */btn,/* Event */
											e) {
										dialog.hide();
									}
								}, {
									text : '全选',
									handler : function(/* Button */btn,/* Event */
											e) {
										for (var i = 0; i < deptIds.length; i++) {
											Ext.getCmp(deptIds[i]).setValue(true);
										} // end_for
									}
								}, {
									text : '重置',
									id : 'reset-btn',
									handler : function(/* Button */btn,/* Event */
											e) {
										for (var i = 0; i < deptIds.length; i++) {
											Ext.getCmp(deptIds[i]).setValue(false);
										} // end_for
									}
								}, {
									text : '取消',
									handler : function(/* Button */btn,/* Event */
											e) {
										dialog.hide();
									}
								}]
					});
		} // end_if
		dialog.show();
	} // end_fun

	/**
	 * @function {构造UI函数}
	 */
	function buildContain() {
		builderGrid();
		var grids_Panel = new Ext.Panel({
					region : 'center',
					layout : 'border',
					items : [sourceGrid]
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
	 * {工作时间转换 工作量}
	 * 
	 * @param {}
	 *            _value
	 */
	function handleDays(/* int */_value) {
		if (_value === 0) {
			return '';
		} else {
			_value = _value / 100 / 8;
		}
		return _value.toFixed(2);
	}

	/**
	 * {创建CatalogUI}
	 */
	function builderGrid() {
		sourceGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					store : model.getRecordStore(),
					stripeRows : true,
					view : new Ext.grid.GroupingView({
								forceFit : true,
								hideGroupedColumn : true,
								groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
							}),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : [{
								text : '可选部门',
								iconCls : 'btn_deption',
								handler : function(/* Ext.button */btn) {
									buildWin();
								}
							}, '-', {
								xtype : 'label',
								text : '开始日期：'
							}, {
								xtype : 'datefield',
								id : 'startDate',
								editable : false,
								format : 'Y-m-d',
								value : new Date()
							}, '-', {
								xtype : 'label',
								text : '截至日期：'
							}, {
								xtype : 'datefield',
								id : 'endDate',
								editable : false,
								format : 'Y-m-d',
								value : new Date()
							}, '-', {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(/* Ext.button */btn) {
									doQueryAction();
								} // end_fun
							}, '->', '-', {
								text : '报表导出',
								iconCls : 'btn_report',
								handler : function(/* Ext.button */btn) {
									downLoadXls();
								} // end_fun
							}],
					columns : [new Ext.grid.RowNumberer(), {
								header : "WK",
								width : 15,
								dataIndex : 'groupBy'
							}, {
								header : "项目组",
								width : 100,
								dataIndex : 'projectName'
							}, {
								header : '姓名',
								width : 40,
								dataIndex : 'userName'
							}, {
								header : '病假',
								width : col_len,
								dataIndex : 'sickDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '事假',
								width : col_len,
								dataIndex : 'thindDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '迟到',
								width : col_len,
								dataIndex : 'laterDay',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '旷工',
								width : col_len,
								dataIndex : 'leaveDay',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '婚假',
								width : col_len,
								dataIndex : 'marryDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '丧假',
								width : col_len,
								dataIndex : 'deadDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '产假',
								width : col_len,
								dataIndex : 'laborDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '年假',
								width : col_len,
								dataIndex : 'happyDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '探亲假',
								width : col_len,
								dataIndex : 'homeDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '培训',
								width : col_len,
								dataIndex : 'learnDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '考勤合计',
								width : col_len + 15,
								dataIndex : 'dutyTotal',
								renderer : function(val) {
									var res = handleDays(val) === '' ? '0' : handleDays(val);
									return '<span style="color:red">' + res + '</span>';
								}
							}, {
								header : '调休',
								width : col_len,
								dataIndex : 'changeDays',
								renderer : function(val) {
									return handleDays(val);
								}
							}, {
								header : '合计',
								width : col_len,
								dataIndex : 'allTotal',
								renderer : function(val) {
									var res = handleDays(val) === '' ? '0' : handleDays(val);
									return '<span style="color:green">' + res + '</span>';
								}
							}, {
								id : 'col_comments',
								header : '备注',
								width : 75,
								dataIndex : 'comments',
								renderer : function(val) {
									return val;
								}
							}],
					autoExpandColumn : 'col_comments'
				});
	} // end_fun

	function checkDate() {
		var start = Ext.getCmp('startDate').getValue();
		var end = Ext.getCmp('endDate').getValue();
		var dead = start.add(Date.DAY, 31);
		if (!end.between(start, dead)) {
			throw new Error('起始日期-截至日期跨度为一个月内，请检查日期是否正确选择。');
		}
	}

	/**
	 * {执行查询操作}
	 */
	function doQueryAction() {
		try {
			var dpIds = new Array();
			for (var i = 0; i < deptIds.length; i++) {
				if (Ext.getCmp(deptIds[i]).getValue()) {
					dpIds.push(deptIds[i]);
				}
			} // end_for
			if (dpIds.length === 0) {
				throw new Error("请先指定统计考勤的部门范围。");
			}
			checkDate();
			var startD = Ext.getCmp('startDate').getValue().format('Y-m-d');
			var endD = Ext.getCmp('endDate').getValue().format('Y-m-d');
			model.getRecordStore().load({
						params : {
							date_startDate : startD,
							date_endDate : endD,
							list_deptIds : dpIds.join('|')
						}
					});
		} catch (e) {
			Ext.MessageBox.alert("操作提示", e.message);
		}
	} // end_fun

	/**
	 * {下载电子表格}
	 */
	function downLoadXls() {
		try {
			var dpIds = new Array();
			for (var i = 0; i < deptIds.length; i++) {
				if (Ext.getCmp(deptIds[i]).getValue()) {
					dpIds.push(deptIds[i]);
				}
			} // end_for
			if (dpIds.length === 0) {
				throw new Error("请先指定统计考勤的部门范围。");
			}
			checkDate();
			var startD = Ext.getCmp('startDate').getValue().format('Y-m-d');
			var endD = Ext.getCmp('endDate').getValue().format('Y-m-d');

			var _title = startD + '_' + endD + '_CHECK_TOTAL';
			window.location.href = 'http://' + window.location.host
					+ '/workbase/report/proxy/view.xls?view=dutyCollect&date_startDate=' + startD + '&date_endDate='
					+ endD + '&list_deptIds=' + dpIds.join('|') + '&title=' + _title;
		} catch (e) {
			Ext.MessageBox.alert("操作提示", e.message);
		}
	} // end_fun
} // end_class
