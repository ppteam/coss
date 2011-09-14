/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	var weekStore_data = [['2', '星期一', 0], ['3', '星期二', 0], ['4', '星期三', 0], ['5', '星期四', 0], ['6', '星期五', 0],
			['7', '星期六', 0], ['1', '星期日', 0]];

	this.getWeekStore = function() {
		if (weekStore === null) {
			weekStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : weekStore_data
					});
		}
		return weekStore;
	};

	this.getProjectStatusStore = function() {
		return projectStatusStore;
	};

	this.getStatsStruts = function() {
		return milestoneStruts;
	};

	this.getRecordStruts = function() {
		return recordStruts;
	};

	this.creatMemberRec = function() {
		return new memberRec();
	};

	this.getMilestoneStore = function() {
		return milestoneStore;
	};
	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	/**
	 * @function 清除数据库中无效的数据
	 * @param {}
	 *            store
	 */
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store,/* String */
			idval) {
		cleanInvaladDataAction(store, idval);
	};

	this.getRoleTypeStore = function() {
		return roleTypeStore;
	};

	this.getUserStore = function() {
		return userStore;
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getRecordStore = function() {
		if (recordStore === null) {
			recordStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'projectId',
						storeId : 'recordStore',
						fields : recordStruts,
						url : '/workbase/console/project/list.json'
					});
		}
		return recordStore;
	};

	/**
	 * 
	 */
	this.getStatsStore = function() {
		if (statsStore === null) {
			statsStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						root : 'data',
						idProperty : 'statsId',
						storeId : 'statsStore',
						fields : milestoneStruts,
						url : '/workbase/console/project/stats.json'
					});
		}
		return statsStore;
	};

	this.getMemberStore = function() {
		if (memberStore === null) {
			memberStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						root : 'data',
						idProperty : 'statsId',
						storeId : 'memberStore',
						fields : memberStruts,
						url : '/workbase/console/project/member.json'
					});
		}
		return memberStore;
	};
	this.getDeptStrore = function() {
		if (deptStrore === null) {
			deptStrore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dept_data
					});
		}
		return deptStrore;
	};
	/**
	 * 
	 * @return {项目状态}
	 */
	this.getProStatusStore = function() {
		if (proStatusStore === null) {
			proStatusStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : projectStatus
					});
		}
		return proStatusStore;
	};

	this.handleAjaxMember = function(/* Object */instance, callfun) {
		UpmrAjaxAction.editProjectMember(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					callfun();
					if (!respone.error) {
						memberStore.removeAll();
						memberStore.reload(memberStore.lastOptions.params);
					} else {
						Ext.Msg.alert('Message', respone.msg);
					}
				});
	};

	this.handleAjaxStats = function(/* Object */instance) {
		UpmrAjaxAction.editProjectMilestone(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						statsStore.removeAll();
						statsStore.reload(statsStore.lastOptions.params);
					} else {
						cleanInvaladDataAction(recordStore, 'statsId');
						Ext.Msg.alert('Message', respone.msg);
					}
				});
	};

	this.loadRoleAction = function(/* String */userIdVal) {
		Ext.Ajax.request({
					url : '/workbase/console/user/loadRoles.json?type=sign',
					params : {
						userId : userIdVal
					},
					callback : function(/* Options */opts,/* boolean */
							success,/* response */
							reps) {
						if (success) {
							var _rec = Ext.util.JSON.decode(reps.responseText);
							alert(dwr.util.toDescriptiveString(_rec, 2));
							roleTypeStore.removeAll();
							if (_rec.data !== null) {
								var temp = new Array();
								for (var i = 0; i < _rec.data.length; i++) {
									for (var j = 0; j < roleType.length; j++) {
										if (_rec.data[i] === roleType[j][0]) {
											temp.push(roleType[j]);
											break;
										}
									}
								} // end_for
								roleTypeStore.loadData(temp);
							}
						} else {
							Ext.Msg.alert('提示', '加载周报数据异常，请稍后再试！');
						}
					}
				});
	};

	this.handleAjax = function(/* DictCatalog */instance,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		var _is_save = instance.projectId === null ? true : false;
		UpmrAjaxAction.editProjectInfo(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						recordStore.reload(recordStore.lastOptions.params);
					} else {
						if (_is_save) {
							cleanInvaladDataAction(recordStore, 'projectId');
						} else {
							grid.getView().refresh();
						}
						Ext.Msg.alert('Message', respone.msg);
					}
				});
	}; // end_fun

	/**
	 * {清空staort 中的非法数据}
	 * 
	 * @param {}
	 *            store
	 */
	function cleanInvaladDataAction(/* Ext.data.JsonStore */store,/* string */
			idVal) {
		store.each(function(/* Record */rec) {
					if (rec.get(idVal) === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	var memberStruts = ['statsId', 'projectId', 'userId', 'roleType', 'joinDate', 'leaveDate', {
				name : 'curtStats',
				type : 'int'
			}, 'userName'];

	/**
	 * @class js对象建模
	 */
	function memberRec() {
		this.statsId = null;
		this.projectId = null;
		this.userId = null;
		this.roleType = null;
		this.curtStats = null;
	};

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
			}, 'milestomeStats', 'planVersion'];
	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	/**
	 * {数据格式转换}
	 */
	function coverWorkHours(val, rec) {
		return (val / 100).toFixed(2);
	}// end_fun

	var recordStruts = ['projectId', 'projectNo', 'projectName', 'projectStats', 'psmName', {
				name : 'needReport',
				type : 'int'
			}, {
				name : 'hoursRule',
				type : 'int'
			}, 'deptId', 'projectDes', 'proAgent', 'proOper', {
				name : 'weekBegin',
				type : 'int'
			}, {
				name : 'workDays',
				type : 'float',
				convert : coverWorkHours
			}, {
				name : 'happenDays',
				type : 'int'
			}];
	// roleType
	var roleTypeStore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : roleType
			});
	var milestoneStore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : milestone
			});

	var projectStatusStore = new Ext.data.ArrayStore({
				fields : optionStruts,
				data : projectStatus
			});

	var memberStore = null;

	var userStore = new Ext.data.JsonStore({
				autoDestroy : true,
				autoLoad : true,
				root : 'data',
				idProperty : 'regValue',
				storeId : 'userStore',
				fields : optionStruts,
				url : '/workbase/console/user/queryForOpts.json'
			});

	var weekStore = null;
	var statsStore = null;
	var recordStore = null;
	var proStatusStore = null;
	var deptStrore = null;
	var def_page_size = 25;
}
