<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style>
.textBoxW{width:150px;}
#listQuery td{font-weight:700;font-size:12px;padding-left:20px;}
</style>
<div id="sysUserTabs" class="easyui-tabs" tabPosition="left" style="width:98%;height:90%;margin:auto;padding-top:10px;">
	<div title="用户列表" class="tab">
		<div class="easyui-panel" style="padding:4px;width:100%;height:100%;" >
			<div class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom:10px;background:#fafafa;">
				<form id="listQuery" class="easyui-form margin-top20" method="post" data-options="novalidate:true">
					<input type="hidden" id="page" name="page"/>
					<input type="hidden" id="rows" name="rows">
					话务工号:&nbsp;<input class="easyui-textbox textBoxW" name="hwNum" id="hwNum"/>&nbsp;&nbsp;
					姓名:&nbsp;<input class="easyui-textbox textBoxW" name="realname" id="realname"/>&nbsp;&nbsp;<br/><br/>
					
					坐席类型:&nbsp;<input class="easyui-combobox textBoxW" id="seats" name="seats" editable="false"
	   					data-options="valueField:'value',width:150,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_seats',required:true">&nbsp;&nbsp;
					角色:&nbsp;<input class="easyui-combobox textBoxW" id="role" name="role" editable="false"
	   					data-options="valueField:'value',width:150,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_role',required:true">&nbsp;&nbsp;
					所属分组:&nbsp;<input class="easyui-combobox textBoxW" id="ingroup" name="ingroup" editable="false"
	   					data-options="valueField:'value',width:150,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_ingroup',required:true">&nbsp;<br/><br/>
					<a href="javascript:queryList();" class="easyui-linkbutton margin-left20">查询</a>&nbsp;&nbsp;
					<a href="javascript:paramReset();" class="easyui-linkbutton margin-left20">条件重置</a>&nbsp;&nbsp;
					<permission:hasPermission action="sys/user/resetPwd">
						<a href="#" class="easyui-linkbutton margin-left20" id="pwdReset"  onclick="pwdResetEvent()" data-options="disabled:true">重置密码</a>&nbsp;
					</permission:hasPermission>
				</form>
			</div>
			<table class="easyui-datagrid" id="listGrid" style="width:100%;height:80%;">
			
			</table>
		</div>
	</div>
	<div title="用户添加" class="tab"  data-options="href:'${ctx}/sys/user/userInfoAE'">
	
	</div>
</div>

