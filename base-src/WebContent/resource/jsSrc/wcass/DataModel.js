/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	var months = [[1, '一月'], [2, '二月'], [3, '三月'], [4, '四月'], [5, '五月'], [6, '六月'], [7, '七月'], [8, '八月'], [9, '九月'],
			[10, '十月'], [11, '十一月'], [12, '十二月']];

	var years = [[2010, '2010'], [2011, '2011'], [2012, '2012'], [2013, '2013'], [2014, '2014'], [2015, '2015'],
			[2016, '2016'], [2017, '2017'], [2018, '2018'], [2019, '2019']];

	this.getMonthList = function() {
		if (monthStore === null) {
			monthStore = new Ext.data.ArrayStore({
						fields : [{
									name : 'regValue',
									type : 'int'
								}, {
									name : 'displayValue',
									type : 'string'
								}],
						data : months
					});
		}
		return monthStore;
	}; // end_fun

	this.getYearStore = function() {
		if (yearStore === null) {
			yearStore = new Ext.data.ArrayStore({
						fields : [{
									name : 'regValue',
									type : 'int'
								}, {
									name : 'displayValue',
									type : 'string'
								}],
						data : years
					});
		}
		return yearStore;
	}; // end_fun

	var monthStore = null;

	var yearStore = null;

	this.getToday = function() {
		return Date.parseDate(server_date, 'Y-m-d');
	};
} // end_fun

