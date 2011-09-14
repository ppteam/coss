/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {
	var problemStore = null;
	var reportStore = null;
	var problemStatsStore = null;
	var planStore = null;
	var postTypeStore = null;

	var current_stats = {
		reportId : null,
		projectId : null,
		loadpm : false,
		loadpn : false
	};

	/**
	 * {当前数据状态指针}
	 * 
	 * @return {current_stats}
	 */
	this.getCurrentStats = function() {
		return current_stats;
	};

	/**
	 * {当前页面状态记录,设置为不可待加载状态}
	 */
	this.resetStatus = function() {
		current_stats.loadpm = false;
		current_stats.loadpn = false;
	}; // end_class

	this.getProplemStore = function() {
		if (problemStore === null) {
			problemStore = new Ext.data.JsonStore({
						autoDestroy : true,
						storeId : 'problemStore',
						root : 'data',
						idProperty : 'userName',
						fields : problemStruts
					});
		}
		return problemStore;
	};

	this.getPlanStore = function() {
		if (planStore === null) {
			planStore = new Ext.data.JsonStore({
						autoDestroy : true,
						storeId : 'getPlanStore',
						root : 'data',
						idProperty : 'dataId',
						fields : planStruts
					});
		}
		return planStore;
	};

	this.getProblemStatsStore = function() {
		if (problemStatsStore === null) {
			problemStatsStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : problemstates
					});
		}
		return problemStatsStore;
	};

	this.getPostTypeStore = function() {
		if (postTypeStore === null) {
			postTypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : posttype
					});
		}
		return postTypeStore;
	};

	this.getReportStore = function() {
		if (reportStore === null) {
			reportStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : pwreport_list
					});
		}
		return reportStore;
	};

	this.creatProblemRec = function() {
		return problemStruts;
	};

	/**
	 * {保存或修改周报数据}
	 * 
	 * @param {}
	 *            object
	 */
	this.handlerPweeklyReport = function(/* Object */instance,/* Layout */layout,/* Form */fm) {
		var _problems = new Array();
		var _plans = new Array();
		problemStore.each(function(/* Ext.data.Record */rec) {
					_problems.push(rec.data);
				});
		planStore.each(function(/* Ext.data.Record */rec) {
					_plans.push(rec.data);
				});
		instance.reportId = current_stats.reportId;
		instance.problems = _problems;
		instance.memberplans = _plans;
		// alert(dwr.util.toDescriptiveString(instance, 3));
		ProwrAjaxHandler.handlerPweeklyReport(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						Ext.Msg.alert('提示', '项目周报修改成功！');
						layout.setActiveItem(0);
						fm.getForm().reset();
					} else {
						Ext.Msg.alert('错误提示', respone.msg);
					}
				});
	};

	/**
	 * {加载制定信息的项目周报信息}
	 * 
	 * @param {}
	 *            instance
	 * @param {}
	 *            panle
	 */
	this.loadReportCtx = function(/* string */reportIdVal,/* Ext.FormPanel */form) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/context.json',
					params : {
						reportId : reportIdVal
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							form.getForm().loadRecord(_rec);
							Ext.getCmp('startDate').setValue(new Date(_rec.data.startDate));
							Ext.getCmp('endDate').setValue(new Date(_rec.data.endDate));
							Ext.getCmp('card-next').setDisabled(false);
							current_stats.reportId = reportIdVal;
							current_stats.projectId = _rec.data.projectId;
							current_stats.loadpm = false;
							current_stats.loadpn = false;
						} else {
							Ext.Msg.alert('提示', '加载周报数据异常，请稍后再试！');
						}
					}
				});
	};

	/**
	 * {加载周报成员工作量统计数据}
	 * 
	 * @param {}
	 *            instance
	 * @param {}
	 *            fun
	 * @param {}
	 *            index
	 * @param {}
	 *            layout
	 */
	this.loadPlansAction = function(/* Function */fun,/* int */index,/* Ext.layout */
			layout) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/addinfo.json?type=plan',
					params : {
						param_reportId : current_stats.reportId + '|String',
						param_projectId : current_stats.projectId + '|String',
						param_startDate:Ext.getCmp('startDate').getValue().format('Y-m-d') +'|Date',
						param_endDate:Ext.getCmp('endDate').getValue().format('Y-m-d') +'|Date'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec, 2));
							current_stats.loadpn = true;
							planStore.removeAll();
							if (_rec.data !== null) {
								planStore.loadData(_rec);
							}

							fun(index, layout);
						} else {
							Ext.Msg.alert('提示', '加载历史数据失败，请稍后再试！');
						}
					}
				});
	};

	/**
	 * 
	 */
	this.loadProbleamsAction = function(/* int */index,/* Ext.layout */lay,/* Function */fun) {
		loadProbleams(index, lay, fun);
	};
	/**
	 * {加载制定项目ID的周报问题列表
	 * 
	 * @param {}
	 *            projectId
	 */
	function loadProbleams(/* int */index,/* Ext.layout */lay,/* Function */fun) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/addinfo.json?type=problem',
					params : {
						param_reportId : current_stats.reportId + '|String',
						param_projectId : current_stats.projectId + '|String'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec, 2));
							problemStore.removeAll();
							if (_rec.data !== null) {
								problemStore.loadData(_rec);
							}
							current_stats.loadpm = true;
							fun(index, lay);
						} else {
							Ext.Msg.alert('提示', '加载历史数据失败，请稍后再试！');
						}
					}
				});
	} // end_fun

	var problemStruts = ['problemId', 'reportId', {
				name : 'discoverDate',
				type : 'int',
				convert : locationToDate
			}, 'userName', 'problemDesc', 'resolveWay', 'problemStats'];

	var planStruts = ['dataId', 'userId', 'userName', {
				name : 'normalTime',
				type : 'int'
			}, {
				name : 'overTime',
				type : 'int'
			}, 'weeklySummary', 'branchId', 'branchName'];

	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun
} // end_class
