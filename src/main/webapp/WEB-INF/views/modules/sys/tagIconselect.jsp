<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
    <title>图标选择</title>
    <meta http-equiv="X-UA-Compatible" content="IE=9" >
    <link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css">
    <script src="${ctxStatic}/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <style type="text/css">
		.the-icons {padding:25px 10px 15px;list-style:none;}
		.the-icons li {float:left;width:22%;line-height:25px;margin:2px 5px;cursor:pointer;}
		.the-icons i {margin:1px 5px;} .the-icons li:hover {background-color:#efefef;}
        .the-icons li.active {background-color:#0088CC;color:#ffffff;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#icons li").click(function(){
	    		$("#icons li").removeClass("active");
	    		$("#icons li i").removeClass("icon-white");
	    		$(this).addClass("active");
	    		$(this).children("i").addClass("icon-white");
	    		$("#icon").val($(this).text());
	    	});
	    	$("#icons li").each(function(){
	    		if ($(this).text()=="${value}"){
	    			$(this).click();
	    		}
	    	});
	    	$("#icons li").dblclick(function(){
	    		$.jBox.getBox().find("button[value='ok']").trigger("click");
	    	});
	    });
    </script>
</head>
<body>
<input type="hidden" id="icon" value="${value}" />
<ul class="the-icons clearfix" id="icons">
    <li><i class="icon-nav icon-nav0"></i>icon-nav0</li>
    <li><i class="icon-nav icon-nav1"></i>icon-nav1</li>
    <li><i class="icon-nav icon-nav2"></i>icon-nav2</li>
    <li><i class="icon-nav icon-nav3"></i>icon-nav3</li>
    <li><i class="icon-nav icon-nav4"></i>icon-nav4</li>
</ul>
</body>