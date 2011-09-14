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
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getDetailStore = function() {
		if (detailStore === null) {
			detailStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							date_startDate : '2000-01-01',
							date_endDate : '2099-01-01'
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'detailId',
						storeId : 'detailStore',
						fields : recordStruts,
						url : '/workbase/console/duty/pckdetail.json'
					});
		}
		return detailStore;
	};

	this.getRecStruts = function() {
		return recordStruts;
	};

	/**
	 * {异步请求提交AJAX}
	 * 
	 * @param {}
	 *            detailId
	 * @param {}
	 *            comments
	 */
	this.handEditComment = function(/* string */detailId,/* String */comments,/* function */fun) {
		DutyAjaxAction.editComments(detailId, comments, function(respone) {
					if (respone.error) {
						Ext.Msg.alert('Message', respone.msg);
					} else {
						fun(comments);
					}
				});
	}; // end_fun

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	var recordStruts = ['detailId', 'userId', 'holidayName', {
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
			}, 'beginCheck', 'endCheck', 'deptName', 'userName', 'comments'];

	var deptIds = new Array();
	var detailStore = null;
	var def_page_size = 20;
}
