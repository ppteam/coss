/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getReportStore = function() {
		if (reportStore === null) {
			var ids = this.initProjectIds();
			reportStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							list_projectIds : ids,
							date_week : null
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'reportId',
						storeId : 'reportStore',
						fields : recordStruts,
						url : '/workbase/console/pwreport/list.json'
					});
		}
		return reportStore;
	};

	/**
	 * {初始化查询列表数据}
	 */
	this.initProjectIds = function() {
		var args = new Array();
		if (dataSet_prolist.length > 0) {
			for (var i = 0; i < dataSet_prolist.length; i++) {
				args.push(dataSet_prolist[i][0]);
			}
		} else {
			args.push('NO_ANY_PROJECTIDS');
		}
		return args.join('|');
	}; // end_fun

	var recordStruts = ['reportId', 'leaderApp', 'projectId', 'reportNo', 'projectDesc', 'projectName', 'startDate',
			'endDate', 'psmerName', {
				name : 'members',
				type : 'int'
			}, 'proStats', {
				name : 'plmCnt',
				type : 'int'
			}, {
				name : 'reportType',
				type : 'int'
			}];

	this.getDateSetProsStore = function() {
		if (prolistStore === null) {
			prolistStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dataSet_prolist
					});
		}
		return prolistStore;
	};

	var reportStore = null;
	var prolistStore = null;
	var def_page_size = 20;
}
