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
											columnWidth : .3,
											layout : 'form',
											items : [{
														xtype : 'textfield',
														fieldLabel : '周报编号',
														name : 'reportNo',
														anchor : '95%',
														emptyText : '必填字段',
														allowBlank : false
													}]
										}, {
											columnWidth : .3,
											layout : 'form',
											items : [{
														xtype : 'datefield',
														fieldLabel : '开始日期',
														format : 'Y-m-d',
														value : new Date(),
														editable : false,
														name : 'startDate',
														anchor : '95%',
														allowBlank : false,
														listeners : {
															change : function(dateField, newValue, oldValue) {
																model.resetStatus();
															} // end_change
														}
													}]
										}, {
											columnWidth : .4,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'projectId',
														emptyText : '必选项',
														forceSelection : true,
														triggerAction : 'all',
														store : model.getProjectStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '项目名称',
														allowBlank : false,
														anchor : '95%',
														listeners : {
															select : function(/* Ext.form.ComboBox */cb,/* Ext.data.Record */
																	rec, /* Number */index) {
																if (model.getCurrentStats().projectId !== rec
																		.get('regValue')) {
																	model.getCurrentStats().projectId = rec
																			.get('regValue');
																	model.getCurrentStats().weekBegin = rec
																			.get('addVal');
																	model.resetStatus();
																}
															}
														}
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
					title : '项目问题描述',
					columnLines : true,
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
											Ext.Msg.alert('错误提示', '该问题为上周遗留问题，不可以删除。');
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
					bbar : ['->', '-', {
								text : '&laquo;上一页',
								disabled : true,
								id : 'card-prev',
								handler : function(/* Ext.button */btn) {
									cardNav(-1);
								}
							}, {
								text : '下一页&raquo;',
								id : 'card-next',
								handler : function(/* Ext.button */btn) {
									cardNav(1);
								}
							}, '-', {
								text : '&nbsp;保&nbsp;&nbsp;存&nbsp;',
								id : 'card-save',
								iconCls : 'btn_saveAll',
								disabled : true,
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
	 * {判断两个日期是否属于同一周}
	 * 
	 * @param {}
	 *            newDt
	 * @param {}
	 *            oldDt
	 * @return boolean
	 */
	function checkSameWeek(/* Date */newDt,/* Date */oldDt) {
		var isSame = false;
		// alert(typeof newDt.format('N'));
		return isSame;
	} // end_fun

	/**
	 * {封装表单数据}
	 */
	function wrapperJson() {
		var instance = edit_form.getForm().getFieldValues();
		model.handlerPweeklyReport(instance);
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
			// 检测表单数据的合法性
			if (edit_form.getForm().isValid()) {
				// 加载数据信息
				if (model.getCurrentStats().checked) {
					resetButtons(n, layout);
				} else {
					var instance = edit_form.getForm().getFieldValues();
					// alert(dwr.util.toDescriptiveString(instance, 2));
					// alert(instance.startDate.format('w'));
					var project_start = -1;
					for (var j = 0; j < project_list.length; j++) {
						if (project_list[j][0] === instance.projectId) {
							project_start = project_list[j][2];
						}
					}
					if (project_start === -1) {
						throw new Error('系统数据异常，请联系系统管理员');
					}
					if (instance.startDate.format('w') + 1 !== project_start) {
						Ext.Msg.alert('提示', '项目周报开始周选择有误，请按照项目实际情况选择。');
					} else {
						model.checkProjectWPInfo(instance, resetButtons, n, layout);
					}
				}
			} else {
				Ext.Msg.alert('操作提示', '请完善表单必填数据项。');
				return;
			}
		} else if (n == 2 && incr > 0) {
			var instance = edit_form.getForm().getFieldValues();
			if (model.getCurrentStats().loadpn) {
				resetButtons(n, layout);
			} else {
				model.loadPlansAction(instance, resetButtons, n, layout);
			}
			Ext.getCmp('card-save').setDisabled(false);
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
