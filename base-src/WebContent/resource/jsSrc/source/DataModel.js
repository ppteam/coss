/**
 * @class 资源管理建模
 * @author haoxiaojie
 */
function DataModel() {

	this.getRecordStruts = function() {
		return recordStruts;
	};

	/**
	 * @function 清除数据库中无效的数据
	 * @param {}
	 *            store
	 */
	this.cleanInvaladData = function(/* Ext.data.JsonStore */store) {
		cleanInvaladDataAction(store);
	};

	this.getMenuStore = function() {
		return menuStore;
	};

	/**
	 * 
	 * @return {}
	 */
	this.getAuthorStore = function() {
		if (authorStore === null) {
			authorStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : authors
					});
		}
		return authorStore;
	}; // end_fun

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getRecordStore = function() {
		if (recordStore === null) {
			menuStore = new Ext.data.ArrayStore({
						autoDestroy : true,
						fields : [{
									name : 'srcId',
									type : 'string'
								}, {
									name : 'srcName',
									type : 'string'
								}]
					});
			recordStore = new Ext.data.GroupingStore({
						reader : myReader,
						autoDestroy : true,
						autoLoad : true,
						groupField : 'srcSort',
						url : '/workbase/console/source/allsource.json'
					});
			recordStore.on('load', function(
							/* Ext.data.GroupingStore */storce, /* Ext.data.Record[] */
							records,/* Object */options) {
						menuStore.removeAll();
						var menus = new Array();
						storce.each(function(/* Ext.data.Record */rec) {
									if (rec.get('srcType') === 0) {
										var item = [rec.get('srcId'), rec.get('srcName')];
										menus.push(item);
									}
								});
						menuStore.loadData(menus);
					});
		}
		return recordStore;
	};

	/**
	 * {异步删除制定资源}
	 */
	this.removeSource = function(/* String */sourceId) {
		var instance = {
			srcId : sourceId
		};
		ArsdRemoteAction.removeSource(instance, function(respone) {
					// alert(dwr.util.toDescriptiveString(respone, 2));
					if (!respone.error) {
						Ext.Msg.alert('提示', '删除资源项操作成功，需要重新登录才可以生效。');
						recordStore.reload(recordStore.lastOptions.params);
					} else {
						Ext.Msg.alert('提示', respone.msg);
					}
				});
	};

	this.handleAjax = function(/* DictCatalog */instance,/* string */type,/* Ext.grid.GridPanel */
			grid) {
		// alert(dwr.util.toDescriptiveString(instance, 2));
		var _is_save = instance.srcId === null ? true : false;
		if (type === 'source') { // 目录操作
			ArsdRemoteAction.saveOrUpdatSource(instance, function(respone) {
						if (!respone.error) {
							if (_is_save) {
								Ext.Msg.alert('提示', '新增资源项操作成功，需要重新登录才可以生效。');
								recordStore.reload(recordStore.lastOptions.params);
							} else {
								grid.getStore().commitChanges();
							}
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
			Ext.Msg.alert('错误提示', '异常操作，请联系系统管理员！');
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
					if (rec.get('srcId') === null) {
						store.remove(rec);
					}
					return true;
				});
	} // end_fun

	var recordStruts = ['srcId', 'srcName', 'srcDesc', {
				name : 'srcOrder',
				type : 'int'
			}, 'srcSort', 'srcUrl', 'authorId', {
				name : 'enabled',
				type : 'int'
			}, {
				name : 'srcType',
				type : 'int'
			}, 'stylecss'];
	var myReader = new Ext.data.JsonReader({
				idProperty : 'srcId',
				root : 'data',
				fields : recordStruts
			});
	var recordStore = null;
	var menuStore = null;
	var authorStore = null;
}
