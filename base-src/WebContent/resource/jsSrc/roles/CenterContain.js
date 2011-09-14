/**
 * @author 角色管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var author_Dialog = null; // 授权对话框
	var Record_Source = Ext.data.Record.create(model.getRecordStruts());
	var sourceGrid = null;
	var _statusbar = null;
	var author_Form = null;
	var _singleSelect_rec = null; // 单行选定指针
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
		builderAuthorGrid();
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
					model.handleAjax(rec.data, 'role', sourceGrid);
				});
		sourceGrid = new Ext.grid.GridPanel({
					id : 'source_Grid',
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
										_singleSelect_rec = rec;
									}
								}
							}),
					tbar : ['-', {
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new Record_Source({
												roleId : null,
												roleSign : null,
												roleDesc : null,
												roleName : null,
												authorityInfos : new Array(),
												enabled : 1
											});
									add(saveInstance, model.getRecordStore(), editor, sourceGrid);
								}
							}, '-', {
								text : '分配权限',
								iconCls : 'btn_edt_role',
								handler : function() {
									if (_singleSelect_rec === null) {
										Ext.Msg.alert('Message', '请选择你要授权的角色信息，(*^__^*) 嘻嘻……');
									} else {
										bulderDialog();
										author_Dialog.setTitle(_singleSelect_rec.get('roleName') + '-授权');
										model.getAuthorByRole(_singleSelect_rec.get('roleId'), author_Dialog,
												author_Form);
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
					columns : [new Ext.grid.RowNumberer(), {
								id : 'col_authorName',
								header : '角色名称',
								width : 150,
								dataIndex : 'roleName',
								editor : {
									xtype : 'textfield',
									emptyText : '中文名称',
									allowBlank : false
								}
							}, {
								header : '角色类别',
								width : 120,
								dataIndex : 'roleSign',
								renderer : function(val) {
									var _name = null;
									model.getRoleTypeStrore().each(function(
													/* Ext.data.Record */rec) {
												if (rec.get('regValue') === val) {
													_name = rec.get('displayValue');
													return false;
												} else {
													return true;
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
									store : model.getRoleTypeStrore(),
									valueField : 'regValue',
									displayField : 'displayValue'
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
								header : "授权列表",
								width : 400,
								dataIndex : 'authorityInfos',
								renderer : function(val) {
									var names = new Array();
									if (val.length !== 0) {
										for (var i = 0; i < val.length; i++) {
											names.push(val[i].authorName);
										}
										return names.join();
									} else {
										return '无授权';
									}
								},
								editor : {
									xtype : 'textfield',
									emptyText : '新增之后才可以授权',
									disabled : true
								}
							}, {
								id : 'col_roleDesc',
								header : "角色描述",
								width : 180,
								dataIndex : 'roleDesc',
								editor : {
									xtype : 'textfield',
									emptyText : '角色信息描述',
									allowBlank : true
								}
							}],
					autoExpandColumn : 'col_roleDesc'
				});
	} // end_fun

	function builderAuthorGrid() {
		author_Form = new Ext.FormPanel({
					region : 'center',
					frame : true,
					defaults : {
						labelWidth : 5
					},
					items : [{
								id : 'author_fieldset',
								xtype : 'fieldset',
								title : '权限组',
								autoHeight : true,
								layout : 'form'
							}]
				});
		model.initAuthorGroup(author_Form);
	}

	/**
	 * @function 授权对话框初始化
	 */
	function bulderDialog() {
		if (author_Dialog === null) {
			author_Dialog = new Ext.Window({
						title : '角色授权-对话框',
						width : 600,
						height : 400,
						closeAction : 'hide',
						plain : true,
						modal : true,
						constrain : true,
						layout : 'border',
						activeItem : 0,
						items : [author_Form],
						buttons : [{
									text : '授权',
									handler : function() {
										authorAction();
									}
								}, {
									text : '取消',
									handler : function() {
										author_Dialog.hide();
									}
								}]
					});
		} // end_if
	}

	/**
	 * {执行授权操作基础数据收集}
	 */
	function authorAction() {
		var authidSet = model.getAuthorIds();
		var _arg_ids = new Array();
		for (var i = 0; i < authidSet.length; i++) {
			if (Ext.getCmp(authidSet[i]).getValue()) {
				_arg_ids.push(authidSet[i]);
			}

		} // end_for
		model.AuthorAjax(_singleSelect_rec.get('roleId'), _arg_ids, author_Dialog);
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
			if (store.getAt(0).get('roleId') !== null) {
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
