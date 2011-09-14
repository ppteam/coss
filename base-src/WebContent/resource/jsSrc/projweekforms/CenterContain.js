/**
 * @author 个人周报表
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var contain = null;
	var _statusbar = null;
	var perweek_form = null;
	var perweek_dialog = null;
	var start = null;
	var end = null;
	// var _dataModel = new ProjPersonModel();

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};

	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		}
		return contain;
	};
	function buildContain() {
		perweek_form = new Ext.FormPanel({
					region : 'center',
					labelWidth : 55,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					items : [new Ext.form.DateField({
								fieldLabel : '报表日期',
								id : 'startDate',
								name : 'startDate',
								anchor : '30%',
								format : 'Y年m月d日',
								allowBlank : false
							})]
				});

		// _dataModel.initDeptGroup(perweek_form);
		perweek_dialog = new Ext.Panel({
					title : '项目周报表',
					layout : 'border',
					region : 'center',

					items : [perweek_form],
					tbar : [{
						text : '生成报表',
						handler : function(/* Button */btn,/* Event */
								e) {
							if (Ext.getCmp('startDate').getValue() === '') {
								Ext.MessageBox.alert("消息", "开始日期不能为空");
								return;
							} else {
								start = Ext.getCmp('startDate').getValue().format('Y-m-d');
							}

							window.location.href = 'http://' + window.location.host
									+ '/workbase/report/proxy/view.xls?view=projWeek&bis_startDate=' + start;
							/***************************************************
							 * + '&endDate=' + end + '&list_deptId=' +
							 * _arg_ids.join("|")
							 **************************************************/
						}
					}, '-', {
						text : '重置',
						handler : function(/* Button */btn,/* Event */
								e) {
							perweek_form.getForm().reset();
						}
					}]
				});
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [perweek_dialog]
				});

	} // end_fun_buildContain
	/**
	 * @function buildContain
	 */

} // end_class

