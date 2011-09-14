/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var Record_Catalog = Ext.data.Record.create(model.getCatalogStruts());
	var catalogGrid = null;
	var recordGrid = null;
	var _statusbar = null;
	var contain = null;
	var _combax_seleted_catalogId = null;

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

		var combo = new Ext.form.ComboBox({
					mode : 'local',
					editable : false,
					typeAhead : true,
					emptyText : '选择字典分类',
					forceSelection : true,
					triggerAction : 'all',
					store : model.getRecordStore(),
					valueField : 'regValue',
					displayField : 'displayValue',
					width : 135,
					listeners : {
						select : function(/* Ext.form.ComboBox */cb,/* Ext.data.Record */
								rec, /* Number */index) {
							_combax_seleted_catalogId = rec.get('regValue');
							model.getCatalogStore().load({
										params : {
											entrySire : _combax_seleted_catalogId
										}
									});
						}
					}
				});

		var editor_catalog = new Ext.ux.grid.RowEditor();
		editor_catalog.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					// 移除无效的数据
					model.cleanInvaladData(model.getCatalogStore());
				});
		editor_catalog.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					rec.set('enabled', rec.get('enabled') ? 1 : 0);
					model.handleAjax(rec.data, 'record', catalogGrid);
				});
		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
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
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new Record_Catalog({
												catalogId : null,
												entryName : null,
												entryShutName : null,
												entryDesc : null,
												enabled : 1,
												entryOrder : 0,
												catalogName : null
											});
									add(saveInstance, model.getCatalogStore(), editor_catalog, catalogGrid);
								}
							}, '-', {
								xtype : 'label',
								text : '字典分类：'
							}, combo, '-', {
								text : '显示全部',
								iconCls : 'btn_grid_all',
								handler : function() {
									combo.reset();
									_combax_seleted_catalogId = null;
									combo.clearValue();
									model.getCatalogStore().load();
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
								width : 75,
								dataIndex : 'entryName',
								editor : {
									xtype : 'textfield',
									emptyText : '字段名称',
									allowBlank : false
								}
							}, {
								header : '缩写',
								width : 75,
								dataIndex : 'entryShutName',
								editor : {
									xtype : 'textfield',
									emptyText : '字段缩写',
									allowBlank : false
								}
							}, {
								header : '分类',
								width : 125,
								dataIndex : 'entrySire',
								renderer : function(val) {
									var _name = null;
									model.getRecordStore().each(function(
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
									allowBlank : false,
									triggerAction : 'all',
									store : model.getRecordStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '排序',
								width : 75,
								dataIndex : 'entryOrder',
								editor : {
									xtype : 'numberfield',
									allowBlank : true
								}
							}, {
								header : '是否可用',
								width : 75,
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">可用</span>';
									} else if (val == 0) {
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
								id : 'col_entryDesc',
								header : '描述信息',
								width : 75,
								dataIndex : 'entryDesc',
								editor : {
									xtype : 'textfield',
									emptyText : '该字段的描述信息',
									allowBlank : true
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_entryDesc'
				});
		catalogGrid.getBottomToolbar().on('beforechange', function( /* Ext.PagingToolbar */pbr,/* Object */params) {
					params.entrySire = _combax_seleted_catalogId;
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
			if (store.getAt(0).get('catalogId') !== null) {
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
