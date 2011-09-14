<#-- ======================================================= -->
<#-- ========= javascript and css config by macro ========== -->
<#-- ======================================================= -->
<#macro ExtJSLib>
	<link rel="Shortcut Icon" href="/workbase/clock.ico" />
	<link  rel="Bookmark" href="/workbase/clock.ico">
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/statusbar/css/statusbar.css" />
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/customIco.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ext-all.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/adapter/local/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/statusbar/StatusBar.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/engine.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/util.js"></script>
</#macro>

<#macro BaseJsLib>
	<script type='text/javascript' language="javascript" src='${ajaxBase}UpmrAjaxAction.js'></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/base/Navigation.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/base/TopContain.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/base/Console.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}DutyAjaxAction.js'></script>
</#macro>

<#macro WelcomeJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/welcome/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/welcome/CenterContain.js"></script>
</#macro>

<#macro DownloadLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/download/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/download/CenterContain.js"></script>
</#macro>

<#macro UploadJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/fileuploadfield/css/fileuploadfield.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/fileuploadfield/FileUploadField.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/upload/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/upload/CenterContain.js"></script>
</#macro>

<#macro DictJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dict/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dict/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro CkrlJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckrulemgr/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckrulemgr/CenterContain.js"></script>
</#macro>

<#macro CkDtlJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckdetail/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckdetail/CenterContain.js"></script>
</#macro>

<#macro CkStsJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckstats/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/ckstats/CenterContain.js"></script>
</#macro>

<#macro CkAgainJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/recheck/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/recheck/CenterContain.js"></script>
</#macro>

<#macro PCkDtlJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/pckdetail/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/pckdetail/CenterContain.js"></script>
</#macro>

<#macro HlidJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/holiday/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/holiday/CenterContain.js"></script>
</#macro>


<#macro PwrltJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowrlist/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowrlist/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro ProJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/project/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/project/ProjectMembers.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/project/MilestoneUI.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/project/CenterContain.js"></script>
</#macro>

<#macro PwrJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkrpt/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkrpt/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ProwrAjaxHandler.js'></script>
</#macro>

<#macro PptJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkipt/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkipt/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ProwrAjaxHandler.js'></script>
</#macro>

<#macro PedJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowred/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowred/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ProwrAjaxHandler.js'></script>
</#macro>

<#macro PkedJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkked/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkked/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ProwrAjaxHandler.js'></script>
</#macro>

<#macro PelJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkrpt/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/prowkrpt/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ProwrAjaxHandler.js'></script>
</#macro>

<#macro DeptJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/XmlTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/share/DeptTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dept/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dept/CenterContain.js"></script>
</#macro>

<#macro UserJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/XmlTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/share/DeptTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/user/UserModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/user/UserDialog.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/user/CenterContain.js"></script>	
</#macro>

<#macro UrmJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/urm/UserModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/urm/UserDialog.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/urm/CenterContain.js"></script>	
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/XmlTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/share/DeptTreeLoader.js"></script>
</#macro>

<#macro RunJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/XmlTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/share/DeptTreeLoader.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/runing/UserModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/runing/CenterContain.js"></script>	
</#macro>

<#macro SrcJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/source/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/source/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro AuthorJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/author/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/author/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro RoleJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/roles/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/roles/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro staffMonthReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/staffmonthforms/StatisticsModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/staffmonthforms/CenterContain.js"></script>
</#macro>

<#macro projPersonReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>	
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/projpersonforms/ProjPersonModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/projpersonforms/CenterContain.js"></script>
</#macro>

<#macro projMonthReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/projmonthforms/ProjMonthModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/projmonthforms/CenterContain.js"></script>
</#macro>

<#macro DailyExportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dailyExport/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dailyExport/CenterContain.js"></script>
</#macro>

<#macro WorkDetailJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/workDetail/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/workDetail/CenterContain.js"></script>
</#macro>

<#macro OverWorkJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/overWork/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/overWork/CenterContain.js"></script>
</#macro>

<#macro staffInfoReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/staffinfoforms/StaffInfoModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/staffinfoforms/CenterContain.js"></script>
</#macro>

<#macro perweekReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/pereekforms/perweekModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/pereekforms/CenterContain.js"></script>
</#macro>

<#macro DutycltJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dutyclt/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/dutyclt/CenterContain.js"></script>
</#macro>

<#macro HappyDayJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/happyday/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/happyday/CenterContain.js"></script>
</#macro>

<#macro addrBookReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/addrbookforms/AddrBookModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/addrbookforms/CenterContain.js"></script>
</#macro>

<#macro ProMemberJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/promember/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/promember/CenterContain.js"></script>
</#macro>

<#macro WcAssitJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/wcass/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/wcass/CenterContain.js"></script>
</#macro>

<#macro projweekReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>	
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/projweekforms/CenterContain.js"></script>
</#macro>
<#macro weekReportJsLib>
	<link rel="stylesheet" type="text/css" href="${resBase}jsLibs/ux/css/RowEditor.css" />
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowEditor.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsLibs/ux/RowExpander.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/reportWeek/CenterContain.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/reportWeek/WeekDialog.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/reportWeek/WeekModel.js"></script>
	<script type='text/javascript' language="javascript" src='${ajaxBase}ArsdRemoteAction.js'></script>
</#macro>

<#macro DailyReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/report/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/report/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src="${ajaxBase}IRemoteDailyReportAction.js"></script>
</#macro>

<#macro ViewDailyReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/view_report/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/view_report/CenterContain.js"></script>
</#macro>

<#macro EWeekReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/eweekreport/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/eweekreport/CenterContain.js"></script>
	<script type='text/javascript' language="javascript" src="${ajaxBase}IWeekReportAction.js"></script>
</#macro>

<#macro ViewWeekReportJsLib>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/view_weekreport/DataModel.js"></script>
	<script type="text/javascript" language="javascript" src="${resBase}jsSrc/view_weekreport/CenterContain.js"></script>
</#macro>

