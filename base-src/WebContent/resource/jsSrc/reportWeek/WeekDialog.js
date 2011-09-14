/**
 * {用户信息编辑对话框}
 */

function WeekDialog(/* UserModel */dataModle) {
	// 表单容器
	var week_form = null;
	// 弹出窗口
	var week_dialog = null;
	var el = null;
	var _dataModel = dataModle;
	var deptCombo = null;
	var userCombo = null;
	var _combax_seleted_deptId = null;
	var str = null;

	this.getWeekDialog = function() {
		if (week_dialog === null) {
			buliderWeekDialog();
		}
		return week_dialog;
	};

	/**
	 * {初始化用户对话信息}
	 */
	function buliderWeekDialog() {
		if (week_form != null) {
			week_form.getForm().reset();
		}
		if (deptCombo === null) {
			deptCombo = new Ext.form.ComboBox({
						id : 'deptID',
						namd : 'deptID',
						mode : 'local',
						fieldLabel : '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门',
						editable : false,
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						store : _dataModel.getDeptStrore(),
						valueField : 'regValue',
						displayField : 'displayValue',
						width : 135,
						anchor : '93%',
						listeners : {
							select : function(/* Ext.form.ComboBox */cb,/* Ext.data.Record */
									rec, /* Number */index) {
								_combax_seleted_deptId = rec.get('regValue');
								alert(_combax_seleted_deptId);
								str = 'userList';
                                Ext.getCmp('userID').setDisabled(false);
								_dataModel.getUserStore(str).load({
											params : {
												deptId : _combax_seleted_deptId
											}
										});
							}
						}
					});
		}
		if (userCombo === null) {
			userCombo = new Ext.form.ComboBox({
						id : 'userID',
						namd : 'userID',
						mode : 'local',
						fieldLabel : '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名',
						editable : false,
						typeAhead : true,
						disabled : true,
						forceSelection : true,
						triggerAction : 'all',
						store : _dataModel.getUserStore(str),
						valueField : 'regValue',
						displayField : 'displayValue',
						width : 135,
						anchor : '93%'
					});
			str = null;
		}
		week_form = new Ext.form.FormPanel({
					region : 'center',
					labelWidth : 53,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 300,
					height : 190,
					region : 'center',
					waitMsgTarget : true,
					defaultType : 'textfield',
					defaults : {
						width : 280
					},
					items : [new Ext.form.DateField({
										fieldLabel : '开始时间',
										id : 'startDate',
										name : 'startDate',
										anchor : '93%',
										format : 'Y年m月d日',
										allowBlank : false
									}), new Ext.form.DateField({
										fieldLabel : '结束时间',
										id : 'endDate',
										name : 'endDate',
										anchor : '93%',
										format : 'Y年m月d日',
										allowBlank : false
									}),
							// {
							// id : 'deptID',
							// namd : 'deptID',
							// fieldLabel :
							// '部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门',
							// xtype : 'combo',
							// mode : 'local',
							// editable : false,
							// typeAhead : true,
							// forceSelection : true,
							// triggerAction : 'all',
							// store : _dataModel.getDeptStrore(),
							// valueField : 'regValue',
							// anchor : '93%',
							// displayField : 'displayValue',
							// allowBlank : false
							// },
							deptCombo, userCombo
					// {
					// xtype : 'textfield',
					// fieldLabel : '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名',
					// name : 'userID',
					// id : 'userID',
					// msgTarget : 'side',
					// anchor : '93%',
					// allowBlank : false
					// }
					]
				});

		week_dialog = new Ext.Window({
					title : '个人周报查询',
					width : 300,
					height : 190,
					id : 'weekReport_win',
					closeAction : 'hide',
					plain : true,
					layout : 'border',
					activeItem : 0,
					items : [week_form],
					buttons : [{
						text : '提交',
						handler : function(/* Button */btn) {
							var instance = week_form.getForm().getFieldValues();
							if (Ext.getCmp('startDate').getValue() != '') {
								instance.startDate = Ext.getCmp('startDate')
										.getValue().format('Y-m-d');
							}
							if (Ext.getCmp('endDate').getValue() != '') {
								instance.endDate = Ext.getCmp('endDate')
										.getValue().format('Y-m-d');
							}
							alert(dwr.util.toDescriptiveString(instance, 2));
							// _dataModel.handleAjax(instance, week_dialog);
						}
					}, {
						text : '取消',
						handler : function(/* Button */btn) {
							week_form.getForm().reset();
							week_dialog.hide(btn);
						}
					}]
				});

		week_dialog.show();

	} // end_fun
} // end_class
