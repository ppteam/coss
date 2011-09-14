/**
 * {用户信息编辑对话框}
 */

function UserDialog(/* UserModel */dataModle) {
	// 表单容器
	var userBase_form = null;
	// 弹出窗口
	var user_dialog = null;

	var username = null;
	var loginNo = null;

	var el = null;
	var _dataModel = dataModle;
	var _user_Id = null;
	var deptid = null;
	var deptname = null;

	this.getUserDialog = function(/* String */userId,/* String */userName) {
		if (user_dialog === null) {
			buliderUserDialog();
		}
		if (userId === null || userId.length != 32) {
			Ext.Msg.alert('警告', '异常的数据，请联系系统管理员!');
			return;
		}
		_user_Id = userId;
		user_dialog.setTitle('[' + userName + '] 详细信息编辑对话框');
		_dataModel.loadUserById(_user_Id, userBase_form, user_dialog);
		return user_dialog;
	};

	/**
	 * {初始化用户对话信息}
	 */
	function buliderUserDialog() {
		userBase_form = new Ext.form.FormPanel({
					region : 'center',
					labelWidth : 53,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 800,
					height : 250,
					region : 'center',
					waitMsgTarget : true,
					items : [{
								items : [{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .33,
														layout : 'form',
														border : false,
														items : [new Ext.form.DateField({
																			id : 'birthday',
																			fieldLabel : '出生日期',
																			name : 'birthday',
																			anchor : '93%',
																			format : 'Y年m月d日'
																		}), {
																	id : 'polticsStatus',
																	fieldLabel : '政治面貌',
																	name : 'polticsStatus',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getPoliticsStore(),
																	valueField : 'regValue',
																	displayField : 'displayValue',
																	anchor : '93%'
																}, {
																	id : 'qualifications',
																	fieldLabel : '职称等级',
																	name : 'qualifications',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getQualificationsStore(),
																	valueField : 'regValue',
																	displayField : 'displayValue',
																	anchor : '93%'
																}, {
																	id : 'mobileNo',
																	xtype : 'textfield',
																	fieldLabel : '手机号码',
																	name : 'mobileNo',
																	msgTarget : 'side',
																	allowBlank : true,
																	regex : /^1[358]{1}\d{9}$/,
																	regexText : '非法手机号码，请输入11位手机号码.',
																	anchor : '93%'
																}, {
																	id : 'graduateSchool',
																	xtype : 'textfield',
																	fieldLabel : '毕业院校',
																	msgTarget : 'side',
																	name : 'graduateSchool',
																	anchor : '93%'
																}, {
																	id : 'enabled',
																	fieldLabel : '账户情况',
																	name : 'enabled',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	allowBlank : false,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getStatusStore(),
																	valueField : 'option_key',
																	displayField : 'option_value',
																	anchor : '93%'
																}]
													}, {
														columnWidth : .33,
														layout : 'form',
														border : false,
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '证件号码',
																	id : 'identity',
																	msgTarget : 'side',
																	regex : /(^\d{15}$)|(^\d{17}([0-9]|[X,x])$)/,
																	regexText : '请输入正确的身份证号码。',
																	emptyText : '请输入15或18位身份证号码',
																	allowBlank : true,
																	name : 'identity',
																	anchor : '93%'
																}, {
																	id : 'maritalStatus',
																	fieldLabel : '婚姻情况',
																	name : 'maritalStatus',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getMaritalStore(),
																	valueField : 'option_key',
																	displayField : 'option_value',
																	anchor : '93%'
																}, new Ext.form.DateField({
																			fieldLabel : '工作时间',
																			name : 'workdate',
																			id : 'workdate',
																			anchor : '93%',
																			format : 'Y年m月d日'
																		}), {
																	xtype : 'textfield',
																	id : 'extensionNo',
																	fieldLabel : '分机号码',
																	msgTarget : 'side',
																	name : 'extensionNo',
																	regex : /^\d{0,12}$/,
																	regexText : '请以数据形式输入少于12位的分机号码。',
																	allowBlank : true,
																	anchor : '93%'
																}, {
																	id : 'degree',
																	fieldLabel : '学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位',
																	name : 'degree',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getDegreeStore(),
																	valueField : 'regValue',
																	displayField : 'displayValue',
																	anchor : '93%'
																}]
													}, {

														columnWidth : .34,
														layout : 'form',
														border : false,
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '内部邮箱',
																	msgTarget : 'side',
																	name : 'workEmail',
																	vtype : 'email',
																	vtypeText : '请输入合法的Email',
																	anchor : '93%'
																}, {
																	xtype : 'textfield',
																	fieldLabel : '单位邮箱',
																	msgTarget : 'side',
																	name : 'cmpyEmail',
																	vtype : 'email',
																	vtypeText : '请输入合法的Email',
																	anchor : '93%'
																}, {
																	id : 'ipAddress',
																	xtype : 'textfield',
																	fieldLabel : '网络地址',
																	msgTarget : 'side',
																	allowBlank : false,
																	regex : /^\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}$/,
																	regexText : '无效IP地址，请以192.168.1.1形式输入。',
																	name : 'ipAddress',
																	anchor : '93%'
																}, {
																	id : 'employeeNo',
																	xtype : 'textfield',
																	fieldLabel : '工牌号码',
																	msgTarget : 'side',
																	name : 'employeeNo',
																	allowBlank : true,
																	anchor : '93%'
																}, {
																	id : 'educationBg',
																	fieldLabel : '学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历',
																	name : 'educationBg',
																	xtype : 'combo',
																	mode : 'local',
																	editable : false,
																	typeAhead : true,
																	forceSelection : true,
																	triggerAction : 'all',
																	store : _dataModel.getEducationStore(),
																	valueField : 'regValue',
																	displayField : 'displayValue',
																	anchor : '93%'
																}, {
																	id : 'specialtyKnow',
																	xtype : 'textfield',
																	fieldLabel : '所属专业',
																	msgTarget : 'side',
																	name : 'specialtyKnow',
																	anchor : '93%'
																}]
													}]
										}]
							}]
				});

		user_dialog = new Ext.Window({
					title : '详细信息窗口',
					width : 780,
					height : 250,
					id : 'dialog_user_win',
					closeAction : 'hide',
					animCollapse : true,
					plain : true,
					layout : 'border',
					modal : true,
					resizable : false,
					activeItem : 0,
					items : [userBase_form],
					buttons : [{
								text : '提交',
								handler : function(/* Button */btn,/* Event */
										e) {
									// 提交前先判断必填项是否已填
									var instance = userBase_form.getForm().getFieldValues();
									instance.userId = _user_Id;
									// alert(dwr.util.toDescriptiveString(instance,
									// 2));
									_dataModel.handleAjax(instance, user_dialog);
								}
							}, {
								text : '取消',
								handler : function(/* Button */btn,/* Event */
										e) {
									userBase_form.getForm().reset();
									user_dialog.hide(btn);
								}
							}]
				});
	} // end_fun
} // end_class
