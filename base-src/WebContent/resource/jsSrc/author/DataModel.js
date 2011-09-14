/**
 * @class 资源管理建模
 * @author haoxiaojie
 */
function DataModel() {

	this.getRecordStruts = function() {
		return recordStruts;
	};
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
						idProperty : 'authorId',
						storeId : 'authorStore',
						fields : recordStruts,
						url : '/workbase/console/author/allRecord.json'
					});
		}
		return recordStore;
	};

	this.handleAjax = function(/* DictCatalog */instance,/* string */type,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		var _is_save = instance.authorId === null ? true : false;
		if (type === 'author') { // 目录操作
			ArsdRemoteAction.saveOrUpdateAuthor(instance, function(respone) {
						if (!respone.error) {
							Ext.Msg.alert('操作提示', '操作成功，需要重新登录方能生效。');
							recordStore.reload(recordStore.lastOptions.params);
						} else {
							if (_is_save) {
								cleanInvaladDataAction(recordStore);
							} else {
								grid.getView().refresh();
							}
							Ext.Msg.alert('Message', respone.msg);
						}
					});
		} else {
			Ext.Msg.alert('错误提示', '非法的参数请求，请联系系统管理员。');
		}
	}; // end_fun

	/**
	 * {清空staort 中的非法数据}
	 * 
	 * @param {}
	 *            store
	 */
	function cleanInvaladDataAction(/* Ext.data.JsonStore */store) {
		store.each(function(/* Record */rec) {
					if (rec.get('authorId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	var recordStruts = [{
				name : 'authorId',
				type : 'string'
			}, {
				name : 'authorSign',
				type : 'string'
			}, {
				name : 'authorDesc',
				type : 'string'
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'authorName',
				type : 'string'
			}];
	var recordStore = null;
	var def_page_size = 20;
}
