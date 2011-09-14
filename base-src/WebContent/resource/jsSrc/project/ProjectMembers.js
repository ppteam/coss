/**
 * @author 项目人员管理
 * @class Dictionary Manager Panle
 */

function ProjectMembers(/* DataModel */model) {
	var _dialog = null;
	var _gridPanel = null;
	var _editForm = null;
	var _projectId = null;
	// 指向当前选中的记录
	var _selected_rec = null;

	this.getDialog = function(/* String */projectIdVal) {
		_projectId = projectIdVal;
		if (_dialog == null) {
			builderDialog();
		}
		model.getMemberStore().removeAll();
		model.getMemberStore().load({
					params : {
						projectId : projectIdVal
					}
				});
		return _dialog;
	};

	function resetCombox() {
		Ext.getCmp('cmp_memberlist').clearValue();
		Ext.getCmp('cmp_rolelist').clearValue();
	} // end_fun

	/**
	 * 
	 */
	function builderGrid() {
		if (_gridPanel === null) {
			_gridPanel = new Ext.grid.GridPanel({
						region : 'center',
						loadMask : true,
						columnLines : true,
						store : model.getMemberStore(),
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						listeners : {
							rowclick : function(/* Grid */grid, /* Number */
									rowIndex, /* Ext.EventObject */e) {
								_selected_rec = grid.getSelectionModel().getSelected();
								Ext.getCmp('cmp_memberlist').setValue(_selected_rec.get('userId'));
								Ext.getCmp('cmp_rolelist').setValue(_selected_rec.get('roleType'));
							}
						},
						columns : [new Ext.grid.RowNumberer(), {
									header : '姓名',
									width : 85,
									dataIndex : 'userName'
								}, {
									header : '项目角色',
									width : 85,
									dataIndex : 'roleType',
									renderer : function(val) {
										var _value = null;
										model.getRoleTypeStore().each(function(/* Ext.data.Record */rec) {
													if (rec.get('regValue') === val) {
														_value = rec.get('displayValue');
														return false;
													}
													return true;
												});
										return _value;
									}
								}, {
									id : 'col_joinDate',
									header : '加入日期',
									width : 85,
									dataIndex : 'joinDate'
								}],
						stripeRows : true,
						autoExpandColumn : 'col_joinDate'
					});
		}
	}

	function buildEditForm() {
		if (_editForm === null) {
			_editForm = new Ext.FormPanel({
						region : 'north',
						height : 100,
						frame : true,
						layout : 'column',
						defaults : {
							labelWidth : 55
						},
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												id : 'cmp_memberlist',
												xtype : 'combo',
												mode : 'local',
												fieldLabel : '人员列表',
												emptyText : '请选择人员',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												store : model.getUserStore(),
												valueField : 'regValue',
												displayField : 'displayValue',
												listeners : {
													select : function(/* Ext.form.ComboBox */combo, /* Ext.data.Record */
															record, /* Number */
															index) {
														// model.loadRoleAction(record.get('regValue'));
													}
												}
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												id : 'cmp_rolelist',
												xtype : 'combo',
												mode : 'local',
												fieldLabel : '角色列表',
												emptyText : '请选择角色',
												editable : false,
												typeAhead : true,
												forceSelection : true,
												triggerAction : 'all',
												store : model.getRoleTypeStore(),
												valueField : 'regValue',
												displayField : 'displayValue'
											}]
								}],
						buttons : [{
							text : '添加',
							handler : function() {
								var instance = model.creatMemberRec();
								instance.roleType = Ext.getCmp('cmp_rolelist').getValue();
								instance.userId = Ext.getCmp('cmp_memberlist').getValue();
								if (instance.userId === '' || instance.roleType === '') {
									Ext.Msg.alert('操作提示', '请先制定人员以及人员对应角色信息。');
									return;
								} // end_if
								instance.projectId = _projectId;
								var isExist = false;
								model.getMemberStore().each(function(rec) {
											if (rec.get('userId') === instance.userId) {
												isExist = true;
												return false;
											}
											return true;
										});
								if (isExist) {
									Ext.Msg.alert('Message', '用户[' + Ext.getCmp('cmp_memberlist').getRawValue()
													+ ']已经存在项目中，请选择其他员工。');
									resetCombox();
								} else {
									model.handleAjaxMember(instance, resetCombox);
								}
							}
						}, {
							text : '修改',
							handler : function() {
								if (_selected_rec === null) {
									Ext.Msg.alert('Message', '请双击选择其中一条记录。');
								} else {
									var instance = _selected_rec.data;
									instance.roleType = Ext.getCmp('cmp_rolelist').getValue();
									model.handleAjaxMember(instance, resetCombox);
								}
							}
						}, {
							text : '删除',
							handler : function() {
								if (_selected_rec === null) {
									Ext.Msg.alert('Message', '请在下列列表中选择要执行删除的员工。');
								} else {
									var instance = _selected_rec.data;
									Ext.MessageBox.confirm('确认操作', '确认调离员工[' + instance.userName + ']离开该项目组?',
											function(btn) {
												if (btn === 'yes') {
													instance.curtStats = 0;
													model.handleAjaxMember(instance, resetCombox);
												} // end_if
											});
								}
							}
						}, {
							text : '取消',
							handler : function() {
								resetCombox();
								_dialog.hide();
							}
						}, {
							text : '刷新',
							handler : function() {
								resetCombox();
								model.getMemberStore().reload(model.getMemberStore().lastOptions.params);
							}
						}]
					});
		}
	}

	/**
	 * {项目对话框组建}
	 */
	function builderDialog() {
		buildEditForm();
		builderGrid();
		_dialog = new Ext.Window({
					title : '项目-人员维护对话框',
					width : 600,
					height : 500,
					closeAction : 'hide',
					plain : true,
					modal : true,
					constrain : true,
					layout : 'border',
					activeItem : 0,
					items : [_editForm, _gridPanel]
				});
	}
} // end_class
