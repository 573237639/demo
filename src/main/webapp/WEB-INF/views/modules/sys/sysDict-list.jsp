<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<html>
<head>
<title>字典管理</title>
</head>
<body>
<div id="sysDictTabs" class="easyui-tabs" tabPosition="left" style="width:98%;height:90%;margin:auto;padding-top:10px;">
	<div title="字典列表" class="tab">
		<div class="easyui-panel" style="padding:4px;width:100%;height:100%;" >
			<div class="easyui-panel" style="padding:4px;width:100%;height:20%;" >
				<form id="dictForm" method="post">  
					<br/>
					<font style="font-size:12px;font-weight:700;">类型:</font>
					<input class="easyui-combobox" id="type" name="type" value="${type}" editable="false"
	   					data-options="valueField:'type',width:200,textField:'type',url:'${ctx}/sys/dict/findDictType'">
					<font style="font-size:12px;font-weight:700;">描述:</font>
					<input class="easyui-textbox" id="description" name="description" style="width:200px"> 
					<br/>
					<br/>
					<a id="searchDict" href="javascript:void(0)" onclick="queryList()" class="easyui-linkbutton" >查询</a>&nbsp;&nbsp;  
					<a id="reset" href="javascript:void(0)" onclick="resetSearch()" class="easyui-linkbutton" >重置</a>  
				</form>
			</div>
			<table class="easyui-datagrid" id="listGrid" style="width:100%;height:78%;">
			
			</table>
		</div>
	</div>
	<div title="字典添加" class="tab"  data-options="href:'${ctx}/sys/dict/dictInfoAE?opt=-1'">
	
	</div>
</div>
</body>
<script>
var windowH_ = $(window).height();
var windowW_ = $(window).width();
$('#sysDictTabs').tabs({
	height: windowH_,
	//清空表单
	onUnselect : function(title,index){
		var tab = $('#sysDictTabs').tabs("getTab",index);  
		if(index == 1){
			tab.panel({
				href : '${ctx}/sys/dict/dictInfoAE?opt=-1'
			})
		}
	},
	onSelect : function(title,index){
	}
});
var loadGrid = function(){
	$("#listGrid").datagrid({
	    fitColumns:true,
	    singleSelect:true,
	    pagination:true,
	    rownumbers:false,
	    url:"${ctx}/sys/dict/roleListData",
	    columns:[[
  	  		{field:'value',title:'键值',width:100},
  	  		{field:'label',title:'标签',width:100,align:"center"},
  	  		{field:'type',title:'类型',width:100,align:"center"},
  	  		{field:'description',title:'描述',width:100,align:"center"},
  	  		{field:'sort',title:'排序',width:100,align:"center"},
  	  		{field:'isDeleted',title:'状态',width:100,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
  	  			return value == 0 ? "<font style='color:green;'>正常</font>" : "<font style='color:gray;'>已删除</font>";
  	  		}},
  	  		{field:'id',title:'操作',width:130,align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(!row.isDeleted){
  	  				btns += '<a href="javascript:editDict('+row.id+',1);" class="lkbtn">修改</a>&nbsp;'
  	  	  			btns += '<a href="javascript:deleteDict('+row.id+');" class="lkbtn">删除</a>&nbsp;'
  	  	  			btns += '<a href="javascript:editDict('+row.id+',0);" class="lkbtn">添加值</a>&nbsp;'
  	  			}
				return btns;
  	  		}}
  		]], 
  		onLoadSuccess : function(){
  			$('.lkbtn').linkbutton({});
  		},
  		loader:function(param,success,error){
  			param.type = $("#type").combobox("getValue");
  			param.description = $("#description").textbox("getValue");
	    	$.ajax({
	            url:"${ctx}/sys/dict/dictListData",
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

/**
 * 修改字典信息
 * rid  字典ID
 */
function editDict(rid,type){
	
	var tab = $('#sysDictTabs').tabs('getTab',1);  // 获取选择的面板
	tab.panel({
		href : "${ctx}/sys/dict/dictInfoAE?did=" + rid + "&opt=" + type
	})
	$('#sysDictTabs').tabs('select', 1);

}
/**
 * 删除字典信息    --假删除
 * rid  字典ID
 */
function deleteDict(rid){
	$.messager.confirm("确认", "确认删除?", function (r) {  
        if (r) {  
        	$.ajax({
    	        url:"${ctx}/sys/dict/delete",
    	        data: {id : rid},
    	        type:"post",
    	        dataType:"json",
    	        jsonpCallback:"callback",
    	        success: function(data){
    	        	if(data.code == 0){
    		        	$.messager.alert('提示','删除成功!');
    		        	queryList();
    		        }else{
    		        	$.messager.alert('提示','删除失败!');
    		        } 
    	        }
    	    })
        } 
    });
}

//查询
function queryList(){
	$("#listGrid").datagrid('load');
	return;
}

//重置
function resetSearch(){
	$('#dictForm').form('reset');
	$("#type").combobox('setValue', '');
}
//重置form
function reloadCur(){
	var tab = $('#sysDictTabs').tabs('getTab',1);  // 获取选择的面板
	var href = tab.panel("options").href;
	tab.panel({
		href : href
	});
	tab.panel('refresh');
}
</script>
</html>

