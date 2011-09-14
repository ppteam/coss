/**
 * @class 资源管理建模
 * @author haoxiaojie
 */
function DataModel() {

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getRecordStore = function() {
		if (recordStore === null) {
			recordStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							list_deptIds : null
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'userId',
						storeId : 'recordStore',
						fields : recordStruts,
						url : '/workbase/console/duty/happydays.json'
					});
		}
		return recordStore;
	};

	var recordStruts = ['userId', 'userName', 'deptName', {
				name : 'overTime',
				type : 'int'
			}, {
				name : 'leaveTime',
				type : 'int'
			}];

	var recordStore = null;
} // end_class
