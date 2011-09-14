/**
 * @class 角色管理建模
 * @author haoxiaojie
 */
function DataModel() {

	/**
	 * 
	 * @return {当前全部列表数据Id}
	 */
	this.getAuthorIds = function() {
		return authorIds;
	};

	this.getRoleTypeStrore = function() {
		return roleTypeStrore;
	};

	/**
	 * 
	 * @return {}
	 */
	this.getRecordStruts = function() {
		return recordStruts;
	};
	this.getPageSize = function() {
		return def_page_size;
	};
	/**
	 * @function 清除数据库中无效的数据
	 * @param {}
	 *            store
	 */
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store) {
		cleanInvaladDataAction(store);
	};

	this.getAuthorStore = function() {
		return authorStore;
	};

	/**
	 * {初始化Panle}
	 * 
	 * @param {}
	 *            fpanle
	 */
	this.initAuthorGroup = function(/* Ext.FormPanel */fpanle) {
		Ext.Ajax.request({
					url : '/workbase/console/author/enableRec.json',
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _arg = Ext.util.JSON.decode(reps.responseText);
							if (_arg.data !== null) {
								var ckGroup = Ext.getCmp('author_fieldset');
								var ck_groups = {
									xtype : 'checkboxgroup',
									columns : 4,
									items : []
								};
								for (var i = 0; i < _arg.data.length; i++) {
									authorIds.push(_arg.data[i].authorId);
									ck_groups.items[i] = new Ext.form.Checkbox({
												boxLabel : _arg.data[i].authorName,
												name : _arg.data[i].authorId,
												id : _arg.data[i].authorId
											});
								} // end_for
								ckGroup.add(ck_groups);
							} // end_if
						} // end_fi
					}
				});
	};

	this.getAuthorByRole = function(/* Stirng */roleId_value,/* Ext.Window */
			win,/* Ext.FormPanel */fPanle) {
		getAuthorByRoleAction(roleId_value, win, fPanle);
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getRecordStore = function() {
		if (recordStore === null) {
			recordStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'roleId',
						storeId : 'roleAllStore',
						fields : recordStruts,
						url : '/workbase/console/role/allRecord.json'
					});
		}
		return recordStore;
	};

	/**
	 * {指定角色授权}
	 * 
	 * @param {}
	 *            roleId
	 * @param {}
	 *            authorIds
	 */
	this.AuthorAjax = function(/* String */roleId,/* Array */authorIds,/* Ext.Window */
			win) {
		ArsdRemoteAction.authorizationAction(roleId, authorIds, function(respone) {
					if (respone.error) {
						Ext.Msg.alert('Message', respone.msg);
					} else {
						Ext.Msg.alert('Message', "授权成功，请执行其他操作");
					}
					win.hide();
				});
	};

	this.handleAjax = function(/* DictCatalog */instance,/* string */type,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		var _is_save = instance.roleId === null ? true : false;
		if (type === 'role') { // 目录操作
			ArsdRemoteAction.saveOrUpdateRole(instance, function(respone) {
						if (!respone.error) {
							if (_is_save) {
								Ext.Msg.alert('信息提示', '新增操作成功，需要重新登录方能生效。');
								recordStore.reload(recordStore.lastOptions.params);
							} else {
								grid.getStore().commitChanges();
							}
						} else {
							if (_is_save) {
								cleanInvaladDataAction(recordStore);
							} else {
								grid.getView().refresh();
							}
							Ext.Msg.alert('Message', respone.msg);
						}
					});
		} else {
			Ext.Msg.alert('错误提示', '非法的参数请求，请联系系共管理员。');
		}
	}; // end_fun

	/**
	 * {清空staort 中的非法数据}
	 * 
	 * @param {}
	 *            store
	 */
	function cleanInvaladDataAction(/* Ext.data.JsonStore */store) {
		store.each(function(/* Record */rec) {
					if (rec.get('roleId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	/**
	 * 
	 * @param {}
	 *            roleId
	 */
	function getAuthorByRoleAction(/* Stirng */roleId_value,/* Ext.Window */
			win,/* Ext.FormPanel */fPanle) {
		// alert(dwr.util.toDescriptiveString(argValue.data, 2))
		Ext.Ajax.request({
					url : '/workbase/console/role/authors.json',
					params : {
						roleId : roleId_value
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							// 重置表单
							for (var i = 0; i < authorIds.length; i++) {
								Ext.getCmp(authorIds[i]).setValue(false);
							}
							var _arg = Ext.util.JSON.decode(reps.responseText);
							if (_arg.data !== null) {
								for (var i = 0; i < _arg.data.length; i++) {
									Ext.getCmp(_arg.data[i]).setValue(true);
								}
							}
							win.show();
						} else {
							Ext.Msg.alert('错误提示', '后台通信异常，请联系系统管理员。');
						}
					}
				});
	} // end_fun

	var recordStruts = [{
				name : 'roleId',
				type : 'string'
			}, {
				name : 'roleName',
				type : 'string'
			}, {
				name : 'roleSign',
				type : 'string'
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'roleDesc',
				type : 'string'
			}, 'authorityInfos'];
	var authorStruts = [{
				name : 'authorId',
				type : 'string'
			}, {
				name : 'authorSign',
				type : 'string'
			}, {
				name : 'authorDesc',
				type : 'string'
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'authorName',
				type : 'string'
			}];
	var roleTypeStrore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : roleType
			});
	var recordStore = null;
	var def_page_size = 20;
	var authorIds = new Array();
}
