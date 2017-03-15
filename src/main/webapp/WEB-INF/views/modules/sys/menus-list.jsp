<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<html>
<head>
<title>菜单管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
</head>
<body class="easyui-layout" id="easyui-layout" style="width:98%;height:90%;margin:auto;margin-top:10px;">   
	<div data-options="region:'north',title:'菜单管理',split:true,collapsible:false" style="height:0px;"></div>   
    <div data-options="region:'west',split:true" style="width:15%;padding:10px;">
    	<ul id="menuTree"></ul> 
    	 
    </div>   
    <div data-options="region:'center'" style="padding:5px;">
    	   	
    </div>  
</body>  
<script>
var loadMenuTree = function(){
	$('#menuTree').tree({    
	    url: "${ctx}/sys/menus/authorMenuTree",    
		method:'get',//请求的方式，默认是post
		animate:false,//在树展开或关闭时是否开启动画效果。默认false
		checkbox:false,//是否开启复选框，默认false
		cascadeCheck:true,//是否开启级联选择，默认true
		onlyLeafCheck:false,//定义是否只有叶子节点显示复选框,默认false
		lines:false,//定义是否显示线,默认false
		dnd:false,//定义是否开启拖放，默认false
		formatter:function(node){
			//对文字内容进行格式化
			return node.text;
		},
		onSelect:function(node){
			var mid = 0;
			if(node==null || node.id == -1){
				mid = 0;
			}else{
				mid = node.id;
			}
			var cpanel = $("#easyui-layout").layout("panel","center");
			cpanel.panel({
				href : "${ctx}/sys/menus/menuinfo?id="+mid
			});
		},
		onLoadSuccess : function(node, data){
			$('#menuTree').tree("collapseAll");
			//获取根节点
		　　    var rooNode = $("#menuTree").tree('getRoot');
		　　    $("#menuTree").tree('expand',rooNode.target); 
		　　    var redPid = ${redPid};
		　　    if(redPid != -1){
		　　    	var rooNode1 = $("#menuTree").tree('find',redPid);
		　　   		$("#menuTree").tree('select',rooNode1.target);
				$("#menuTree").tree('expand',rooNode1.target); 
		　　    }
		}
	});  
}
$(function(){
	loadMenuTree();
});

/**
 * 切换菜单
 */
function menuInfoClick(href,mid){
	mid = mid == "0" ? -1 : mid;
    var rooNode = $("#menuTree").tree('find',mid);
    if(undefined != rooNode)
	$("#menuTree").tree('select',rooNode.target);
}
//菜单操作
function menuOptClick(href){
	var cpanel = $("#easyui-layout").layout("panel","center");
	cpanel.panel({
		href : href
	});
}
//菜单删除
function menuDelClick(href){
	$.messager.confirm("确认", "确认删除?", function (r) {  
        if (r) {  
        	window.location.href = href;
        } 
    });
}
</script>
</html>

