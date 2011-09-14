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
	var requirementAlterStore = null;
	var efficiencyExecuteStrore = null;
	var clientRelationStore = null;
	var humanResourceStore = null;
	var statsStore = null;

	var milestoneStore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : miltonStatus
			});

	var current_stats = {
		projectId : null,
		weekBegin : null,
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

	this.setSelectProId = function(/* String */_proId) {
		if (current_stats.projectId !== _proId) {
			current_stats.projectId = _proId;
			this.resetStatus();
		}
	}; // end_fun

	this.setSelectedDate = function(/* Date */_date) {
		if (current_stats.weekBegin === null) {
			current_stats.weekBegin = _date;
			current_stats.loadpn = false;
			current_stats.loadpm = false;
		} else {
			// alert(typeof current_stats.weekBegin);
			if (current_stats.weekBegin.format('Y-m-d') !== _date.format('Y-m-d')) {
				current_stats.loadpn = false;
				current_stats.loadpm = false;
				current_stats.weekBegin = _date;
			}
		}
	}; // end_fun
	/**
	 * {当前页面状态记录,设置为不可待加载状态}
	 */
	this.resetStatus = function() {
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
	this.handlerPweeklyReport = function(/* Object */instance,/* function */reserAction) {
		var _problems = new Array();
		var _plans = new Array();
		var _stats = new Array();
		problemStore.each(function(/* Ext.data.Record */rec) {
					_problems.push(rec.data);
				});
		planStore.each(function(/* Ext.data.Record */rec) {
					_plans.push(rec.data);
				});
		instance.startDate = Ext.getCmp('startDate').getValue();
		instance.endDate = instance.startDate.add(Date.DAY, 6);
		instance.problems = _problems;
		instance.memberplans = _plans;
		instance.completePlan = Ext.getCmp('completePlan').getValue();
		instance.nweekPlan = Ext.getCmp('nweekPlan').getValue();
		instance.reportType = 1; // 周报类型为开发类型
		var recds = statsStore.getModifiedRecords();
		for (var i = 0; i < recds.length; i++) {
			_stats.push(recds[i].data);
		}
		instance.stats = _stats;
		// alert(dwr.util.toDescriptiveString(instance, 2));
		ProwrAjaxHandler.handlerPweeklyReport(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						reserAction();
						Ext.Msg.alert('提示', '项目周报新增成功，如有改动，请从“项目修改”菜单进入修改。');
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
		if (current_stats.loadSt) {
			fun(index, layout);
		} else {
			loadStatsList(instance, fun, index, layout);
		}
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
	function loadStatsList(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */layout) {
		// alert(dwr.util.toDescriptiveString(current_stats, 2));
		Ext.Ajax.request({
					url : '/workbase/console/project/stats.json',
					params : {
						projectId : instance.projectId
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
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
	} // end_fun

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
		if (current_stats.loadpn) {
			fun(index, layout);
		} else {
			var end_day = instance.startDate.add(Date.DAY, 6);
			Ext.Ajax.request({
						url : '/workbase/console/pwreport/problemAndplan.json?type=plan',
						params : {
							param_projectId : instance.projectId + '|String',
							param_startDate : instance.startDate.format('Y-m-d') + '|Date|yyyy-MM-dd',
							param_endDate : end_day.format('Y-m-d') + '|Date|yyyy-MM-dd'
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
		} // end_fun
	};

	/**
	 * 
	 */
	this.loadProbleamsAction = function(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */
			layout) {
		if (current_stats.loadpm) {
			fun(index, layout);
		} else {
			loadProbleams(instance, fun, index, layout);
		}
	};

	/**
	 * {加载制定项目ID的周报问题列表
	 * 
	 * @param {}
	 *            projectId
	 */
	function loadProbleams(/* Object */instance,/* Function */fun,/* int */index,/* Ext.layout */layout) {
		Ext.Ajax.request({
					url : '/workbase/console/pwreport/problemAndplan.json?type=problem',
					params : {
						param_projectId : instance.projectId + '|String',
						param_startDate : instance.startDate.format('Y-m-d') + '|Date|yyyy-MM-dd'
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							// alert(dwr.util.toDescriptiveString(_rec, 2));
							if (_rec.addition !== null && typeof _rec.addition.nweekPlan !== 'undefined') {
								Ext.getCmp('bef_weekPlan').setValue(_rec.addition.nweekPlan);
							}
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
