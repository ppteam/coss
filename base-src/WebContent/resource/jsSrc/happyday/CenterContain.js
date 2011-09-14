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
					columns : 3,
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
						height : 400,
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
	function handleTimer(/* int */_value) {
		if (_value === null) {
			return 0.00;
		} else {
			_value = _value / 100;
		}
		return _value;
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
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : [{
								text : '部门选择',
								iconCls : 'btn_deption',
								handler : function(/* Ext.button */btn) {
									buildWin();
								}
							}, '-', {
								text : '统计明细',
								iconCls : 'btn_found',
								handler : function(/* Ext.button */btn) {
									doQueryAction();
								} // end_fun
							}],
					columns : [new Ext.grid.RowNumberer(), {
								header : '姓名',
								width : 100,
								dataIndex : 'userName'
							}, {
								header : '部门',
								width : 100,
								dataIndex : 'deptName'
							}, {
								header : '累计加班',
								width : 75,
								dataIndex : 'overTime',
								renderer : function(val) {
									return handleTimer(val);
								}
							}, {
								header : '累计调休',
								width : 75,
								dataIndex : 'leaveTime',
								renderer : function(val) {
									return handleTimer(val);
								}
							}, {
								header : '可用时间',
								width : 75,
								renderer : function(value, metaData, record, rowIndex, colIndex, store) {
									var a = record.get('overTime') === null ? 0 : record.get('overTime');
									var b = record.get('leaveTime') === null ? 0 : record.get('leaveTime');
									return handleTimer(a - b);
								}
							}, {
								id : 'col_deptName',
								header : '其他',
								width : 100
							}],
					autoExpandColumn : 'col_deptName'
				});
	} // end_fun

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
			model.getRecordStore().load({
						params : {
							list_deptIds : dpIds.join('|')
						}
					});
		} catch (e) {
			Ext.MessageBox.alert("操作提示", e.message);
		}
	} // end_fun

} // end_class
