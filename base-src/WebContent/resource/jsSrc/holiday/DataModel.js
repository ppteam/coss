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
	this.getHolidayStore = function() {
		if (holidaysStore === null) {
			var date = new Date();
			holidaysStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							int_year : date.format('Y')
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'holidayId',
						storeId : 'holidaysStore',
						fields : recordStruts,
						url : '/workbase/console/duty/holiday.json'
					});
		}
		return holidaysStore;
	};

	this.handleAjax = function(/* CheckingRule.java */instance,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		DutyAjaxAction.handlerHoldayRule(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						// Ext.Msg.alert('操作提示', '假期编辑成功，系统会在假期之内忽略员工签到。');
						if (instance.holidayId === null) {
							holidaysStore.reload(holidaysStore.lastOptions.params);
						} else {
							holidaysStore.commitChanges();
						}
					} else {
						cleanInvaladDataAction(holidaysStore);
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
					if (rec.get('holidayId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	this.getRecStruts = function() {
		return recordStruts;
	};

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	this.getDayTypeStore = function() {
		if (dayTypeStore === null) {
			dayTypeStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dayTypeDate
					});
		}
		return dayTypeStore;
	}; // end_fun

	var recordStruts = ['holidayId', 'userId', 'holidayName', {
				name : 'startDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'endDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'entryDate',
				type : 'int',
				convert : locationToDate
			}, 'additionInfo', {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'dayType',
				type : 'int'
			}];

	var holidaysStore = null;
	var dayTypeStore = null;
	var def_page_size = 20;
	var dayTypeDate = [[0,'假期',0], [1,'上班',0]];
}
