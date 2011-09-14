/**
 * @author 人员角色授予 以及 进出场管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {

	var UserRecord = Ext.data.Record.create(['userId', 'workPlace', {
				name : 'teamStats',
				type : 'int'
			}, 'deptId', 'name', 'companyName', {
				name : 'sexed',
				type : 'int'
			}, 'loginId', 'deptName']);

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
	 * @function buildContain
	 */
	function buildContain() {
		var editor_plugins = new Ext.ux.grid.RowEditor();
		editor_plugins.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					model.cleanInvaladData(model.getUserListStore());
				});
		editor_plugins.on('beforeedit', function(
						/* Ext.ux.grid.RowEditor */edit,/* Number */
						rowIndex) {
					_edit_rec = grid_Panel.getStore().getAt(rowIndex);
					_old_rec = _edit_rec.copy();
				});
		editor_plugins.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					rec.set('teamStats', rec.get('teamStats') ? 1 : 0);
					changed.teamStats = rec.get('teamStats');
					var isChange = false;
					for (var p in changed) {
						if (p !== 'teamStats') {
							isChange = true;
							break;
						}
					} // end_for
					if (!isChange) { // 未修改数据的情况下提交代码
						editor_plugins.stopEditing(false);
						grid_Panel.getStore().commitChanges();
						return;
					} // end_if (!isChange)
					if (typeof changed.deptId !== 'undefined') {
						if (rec.get('userId') !== null && rec.get('teamStats') === 1) {
							Ext.MessageBox.confirm('确认操作', '该员工为在未离场的情况发生部门变更，会引起数据异常，你确认提交吗?', function(btn) {
										if (btn === 'yes') {
											model.saveOrupdateAction(rec.data, grid_Panel);
										} else {
											editor_plugins.stopEditing(false);
											for (var p in changed) {
												rec.set(p, _old_rec.get(p));
											} // end_for
											grid_Panel.getStore().commitChanges();
										}
									});
						} else {
							model.saveOrupdateAction(rec.data, grid_Panel);
						}
					} else {
						model.saveOrupdateAction(rec.data, grid_Panel);
					}
				});

		grid_Panel = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					plugins : [editor_plugins],
					store : model.getUserListStore(),
					tbar : ['-', {
								text : '新员工入场',
								iconCls : 'btn_add_user',
								handler : function() {
									var saveInstance = new UserRecord({
												userId : null,
												name : null,
												deptId : null,
												workPlace : null,
												companyName : null,
												sexed : 1,
												teamStats : 1,
												postType : '654544f6c242468cb958fd4196d3e6a4',
												loginId : null
											});
									addNewUser(saveInstance, editor_plugins, grid_Panel);
								}
							}, '-', {
								text : '进/离场',
								iconCls : 'btn_del_user',
								handler : function(/* Button */btn) {
									var user_rec = grid_Panel.getSelectionModel().getSelected();
									if (typeof user_rec === 'undefined') {
										Ext.Msg.alert('提示', '请在如下的用户列表选择你要执行操作的人员（单选）.');
									} else {
										var action = user_rec.get('teamStats') === 1 ? '离场' : '进场';
										var sign = user_rec.get('teamStats') === 1 ? 0 : 1;
										Ext.MessageBox.confirm('Confirm', '是否确认对用户[' + user_rec.get('name') + ']执行['
														+ action + ']操作?', function(btn) {
													if (btn === 'yes') {
														model.clearUserAction(user_rec.get('userId'), sign);
													} // end_if
												});
									} // end_if
								}
							}, '-', {
								xtype : 'label',
								text : '姓名：'
							}, {
								id : 'findName',
								xtype : 'textfield',
								emptyText : '请输入员工姓名'
							}, {
								text : '查询',
								iconCls : 'btn_found',
								handler : function(/* Button */btn) {
									var userName = Ext.getCmp('findName').getValue();
									if (userName === '') {
										model.getUserListStore().load({
													params : {
														userName : null
													}
												});
									} else {
										model.getUserListStore().load({
													params : {
														userName : '%' + userName + '%'
													}
												});
									} // end_if
								} // end_handler
							}, '-'],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getUserListStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '姓名',
								width : 85,
								dataIndex : 'name',
								editor : {
									emptyText : '员工姓名',
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '帐号',
								width : 85,
								dataIndex : 'loginId',
								editor : {
									emptyText : '员工帐号',
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '性别',
								width : 50,
								dataIndex : 'sexed',
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">男</span>';
									} else if (val == 0) {
										return '<span style="color:red;">女</span>';
									} else {
										return '<span style="color:red;">未知</span>';
									}
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getSexdStore(),
									valueField : 'option_key',
									displayField : 'option_value'
								}
							}, {
								header : '进场状态',
								width : 75,
								dataIndex : 'teamStats',
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">在场</span>';
									} else if (val == 0) {
										return '<span style="color:red;">离场</span>';
									} else {
										return '<span style="color:red;">未知</span>';
									}
								},
								editor : {
									xtype : 'checkbox',
									disabled : true
								}
							}, {
								header : '所在组织',
								width : 95,
								dataIndex : 'postType',
								renderer : function(val) {
									var res = '';
									model.getPosttypeStore().each(function(rec) {
												if (rec.get('regValue') === val) {
													res = rec.get('displayValue');
													return false;
												} else {
													return true;
												}
											});
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
									store : model.getPosttypeStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '所在部门',
								width : 95,
								dataIndex : 'deptId',
								renderer : function(val) {
									var res = null;
									model.getDeptStore().each(function(rec) {
												if (rec.get('regValue') === val) {
													res = rec.get('displayValue');
													return false;
												} else {
													return true;
												}
											});
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
									store : model.getDeptStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '公司名称',
								width : 150,
								dataIndex : 'companyName',
								editor : {
									emptyText : '员工公司名称',
									xtype : 'textfield'
								}
							}, {
								id : 'col_workPlace',
								header : '工作地点',
								width : 75,
								dataIndex : 'workPlace',
								editor : {
									emptyText : '员工工作地点',
									xtype : 'textfield'
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_workPlace'
				});
		// 查询时必须有此方法，才能正常分页
		grid_Panel.getBottomToolbar().on('beforechange', function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getUserListStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.userName = old_params.userName;
					}
				});

		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [grid_Panel]
				});

	} // end_fun_buildContain

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
	function addNewUser(/* Ext.data.Record */newRec,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		if (grid.getStore().getCount() != 0) {
			if (grid.getStore().getAt(0).get('userId') !== null) {
				editor.stopEditing();
				grid.getStore().insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		} else {
			if (typeof grid.getStore().getAt(0) === 'undefined') {
				editor.stopEditing();
				grid.getStore().insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		}
	} // end_fun

	var model = new UserModel();
	var grid_Panel = null;
	var contain = null;
	var _statusbar = null;
	var _edit_rec = null;
	var _old_rec = null;
} // end_class
