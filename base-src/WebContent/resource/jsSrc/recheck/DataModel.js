/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	/**
	 * @function 默认的分页数量
	 * @return int
	 */
	this.getPageSize = function() {
		return def_page_size;
	};

	/**
	 * {初始化部门选择列表UI ckbox}
	 * 
	 * @param {}
	 *            fpanle
	 */
	this.initDeptGroup = function(/* Ext.FormPanel */fpanle) {
		if (dept_data !== null && dept_data.length !== 0) {
			var ckGroup = Ext.getCmp('deptGroup');
			var ck_groups = {
				xtype : 'checkboxgroup',
				columns : 2,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dept_data.length; i++) {
				deptIds.push(dept_data[i][0]);
				ck_groups.items[j] = new Ext.form.Checkbox({
							boxLabel : dept_data[i][1],
							name : dept_data[i][0],
							id : dept_data[i][0]
						});
				j++;
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if
	};
	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getDetailStore = function() {
		if (detailStore === null) {
			detailStore = new Ext.data.JsonStore({
						autoDestroy : true,
						autoLoad : true,
						baseParams : {
							int_start : 0,
							int_limit : def_page_size,
							userName : null
						},
						root : 'data',
						totalProperty : 'total',
						idProperty : 'detailId',
						storeId : 'detailStore',
						fields : recordStruts,
						url : '/workbase/console/duty/ckdetail.json'
					});
		}
		return detailStore;
	};

	this.getRecStruts = function() {
		return recordStruts;
	};

	/**
	 * {数据格式转换}
	 */
	function locationToDate(v, rec) {
		if (v !== null && v !== -28800000) {
			return new Date(v);
		}
	}// end_fun

	/**
	 * @note 异步请求修改签到数据信息
	 * @param {}
	 *            data
	 */
	this.checkEditedAjax = function(/* Record */rec) {
		// alert(dwr.util.toDescriptiveString(rec.data, 2));
		DutyAjaxAction.checkEdited(rec.data, function(/* Json */reponse) {
					// alert(dwr.util.toDescriptiveString(reponse.callData, 3));
					if (!reponse.error) {
						var callData = reponse.callData.checkDetail;
						rec.set('beginStats', callData.beginStats);
						rec.set('endStats', callData.endStats);
						rec.set('dayStats', callData.dayStats);
						detailStore.commitChanges();
					} else {
						Ext.Msg.alert('错误提示', respone.msg);
					}
				});
	};

	/**
	 * {初始化规则列表数据信息}
	 */
	this.getRuleStore = function() {
		if (ruleStore === null) {
			var dataList = new Array();
			dataList.push(happyDay);
			for (var i = 0; i < rule_data.length; i++) {
				dataList.push(rule_data[i]);
			}
			ruleStore = new Ext.data.ArrayStore({
						fields : optionStruts,
						data : dataList
					});
		}
		return ruleStore;
	};

	var recordStruts = ['detailId', 'userId', 'holidayName', {
				name : 'dayStats',
				type : 'int'
			}, {
				name : 'beginStats',
				type : 'int'
			}, {
				name : 'endStats',
				type : 'int'
			}, {
				name : 'checkDate',
				type : 'int',
				convert : locationToDate
			}, 'beginCheck', 'endCheck', 'deptName', 'userName', 'ruleId', 'comments'];

	var ruleStore = null;
	var deptIds = new Array();
	var detailStore = null;
	var def_page_size = 20;
	var happyDay = ['00000000000000000000000000000000', '不考核', 0];
}
