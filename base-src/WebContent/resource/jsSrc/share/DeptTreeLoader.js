/*
 * ! Ext JS Library 3.3.1 Copyright(c) 2006-2010 Sencha Inc.
 * licensing@sencha.com http://www.sencha.com/license
 */
Ext.extend.DeptTreeLoader = Ext.extend(Ext.ux.tree.XmlTreeLoader, {
			processAttributes : function(attr) {
				if (attr.leaf === '1') {
					attr.text = attr.value;
					attr.order = attr.order;
					attr.id = attr.id;
					attr.loaded = true;
					attr.iconCls = 'tree_node_father';
					attr.expanded = true;
				} else {
					attr.text = attr.value;
					attr.id = attr.id;
					attr.order = attr.order;
					attr.iconCls = 'tree_node_leaf';
					attr.leaf = true;
				}
			}
		});

Ext.extend.DeptCheckedTreeLoader = Ext.extend(Ext.ux.tree.XmlTreeLoader, {
			processAttributes : function(attr) {
				if (attr.leaf === '1') {
					attr.text = attr.value;
					attr.id = attr.id;
					attr.loaded = true;
					attr.expanded = true;
					attr.iconCls = 'tree_node_father';
					attr.checked = false;
				} else {
					attr.text = attr.value;
					attr.id = attr.id;
					attr.iconCls = 'tree_node_leaf';
					attr.leaf = true;
					attr.checked = false;
				}
			}
		});

function xmlTreeLoader() {
	var loader = new Ext.extend.DeptTreeLoader({
				dataUrl : '/workbase/console/dept/tree.xml'
			});
	return loader;
}

function xmlTreeCheckLoader() {
	var loader = new Ext.extend.DeptCheckedTreeLoader({
				dataUrl : '/workbase/console/dept/tree.xml'
			});
	return loader;
}