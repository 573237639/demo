<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<div id="sysRoleTabs" class="easyui-tabs" tabPosition="left" style="width:98%;height:90%;margin:auto;padding-top:10px;">
	<div title="角色列表" class="tab">
		<div class="easyui-panel" style="padding:4px;width:100%;height:100%;" >
			<table class="easyui-datagrid" id="listGrid" style="width:100%;height:98%;">
			
			</table>
		</div>
	</div>
	<div title="角色添加" class="tab"  data-options="href:'${ctx}/sys/role/roleInfoAE'">
	
	</div>
</div>
<script>
var windowH_ = $(window).height();
var windowW_ = $(window).width();
$('#sysRoleTabs').tabs({
	height: windowH_,
	//清空表单
	onUnselect : function(title,index){
		var tab = $('#sysRoleTabs').tabs("getTab",index);  
		if(index == 1){
			tab.panel({
				href : '${ctx}/sys/role/roleInfoAE'
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
	    url:"${ctx}/sys/role/roleListData",
	    columns:[[
  	  		{field:'id',title:'ID',width:100},
  	  		{field:'name',title:'角色名称',width:100,align:"center"},
  	  		{field:'remarks',title:'描述',width:100,align:"center"},
  	  		{field:'isDeleted',title:'状态',width:100,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
  	  			return value == 0 ? "<font style='color:green;'>正常</font>" : "<font style='color:gray;'>已删除</font>";
  	  		}},
  	  		{field:'dis',title:'操作',width:130,align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(!row.isDeleted){
  	  				btns += '<a href="javascript:editRole('+row.id+');" class="lkbtn">修改</a>&nbsp;'
  	  	  			btns += '<a href="javascript:deleteRole('+row.id+');" class="lkbtn">删除</a>&nbsp;'
  	  			}
				return btns;
  	  		}}
  		]], 
  		onLoadSuccess : function(){
  			$('.lkbtn').linkbutton({});
  		}
	});
}
setTimeout(loadGrid,1000);

/**
 * 修改角色信息
 * rid  角色ID
 */
function editRole(rid){
	
	var tab = $('#sysRoleTabs').tabs('getTab',1);  // 获取选择的面板
	tab.panel({
		href : "${ctx}/sys/role/roleInfoAE?rid=" + rid
	})
	$('#sysRoleTabs').tabs('select', 1);

}
/**
 * 删除角色信息    --逻辑删除
 * rid  角色ID
 */
function deleteRole(rid){
	$.messager.confirm("确认", "确认删除?", function (r) {  
        if (r) {  
        	$.ajax({
    	        url:"${ctx}/sys/role/delete",
    	        data: {rid : rid},
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
function reloadCur(){
	var tab = $('#sysRoleTabs').tabs('getTab',1);  // 获取选择的面板
	var href = tab.panel("options").href;
	tab.panel({
		href : href
	});
	tab.panel('refresh');
}
</script>

