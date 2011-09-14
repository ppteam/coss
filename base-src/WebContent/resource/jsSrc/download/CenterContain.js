/**
 * @author 代办UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var todoGrid = null;
	var _statusbar = null;
	var contain = null;

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};

	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		}
		return contain;
	};

	/**
	 * @function {构造UI函数}
	 */
	function buildContain() {
		builderCatalogGrid();
		var grids_Panel = new Ext.Panel({
					region : 'center',
					layout : 'border',
					items : [todoGrid]
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [grids_Panel]
				});

	} // end_fun_buildContain

	/**
	 * {创建CatalogUI}
	 */
	function builderCatalogGrid() {
		todoGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					title : '系统相关资料以及软件下载::欢迎大家补充',
					columnLines : true,
					stripeRows : true,
					store : model.getTodoStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '软件名称',
								width : 160,
								sortable : true,
								dataIndex : 'softName'
							}, {
								id : 'col_todoDes',
								header : '软件介绍',
								width : 75,
								sortable : true,
								dataIndex : 'softCmts'
							}, {
								xtype : 'actioncolumn',
								header : '下载',
								width : 70,
								items : [{
											icon : '../../resource/jsLibs/ux/icons/download.png', // Use
											tooltip : 'Download',
											handler : function(grid, rowIndex, colIndex) {
												var rec = model.getTodoStore().getAt(rowIndex);
												download(rec.get('softName'), rec.get('loadPath'));
											}
										}]
							}],
					autoExpandColumn : 'col_todoDes'
				});
	} // end_fun

	/**
	 * Custom function used for column renderer
	 * 
	 * @param {Object}
	 *            val
	 */
	function download(/* string */title,/* string */softName) {
		window.open(url_prefix + softName, title);
	} // end_if

	var url_prefix = 'http://' + window.location.host + '/software/';
} // end_class
