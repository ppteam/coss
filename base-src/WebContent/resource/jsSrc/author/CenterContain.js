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
					model.handleAjax(rec.data, 'author', sourceGrid);
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
					tbar : ['-', {
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new Record_Source({
												authorId : null,
												authorSign : null,
												authorDesc : null,
												authorName : null,
												enabled : 1
											});
									add(saveInstance, model.getRecordStore(), editor, sourceGrid);
								}
							}, '-', {
								text : '删除',
								iconCls : 'btn_del_data',
								handler : function() {
									var _select_rec = sourceGrid.getSelectionModel().getSelected();
									if (typeof _select_rec === 'undefined') {
										Ext.Msg.alert('操作提示', '请在如下的权限列表选择你要删除的权限信息。');
									} else {
										// alert(dwr.util.toDescriptiveString(_select_rec.data,
										// 2));
										Ext.MessageBox.confirm('确认操作',
												'你确认删除权限[' + _select_rec.data.authorName + ']吗?', function(btn) {
													if (btn === 'yes') {
														_select_rec.data.authorSign = 'remove-myself';
														model.handleAjax(_select_rec.data, 'author', sourceGrid);
													} // end_if
												});
									}
								}
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
					// view : new Ext.grid.GroupingView({}),
					columns : [new Ext.grid.RowNumberer(), {
								id : 'col_authorName',
								header : '权限名称',
								width : 150,
								dataIndex : 'authorName',
								editor : {
									xtype : 'textfield',
									emptyText : '中文名称',
									allowBlank : false
								}
							}, {
								header : '系统标识',
								width : 120,
								dataIndex : 'authorSign',
								editor : {
									xtype : 'textfield',
									allowBlank : false,
									regex : /[a-zA-Z]/,
									emptyText : '英文标识',
									regexText : '请输入英文字符'
								}
							}, {
								header : '起停',
								width : 50,
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
								id : 'col_authorDesc',
								header : "权限描述",
								width : 180,
								dataIndex : 'authorDesc',
								editor : {
									xtype : 'textfield',
									emptyText : '权限描述',
									allowBlank : true
								}
							}],
					autoExpandColumn : 'col_authorDesc'
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
		if (store.getTotalCount() != 0) {
			if (store.getAt(0).get('authorId') !== null) {
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
