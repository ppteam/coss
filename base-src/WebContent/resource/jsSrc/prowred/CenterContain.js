/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */
function CenterContain() {
	var model = new DataModel();
	var Record_Problem = Ext.data.Record.create(model.creatProblemRec());
	var card_Panel = null;
	var edit_form = null;
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
		edit_form = new Ext.FormPanel({
					frame : true,
					id : 'pwr-card-0',
					title : '项目周报基本信息',
					items : [{
								layout : 'column',
								items : [{
											columnWidth : .5,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'reportId',
														forceSelection : true,
														triggerAction : 'all',
														store : model.getReportStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '周报编号',
														emptyText : '选择周报编号',
														allowBlank : false,
														anchor : '95%',
														listeners : {
															change : function(/* Ext.form.ComboBox */cb,/* Mixed */
																	newVal, /* Mixed */oldVal) {
																model.loadReportCtx(newVal, edit_form);
															}
														}
													}, {
														xtype : 'datefield',
														id : 'startDate',
														fieldLabel : '开始日期',
														format : 'Y-m-d',
														disabled : true,
														name : 'startDate',
														anchor : '95%',
														allowBlank : false
													}, {
														xtype : 'textfield',
														fieldLabel : '项目经理',
														name : 'psmName',
														anchor : '95%',
														disabled : true
													}]
										}, {
											columnWidth : .5,
											layout : 'form',
											items : [{
														xtype : 'textfield',
														fieldLabel : '项目名称',
														name : 'projectName',
														anchor : '95%',
														disabled : true
													}, {
														xtype : 'datefield',
														id : 'endDate',
														fieldLabel : '截至日期',
														format : 'Y-m-d',
														disabled : true,
														name : 'endDate',
														anchor : '95%',
														allowBlank : false
													}]
										}]
							}, {
								xtype : 'textarea',
								name : 'projectDesc',
								fieldLabel : '项目进展综述',
								height : 100,
								anchor : '98%'
							}, {
								xtype : 'textarea',
								name : 'leaderAppraise',
								fieldLabel : '负责人总体评价',
								height : 100,
								anchor : '98%'
							}]
				});

		var editor_prolem = new Ext.ux.grid.RowEditor();
		editor_prolem.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					model.getProplemStore().commitChanges();
				});
		var prolem_Grid = new Ext.grid.GridPanel({
					loadMask : true,
					id : 'pwr-card-1',
					columnLines : true,
					title : '项目问题描述',
					plugins : [editor_prolem],
					store : model.getProplemStore(),
					tbar : ['-', {
								text : '新增',
								iconCls : 'btn_add_data',
								handler : function(/* Ext.Button */btn) {
									var saveInstance = new Record_Problem({
												problemId : null,
												reportId : null,
												discoverDate : new Date(),
												userName : '提出人',
												problemDesc : '问题描述',
												resolveWay : '解决措施',
												problemStats : '2348997cc7c1438ba317bf2d7da1288b'
											});
									addProlem(saveInstance, model.getProplemStore(), editor_prolem, prolem_Grid);
								} // end_handle
							}, '-', {
								text : '删除',
								iconCls : 'btn_del_data',
								handler : function(/* Ext.Button */btn) {
									var rec = prolem_Grid.getSelectionModel().getSelected();
									if (typeof rec === 'undefined') {
										Ext.Msg.alert('提示', '请在下表中选择要删除的数据信息');
									} else {
										if (rec.get('problemId') === null) {
											model.getProplemStore().remove(rec);
											model.getProplemStore().commitChanges();
										} else {
											Ext.Msg.alert('错误提示', '该问题为项目已有问题，不能删除。');
										}
									} // end_if

								}
							}],
					columns : [new Ext.grid.RowNumberer(), {
								xtype : 'datecolumn',
								format : 'Y-m-d',
								header : '提出日期',
								width : 95,
								dataIndex : 'discoverDate',
								editor : {
									xtype : 'datefield',
									format : 'Y-m-d',
									allowBlank : false
								}
							}, {
								header : '提出人',
								width : 85,
								dataIndex : 'userName',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								id : 'col_problemDesc',
								header : '问题描述',
								width : 75,
								dataIndex : 'problemDesc',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '解决措施',
								width : 205,
								dataIndex : 'resolveWay',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '问题状态',
								width : 75,
								dataIndex : 'problemStats',
								renderer : function(val) {
									var _name = null;
									model.getProblemStatsStore().each(function(
													/* Ext.data.Record */rec) {
												if (rec.get('regValue') === val) {
													_name = rec.get('displayValue');
													return false;
												}
											});
									return _name;
								},
								editor : {
									xtype : 'combo',
									mode : 'local',
									editable : false,
									typeAhead : true,
									allowBlank : false,
									forceSelection : true,
									triggerAction : 'all',
									store : model.getProblemStatsStore(),
									valueField : 'regValue',
									displayField : 'displayValue'
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_problemDesc'
				});

		var editor_plan = new Ext.ux.grid.RowEditor();
		editor_plan.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					model.getPlanStore().commitChanges();
				});
		var planGrid = new Ext.grid.GridPanel({
					loadMask : true,
					id : 'pwr-card-2',
					title : '人员工作量统计',
					columnLines : true,
					plugins : [editor_plan],
					store : model.getPlanStore(),
					columns : [new Ext.grid.RowNumberer(), {
								header : '姓名',
								width : 75,
								dataIndex : 'userName',
								editor : {
									xtype : 'textfield',
									disabled : true,
									allowBlank : false
								}
							}, {
								header : '部门',
								width : 75,
								dataIndex : 'branchName',
								editor : {
									xtype : 'textfield',
									disabled : true,
									allowBlank : true
								}
							}, {
								id : 'col_weeklySummary',
								header : '本周主要工作',
								width : 75,
								dataIndex : 'weeklySummary',
								editor : {
									xtype : 'textfield',
									allowBlank : true
								}
							}, {
								header : '正常工时(h)',
								width : 75,
								dataIndex : 'normalTime',
								renderer : function(val) {
									return (val / 100).toFixed(2);
								},
								editor : {
									xtype : 'numberfield',
									disabled : true,
									allowBlank : false
								}
							}, {
								header : '加班工时(h)',
								width : 75,
								dataIndex : 'overTime',
								renderer : function(val) {
									return (val / 100).toFixed(2);
								},
								editor : {
									xtype : 'numberfield',
									disabled : true,
									allowBlank : false
								}
							}, {
								header : '总工作量(h)',
								width : 75,
								dataIndex : 'overTime',
								renderer : function(value, metaData, record) {
									return ((record.get('normalTime') + record.get('overTime')) / 100).toFixed(2);
								},
								editor : {
									xtype : 'numberfield',
									disabled : true,
									allowBlank : false
								}
							}],
					stripeRows : true,
					autoExpandColumn : 'col_weeklySummary'
				});
		card_Panel = new Ext.Panel({
					region : 'center',
					layout : 'card',
					activeItem : 0,
					items : [edit_form, prolem_Grid, planGrid],
					bbar : ['->', {
								text : '&laquo;上一页',
								disabled : true,
								id : 'card-prev',
								handler : function(/* Ext.button */btn) {
									cardNav(-1);
								}
							}, {
								text : '下一页&raquo;',
								id : 'card-next',
								disabled : true,
								handler : function(/* Ext.button */btn) {
									cardNav(1);
								}
							}, {
								text : '&nbsp;更&nbsp;&nbsp;新&nbsp;',
								id : 'card-save',
								iconCls : 'btn_refresh',
								handler : function(/* Ext.btn */btn) {
									wrapperJson();
								}
							}]
				});

		// create the Grid
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [card_Panel]
				});

	} // end_fun_buildContain

	/**
	 * {封装表单数据}
	 */
	function wrapperJson() {
		var instance = edit_form.getForm().getFieldValues();
		model.handlerPweeklyReport(instance, card_Panel.getLayout(), edit_form);
	} // end_fun

	/**
	 * {导航切换操作}
	 * 
	 * @param {}
	 *            prev
	 * @param {}
	 *            next
	 */
	function cardNav(/* int */incr,/* Button */prev,/* Button */next) {
		var layout = card_Panel.getLayout();
		var i = layout.activeItem.id.split('pwr-card-')[1];
		var n = parseInt(i, 10) + incr;
		if (n === 1 && incr > 0) {
			if (model.getCurrentStats().loadpm) {
				resetButtons(n, layout);
			} else {
				model.loadProbleamsAction(n, layout, resetButtons);
			}
		} else if (n == 2 && incr > 0) {
			if (model.getCurrentStats().loadpn) {
				resetButtons(n, layout);
			} else {
				model.loadPlansAction(resetButtons, n, layout);
			}
		} else {
			resetButtons(n, layout);
		}
	} // end_fun

	/**
	 * {重置导航按钮}
	 * 
	 * @param {}
	 *            index
	 * @param {}
	 *            lay
	 */
	function resetButtons(/* int */index,/* Ext.layout */lay) {
		lay.setActiveItem(index);
		Ext.getCmp('card-prev').setDisabled(index === 0);
		Ext.getCmp('card-next').setDisabled(index === 2);
	}

	/*
	 * {新增问题描述记录}
	 */
	function addProlem(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		editor.stopEditing();
		store.insert(0, newRec);
		grid.getView().refresh();
		grid.getSelectionModel().selectRow(0);
		editor.startEditing(0);
	} // end_fun

} // end_class
