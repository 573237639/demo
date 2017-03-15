<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="${ctxStatic}/easyui/jquery.min.js"></script>
<script src="${ctxStatic}/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="${ctxStatic}/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>

<script type="text/javascript" src="${ctxStatic}/easyui/ext/extBrowser.js" charset="utf-8"></script>
<!-- 引入bootstrap样式 -->
<link href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${ctxStatic}/easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" type="text/css">
<link rel="stylesheet" href="${ctxStatic}/easyui/themes/icon.css" type="text/css">
<script type="text/javascript" src="${ctxStatic}/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/easyui/plugins/jquery.layout.js" charset="utf-8"></script>

<link rel="stylesheet" href="${ctxStatic}/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<!-- 扩展EasyUI -->
<script type="text/javascript" src="${ctxStatic}/easyui/ext/extEasyUI.js" charset="utf-8"></script>

<!-- 扩展EasyUI Icon -->
<link rel="stylesheet" href="${ctxStatic}/easyui/ext/style/extEasyUIIcon.css" type="text/css">

<!-- 扩展jQuery -->
<script type="text/javascript" src="${ctxStatic}/easyui/ext/extJquery.js" charset="utf-8"></script>
<script>
var  ctx="${ctx}";
var  ctxstaic="${ctxStatic}";
//防止页面后退
$(document).keydown(function(e){    
	   var target = e.target ;    
	   var tag = e.target.tagName.toUpperCase();    
	   if(e.keyCode == 8){    
		   if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){    
			     if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){    
			      	return false ;    
			     }   
			     return true ; 
		   }else{    
		     	return false ;    
		   }    
	   }else if(e.keyCode == 116){
		   return false ;
	   }    
}); 
</script>