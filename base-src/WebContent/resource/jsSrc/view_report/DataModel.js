/**
 * @class 查看日报Bean 建模
 * @author lishijian
 */
function ViewDailyReportModel() {

	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	// 获取全部列表信息
	this.getAllDailyReportInfoStore = function() {
		if (allDailyReportInfoStore == null) {
			allDailyReportInfoStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							projectId : null,
							userName : null,
							reportDate : null
						},
						storeId : 'dailyReportInfoStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'dreportID',
						url : '/workbase/console/viewdaily/viewAllDailyReport.json',
						fields : createDailyReportRecord
					});
		}
		return allDailyReportInfoStore;
	};

	// 项目名称下拉框数据
	this.getProjectListStore = function() {
		if (projectListStore === null) {
			var source_dt = new Array();
			projectListStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dataSet_prolist
					});
		} // end_if
		return projectListStore;
	};

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	var createDailyReportRecord = [{
				name : 'dreportID',
				type : 'string'
			}, {
				name : 'projectName',
				type : 'string'
			}, {
				name : 'userName',
				type : 'string'
			}, {
				name : 'deptName',
				type : 'string'
			}, {
				name : 'reportDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'weekday',
				type : 'string'
			}, {
				name : 'workHours',
				type : 'int'
			}, {
				name : 'workType',
				type : 'string'
			}, {
				name : 'workActivity',
				type : 'string'
			}, {
				name : 'workSubActivity',
				type : 'string'
			}, {
				name : 'workStats',
				type : 'string'
			}, {
				name : 'recordorID',
				type : 'string'
			}, {
				name : 'recordDate',
				type : 'string'
			}, {
				name : 'workContent',
				type : 'string'
			}, {
				name : 'resultsShow',
				type : 'string'
			}, {
				name : 'repComment',
				type : 'string'
			}];

	var allDailyReportInfoStore = null;// 查询所有列表
	var def_page_size = 25;
	var projectListStore = null;// 项目名称
}
