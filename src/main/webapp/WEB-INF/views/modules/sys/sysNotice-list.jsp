<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" src="${ctxStatic}/easyui/jquery-easyui-datagridview/datagrid-detailview.js"></script>
<script>
var loadGrid = function(){
	$("#notice-datagrid").datagrid({
		striped:true,
  		checkOnSelect:false ,
  		fitColumns:true,
  		remoteSort:false,
  		singleSelect:true,
  		nowrap:false,
	    columns:[[
  	  		{field:'vacTitle',title:'标题',width:'40%'},
//   	  		{field:'textContent',title:'内容',align:"left",width:'30%',height:20,sortable:true,formatter:function(value,row,index){
//   	  			var btns = "";
//   	  			if(value.indexOf("</p>") > -1){
//   	  				btns += value.substring(0,value.indexOf("</p>"))  ;
//   	  	            btns += '<a href="javascript:viewNotice('+row.id+');" class="lkbtn"><font style="color:red">>>>>>点击详情</font></a>&nbsp;'
//   	  			}else if(value.indexOf("<img") > -1){
//   	  				btns += value.substring(0,value.indexOf("<img"))  ;
//   	  	            btns += '<a href="javascript:viewNotice('+row.id+');" class="lkbtn"><font style="color:red">>>>>>点击详情</font></a>&nbsp;'
//   	  			}else	if(value.length > 30){
//   	  				btns += value.substring(0,30) ;
//     	  	        btns += '<a href="javascript:viewNotice('+row.id+');" class="lkbtn"><font style="color:red">>>>>>点击详情</font></a>&nbsp;'
//   	  			}else{
//   	  				btns += value;
//   	  			}
//   	  			return btns;
//   	  		}},
  	  		{field:'isDeleted',title:'状态',width:'30%',align:"center",formatter:function(value,row,index){
  	  			if(value == undefined) return;
  	  			return value == 0 ? "<font style='color:green;'>正常</font>" : "<font style='color:gray;'>已删除</font>";
  	  		}},
  	  		{field:'id',title:'操作',width:'30%',align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(!row.isDeleted){
  	  				btns += '<a href="javascript:viewNotice('+row.id+');" class="lkbtn"><font style="color:red">查看</font></a>&nbsp;'
  	  				btns += '<permission:hasPermission action="sys/notice/sysNotice-edit"><a href="javascript:editNotice('+row.id+');" class="lkbtn"><font style="color:red">修改</font></a></permission:hasPermission>&nbsp;'
  	  	  			btns += '<permission:hasPermission action="sys/notice/deleteNotice"><a href="javascript:deleteNotice('+row.id+');" class="lkbtn"><font style="color:red">删除</font></a></permission:hasPermission>&nbsp;'
  	  			}
				return btns;
  	  		}}
  		]],
  		loader:function(param,success,error){
  			param.vacTitle = $("#vacTitle").textbox("getValue");
//   			param.textContent = $("#textContent").textbox("getValue");
	    	$.ajax({
	            url:"${ctx}/sys/notice/noticeListData",
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
</script>

<script type="text/javascript">
	//点击搜索	
	function searchNotice() {
		var validate = $("#notice-from").form("validate");
		if(!validate){
// 			$.messager.alert("消息提醒","请检查你输入的数据!","warning");
			return;
		} else{
			$("#searchNotice").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#notice-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchNotice").linkbutton('enable');
		}
	}
	
	function addNotice(){
		window.parent.window.newTab('',"/sys/notice/sysNotice-add","新增公告:","icon-save");
	}

	function editNotice(id){
		window.parent.window.newTab(id,"/sys/notice/sysNotice-edit","修改公告:","icon-save");
	}
	
	function viewNotice(id){
		window.parent.window.newTab(id,"/sys/notice/sysNotice-view","公告详情"+id,"icon-save");
	}
	
	function deleteNotice(id){
		
		$.messager.confirm("确认", "确认删除?", function (r) {  
	        if (r) {  
	        	$.ajax({
	    	        url:"${ctx}/sys/notice/deleteNotice",
	    	        data: {id : id},
	    	        type:"post",
	    	        dataType:"json",
	    	        jsonpCallback:"callback",
	    	        success: function(data){
	    	        	if(data.code == 0){
	    		        	$.messager.alert('提示','删除成功!');
	    		        	$("#notice-datagrid").datagrid('load');
	    		        }else{
	    		        	$.messager.alert('提示','删除失败!');
	    		        } 
	    	        }
	    	    })
	        }
	    });
		
		
	}
	
	
	
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="notice-from" method="post" action=""  modelAttribute="sysNotice">
			<br/>
			<br/>
				<div >
					<span class="div_margin_left  div_color_red" >* </span><span >标题</span>
					<input class="easyui-textbox"  style="width:250px;height:32px;" id="vacTitle" name="vacTitle"  validType="length[0,30]" />
<!-- 					<span class="div_margin_left  div_color_red" >* </span><span >内容</span> -->
<!-- 					<input class="easyui-textbox" id="textContent"　name="textContent"  style="width:300px;height:32px;" /> -->
<!-- 						&nbsp;&nbsp;&nbsp;&nbsp; -->
					<a href="javascript:searchNotice();" id="searchNotice"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<permission:hasPermission action="sys/notice/sysNotice-add">
					<a href="javascript:addNotice();" id="addNotice"  iconCls="icon-save" class="easyui-linkbutton a_button">发布公告</a>
					</permission:hasPermission>
				</div>
				<br/>
			<br/>
		</form>
		<div style="margin: 10px;margin-left: 20px;">
		<table class="easyui-datagrid" id="notice-datagrid" 	style="width: 100%;" pagination="true">
		</table>
		</div>
	</div>





