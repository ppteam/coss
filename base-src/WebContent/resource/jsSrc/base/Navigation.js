/**
 * @author HaoXiaoJie
 */
Ext.apply(Ext.form.VTypes, {
			password : function(val, field) {
				if (field.initialPassField) {
					var pwd = Ext.getCmp(field.initialPassField);
					if (val == pwd.getValue()) {
						Ext.getCmp('smt_pwd_btn').setDisabled(false);
					} else {
						Ext.getCmp('smt_pwd_btn').setDisabled(true);
					}
					return (val == pwd.getValue());
				}
				return true;
			},
			passwordText : '密码不一致，请检测密码输入项！'
		});

function Navigation() {

	this.init = function() {
		toolbar = new Ext.Toolbar({
					items : [{
						text : '通用功能',
						iconCls : 'menu_common',
						menu : [{
							text : '待办事宜',
							iconCls : 'btn_rember',
							handler : function(/* Ext.button */btn) {
								window.location.href = 'http://' + window.location.host
										+ '/workbase/console/welcome/view.ftl';
							}
						}, {
							text : '密码修改',
							iconCls : 'btn_password',
							handler : function(/* Ext.button */btn) {
								builderWin();
							}
						}, {
							text : '相关下载',
							iconCls : 'sub_menu_down',
							handler : function(/* Ext.button */btn) {
								window.location.href = 'http://' + window.location.host
										+ '/workbase/console/welcome/download.ftl';
							}
						}]
					}, '-']
				});
		ajaxLoadMenus();
		return toolbar;
	};

	/**
	 * {异步获取菜单数据}
	 */
	function ajaxLoadMenus() {
		Ext.Ajax.request({
					url : '/workbase/console/source/menus.json',
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _arg = Ext.util.JSON.decode(reps.responseText);
							if (_arg.data !== null) {
								// alert(dwr.util.toDescriptiveString(_arg.data,2));
								builderMenuByJson(_arg.data);
							}
							addtail();
						} else {
							addtail();
							Ext.Msg.alert('Message', '加载菜单项失败..(*^__^*)！');
						}
					}
				});
	} // end_if

	function addtail() {
		toolbar.add('->', '-', {
					text : '签&nbsp;&nbsp;&nbsp;到',
					iconCls : 'btn_login',
					handler : function(/* Ext.button */btn) {
						DutyAjaxAction.checkAction(0, function(respone) {
									if (!respone.error) {
										Ext.Msg.alert('操作提示', '新的一天开始了，祝你工作愉快。');
									} else {
										Ext.Msg.alert('错误提示', respone.msg);
									}
								});
					}
				}, '-', {
					text : '签&nbsp;&nbsp;&nbsp;退',
					iconCls : 'btn_logoff ',
					handler : function(/* Ext.button */btn) {
						Ext.MessageBox.confirm('系统提示', '结束一天工作了,这不是一件让人愉快的事情吗？', function(btn) {
									if (btn === 'yes') {
										DutyAjaxAction.checkAction(1, function(respone) {
													if (!respone.error) {
														Ext.MessageBox.alert('操作提示', '签退成功，请执行其他操作');
													} else {
														Ext.MessageBox.alert('错误提示', respone.msg);
													}
												});
									}
								});

					}
				}, '-', {
					text : '退&nbsp;&nbsp;&nbsp;出',
					iconCls : 'button_logout',
					handler : function(/* button */btn) {
						window.location.href = 'http://' + window.location.host + '/workbase/j_spring_security_logout';
					}
				}, '-', {
					text : '关于系统',
					iconCls : 'button_about',
					handler : function(/* button */btn) {
						Ext.MessageBox.alert('系统说明',
								'系统名称:工时管理系统 V 0.1版本<br/><br/>使用日期： 2011-08-01 <br/><br/> 版权归 东软集团广州研发金融部所有 禁止盗版。。。');
					}
				}, '-');
		toolbar.doLayout();
	}
	/**
	 * {通过JSON串 builder 菜单数组}
	 * 
	 * @param {}
	 *            menuJson
	 */
	function builderMenuByJson(/* Array */menu_args) {
		var menuItmes = new Array();
		for (var i = 0; i < menu_args.length; i++) {
			var menuItmes = menu_args[i].items;
			var _menu = new Ext.menu.Menu();
			for (var j = 0; j < menuItmes.length; j++) {
				_menu.add({
							text : menuItmes[j].menuName,
							url : menuItmes[j].menuValue,
							id : menuItmes[j].menuId,
							iconCls : menuItmes[j].stylecss,
							listeners : {
								click : function( /* Ext.menu.BaseItem */item, /* Ext.EventObject */
										e) {
									window.location.href = 'http://' + window.location.host + '/workbase/console/'
											+ item.url;
								}
							}
						});
			} // end_for
			toolbar.add({
						text : menu_args[i].menuName,
						iconCls : menu_args[i].menuCss,
						menu : _menu
					}, '-');
		} // end_fun
	} // end_fun

	/**
	 * {构建密码修改对话框}
	 */
	function builderWin() {
		if (_win_changePwd === null) {
			_frm_pwd = new Ext.FormPanel({
						region : 'center',
						labelWidth : 75,
						frame : true,
						bodyStyle : 'padding:5px 5px 0',
						width : 350,
						defaults : {
							width : 175,
							inputType : 'password'
						},
						defaultType : 'textfield',
						items : [{
									fieldLabel : '原始密码',
									name : 'oldpass',
									allowBlank : false,
									id : 'oldpass'
								}, {
									fieldLabel : '设置密码',
									name : 'newpass',
									allowBlank : false,
									id : 'newpass'
								}, {
									fieldLabel : '确认密码',
									name : 'cfmpass',
									allowBlank : false,
									id : 'cfmpass',
									vtype : 'password',
									initialPassField : 'newpass'
								}]
					});

			_win_changePwd = new Ext.Window({
						layout : 'border',
						width : 350,
						height : 180,
						closeAction : 'hide',
						plain : true,
						modal : true,
						resizable : false,
						items : _frm_pwd,
						buttons : [{
									text : '修改',
									disabled : true,
									id : 'smt_pwd_btn',
									handler : function(/* Ext.button */btn) {
										if (_frm_pwd.getForm().isValid()) {
											var pw1 = Ext.getCmp('oldpass').getValue();
											var pw2 = Ext.getCmp('newpass').getValue();
											UpmrAjaxAction.resetPassWord(pw1, pw2, null, function(respone) {
														_win_changePwd.hide();
														// alert(dwr.util.toDescriptiveString(respone,
														// 2));
														if (!respone.error && respone.callData.resetpsd === 1) {
															Ext.Msg.alert('操作提示', '密码修改成功');
														} else {
															Ext.Msg.alert('操作提示', respone.msg);
														}
													});
										} // end_if
									} // end_handler
								}, {
									text : '取消',
									handler : function() {
										_win_changePwd.hide();
									}
								}]
					});
		} // end_if
		Ext.getCmp('smt_pwd_btn').setDisabled(true);
		_win_changePwd.show();
	}
	var _frm_pwd = null;
	var _win_changePwd = null;
	var toolbar = null;
}