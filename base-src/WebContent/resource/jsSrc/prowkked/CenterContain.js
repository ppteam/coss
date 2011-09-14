/**
 * @author 字典管理UI
 * @class Dictionary Manager Panle
 */
function CenterContain() {
	var model = new DataModel();
	var Record_Problem = Ext.data.Record.create(model.creatProblemRec());
	var card_Panel = null;
	var edit_form = null;
	var prolem_plan_form = null;
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
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'reportId',
														id : 'reportId',
														emptyText : '请选择周报编号',
														forceSelection : true,
														triggerAction : 'all',
														store : model.getReportStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '周报编号',
														allowBlank : false,
														anchor : '95%',
														listeners : {
															change : function(/* Ext.form.ComboBox */cb,/* Mixed */
																	newVal, /* Mixed */oldVal) {
																model.loadReportCtx(newVal, edit_form);
															}
														}
													}, {
														xtype : 'textfield',
														fieldLabel : '填写人',
														anchor : '95%',
														disabled : true
													}]
										}, {
											columnWidth : .3,
											layout : 'form',
											items : [{
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
											columnWidth : .4,
											layout : 'form',
											items : [{
														xtype : 'datefield',
														id : 'endDate',
														fieldLabel : '截至日期',
														format : 'Y-m-d',
														disabled : true,
														name : 'endDate',
														anchor : '95%',
														allowBlank : false
													}, {
														xtype : 'textfield',
														fieldLabel : '项目名称',
														name : 'projectName',
														anchor : '95%',
														disabled : true
													}]
										}]
							}, {
								layout : 'column',
								items : [{
											columnWidth : .25,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'efficiencyExecute',
														emptyText : '项目执行效率',
														forceSelection : true,
														triggerAction : 'all',
														store : model.getEfficiencyExecuteStrore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '执行效率',
														allowBlank : false,
														anchor : '95%'
													}]
										}, {
											columnWidth : .25,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'humanResource',
														emptyText : '人员变动频率',
														forceSelection : true,
														triggerAction : 'all',
														store : model.geHumanResourceStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '人力资源',
														allowBlank : false,
														anchor : '95%'
													}]
										}, {
											columnWidth : .25,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'requirementAlter',
														emptyText : '需求变更频率',
														forceSelection : true,
														triggerAction : 'all',
														store : model.getRequirementAlterStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '需求变更',
														allowBlank : false,
														anchor : '95%'
													}]
										}, {
											columnWidth : .25,
											layout : 'form',
											items : [{
														xtype : 'combo',
														mode : 'local',
														editable : false,
														name : 'clientRelation',
														emptyText : '与客户关系程度',
														forceSelection : true,
														triggerAction : 'all',
														store : model.geClientRelationStore(),
														valueField : 'regValue',
														displayField : 'displayValue',
														fieldLabel : '客户关系',
														allowBlank : false,
														anchor : '95%'
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
								name : 'resultsShow',
								fieldLabel : '主要提交产品',
								height : 100,
								anchor : '98%'
							}, {
								xtype : 'textarea',
								name : 'leaderAppraise',
								fieldLabel : '项目总体评价',
								height : 100,
								anchor : '98%'
							}]
				});

		// 里程碑管理
		var editor_plugs = new Ext.ux.grid.RowEditor();
		var millStone_grid = new Ext.grid.GridPanel({
					region : 'center',
					loadMask : true,
					id : 'pwr-card-1',
					title : '项目里程碑管理',
					columnLines : true,
					stripeRows : true,
					store : model.getStatsStore(),
					plugins : [editor_plugs],
					sm : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					columns : [new Ext.grid.RowNumberer(), {
								header : '里程碑',
								width : 85,
								dataIndex : 'milestoneDes',
								editor : {
									xtype : 'textfield',
									disabled : true
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
							}],
					autoExpandColumn : 'col_milestomeStats'
				});
		// --------- split line -------------------
		var editor_prolem = new Ext.ux.grid.RowEditor();
		editor_prolem.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					model.getProplemStore().commitChanges();
				});
		var prolem_Grid = new Ext.grid.GridPanel({
					loadMask : true,
					title : '项目问题描述',
					region : 'center',
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
												userName : null,
												problemDesc : null,
												resolveWay : null,
												resposibler : null,
												solveDate : null,
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
								xtype : 'datecolumn',
								format : 'Y-m-d',
								header : '解决日期',
								width : 95,
								dataIndex : 'solveDate',
								editor : {
									xtype : 'datefield',
									format : 'Y-m-d',
									allowBlank : false
								}
							}, {
								header : '负责人',
								width : 75,
								dataIndex : 'resposibler',
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

		prolem_plan_form = new Ext.form.FormPanel({
					id : 'pwr-card-2',
					layout : 'border',
					title : '周计划跟踪以及问题列表',
					items : [{
								region : 'north',
								layout : 'column',
								frame : true,
								labelAlign : 'top',
								height : 160,
								items : [{
											columnWidth : .5,
											layout : 'form',
											items : [{
														xtype : 'textarea',
														id : 'completePlan',
														name : 'completePlan',
														fieldLabel : '完成情况说明',
														emptyText : '对比上周计划，填写完成情况',
														height : 120,
														allowBlank : false,
														anchor : '98%'
													}]
										}, {
											columnWidth : .5,
											layout : 'form',
											items : [{
														xtype : 'textarea',
														id : 'nweekPlan',
														name : 'nweekPlan',
														fieldLabel : '下周计划',
														emptyText : '下周正对该项目的的工作计划',
														allowBlank : false,
														height : 120,
														anchor : '98%'
													}]
										}]
							}, prolem_Grid]
				});
		// --------- split line -------------------
		var editor_plan = new Ext.ux.grid.RowEditor();
		editor_plan.on('afteredit', function(
						/* Ext.ux.grid.RowEditor */editor,/* object */
						changed,/* Ext.data.Record */rec,/* Number */
						rowIndex) {
					model.getPlanStore().commitChanges();
				});
		var planGrid = new Ext.grid.GridPanel({
					loadMask : true,
					id : 'pwr-card-3',
					title : '项目人员工作总结与计划',
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
								header : '下周工作计划',
								width : 200,
								dataIndex : 'weeklyPlan',
								editor : {
									xtype : 'textfield',
									allowBlank : false
								}
							}, {
								header : '正常(h)',
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
								header : '加班(h)',
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
							}],
					stripeRows : true,
					autoExpandColumn : 'col_weeklySummary'
				});
		card_Panel = new Ext.Panel({
					region : 'center',
					layout : 'card',
					activeItem : 0,
					items : [edit_form, millStone_grid, prolem_plan_form, planGrid],
					bbar : ['->', '-', {
								text : '上一页',
								disabled : true,
								iconCls : 'btn_go_back',
								iconAlign : 'left',
								id : 'card-prev',
								handler : function(/* Ext.button */btn) {
									cardNav(-1);
								}
							}, {
								text : '下一页',
								id : 'card-next',
								iconCls : 'btn_go_next',
								iconAlign : 'right',
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
		model.handlerPweeklyReport(instance, resetUI);
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
				model.loadStatsList(resetButtons, n, layout);
			} else {
				Ext.Msg.alert('提示', '请完善项目周报的必填数据项。');
				return;
			}
		} else if (n === 2 && incr > 0) {
			model.loadProbleamsAction(resetButtons, n, layout);
		} else if (n === 3 && incr > 0) {
			// 判断问题填写数据的合法性
			var _sign = false;
			model.getProplemStore().each(function(rec) {
				if (rec.get('userName') == null || rec.get('problemDesc') == null || rec.get('resolveWay') == null
						|| rec.get('solveDate') == null || rec.get('resposibler') == null) {
					_sign = true;
					return false;
				}
			});
			if (_sign) {
				Ext.Msg.alert('提示', '问题描述存在数据信息不全，请完善相关数据项。');
				return;
			}

			if (Ext.getCmp('completePlan').getValue() !== '' && Ext.getCmp('nweekPlan').getValue() !== '') {
				if (model.getCurrentStats().loadpn) {
					resetButtons(n, layout);
				} else {
					model.loadPlansAction(resetButtons, n, layout);
				}
				Ext.getCmp('card-save').setDisabled(false);
			} else {
				Ext.Msg.alert('提示', '请完项目周报的必填数据项。');
				return;
			} // end_if
		}
		layout.setActiveItem(n);
		Ext.getCmp('card-prev').setDisabled(n === 0);
		Ext.getCmp('card-next').setDisabled(n === 3);
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
		Ext.getCmp('card-next').setDisabled(index === 3);
	}

	/*
	 * {新增问题描述记录}
	 */
	function addProlem(/* Ext.data.Record */newRec,/* Ext.data.JsonStore */store,/* Ext.ux.grid.RowEditor */
			editor,/* Ext.grid.GridPanel */grid) {
		editor.stopEditing();
		var rec = store.getAt(0);
		if (typeof rec !== 'undefined') {
			if (rec.get('userName') == null || rec.get('problemDesc') == null || rec.get('resolveWay') == null
					|| rec.get('solveDate') == null || rec.get('resposibler') == null) {
				editor.startEditing(0);
			} else {
				store.insert(0, newRec);
				grid.getView().refresh();
				grid.getSelectionModel().selectRow(0);
				editor.startEditing(0);
			}
		} else {
			store.insert(0, newRec);
			grid.getView().refresh();
			grid.getSelectionModel().selectRow(0);
			editor.startEditing(0);
		}
	} // end_fun

	/**
	 * {复位当前UI界面到打开状态}
	 */
	function resetUI() {
		var layout = card_Panel.getLayout();
		layout.setActiveItem(0);
		Ext.getCmp('card-prev').setDisabled(true);
		Ext.getCmp('card-next').setDisabled(false);
		Ext.getCmp('card-save').setDisabled(true);
		Ext.getCmp('reportId').clearValue();
		edit_form.getForm().reset();
	} // end_fun
} // end_class
