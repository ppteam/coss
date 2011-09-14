<#-- ======================================================= -->
<#-- ========= Data Modle for javascript by macro ========== -->
<#-- ======================================================= -->
<#macro OptionsList optList>
	<script type="text/javascript" language="javascript">
		var optionStruts = [{name : 'regValue',type : 'string'}, {name : 'displayValue',type : 'string'},{name : 'addVal',type : 'int'}];
	<#list optList as opts>
		var ${opts.storName} = [<#list opts.items as opt>['${opt.regValue}','${opt.displayValue}',${opt.leafNode}]<#if opt_has_next>,</#if></#list>]; 
	</#list>
	</script>
</#macro>

<#macro LoginInfo>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${Session.LoginUser.KEY_TITLE}</title>
	<script type="text/javascript" language="javascript">
	<#if Session.LoginUser?exists>
		var loginName = '${Session.LoginUser.KEY_USERNAME}';
	<#else>
		var loginName = '匿名用户';
	</#if>
		var server_date = '${Session.LoginUser.KEY_TODAY?string("yyyy-MM-dd")}';
	</script>
</#macro>

<#macro ComboxItems comboxs>
	<script type="text/javascript" language="javascript">
	<#list comboxs as cmbs>
		var ${cmbs.storName} = [<#list cmbs.items as cmb>{boxLabel:'${cmb.displayValue}',id:'${cmb.regValue}'}<#if cmb_has_next>,</#if></#list>]; 
	</#list>
	</script>
</#macro>