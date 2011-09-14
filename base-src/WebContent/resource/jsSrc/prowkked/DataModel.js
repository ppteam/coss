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
	var requirementAlterStore = null;
	var efficiencyExecuteStrore = null;
	var clientRelationStore = null;
	var humanResourceStore = null;
	var statsStore = null;
	var _projectId = null;
	var milestoneStore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : miltonStatus
			});

	var current_stats = {
		projectId : null,
		weekBegin : null,
		checked : false,
		loadSt : false,
		loadpm : false,
		loadpn : false
	};

	this.getMilestoneStore = function() {
		return milestoneStore;
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
		current_stats.loadSt = false;
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

	this.getStatsStore = function() {
		if (statsStore === null) {
			statsStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						root : 'data',
						idProperty : 'statsId',
						storeId : 'statsStore',
						fields : milestoneStruts
					});
		}
		return statsStore;
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

	this.getRequirementAlterStore = function() {
		if (requirementAlterStore === null) {
			requirementAlterStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : requirementAlter
					});
		}
		return requirementAlterStore;
	};

	this.getEfficiencyExecuteStrore = function() {
		if (efficiencyExecuteStrore === null) {
			efficiencyExecuteStrore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : efficiencyExecute
					});
		}
		return efficiencyExecuteStrore;
	};

	// clientRelationStore
	this.geClientRelationStore = function() {
		if (clientRelationStore === null) {
			clientRelationStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : clientRelation
					});
		}
		return clientRelationStore;
	};
	// humanResourceStore
	this.geHumanResourceStore = function() {
		if (humanResourceStore === null) {
			humanResourceStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : humanResource
					});
		}
		return humanResourceStore;
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
	this.handlerPweeklyReport = function(/* Object */instance,/* function */resetAction) {
		var _problems = new Array();
		var _plans = new Array();
		var _stats = new Array();
		problemStore.each(function(/* Ext.data.Record */rec) {
					_problems.push(rec.data);
				});
		planStore.each(function(/* Ext.data.Record */rec) {
					_plans.push(rec.data);
				});
		instance.weekBegin = current_stats.weekBegin;
		instance.problems = _problems;
		instance.memberplans = _plans;
		instance.reportId = current_stats.reportId;
		instance.completePlan = Ext.getCmp('completePlan').getValue();
		instance.nweekPlan = Ext.getCmp('nweekPlan').getValue();
		instance.reportType = 1; // 设置为测试项目周报类型
		var recds = statsStore.getModifiedRecords();
		// alert(typeof recds);
		for (var i = 0; i < recds.length; i++) {
			_stats.push(recds[i].data);
		}
		instance.stats = _stats;
		// alert(dwr.util.toDescriptiveString(instance.stats, 3));
		ProwrAjaxHandler.handlerPweeklyReport(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						resetAction();
						Ext.Msg.alert('提示', '项目周报新修改成功。');
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
							Ext.getCmp('completePlan').setValue(_rec.data.completePlan);
							Ext.getCmp('nweekPlan').setValue(_rec.data.nweekPlan);
							Ext.getCmp('card-next').setDisabled(false);
							current_stats.reportId = reportIdVal;
							current_stats.projectId = _rec.data.projectId;
							current_stats.loadSt = false;
							current_stats.loadpm = false;
							current_stats.loadpn = false;
						} else {
							Ext.Msg.alert('提示', '加载周报数据异常，请稍后再试！');
						}
					}
				});
	};

	/**
	 * {加载制定项目ID的项目里程碑 列表}
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
	this.loadStatsList = function(/* Function */fun,/* int */index,/* Ext.layout */layout) {
		// alert(dwr.util.toDescriptiveString(current_stats, 2));
		if (!current_stats.loadSt) {
			Ext.Ajax.request({
						url : '/workbase/console/project/stats.json',
						params : {
							projectId : current_stats.projectId
						},
						callback : function(/* Options */opts,/* boolean */
								success,/* response */
								reps) {
							if (success) {
								var _rec = Ext.util.JSON.decode(reps.responseText);
								// alert(dwr.util.toDescriptiveString(_rec, 2));
								if (_rec.data !== null) {
									statsStore.loadData(_rec);
								}
								current_stats.loadSt = true;
								fun(index, layout);
							} else {
								Ext.Msg.alert('提示', '加载历史数据失败，请稍后再试！');
							}
						}
					});
		} else {
			fun(index, layout);
		}
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
						param_projectId : current_stats.projectId + '|String'
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
	this.loadProbleamsAction = function(/* Function */fun,/* int */index,/* Ext.layout */
			layout) {
		if (!current_stats.loadpm) {
			loadProbleams(fun, index, layout);
		} else {
			fun(index, layout);
		}
	};

	/**
	 * {加载制定项目ID的周报问题列表
	 * 
	 * @param {}
	 *            projectId
	 */
	function loadProbleams(/* Function */fun,/* int */index,/* Ext.layout */layout) {
		// alert(dwr.util.toDescriptiveString(current_stats, 2));
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
							fun(index, layout);
						} else {
							Ext.Msg.alert('提示', '加载历史数据失败，请稍后再试！');
						}
					}
				});
	} // end_fun

	var milestoneStruts = ['statsId', 'projectId', 'milestoneName', {
				name : 'planStart',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'planEnd',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'realityStart',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'realityEnd',
				type : 'int',
				convert : locationToDate
			}, 'milestomeStats', 'planVersion', 'milestoneDes'];

	var problemStruts = ['problemId', 'reportId', {
				name : 'discoverDate',
				type : 'int',
				convert : locationToDate
			}, 'userName', 'problemDesc', 'resolveWay', 'problemStats', 'resposibler', {
				name : 'solveDate',
				type : 'int',
				convert : locationToDate
			}];

	var planStruts = ['dataId', 'userId', 'userName', {
				name : 'normalTime',
				type : 'int'
			}, {
				name : 'overTime',
				type : 'int'
			}, 'weeklySummary', 'weeklyPlan', 'branchId', 'branchName'];

	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun
} // end_class
