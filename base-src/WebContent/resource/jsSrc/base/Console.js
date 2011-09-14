/**
 * @function JS 总体布局构建
 * @author HaoXiaoJie
 */

var viewport = null;

Ext.onReady(function() {
			Ext.QuickTips.init();
			dwr.util.useLoadingMessage();
			Ext.BLANK_IMAGE_URL = "/workbase/resource/jsLibs/resources/images/default/s.gif";

			var navigation = new Navigation();
			var toolbar = navigation.init();
			var clock = new Ext.Toolbar.TextItem('');
			var timeValue = 0;
			// 同步服务器时间
			UpmrAjaxAction.sycDateTime(function(callData) {
						timeValue = parseInt(callData, 10);
						if (Ext.fly(clock.getEl()) !== null) {
							var task = {
								run : function() {
									var dd = new Date(timeValue);
									Ext.fly(clock.getEl()).update(dd.format('y-m-d G:i:s'));
									timeValue += 1000;
								},
								interval : 1000
							}
							Ext.TaskMgr.start(task);
						}
					});

			var statusbar = new Ext.ux.StatusBar({
						defaultText : '：：' + loginName + '：您好，系统推荐使用[FireFox、Chrome]，字体设置为 [微软雅黑] 便于达到最佳体验',
						id : 'right-statusbar',
						statusAlign : 'left',
						items : ['-', '服务器时间：', clock, '-']
					});

			var topContain = new TopContain();
			topContain.init('p_110', toolbar);
			var top_panle = topContain.getContainer();

			var centerContain = new CenterContain();
			centerContain.init(statusbar);
			var cen_panle = centerContain.getPanel();

			cen_panle.viewport = new Ext.Viewport({
						layout : 'border',
						items : [top_panle, cen_panle]
					});

		});