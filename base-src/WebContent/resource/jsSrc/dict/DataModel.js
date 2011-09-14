/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	this.getCatalogStruts = function() {
		return recordStruts;
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
						idProperty : 'catalogId',
						storeId : 'catalogStore',
						fields : recordStruts,
						url : '/workbase/console/dict/records.json'
					});
		}
		return catalogStore;
	};

	this.getRecordStore = function() {
		if (recordStore === null) {
			recordStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : catalog_data
					});
		}
		return recordStore;
	};

	this.handleAjax = function(/* DictCatalog */instance,/* string */type,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		// 1:add / 0:update
		var _is_save = instance.catalogId === null ? true : false;
		if (type === 'catalog') { // 目录操作
			ArsdRemoteAction.saveOrUpdateCatalog(instance, function(respone) {
						if (!respone.error) {
							if (_is_save) {
								catalogStore.reload(catalogStore.lastOptions.params);
							} else {
								grid.getStore().commitChanges();
							}
						} else {
							if (_is_save) {
								cleanInvaladDataAction(catalogStore);
							} else {
								grid.getView().refresh();
							}
							Ext.Msg.alert('错误信息', respone.msg);
						}
					});
		} else if (type === 'record') { // 明细
			if (instance.entrySire === null) {
				Ext.Msg.alert('错误提示', '请选择对应的目录类型。');
				return;
			}
			ArsdRemoteAction.saveOrUpdateRecord(instance, function(respone) {
						if (!respone.error) {
							if (_is_save) {
								catalogStore.reload(catalogStore.lastOptions.params);
							} else {
								grid.getStore().commitChanges();
							}
						} else {
							if (_is_save) {
								cleanInvaladDataAction(catalogStore);
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
					if (rec.get('catalogId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	var recordStruts = [{
				name : 'catalogId',
				type : 'string'
			}, {
				name : 'entryName',
				type : 'string'
			}, {
				name : 'entryShutName',
				type : 'string'
			}, {
				name : 'entryDesc',
				type : 'string'
			}, {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'catalogName',
				type : 'string'
			}, {
				name : 'entrySire',
				type : 'string'
			}, {
				name : 'entryOrder',
				type : 'int'
			}];

	var catalogStruts = [{
				name : 'catalogId',
				type : 'string'
			}, {
				name : 'entryName',
				type : 'string'
			}, {
				name : 'entryShutName',
				type : 'string'
			}, {
				name : 'entryDesc',
				type : 'string'
			}, {
				name : 'enabled',
				type : 'int'
			}];
	var catalogStore = null;
	var recordStore = null;
	var def_page_size = 20;
}
