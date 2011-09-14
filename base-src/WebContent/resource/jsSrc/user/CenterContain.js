/**
 * @author 考勤管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {

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
		grid_Panel = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					store : model.getUserListStore(),
					tbar : ['-', {
								text : '编辑用户信息',
								iconCls : 'btn_edit_user',
								listeners : {
									'click' : function(/* Button */btn,/* Event */
											e) {
										var user_rec = grid_Panel.getSelectionModel().getSelected();
										if (typeof user_rec === 'undefined') {
											Ext.Msg.alert('提示', '请在如下的用户列表选择你要编辑的人员（单选）.');
										} else {
											userDialog.getUserDialog(user_rec.get('userId'), user_rec.get('name'));
										} // end_if
									}
								}
							}, '-', {
								text : '员工附加信息',
								iconCls : 'btn_add_user_other',
								handler : function(/* Button */btn) {
									alert('待开发');
								}
							}, '-', {
								text : '重置密码',
								iconCls : 'button_password',
								handler : function(/* Button */btn) {
									var user_rec = grid_Panel.getSelectionModel().getSelected();
									if (typeof user_rec === 'undefined') {
										Ext.Msg.alert('提示', '请在如下的用户列表选择需要操作的人员（单选）.');
									} else {
										Ext.MessageBox.confirm('确认操作', '确认重置[' + user_rec.get('name')
														+ ']的登录密码？<br/><br/>'
														+ '<font style="color:red;">提示重置后原始密码为该用户的帐号！</font>',
												function(btn) {
													if (btn === 'yes') {
														model.resetPwd(user_rec.get('userId'));
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
							}],
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
								dataIndex : 'name'
							}, {
								header : '帐号',
								width : 85,
								dataIndex : 'loginId'
							}, {
								header : '状态',
								width : 85,
								dataIndex : 'enabled',
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">启用</span>';
									} else if (val == 0) {
										return '<span style="color:red;">禁用</span>';
									} else {
										return '<span style="color:red;">未知</span>';
									}
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
								}
							}, {
								header : '部门',
								width : 75,
								dataIndex : 'deptId',
								renderer : function(val) {
									var res = null;
									model.getDeptStore().each(function(/* Ext.data.Record */rec) {
												if (rec.get('regValue') === val) {
													res = rec.get('displayValue');
													return false;
												} else {
													return true;
												}
											});
									return res;
								}
							}, {
								header : '手机号码',
								width : 75,
								dataIndex : 'mobileNo'
							}, {
								header : 'IP地址',
								width : 75,
								dataIndex : 'ipAddress'
							}, {
								header : '内部邮箱',
								width : 175,
								dataIndex : 'workEmail'
							}, {
								id : 'col_workPlace',
								header : '工作地点',
								width : 75,
								dataIndex : 'workPlace'
							}],
					stripeRows : true,
					autoExpandColumn : 'col_workPlace'
				});
		grid_Panel.on('dblclick', function(/* Ext.EventObject */e) {
					var rec = grid_Panel.getSelectionModel().getSelected();
					userDialog.getUserDialog(rec.get('userId'), rec.get('name'));
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [grid_Panel]
				});

	} // end_fun_buildContain

	var model = new UserModel();
	var userDialog = new UserDialog(model);
	var grid_Panel = null;
	var contain = null;
	var _statusbar = null;
} // end_class
