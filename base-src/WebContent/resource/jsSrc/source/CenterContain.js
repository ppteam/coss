/**
 * @author 资源管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var Record_Source = Ext.data.Record.create(model.getRecordStruts());
	var sourceGrid = null;
	var _statusbar = null;
	var contain = null;

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
	 * {创建CatalogUI}
	 */
	function builderGrid() {
		var editor = new Ext.ux.grid.RowEditor();
		editor.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					// 移除无效的数据
					model.cleanInvaladData(model.getRecordStore());
				});
		editor.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					rec.set('enabled', rec.get('enabled') ? 1 : 0);
					model.handleAjax(rec.data, 'source', sourceGrid);
				});
		sourceGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					store : model.getRecordStore(),
					plugins : [editor],
					stripeRows : true,
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true,
								listeners : {
									rowselect : function(
											/* SelectionModel */sml,/* Number */
											rowIndex, /* Ext.data.Record */
											rec) {
										editor.stopEditing(false);
									}
								}
							}),
					tbar : [{
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function(/* Ext.button */btn) {
									var saveInstance = new Record_Source({
												srcId : null,
												srcName : null,
												srcDesc : null,
												srcOrder : 0,
												srcSort : null,
												srcUrl : null,
												authorId : null,
												enabled : 0,
												stylecss : null,
												srcType : 0
											});
									add(saveInstance, model.getRecordStore(), editor, sourceGrid);
								}
							}, '-', {
								text : '刷新',
								iconCls : 'btn_ref_grid',
								handler : function(/* Ext.button */btn) {
									model.getRecordStore().reload(model.getRecordStore().lastOptions.params);
								}
							}, '-', {
								text : '删除',
								iconCls : 'btn_del_data',
								handler : function(/* Ext.button */btn) {
									if (typeof sourceGrid.getSelectionModel().getSelected() === 'undefined') {
										Ext.Msg.alert('错误', '请选择你要删除的资源数据(单选).');
									} else {
										var rec = sourceGrid.getSelectionModel().getSelected();
										model.removeSource(rec.get('srcId'));
									}
								}
							}],
					// view : new Ext.grid.GroupingView({}),
					columns : [new Ext.grid.RowNumberer(), {
								id : 'col_srcName',
								header : "名称",
								width : 150,
								dataIndex : 'srcName',
								renderer : function(/* string */value, /* metaData */
										md,/* Ext.data.Record */record) {
									if (record.get('srcType') == 0) {
										return '<span style="color:green;">' + value + '</span>';
									} else if (record.get('srcType') == 1) {
										return '<span style="color:red;">&nbsp;&nbsp;&nbsp;' + value + '</span>';
									}
								},
								editor : {
									xtype : 'textfield',
									emptyText : '资源名称',
									allowBlank : false
								}
							}, {
								header : '排序',
								width : 40,
								dataIndex : 'srcOrder',
								editor : {
									xtype : 'numberfield',
									allowBlank : true
								}
							}, {
								header : '菜单项',
								width : 75,
								dataIndex : 'srcSort',
								renderer : function(val) {
									var _name = null;
									model.getRecordStore().each(function(/* Ext.data.Record */rec) {
												if (val === rec.get('srcId')) {
													_name = rec.get('srcName');
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
									forceSelection : true,
									triggerAction : 'all',
									store : model.getMenuStore(),
									valueField : 'srcId',
									displayField : 'srcName'
								}
							}, {
								header : '分类',
								width : 70,
								dataIndex : 'srcType',
								renderer : function(val) {
									if (val == 0) {
										return '<span style="color:green;">主菜单</span>';
									} else if (val == 1) {
										return '<span style="color:red;">子菜单</span>';
									}
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									store : new Ext.data.ArrayStore({
												fields : [{
															name : 'displayValue',
															type : 'string'
														}, {
															name : 'regValue',
															type : 'int'
														}],
												data : [['子菜单', 1], ['主菜单', 0]]
											}),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '权限',
								width : 85,
								dataIndex : 'authorId',
								renderer : function(/* value */val) {
									var _name = '';
									model.getAuthorStore().each(function(/* Ext.data.Record */
													rec) {
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
									store : model.getAuthorStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '状态',
								width : 40,
								renderer : function(val) {
									if (val === 1) {
										return '<span style="color:green;">可用</span>';
									} else if (val === 0) {
										return '<span style="color:red;">禁用</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								dataIndex : 'enabled',
								editor : {
									xtype : 'checkbox'
								}
							}, {
								header : '菜单CSS',
								width : 85,
								dataIndex : 'stylecss',
								editor : {
									xtype : 'textfield',
									emptyText : '参考存在的样式',
									allowBlank : true
								}
							}, {
								header : 'Url地址',
								width : 250,
								dataIndex : 'srcUrl',
								editor : {
									xtype : 'textfield',
									emptyText : '填写资源URL访问地址',
									allowBlank : false
								}
							}, {
								id : 'col_srcDesc',
								header : "资源描述",
								width : 20,
								dataIndex : 'srcDesc',
								editor : {
									xtype : 'textfield',
									emptyText : '填写资源描述信息',
									allowBlank : true
								}
							}],
					autoExpandColumn : 'col_srcDesc'
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
		if (store.getAt(0).get('srcId') !== null) {
			editor.stopEditing();
			store.insert(0, newRec);
			grid.getView().refresh();
			grid.getSelectionModel().selectRow(0);
			editor.startEditing(0);
		}
	} // end_fun
} // end_class
