/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function UserModel() {

	this.getPageSize = function() {
		return def_page_size;
	};

	this.getUserListStruts = function() {
		return userListStruts;
	};

	/**
	 * {初始化Panle}
	 * 
	 * @param {}
	 *            fpanle
	 */
	this.initRoleGroup = function(/* Ext.FormPanel */fpanle) {
		if (roleopts.length > 0) {
			var ckGroup = Ext.getCmp('role_fieldset');
			var ck_groups = {
				xtype : 'checkboxgroup',
				columns : 3,
				items : []
			};
			for (var i = 0; i < roleopts.length; i++) {
				roleIds.push(roleopts[i][0]);
				ck_groups.items[i] = new Ext.form.Checkbox({
							boxLabel : roleopts[i][1],
							name : roleopts[i][0],
							id : roleopts[i][0]
						});
			} // end_for
			ckGroup.add(ck_groups);
		} // end_if
	}; // end_fun

	/**
	 * 
	 */
	this.handleAccessData = function(/* Ext.data.Record */rec,/* Array */deptIds,/* Ext.Window */
			window) {
		deptIds = deptIds.length === 0 ? null : deptIds;
		UpmrAjaxAction.editAccDataSet(rec.get('userId'), deptIds, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						rec.set('dataSet', deptIds === null ? new Array() : deptIds);
						userListStore.commitChanges();
						window.hide();
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
					window.hide();
				});
	};

	this.handleRoleAction = function(/* Ext.data.Record */rec,/* Ext.Window */
			window) {
		var selected_roleIds = new Array();
		for (var i = 0; i < roleIds.length; i++) {
			if (Ext.getCmp(roleIds[i]).getValue()) {
				selected_roleIds.push(roleIds[i]);
			}
		} // end_for
		if (selected_roleIds.length === 0) {
			selected_roleIds = null;
		}
		// alert(dwr.util.toDescriptiveString(selected_roleIds, 2));
		UpmrAjaxAction.editUserMapRole(rec.get('userId'), selected_roleIds, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						rec.set('roleSet', selected_roleIds === null ? new Array() : selected_roleIds);
						userListStore.commitChanges();
						window.hide();
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
					window.hide();
				});
	}; // end_fun

	/**
	 * {加载用户数据集权列表}
	 * 
	 * @param {}
	 *            rec
	 * @param {}
	 *            win
	 * @param {}
	 *            tPanel
	 */
	this.loadDatSetByUserId = function(/* Ext.data.Record */rec,/* Ext.Window */win,/* Ext.tree.TreePanel */tPanel,/* Boolean */
			loaded) {
		win.show();
		if (loaded) {
			// alert(dwr.util.toDescriptiveString(rec, 2));
			var selectedIds = tPanel.getChecked("id");
			if (selectedIds.length !== 0) {
				for (var i = 0; i < selectedIds.length; i++) {
					tPanel.getNodeById(selectedIds[i]).getUI().toggleCheck(false);
				}
			}
			if (rec.get('dataSet').length != 0) {
				var _datalist = rec.get('dataSet');
				// alert(dwr.util.toDescriptiveString(_datalist, 2));
				for (var i = 0; i < _datalist.length; i++) {
					tPanel.getNodeById(_datalist[i]).getUI().toggleCheck(true);
				}
			} // end_if
		}
	};

	this.loadRoleByUser = function(/* Ext.data.Record */rec,/* Ext.Window */
			win) {
		// alert(dwr.util.toDescriptiveString(rec, 2));
		cleanRoleForm();
		if (rec.get('roleSet').length != 0) {
			var _roleslist = rec.get('roleSet');
			for (var i = 0; i < _roleslist.length; i++) {
				Ext.getCmp(_roleslist[i]).setValue(true);
			}
		} // end_if
		win.show();
	}; // end_fun

	/**
	 * 重置角色选择对话框表单数据
	 */
	function cleanRoleForm() {
		for (var i = 0; i < roleIds.length; i++) {
			Ext.getCmp(roleIds[i]).setValue(false);
		}
	} // end_fun

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
						url : '/workbase/console/user/list.json?needRole=yes&needData=yes',
						fields : userListStruts
					});
		}
		return userListStore;
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

	// 用户列表
	var userListStruts = ['userId', 'identity', 'workPlace', 'maritalStatus', {
				name : 'teamStats',
				type : 'int'
			}, 'qualifications', 'employeeNo', 'mobileNo', 'educationBg', 'workEmail', 'deptId', 'name', 'extensionNo',
			{
				name : 'birthday',
				type : 'date'
			}, 'password', 'postType', 'ipAddress', 'companyName', 'polticsStatus', {
				name : 'sexed',
				type : 'int'
			}, {
				name : 'enabled',
				type : 'int'
			}, 'loginId', 'graduateSchool', {
				name : 'enabled',
				type : 'int'
			}, 'specialtyKnow', 'deptName', 'roleSet', 'dataSet'];

	var _select_nodes = new Array();
	var userListStore = null;
	// 用户状态情况
	var statusStore = null;
	var statusValue = [[1, '启用'], [0, '禁用']];
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
