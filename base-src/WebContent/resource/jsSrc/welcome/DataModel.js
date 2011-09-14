/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	this.getTodoStruts = function() {
		return todoStruts;
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
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store) {
		cleanInvaladDataAction(store);
	};

	/**
	 * {完成情况上下文}
	 * 
	 * @return {}
	 */
	this.getStatusStore = function() {
		return statusStore;
	};

	this.getStatusOptStore = function() {
		return statusOptStore;
	};

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getTodoStore = function() {
		if (todoStore === null) {
			todoStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						root : 'data',
						totalProperty : 'total',
						idProperty : 'todoId',
						storeId : 'todoStore',
						fields : todoStruts,
						url : '/workbase/console/user/todoList.json'
					});
		}
		return todoStore;
	};

	this.handleAjax = function(/* DictCatalog */instance,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		UpmrAjaxAction.saveOrUpdateTodo(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						todoStore.reload();
					} else {
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
					if (rec.get('todoId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	var todoStruts = ['todoId', 'userId', {
				name : 'entryDate',
				type : 'int',
				convert : locationToDate
			}, {
				name : 'deadDate',
				type : 'int',
				convert : locationToDate
			}, 'todoDes', {
				name : 'status',
				type : 'int'
			}, {
				name : 'overTag',
				type : 'int'
			}, {
				name : 'todoType',
				type : 'int'
			}];

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun
	var todoStore = null;
	var def_page_size = 20;
	var optStruts = [{
				name : 'regValue',
				type : 'int'
			}, {
				name : 'displayValue',
				type : 'string'
			}];

	var statusStore = new Ext.data.ArrayStore({
				fields : optStruts,
				data : [[0, '进行中'], [1, '完成']]
			});
	var statusOptStore = new Ext.data.ArrayStore({
				fields : optStruts,
				data : [[0, '进行中'], [1, '完成'], [-1, '全部状态']]
			});

} // end_class
