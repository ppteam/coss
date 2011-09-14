/**
 * @class 个人日报管理Bean 建模
 * @author lishijian
 */
function DailyReportModel() {

	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	// 新建一条记录
	var Record = Ext.data.Record.create([{
				name : 'projectID',
				type : 'string'
			}, {
				name : 'userID',
				type : 'string'
			}, {
				name : 'reportDate',
				type : 'date'
			}, {
				name : 'workHours',
				type : 'float'
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
				name : 'workContent',
				type : 'string'
			}, {
				name : 'resultsShow',
				type : 'string'
			}, {
				name : 'repComment',
				type : 'string'
			}]);

	// 获取本周全部列表信息
	this.getDailyReportStore = function() {
		if (dailyReportStore == null) {
			dailyReportStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : {
							params : {
								int_start : 0,
								int_limit : def_page_size,
								projectId : null
							}
						},
						storeId : 'dailyReportStore',
						root : 'data',
						totalProperty : 'total',
						idProperty : 'dreportID',
						url : '/workbase/console/edaily/dailyReportall.json',
						fields : createDailyReportRecord
					});
		}
		return dailyReportStore;
	};

	// 项目活动下拉框数据
	this.getProjActivityStore = function() {// projActivity
		if (projActivityStore === null) {
			projActivityStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : projActivity
					});
		}
		return projActivityStore;
	};

	// 项目子活动下拉框数据
	this.getSubProjActivityStore = function() {// subProjActivityStore
		if (subProjActivityStore === null) {
			subProjActivityStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : subProjActivity
					});
		}
		return subProjActivityStore;
	};

	// 工时类别下拉框数据
	this.getManHourTypeStore = function() {
		if (manHourTypeStore === null) {
			manHourTypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : manHourType
					});
		}
		return manHourTypeStore;
	};

	// 请假类别
	this.getBegoffTypeStore = function() {
		if (begoffTypeStore === null) {
			begoffTypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : begoffType
					});
		}
		return begoffTypeStore;
	};

	this.getRepTypeStore = function() {
		if (repTypeStore === null) {
			repTypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : [[0, '网银日报'], [1, '主机日报']]
					});
		}
		return repTypeStore;
	};

	// 工作状态下拉框数据
	this.getDailyReportWorkStatusStore = function() {// dailyReportWorkStatus
		if (dailyReportWorkStatusStore === null) {
			dailyReportWorkStatusStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dailyReportWorkStatus
					});
		}
		return dailyReportWorkStatusStore;
	};

	// 个人查看填写时对应的项目名称下拉框数据
	this.getDailyReportProNameSelfStore = function() {// dailyReportProNameSelfStore
		if (dailyReportProNameSelfStore === null) {
			dailyReportProNameSelfStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : project_info_data
					});
		}
		return dailyReportProNameSelfStore;
	};

	/**
	 * {批量请假处理}
	 */
	this.handleBatchAjax = function(/* Array */args,/* Ext.Window */win) {
		// alert(dwr.util.toDescriptiveString(args, 2));
		IRemoteDailyReportAction.batEditDailyReport(args, function(respone) {
					win.hide();
					if (!respone.error) {
						dailyReportStore.reload(dailyReportStore.lastOptions.params);
					} else {
						Ext.Msg.alert('错误提示', respone.msg);
					}
				});
	}; // end_fun

	this.handleAjax = function(/* DailyReport */instance,/* string */
			type,/* Window */window) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		if (type === "add") {
			IRemoteDailyReportAction.saveOrUpdateDailyReport(instance, function(respone) {
						window.hide();
						if (!respone.error) {
							dailyReportStore.reload(dailyReportStore.lastOptions.params);
						} else {
							Ext.Msg.alert('错误提示', respone.msg);
						}
					});
		} else if (type === "modify") {
			IRemoteDailyReportAction.saveOrUpdateDailyReport(instance, function(respone) {
						window.hide();
						if (!respone.error) {
							dailyReportStore.reload(dailyReportStore.lastOptions.params);
						} else {
							Ext.Msg.alert('错误提示', respone.msg);
						}
					});
		} else if (type === 'remove') {
			IRemoteDailyReportAction.saveOrUpdateDailyReport(instance, function(respone) {
						if (!respone.error) {
							dailyReportStore.reload(dailyReportStore.lastOptions.params);
						} else {
							Ext.Msg.alert('错误提示', respone.msg);
						}
					});
		}
	}; // end_fun

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
			}, 'begType', {
				name : 'reportType',
				type : 'int'
			}, {
				name : 'workSchde',
				type : 'float'
			}, 'delayAffect', 'delaySolve'];

	var dailyReportStore = null;// 查询本周所有列表
	var def_page_size = 20; // 默认的分页数量
	var projActivityStore = null;// 项目活动
	var subProjActivityStore = null;// 项目子活动
	var manHourTypeStore = null;// 工时类别
	var dailyReportWorkStatusStore = null;// 工作状态
	var dailyReportProNameSelfStore = null;// 个人日报填写时对应的项目名称
	var begoffTypeStore = null; // 假期类别
	var repTypeStore = null;
	var minDay = null;

	var maxDay = null;

	this.getMaxDay = function() {
		if (maxDay === null) {
			maxDay = Date.parseDate(server_date, 'Y-m-d');
		}
		return maxDay;
	};

	this.getMinDay = function() {
		if (minDay === null) {
			var startDay = this.getMaxDay();
			var w_index = startDay.format('N');
			if (w_index === 0) {
				w_index = -1;
			} else if (w_index === 0) {
				w_index = 0;
			} else {
				w_index = 0 - (w_index + 1);
			} // end_if
			minDay = startDay.add(Date.DAY, w_index);
		} // end_if
		return minDay;
	};

} // end_class
