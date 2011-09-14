/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var RuleRecord = Ext.data.Record.create(model.getRecStruts());
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
		var editor_catalog = new Ext.ux.grid.RowEditor();
		editor_catalog.on('beforeedit', function(
						/* Ext.ux.grid.RowEditor */edit,/* Number */
						rowIndex) {
					old_rec = model.getCatalogStore().getAt(rowIndex).copy();
				});
		editor_catalog.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					model.cleanInvaladData(model.getCatalogStore());
				});

		editor_catalog.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					if (rec.get('ruleId') === null) {
						model.handleAjax(rec.data, catalogGrid);
					} else {
						for (var item in changed) {
							rec.set(item, old_rec.get(item));
						}
						model.getCatalogStore().commitChanges();
						Ext.Msg.alert('错误信息', '禁止修改已经生效的规则。');
					}
				});

		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					plugins : [editor_catalog],
					store : model.getCatalogStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true,
								listeners : {
									rowselect : function(
											/* SelectionModel */sml,/* Number */
											rowIndex, /* Ext.data.Record */
											rec) {
										editor_catalog.stopEditing(false);
									}
								}
							}),
					tbar : [{
								text : '新增规则',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new RuleRecord({
												ruleId : null,
												deptId : null,
												startDate : null,
												endDate : null,
												beiginWkTime : '08:00',
												endWkTime : '18:00',
												ruleName : null
											});
									addLocal(saveInstance, model.getCatalogStore(), editor_catalog, catalogGrid);
								}
							}, '-', {
								text : '注销规则',
								iconCls : 'btn_del_data',
								handler : function() {
									var sel_record = catalogGrid.getSelectionModel().getSelected();
									if (typeof sel_record === 'undefined') {
										Ext.Msg.alert('操作提示', '请在下表中选择需要注销的规则。');
									} else if (sel_record.get('enabled') === 0) {
										Ext.Msg.alert('操作提示', '该规则已经被注销，请选择其他规则进行该操作。');
									} else {
										Ext.MessageBox.confirm('确认操作', '确认注销[' + sel_record.get('ruleName')
														+ ']的考勤规则吗?', function(btn) {
													if (btn === 'yes') {
														model.handleAjax(sel_record.data, catalogGrid);
													} // end_if
												});
									}
								}
							}, '->', '-', {
								text : '规则说明',
								iconCls : 'btn_help',
								handler : function() {
									Ext.Msg.alert('规则说明', '系统会匹配最新新增的有效考勤规则作为考勤标准;<br/>其次是规则所在的部门是向下兼容的。');
								}
							}],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getCatalogStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '名称',
								width : 125,
								dataIndex : 'ruleName',
								editor : {
									xtype : 'textfield',
									allowBlank : false,
									emptyText : '必填字段'
								}
							}, {
								header : '填写人',
								width : 75,
								dataIndex : 'userId',
								editor : {
									xtype : 'textfield',
									allowBlank : true,
									disabled : true
								}
							}, {
								header : '部门',
								width : 85,
								dataIndex : 'deptId',
								renderer : function(value, metaData, record, rowIndex, colIndex, store) {
									var name = '';
									for (var i = 0; i < dept_data.length; i++) {
										if (value === dept_data[i][0]) {
											name = dept_data[i][1];
											break;
										}
									} // end_for
									return name === '' ? record.get('deptName') + '(无效)' : name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									emptyText : '必填字段',
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getDeptStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '生效状态',
								width : 75,
								dataIndex : 'enabled',
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">生效</span>';
									} else if (val == 0) {
										return '<span style="color:red;">无效</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '生效日期',
								width : 95,
								dataIndex : 'startDate',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									date : new Date()
								}
							}, {
								header : '截止日期',
								width : 95,
								dataIndex : 'endDate',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									date : new Date()
								}
							}, {
								header : '起始周',
								width : 95,
								dataIndex : 'beginWeek',
								renderer : function(value, metaData, record, rowIndex, colIndex, store) {
									var name = '';
									model.getWeekStore().each(function(rec) {
												if (value === parseInt(rec.get('regValue'), 10)) {
													name = rec.get('displayValue');
													return false;
												} else {
													return true;
												}
											});
									return name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									emptyText : '必填字段',
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getWeekStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '结束周',
								width : 95,
								dataIndex : 'endWeek',
								renderer : function(value, metaData, record, rowIndex, colIndex, store) {
									var name = '';
									model.getWeekStore().each(function(rec) {
												if (value === parseInt(rec.get('regValue'), 10)) {
													name = rec.get('displayValue');
													return false;
												} else {
													return true;
												}
											});
									return name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									emptyText : '必填字段',
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getWeekStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '上班时间',
								width : 85,
								dataIndex : 'beiginWkTime',
								editor : {
									xtype : 'timefield',
									allowBlank : false,
									minValue : '6:00',
									allowBlank : false,
									maxValue : '19:00',
									format : 'H:i',
									increment : 30
								}
							}, {
								header : '下班时间',
								width : 85,
								dataIndex : 'endWkTime',
								editor : {
									xtype : 'timefield',
									allowBlank : false,
									minValue : '6:00',
									maxValue : '19:00',
									allowBlank : false,
									format : 'H:i',
									increment : 30
								}
							}, {
								id : 'col_entryIngDate',
								header : '填写日期',
								width : 75,
								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'entryIngDate',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									disabled : true
								}
							}],
					autoExpandColumn : 'col_entryIngDate'
				});
	} // end_fun

	/**
	 * @function 新增记录
	 * @param {}
	 *            newRec
	 * @param {}
	 *            store
	 * @param {}
	 *            editor
	 * @param {}
	 *            grid
	 */
	function addLocal(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		if (store.getCount() != 0) {
			if (store.getAt(0).get('ruleId') !== null) {
				editor.stopEditing();
				store.insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		} else {
			if (typeof store.getAt(0) === 'undefined') {
				editor.stopEditing();
				store.insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		}
	} // end_fun
} // end_class
