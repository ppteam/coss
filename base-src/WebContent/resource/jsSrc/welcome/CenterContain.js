/**
 * @author 代办UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var Record_Todo = Ext.data.Record.create(model.getTodoStruts());
	var todoGrid = null;
	var _statusbar = null;
	var _selected_Id = null;
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
		builderCatalogGrid();
		var grids_Panel = new Ext.Panel({
					region : 'center',
					layout : 'border',
					items : [todoGrid]
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
		var editor = new Ext.ux.grid.RowEditor();
		editor.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					model.cleanInvaladData(model.getTodoStore());
				});
		editor.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					if (rec.get('todoType') === 0) {
						model.handleAjax(rec.data, todoGrid);
					} else {
						Ext.Msg.alert('操作提示', '日报未填写 有木有啊，不管你信不信，系统我不信。。。');
					}
				});
		todoGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					plugins : [editor],
					store : model.getTodoStore(),
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
					tbar : ['-', {
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new Record_Todo({
												todoId : null,
												userId : null,
												entryDate : null,
												deadDate : null,
												todoDes : null,
												status : 0
											});
									add(saveInstance, model.getTodoStore(), editor, todoGrid);
								}
							}, '-', {
								text : '刷新',
								iconCls : 'btn_refresh',
								handler : function() {
									model.getTodoStore().reload();
								}
							}, '-'],
					columns : [new Ext.grid.RowNumberer(), {
								xtype : 'datecolumn',
								format : 'Y-m-d',
								header : '录入日期',
								width : 85,
								dataIndex : 'entryDate',
								editor : {
									xtype : 'datefield',
									disabled : true
								}
							}, {
								xtype : 'datecolumn',
								format : 'Y-m-d',
								header : '截至日期',
								width : 85,
								dataIndex : 'deadDate',
								editor : {
									xtype : 'datefield',
									allowBlank : false
								}
							}, {
								header : '完成情况',
								width : 75,
								dataIndex : 'status',
								renderer : function(value, metaData, rec, rowIndex, colIndex, store) {
									var res = '';
									switch (value) {
										case 0 :
											if (rec.get('overTag') === 0) {
												res = '<span style="color:green;">待办中</span>';
											} else {
												res = '<span style="color:red;">超期中</span>';
											}
											break;
										case 1 :
											res = '完成';
											break;
										default :
											res = '等待...';
											break;
									} // end_swh
									return res;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getStatusStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								id : 'col_todoDes',
								header : '待办事项具体描述',
								width : 75,
								dataIndex : 'todoDes',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}],
					autoExpandColumn : 'col_todoDes'
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
			if (store.getAt(0).get('todoId') !== null) {
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
