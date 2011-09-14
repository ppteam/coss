/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function WeekModel() {
	var deptStore = null;
	var userStore = null;
	this.getDeptStrore = function() {
		if (deptStore === null) {
			deptStore = new Ext.data.ArrayStore({
						fields : [{
									name : 'regValue',
									type : 'string'
								}, {
									name : 'displayValue',
									type : 'string'
								}],
						data : dept_data
					});
		}
		return deptStore;
	};
	this.getUserStore = function(str) {
		if (str === null) {
			return null;
		}
		if (userStore === null) {
			userStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						root : 'data',
						idProperty : 'userID',
						storeId : 'userStore',
						fields : [{
									name : 'regValue',
									type : 'string'
								}, {
									name : 'displayValue',
									type : 'string'
								}],
						url : '/workbase/console/reportWeek/findUserByDeptId.json'
					});
		}
		return userStore;
	};
	
	this.handleAjax = function(/* UserBase */instance,/* Ext.Window */
			window) {
		ReportAjaxAction.personWeek(instance, null, function(respone) {
					if (!respone.error) {
						// userListStore.reload(userListStore.lastOptions.params);
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
					window.hide();
				});
	};
} // end_fun
