<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<style>
		.reportList {
			list-style-type: none;
			padding: 20px 40px;
		}
		.reportList li {
			display: block;
			width: 310px;
			margin: 15px;
			padding: 15px 0px 15px 30px;
			text-align: left;
			background: #A8C8F7;
		}
		.reportList li a {
			display: block;
			width: 100%;
			font-weight: bold;
			text-decoration: none;
			color: #333;
		}
		.reportList li:hover {
			color: #0E48B6;
			box-shadow: 1px 1px 5px #444;
		}
		.reportList li a:hover {
			color: #0E48B6;
		}
	</style>
	<script type="text/javascript">
	function openwin(url) { 
		window.open (url, "newwindow", "height=772, width=1050, top=200,left=200 , toolbar =no, menubar=no, scrollbars=no, resizeable=yes, location=no, status=no") 
		} 
	</script>
</head>
<body>
	<ul class="reportList">
		<li><a href="#" onclick="openwin('${GD_AGENT_SATISFACTION }');">广东12348坐席满意度统计表</a></li>
		<li><a  href="#" onclick="openwin('${GD_CALL_SATISFACTION }');">广东12348法律服务满意度报表</a></li>
	</ul>
</body>
</html>