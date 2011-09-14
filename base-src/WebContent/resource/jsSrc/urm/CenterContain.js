/**
 * @author 人员角色授予 以及 进出场管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};

	this.getPanel = function() {
		if (contain === null) {
			buildContain();
			builderTreeDialog();
			// 初始化数据
			// tree_Dialog.show();
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
								text : '分配角色',
								iconCls : 'btn_edit_user_role',
								handler : function(/* Ext.Button */btn) {
									if (selected_user_rec === null) {
										Ext.Msg.alert('提示', '请先在如下人员列表中选择一位用户。');
									} else {
										bulderDialog();
										model.loadRoleByUser(selected_user_rec, role_Dialog);
									}
								}
							}, '-', {
								text : '分配数据权限',
								iconCls : 'btn_edit_user_dataset',
								handler : function(/* Ext.Button */btn) {
									if (tree_Dialog === null) {
										builderTreeDialog();
									}
									if (selected_user_rec === null) {
										Ext.Msg.alert('提示', '请先在如下人员列表中选择一位用户。');
									} else {
										model.loadDatSetByUserId(selected_user_rec, tree_Dialog, tree_Panle,
												treeXml_load);
									}
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
								header : '数据权限',
								width : 250,
								dataIndex : 'dataSet',
								renderer : function(/* Array */val) {
									var cn_name = new Array();
									for (var i = 0; i < val.length; i++) {
										for (var j = 0; j < dept_data.length; j++) {
											if (val[i] === dept_data[j][0]) {
												cn_name.push(dept_data[j][1]);
											}
										}
									}
									return cn_name.join('，');
								}
							}, {
								id : 'col_roleSet',
								header : '角色列表',
								width : 75,
								dataIndex : 'roleSet',
								renderer : function(/* Array */val) {
									var cn_name = new Array();
									for (var i = 0; i < val.length; i++) {
										for (var j = 0; j < roleopts.length; j++) {
											if (val[i] === roleopts[j][0]) {
												cn_name.push(roleopts[j][1]);
											}
										}
									}
									return cn_name.join('，');
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_roleSet'
				});
		grid_Panel.on('rowclick', function(/* Ext.EventObject */e) {
					selected_user_rec = grid_Panel.getSelectionModel().getSelected();
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [grid_Panel]
				});
	} // end_fun_buildContain

	function builderRoleFrom() {
		role_Form = new Ext.FormPanel({
					region : 'center',
					frame : true,
					defaults : {
						labelWidth : 5
					},
					items : [{
								id : 'role_fieldset',
								xtype : 'fieldset',
								title : '角色列表',
								autoHeight : true,
								layout : 'form'
							}]
				});
		model.initRoleGroup(role_Form);
	} // end_fun

	/**
	 * 
	 */
	function builderTreeDialog() {
		if (tree_Dialog === null) {
			tree_Panle = new Ext.tree.TreePanel({
						region : 'center',
						width : 250,
						animate : true,
						split : true,
						autoScroll : true,
						rootVisible : false,
						root : new Ext.tree.AsyncTreeNode({
									id : 'hxj'
								}),
						loader : new Ext.extend.DeptCheckedTreeLoader({
									dataUrl : '/workbase/console/dept/tree.xml',
									listeners : {
										'load' : function(/* Object */loader, /* Object */node,/* Object */response) {
											if (selected_user_rec !== null) {
												treeXml_load = true;
												model.loadDatSetByUserId(selected_user_rec, tree_Dialog, tree_Panle,
														treeXml_load);
											}
										}
									}
								}),
						listeners : {
							'checkchange' : function(/* Ext.tree.TreeNode */node,/* boolean */
									checked) {
								var args = new Array();
								args.push(node);
								while (args.length !== 0) {
									var nd = args.pop();
									if (nd.hasChildNodes()) {
										for (var i = 0; i < nd.childNodes.length; i++) {
											args.push(nd.childNodes[i]);
										}
									} // end_if
									if (node.id !== nd.id) {
										if (checked) {
											nd.getUI().toggleCheck(false);
											nd.disable();
										} else {
											nd.enable();
										}
									}
								} // end_while
							}
						}
					});
			tree_Dialog = new Ext.Window({
						title : '数据集权限-对话框',
						width : 250,
						height : 400,
						closeAction : 'hide',
						plain : true,
						modal : true,
						constrain : true,
						layout : 'border',
						items : [tree_Panle],
						buttons : [{
									text : '提交',
									handler : function() {
										var selectedIds = tree_Panle.getChecked('id');
										model.handleAccessData(selected_user_rec, selectedIds, tree_Dialog);
									}
								}, {
									text : '取消',
									handler : function() {
										tree_Dialog.hide();
									}
								}]
					});
		}
	}

	/**
	 * 
	 */
	function bulderDialog() {
		if (role_Dialog === null) {
			builderRoleFrom();
			role_Dialog = new Ext.Window({
						title : '人员授权-对话框',
						width : 600,
						height : 400,
						closeAction : 'hide',
						plain : true,
						modal : true,
						constrain : true,
						layout : 'border',
						activeItem : 0,
						items : [role_Form],
						buttons : [{
									text : '授权',
									handler : function() {
										model.handleRoleAction(selected_user_rec, role_Dialog);
									}
								}, {
									text : '取消',
									handler : function() {
										role_Dialog.hide();
									}
								}]
					});
		} // end_if
	}

	var model = new UserModel();
	var role_Form = null;
	var grid_Panel = null;
	var contain = null;
	var _statusbar = null;
	var role_Dialog = null;
	var tree_Dialog = null;
	var tree_Panle = null;
	var treeXml_load = false;
	var selected_user_rec = null;
} // end_class
