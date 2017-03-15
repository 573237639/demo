<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var number = '${number}';
$(function(){
	$('#w').window({    
		title:"来电- "+number,
	    width:850,    
	    height:600, 
	    padding:10,
	    modal:true   
	});  
	var pCallID = "111";
	var pCalled = "15221172648";
	var pSkill = 0;
	$('#w').window('refresh', '${ctx}/tb/call/call-index?callid='+pCallID+'&number='+pCalled+'&pSkill='+pSkill);  
});
</script>
	<div id="w"></div>