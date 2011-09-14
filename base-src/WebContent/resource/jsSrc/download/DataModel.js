/**
 * @class 字典管理Bean 建模
 * @author haoxiaojie
 */
function DataModel() {

	/**
	 * @function Catalog 请求Store
	 * @return {}
	 */
	this.getTodoStore = function() {
		if (todoStore === null) {
			todoStore = new Ext.data.ArrayStore({
						fields : [{
									name : 'softName'
								}, {
									name : 'softCmts'
								}, {
									name : 'loadPath'
								}]
					});
		} // end_if
		todoStore.loadData(myData);
		return todoStore;
	};

	var todoStore = null;

	var myData = [['火狐浏览器', '开源浏览器，你懂的...', 'Firefox.exe'],
			['雅黑字体', 'Vista 的最大贡献之一,解压之后复制到  window/fonts/ 下...', 'msyh.zip'],
			['Spark 2.6.3', '新版Spark 软件，支持更多设置...', 'spark263.exe'],
			['Mysql 5.* 数据库', '免费数据库服务器,32位版本...', 'mysql-win32.rar'],
			['Putty 新版', '连接Linux利器，支持宽屏显示...', 'putty.rar'],
			['工时系统操作手册', '概要版本的操作手册，希望对大家都所帮助...2011-09-01 更新', 'handbook.pdf']];

} // end_class
