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
			recordStore = new Ext.data.GroupingStore({
						autoDestroy : true,
						autoLoad : false,
						groupField : 'groupBy',
						sortInfo : {
							field : 'userId',
							direction : 'ASC'
						},
						storeId : 'recordStore',
						reader : new Ext.data.JsonReader({
									totalProperty : 'total',
									idProperty : 'recId',
									root : 'data',
									fields : recordStruts
								}),
						remoteGroup : false,
						url : '/workbase/console/dutyclt/list.json'
					});
		}
		return recordStore;
	};

	var recordStruts = ['recId', 'userId', 'projectName', 'userName', 'groupBy', {
				name : 'sickDays',
				type : 'int'
			}, {
				name : 'thindDays',
				type : 'int'
			}, {
				name : 'laterDay',
				type : 'int'
			}, {
				name : 'leaveDay',
				type : 'int'
			}, {
				name : 'marryDays',
				type : 'int'
			}, {
				name : 'deadDays',
				type : 'int'
			}, {
				name : 'laborDays',
				type : 'int'
			}, {
				name : 'happyDays',
				type : 'int'
			}, {
				name : 'homeDays',
				type : 'int'
			}, {
				name : 'learnDays',
				type : 'int'
			}, {
				name : 'changeDays',
				type : 'int'
			}, {
				name : 'dutyTotal',
				type : 'int'
			}, {
				name : 'allTotal',
				type : 'int'
			}, 'comments'];
	var myReader = new Ext.data.JsonReader({
				idProperty : 'srcId',
				root : 'data',
				fields : recordStruts
			});

	var recordStore = null;
} // end_class
