/**
 * @class 顶部Panel
 * @author hxj
 */
function TopContain() {

	var tital_html = '<div style="background-color:#DFE8F6;height:100px;text-indent:30px;">'
			+ '<font style="color:#3C3C3C;font-style: italic;font-size:20px;font-weight:700;text-decoration : underline; font-family: "MSYH", "Courier New";">'
			+ 'Neusoft-项目工时管理系统</font>'
			+ '<font style="color:#F07746;font-style: italic;font-size:12px;">&nbsp;&nbsp;(Beta 0.1)</font>' + '</div>';

	/**
	 * {初始化UI等动作预定义}
	 * @param {} idValue
	 * @param {} toolbar
	 */
	this.init = function(/* String */idValue,/* ToolBar */toolbar) {
		top_id = idValue;
		p_bbar = toolbar;
	};

	this.getContainer = function() {
		if (p_panel == null) {
			builderPanle();
		}
		return p_panel;
	};

	this.getId = function() {
		return top_id;
	};

	function builderPanle() {
		p_panel = new Ext.Panel({
					region : 'north',
					id : top_id,
					height : 65,
					html : tital_html,
					bbar : p_bbar
				});
	}

	var top_id = null;
	var p_panel = null;
	var p_bbar = null;
}
