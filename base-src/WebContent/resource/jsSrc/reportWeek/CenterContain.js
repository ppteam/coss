/**
 * @author 考勤管理
 * @class Dictionary Manager Panle
 */

function CenterContain() {

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
	 * @function buildContain
	 */
	function buildContain() {
		weekDialog.getWeekDialog();
	} // end_fun_buildContain

	var model = new WeekModel();
	var weekDialog = new WeekDialog(model);
	var contain = null;
	var _statusbar = null;
} // end_class
