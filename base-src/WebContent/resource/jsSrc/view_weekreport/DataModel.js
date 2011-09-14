/**
 * @class 查看日报Bean 建模
 * @author lishijian
 */
function DataModel() {

	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	// detailStore
	this.getDetailStore = function() {
		if (detailStore == null) {
			detailStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						storeId : 'detailStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'dreportID',
						url : '/workbase/console/viewWeekReports/detailList.json',
						fields : detailStruts
					});
		}
		return detailStore;
	};// end_fun

	this.getReportInfoStore = function() {
		if (reportInfoStore == null) {
			reportInfoStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							list_projectIds : null,
							userName : null
						},
						storeId : 'reportInfoStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'wreportID',
						url : '/workbase/console/viewWeekReports/reports.json',
						fields : recordStruts
					});
		}
		return reportInfoStore;
	};

	/**
	 * 获取项目ID列表
	 */
	this.getProjectList = function() {
		var list = '';
		if (dataSet_prolist.length != 0) {
			var arys = new Array();
			for (var i = 0; i < dataSet_prolist.length; i++) {
				arys.push(dataSet_prolist[i][0]);
			}
			list = arys.join('|');
		} else {
			list = 'NOANYPROJECT';
		}
		return list;
	};

	// 项目名称下拉框数据
	this.getProjectListStore = function() {
		if (projectListStore === null) {
			projectListStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dataSet_prolist
					});
		} // end_if
		return projectListStore;
	};

	var detailStruts = [{
				name : 'dreportID',
				type : 'string'
			}, {
				name : 'projectID',
				type : 'string'
			}, {
				name : 'userID',
				type : 'string'
			}, {
				name : 'deptID',
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
			}, {
				name : 'editEnable',
				type : 'int'
			}, {
				name : 'projectName',
				type : 'string'
			}, {
				name : 'userName',
				type : 'string'
			}, {
				name : 'deptName',
				type : 'string'
			}];

	var recordStruts = [{
				name : 'wreportID',
				type : 'string'
			}, {
				name : 'userID',
				type : 'string'
			}, {
				name : 'deptID',
				type : 'string'
			}, {
				name : 'startDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'endDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'reportNo',
				type : 'string'
			}, {
				name : 'recordDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'recordorID',
				type : 'string'
			}, {
				name : 'userName',
				type : 'string'
			}, {
				name : 'workSummary',
				type : 'string'
			}, {
				name : 'nweekPlan',
				type : 'string'
			}, {
				name : 'unsolveProblem',
				type : 'string'
			}, {
				name : 'deptName',
				type : 'string'
			}, {
				name : 'norHours',
				type : 'int'
			}, {
				name : 'addHours',
				type : 'int'
			}, {
				name : 'evlHours',
				type : 'int'
			}, {
				name : 'editEnable',
				type : 'int'
			}];

	/**
	 * {数字与日期之间的转换}
	 * 
	 * @param {}
	 *            v
	 * @param {}
	 *            rec
	 * @return {}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun
	var reportInfoStore = null;
	var projectListStore = null;
	var def_page_size = 20;
	var detailStore = null;

} // end_class
