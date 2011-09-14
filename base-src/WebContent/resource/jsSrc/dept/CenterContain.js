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
		treePanle = new Ext.tree.TreePanel({
					region : 'west',
					width : 250,
					animate : true,
					split : true,
					autoScroll : true,
					rootVisible : false,
					title : '组织结构树',
					root : new Ext.tree.AsyncTreeNode({
								id : 'root'
							}),
					loader : new Ext.extend.DeptTreeLoader({
								dataUrl : '/workbase/console/dept/tree.xml'
							}),
					listeners : {
						click : function(/* Ext.tree.TreeNode */node,/* Event */
								event) {
							clickAction(node);
						}
					}
				});
		editPanle = new Ext.form.FormPanel({
					region : 'center',
					frame : true,
					labelWidth : 80,
					title : '组织机构编辑区',
					defaultType : 'textfield',
					items : [{
								xtype : 'fieldset',
								title : '选中节点信息',
								collapsible : true,
								autoHeight : true,
								defaults : {
									width : 210
								},
								defaultType : 'textfield',
								items : [{
											id : 'fatherId',
											allowBlank : true,
											hidden : true,
											name : 'fatherId'
										}, {
											id : 'deptId',
											allowBlank : true,
											hidden : true,
											name : 'deptId'
										}, {
											fieldLabel : '部门名称',
											emptyText : '请输入部门名称',
											allowBlank : false,
											name : 'deptName',
											anchor : '100%'
										}, {
											fieldLabel : '部门排序',
											name : 'deptOrder',
											id : 'deptOrder',
											allowBlank : false,
											emptyText : '请输入数字，影响拍部门排序',
											xtype : 'numberfield',
											anchor : '100%'
										}]
							}],
					buttons : [{
								text : '刷新页面',
								handler : function(/* Ext.Button */btn) {
									window.location.reload();
								}
							}, {
								text : '修改本节点',
								handler : function(/* Ext.Button */btn) {
									if (selected_node === null) {
										Ext.Msg.alert('操作提示', '请选选择你要执行操作的部门节点');
									} else {
										if (editPanle.getForm().isValid()) {
											var instance = editPanle.getForm().getFieldValues();
											// alert(dwr.util.toDescriptiveString(instance,
											// 2));
											Ext.MessageBox.confirm('确认操作', '修改[' + selected_node.attributes.text
															+ ']的部门信息吗?', function(btn) {
														if (btn === 'yes') {
															model.handlerAjax(instance, 0);
														} // end_if
													});
										} else {
											Ext.Msg.alert('操作提示', '请完善表单对应的必填数据。');
										}
									}
								}
							}, {
								text : '新增子节点',
								handler : function(/* Ext.Button */btn) {
									if (selected_node === null) {
										Ext.Msg.alert('操作提示', '请选选择你要执行操作的部门节点');
									} else {
										if (editPanle.getForm().isValid()) {
											var instance = editPanle.getForm().getFieldValues();
											instance.fatherId = instance.deptId;
											instance.deptId = null;
											// alert(dwr.util.toDescriptiveString(instance,
											// 2));
											Ext.MessageBox.confirm('确认操作', '确认为[' + selected_node.attributes.text
															+ ']增加子部门[' + instance.deptName + ']吗?', function(btn) {
														if (btn === 'yes') {
															model.handlerAjax(instance, 1);
														} // end_if
													});
										} else {
											Ext.Msg.alert('操作提示', '请完善表单对应的必填数据。');
										}
									}
								}
							}, {
								text : '删除节点',
								handler : function(/* Ext.Button */btn) {
									if (selected_node === null) {
										Ext.Msg.alert('操作提示', '请选选择你要执行操作的部门节点');
									} else {
										var instance = editPanle.getForm().getFieldValues();
										Ext.MessageBox.confirm('确认操作', '确认删除[' + selected_node.attributes.text
														+ ']部门以及子部门全部数据吗?', function(btn) {
													if (btn === 'yes') {
														model.handlerAjax(instance, -1);
													} // end_if
												});
									}
								}
							}]
				});
		// create the Grid
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [treePanle, editPanle]
				});

	} // end_fun_buildContain

	function clickAction(/* Ext.tree.TreeNode */node) {
		selected_node = node;
		var record = new NodeRecord({
					deptId : selected_node.attributes.id,
					fatherId : selected_node.parentNode.attributes.id === 'root'
							? selected_node.attributes.id
							: selected_node.parentNode.attributes.id,
					deptName : selected_node.attributes.text,
					nodeDeep : parseInt(selected_node.attributes.deep, 10),
					deptOrder : parseInt(selected_node.attributes.order, 10)
				});
		// alert(dwr.util.toDescriptiveString(record.data, 2));
		editPanle.getForm().loadRecord(record);
	}

	var contain = null;
	var _statusbar = null;
	var editPanle = null;
	var treePanle = null;
	var selected_node = null;
	var model = new DataModel();
	var NodeRecord = Ext.data.Record.create(model.getRecord());
} // end_class
