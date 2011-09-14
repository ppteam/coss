/**
 * @author 项目里程碑
 * @class Dictionary Manager Panle
 */

function MilestoneUI(/* DataModel */model) {
	var _dialog = null;
	var _gridPanel = null;
	var _projectId = null;
	var editor_plugs = null;
	var Record_Stats = Ext.data.Record.create(model.getStatsStruts());
	// 指向当前选中的记录
	var _selected_rec = null;

	this.getDialog = function(/* String */projectIdVal) {
		_projectId = projectIdVal;
		if (_dialog == null) {
			builderDialog();
		}
		model.getStatsStore().removeAll();
		model.getStatsStore().load({
					params : {
						projectId : projectIdVal
					}
				});
		return _dialog;
	};

	/**
	 * 
	 */
	function builderGrid() {
		if (_gridPanel === null) {
			editor_plugs = new Ext.ux.grid.RowEditor();
			editor_plugs.on('canceledit', function(
							/* Ext.ux.grid.RowEditor */edit,/* boolean */
							pressed) {
						// 移除无效的数据
						model.cleanInvaladData(model.getStatsStore(), 'statsId');
					});
			editor_plugs.on('afteredit', function(
							/* Ext.ux.grid.RowEditor */editor,/* object */
							changed,/* Ext.data.Record */rec,/* Number */
							rowIndex) {
						// alert(dwr.util.toDescriptiveString(rec.data, 2));
						model.handleAjaxStats(rec.data);
					});
			_gridPanel = new Ext.grid.GridPanel({
						region : 'center',
						loadMask : true,
						columnLines : true,
						store : model.getStatsStore(),
						plugins : [editor_plugs],
						tbar : [{
									text : '新增',
									iconCls : 'btn_add_data',
									handler : function() {
										var saveInstance = new Record_Stats({
													statsId : null,
													projectId : _projectId,
													milestoneName : null,
													planStart : null,
													planEnd : null,
													realityStart : null,
													realityEnd : null,
													milestomeStats : null,
													planVersion : null
												});
										add(saveInstance, model.getStatsStore(), editor_plugs, _gridPanel);
									}
								}, '-', {
									text : '删除',
									iconCls : 'btn_del_data',
									handler : function(/* Ext.Button */btn) {
										var rec = _gridPanel.getSelectionModel().getSelected();
										if (typeof rec !== 'undefined') {
											rec.data.statsId = '-1';
											model.handleAjaxStats(rec.data);
										};
									}
								}],
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true,
									listeners : {
										rowselect : function(
												/* SelectionModel */sml,/* Number */
												rowIndex, /* Ext.data.Record */
												rec) {
											editor_plugs.stopEditing(false);
										}
									}
								}),
						columns : [new Ext.grid.RowNumberer(), {
									header : '里程碑',
									width : 85,
									dataIndex : 'milestoneName',
									editor : {
										xtype : 'combo',
										mode : 'local',
										editable : false,
										typeAhead : true,
										allowBlank : false,
										forceSelection : true,
										triggerAction : 'all',
										store : model.getMilestoneStore(),
										valueField : 'regValue',
										displayField : 'displayValue'
									},
									renderer : function(val) {
										var _value = null;
										model.getMilestoneStore().each(function(/* Ext.data.Record */rec) {
													if (rec.get('regValue') === val) {
														_value = rec.get('displayValue');
														return false;
													}
													return true;
												});
										return _value;
									}
								}, {
									xtype : 'datecolumn',
									format : 'Y-m-d',
									header : '计划开始',
									width : 85,
									dataIndex : 'planStart',
									editor : {
										xtype : 'datefield',
										allowBlank : false
									}
								}, {
									xtype : 'datecolumn',
									format : 'Y-m-d',
									header : '计划结束',
									width : 85,
									dataIndex : 'planEnd',
									editor : {
										xtype : 'datefield',
										allowBlank : false
									}
								}, {
									xtype : 'datecolumn',
									format : 'Y-m-d',
									header : '实际开始',
									width : 85,
									dataIndex : 'realityStart',
									editor : {
										xtype : 'datefield',
										allowBlank : true
									}
								}, {
									xtype : 'datecolumn',
									format : 'Y-m-d',
									header : '实际结束',
									width : 85,
									dataIndex : 'realityEnd',
									editor : {
										xtype : 'datefield',
										allowBlank : true
									}
								}, {
									id : 'col_milestomeStats',
									header : '项目状态',
									width : 85,
									dataIndex : 'milestomeStats',
									editor : {
										xtype : 'combo',
										mode : 'local',
										editable : false,
										typeAhead : true,
										allowBlank : false,
										forceSelection : true,
										triggerAction : 'all',
										store : model.getProjectStatusStore(),
										valueField : 'regValue',
										displayField : 'displayValue'
									},
									renderer : function(val) {
										var _value = null;
										model.getProjectStatusStore().each(function(/* Ext.data.Record */rec) {
													if (rec.get('regValue') === val) {
														_value = rec.get('displayValue');
														return false;
													}
													return true;
												});
										return _value;
									}
								}],
						stripeRows : true,
						autoExpandColumn : 'col_milestomeStats'
					});
		}
	}

	/**
	 * {项目对话框组建}
	 */
	function builderDialog() {
		builderGrid();
		_dialog = new Ext.Window({
					title : '项目-里程碑维护对话框',
					width : 700,
					height : 300,
					closeAction : 'hide',
					plain : true,
					modal : true,
					constrain : true,
					layout : 'border',
					items : [_gridPanel]
				});
	}

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
	function add(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		if (store.getCount() !== 0) {
			if (store.getAt(0).get('statsId') !== null) {
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
