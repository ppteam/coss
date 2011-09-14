/**
 * @class 部门树操作
 * @author haoxiaojie
 */
function DataModel() {

	this.handlerAjax = function(/* Class */instance,/* int */action) {
		UpmrAjaxAction.insertOrUpdateDept(instance, action, function(respone) {
					if (!respone.error) {
						Ext.Msg.alert('操作提示', '操作成功，请手动刷新页面同步数据。');
					} else {
						Ext.Msg.alert('错误信息', respone.msg);
					}
				});
	};

	this.getRecord = function() {
		return recordStruts;
	};

	var recordStruts = ['deptId', 'fatherId', 'deptName', 'nodeXpath', {
				name : 'nodeDeep',
				type : 'int'
			}, {
				name : 'deptOrder',
				type : 'int'
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'leafNode',
				type : 'int'
			}];
}
