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
	 * @note 获取本月最后一天的日期
	 */
	this.getLastDateOfMonth = function() {
		return today.getLastDateOfMonth();
	};

	/**
	 * @note 获取本月第一天的日期
	 */
	this.getFirstDateOfMonth = function() {
		return today.getFirstDateOfMonth();
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getStatsStore = function() {
		if (statsStore === null) {
			statsStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							date_startDate : this.getFirstDateOfMonth().format('Y-m-d'),
							date_endDate : this.getLastDateOfMonth().format('Y-m-d')
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'userId',
						storeId : 'statsStore',
						fields : recordStruts,
						url : '/workbase/console/duty/checkStats.json'
					});
		}
		return statsStore;
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getDetailStore = function() {
		if (detailStore === null) {
			detailStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : false,
						baseParams : {
							userId : '',
							date_startDate : '2000-01-01',
							date_endDate : '2099-01-01'
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'detailId',
						storeId : 'detailStore',
						fields : detailStruts,
						url : '/workbase/console/duty/smpdetail.json'
					});
		}
		return detailStore;
	};

	/**
	 * {初始化部门选择列表UI ckbox}
	 * 
	 */
	this.initDeptGroup = function() {
		if (dept_data !== null && dept_data.length !== 0) {
			var ckGroup = Ext.getCmp('deptGroup');
			var ck_groups = {
				xtype : 'checkboxgroup',
				columns : 2,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dept_data.length; i++) {
				deptIds.push(dept_data[i][0]);
				ck_groups.items[j] = new Ext.form.Checkbox({
							boxLabel : dept_data[i][1],
							name : dept_data[i][0],
							id : dept_data[i][0]
						});
				j++;
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if
	};

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

	var detailStruts = ['detailId', 'userId', 'holidayName', {
				name : 'dayStats',
				type : 'int'
			}, {
				name : 'beginStats',
				type : 'int'
			}, {
				name : 'endStats',
				type : 'int'
			}, {
				name : 'checkDate',
				type : 'int',
				convert : locationToDate
			}, 'beginCheck', 'endCheck', 'deptName', 'userName'];

	var recordStruts = ['userId', 'userName', 'deptName', {
				name : 'bgGod',
				type : 'int'
			}, {
				name : 'bgExp',
				type : 'int'
			}, {
				name : 'bgErr',
				type : 'int'
			}, {
				name : 'edGod',
				type : 'int'
			}, {
				name : 'edExp',
				type : 'int'
			}, {
				name : 'edErr',
				type : 'int'
			}];

	var deptIds = new Array();
	var today = new Date();
	var statsStore = null;
	var detailStore = null;
	var def_page_size = 20;
}
