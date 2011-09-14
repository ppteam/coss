/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var RuleRecord = Ext.data.Record.create(model.getRecStruts());
	var catalogGrid = null;
	var _statusbar = null;
	var contain = null;
	var old_rec = null;
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
					items : [catalogGrid]
				});

		// create the Grid
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
		var editor_catalog = new Ext.ux.grid.RowEditor();
		editor_catalog.on('beforeedit', function(
						/* Ext.ux.grid.RowEditor */edit,/* Number */
						rowIndex) {
					old_rec = model.getHolidayStore().getAt(rowIndex).copy();
				});
		editor_catalog.on('canceledit', function(
						/* Ext.ux.grid.RowEditor */edit,/* boolean */
						pressed) {
					model.cleanInvaladData(model.getHolidayStore());
				});
		editor_catalog.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					rec.set('enabled', rec.get('enabled') ? 1 : 0);
					rec.set('ruleType', rec.get('ruleType') ? 1 : 0);
					rec.set('dayType', rec.get('dayType') ? 1 : 0);
					model.handleAjax(rec.data, catalogGrid);
				});
		catalogGrid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					columnLines : true,
					stripeRows : true,
					plugins : [editor_catalog],
					store : model.getHolidayStore(),
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true,
								listeners : {
									rowselect : function(
											/* SelectionModel */sml,/* Number */
											rowIndex, /* Ext.data.Record */
											rec) {
										editor_catalog.stopEditing(false);
									}
								}
							}),
					tbar : [{
								text : '新增假期',
								iconCls : 'btn_add_data',
								handler : function() {
									var saveInstance = new RuleRecord({
												holidayId : null,
												userId : null,
												holidayName : null,
												startDate : null,
												dayType : 0,
												endDate : null,
												enabled : 1,
												additionInfo : null
											});
									addLocal(saveInstance, model.getHolidayStore(), editor_catalog, catalogGrid);
								}
							}, '-'],
					bbar : new Ext.PagingToolbar({
								pageSize : model.getPageSize(),
								store : model.getHolidayStore(),
								displayInfo : true,
								paramNames : {
									start : 'int_start',
									limit : 'int_limit'
								},
								displayMsg : '(H:双击进入编辑模式) 显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg : '没有记录'
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '假期名称',
								width : 125,
								dataIndex : 'holidayName',
								editor : {
									xtype : 'textfield',
									emptyText : '自动生成'
								}
							}, {
								header : '填写人',
								width : 75,
								dataIndex : 'userId',
								editor : {
									xtype : 'textfield',
									allowBlank : true,
									disabled : true
								}
							}, {
								header : '假期类型',
								width : 75,
								dataIndex : 'dayType',
								renderer : function(val) {
									if (val == 0) {
										return '<span style="color:green;">假期</span>';
									} else if (val == 1) {
										return '<span style="color:red;">上班</span>';
									} else {
										return '<span style="color:red;">未知[' + val + ']</span>';
									}
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									forceSelection : true,
									allowBlank : false,
									triggerAction : 'all',
									store : model.getDayTypeStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}, {
								header : '生效状态',
								width : 75,
								dataIndex : 'enabled',
								renderer : function(val) {
									if (val == 1) {
										return '<span style="color:green;">生效</span>';
									} else if (val == 0) {
										return '<span style="color:red;">无效</span>';
									} else {
										return '<span style="color:red;">未知状态</span>';
									}
								},
								editor : {
									xtype : 'checkbox'
								}
							}, {
								header : '开始日期',
								width : 95,
								dataIndex : 'startDate',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									date : new Date()
								}
							}, {
								header : '截止日期',
								width : 95,
								dataIndex : 'endDate',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									date : new Date()
								}
							}, {
								header : '填写日期',
								width : 75,
								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'entryDate',
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d',
									disabled : true
								}
							}, {
								id : 'col_additionInfo',
								header : '附加说明',
								width : 125,
								dataIndex : 'additionInfo',
								editor : {
									xtype : 'textfield',
									emptyText : '选填字段'
								}
							}],
					autoExpandColumn : 'col_additionInfo'
				});
		catalogGrid.getBottomToolbar().on('beforechange', function(/* Ext.PagingToolbar */pp, /* Object */params) {
					var old_params = model.getHolidayStore().lastOptions.params;
					if (typeof old_params !== 'undefined') {
						params.int_year = old_params.int_year;
					}
				});
	} // end_fun

	/**
	 * @function 新增记录
	 * @param {}
	 *            newRec
	 * @param {}
	 *            store
	 * @param {}
	 *            editor
	 * @param {}
	 *            grid
	 */
	function addLocal(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		if (store.getCount() != 0) {
			if (store.getAt(0).get('holidayId') !== null) {
				editor.stopEditing();
				store.insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		} else {
			if (typeof store.getAt(0) === 'undefined') {
				editor.stopEditing();
				store.insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		}
	} // end_fun
} // end_class
