<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<html>
<head>
<title>意见栏</title>
</head>
<body>
	<div class="easyui-panel" style="padding:4px;width:100%;height:auto;" >
		<div style="display:none;"><input class="easyui-combobox" id="addtype" 
		  					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/repository_type'"></div>
		<h5>意见栏</h5>
		<table class="easyui-datagrid" id="listGrid" style="width:100%;height:auto;">
		
		</table>
	</div>
</body>
<script>
var loadGrid = function(){
	var repType = $("#addtype").combobox("getData");
	$("#listGrid").datagrid({
	    fitColumns:true,
	    singleSelect:true,
	    pagination:true,
	    rownumbers:true,
	    url:"${ctx}/sys/repository/getSuggests",
	    columns:[[
  	  		{field:'varOpinionContent',title:'意见',width:200,align:"center"},
  	  		{field:'varOpinionType',title:'知识类型',width:100,align:"center",formatter:function(value,row,index){
  	  			if(value==undefined) return;
  	  			for(var i =0;i<repType.length;i++){
  	  				if(value == repType[i].value){
  	  					return repType[i].label;
  	  				}
  	  			}
  	  		}},
  	  		{field:'varOpinionTitle',title:'知识标题',width:100,align:"center"},
  	  		{field:'varCreaterName',title:'提出人',width:100,align:"center"},
  	  		{field:'gmtCreatedString',title:'意见提出时间',width:100,align:"center"}
  		]], 
  		loader:function(param,success,error){
	    	$.ajax({
	            url:"${ctx}/sys/repository/getSuggests",
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	                success(data);
	            }
	        })
  		}
	});
}

setTimeout(loadGrid,1000);


//查询
function queryList(){
	$("#listGrid").datagrid('load');
	return;
}
</script>
</html>

