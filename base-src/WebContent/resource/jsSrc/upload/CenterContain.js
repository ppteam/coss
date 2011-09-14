/**
 * @author 代办UI
 * @class Dictionary Manager Panle
 */

function CenterContain() {
	var model = new DataModel();
	var fileForm = null;
	var _statusbar = null;
	var _selected_Id = null;
	var contain = null;

	this.init = function(statusbar) {
		_statusbar = statusbar;
	};

	this.getPanel = function() {
		if (contain === null) {
			buildContain();
		} // end_if
		return contain;
	};

	/**
	 * @function {构造UI函数}
	 */
	function buildContain() {
		fileForm = new Ext.FormPanel({
					region : 'center',
					fileUpload : true,
					frame : true,
					title : '文件上传',
					bodyStyle : 'padding: 10px 10px 0 10px;',
					labelWidth : 75,
					defaults : {
						anchor : '25%',
						allowBlank : false,
						msgTarget : 'side'
					},
					items : [{
								xtype : 'textfield',
								fieldLabel : '文件名称',
								value : 'user_list.txt',
								name : 'fileName',
								id : 'fileName'
							}, {
								xtype : 'fileuploadfield',
								id : 'file',
								emptyText : '选择上传的模板',
								fieldLabel : '本地路径',
								name : 'file',
								buttonText : '',
								buttonCfg : {
									iconCls : 'btn_upload_icon'
								}
							}],
					buttons : [{
						text : '显示模板',
						handler : function() {
							window.location.href = 'http://' + window.location.host
									+ '/workbase/resource/templet/bat_import_templet.txt';
						}
					}, {
						text : '上传资料',
						handler : function() {
							if (fileForm.getForm().isValid()) {
								fileForm.getForm().submit({
											url : '/workbase/console/multipart/file.ftl',
											method : 'POST',
											waitMsg : '上传中，请耐心等待...',
											success : function(fp, action) {
												// alert(dwr.util.toDescriptiveString(action.response,
												// 2));
												Ext.Msg.alert('操作结果', action.result.msg);
											}
										});
							}
						}
					}, {
						text : '重置信息',
						handler : function() {
							fileForm.getForm().reset();
						}
					}]
				});

		// create the Grid
		contain = new Ext.Panel({
					layout : 'border',
					region : 'center',
					bbar : _statusbar,
					items : [fileForm]
				});

	} // end_fun_buildContain

} // end_class