<script>
var windowH_ = $(window).height();
var windowW_ = $(window).width();
$('#sysUserTabs').tabs({
	height: windowH_,
	//清空表单
	onUnselect : function(title,index){
		var tab = $('#sysUserTabs').tabs("getTab",index);  
		if(index == 1){
			tab.panel({
				href : '${ctx}/sys/user/userInfoAE'
			})
		}
	},
	onSelect : function(title,index){
	}
});
var loadGrid = function(){
	$("#listGrid").datagrid({
	    fitColumns:true,
	    singleSelect:false,
	    pagination:true,
	    rownumbers:false,
	    url:"${ctx}/sys/user/userListData?"+$("#listQuery").serialize(),
	    columns:[[
	        {field:'id',checkbox:true },
  	  		{field:'hwNum',title:'话务工号',width:80},
  	  		{field:'realname',title:'姓名',width:80,align:"center"},
  	  		{field:'sex',title:'性别',width:50,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
  	  			return value == 1 ? "男" : value == 3 ? "女" : "";
  	  		}},
  	  		{field:'seats',title:'坐席类型',width:120,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
	  	  		var comboxData = $("#seats").combobox("getData");
				for(var data in comboxData){
					if(comboxData[data].value == value){
						return comboxData[data].label;
					}
				} 
  	  		}},
  	  		{field:'ingroup',title:'所属分组',width:120,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
	  	  		var comboxData = $("#ingroup").combobox("getData");
				for(var data in comboxData){
					if(comboxData[data].value == value){
						return comboxData[data].label;
					}
				}
  	  		}},
  	  		{field:'role',title:'角色',width:50,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
	  	  		var comboxData = $("#role").combobox("getData");
				for(var data in comboxData){
					if(comboxData[data].value == value){
						return comboxData[data].label;
					}
				}
  	  		}},
  	  		{field:'lastLoginTimeString',title:'最后登录时间',width:100,align:"center"},
  	  		{field:'extNum',title:'分机号',width:100,align:"center"},
  	  		{field:'isDeleted',title:'状态',width:50,align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
  	  			return value == 0 ? "<font style='color:green;'>正常</font>" : "<font style='color:gray;'>已删除</font>";
  	  		}},
  	  		{field:'creator',title:'操作',width:'100',align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(!row.isDeleted){
  	  				btns += '<a href="javascript:editUser('+row.id+');" class="lkbtn">修改</a>&nbsp;'
  	  	  			btns += '<a href="javascript:deleteUser('+row.id+');" class="lkbtn">删除</a>&nbsp;'
  	  			}
				return btns;
  	  		}}
  		]], 
  		onLoadSuccess : function(){
  			$('.lkbtn').linkbutton({});
  		},
  		onCheck : function(){
  			pwdResetBtn(false);
  		},
  		onCheckAll : function(){
  			pwdResetBtn(false);
  		},
  		onUncheck : function(){
  			var checked = $("#listGrid").datagrid('getChecked');
  			pwdResetBtn(checked.length == 0 ? true : false);
  		},
  		onUncheckAll : function(){
  			pwdResetBtn(true);
  		},
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/sys/user/userListData",
	            data:$("#listQuery").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	                success(data);
	                pwdResetBtn(true);
	            }
	        })
  		}
	});
}
setTimeout(loadGrid,1000);

//判断是否可以进行密码修改
function pwdResetBtn(disable){
	$("#pwdReset").linkbutton({
		disabled:disable,
		onClick : pwdReset
	});
}
//重置密码
function pwdResetEvent(){
	var checkedUser = $("#listGrid").datagrid('getChecked');
	$.messager.confirm("确认", "是否重置所选工号的密码?", function (r) {  
        if (r) {  
        	var uids = [];
        	for(var i=0;i<checkedUser.length;i++){
        		uids.push(checkedUser[i].id);
        	}
        	$.ajax({
    	        url:"${ctx}/sys/user/resetPwd",
    	        data: {uids : uids.toString()},
    	        type:"post",
    	        dataType:"json",
    	        jsonpCallback:"callback",
    	        success: function(data){
    	        	if(data.code == 0){
    		        	$.messager.alert('提示','重置密码成功!');
    		        }else{
    		        	$.messager.alert('提示','重置密码失败!');
    		        } 
    	        	pwdResetBtn(true);
		        	queryList();
    	        }
    	    })
        } 
    });  
}

/**
 * 修改用户信息
 * uid  用户ID
 */
function editUser(uid){
	var tab = $('#sysUserTabs').tabs('getTab',1);  // 获取选择的面板
	tab.panel({
		href : "${ctx}/sys/user/userInfoAE?uid=" + uid
	})
	$('#sysUserTabs').tabs('select', 1);

}
/**
 * 删除用户信息    --逻辑删除
 * uid  用户ID
 */
function deleteUser(uid){
	$.messager.confirm("确认", "确认删除?", function (r) {  
        if (r) {  
        	$.ajax({
    	        url:"${ctx}/sys/user/delete",
    	        data: {uid : uid},
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
	/* $("#listGrid").datagrid({
	    url:"${ctx}/sys/user/userListData?"+$("#listQuery").serialize()
	}); */
	$("#listGrid").datagrid('load');
	return;
}
//查询条件重置
function paramReset(){
	$("#listQuery").form('reset');
}
//重置
function reloadCur(){
	var tab = $('#sysUserTabs').tabs('getTab',1);  // 获取选择的面板
	var href = tab.panel("options").href;
	tab.panel({
		href : href
	});
	tab.panel('refresh');
}
</script>
