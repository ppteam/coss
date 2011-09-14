function perweekModel() {
	var deptIds = new Array();

	this.getDeptIds = function() {
		return deptIds;
	};

	this.initDeptGroup = function(/* Ext.FormPanel */fpanle) {
		if (dept_data !== null) {
			var ckGroup = Ext.getCmp('deptGroup');
			var ck_groups = {
				xtype : 'checkboxgroup',
				columns : 2,
				autoWidth : true,
				items : []
			};
			var j = 0;
			for (var i = 0; i < dept_data.length; i++) {
				// 过滤父节点
				if (dept_data[i][2] === 0) {
					deptIds.push(dept_data[i][0]);
					ck_groups.items[j] = new Ext.form.Checkbox({
								boxLabel : dept_data[i][1],
								name : dept_data[i][0],
								id : dept_data[i][0]
							});
					j++;
				}
			} // end_for
			ckGroup.add(ck_groups);
			ckGroup.doLayout();
		} // end_if

	};
} // end_fun

