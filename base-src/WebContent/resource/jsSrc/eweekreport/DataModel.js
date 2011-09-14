/**
 * @class 个人日报管理Bean 建模
 * @author lishijian
 */
function EWeekReportModel() {

	this.getPageSize = function() {
		return def_page_size;
	};

	// 从数据库里加载一条记录
	this.getRecordById = function(/* Ext.form.FormPanel */form,/* string */id) {
		Ext.Ajax.request({
					url : '/workbase/console/eWeekReports/eWeekReportById.json',
					params : {
						wreportID : id
					},
					success : function(resp, opts) {
						// alert(dwr.util.toDescriptiveString(resp, 2));
						var respText = Ext.util.JSON.decode(resp.responseText);
						form.getForm().loadRecord(new Record({
									'wreportID' : respText.data.wreportID,
									'userID' : respText.data.userID,
									'startDate' : respText.data.startDate,
									'endDate' : respText.data.endDate,
									'reportNo' : respText.data.reportNo,
									'workSummary' : respText.data.workSummary,
									'nweekPlan' : respText.data.nweekPlan,
									'unsolveProblem' : respText.data.unsolveProblem
								}));
					},
					failure : function(resp, opts) {
						var respText = Ext.util.JSON.decode(resp.responseText);
						Ext.Msg.alert('错误', respText.error);
					}
				});
	};

	var Record = Ext.data.Record.create([{
				name : 'wreportID',
				type : 'string'
			}, {
				name : 'userID',
				type : 'string'
			}, {
				name : 'startDate',
				type : 'string'
			}, {
				name : 'endDate',
				type : 'string'
			}, {
				name : 'reportNo',
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
			}]);

	// 获取本周全部列表信息
	this.getEWeekReportListStore = function() {
		if (eWeekReportListStore == null) {
			eWeekReportListStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : {
							params : {
								int_start : 0,
								int_limit : def_page_size
							}
						},
						storeId : 'eWeekReportListStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'wreportID',
						url : '/workbase/console/eWeekReports/datalist.json',
						fields : recordStruts
					});
		}
		return eWeekReportListStore;
	};

	// 获取本周全部列表信息
	this.getDailyReportStore = function() {
		if (dailyReportStore == null) {
			dailyReportStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							int_start : 0,
							int_limit : 100
						},
						storeId : 'dailyReportStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'dreportID',
						url : '/workbase/console/edaily/dailyReportall.json',
						fields : dayRecord
					});
		}
		return dailyReportStore;
	};

	/**
	 * {调用AJAX 方法执行 增、删、改的操作}
	 * 
	 * @param {}
	 *            instance
	 * @param {}
	 *            window
	 */
	this.handleAjax = function(/* EWeekReport */instance,/* Ext.Window */window) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		IWeekReportAction.saveOrUpdateEWeekReport(instance, function(respone) {
					if (window !== null) {
						window.hide();
					}
					if (!respone.error) {
						eWeekReportListStore.reload(eWeekReportListStore.lastOptions.params);
					} else {
						Ext.Msg.alert('错误提示', respone.msg);
					}
				});
	}; // end_ajax

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
			}];

	var dayRecord = [{
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
				type : 'int',
				convert : locationToDate
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
				name : 'projectName',
				type : 'string'
			}, {
				name : 'userName',
				type : 'string'
			}, {
				name : 'deptName',
				type : 'string'
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

	var dailyReportStore = null;
	var eWeekReportListStore = null;
	var def_page_size = 20;

	var minDay = null;
	var maxDay = null;
	var today = null;

	this.initDate = function() {
		today = Date.parseDate(server_date, 'Y-m-d');
		var w_index = today.format('N');
		if (w_index === 6) {
			minDay = today;
			maxDay = today.add(Date.DAY, 6);
		} else if (w_index === 0) {
			minDay = today.add(Date.DAY, -1);
			maxDay = today.add(Date.DAY, 5);
		} else {
			minDay = today.add(Date.DAY, 0 - 1 - w_index);
			maxDay = today.add(Date.DAY, 5 - w_index);
		}
	}; // end_fun

	this.getToday = function() {
		return today;
	};

	this.getMaxDay = function() {
		return maxDay;
	};

	this.getMinDay = function() {
		return minDay;
	};
} // end_class
