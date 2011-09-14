/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function UserModel() {

	var UserRecord = Ext.data.Record.create(['userId', 'loginId', 'identity', 'workPlace', 'maritalStatus',
			'teamStats', 'qualifications', 'employeeNo', 'mobileNo', 'educationBg', 'workEmail', 'deptId', 'name',
			'extensionNo', 'cmpyEmail', {
				name : 'birthday',
				type : 'date'
			}, {
				name : 'workdate',
				type : 'date'
			}, 'password', 'deptName', 'ipAddress', 'companyName', 'polticsStatus', {
				name : 'sexed',
				type : 'int'
			}, 'loginId', 'graduateSchool', {
				name : 'enabled',
				type : 'int'
			}, 'specialtyKnow', 'degree']);

	this.creatNewUser = function() {
		return new UserRecord();
	};
	this.getPageSize = function() {
		return def_page_size;
	};

	this.getUserListStruts = function() {
		return userListStruts;
	};

	this.resetPwd = function(/* String */userId) {
		if (userId === null || '' === userId) {
			Ext.Msg.alert('提示', '读取用户Id发生异常，请检查操作或联系系统管理员');
		} else {
			UpmrAjaxAction.resetPassWord("change", "", userId, function(respone) {
						if (!respone.error && respone.callData.resetpsd === 1) {
							Ext.Msg.alert('操作', '密码重置操作成功');
						} else {
							Ext.Msg.alert('操作', respone.msg);
						}
					});
		} // end_if
	};

	this.handleAjax = function(/* UserBase */instance,/* Ext.Window */
			window) {
		UpmrAjaxAction.editUserBaseInfo(instance, null, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						userListStore.reload(userListStore.lastOptions.params);
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
					window.hide();
				});
	}; // end_fun

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
						if (typeof _rec.data.birthday === 'undefined' || _rec.data.birthday === null) {
							_rec.data.birthday = null;
						} else {
							_rec.data.birthday = new Date(_rec.data.birthday);
						}
						if (typeof _rec.data.workdate === 'undefined' || _rec.data.workdate === null) {
							_rec.data.workdate = null;
						} else {
							_rec.data.workdate = new Date(_rec.data.workdate);
						}
						// alert(dwr.util.toDescriptiveString(_rec.data, 2));
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
						url : '/workbase/console/user/list.json',
						fields : userListStruts
					});
		}
		return userListStore;
	};

	// 政治面貌列表
	this.getPoliticsStore = function() {
		if (politicsStore === null) {
			politicsStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : politic
					});
		}
		return politicsStore;
	};

	// 学历列表
	this.getEducationStore = function() {
		if (educationStore === null) {
			educationStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : educationBackground
					});
		}
		return educationStore;
	};

	// 部门树
	this.getDeptStore = function() {
		if (deptStore === null) {
			deptStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dept_data
					});
		}
		return deptStore;
	};

	// 学位列表
	this.getDegreeStore = function() {
		if (degreeStore === null) {
			degreeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : degree
					});
		}
		return degreeStore;
	};

	// 入场情况，1-在场,0-离场
	this.getTeamStatsStore = function() {
		if (teamStatsStore === null) {
			teamStatsStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : factoryStatus
					});
		}
		return teamStatsStore;
	};

	// 职称等级列表
	this.getQualificationsStore = function() {
		if (qualificationsStore === null) {
			qualificationsStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : qualification
					});
		}
		return qualificationsStore;
	};

	// 账户状态列表，1-启用，0-禁用
	this.getStatusStore = function() {
		if (statusStore === null) {
			statusStore = new Ext.data.ArrayStore({
						autoDestroy : false,
						fields : defindStruts
					});
			statusStore.loadData(statusValue);
		}
		return statusStore;
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

	// 婚姻情况，0-未婚，1-已婚
	this.getMaritalStore = function() {
		if (maritalStore === null) {
			maritalStore = new Ext.data.ArrayStore({
						autoDestroy : false,
						fields : defindStruts
					});
			maritalStore.loadData(maritalValue);
		}
		return maritalStore;
	};

	// 用户列表
	var userListStruts = ['userId', 'identity', 'workPlace', 'maritalStatus', {
				name : 'teamStats',
				type : 'int'
			}, 'qualifications', 'cmpyEmail', 'employeeNo', 'mobileNo', 'educationBg', 'workEmail', 'deptId', 'name',
			'extensionNo', {
				name : 'birthday',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'workdate',
				type : 'int',
				convert : locationToDate
			}, 'password', 'postType', 'ipAddress', 'companyName', 'polticsStatus', {
				name : 'sexed',
				type : 'int'
			}, 'loginId', 'graduateSchool', {
				name : 'enabled',
				type : 'int'
			}, 'specialtyKnow', 'deptName'];

	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun
	var userListStore = null;
	// 用户状态情况
	var statusStore = null;
	var statusValue = [[1, '启用'], [0, '禁用']];
	// 性别情况
	var sexdStore = null;
	var sexdValue = [[1, '男'], [0, '女']];
	// 婚姻情况
	var maritalStore = null;
	var maritalValue = [[0, '未婚'], [1, '已婚']];

	var teamStatsStore = null; // 入场情况
	var politicsStore = null;// 政治面貌列表
	var educationStore = null;// 学历列表
	var degreeStore = null;// 学位列表
	var qualificationsStore = null;// 职称等级列表
	var deptStore = null;// 职称等级列表
	var def_page_size = 20; // 默认的分页数量

	var optionStruts = [{
				name : 'regValue',
				type : 'string'
			}, {
				name : 'displayValue',
				type : 'string'
			}];
	var defindStruts = [{
				name : 'option_key',
				type : 'int'
			}, {
				name : 'option_value'
			}];
} // end_fun
