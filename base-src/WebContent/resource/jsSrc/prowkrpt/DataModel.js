/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {
	var problemStore = null;
	var projectStore = null;
	var problemStatsStore = null;
	var planStore = null;
	var postTypeStore = null;

	var current_stats = {
		projectId : null,
		weekBegin : null,
		checked : false,
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
		current_stats.checked = false;
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

	this.getProjectStore = function() {
		if (projectStore === null) {
			projectStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : project_list
					});
		}
		return projectStore;
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
	this.handlerPweeklyReport = function(/* Object */instance) {
		var _problems = new Array();
		var _plans = new Array();
		problemStore.each(function(/* Ext.data.Record */rec) {
					_problems.push(rec.data);
				});
		planStore.each(function(/* Ext.data.Record */rec) {
					_plans.push(rec.data);
				});
		instance.weekBegin = current_stats.weekBegin;
		instance.problems = _problems;
		instance.memberplans = _plans;
		instance.reportType = 0; // 设置为测试项目周报类型
		// alert(dwr.util.toDescriptiveString(instance, 3));
		ProwrAjaxHandler.handlerPweeklyReport(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						Ext.Msg.alert('提示', '项目周报新增成功。');
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
	this.checkProjectWPInfo = function(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */layout) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/check.json',
					params : {
						param_projectId : instance.projectId + '|String',
						param_startDate : instance.startDate.format('Y-m-d') + '|Date|yyyy-MM-dd',
						param_reportNo : instance.reportNo + '|String'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec, 2));
							if (_rec.data === 0) {
								current_stats.checked = true;
								if (current_stats.loadpm) {
									fun(index, layout);
								} else {
									loadProbleams(instance, fun, index, layout);
								}
							} else {
								Ext.Msg.alert('提示', '该项目周报已经填写或者周报编号有误，请检查！');
							}
						} else {
							Ext.Msg.alert('提示', '与后台数据通信异常，请稍后再试！');
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
	this.loadPlansAction = function(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */
			layout) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/problemAndplan.json?type=plan',
					params : {
						param_projectId : instance.projectId + '|String',
						param_startDate : instance.startDate.format('Y-m-d') + '|Date|yyyy-MM-dd',
						param_weekBegin : current_stats.weekBegin + '|Int'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec, 2));
							current_stats.loadpn = true;
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
	this.loadProbleamsAction = function(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */
			layout) {
		loadProbleams(instance, fun, index, layout);
	};
	/**
	 * {加载制定项目ID的周报问题列表
	 * 
	 * @param {}
	 *            projectId
	 */
	function loadProbleams(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */layout) {
		// alert(dwr.util.toDescriptiveString(current_stats, 2));
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/problemAndplan.json?type=problem',
					params : {
						param_projectId : instance.projectId + '|String'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec.addition,
							// 2));
							if (_rec.data !== null) {
								problemStore.loadData(_rec);
							}
							current_stats.loadpm = true;
							fun(index, layout);
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
