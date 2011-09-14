/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var proMember = new ProjectMembers(model);
	var _milestone = new MilestoneUI(model);
	var Record_Catalog = Ext.data.Record.create(model.getRecordStruts());
	var projectGrid = null;
	var recordGrid = null;
	var _statusbar = null;
	var contain = null;
	// 选定项目指针
	var _selected_project_rec = null;

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
		builderProjectGrid();
		var grids_Panel = new Ext.Panel({
					region : 'center',
					layout : 'border',
					items : [projectGrid]
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
	function builderProjectGrid() {

		var editor_catalog = new Ext.ux.grid.RowEditor();
		editor_catalog.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					// 移除无效的数据
					model.cleanInvaladData(model.getRecordStore(), 'projectId');
				});
		editor_catalog.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					rec.set('needReport', rec.get('needReport') ? 1 : 0);
					// alert(dwr.util.toDescriptiveString(rec.data, 2));
					if (typeof changed.weekBegin !== 'undefined') {
						rec.data.weekBegin = parseInt(changed.weekBegin, 10);
					}
					rec.data.workDays = rec.data.workDays * 100;
					// alert(dwr.util.toDescriptiveString(rec.data, 2));
					model.handleAjax(rec.data, projectGrid);
				});
		projectGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					plugins : [editor_catalog],
					store : model.getRecordStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true,
								listeners : {
									rowselect : function(
											/* SelectionModel */sml,/* Number */
											rowIndex, /* Ext.data.Record */
											rec) {
										editor_catalog.stopEditing(false);
										_selected_project_rec = rec;
									}
								}
							}),
					tbar : [{
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new Record_Catalog({
												projectId : null,
												projectNo : '项目编号',
												projectName : '项目名称',
												projectStats : null,
												deptId : null,
												needReport : 1,
												weekBegin : 7,
												workDays : 0,
												hoursRule : 8,
												proAgent : null,
												proOper : null,
												happenDays : 0,
												projectDes : '项目描述'
											});
									add(saveInstance, model.getRecordStore(), editor_catalog, projectGrid);
								}
							}, '-', {
								text : '刷新',
								iconCls : 'btn_ref_grid',
								handler : function() {
									model.getRecordStore().load();
								}
							}, '-', {
								text : '项目成员管理',
								iconCls : 'btn_pro_member',
								handler : function(/* Ext.Button */btn) {
									if (_selected_project_rec === null) {
										Ext.Msg.alert('Message', '请先单击选择一条记录进行编辑.');
									} else {
										proMember.getDialog(_selected_project_rec.get('projectId')).show(btn);
									}
								}
							}, '-', {
								xtype : 'label',
								text : '项目名称：'
							}, {
								id : 'projectKey',
								xtype : 'textfield',
								enableKeyEvents : true,
								listeners : {
									'keypress' : function(/* Ext.form.TextField */txf, /* Ext.EventObject */e) {
										if (e.keyCode === 13) {
											var projectKey = txf.getValue();
											if (projectKey === '') {
												model.getRecordStore().load({
															params : {
																int_start : 0,
																int_limit : model.getPageSize(),
																projectName : null
															}
														});
											} else {
												model.getRecordStore().load({
															params : {
																int_start : 0,
																int_limit : model.getPageSize(),
																projectName : '%' + projectKey + '%'
															}
														});
											} // end_if

										}
									}
								},
								emptyText : '项目名称关键字'
							}, {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(/* Button */btn) {
									var projectKey = Ext.getCmp('projectKey').getValue();
									if (projectKey === '') {
										model.getRecordStore().load({
													params : {
														int_start : 0,
														int_limit : model.getPageSize(),
														projectName : null
													}
												});
									} else {
										model.getRecordStore().load({
													params : {
														int_start : 0,
														int_limit : model.getPageSize(),
														projectName : '%' + projectKey + '%'
													}
												});
									} // end_if
								} // end_handler
							}, '-'],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getRecordStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '项目名称',
								width : 250,
								dataIndex : 'projectName',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '项目编号',
								width : 120,
								dataIndex : 'projectNo',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : 'PSM',
								width : 50,
								dataIndex : 'psmName',
								editor : {
									xtype : 'textfield',
									disabled : true
								}
							}, {
								header : '经办',
								width : 50,
								dataIndex : 'proAgent',
								editor : {
									xtype : 'textfield'
								}
							}, {
								header : '业务',
								width : 50,
								dataIndex : 'proOper',
								editor : {
									xtype : 'textfield'
								}
							}, {
								header : '度量',
								width : 40,
								dataIndex : 'hoursRule',
								editor : {
									xtype : 'numberfield',
									allowBlank : false,
									minValue : 0

								}
							}, {
								header : '计划量(人日)',
								width : 80,
								dataIndex : 'workDays',
								editor : {
									xtype : 'numberfield',
									allowBlank : false,
									minValue : 0

								}
							}, {
								header : '发生量(人日)',
								width : 80,
								dataIndex : 'happenDays',
								renderer : function(value, metaData, record, rowIndex, colIndex, store) {
									var rule = 100 * parseInt(record.get('hoursRule'));
									var res = (value / rule).toFixed(2);
									if (parseFloat(res) > parseFloat(record.get('workDays'))) {
										res = '<span style="color:red">' + res + '</span>';
									} else {
										res = '<span style="color:green">' + res + '</span>';
									}
									return res;
								},
								editor : {
									xtype : 'numberfield',
									allowBlank : true,
									disabled : true
								}
							}, {
								header : '周报填否',
								width : 60,
								dataIndex : 'needReport',
								renderer : function(val) {
									if (val === 1) {
										return '<span style="color:blue;">填写</span>';
									} else if (val === 0) {
										return '<span style="color:#668844;">不填</span>';
									} else {
										return '<span style="color:red;">未知</span>';
									}
								},
								editor : {
									xtype : 'checkbox'
								}
							}, {
								header : '部门',
								width : 60,
								dataIndex : 'deptId',
								renderer : function(val) {
									var _name = null;
									model.getDeptStrore().each(function(
													/* Ext.data.Record */rec) {
												if (rec.get('regValue') === val) {
													_name = rec.get('displayValue');
													return false;
												}
											});
									return _name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									store : model.getDeptStrore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '项目状态',
								width : 70,
								dataIndex : 'projectStats',
								renderer : function(val) {
									var _name = null;
									model.getProStatusStore().each(function(
													/* Ext.data.Record */rec) {
												if (rec.get('regValue') === val) {
													_name = rec.get('displayValue');
													return false;
												}
											});
									return _name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									store : model.getProStatusStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '周起始',
								width : 70,
								dataIndex : 'weekBegin',
								renderer : function(val) {
									var _name = null;
									model.getWeekStore().each(function(
													/* Ext.data.Record */rec) {
												if (parseInt(rec.get('regValue'), 10) === val) {
													_name = rec.get('displayValue');
													return false;
												}
												return true;
											});
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
									store : model.getWeekStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								id : 'col_projectDes',
								header : '描述信息',
								width : 75,
								dataIndex : 'projectDes',
								editor : {
									xtype : 'textfield'
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_projectDes'
				});

		projectGrid.getBottomToolbar().on('beforechange', function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getRecordStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.projectName = old_params.projectName;
					}
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
	function add(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		if (store.getCount() != 0) {
			if (store.getAt(0).get('projectId') !== null) {
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
