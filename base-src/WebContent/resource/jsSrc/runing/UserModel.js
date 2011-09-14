/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function UserModel() {

	this.setTreeDog = function(td) {
		_treeDog = td;
	};

	/**
	 * @function 清除数据库中无效的数据
	 * @param {}
	 *            store
	 */
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store) {
		cleanInvaladDataAction(store);
	};

	this.creatNewUser = function() {
		return new UserRecord();
	};
	this.getPageSize = function() {
		return def_page_size;
	};

	this.getUserListStruts = function() {
		return userListStruts;
	};

	/**
	 * {注册或则修改用户进场信息}
	 * 
	 * @param {}
	 *            user
	 * @param {}
	 *            win
	 */
	this.saveOrupdateAction = function(/* UserRecord */user,/* Ext.grid.GridPanel */grid) {
		// alert(dwr.util.toDescriptiveString(user, 2));
		UpmrAjaxAction.saveOrUpdateUser(user, function(respone) {
					if (!respone.error) {
						if (user.userId === null) {
							Ext.Msg.alert('提示', '新员工进场操作成功。');
							userListStore.reload(userListStore.lastOptions.params);
						} else {
							userListStore.commitChanges();
							Ext.Msg.alert('提示', '编辑用户信息成功。');
						}
					} else {
						cleanInvaladDataAction(userListStore);
						Ext.Msg.alert('错误提示', respone.msg);
					}
				});
	};

	/**
	 * {注册或则修改用户进场信息}
	 * 
	 * @param {}
	 *            user
	 * @param {}
	 *            win
	 */
	this.clearUserAction = function(/* String */userId,/* int */sign) {
		UpmrAjaxAction.editTeamStats(userId, sign, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						userListStore.getById(userId).set('teamStats', sign);
						userListStore.commitChanges();
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
				});
	};
	/**
	 * {加载用户信息，用户修改操作}
	 * 
	 * @param {}
	 *            user_Id
	 * @param {}
	 *            uForm
	 * @param {}
	 *            win
	 */
	this.loadUserById = function(/* String */user_Id,/* Ext.form */uForm,/* Ext.Window */
			win) {
		Ext.Ajax.request({
					url : '/workbase/console/user/findUserById.json',
					params : {
						userId : user_Id
					},
					success : function(resp, opts) {
						uForm.getForm().reset();
						var _userData = Ext.util.JSON.decode(resp.responseText);
						var _rec = creatUserRecord(_userData.data);
						uForm.getForm().loadRecord(_rec);
						win.show();
					},
					failure : function(resp, opts) {
						Ext.Msg.alert('错误', '加载个人信息发生异常，请稍后再试。');
					}
				});

	}; // end_fun

	function creatUserRecord(/* JSON */userData) {
		var rec = new UserRecord();
		// alert(dwr.util.toDescriptiveString(rec.fields.map, 2));
		for (var p in userData) {
			rec.set(p, userData[p]);
		} // end_for
		// alert(dwr.util.toDescriptiveString(rec.fields.map, 2));
		return rec;
	}

	this.getUserListStore = function() {
		if (userListStore === null) {
			userListStore = new Ext.data.JsonStore({
						autoDestroy : false,
						autoLoad : true,
						storeId : 'userListStore',
						baseParams : {
							int_start : 0,
							int_limit : def_page_size
						},
						root : 'data',
						idProperty : 'userId',
						url : '/workbase/console/user/listAll.json',
						fields : userListStruts
					});
		}
		return userListStore;
	};

	// 性别列表，1-男，0-女
	this.getSexdStore = function() {
		if (sexdStore === null) {
			sexdStore = new Ext.data.ArrayStore({
						autoDestroy : false,
						fields : defindStruts
					});
			sexdStore.loadData(sexdValue);
		}
		return sexdStore;
	};

	this.getPosttypeStore = function() {
		if (posttypeStore === null) {
			posttypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : posttype
					});
		}
		return posttypeStore;
	}; // end_fun

	this.getDeptStore = function() {
		if (deptStore === null) {
			deptStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dept_data
					});
		}
		return deptStore;
	};
	/**
	 * {清空staort 中的非法数据}
	 * 
	 * @param {}
	 *            store
	 */
	function cleanInvaladDataAction(/* Ext.data.JsonStore */store) {
		store.each(function(/* Record */rec) {
					if (rec.get('userId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	// 用户列表
	var userListStruts = ['userId', 'workPlace', {
				name : 'teamStats',
				type : 'int'
			}, 'deptId', 'name', 'companyName', {
				name : 'sexed',
				type : 'int'
			}, 'loginId', 'deptName', 'postType'];

	var userListStore = null;
	var deptStore = null;
	var posttypeStore = null;
	// 性别情况
	var sexdStore = null;
	var sexdValue = [[1, '男'], [0, '女']];
	var def_page_size = 20; // 默认的分页数量
	var roleIds = new Array();
	var _def_page_size = 20;
	var defindStruts = [{
				name : 'option_key',
				type : 'int'
			}, {
				name : 'option_value'
			}];
} // end_fun
