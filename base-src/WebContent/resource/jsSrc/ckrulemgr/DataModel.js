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
	 * @function 清除数据库中无效的数据
	 * @param {}
	 *            store
	 */
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store) {
		cleanInvaladDataAction(store);
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getCatalogStore = function() {
		if (catalogStore === null) {
			catalogStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'ruleId',
						storeId : 'catalogStore',
						fields : recordStruts,
						url : '/workbase/console/duty/list.json'
					});
		}
		return catalogStore;
	};

	this.handleAjax = function(/* CheckingRule.java */instance,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		var isInsert = instance.ruleId === null;
		DutyAjaxAction.handlerCkRule(instance, function(respone) {
					if (!respone.error) {
						if (isInsert) {
							catalogStore.reload(catalogStore.lastOptions.params);
						} else {
							grid.getSelectionModel().getSelected().set('enabled', 0);
							catalogStore.commitChanges();
						} // end_if
					} else {
						cleanInvaladDataAction(catalogStore);
						Ext.Msg.alert('错误信息', respone.msg);
					}
				});
	}; // end_fun

	/**
	 * {清空staort 中的非法数据}
	 * 
	 * @param {}
	 *            store
	 */
	function cleanInvaladDataAction(/* Ext.data.JsonStore */store) {
		store.each(function(/* Record */rec) {
					if (rec.get('ruleId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	this.getRecStruts = function() {
		return recordStruts;
	};

	this.getDeptStore = function() {
		if (deptStore === null) {
			deptStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dept_data
					});
		}
		return deptStore;
	};

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	var recordStruts = ['ruleId', 'ruleName', 'deptId', 'deptName', 'userId', {
				name : 'startDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'endDate',
				type : 'int',
				convert : locationToDate
			}, 'beiginWkTime', 'endWkTime', {
				name : 'entryIngDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'beginWeek',
				type : 'int'
			}, {
				name : 'endWeek',
				type : 'int'
			}, {
				name : 'enabled',
				type : 'int'
			}];

	this.getWeekStore = function() {
		if (weekStore === null) {
			weekStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : weekStore_data
					});
		}
		return weekStore;
	};

	var weekStore_data = [[2, '星期一', 0], [3, '星期二', 0], [4, '星期三', 0], [5, '星期四', 0], [6, '星期五', 0], [7, '星期六', 0],
			[1, '星期日', 0]];
	var weekStore = null;
	var deptStore = null;
	var catalogStore = null;
	var def_page_size = 20;
}
