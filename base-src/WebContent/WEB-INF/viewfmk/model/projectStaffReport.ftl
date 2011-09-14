<#import "/macro/inspiron.ftl" as inspiron />
<#import "/macro/datamodel.ftl" as dataMdoel />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<@dataMdoel.LoginInfo />
	<@inspiron.ExtJSLib />
	<@dataMdoel.OptionsList optionData />
	<@inspiron.projPersonReportJsLib />
	<@inspiron.BaseJsLib />
</head>
<body>
</body>
</html>